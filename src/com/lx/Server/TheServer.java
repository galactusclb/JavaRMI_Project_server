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
		
		
	} 

}
 