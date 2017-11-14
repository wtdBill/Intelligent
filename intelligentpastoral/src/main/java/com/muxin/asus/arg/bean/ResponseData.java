package com.muxin.asus.arg.bean;


import java.util.List;

public class ResponseData {
	private String message;
	private int result;
	private int total;

	public ResponseData() {
	}

	public ResponseData(String message, int result, int total) {
		this.message = message;
		this.result = result;
		this.total = total;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
