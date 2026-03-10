package com.platform.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.platform.userservice.entity.EmergencyContactEntity;
import com.platform.userservice.entity.SysUserEntity;
import com.platform.userservice.mapper.EmergencyContactMapper;
import com.platform.userservice.mapper.UserMapper;
import com.platform.userservice.model.dto.EmergencyContactDTO;
import com.platform.userservice.service.EmergencyContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类说明：EmergencyContactServiceImpl 负责短信验证码绑定紧急联系人的业务实现。
 */
@Service
@RequiredArgsConstructor
public class EmergencyContactServiceImpl implements EmergencyContactService {

    private static final int MAX_ACTIVE_CONTACTS = 3;
    private static final Duration CODE_TTL = Duration.ofMinutes(5);
    private static final Duration SEND_INTERVAL = Duration.ofSeconds(60);

    private final EmergencyContactMapper emergencyContactMapper;
    private final UserMapper userMapper;
    private final StringRedisTemplate redisTemplate;
    private final RestTemplate restTemplate;

    @org.springframework.beans.factory.annotation.Value("${platform.sms-service.base-url:http://localhost:8100}")
    private String smsServiceBaseUrl;

    @org.springframework.beans.factory.annotation.Value("${platform.admin-contact.name:Alive管理员}")
    private String adminContactName;

    @org.springframework.beans.factory.annotation.Value("${platform.admin-contact.mobile:13800000000}")
    private String adminContactMobile;

    @Override
    public void sendBindCode(Long userId, String contactName, String contactMobile) {
        validateUser(userId);
        validateContactInput(contactName, contactMobile);
        validateMaxAndDuplicate(userId, contactMobile, false);

        String guardKey = sendGuardKey(userId, contactMobile);
        if (Boolean.FALSE.equals(redisTemplate.opsForValue().setIfAbsent(guardKey, "1", SEND_INTERVAL))) {
            throw new IllegalStateException("验证码发送过于频繁，请稍后再试");
        }

        String code = randomCode();
        redisTemplate.opsForValue().set(codeKey(userId, contactMobile), code, CODE_TTL);

        Map<String, Object> payload = new HashMap<>();
        payload.put("targetMobile", contactMobile);
        payload.put("content", "Alive 紧急联系人绑定验证码：" + code + "，5分钟内有效。");
        restTemplate.postForEntity(smsServiceBaseUrl + "/api/sms/notify", payload, Void.class);
    }

    @Override
    public void confirmBind(Long userId, String contactName, String contactMobile, String code) {
        validateUser(userId);
        validateContactInput(contactName, contactMobile);
        validateMaxAndDuplicate(userId, contactMobile, false);

        String cacheCode = redisTemplate.opsForValue().get(codeKey(userId, contactMobile));
        if (cacheCode == null || !cacheCode.equals(code)) {
            throw new IllegalArgumentException("验证码错误或已过期");
        }

        insertContact(userId, contactName, contactMobile);
        redisTemplate.delete(codeKey(userId, contactMobile));
    }

    @Override
    public void bindAdminContact(Long userId) {
        validateUser(userId);
        validateContactInput(adminContactName, adminContactMobile);
        validateMaxAndDuplicate(userId, adminContactMobile, true);
        insertContact(userId, adminContactName, adminContactMobile);
    }

    @Override
    public List<EmergencyContactDTO> myContacts(Long userId) {
        validateUser(userId);
        List<EmergencyContactEntity> rows = emergencyContactMapper.selectList(new LambdaQueryWrapper<EmergencyContactEntity>()
                .eq(EmergencyContactEntity::getUserId, userId)
                .orderByDesc(EmergencyContactEntity::getCreatedAt));
        return toDTO(rows);
    }

    @Override
    public List<EmergencyContactDTO> activeContacts(Long userId) {
        return myContacts(userId);
    }

    @Override
    public List<Long> ownersWithActiveContacts() {
        return emergencyContactMapper.selectList(new LambdaQueryWrapper<EmergencyContactEntity>()
                        .select(EmergencyContactEntity::getUserId))
                .stream()
                .map(EmergencyContactEntity::getUserId)
                .distinct()
                .toList();
    }

    @Override
    public void unbind(Long userId, Long relationId) {
        EmergencyContactEntity relation = emergencyContactMapper.selectById(relationId);
        if (relation == null) {
            throw new IllegalArgumentException("绑定关系不存在: " + relationId);
        }
        if (!relation.getUserId().equals(userId)) {
            throw new IllegalArgumentException("只能解绑自己绑定的紧急联系人");
        }
        emergencyContactMapper.deleteById(relationId);
    }

    private void validateUser(Long userId) {
        if (userId == null || userMapper.selectById(userId) == null) {
            throw new IllegalArgumentException("用户不存在: " + userId);
        }
    }

    private void validateContactInput(String contactName, String contactMobile) {
        if (contactName == null || contactName.isBlank()) {
            throw new IllegalArgumentException("联系人姓名不能为空");
        }
        if (contactMobile == null || contactMobile.isBlank()) {
            throw new IllegalArgumentException("联系人手机号不能为空");
        }
    }

    private void validateMaxAndDuplicate(Long userId, String contactMobile, boolean skipLimit) {
        String normalizedMobile = contactMobile.trim();
        String adminMobile = adminContactMobile == null ? "" : adminContactMobile.trim();

        if (!skipLimit && !normalizedMobile.equals(adminMobile)) {
            Long nonAdminCount = emergencyContactMapper.selectCount(new LambdaQueryWrapper<EmergencyContactEntity>()
                    .eq(EmergencyContactEntity::getUserId, userId)
                    .ne(EmergencyContactEntity::getContactMobile, adminMobile));
            if (nonAdminCount != null && nonAdminCount >= MAX_ACTIVE_CONTACTS) {
                throw new IllegalStateException("普通紧急联系人最多只能绑定3个（管理员不计入）");
            }
        }

        Long duplicate = emergencyContactMapper.selectCount(new LambdaQueryWrapper<EmergencyContactEntity>()
                .eq(EmergencyContactEntity::getUserId, userId)
                .eq(EmergencyContactEntity::getContactMobile, normalizedMobile));
        if (duplicate != null && duplicate > 0) {
            throw new IllegalStateException("该手机号已绑定为紧急联系人");
        }
    }

    private void insertContact(Long userId, String contactName, String contactMobile) {
        EmergencyContactEntity entity = new EmergencyContactEntity();
        entity.setUserId(userId);
        entity.setContactName(contactName.trim());
        entity.setContactMobile(contactMobile.trim());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setBoundAt(LocalDateTime.now());
        emergencyContactMapper.insert(entity);
    }

    private List<EmergencyContactDTO> toDTO(List<EmergencyContactEntity> rows) {
        Map<Long, SysUserEntity> users = userMapper.selectBatchIds(rows.stream().map(EmergencyContactEntity::getUserId).distinct().toList())
                .stream().collect(java.util.stream.Collectors.toMap(SysUserEntity::getId, v -> v));

        return rows.stream().map(row -> {
            EmergencyContactDTO dto = new EmergencyContactDTO();
            dto.setId(row.getId());
            dto.setUserId(row.getUserId());
            dto.setContactName(row.getContactName());
            dto.setContactMobile(row.getContactMobile());
            dto.setCreatedAt(row.getCreatedAt());
            dto.setBoundAt(row.getBoundAt());
            SysUserEntity user = users.get(row.getUserId());
            dto.setUsername(user == null ? null : user.getUsername());
            return dto;
        }).toList();
    }

    private String codeKey(Long userId, String mobile) {
        return "EMERGENCY:BIND:CODE:" + userId + ":" + mobile.trim();
    }

    private String sendGuardKey(Long userId, String mobile) {
        return "EMERGENCY:BIND:SEND_GUARD:" + userId + ":" + mobile.trim();
    }

    private String randomCode() {
        int num = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(num);
    }
}
