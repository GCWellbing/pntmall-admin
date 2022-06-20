package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class PointConfig extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1952714407505103890L;
	
	private Integer cno;
	private Integer typeNo;
	private String typeName;
	private String title;
	private Integer point;
	
	public Integer getCno() {
		return cno;
	}
	public void setCno(Integer cno) {
		this.cno = cno;
	}
	public Integer getTypeNo() {
		return typeNo;
	}
	public void setTypeNo(Integer typeNo) {
		this.typeNo = typeNo;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	
	
	
}
