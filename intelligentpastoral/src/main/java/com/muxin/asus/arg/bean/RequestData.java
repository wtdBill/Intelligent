package com.muxin.asus.arg.bean;

public class RequestData {
	private int curpage = 1;
	private int pagesize = 10;
	private String gatewayid;
	public String getGatewayid() {
		return gatewayid;
	}
	public void setGatewayid(String gatewayid) {
		this.gatewayid = gatewayid;
	}
	public RequestData() {
	}
	public RequestData(int curpage, int pagesize) {
		this.curpage = curpage;
		this.pagesize = pagesize;
	}
	public int getCurpage() {
		return curpage;
	}
	public void setCurpage(int curpage) {
		this.curpage = curpage;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	
}
