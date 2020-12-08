package com.lx.Beans;

public class ClientFeedbackBean {
	private int _id;
	private String QA, date,_clientId;
	
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String get_clientId() {
		return _clientId;
	}
	public void set_clientId(String _clientId) {
		this._clientId = _clientId;
	}
	public String getQA() {
		return QA;
	}
	public void setQA(String qA) {
		QA = qA;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
