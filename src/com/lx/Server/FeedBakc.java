package com.lx.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.lx.Beans.FeedBackBean;
import com.lx.Dao.FeedBackDao;
import com.lx.Interfaces.FeedBackI;

public class FeedBakc extends UnicastRemoteObject implements FeedBackI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1885072386480071975L;
	private FeedBackDao dao;
	private ObjectMapper mapper = new ObjectMapper();

	protected FeedBakc() throws RemoteException {
		super();
		dao = new FeedBackDao();
	}

	@Override
	public List<FeedBackBean> getFeedBack() throws RemoteException {
		List<FeedBackBean> modal1 = null;
		modal1 = dao.getAllFeedbacks();
		for (FeedBackBean fb : modal1) {
			System.out.println("  type : " + fb.getType());
		}
		return modal1;
	}

	@Override
	public String addFeedBack(String type, String question, String answers, int order) throws Exception {
		boolean res = dao.addFeedBack(type, question, answers, order);
		
		JSONObject obj = new JSONObject();
		String response=null;
		
		if (res) {
			obj.put("status", new Integer(400));
			obj.put("message", "Insert successfull");
			
			response = obj.toString();
			System.out.println(response);
			return response;
		}else {
			obj.put("status", new Integer(200));
			obj.put("message", "error");
			
			response = obj.toString();
			return response;
		}
	}

	@Override
	public String getAllFeedBack() throws Exception {
		List<FeedBackBean> modal = null;
		
		JSONArray objArray = new JSONArray();
		String response=null;
		
		modal = dao.getAllFeedbacks();
		for (FeedBackBean fb : modal) {
			JSONObject subObj = new JSONObject();
//			System.out.println("  type : " + fb.getType());
			subObj.put("_id",fb.get_id());
			subObj.put("type", fb.getType());
			subObj.put("question", fb.getQuestion());
			subObj.put("answers", fb.getAnswers());
			subObj.put("order", fb.getOrder());
			
			objArray.add(subObj);
			
		}		
		response = objArray.toString();
		return response;
	}

	@Override
	public String clientFeedBack(String uid,String output) throws Exception{
		JSONArray objArray = new JSONArray();
		String js=null;
		
		FeedBackBean[] model = mapper.readValue(output, FeedBackBean[].class);	
		for (FeedBackBean fb : model) {
			System.out.println("gg : "+fb.get_id()+" ansg :"+fb.getSelectedAnswer());
			
			JSONObject subObj = new JSONObject();
//			System.out.println("  type : " + fb.getType());
			subObj.put("_id",fb.get_id());
			subObj.put("type", fb.getType());
			subObj.put("question", fb.getQuestion());
			subObj.put("selectedAnswer", fb.getSelectedAnswer());
			
			objArray.add(subObj);
		}
		
		js = objArray.toString();
		
		dao.addClientFeedBack(uid, output);
		
		return null;
	}

}
