package com.lx.Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TheServer {

	public static void main(String[] args) throws Exception {
		System.out.println("Welcome to the GrandLuck university");

		Registry reg = LocateRegistry.createRegistry(1099);
		
		UserEventsC user = new UserEventsC();
		
		FeedBakc feed = new FeedBakc();

		reg.rebind("UserEvents", user);
		reg.rebind("Feedbacks", feed);
		
		
		
		//http access
//		try {
//
//			String url = "http://localhost:3000/api/getUsersList";
//			URL obj = new URL(url);
//
//			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//			int responsStatus = con.getResponseCode();
//			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//			String inputLine;
//			StringBuffer response = new StringBuffer();
//
//			while ((inputLine = in.readLine()) != null) {
//				response.append(inputLine);
//			}
//
//			in.close();
//
//			System.out.println(response.toString());
//			
////			JSONObject reqobj = new JSONObject(response.toString());
//			
////			JSONArray arr = reqobj.getJSONArray("posts");
////	        for (int i = 0; i < arr.length(); i++) {
////	            String post_id = arr.getJSONObject(i).getString("userID");
////	            System.out.println(post_id);
////	        }
//
//			JSONArray jArray = (JSONArray) new JSONTokener(response.toString()).nextValue();
//			// once you get the array, you may check items like
//			
//			System.out.println(jArray.length());
//			
//			for (int i = 1; i < jArray.length(); i++) {
//				JSONObject jObject = jArray.getJSONObject(i);
//				System.out.println(jObject.getString("userID") +" "+jObject.getString("uName")+ " "+jObject.getString("role"));
//			}
//			
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		
		
		
		
		
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
