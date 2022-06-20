package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Notice extends Domain {

	private static final long serialVersionUID = 4980544794256050179L;
	private Integer noticeNo;
	private String cate;
	private String cateName;
	private String title;
	private String content;
	private String fixYn;
	private String commentYn;
	private String status;
	private String statusName;

	public Integer getNoticeNo() {
		return noticeNo;
	}
	public void setNoticeNo(Integer noticeNo) {
		this.noticeNo = noticeNo;
	}
	public String getCate() {
		return cate;
	}
	public void setCate(String cate) {
		this.cate = cate;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
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
	public String getFixYn() {
		return fixYn;
	}
	public void setFixYn(String fixYn) {
		this.fixYn = fixYn;
	}
	public String getCommentYn() {
		return commentYn;
	}
	public void setCommentYn(String commentYn) {
		this.commentYn = commentYn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}



}
