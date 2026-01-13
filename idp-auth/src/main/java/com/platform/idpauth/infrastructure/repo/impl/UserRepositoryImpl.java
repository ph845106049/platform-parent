package com.platform.idpauth.infrastructure.repo.impl;

import com.platform.idpauth.domain.model.SysUser;
import com.platform.idpauth.infrastructure.repo.UserRepository;
import com.platform.idpauth.infrastructure.repository.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 12:02
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SysUserMapper sysUserMapper;

    @Override
    public SysUser findByUsername(String username) {
        return sysUserMapper.findByUsername(username);
    }

    @Override
    public SysUser findById(Long id) {
        return sysUserMapper.findById(id);
    }

    @Override
    public void lock(Long id) {
        sysUserMapper.lock(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return sysUserMapper.countByUsername(username) > 0;
    }

    @Override
    public void save(SysUser user) {
        sysUserMapper.insert(user);
    }

}
