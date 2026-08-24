package com.example.c8.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;


@Component
public class InterruptingAndNonInterruptingJobWorker {

	private static final Logger LOG = LoggerFactory.getLogger(InterruptingAndNonInterruptingJobWorker.class);
	
	 @JobWorker(type = "int_nonint_job")
	public void intNonIntJobworker(ActivatedJob job, JobClient client) {
		
		 Map<String, Object> vars = job.getVariablesAsMap();
	        String orderId = (String) vars.get("orderId");
	        
	        LOG.info("InterruptingAndNonInterruptingJobWorker orderId:"+orderId);
		
	}
	
	
}
