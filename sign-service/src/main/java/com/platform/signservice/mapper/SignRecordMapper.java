package com.platform.signservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.signservice.entity.SignEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年03月05日 18:30
 */
@Mapper
public interface SignRecordMapper extends BaseMapper<SignEntity> {
}
