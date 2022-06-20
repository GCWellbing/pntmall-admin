package com.pntmall.admin.domain;

import com.pntmall.common.type.SearchDomain;

import java.util.Date;

public class PopUpSearch extends SearchDomain {
    private Integer popupid;
    private Integer type; //분류 1:상단띠배너, 2:전면팝업
    private String title;
    private String sdate;
    private String edate;
    private String displayGradeStatus; //등급별공개 A:전체 G:등급별
    private String displayGrade; // ,로 구분
    private String status; // 상태 S:공개 H:비공개 D:삭제

    public Integer getPopupid() {
        return popupid;
    }
    public void setPopupid(Integer popupid) {
        this.popupid = popupid;
    }
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
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
    public String getDisplayGradeStatus() {
        return displayGradeStatus;
    }
    public void setDisplayGradeStatus(String displayGradeStatus) {
        this.displayGradeStatus = displayGradeStatus;
    }
    public String getDisplayGrade() {
        return displayGrade;
    }
    public void setDisplayGrade(String displayGrade) {
        this.displayGrade = displayGrade;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
