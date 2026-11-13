package com.cam.camunda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cam.camunda.dto.CustomerOnBoardingRequestDTO;
import com.cam.camunda.service.CustomerOnBoardingService;

@RestController
@RequestMapping("/cusomeronboarding")
public class CustomerOnBoardingRequestController {

	@Autowired
	CustomerOnBoardingService customerOnBoardingService;
	
	@PostMapping("/onboarding")
	public ResponseEntity<String> onBoarding(@RequestBody CustomerOnBoardingRequestDTO customerOnBoardingRequestDTO){
		
		String response = customerOnBoardingService.addCustomer(customerOnBoardingRequestDTO);
		
		return ResponseEntity.ok("successfully added");
	}
}
