package com.platform.userservice.controller;


import com.platform.common.common.utils.R;
import com.platform.userservice.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserMeController {

    private final UserService userService;

    @GetMapping("getUser")
    public R getUser(){
        return R.ok(userService.getUser());
    }

}

