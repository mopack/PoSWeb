/*
 * Copyright XtremeApp Corporation. All Rights Reserved.
 */
package com.example.chaincode.invocation;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.client.CAClient;
import com.example.client.ChannelClient;
import com.example.client.FabricClient;
import com.example.config.Config;
import com.example.user.UserContext;
import com.example.util.Util;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.ChaincodeResponse.Status;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;

/**
 * Invoke ChangeConsumer Chaincode.
 * 
 * @author Mopack
 * @date 2020-07-14
 */
@RestController
public class ChangeConsumer {

	private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);
	private static final String EXPECTED_EVENT_NAME = "event";

	@RequestMapping("/ChangeConsumer")
	public String InvokeChangeConsumer(
			@RequestParam(value="id", required=true) String id,
			@RequestParam(value="name", required=true) String name) {
		JSONObject response = new JSONObject();
		
		try {
            Util.cleanUp();
			String caUrl = Config.CA_ORG1_URL;
			CAClient caClient = new CAClient(caUrl, null);
			// Enroll Admin to Org1MSP
			UserContext adminUserContext = new UserContext();
			adminUserContext.setName(Config.ADMIN);
			adminUserContext.setAffiliation(Config.ORG1);
			adminUserContext.setMspId(Config.ORG1_MSP);
			caClient.setAdminUserContext(adminUserContext);
			adminUserContext = caClient.enrollAdminUser(Config.ADMIN, Config.ADMIN_PASSWORD);
			
			FabricClient fabClient = new FabricClient(adminUserContext);
			
			ChannelClient channelClient = fabClient.createChannelClient(Config.CHANNEL_NAME);
			Channel channel = channelClient.getChannel();
			Peer peer = fabClient.getInstance().newPeer(Config.ORG1_PEER_0, Config.ORG1_PEER_0_URL);
			EventHub eventHub = fabClient.getInstance().newEventHub(Config.EVENT_HUP_1_NAME, Config.EVENT_HUP_1_URL);
			Orderer orderer = fabClient.getInstance().newOrderer(Config.ORDERER_NAME, Config.ORDERER_URL);
			channel.addPeer(peer);
			channel.addEventHub(eventHub);
			channel.addOrderer(orderer);
			channel.initialize();

			TransactionProposalRequest request = fabClient.getInstance().newTransactionProposalRequest();
			ChaincodeID ccid = ChaincodeID.newBuilder().setName(Config.CHAINCODE_1_NAME).build();
			request.setChaincodeID(ccid);
			request.setFcn(Config.CHAINCODE_1_CHANGE_CONSUMER);
			String[] arguments = { Config.CHAINCODE_1_INDEX_PREFIX + id, name, Util.DateToString(new Date()) };
			request.setArgs(arguments);
			request.setProposalWaitTime(1000);

			Map<String, byte[]> tm2 = new HashMap<>();
			tm2.put("HyperLedgerFabric", "TransactionProposalRequest:JavaSDK".getBytes(UTF_8)); 																								
			tm2.put("method", "TransactionProposalRequest".getBytes(UTF_8)); 
			tm2.put("result", ":)".getBytes(UTF_8));
			tm2.put(EXPECTED_EVENT_NAME, EXPECTED_EVENT_DATA); 
			request.setTransientMap(tm2);
			Collection<ProposalResponse> responses = channelClient.sendTransactionProposal(request);
			
			for (ProposalResponse res: responses) {
				Status status = res.getStatus();
				response.put("status", status);
				Logger.getLogger(ChangeConsumer.class.getName()).log(Level.INFO, "Invoked " + Config.CHAINCODE_1_CHANGE_CONSUMER + " on "+Config.CHAINCODE_1_NAME + ". Status - " + status);
			}
		} catch (Exception e) {
			/**
			 * If a shim.Error occurs upon the chaincode, the message will return as ErrorMessage, 
			 * and the ErrorType should be InvalidArgumentException.
			 */
			System.out.println("ErrorMessage: " + e.getMessage());
			System.out.println("ErrorType: " + e.getClass().getCanonicalName());
			e.printStackTrace();
			
			response.put("status", e.getMessage());
		}
		return response.toString();
	}

}
