package com.lx.Server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.lx.Beans.ClientFeedbackBean;
import com.lx.Beans.UserBean;
import com.lx.Dao.FeedBackDao;
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
	private FeedBackDao daoF;

	protected UserEventsC() throws RemoteException {
		super();
		dao = new UserDao();
		daoF = new FeedBackDao();
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
		JSONArray jArray = null;

		try {

//			String url = "http://localhost:3000/api/getUsersList";
			String url = "https://hiruwaterbottlesystemapi.herokuapp.com/api/getUsersList";
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
			return null;
		}
	}

	@Override
	public String LoginUsersApi(String username, String password) throws Exception {
		try {

			String url = "http://localhost:3000/api/login";
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);

			JSONObject obj = new JSONObject();

			obj.put("name", username);
			obj.put("password", password);

			StringEntity postingString = new StringEntity(obj.toString());
			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");
			HttpResponse response = httpClient.execute(post);

			// read response
			InputStream ips = response.getEntity().getContent();
			BufferedReader buf = new BufferedReader(new InputStreamReader(ips, "UTF-8"));

			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
//	            throw new Exception(response.getStatusLine().getReasonPhrase());
				return null;
			}

			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = buf.readLine()) != null) {
				sb.append(s);

			}

			buf.close();
			ips.close();
			System.out.println(sb.toString());

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getUserDetails(String uname) throws Exception {

		try {

			String url = "http://localhost:3000/api/userdetails?uname=" + uname;
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
			
			ClientFeedbackBean cfb = daoF.getClientFeedBackByClientId(uname);

			System.out.println(cfb.getDate());
//			System.out.println(response.toString());

			JSONObject jsonobj = new JSONObject(response.toString());
			
			jsonobj.put("date", cfb.getDate());
//			
			System.out.println(jsonobj.toString());

			if (response.toString() != null) {
				return jsonobj.toString();
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String updatePassword(String uid, String password) throws Exception {
		
		try {

			String url = "http://localhost:3000/api/updatepassword";
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);

			JSONObject obj = new JSONObject();

			obj.put("uid", uid);
			obj.put("password", password);

			StringEntity postingString = new StringEntity(obj.toString());
			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");
			
			HttpResponse response = httpClient.execute(post);
			
			InputStream ips = response.getEntity().getContent();
			BufferedReader buf = new BufferedReader(new InputStreamReader(ips, "UTF-8"));

			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
//	            throw new Exception(response.getStatusLine().getReasonPhrase());
				return null;
			}

			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = buf.readLine()) != null) {
				sb.append(s);
			}

			buf.close();
			ips.close();
			System.out.println(sb.toString());

			return sb.toString();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
