package com.lx.Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONObject;


public class TheServer {

	public static void main(String[] args) throws Exception {
		System.out.println("Welcome to the GrandLuck university"); 
		
		Registry reg = LocateRegistry.createRegistry(1099);
		
		UserEventsC user = new UserEventsC();
		
		FeedBakc feed = new FeedBakc();

		reg.rebind("UserEvents", user);
		reg.rebind("Feedbacks", feed);
		
		
		
		
		
//		String out = feed.getclientFeedbackSummaryByQid(8);
//		System.out.println(out);
//		
////		JSONObject jsonObject = new JSONObject(out);
////		System.out.println(jsonObject.getString("questionsCount"));
//		
//		ObjectMapper mapper = new ObjectMapper();
//		Map<String, Object> map = mapper.readValue(out, new TypeReference<Map<String, Object>>() {
//		});
//
////		List mainMap2 = (List) map.get("questionsCount");
//		
//		//for (Object item : mainMap2) {
////		    System.out.println("itemResult" + item.toString());
//		//}
//		
////		int id = (int)((Map)mainMap2.get(0)).get("id");
//		System.out.println(map.get("questionsCount"));
	}

}


