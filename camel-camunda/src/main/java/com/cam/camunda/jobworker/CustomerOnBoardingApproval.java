package com.cam.camunda.jobworker;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;


@Component
public class CustomerOnBoardingApproval {

	Logger LOG = LoggerFactory.getLogger(getClass());
	
	@JobWorker(type = "validate_onboarding_request")
	public void onboarding(final ActivatedJob activatedJob, final JobClient jobClient) {
		
		Map<String, Object> resultVarsMAp= activatedJob.getVariablesAsMap();
		
		LOG.info("After All Approvals all variables:"+resultVarsMAp.toString());
		
		if(resultVarsMAp != null && resultVarsMAp.containsKey("approvalResults") && null != resultVarsMAp.get("approvalResults")) {
			List<String> approvalList = (List<String>) resultVarsMAp.get("approvalResults");
		
		LOG.info("After All Approvals approvalResults:"+approvalList);
		
		jobClient.newCompleteCommand(activatedJob.getKey())
         .send()
         .join();
		
		}else {
			
			LOG.info("Error Else - After All Approvals approvalResults");
			
			jobClient.newFailCommand(activatedJob)
			.retries(activatedJob.getRetries()-1)
			.send()
			.join();
		}
		
	}
	
}
