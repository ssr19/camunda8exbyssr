package com.example.c8.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.c8.dto.OrderRequest;
import com.example.c8.service.ProcessStarterService;

@RestController
@RequestMapping(path = "/camunda8ssr")
public class ProcessStartRestController {

	@Autowired
	ProcessStarterService processStarterService;
	
	private static final Logger LOG = LoggerFactory.getLogger(ProcessStartRestController.class);
	
	@PostMapping(path = "/startC8")
	public ResponseEntity<String> startC8Instance(@RequestBody OrderRequest orderRequest) {

		LOG.info("############# Rest Request received for start instance ###################");
		
		String res = processStarterService.startProcess(orderRequest);
		
		  LOG.info("############# After Process Started Successfully ###################");
		  
		  return ResponseEntity.ok(res);
	}
	
	@PostMapping(path = "/deleteC8")
	public ResponseEntity<String> deleteC8Instance(@RequestBody OrderRequest orderRequest) {

		LOG.info("############# Rest Request received for delete instance ###################");
		
		String res = processStarterService.deleteProcess(orderRequest.getProcessInstanceKey());
		
		  LOG.info("############# After Process Deleted Successfully ###################");
		  
		  return ResponseEntity.ok(res);
	}
	
}
