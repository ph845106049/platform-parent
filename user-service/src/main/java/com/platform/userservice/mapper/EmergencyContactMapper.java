package com.platform.userservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.userservice.entity.EmergencyContactEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 类说明：EmergencyContactMapper 负责紧急联系人关系的数据库访问。
 */
@Mapper
public interface EmergencyContactMapper extends BaseMapper<EmergencyContactEntity> {

    @Select("select count(1) from emergency_contact where user_id = #{userId}")
    int countByUserId(Long userId);
}
