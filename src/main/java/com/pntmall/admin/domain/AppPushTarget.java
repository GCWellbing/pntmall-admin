package com.pntmall.admin.domain;

import java.util.Date;

import com.pntmall.common.type.SearchDomain;

public class AppPushTarget extends SearchDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 240662187169192441L;

	private Integer tno;
	private Integer pno;
	private String deviceId;
	private Integer memNo;
	private String appToken;
	private Date sdate;
	private Date adate;
	private Date odate;
	private String appOs;
	private String pushYn;
	private String memId;
	private String memName;
	
	public Integer getTno() {
		return tno;
	}
	public void setTno(Integer tno) {
		this.tno = tno;
	}
	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public String getAppToken() {
		return appToken;
	}
	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public Date getAdate() {
		return adate;
	}
	public void setAdate(Date adate) {
		this.adate = adate;
	}
	public Date getOdate() {
		return odate;
	}
	public void setOdate(Date odate) {
		this.odate = odate;
	}
	public String getAppOs() {
		return appOs;
	}
	public void setAppOs(String appOs) {
		this.appOs = appOs;
	}
	public String getPushYn() {
		return pushYn;
	}
	public void setPushYn(String pushYn) {
		this.pushYn = pushYn;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	
}
