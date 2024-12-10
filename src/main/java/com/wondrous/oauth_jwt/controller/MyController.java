package com.wondrous.oauth_jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class MyController {

    @GetMapping("/my")
    public String myPage() {
        return "my";
    }
}
