package com.pntmall.admin.domain;

import com.pntmall.common.type.Domain;

import java.util.Date;

public class PopUp extends Domain {

    private Integer popupid;
    private Integer type; // 타입 1:상단띠배너 2:전면팝업
    private String typeName;
    private String title;
    private String sdate;
    private String edate;
    private String pcImg;
    private String pcImgAlt;
    private String moImg;
    private String moImgAlt;
    private String rebonImg;
    private String rebonImgAlt;
    private String url;
    private String displayGradeStatus; //등급별공개 A:전체 G:등급별
    private String displayGrade; // ,로 구분
    private String status; // 상태 S:공개 H:비공개 D:삭제
    private String statusName;
    private String target;
    private String backgroundColor;
    private String fontColor;

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
    public String getPcImg() {
        return pcImg;
    }
    public void setPcImg(String pcImg) {
        this.pcImg = pcImg;
    }
    public String getPcImgAlt() {
        return pcImgAlt;
    }
    public void setPcImgAlt(String pcImgAlt) {
        this.pcImgAlt = pcImgAlt;
    }
    public String getMoImg() {
        return moImg;
    }
    public void setMoImg(String moImg) {
        this.moImg = moImg;
    }
    public String getMoImgAlt() {
        return moImgAlt;
    }
    public void setMoImgAlt(String moImgAlt) {
        this.moImgAlt = moImgAlt;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
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
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public String getStatusName() {
        return statusName;
    }
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
        this.target = target;
    }
    public String getBackgroundColor() {
        return backgroundColor;
    }
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    public String getFontColor() {
        return fontColor;
    }
    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }
    public String getRebonImg() {
        return rebonImg;
    }
    public void setRebonImg(String rebonImg) {
        this.rebonImg = rebonImg;
    }
    public String getRebonImgAlt() {
        return rebonImgAlt;
    }
    public void setRebonImgAlt(String rebonImgAlt) {
        this.rebonImgAlt = rebonImgAlt;
    }
}
