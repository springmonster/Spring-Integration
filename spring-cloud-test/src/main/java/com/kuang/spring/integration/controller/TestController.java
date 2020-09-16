package com.kuang.spring.integration.controller;

import com.kuang.spring.integration.dto.TestDto;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/hello1")
    public String hello1() {
        return "hello test";
    }

    @PostMapping("/hello2")
    public String hello2(@RequestBody TestDto testDto) {
        return testDto.toString();
    }
}
