package com.pntmall.admin.domain;

import java.util.Date;

import com.pntmall.common.type.Domain;

public class Member extends Domain {

	private static final long serialVersionUID = 4727150844664880022L;

	private Integer memNo;
	private String memId;
	private String passwd;
	private String name;
	private String birthday;
	private String gender;
	private String mtel1;
	private String mtel2;
	private String email;
	private String smsYn;
	private String emailYn;
	private Integer height;
	private Integer weight;
	private Integer gradeNoOld;
	private Integer gradeNo;
	private String gradeName;
	private String clinicYn;
	private String status;
	private String statusName;
	private Integer loginCnt;
	private Date loginDate;
	private String sleepYn;
	private Date sleepDate;
	private String memo;
	private String curPoint;
	private String joinType;
	private String clinicId;
	private String clinicName;
	private String secedeRsn;
	private String secedeRsnName;
	private String secedeMemo;
	private String joinDevice;
	private Integer orderCnt;
	private Integer orderPayAmt;
	private Integer csCnt;

	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSmsYn() {
		return smsYn;
	}
	public void setSmsYn(String smsYn) {
		this.smsYn = smsYn;
	}
	public String getEmailYn() {
		return emailYn;
	}
	public void setEmailYn(String emailYn) {
		this.emailYn = emailYn;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getGradeNo() {
		return gradeNo;
	}
	public void setGradeNo(Integer gradeNo) {
		this.gradeNo = gradeNo;
	}
	public String getClinicYn() {
		return clinicYn;
	}
	public void setClinicYn(String clinicYn) {
		this.clinicYn = clinicYn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getLoginCnt() {
		return loginCnt;
	}
	public void setLoginCnt(Integer loginCnt) {
		this.loginCnt = loginCnt;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public String getSleepYn() {
		return sleepYn;
	}
	public void setSleepYn(String sleepYn) {
		this.sleepYn = sleepYn;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getCurPoint() {
		return curPoint;
	}
	public void setCurPoint(String curPoint) {
		this.curPoint = curPoint;
	}
	public String getJoinType() {
		return joinType;
	}
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	public String getClinicId() {
		return clinicId;
	}
	public void setClinicId(String clinicId) {
		this.clinicId = clinicId;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getMtel1() {
		return mtel1;
	}
	public void setMtel1(String mtel1) {
		this.mtel1 = mtel1;
	}
	public String getMtel2() {
		return mtel2;
	}
	public void setMtel2(String mtel2) {
		this.mtel2 = mtel2;
	}
	public String getSecedeRsn() {
		return secedeRsn;
	}
	public void setSecedeRsn(String secedeRsn) {
		this.secedeRsn = secedeRsn;
	}
	public String getSecedeMemo() {
		return secedeMemo;
	}
	public void setSecedeMemo(String secedeMemo) {
		this.secedeMemo = secedeMemo;
	}
	public String getSecedeRsnName() {
		return secedeRsnName;
	}
	public void setSecedeRsnName(String secedeRsnName) {
		this.secedeRsnName = secedeRsnName;
	}
	public String getJoinDevice() {
		return joinDevice;
	}
	public void setJoinDevice(String joinDevice) {
		this.joinDevice = joinDevice;
	}
	public Date getSleepDate() {
		return sleepDate;
	}
	public void setSleepDate(Date sleepDate) {
		this.sleepDate = sleepDate;
	}
	public Integer getGradeNoOld() {
		return gradeNoOld;
	}
	public void setGradeNoOld(Integer gradeNoOld) {
		this.gradeNoOld = gradeNoOld;
	}
	public Integer getOrderCnt() {
		return orderCnt;
	}
	public void setOrderCnt(Integer orderCnt) {
		this.orderCnt = orderCnt;
	}
	public Integer getOrderPayAmt() {
		return orderPayAmt;
	}
	public void setOrderPayAmt(Integer orderPayAmt) {
		this.orderPayAmt = orderPayAmt;
	}
	public Integer getCsCnt() {
		return csCnt;
	}
	public void setCsCnt(Integer csCnt) {
		this.csCnt = csCnt;
	}

}
