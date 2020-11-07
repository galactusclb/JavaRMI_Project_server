package com.lx.Interfaces;

import java.rmi.Remote;

public interface UsersEvents_Interface extends Remote{
	public String Login(String username,String password) throws Exception;
}
