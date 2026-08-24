package com.example.c8.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.c8.dto.LoanEligibilityReaquest;
import com.example.c8.dto.OrderRequest;

import io.camunda.client.CamundaClient;

//import io.camunda.zeebe.client.ZeebeClient;
import java.util.Map;

@Service
public class ProcessStarterService {

	private static final Logger LOG = LoggerFactory.getLogger(ProcessStarterService.class);
    private final CamundaClient camundaClient;

   
    public ProcessStarterService(CamundaClient camundaClient) {
        this.camundaClient = camundaClient;
    }

    public String startProcess(OrderRequest orderRequest) {
    	
    	LoanEligibilityReaquest loanEligibilityReaquest= orderRequest.getLoanEligibilityReaquest();
    	
        camundaClient.newCreateInstanceCommand()
        			 //.bpmnProcessId("account_order_Process_c8")
                     .bpmnProcessId(orderRequest.getProcessID())
                     .latestVersion()
                     .variables(Map.of("amount", orderRequest.getAmount(),"source",orderRequest.getSource(),"orderId","triggerMsgEventSuccess", "age", loanEligibilityReaquest.getAge(),
                    		    "isDefaulter", loanEligibilityReaquest.isDefaulter(),
                    		    "loanAmount", loanEligibilityReaquest.getLoanAmount(),
                    		    "monthlyIncome", loanEligibilityReaquest.getMonthlyIncome()))
                     .send()
                     .join();
        
        LOG.info("############# Process Started Successfully ###################");
        
        return "Process Started Successfully for OrderId :"+orderRequest.getSource();
    }

    
    public String deleteProcess(long processInstanceKey) {
    	
    	camundaClient
    	.newDeleteProcessInstanceCommand(processInstanceKey)
    	.send()
    	.join();
    	
    	return "Process Successfully deleted having processInstanceKey as :"+processInstanceKey;
    	
    }

}
