package com.cam.camunda.jobworker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;

@Component
public class PrepareApprovers {

	Logger LOG = LoggerFactory.getLogger(getClass());
	
	@JobWorker(type = "prepare_approvers" )
	public void prepareApproversList(ActivatedJob activatedJob, JobClient jobClient) {
		
		Map<String, Object> invars= new HashMap<>();
		invars.put("approvers", List.of("manager1", "manager2", "manager3"));
		
		
		jobClient.newCompleteCommand(activatedJob.getKey())
		.variables(invars)
		.send()
		.join();
		
		LOG.info("newCompleteCommand completed");
	}
	
	
}
