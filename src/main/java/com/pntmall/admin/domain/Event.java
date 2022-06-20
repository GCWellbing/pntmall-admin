package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Event extends Domain {

    private Integer eventId;
    private String name;
    @DateTimeFormat(pattern="yyyy.MM.dd")
    private Date sdate;
    @DateTimeFormat(pattern="yyyy.MM.dd")
    private Date edate;

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
    public Date getSdate() {
        return sdate;
    }
    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }
    public Date getEdate() {
        return edate;
    }
    public void setEdate(Date edate) {
        this.edate = edate;
    }
}
