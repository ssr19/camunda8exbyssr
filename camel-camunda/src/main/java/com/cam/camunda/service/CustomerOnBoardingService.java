package com.cam.camunda.service;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.camunda.model.ProcessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cam.camunda.dto.CustomerOnBoardingRequestDTO;

@Service
public class CustomerOnBoardingService {

	@Autowired
	ProducerTemplate producerTemplate;
	
	public String addCustomer(CustomerOnBoardingRequestDTO customerOnBoardingRequestDTO) {
		ProcessResponse response = producerTemplate.requestBody("direct:valiadteCustomerRequest",customerOnBoardingRequestDTO, ProcessResponse.class);
		return "response :"+response.getProcessId();
	}
	
}
