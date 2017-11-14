package com.muxin.asus.arg.bean;

import java.io.Serializable;

public class SensorBean implements Serializable {
	private static final long serialVersionUID = -5240094226287236072L;


	/**
	 * addtime : 2016-06-01 20:51:52
	 * adduser : 0
	 * channelno : 4
	 * curpage : 0
	 * gatewayid : 1001
	 * gatewayname : 湖熟
	 * pagesize : 0
	 * pagestart : 0
	 * remarks :
	 * sortord :
	 * term :
	 * transducerid : 26
	 * transducername : 光照强度
	 * transducertype : 光照强度
	 * updatetime : 2016-06-03 11:14:39
	 * updateuser : 0
	 */

	private int channelno;
	private String gatewayid;
	private String gatewayname;
	private int transducerid;
	private String transducername;
	private String transducertype;

	public int getChannelno() {
		return channelno;
	}

	public void setChannelno(int channelno) {
		this.channelno = channelno;
	}

	public String getGatewayid() {
		return gatewayid;
	}

	public void setGatewayid(String gatewayid) {
		this.gatewayid = gatewayid;
	}

	public String getGatewayname() {
		return gatewayname;
	}

	public void setGatewayname(String gatewayname) {
		this.gatewayname = gatewayname;
	}

	public int getTransducerid() {
		return transducerid;
	}

	public void setTransducerid(int transducerid) {
		this.transducerid = transducerid;
	}

	public String getTransducername() {
		return transducername;
	}

	public void setTransducername(String transducername) {
		this.transducername = transducername;
	}

	public String getTransducertype() {
		return transducertype;
	}

	public void setTransducertype(String transducertype) {
		this.transducertype = transducertype;
	}
}
