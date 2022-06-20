package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class BbsComment extends Domain {

	private static final long serialVersionUID = -7750883746025079008L;

	private Integer bbsNo;
	private Integer commentNo;
	private String comment;
	private String status;
	private String statusName;
	private String cuserName;

	public Integer getBbsNo() {
		return bbsNo;
	}
	public void setBbsNo(Integer bbsNo) {
		this.bbsNo = bbsNo;
	}
	public Integer getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(Integer commentNo) {
		this.commentNo = commentNo;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCuserName() {
		return cuserName;
	}
	public void setCuserName(String cuserName) {
		this.cuserName = cuserName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}


}
