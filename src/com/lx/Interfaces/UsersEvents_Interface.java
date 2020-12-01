package com.lx.Interfaces;

import java.rmi.Remote;

import org.json.JSONArray;

public interface UsersEvents_Interface extends Remote{
	public String Login(String username,String password) throws Exception;
	
	public String getUsers() throws Exception;
}
