package com.cam.camunda.jobworker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;

@Component
public class AssignApprover {

	Logger logger= LoggerFactory.getLogger(getClass());
	
	@JobWorker(type = "assign_approver")
	public void assignApprover(ActivatedJob activatedJob, JobClient jobClient){
		
		
		String approver = (activatedJob.getVariable("approver") != null ) ? activatedJob.getVariable("approver").toString() : null;
		
		logger.info("assign approver:"+approver);
		
		jobClient.newCompleteCommand(activatedJob.getKey())
		.send()
		.join();
	}
	
}
