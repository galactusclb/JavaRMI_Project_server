package com.lx.Server;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.lx.Beans.UserBean;
import com.lx.Dao.UserDao;
import com.lx.Interfaces.UsersEvents_Interface;

public class UserEventsC extends UnicastRemoteObject implements UsersEvents_Interface{
	
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
		System.out.println("user :"+username+" password : "+password);
		Boolean resulte = false;
		String role = null ;
		mapper = new ObjectMapper();
		UserBean userbean = new UserBean();
		
		userbean.setUname(username);
		userbean.setPassword(password);
		
		userbean = dao.loginCheck(userbean);
		
		System.out.println("ub : " +userbean.getRole());
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
		}else {
			return null;
		}
	}

}
