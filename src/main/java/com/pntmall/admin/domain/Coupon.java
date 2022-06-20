package com.pntmall.admin.domain;

import java.util.Date;

import com.pntmall.common.type.Domain;

public class Coupon extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7572214750459311117L;

	private String couponid;
	private Integer gubun;
	private String title;
	private String sdate;
	private String edate;
	private String expire;
	private Integer target;
	private Integer discountType;
	private Integer discount;
	private Integer minPrice;
	private Integer maxDiscount;
	private String status;
	
	private String gubunName;
	private String statusName;
	private String status2Name;
	private Integer totCnt;
	private Integer totUseCnt;
	
	private String mcouponid;
	private Integer memNo;
	private String memId;
	private String memName;
	private Date useDate;
	private String orderid;
	private String gradeName;
	
	public String getCouponid() {
		return couponid;
	}
	public void setCouponid(String couponid) {
		this.couponid = couponid;
	}
	public Integer getGubun() {
		return gubun;
	}
	public void setGubun(Integer gubun) {
		this.gubun = gubun;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public String getExpire() {
		return expire;
	}
	public void setExpire(String expire) {
		this.expire = expire;
	}
	public Integer getTarget() {
		return target;
	}
	public void setTarget(Integer target) {
		this.target = target;
	}
	public Integer getDiscountType() {
		return discountType;
	}
	public void setDiscountType(Integer discountType) {
		this.discountType = discountType;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Integer getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}
	public Integer getMaxDiscount() {
		return maxDiscount;
	}
	public void setMaxDiscount(Integer maxDiscount) {
		this.maxDiscount = maxDiscount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGubunName() {
		return gubunName;
	}
	public void setGubunName(String gubunName) {
		this.gubunName = gubunName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Integer getTotCnt() {
		return totCnt;
	}
	public void setTotCnt(Integer totCnt) {
		this.totCnt = totCnt;
	}
	public Integer getTotUseCnt() {
		return totUseCnt;
	}
	public void setTotUseCnt(Integer totUseCnt) {
		this.totUseCnt = totUseCnt;
	}
	public String getStatus2Name() {
		return status2Name;
	}
	public void setStatus2Name(String status2Name) {
		this.status2Name = status2Name;
	}
	public String getMcouponid() {
		return mcouponid;
	}
	public void setMcouponid(String mcouponid) {
		this.mcouponid = mcouponid;
	}
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
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public Date getUseDate() {
		return useDate;
	}
	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
}
