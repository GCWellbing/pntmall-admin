package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

public class EventLog extends Domain {

    private Integer logId;
    private Integer eventId;
    private Integer memNo;
    private Integer itemId;

    public Integer getLogId() {
        return logId;
    }
    public void setLogId(Integer logId) {
        this.logId = logId;
    }
    public Integer getEventId() {
        return eventId;
    }
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
    public Integer getMemNo() {
        return memNo;
    }
    public void setMemNo(Integer memNo) {
        this.memNo = memNo;
    }
    public Integer getItemId() {
        return itemId;
    }
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

}
