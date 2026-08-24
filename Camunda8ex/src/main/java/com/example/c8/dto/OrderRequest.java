package com.example.c8.dto;

import javax.annotation.Nonnull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class OrderRequest {

	@Nonnull
	String source;
	
	@NumberFormat
	int amount;
	
	//@Nonnull
	//String orderId; 
	
	@Nonnull
	long processInstanceKey;
	
	@Nonnull
	String processID;
	
	LoanEligibilityReaquest loanEligibilityReaquest;
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	/*
	 * public String getOrderId() { return orderId; }
	 * 
	 * public void setOrderId(String orderId) { this.orderId = orderId; }
	 */
	public long getProcessInstanceKey() {
		return processInstanceKey;
	}

	public void setProcessInstanceKey(long processInstanceKey) {
		this.processInstanceKey = processInstanceKey;
	}

	public String getProcessID() {
		return processID;
	}

	public void setProcessID(String processID) {
		this.processID = processID;
	}

	public LoanEligibilityReaquest getLoanEligibilityReaquest() {
		return loanEligibilityReaquest;
	}

	public void setLoanEligibilityReaquest(LoanEligibilityReaquest loanEligibilityReaquest) {
		this.loanEligibilityReaquest = loanEligibilityReaquest;
	}


}
