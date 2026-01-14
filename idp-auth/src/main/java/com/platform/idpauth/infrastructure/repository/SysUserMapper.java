package com.platform.idpauth.infrastructure.repository;

import com.platform.idpauth.domain.model.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 12:03
 */
@Mapper
public interface SysUserMapper {

    @Select("select id, username, password, enabled,locked from sys_user where username = #{username}")
    SysUser findByUsername(String username);

    @Select("select id, username, password, enabled from sys_user where id = #{id}")
    SysUser findById(Long id);

    @Update("update sys_user set locked = 1 where id = #{id}")
    void lock(Long id);

    @Select("select count(1) from sys_user where username = #{username}")
    int countByUsername(String username);

    @Insert("insert into sys_user(username,password,real_name,mobile,email,enabled,locked,create_time) values(#{username},#{password},#{realName},#{mobile},#{email},1,0,now())")
    void insert(SysUser user);
}
