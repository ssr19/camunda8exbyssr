package com.cam.camunda.jobworker;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;

@Component
public class AfterApproval {

	Logger logger = LoggerFactory.getLogger(getClass());

	@JobWorker(type = "after_assign_approval")
	public void afterAssigneeApproval(ActivatedJob activatedJob, JobClient jobClient) {

		Map<String, Object> resultVarsMAp = activatedJob.getVariablesAsMap();

		logger.info("All Variables:"+resultVarsMAp.toString());
		if (resultVarsMAp != null && resultVarsMAp.containsKey("managerapproval")
				&& null != resultVarsMAp.get("managerapproval")) {
			String managerapproval = (String) resultVarsMAp.get("managerapproval");

			logger.info("After Loan approval result:" + managerapproval);

			String approver = (activatedJob.getVariable("approver") != null)
					? activatedJob.getVariable("approver").toString()
					: null;

			logger.info("after assigned approver:" + approver);

			Map<String, Object> approvalResult = new HashMap<String, Object>();
			approvalResult.put("approver", approver);
			approvalResult.put("decision", managerapproval);

			jobClient.newCompleteCommand(activatedJob.getKey()).variables(Map.of("CustomerOnBoardingStatus",
					managerapproval.equalsIgnoreCase("approved") ? "APPROVED" : "DECLINED", "approvalResult",approvalResult))
			.send()
			.join();
		} else {

			logger.info("Error Else - Loan approval result:");

			jobClient.newFailCommand(activatedJob).retries(activatedJob.getRetries() - 1).send().join();
		}
	}

}
