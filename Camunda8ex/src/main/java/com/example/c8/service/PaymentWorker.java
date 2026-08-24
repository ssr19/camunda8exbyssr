package com.example.c8.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;

@Component
public class PaymentWorker {
	private static final Logger LOG = LoggerFactory.getLogger(PaymentWorker.class);
	/*
    @JobWorker(type = "order_received_job")
    public Map<String, Object> handlePayment(Map<String, Object> variables) {
        String orderId = (String) variables.get("orderId");
        Double amount = (Double) variables.get("amount");
        LOG.info("############# PaymentWorker JobWorker Started Successfully ###################");
        boolean success = amount < 1000;
        return Map.of("paymentStatus", success ? "APPROVED" : "DECLINED");
    }
    */
    @JobWorker(type = "order_received_job")
    public void handlePayment(final ActivatedJob job, final JobClient client) {
        Map<String, Object> vars = job.getVariablesAsMap();
        //String orderId = (String) vars.get("orderId");
        Double amount = Double.valueOf(String.valueOf(vars.get("amount")));

        LOG.info("############# PaymentWorker JobWorker Started Successfully ################### amount"+amount);
        client.newCompleteCommand(job.getKey())
              .variables(Map.of("paymentStatus", amount < 1000 ? "APPROVED" : "DECLINED","orderId","triggerMsgEventSuccess"))
              .send()
              .join();
    }

}
