package com.dockerimgnexus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NexusDeploy {
    @GetMapping("/NexusDeploy")
    public String getData() {return  "we are deploying docker images on nexus via jenkins cicd pipeline,"; }
}