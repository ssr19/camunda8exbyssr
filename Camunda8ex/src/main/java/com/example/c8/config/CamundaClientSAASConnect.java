package com.example.c8.config;

import io.camunda.client.CamundaClient;

public class CamundaClientSAASConnect {

	private static final String CAMUNDA_CLUSTER_ID = "b6d93786-4435-46c3-9110-d7094bd66082";
	private static final String CAMUNDA_CLIENT_ID = "M6uLiHQBOp.u515eNtFtJL4gaNsiCMxk";
	private static final String CAMUNDA_CLIENT_SECRET = "j39wXksuF.Kd..h2q1uclKhmcE2mMwSO20o0U.nwSk5mcEWZWi~hOrzZy.MAxijz";
	private static final String CAMUNDA_CLUSTER_REGION = "cle-1";
	public static void main(String[] args) {
		// TODO Auto-generated method stub


		    try (CamundaClient client = CamundaClient.newCloudClientBuilder()
		            .withClusterId(CAMUNDA_CLUSTER_ID)
		            .withClientId(CAMUNDA_CLIENT_ID)
		            .withClientSecret(CAMUNDA_CLIENT_SECRET)
		            .withRegion(CAMUNDA_CLUSTER_REGION)
		            .build()) {

		        // Test the connection
		        client.newTopologyRequest().send().join();
		        System.out.println("Connected to Camunda 8!");
		    }
		}
	}

