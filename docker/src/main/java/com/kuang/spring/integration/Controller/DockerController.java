package com.kuang.spring.integration.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockerController {

    @GetMapping("/docker")
    public String getDockerInfo() {
        return "hello docker!";
    }
}
