package com.muxin.asus.arg.bean;

import java.io.Serializable;

public class MonitorResponse implements Serializable{
	private static final long serialVersionUID = -4181271039484834565L;

	/**
	 * addtime : 2016-06-21 10:02:21
	 * adduser : 0
	 * appipaddress : moresignal.imwork.net:14706
	 * curpage : 0
	 * ipaddress : moresignal.imwork.net:12296
	 * monitorid : 2
	 * monitorname : 第二监控点
	 * pagesize : 0
	 * pagestart : 0
	 * password : jnnyj12345
	 * remarks :
	 * siteid : 111
	 * sitename : 六合区
	 * sortord :
	 * term :
	 * updatetime :
	 * updateuser : 0
	 * username : admin
	 */

	private String appipaddress;
	private String ipaddress;
	private int monitorid;
	private String monitorname;
	private String password;
	private String siteid;
	private String sitename;
	private String username;

	public String getAppipaddress() {
		return appipaddress;
	}

	public void setAppipaddress(String appipaddress) {
		this.appipaddress = appipaddress;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public int getMonitorid() {
		return monitorid;
	}

	public void setMonitorid(int monitorid) {
		this.monitorid = monitorid;
	}

	public String getMonitorname() {
		return monitorname;
	}

	public void setMonitorname(String monitorname) {
		this.monitorname = monitorname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}

	public String getSitename() {
		return sitename;
	}

	public void setSitename(String sitename) {
		this.sitename = sitename;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
