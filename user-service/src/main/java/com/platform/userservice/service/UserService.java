package com.platform.userservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.common.base.model.dto.SysUserDTO;
import com.platform.userservice.entity.SysUserEntity;

/**
 * 类说明：该类型负责所属模块中的核心功能实现与协作。
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月19日 16:31
 */
public interface UserService extends IService<SysUserEntity> {

    SysUserDTO getUser();

}
