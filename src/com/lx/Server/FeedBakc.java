package com.lx.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.lx.Beans.AnsweredQABean;
import com.lx.Beans.ClientFeedbackBean;
import com.lx.Beans.FeedBackBean;
import com.lx.Dao.FeedBackDao;
import com.lx.Interfaces.FeedBackI;

public class FeedBakc<K> extends UnicastRemoteObject implements FeedBackI {

	boolean b = 5 < 0;
	
	Character g ;
	
	byte x = -12;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1885072386480071975L;
	private FeedBackDao dao;
	private ObjectMapper mapper = new ObjectMapper();

	
//	this is the class where all the feedbacks methods are implemented 
	protected FeedBakc() throws RemoteException {
		super();
		
		//get the database connection from connection provide class
		dao = new FeedBackDao();
	}

	@Override
	public List<FeedBackBean> getFeedBack() throws RemoteException {
		List<FeedBackBean> modal1 = null;
		return modal1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String addFeedBack(String type, String question, String answers, int order,boolean status) throws Exception {
		boolean res = dao.addFeedBack(type, question, answers, order,status);

		JSONObject obj = new JSONObject();
		String response = null;

		if (res) {
			obj.put("status", new Integer(200));
			obj.put("message", "Insert successfull");

			response = obj.toString();
			System.out.println(response);
			return response;
		} else {
			obj.put("status", new Integer(400));
			obj.put("message", "error");

			response = obj.toString();
			return response;
		}
	}
	
	
	//get all feedbacks
	@SuppressWarnings("unchecked")
	@Override
	public String getAllFeedBack() throws Exception {
		System.out.println("Static Polymorphism.");
		List<FeedBackBean> modal = null;

		JSONArray objArray = new JSONArray();
		String response = null;

		modal = dao.getAllFeedbacks(false);
		for (FeedBackBean fb : modal) {
			JSONObject subObj = new JSONObject();
//			System.out.println("  type : " + fb.getType());
			subObj.put("_id", fb.get_id());
			subObj.put("type", fb.getType());
			subObj.put("question", fb.getQuestion());
			subObj.put("answers", fb.getAnswers());
			subObj.put("order", fb.getOrder());

			objArray.add(subObj);

		}
		response = objArray.toString();
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getAllFeedBack(Boolean status) throws Exception {
		List<FeedBackBean> modal = null;

		JSONArray objArray = new JSONArray();
		String response = null;

		modal = dao.getAllFeedbacks(status);
		for (FeedBackBean fb : modal) {
			JSONObject subObj = new JSONObject();
//			System.out.println("  type : " + fb.getType());
			subObj.put("_id", fb.get_id());
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
	public List<Integer> getFeedbacksOrderNumbers() throws Exception {
		List<Integer> modal = null;
		modal = dao.getFeedbacksOrderNumbers();

		return modal;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String editFeedBack(int qId, String type, String question, String answers, int order,boolean status) throws Exception {

		boolean res = dao.editFeedBack(qId, type, question, answers, order,status);

		JSONObject obj = new JSONObject();
		String response = null;

		if (res) {
			obj.put("status", new Integer(200));
			obj.put("message", "Update successfull");

			response = obj.toString();
			System.out.println(response);
			return response;
		} else {
			obj.put("status", new Integer(400));
			obj.put("message", "error");

			response = obj.toString();
			return response;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getFeedBackByQid(int qid) throws Exception {
		JSONArray objArray = new JSONArray();
		String response = null;

		FeedBackBean modal = dao.getFeedBackByQid(qid);

		JSONObject subObj = new JSONObject();

		subObj.put("_id", modal.get_id());
		subObj.put("type", modal.getType());
		subObj.put("question", modal.getQuestion());
		subObj.put("answers", modal.getAnswers());
		subObj.put("order", modal.getOrder());
		subObj.put("status", modal.isStatus());
		
		objArray.add(subObj);

		response = objArray.toString();
		return response;
	}

	@Override
	public Boolean deleteFeedBackByQid(int qid) throws Exception {

		boolean res = dao.deleteFeedBackByQid(qid);

		if (res) {
			return true;
		} else {
			return false;
		}

	}

//	clinetFeedBack section
	@SuppressWarnings("unchecked")
	@Override
	public String clientFeedBack(String uid, String output) throws Exception {
		JSONArray objArray = new JSONArray();
		String js = null;

		FeedBackBean[] model = mapper.readValue(output, FeedBackBean[].class);
		for (FeedBackBean fb : model) {
			System.out.println("gg : " + fb.get_id() + " ansg :" + fb.getSelectedAnswer());

			JSONObject subObj = new JSONObject();
//			System.out.println("  type : " + fb.getType());
			subObj.put("_id", fb.get_id());
			subObj.put("type", fb.getType());
			subObj.put("question", fb.getQuestion());
			subObj.put("selectedAnswer", fb.getSelectedAnswer());

			objArray.add(subObj);
		}

		js = objArray.toString();

		dao.addClientFeedBack(uid, output);

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getclientFeedbackSummaryByQid(int qid) throws Exception {
		JSONObject finalObj = new JSONObject();
		JSONArray objArray = new JSONArray();
		String output = null;//for return 
		
		
		FeedBackBean fb = dao.getFeedBackByQid(qid);
		String answers = fb.getAnswers();
		
		String[] answersArray = answers.split(",");
		
		List<AnsweredQABean> QA= dao.getclientFeedbackSummaryByQid(qid);
		
		//for splited answers
		
		finalObj.put("questionsCount", QA.size());
		
		
		for (String ans : answersArray) {
			int count =0;
			if (QA != null) {
				//for get count from selected answer in QA column in clientfeedback table
				for (AnsweredQABean answeredQABean : QA) {
//					System.out.println(answeredQABean.getSelectedAnswer());
					if (ans.equalsIgnoreCase(answeredQABean.getSelectedAnswer())) {
						count +=1;
					}
				}
			}
//			System.out.println(ans +" - " +count);
			
			JSONObject subObj = new JSONObject();
			subObj.put("answer", ans);
			subObj.put("count", count);
			
			objArray.add(subObj);
		}
		
		finalObj.put("answersCount", objArray);
		output = finalObj.toString();
		
		
		return output;
	}
	
	@SuppressWarnings("unchecked")
	public String getclientFeedbackSummaryByClientId(String uid) throws Exception {
		JSONObject finalObj = new JSONObject();
		String output = null;//for return 
		
		ClientFeedbackBean cfb = dao.getClientFeedBackByClientId(uid);
		
		//for splited answers
//		String qa = fb.getQA();
//		FeedBackBean[] model = mapper.readValue(qa, FeedBackBean[].class);
		
//		for (FeedBackBean feedBackBean : model) {
//			System.out.println(feedBackBean.getQuestion()+ " : "+feedBackBean.getSelectedAnswer());
//		}
		
		finalObj.put("_id", cfb.get_id());
		finalObj.put("_clientId", cfb.get_clientId());
		finalObj.put("QA", cfb.getQA());
		finalObj.put("date", cfb.getDate());
		
		
		output = finalObj.toString();
		
		
		return output;
	}

	
}
