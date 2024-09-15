package com.example.aws.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<?> test() {

        System.out.println("테스트 용");
        return ResponseEntity.ok("TEST_API");
    }
}
