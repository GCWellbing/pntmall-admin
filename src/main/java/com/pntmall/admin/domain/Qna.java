package com.pntmall.admin.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.pntmall.common.type.Domain;

public class Qna extends Domain {

	private static final long serialVersionUID = -493484009428336896L;
	private Integer qnaNo;
	private String cate;
	private String cateName;
	private Integer pno;
	private String pname;
	private Integer clinicMemNo;
	private String clinicName;
	private String title;
	private String question;
	private String answer;
	private String status;
	private String oldStatus;
	private String statusName;
	private Integer quser;
	private String quserId;
	private String quserName;
	private Date qdate;


	public Integer getQnaNo() {
		return qnaNo;
	}
	public void setQnaNo(Integer qnaNo) {
		this.qnaNo = qnaNo;
	}
	public String getCate() {
		return cate;
	}
	public void setCate(String cate) {
		this.cate = cate;
	}
	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public Integer getClinicMemNo() {
		return clinicMemNo;
	}
	public void setClinicMemNo(Integer clinicMemNo) {
		this.clinicMemNo = clinicMemNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public Integer getQuser() {
		return quser;
	}
	public void setQuser(Integer quser) {
		this.quser = quser;
	}
	public Date getQdate() {
		return qdate;
	}
	public void setQdate(Date qdate) {
		this.qdate = qdate;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getQuserName() {
		return quserName;
	}
	public void setQuserName(String quserName) {
		this.quserName = quserName;
	}
	public String getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}
	public String getQuserId() {
		return quserId;
	}
	public void setQuserId(String quserId) {
		this.quserId = quserId;
	}

}
