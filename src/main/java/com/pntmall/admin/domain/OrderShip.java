package com.pntmall.admin.domain;

import java.util.Date;
import java.util.List;

import com.pntmall.common.type.Domain;

public class OrderShip extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7627336764559488240L;
	
	private String orderid;
	private Integer shipNo;
	private Integer shipGubun;
	private String shipper;
	private String invoice;
	private String deliveryYn;
	private String shipperName;
	private Date date150;
	private Date date160;
	private Integer itemCount;
	
	private List<OrderItem> items;
	
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public Integer getShipGubun() {
		return shipGubun;
	}
	public void setShipGubun(Integer shipGubun) {
		this.shipGubun = shipGubun;
	}
	public String getShipper() {
		return shipper;
	}
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getShipperName() {
		return shipperName;
	}
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	public String getDeliveryYn() {
		return deliveryYn;
	}
	public void setDeliveryYn(String deliveryYn) {
		this.deliveryYn = deliveryYn;
	}
	public Integer getShipNo() {
		return shipNo;
	}
	public void setShipNo(Integer shipNo) {
		this.shipNo = shipNo;
	}
	public Date getDate150() {
		return date150;
	}
	public void setDate150(Date date150) {
		this.date150 = date150;
	}
	public Date getDate160() {
		return date160;
	}
	public void setDate160(Date date160) {
		this.date160 = date160;
	}
	public Integer getItemCount() {
		return itemCount;
	}
	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}
	

	
}
