package com.platform.userservice.controller;

import com.platform.authclient.context.UserContext;
import com.platform.common.base.utils.R;
import com.platform.userservice.model.dto.EmergencyContactDTO;
import com.platform.userservice.service.EmergencyContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * 类说明：EmergencyContactController 负责紧急联系人绑定、审核与查询接口。
 */
@RestController
@RequestMapping("/api/emergency")
@RequiredArgsConstructor
public class EmergencyContactController {

    private final EmergencyContactService emergencyContactService;

    @Value("${platform.internal.api-token:alive-internal-token}")
    private String internalApiToken;

    private Long currentUserId() {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "请先登录后再操作");
        }
        return userId;
    }

    private void verifyInternalToken(String token) {
        if (token == null || token.isBlank() || !internalApiToken.equals(token)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "内部接口鉴权失败");
        }
    }

    @PostMapping("/bind/send-code")
    public R<Void> sendBindCode(@RequestParam String contactName,
                                @RequestParam String contactMobile) {
        emergencyContactService.sendBindCode(currentUserId(), contactName, contactMobile);
        return R.okVoid();
    }

    @PostMapping("/bind/confirm")
    public R<Void> confirmBind(@RequestParam String contactName,
                               @RequestParam String contactMobile,
                               @RequestParam String code) {
        emergencyContactService.confirmBind(currentUserId(), contactName, contactMobile, code);
        return R.okVoid();
    }

    @PostMapping("/bind-admin")
    public R<Void> bindAdminContact() {
        emergencyContactService.bindAdminContact(currentUserId());
        return R.okVoid();
    }

    @GetMapping("/my")
    public R<List<EmergencyContactDTO>> myContacts() {
        return R.ok(emergencyContactService.myContacts(currentUserId()));
    }

    @GetMapping("/active")
    public R<List<EmergencyContactDTO>> activeContacts() {
        return R.ok(emergencyContactService.activeContacts(currentUserId()));
    }

    @GetMapping("/internal/active")
    public R<List<EmergencyContactDTO>> internalActiveContacts(@RequestParam Long userId,
                                                               @RequestHeader(value = "X-Internal-Token", required = false) String internalToken) {
        verifyInternalToken(internalToken);
        return R.ok(emergencyContactService.activeContacts(userId));
    }

    @GetMapping("/owners-active-users")
    public R<List<Long>> ownersWithActiveContacts(
            @RequestHeader(value = "X-Internal-Token", required = false) String internalToken) {
        verifyInternalToken(internalToken);
        return R.ok(emergencyContactService.ownersWithActiveContacts());
    }

    @DeleteMapping("/unbind")
    public R<Void> unbind(@RequestParam Long relationId) {
        emergencyContactService.unbind(currentUserId(), relationId);
        return R.okVoid();
    }
}
