package com.pntmall.admin.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.pntmall.common.type.SearchDomain;

public class ReviewSearch extends SearchDomain {

	private static final long serialVersionUID = 9050169909937073509L;

	private Integer reviewNo;
	private Integer cate1;
	private Integer cate2;
	private Integer star;
	private Integer pno;
	private String pname;
	private String cuserName;
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date fromDate;
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date toDate;
	private String type1;
	private String type2;
	private String bestYn;
	private String mainYn;
	private String reviewYn;
	private String commentYn;
	private String title;
	private String status;

	public Integer getReviewNo() {
		return reviewNo;
	}
	public void setReviewNo(Integer reviewNo) {
		this.reviewNo = reviewNo;
	}
	public Integer getCate1() {
		return cate1;
	}
	public void setCate1(Integer cate1) {
		this.cate1 = cate1;
	}
	public Integer getCate2() {
		return cate2;
	}
	public void setCate2(Integer cate2) {
		this.cate2 = cate2;
	}
	public Integer getStar() {
		return star;
	}
	public void setStar(Integer star) {
		this.star = star;
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
	public String getCuserName() {
		return cuserName;
	}
	public void setCuserName(String cuserName) {
		this.cuserName = cuserName;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public String getBestYn() {
		return bestYn;
	}
	public void setBestYn(String bestYn) {
		this.bestYn = bestYn;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCommentYn() {
		return commentYn;
	}
	public void setCommentYn(String commentYn) {
		this.commentYn = commentYn;
	}

}
