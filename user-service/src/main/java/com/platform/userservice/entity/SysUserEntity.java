package com.platform.userservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月19日 16:34
 */
@TableName("sys_user")
@Data
public class SysUserEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String username;

    private String realName;

    private String mobile;

    private String email;

    private Integer enabled;

    private Integer locked;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
