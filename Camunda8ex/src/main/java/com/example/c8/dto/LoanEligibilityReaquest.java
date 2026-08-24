package com.example.c8.dto;

import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class LoanEligibilityReaquest {

	
	int age;
	
	boolean isDefaulter;
	
	double loanAmount;
	
	double monthlyIncome;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isDefaulter() {
		return isDefaulter;
	}

	public void setDefaulter(boolean isDefaulter) {
		this.isDefaulter = isDefaulter;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(double monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	
	
}
