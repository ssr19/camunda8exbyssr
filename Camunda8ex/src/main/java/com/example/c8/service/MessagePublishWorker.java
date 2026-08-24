package com.example.c8.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import io.camunda.client.CamundaClient;
import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;

@Component
public class MessagePublishWorker {

	private static final Logger LOG = LoggerFactory.getLogger(MessagePublishWorker.class);
	
	 private final CamundaClient camundaClient;
	 private final KafkaTemplate<String, String> kafkaTemplate;
	 
	    public MessagePublishWorker(CamundaClient camundaClient, KafkaTemplate<String, String> kafkaTemplate) {
	        this.camundaClient = camundaClient;
	        this.kafkaTemplate = kafkaTemplate;
	    }
	
	@JobWorker(type = "orderReceived_msg_send")	
    public void handleSendOrder(ActivatedJob job, JobClient client) {
        Map<String, Object> vars = job.getVariablesAsMap();
        String orderId = (String) vars.get("orderId");

        // Publish the BPMN message
        camundaClient.newPublishMessageCommand()
                     .messageName("OrderReceivedMessage")
                     .correlationKey(orderId)
                     .variables(Map.of("orderId", orderId, "status", "CONFIRMED"))
                     .send()
                     .join();

     // 2️⃣ Produce Kafka event for other microservices
        String payload = "{ \"orderId\": \"" + orderId + "\", \"status\": \"CONFIRMED\" }";
        kafkaTemplate.send("camunda-topic", orderId, payload);
        
        
        // Complete the service task job
        client.newCompleteCommand(job.getKey()).send().join();

        LOG.info("############ Message published and job completed for orderId=" + orderId);
    }

	
}
