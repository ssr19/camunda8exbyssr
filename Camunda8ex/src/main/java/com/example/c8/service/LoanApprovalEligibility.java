package com.example.c8.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.api.response.ActivatedJob;
import io.camunda.client.api.worker.JobClient;

@Component
public class LoanApprovalEligibility {

	private static final Logger LOG = LoggerFactory.getLogger(LoanApprovalEligibility.class);
	
	@JobWorker(type = "unique_loanApprovalEligibility_result")
	public void eligibilityResultUnique(final ActivatedJob activatedJob, final JobClient jobClient) {
		
		Map<String, Object> resultVarsMAp= activatedJob.getVariablesAsMap();
		
		if(resultVarsMAp != null && resultVarsMAp.containsKey("isApproved") && null != resultVarsMAp.get("isApproved")) {
			boolean isApproved = (boolean) resultVarsMAp.get("isApproved");
		
		LOG.info("No error If - Loan approval DMN result:"+isApproved);
		
		jobClient.newCompleteCommand(activatedJob.getKey())
		 .variables(Map.of("loanApprovalStatus", isApproved == true ? "APPROVED" : "DECLINED"))
         .send()
         .join();
		}else {
			
			LOG.info("Error Else - Loan approval DMN result:");
			
			jobClient.newFailCommand(activatedJob)
			.retries(activatedJob.getRetries()-1)
			.send()
			.join();
		}
		
	}
	
	
	@JobWorker(type = "ruleorder_loanApprovalEligibility_result")
	public void eligibilityResultRuleOrder(final ActivatedJob activatedJob, final JobClient jobClient) {
		
		Map<String, Object> resultVarsMAp= activatedJob.getVariablesAsMap();
		
		if(resultVarsMAp != null && resultVarsMAp.containsKey("isApproved") && null != resultVarsMAp.get("isApproved")) {
			List<Boolean> loanApprovedList = (List<Boolean>) resultVarsMAp.get("isApproved");
		
		LOG.info("RuleOrder No error If - Loan approval DMN result:"+loanApprovedList);
		loanApprovedList.stream().forEach(result -> LOG.info("Result:"+result.booleanValue()));
		jobClient.newCompleteCommand(activatedJob.getKey())
		 .variables(Map.of("loanApprovalStatus", "APPROVED"))
         .send()
         .join();
		}else {
			
			LOG.info("RuleOrder Error Else - Loan approval DMN result:");
			
			jobClient.newFailCommand(activatedJob)
			.retries(activatedJob.getRetries()-1)
			.send()
			.join();
		}
		
	}
	
	
	@JobWorker(type = "collect_loanApprovalEligibility_result")
	public void eligibilityResultcollect(final ActivatedJob activatedJob, final JobClient jobClient) {
		
		Map<String, Object> resultVarsMAp= activatedJob.getVariablesAsMap();
		
		if(resultVarsMAp != null && resultVarsMAp.containsKey("approvedAmount") && null != resultVarsMAp.get("approvedAmount")) {
			List<Integer> loanApprovedList = (List<Integer>) resultVarsMAp.get("approvedAmount");
		
		LOG.info("collect No error If - Loan approval DMN result:"+loanApprovedList);
		loanApprovedList.stream().forEach(result -> LOG.info("Result:"+result.intValue()));
		jobClient.newCompleteCommand(activatedJob.getKey())
		 .variables(Map.of("loanApprovalStatus", "APPROVED"))
         .send()
         .join();
		}else {
			
			LOG.info("collect Error Else - Loan approval DMN result:");
			
			jobClient.newFailCommand(activatedJob)
			.retries(activatedJob.getRetries()-1)
			.send()
			.join();
		}
		
	}
	
	@JobWorker(type = "collect_SUM_loanApprovalEligibility_result")
	public void eligibilityResultcollectSUM(final ActivatedJob activatedJob, final JobClient jobClient) {
		
		try {
			
		Map<String, Object> resultVarsMAp= activatedJob.getVariablesAsMap();
		
		if(resultVarsMAp != null && resultVarsMAp.containsKey("approvedAmount") && null != resultVarsMAp.get("approvedAmount")) {
			int totalLoanApproved =  (int) resultVarsMAp.get("approvedAmount");
		
		LOG.info("collect SUM No error If - Loan approval DMN result:"+totalLoanApproved);
		jobClient.newCompleteCommand(activatedJob.getKey())
		 .variables(Map.of("loanApprovalStatus", "APPROVED"))
         .send()
         .join();
		
		//throw new Exception("technical error failed");
		}else {
			
			LOG.info("collect SUM Error Else - Loan approval DMN result:");
			
			jobClient.newThrowErrorCommand(activatedJob.getKey())
            .errorCode("BUSINESS_LOAN_DENIED")
            .errorMessage("Applicant does not meet minimum score")
            .variables(Map.of("technicalErrorReason", "Applicant does not meet minimum score"))
            .send()
            .join();
			
			
		}
		
		}catch (Exception ex) {

			
			jobClient.newFailCommand(activatedJob.getKey())
			.retries(Math.max(activatedJob.getRetries() - 1, 0))
			.errorMessage("External service timeout: " + ex.getMessage())
			 .variables(Map.of("technicalErrorReason", "External service timeout: " + ex.getMessage()))
			.send()
			.join();
		}
	}
}
