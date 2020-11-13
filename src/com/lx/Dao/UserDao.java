package com.lx.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.lx.Beans.UserBean;
import com.lx.DbConnection.ConnectionProvider;

public class UserDao {
	private Connection conn;

	public UserDao() {
		conn = ConnectionProvider.getConnection();
	}
	
	public UserBean loginCheck(UserBean ub) {
		String userName = ub.getUname();
		String password = ub.getPassword();
		
		try {
			String sql="SELECT * FROM users WHERE uname='"+userName+"' AND password='"+password+"'";
			PreparedStatement ps= conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			boolean more=rs.next();
			
			if(!more) {
				ub.setValid(false);
				System.out.println("xss");
			}else {
				String role= rs.getString("role");
				
				ub.setUname(rs.getString("uname"));
				ub.setRole(role);
				
				System.out.println("role is "+ role);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ub;
	}
}
