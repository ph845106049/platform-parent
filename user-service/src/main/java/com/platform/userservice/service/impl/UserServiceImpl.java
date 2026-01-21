package com.platform.userservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.authclient.context.UserContext;
import com.platform.common.common.model.dto.SysUserDTO;
import com.platform.userservice.entity.SysUserEntity;
import com.platform.userservice.mapper.UserMapper;
import com.platform.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月19日 16:32
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUserEntity> implements UserService {

    @Override
    public SysUserDTO getUser() {
        Long userId = UserContext.getUserId();
        SysUserEntity user = baseMapper.selectById(userId);
        SysUserDTO userDTO = new SysUserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
