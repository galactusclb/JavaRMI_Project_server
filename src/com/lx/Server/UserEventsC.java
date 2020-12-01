package com.lx.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.lx.Beans.UserBean;
import com.lx.Dao.UserDao;
import com.lx.Interfaces.UsersEvents_Interface;

public class UserEventsC extends UnicastRemoteObject implements UsersEvents_Interface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 193555869281889602L;

	private ObjectMapper mapper;
	private UserBean[] user;
	private UserDao dao;

	protected UserEventsC() throws RemoteException {
		super();
		dao = new UserDao();
	}

	@Override
	public String Login(String username, String password) throws Exception {
		System.out.println("user :" + username + " password : " + password);
		Boolean resulte = false;
		String role = null;
		mapper = new ObjectMapper();
		UserBean userbean = new UserBean();

		userbean.setUname(username);
		userbean.setPassword(password);

		userbean = dao.loginCheck(userbean);

		System.out.println("ub : " + userbean.getRole());
		role = userbean.getRole();

//		try {
//			user = mapper.readValue(new File("users.json"), UserBean[].class);
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

//		for (int i = 0; i < user.length; i++) {
//			if (username.equals(user[i].getUname()) && password.equals(user[i].getPassword())) {
//				resulte = true;
//				if (user[i].getRole().equals("admin")) {
//					role = "admin";
//				}else {
//					role="user";
//				}
//			}
//		}

		if (role != null && !role.trim().isEmpty()) {
			return role;
		} else {
			return null;
		}
	}

	
	@Override
	public String getUsers() throws Exception {
		System.out.println("fffff");
//		JSONObject mainobj = new JSONObject();
		JSONArray jArray  = null;
		
		try {

			String url = "http://localhost:3000/api/getUsersList";
			URL obj = new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			int responsStatus = con.getResponseCode();
			System.out.println(responsStatus);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();

//			System.out.println(response.toString());

			jArray = (JSONArray) new JSONTokener(response.toString()).nextValue();
			// once you get the array, you may check items like

			for (int i = 1; i < jArray.length(); i++) {
				JSONObject jObject = jArray.getJSONObject(i);
//				System.out.println(jObject.getString("userID") + " " + jObject.getString("uName") + " "
//						+ jObject.getString("role"));
			}
			
			return jArray.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null ;
		}

	}

}
