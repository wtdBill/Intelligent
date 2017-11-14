package com.muxin.asus.arg.bean;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/21
 * Description:
 */
public class SensorValueBean {
    private int channelno;
    private String gatewayid;
    private String gatewayname;
    private String transducername;
    private float value;
    private String locale;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public float getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = (float)value;
    }

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

    public String getTransducername() {
        return transducername;
    }

    public void setTransducername(String transducername) {
        this.transducername = transducername;
    }
}
