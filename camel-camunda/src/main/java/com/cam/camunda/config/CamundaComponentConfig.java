package com.cam.camunda.config;

import org.apache.camel.CamelContext;
import org.apache.camel.component.camunda.CamundaComponent;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CamundaComponentConfig {

    @Value("${camunda.client.grpc-address}")
    private String grpcAddress;

    @Value("${camunda.client.rest-address}")
    private String restAddress;

    @Bean
    CamelContextConfiguration camundaConfiguration() {

        return new CamelContextConfiguration() {

            @Override
            public void beforeApplicationStart(CamelContext context) {

                CamundaComponent camunda =
                        context.getComponent(
                                "camunda",
                                CamundaComponent.class);

                camunda.setGrpcAddress(grpcAddress);
                camunda.setRestAddress(restAddress);
            }

            @Override
            public void afterApplicationStart(CamelContext context) {
            }
        };
    }
}