package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class SapProduct extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5194128301943280977L;
	
	private String matnr;
	private String maktx;
	private String spart;
	private String werks;
	private String lgort;
	private String charg;
	private String prdha;
	private Integer labst;
	private String meins;
	private String tempb;
	private Integer netpr;
	private Integer mwsbp;
	private String waerk;
	
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getMaktx() {
		return maktx;
	}
	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	public String getSpart() {
		return spart;
	}
	public void setSpart(String spart) {
		this.spart = spart;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public String getLgort() {
		return lgort;
	}
	public void setLgort(String lgort) {
		this.lgort = lgort;
	}
	public String getPrdha() {
		return prdha;
	}
	public void setPrdha(String prdha) {
		this.prdha = prdha;
	}
	public Integer getLabst() {
		return labst;
	}
	public void setLabst(Integer labst) {
		this.labst = labst;
	}
	public String getTempb() {
		return tempb;
	}
	public void setTempb(String tempb) {
		this.tempb = tempb;
	}
	public Integer getNetpr() {
		return netpr;
	}
	public void setNetpr(Integer netpr) {
		this.netpr = netpr;
	}
	public Integer getMwsbp() {
		return mwsbp;
	}
	public void setMwsbp(Integer mwsbp) {
		this.mwsbp = mwsbp;
	}
	public String getCharg() {
		return charg;
	}
	public void setCharg(String charg) {
		this.charg = charg;
	}
	public String getMeins() {
		return meins;
	}
	public void setMeins(String meins) {
		this.meins = meins;
	}
	public String getWaerk() {
		return waerk;
	}
	public void setWaerk(String waerk) {
		this.waerk = waerk;
	}
	
}
