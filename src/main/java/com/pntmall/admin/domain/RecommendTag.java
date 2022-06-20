package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class RecommendTag extends Domain {

	private static final long serialVersionUID = 3786486937929635052L;

	private Integer tno;
	private String tag;
	private Integer rank;
	private String status;
	private String statusName;

	public Integer getTno() {
		return tno;
	}
	public void setTno(Integer tno) {
		this.tno = tno;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
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
