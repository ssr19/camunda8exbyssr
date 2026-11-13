package com.cam.camunda.dto;

import org.springframework.stereotype.Component;

@Component
public class CustomerOnBoardingRequestDTO {

	private String name;
	private boolean existingAccountHolder;
	private boolean eligibleForBrokerOnboarding;
	private double loanAmount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isExistingAccountHolder() {
		return existingAccountHolder;
	}
	public void setExistingAccountHolder(boolean existingAccountHolder) {
		this.existingAccountHolder = existingAccountHolder;
	}
	public boolean isEligibleForBrokerOnboarding() {
		return eligibleForBrokerOnboarding;
	}
	public void setEligibleForBrokerOnboarding(boolean eligibleForBrokerOnboarding) {
		this.eligibleForBrokerOnboarding = eligibleForBrokerOnboarding;
	}
	public double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	
	
}
