package com.platform.signservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.signservice.entity.AlertLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 类说明：AlertLogMapper 负责风险提醒日志表读写。
 */
@Mapper
public interface AlertLogMapper extends BaseMapper<AlertLogEntity> {
}
