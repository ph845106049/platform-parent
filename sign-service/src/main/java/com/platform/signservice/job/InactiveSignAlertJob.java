package com.platform.signservice.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.platform.common.base.model.dto.NoSignAlertMessage;
import com.platform.signservice.entity.AlertLogEntity;
import com.platform.signservice.mapper.AlertLogMapper;
import com.platform.signservice.mq.AlertMessagePublisher;
import com.platform.signservice.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 类说明：InactiveSignAlertJob 负责扫描连续三天未签到用户并通知其紧急联系人。
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class InactiveSignAlertJob {

    private static final String ALERT_TYPE = "NO_SIGN_3_DAYS";

    private final SignService signService;
    private final AlertLogMapper alertLogMapper;
    private final RestTemplate restTemplate;
    private final AlertMessagePublisher alertMessagePublisher;

    @Value("${platform.user-service.base-url:http://localhost:8000}")
    private String userServiceBaseUrl;

    @Value("${platform.internal.api-token:alive-internal-token}")
    private String internalApiToken;

    /**
     * 每天 09:00 触发一次，扫描三天未签到用户。
     */
    @Scheduled(cron = "${platform.alert.no-sign-cron:0 0 9 * * ?}")
    public void scanAndAlertNoSignUsers() {
        List<Long> owners = getOwnersWithActiveContacts();
        if (owners == null || owners.isEmpty()) {
            return;
        }

        for (Long ownerId : owners) {
            try {
                if (alreadyAlertedToday(ownerId)) {
                    continue;
                }
                if (signService.hasSignedWithinDays(ownerId, 3)) {
                    continue;
                }
                notifyEmergencyContacts(ownerId);
                saveAlertLog(ownerId);
            } catch (Exception ex) {
                log.error("扫描未签到提醒失败, userId={}", ownerId, ex);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private List<Long> getOwnersWithActiveContacts() {
        String url = userServiceBaseUrl + "/api/emergency/owners-active-users";
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, internalRequestEntity(), Map.class);
        Map<String, Object> body = response.getBody();
        if (body == null || body.get("data") == null) {
            return List.of();
        }
        List<Object> ids = (List<Object>) body.get("data");
        return ids.stream().map(v -> ((Number) v).longValue()).toList();
    }

    @SuppressWarnings("unchecked")
    private void notifyEmergencyContacts(Long ownerId) {
        String url = userServiceBaseUrl + "/api/emergency/internal/active?userId=" + ownerId;
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, internalRequestEntity(), Map.class);
        Map<String, Object> body = response.getBody();
        if (body == null || body.get("data") == null) {
            return;
        }

        List<Map<String, Object>> contacts = (List<Map<String, Object>>) body.get("data");
        for (Map<String, Object> contact : contacts) {
            String ownerName = contact.get("username") == null ? String.valueOf(ownerId) : String.valueOf(contact.get("username"));
            String content = ownerName + "已经三天没有在平台进行签到，请及时查看" + ownerName + "状态";
            String contactMobile = contact.get("contactMobile") == null ? null : String.valueOf(contact.get("contactMobile"));
            if (contactMobile == null || contactMobile.isBlank()) {
                continue;
            }
            NoSignAlertMessage message = new NoSignAlertMessage();
            message.setUserId(ownerId);
            message.setUsername(ownerName);
            message.setTargetMobile(contactMobile);
            message.setContent(content);
            alertMessagePublisher.publish(message);
        }
    }

    private boolean alreadyAlertedToday(Long userId) {
        return alertLogMapper.selectCount(new LambdaQueryWrapper<AlertLogEntity>()
                .eq(AlertLogEntity::getUserId, userId)
                .eq(AlertLogEntity::getAlertType, ALERT_TYPE)
                .eq(AlertLogEntity::getAlertDate, LocalDate.now())) > 0;
    }

    private void saveAlertLog(Long userId) {
        AlertLogEntity logEntity = new AlertLogEntity();
        logEntity.setUserId(userId);
        logEntity.setAlertType(ALERT_TYPE);
        logEntity.setAlertDate(LocalDate.now());
        logEntity.setCreatedAt(LocalDateTime.now());
        alertLogMapper.insert(logEntity);
    }

    private HttpEntity<Void> internalRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Internal-Token", internalApiToken);
        return new HttpEntity<>(headers);
    }
}
