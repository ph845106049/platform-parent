package com.platform.idpauth.infrastructure.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.idpauth.domain.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 12:03
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select id, username, password, enabled,locked from sys_user where username = #{username}")
    SysUser findByUsername(String username);

    @Select("select id, username, password, enabled from sys_user where id = #{id}")
    SysUser findById(Long id);

    @Update("update sys_user set locked = 1 where id = #{id}")
    void lock(Long id);

    @Select("select count(1) from sys_user where username = #{username}")
    int countByUsername(String username);

    int insert(SysUser user);
}
