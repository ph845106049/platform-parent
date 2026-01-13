package com.platform.idpauth.infrastructure.repo;

import com.platform.common.model.model.SysUser;

/**
 * @author lhq
 * @version 1.0
 * @description:
 * @date @date 2026年01月13日 11:42
 */
public interface UserRepository {

    SysUser findByUsername(String username);

    SysUser findById(Long id);

    void lock(Long id);

    boolean existsByUsername(String username);

    void save(SysUser user);
}
