package com.lx.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lx.Beans.FeedBackBean;
import com.lx.DbConnection.ConnectionProvider;

public class FeedBackDao {
	private Connection conn;

	public FeedBackDao() {
		conn = ConnectionProvider.getConnection();
	}

	public Boolean addFeedBack(String type, String question, String answers, int order) {
		PreparedStatement ps = null;

		try {
			String sql = "INSERT INTO feedback (type,q,answers,qOrder,status)VALUES(?,?,?,?,?)";
			ps = conn.prepareStatement(sql);

			ps.setString(1, type);
			ps.setString(2, question);
			ps.setString(3, answers);
			ps.setInt(4, order);
			ps.setString(5, "1");

			int i = ps.executeUpdate();

			if (i > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean addClientFeedBack(String uid,String QA) {
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH.mm.ss").format(new Date());
		PreparedStatement ps = null;

		try {
			String sql = "INSERT INTO clientfeedback (_clientId,QA,date)VALUES(?,?,?)";
			ps = conn.prepareStatement(sql);

			ps.setString(1, uid);
			ps.setString(2, QA);
			ps.setString(3, timeStamp);

			int i = ps.executeUpdate();

			if (i > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<FeedBackBean> getAllFeedbacks(Boolean status ) {
		List<FeedBackBean> QaA = new ArrayList<FeedBackBean>();
		PreparedStatement ps = null;
		try {
			String sql = null;
			if(status == true) {
				sql = "SELECT * FROM feedback WHERE status = 1";				
			}else {
				sql = "SELECT * FROM feedback";
			}
			ps = conn.prepareStatement(sql);

			ResultSet result = ps.executeQuery();

//			String[] answers = new String[5];

			while (result.next()) {
				FeedBackBean fb = new FeedBackBean();

				fb.set_id(result.getInt("_id"));
				fb.setQuestion(result.getString("q"));
				fb.setOrder(result.getInt("qOrder"));
				fb.setType(result.getString("type"));

//				System.out.println(fb.getQuestion());

//				answers[0] = "yes";
//				answers[1] = "No";
//				answers[2] = "none";
				fb.setAnswers(result.getString("answers"));

				QaA.add(fb);
			}

			result.close();
			return QaA;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
}
