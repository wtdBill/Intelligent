package com.muxin.asus.arg.bean;

public class LoginRequest {
	private String loginname;
	private String password;

	public LoginRequest() {
	}

	public LoginRequest(String loginname, String password) {
		this.loginname = loginname;
		this.password = password;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
