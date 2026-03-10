package com.platform.signservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.base.model.dto.SignRecordDTO;
import com.platform.signservice.entity.SignEntity;
import com.platform.signservice.entity.SignGoalEntity;
import com.platform.signservice.mapper.SignRecordMapper;
import com.platform.signservice.mapper.SignGoalMapper;
import com.platform.signservice.model.dto.SignStreakProgressDTO;
import com.platform.signservice.service.SignService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年03月05日 18:28
 */
@Service
public class SignServiceImpl extends ServiceImpl<SignRecordMapper, SignEntity> implements SignService {

    private static final int DEFAULT_GOAL_DAYS = 7;
    private static final int MAX_QUERY_DAYS = 365;

    private final SignGoalMapper signGoalMapper;

    public SignServiceImpl(SignGoalMapper signGoalMapper) {
        this.signGoalMapper = signGoalMapper;
    }

    @Override
    public boolean hasSignedToday(Long userId) {
        LocalDate today = LocalDate.now();
        return baseMapper.selectCount(new LambdaQueryWrapper<SignEntity>()
                .eq(SignEntity::getUserId, userId)
                .eq(SignEntity::getSignDate, today)) > 0;
    }

    @Override
    public SignRecordDTO getYesterdaySign(Long userId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        SignEntity entity = baseMapper.selectOne(new LambdaQueryWrapper<SignEntity>()
                .eq(SignEntity::getUserId, userId)
                .eq(SignEntity::getSignDate, yesterday)
                .last("limit 1"));
        if (entity == null) {
            return null;
        }
        SignRecordDTO dto = new SignRecordDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setMessage(entity.getMessageForTomorrow());
        return dto;
    }

    @Override
    public void sign(Long userId, String message) {
        LocalDate today = LocalDate.now();
        long exists = baseMapper.selectCount(new LambdaQueryWrapper<SignEntity>()
                .eq(SignEntity::getUserId, userId)
                .eq(SignEntity::getSignDate, today));
        if (exists > 0) {
            throw new IllegalStateException("今天已经签到，无需重复签到");
        }

        SignEntity entity = new SignEntity();
        entity.setUserId(userId);
        entity.setSignDate(today);
        entity.setMessageForTomorrow(message);
        entity.setCreatedAt(LocalDateTime.now());
        baseMapper.insert(entity);
    }

    @Override
    public boolean hasSignedWithinDays(Long userId, int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("days 必须大于 0");
        }
        LocalDate fromDate = LocalDate.now().minusDays(days - 1L);
        return baseMapper.selectCount(new LambdaQueryWrapper<SignEntity>()
                .eq(SignEntity::getUserId, userId)
                .ge(SignEntity::getSignDate, fromDate)
                .le(SignEntity::getSignDate, LocalDate.now())) > 0;
    }

    @Override
    public void setStreakGoalDays(Long userId, Integer goalDays) {
        if (goalDays == null || goalDays <= 0) {
            throw new IllegalArgumentException("goalDays 必须大于 0");
        }

        SignGoalEntity existing = signGoalMapper.selectOne(new LambdaQueryWrapper<SignGoalEntity>()
                .eq(SignGoalEntity::getUserId, userId)
                .last("limit 1"));
        if (existing == null) {
            SignGoalEntity entity = new SignGoalEntity();
            entity.setUserId(userId);
            entity.setGoalDays(goalDays);
            entity.setCreatedAt(LocalDateTime.now());
            entity.setUpdatedAt(LocalDateTime.now());
            signGoalMapper.insert(entity);
            return;
        }
        existing.setGoalDays(goalDays);
        existing.setUpdatedAt(LocalDateTime.now());
        signGoalMapper.updateById(existing);
    }

    @Override
    public SignStreakProgressDTO getStreakProgress(Long userId) {
        Integer goalDays = getGoalDays(userId);
        Integer streakDays = calculateCurrentStreak(userId);
        SignStreakProgressDTO dto = new SignStreakProgressDTO();
        dto.setUserId(userId);
        dto.setGoalDays(goalDays);
        dto.setCurrentStreakDays(streakDays);
        dto.setReachedGoal(streakDays >= goalDays);
        return dto;
    }

    private Integer getGoalDays(Long userId) {
        SignGoalEntity existing = signGoalMapper.selectOne(new LambdaQueryWrapper<SignGoalEntity>()
                .eq(SignGoalEntity::getUserId, userId)
                .last("limit 1"));
        return existing == null ? DEFAULT_GOAL_DAYS : existing.getGoalDays();
    }

    private Integer calculateCurrentStreak(Long userId) {
        LocalDate fromDate = LocalDate.now().minusDays(MAX_QUERY_DAYS - 1L);
        Set<LocalDate> signedDays = baseMapper.selectList(new LambdaQueryWrapper<SignEntity>()
                        .eq(SignEntity::getUserId, userId)
                        .ge(SignEntity::getSignDate, fromDate)
                        .le(SignEntity::getSignDate, LocalDate.now())
                        .select(SignEntity::getSignDate))
                .stream()
                .map(SignEntity::getSignDate)
                .collect(Collectors.toSet());

        int streak = 0;
        LocalDate day = LocalDate.now();
        while (signedDays.contains(day)) {
            streak++;
            day = day.minusDays(1);
        }
        return streak;
    }
}
