package com.pntmall.admin.domain;

import java.util.Date;

import com.pntmall.common.type.Domain;

public class Order extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5475438446095727767L;

	private String orderid;
	private String groupid;	
	private Integer gubun;
	private Integer orderGubun;
	private Integer memNo;
	private Integer gradeNo;
	private Integer clinicMemNo;
	private String oname;
	private String omtel1;
	private String omtel2;
	private String otel1;
	private String otel2;
	private String oemail;
	private Integer amt;
	private Integer shipAmt;
	private Integer totAmt;
	private Integer gradeDiscount;
	private Integer couponDiscount;
	private Integer shipDiscount;
	private Integer totDiscount;
	private Integer point;
	private Integer payAmt;
	private String payType;
	private String device;
	private String shipMcouponid;
	private String firstOrderYn;
	private Date odate;
	private String orgOrderid;
	private String memo;
	private String gubunName;
	private String orderGubunName;
	private Integer shipSeq;
	private String pname;
	private String memId;
	private String memName;
	private String gradeName;
	private String payTypeName;
	private Integer itemCount;
	private Integer sumPoint;
	private String clinicId;
	private String status;
	private String statusName;
	private Integer pickupClinic;
	private String pickupDate;
	private String pickupTime;
	private String pickupClinicName;
	private String escrow;
	private String escrowTran;
	private String email;
	private String mtel1;
	private String mtel2;
	private String clinicName;
	private Date Cdate;

	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public Integer getGubun() {
		return gubun;
	}
	public void setGubun(Integer gubun) {
		this.gubun = gubun;
	}
	public Integer getOrderGubun() {
		return orderGubun;
	}
	public void setOrderGubun(Integer orderGubun) {
		this.orderGubun = orderGubun;
	}
	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public Integer getGradeNo() {
		return gradeNo;
	}
	public void setGradeNo(Integer gradeNo) {
		this.gradeNo = gradeNo;
	}
	public Integer getClinicMemNo() {
		return clinicMemNo;
	}
	public void setClinicMemNo(Integer clinicMemNo) {
		this.clinicMemNo = clinicMemNo;
	}
	public String getOname() {
		return oname;
	}
	public void setOname(String oname) {
		this.oname = oname;
	}
	public String getOmtel1() {
		return omtel1;
	}
	public void setOmtel1(String omtel1) {
		this.omtel1 = omtel1;
	}
	public String getOmtel2() {
		return omtel2;
	}
	public void setOmtel2(String omtel2) {
		this.omtel2 = omtel2;
	}
	public String getOtel1() {
		return otel1;
	}
	public void setOtel1(String otel1) {
		this.otel1 = otel1;
	}
	public String getOtel2() {
		return otel2;
	}
	public void setOtel2(String otel2) {
		this.otel2 = otel2;
	}
	public String getOemail() {
		return oemail;
	}
	public void setOemail(String oemail) {
		this.oemail = oemail;
	}
	public Integer getAmt() {
		return amt;
	}
	public void setAmt(Integer amt) {
		this.amt = amt;
	}
	public Integer getShipAmt() {
		return shipAmt;
	}
	public void setShipAmt(Integer shipAmt) {
		this.shipAmt = shipAmt;
	}
	public Integer getTotAmt() {
		return totAmt;
	}
	public void setTotAmt(Integer totAmt) {
		this.totAmt = totAmt;
	}
	public Integer getGradeDiscount() {
		return gradeDiscount;
	}
	public void setGradeDiscount(Integer gradeDiscount) {
		this.gradeDiscount = gradeDiscount;
	}
	public Integer getCouponDiscount() {
		return couponDiscount;
	}
	public void setCouponDiscount(Integer couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
	public Integer getShipDiscount() {
		return shipDiscount;
	}
	public void setShipDiscount(Integer shipDiscount) {
		this.shipDiscount = shipDiscount;
	}
	public Integer getTotDiscount() {
		return totDiscount;
	}
	public void setTotDiscount(Integer totDiscount) {
		this.totDiscount = totDiscount;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public Integer getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(Integer payAmt) {
		this.payAmt = payAmt;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getShipMcouponid() {
		return shipMcouponid;
	}
	public void setShipMcouponid(String shipMcouponid) {
		this.shipMcouponid = shipMcouponid;
	}
	public String getFirstOrderYn() {
		return firstOrderYn;
	}
	public void setFirstOrderYn(String firstOrderYn) {
		this.firstOrderYn = firstOrderYn;
	}
	public Date getOdate() {
		return odate;
	}
	public void setOdate(Date odate) {
		this.odate = odate;
	}
	public String getOrgOrderid() {
		return orgOrderid;
	}
	public void setOrgOrderid(String orgOrderid) {
		this.orgOrderid = orgOrderid;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getGubunName() {
		return gubunName;
	}
	public void setGubunName(String gubunName) {
		this.gubunName = gubunName;
	}
	public String getOrderGubunName() {
		return orderGubunName;
	}
	public void setOrderGubunName(String orderGubunName) {
		this.orderGubunName = orderGubunName;
	}
	public Integer getShipSeq() {
		return shipSeq;
	}
	public void setShipSeq(Integer shipSeq) {
		this.shipSeq = shipSeq;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getPayTypeName() {
		return payTypeName;
	}
	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}
	public Integer getItemCount() {
		return itemCount;
	}
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}
	public String getClinicId() {
		return clinicId;
	}
	public void setClinicId(String clinicId) {
		this.clinicId = clinicId;
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
	public Integer getPickupClinic() {
		return pickupClinic;
	}
	public void setPickupClinic(Integer pickupClinic) {
		this.pickupClinic = pickupClinic;
	}
	public String getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	public String getPickupTime() {
		return pickupTime;
	}
	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}
	public String getPickupClinicName() {
		return pickupClinicName;
	}
	public void setPickupClinicName(String pickupClinicName) {
		this.pickupClinicName = pickupClinicName;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getEscrow() {
		return escrow;
	}
	public void setEscrow(String escrow) {
		this.escrow = escrow;
	}
	public String getEscrowTran() {
		return escrowTran;
	}
	public void setEscrowTran(String escrowTran) {
		this.escrowTran = escrowTran;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMtel1() {
		return mtel1;
	}
	public void setMtel1(String mtel1) {
		this.mtel1 = mtel1;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public Integer getSumPoint() {
		return sumPoint;
	}
	public void setSumPoint(Integer sumPoint) {
		this.sumPoint = sumPoint;
	}
	public String getMtel2() {
		return mtel2;
	}
	public void setMtel2(String mtel2) {
		this.mtel2 = mtel2;
	}

	@Override
	public Date getCdate() {
		return Cdate;
	}

	@Override
	public void setCdate(Date cdate) {
		Cdate = cdate;
	}
}
