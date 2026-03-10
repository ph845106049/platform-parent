package com.platform.signservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.signservice.entity.SignEntity;
import com.platform.common.base.model.dto.SignRecordDTO;
import com.platform.signservice.model.dto.SignStreakProgressDTO;


/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 */
public interface SignService extends IService<SignEntity> {

    /**
     * 今天是否签到
     */
    boolean hasSignedToday(Long userId);

    /**
     * 昨日签到信息
     */
    SignRecordDTO getYesterdaySign(Long userId);

    /**
     * 签到
     */
    void sign(Long userId, String message);

    /**
     * 在最近 N 天内是否有签到记录
     */
    boolean hasSignedWithinDays(Long userId, int days);

    /**
     * 设置用户连续签到目标天数
     */
    void setStreakGoalDays(Long userId, Integer goalDays);

    /**
     * 获取用户连续签到进度
     */
    SignStreakProgressDTO getStreakProgress(Long userId);
}
