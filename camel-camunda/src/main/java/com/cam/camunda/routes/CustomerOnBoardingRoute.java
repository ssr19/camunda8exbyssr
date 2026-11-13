package com.cam.camunda.routes;

import java.util.Map;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.camunda.model.ProcessRequest;
import org.apache.camel.component.camunda.model.ProcessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cam.camunda.dto.CustomerOnBoardingRequestDTO;

@Component
public class CustomerOnBoardingRoute extends RouteBuilder{

	private static final Logger LOG = LoggerFactory.getLogger(CustomerOnBoardingRoute.class);
	
	@Override
	public void configure() throws Exception {

		from("direct:valiadteCustomerRequest")
		.routeId("ValiadteCustomerRequestRoute")
		.log(LoggingLevel.INFO, "Valiadte Customer Request Received")
		.validate(body().isNotNull())
		//.validate(simple(
		//	    "${body} instanceof 'com.cam.camunda.dto.CustomerOnBoardingRequestDTO'"
		//	))
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				CustomerOnBoardingRequestDTO inbody=exchange.getIn().getBody(CustomerOnBoardingRequestDTO.class);
				//CustomerOnBoardingRequestDTO optional= Optional.ofNullable(inbody).orElseThrow(() -> new IllegalArgumentException(
                 //       "CustomerOnBoardingRequestDTO cannot be null"));
				
				ProcessRequest processRequest = new ProcessRequest();
				processRequest.setProcessId("customer_onboarding_Process_0i45xui");
				//processRequest.setProcessInstanceKey("");
				processRequest.setVariables(Map.of("name",inbody.getName(),"existingAccountHolder",inbody.isExistingAccountHolder(),"accounttype","karma"));
				processRequest.setProcessVersion(HIGHEST);
				exchange.getIn().setBody(processRequest);
			}
		})
		.to("camunda:startProcess")
		.process(exchange -> {
	        ProcessResponse body = exchange.getIn().getBody(ProcessResponse.class);
	        LOG.info("Customer Onboarding process response body"+body.toString());
	        if (body != null) {
	            boolean success = body.isSuccess();
	            long processInstanceKey = body.getProcessInstanceKey();
	            exchange.getIn().setHeader("success", success);
	            exchange.getIn().setHeader("processInstanceKey", processInstanceKey);
	        }
	    })
		.log(LoggingLevel.INFO, "Customer Onboarding process successfully started with processInstanceKey: ${header.processInstanceKey} Camunda response : ${body}")
		.end();
		
	}

	
	
}
