package com.platform.userservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.userservice.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月19日 16:35
 */
@Mapper
public interface UserMapper extends BaseMapper<SysUserEntity> {
}
