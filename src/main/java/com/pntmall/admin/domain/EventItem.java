package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class EventItem extends Domain {
    private Integer itemId;
    private Integer eventId;
    private String name;
    private Integer rank;
    private Integer qty;
    private Integer isCoupon;
    private String couponid;

    public Integer getItemId() {
        return itemId;
    }
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
    public Integer getEventId() {
        return eventId;
    }
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getRank() {
        return rank;
    }
    public void setRank(Integer rank) {
        this.rank = rank;
    }
    public Integer getQty() {
        return qty;
    }
    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public Integer getIsCoupon() {
        return isCoupon;
    }
    public void setIsCoupon(Integer isCoupon) {
        this.isCoupon = isCoupon;
    }
    public String getCouponid() {
        return couponid;
    }
    public void setCouponid(String couponid) {
        this.couponid = couponid;
    }
}
