package com.platform.signservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.signservice.entity.SignGoalEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 类说明：SignGoalMapper 负责用户连续签到目标配置的数据库访问。
 */
@Mapper
public interface SignGoalMapper extends BaseMapper<SignGoalEntity> {
}
