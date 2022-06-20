package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class Product extends Domain {

	/**
	 *
	 */
	private static final long serialVersionUID = -3775174139528729920L;

	private Integer pno;
	private String matnr;
	private Integer ptype;
	private String reviewYn;
	private String clinicSettlement;
	private String sdate;
	private String edate;
	private String soldout;
	private String status;
	private Integer rank;
	private String brand;
	private String pname;
	private String capa;
	private Integer doseMonth;
	private String subdivision;
	private String summary;
	private String pcDesc;
	private String moDesc;
	private Integer salePrice;
	private Integer supplyPrice;
	private String discountType;
	private String orgImg;
	private String img;
	private String img1;
	private String img2;
	private String img3;
	private String img4;
	private String img5;
	private String routineYn;
	private String clinicPickup;
	private String coldYn;
	private String info1;
	private String info2;
	private String info3;
	private String info4;
	private String info5;
	private String info6;
	private String info7;
	private String info8;
	private String info9;
	private String info10;
	private String info11;
	private String info12;
	private String info13;
	private Integer dosage;
	private Integer doseCnt;
	private String doseMethod;

	private String ptypeName;
	private Integer labst;
	private String cuserName;
	private String uuserName;
	private String maktx;
	private String brandName;
	private Integer netpr;
	private String statusName;
	private String nutritionName;

	public Integer getPno() {
		return pno;
	}
	public void setPno(Integer pno) {
		this.pno = pno;
	}
	public Integer getPtype() {
		return ptype;
	}
	public void setPtype(Integer ptype) {
		this.ptype = ptype;
	}
	public String getReviewYn() {
		return reviewYn;
	}
	public void setReviewYn(String reviewYn) {
		this.reviewYn = reviewYn;
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
	public String getSoldout() {
		return soldout;
	}
	public void setSoldout(String soldout) {
		this.soldout = soldout;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getCapa() {
		return capa;
	}
	public void setCapa(String capa) {
		this.capa = capa;
	}
	public String getSubdivision() {
		return subdivision;
	}
	public void setSubdivision(String subdivision) {
		this.subdivision = subdivision;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getPcDesc() {
		return pcDesc;
	}
	public void setPcDesc(String pcDesc) {
		this.pcDesc = pcDesc;
	}
	public String getMoDesc() {
		return moDesc;
	}
	public void setMoDesc(String moDesc) {
		this.moDesc = moDesc;
	}
	public Integer getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public String getOrgImg() {
		return orgImg;
	}
	public void setOrgImg(String orgImg) {
		this.orgImg = orgImg;
	}
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getImg3() {
		return img3;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}
	public String getImg4() {
		return img4;
	}
	public void setImg4(String img4) {
		this.img4 = img4;
	}
	public String getImg5() {
		return img5;
	}
	public void setImg5(String img5) {
		this.img5 = img5;
	}
	public String getRoutineYn() {
		return routineYn;
	}
	public void setRoutineYn(String routineYn) {
		this.routineYn = routineYn;
	}
	public String getClinicPickup() {
		return clinicPickup;
	}
	public void setClinicPickup(String clinicPickup) {
		this.clinicPickup = clinicPickup;
	}
	public String getColdYn() {
		return coldYn;
	}
	public void setColdYn(String coldYn) {
		this.coldYn = coldYn;
	}
	public String getInfo1() {
		return info1;
	}
	public void setInfo1(String info1) {
		this.info1 = info1;
	}
	public String getInfo2() {
		return info2;
	}
	public void setInfo2(String info2) {
		this.info2 = info2;
	}
	public String getInfo3() {
		return info3;
	}
	public void setInfo3(String info3) {
		this.info3 = info3;
	}
	public String getInfo4() {
		return info4;
	}
	public void setInfo4(String info4) {
		this.info4 = info4;
	}
	public String getInfo5() {
		return info5;
	}
	public void setInfo5(String info5) {
		this.info5 = info5;
	}
	public String getInfo6() {
		return info6;
	}
	public void setInfo6(String info6) {
		this.info6 = info6;
	}
	public String getInfo7() {
		return info7;
	}
	public void setInfo7(String info7) {
		this.info7 = info7;
	}
	public String getInfo8() {
		return info8;
	}
	public void setInfo8(String info8) {
		this.info8 = info8;
	}
	public String getInfo9() {
		return info9;
	}
	public void setInfo9(String info9) {
		this.info9 = info9;
	}
	public String getInfo10() {
		return info10;
	}
	public void setInfo10(String info10) {
		this.info10 = info10;
	}
	public String getInfo11() {
		return info11;
	}
	public void setInfo11(String info11) {
		this.info11 = info11;
	}
	public String getInfo12() {
		return info12;
	}
	public void setInfo12(String info12) {
		this.info12 = info12;
	}
	public String getInfo13() {
		return info13;
	}
	public void setInfo13(String info13) {
		this.info13 = info13;
	}
	public String getPtypeName() {
		return ptypeName;
	}
	public void setPtypeName(String ptypeName) {
		this.ptypeName = ptypeName;
	}
	public String getCuserName() {
		return cuserName;
	}
	public void setCuserName(String cuserName) {
		this.cuserName = cuserName;
	}
	public String getUuserName() {
		return uuserName;
	}
	public void setUuserName(String uuserName) {
		this.uuserName = uuserName;
	}
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	public Integer getDoseMonth() {
		return doseMonth;
	}
	public void setDoseMonth(Integer doseMonth) {
		this.doseMonth = doseMonth;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Integer getNetpr() {
		return netpr;
	}
	public void setNetpr(Integer netpr) {
		this.netpr = netpr;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public Integer getLabst() {
		return labst;
	}
	public void setLabst(Integer labst) {
		this.labst = labst;
	}
	public String getClinicSettlement() {
		return clinicSettlement;
	}
	public void setClinicSettlement(String clinicSettlement) {
		this.clinicSettlement = clinicSettlement;
	}
	public Integer getSupplyPrice() {
		return supplyPrice;
	}
	public void setSupplyPrice(Integer supplyPrice) {
		this.supplyPrice = supplyPrice;
	}
	public String getNutritionName() {
		return nutritionName;
	}
	public void setNutritionName(String nutritionName) {
		this.nutritionName = nutritionName;
	}
	public Integer getDosage() {
		return dosage;
	}
	public void setDosage(Integer dosage) {
		this.dosage = dosage;
	}
	public Integer getDoseCnt() {
		return doseCnt;
	}
	public void setDoseCnt(Integer doseCnt) {
		this.doseCnt = doseCnt;
	}
	public String getDoseMethod() {
		return doseMethod;
	}
	public void setDoseMethod(String doseMethod) {
		this.doseMethod = doseMethod;
	}

}
