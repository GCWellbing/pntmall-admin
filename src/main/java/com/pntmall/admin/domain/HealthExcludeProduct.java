package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class HealthExcludeProduct extends Domain {
	private static final long serialVersionUID = 351006734109837128L;

	private String gubun;	//구분
	private Integer pno;	//상품번호
	private String pname;

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
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
}
