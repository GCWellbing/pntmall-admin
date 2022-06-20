package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

public class EventSearch extends SearchDomain {

    private String sdate;
    private String edate;
    private String name;

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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
