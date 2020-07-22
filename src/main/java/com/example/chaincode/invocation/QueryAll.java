/*
 * Copyright XtremeApp Corporation. All Rights Reserved.
 */
package com.example.chaincode.invocation;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.client.CAClient;
import com.example.client.ChannelClient;
import com.example.client.FabricClient;
import com.example.config.Config;
import com.example.user.UserContext;
import com.example.util.Util;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;

/**
 * Invoke QueryAll Chaincode.
 * 
 * @author Mopack
 * @date 2020-07-14
 */
public class QueryAll {

	private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);
	private static final String EXPECTED_EVENT_NAME = "event";

	public static void main(String args[]) {
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

			Logger.getLogger(QueryAll.class.getName()).log(Level.INFO, "Querying for all products ...");
			Collection<ProposalResponse>  responsesQuery = channelClient.queryByChainCode(Config.CHAINCODE_1_NAME, Config.CHAINCODE_1_QUERY_ALL, null);
			for (ProposalResponse pres : responsesQuery) {
				String stringResponse = new String(pres.getChaincodeActionResponsePayload());
				Logger.getLogger(QueryAll.class.getName()).log(Level.INFO, stringResponse);
			}
			
		} catch (Exception e) {
			/**
			 * If a shim.Error occurs upon the chaincode, the message will return as ErrorMessage, 
			 * and the ErrorType should be InvalidArgumentException.
			 */
			System.out.println("ErrorMessage: " + e.getMessage());
			System.out.println("ErrorType: " + e.getClass().getCanonicalName());
			e.printStackTrace();
		}
	}

}
