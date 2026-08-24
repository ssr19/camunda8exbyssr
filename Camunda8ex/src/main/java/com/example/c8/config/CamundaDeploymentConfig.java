package com.example.c8.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.core.util.FileUtil;
import io.camunda.client.CamundaClient;

//@Configuration
public class CamundaDeploymentConfig {

    @Bean
    public CommandLineRunner deployProcess(CamundaClient client) {
        return args -> {
            client
            .newDeployResourceCommand()
            .addResourceFile("D:\\learning\\camunda\\prjs\\Camunda8ex\\src\\main\\resources\\bpmn\\order_processc8.bpmn")
            .send()
            .join();
           
            System.out.println("✅ BPMN deployed successfully!");
        };
    }
}
