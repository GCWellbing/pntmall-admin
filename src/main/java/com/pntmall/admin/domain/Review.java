package com.pntmall.admin.domain;

import java.util.Date;

import com.pntmall.common.type.Domain;

public class Review extends Domain {

	private static final long serialVersionUID = 6570517617724815561L;

	private Integer reviewNo;
	private String type1;
	private String type1Name;
	private String type2;
	private String type2Name;
	private Integer pno;
	private String pname;
	private Integer star;
	private String title;
	private String content;
	private String bestYn;
	private String bestPointYn;
	private Integer bestSeq;
	private String mainYn;
	private String reviewYn;
	private Integer reviewSeq;
	private String dispYn;
	private String status;
	private String statusName;
	private String comment;
	private Integer cmtCuser;
	private String cmtCuserName;
	private String cmtCuserId;
	private Date cmtCdate;
	private Integer cmtUuser;
	private String cmtUuserName;
	private String cmtUuserID;
	private Date cmtUdate;

	public Integer getReviewNo() {
		return reviewNo;
	}
	public void setReviewNo(Integer reviewNo) {
		this.reviewNo = reviewNo;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getType1Name() {
		return type1Name;
	}
	public void setType1Name(String type1Name) {
		this.type1Name = type1Name;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public String getType2Name() {
		return type2Name;
	}
	public void setType2Name(String type2Name) {
		this.type2Name = type2Name;
	}
	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public Integer getStar() {
		return star;
	}
	public void setStar(Integer star) {
		this.star = star;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getBestYn() {
		return bestYn;
	}
	public void setBestYn(String bestYn) {
		this.bestYn = bestYn;
	}
	public Integer getBestSeq() {
		return bestSeq;
	}
	public void setBestSeq(Integer bestSeq) {
		this.bestSeq = bestSeq;
	}
	public String getMainYn() {
		return mainYn;
	}
	public void setMainYn(String mainYn) {
		this.mainYn = mainYn;
	}
	public String getReviewYn() {
		return reviewYn;
	}
	public void setReviewYn(String reviewYn) {
		this.reviewYn = reviewYn;
	}
	public String getDispYn() {
		return dispYn;
	}
	public void setDispYn(String dispYn) {
		this.dispYn = dispYn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getCmtCuser() {
		return cmtCuser;
	}
	public void setCmtCuser(Integer cmtCuser) {
		this.cmtCuser = cmtCuser;
	}
	public Date getCmtCdate() {
		return cmtCdate;
	}
	public void setCmtCdate(Date cmtCdate) {
		this.cmtCdate = cmtCdate;
	}
	public Integer getCmtUuser() {
		return cmtUuser;
	}
	public void setCmtUuser(Integer cmtUuser) {
		this.cmtUuser = cmtUuser;
	}
	public Date getCmtUdate() {
		return cmtUdate;
	}
	public void setCmtUdate(Date cmtUdate) {
		this.cmtUdate = cmtUdate;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Integer getReviewSeq() {
		return reviewSeq;
	}
	public void setReviewSeq(Integer reviewSeq) {
		this.reviewSeq = reviewSeq;
	}
	public String getBestPointYn() {
		return bestPointYn;
	}
	public void setBestPointYn(String bestPointYn) {
		this.bestPointYn = bestPointYn;
	}
	public String getCmtCuserName() {
		return cmtCuserName;
	}
	public void setCmtCuserName(String cmtCuserName) {
		this.cmtCuserName = cmtCuserName;
	}
	public String getCmtCuserId() {
		return cmtCuserId;
	}
	public void setCmtCuserId(String cmtCuserId) {
		this.cmtCuserId = cmtCuserId;
	}
	public String getCmtUuserName() {
		return cmtUuserName;
	}
	public void setCmtUuserName(String cmtUuserName) {
		this.cmtUuserName = cmtUuserName;
	}
	public String getCmtUuserID() {
		return cmtUuserID;
	}
	public void setCmtUuserID(String cmtUuserID) {
		this.cmtUuserID = cmtUuserID;
	}


}
