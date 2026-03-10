package com.platform.userservice.service;

import com.platform.userservice.model.dto.EmergencyContactDTO;

import java.util.List;

/**
 * 类说明：EmergencyContactService 负责紧急联系人申请、审核与查询能力。
 */
public interface EmergencyContactService {

    void sendBindCode(Long userId, String contactName, String contactMobile);

    void confirmBind(Long userId, String contactName, String contactMobile, String code);

    void bindAdminContact(Long userId);

    List<EmergencyContactDTO> myContacts(Long userId);

    List<EmergencyContactDTO> activeContacts(Long userId);

    List<Long> ownersWithActiveContacts();

    void unbind(Long userId, Long relationId);
}
