package com.platform.userservice.controller;

import com.platform.common.base.utils.R;
import com.platform.userservice.model.dto.EmergencyContactDTO;
import com.platform.userservice.service.EmergencyContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 类说明：EmergencyContactController 负责紧急联系人绑定、审核与查询接口。
 */
@RestController
@RequestMapping("/api/emergency")
@RequiredArgsConstructor
public class EmergencyContactController {

    private final EmergencyContactService emergencyContactService;

    @PostMapping("/bind/send-code")
    public R<Void> sendBindCode(@RequestParam Long userId,
                                @RequestParam String contactName,
                                @RequestParam String contactMobile) {
        emergencyContactService.sendBindCode(userId, contactName, contactMobile);
        return R.okVoid();
    }

    @PostMapping("/bind/confirm")
    public R<Void> confirmBind(@RequestParam Long userId,
                               @RequestParam String contactName,
                               @RequestParam String contactMobile,
                               @RequestParam String code) {
        emergencyContactService.confirmBind(userId, contactName, contactMobile, code);
        return R.okVoid();
    }

    @PostMapping("/bind-admin")
    public R<Void> bindAdminContact(@RequestParam Long userId) {
        emergencyContactService.bindAdminContact(userId);
        return R.okVoid();
    }

    @GetMapping("/my")
    public R<List<EmergencyContactDTO>> myContacts(@RequestParam Long userId) {
        return R.ok(emergencyContactService.myContacts(userId));
    }

    @GetMapping("/active")
    public R<List<EmergencyContactDTO>> activeContacts(@RequestParam Long userId) {
        return R.ok(emergencyContactService.activeContacts(userId));
    }

    @GetMapping("/owners-active-users")
    public R<List<Long>> ownersWithActiveContacts() {
        return R.ok(emergencyContactService.ownersWithActiveContacts());
    }

    @DeleteMapping("/unbind")
    public R<Void> unbind(@RequestParam Long userId, @RequestParam Long relationId) {
        emergencyContactService.unbind(userId, relationId);
        return R.okVoid();
    }
}
