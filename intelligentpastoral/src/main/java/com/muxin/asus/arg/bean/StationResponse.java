package com.muxin.asus.arg.bean;

import java.io.Serializable;

/**
 * Author:   Lianwei Bu
 * Date:     2016/6/24
 * Description:
 */
public class StationResponse implements Serializable {

    /**
     * addtime : 2016-06-20 10:00:05
     * adduser : 0
     * curpage : 0
     * gatewayid : 1002
     * gatewayname : 东阳社区
     * ip : 
     * locale : 江宁区
     * pagesize : 0
     * pagestart : 0
     * port : 
     * principal : 孔宁
     * remarks : 东阳社区
     * siteid : 6
     * sitename : 江宁区
     * sortord : 
     * term : 
     * updatetime : 
     * updateuser : 0
     */

    private int curpage;
    private String gatewayid;
    private String gatewayname;
    private String ip;
    private String locale;
    private String principal;
    private String remarks;
    private int siteid;
    private String sitename;

    public int getCurpage() {
        return curpage;
    }

    public void setCurpage(int curpage) {
        this.curpage = curpage;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }
}
