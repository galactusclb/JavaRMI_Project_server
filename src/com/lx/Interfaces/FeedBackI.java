package com.lx.Interfaces;

import java.rmi.Remote;
import java.util.List;

import com.lx.Beans.FeedBackBean;

public interface FeedBackI extends Remote{
	public List<FeedBackBean> getFeedBack() throws Exception;
	
//	public String getAllFeedBack() throws Exception;
	
	public String getAllFeedBack(Boolean status) throws Exception;
	
	public List<Integer> getFeedbacksOrderNumbers() throws Exception;
	
	public String getFeedBackByQid(int qid) throws Exception;
	
	public String addFeedBack(String type,String question,String answers,int order,boolean status) throws Exception;
	
	public String editFeedBack(int questionId,String type,String question,String answers,int order,boolean status) throws Exception;
	
	public Boolean deleteFeedBackByQid(int qid) throws Exception;
	
	
//	summary
	public String clientFeedBack(String uid,String output) throws Exception;

	public String getclientFeedbackSummaryByQid(int qid) throws Exception;
	
	public String getclientFeedbackSummaryByClientId(String uid) throws Exception;
	
}
