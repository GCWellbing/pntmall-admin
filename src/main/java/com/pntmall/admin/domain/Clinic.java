package com.pntmall.admin.domain;

import java.util.Date;

import com.pntmall.common.type.Domain;

public class Clinic extends Domain {

	private static final long serialVersionUID = -7839304915994810455L;

	private Integer memNo;
	private String memId;
	private String name;
	private String mtel1;
	private String mtel2;
	private String email;
	private String clinicId;
	private String clinicName;
	private String zip;
	private String addr1;
	private String addr2;
	private String tel1;
	private String tel2;
	private String intro;
	private String subject;
	private String monSh;
	private String monSm;
	private String monEh;
	private String monEm;
	private String monClose;
	private String tueSh;
	private String tueSm;
	private String tueEh;
	private String tueEm;
	private String tueClose;
	private String wedSh;
	private String wedSm;
	private String wedEh;
	private String wedEm;
	private String wedClose;
	private String thuSh;
	private String thuSm;
	private String thuEh;
	private String thuEm;
	private String thuClose;
	private String friSh;
	private String friSm;
	private String friEh;
	private String friEm;
	private String friClose;
	private String satSh;
	private String satSm;
	private String satEh;
	private String satEm;
	private String satClose;
	private String sunSh;
	private String sunSm;
	private String sunEh;
	private String sunEm;
	private String sunClose;
	private String holidaySh;
	private String holidaySm;
	private String holidayEh;
	private String holidayEm;
	private String holidayClose;
	private String lunchSh;
	private String lunchSm;
	private String lunchEh;
	private String lunchEm;
	private String lunchYn;
	private String alarmTel1;
	private String alarmTel2;
	private String alarmType;
	private String blog;
	private String youtube;
	private String facebook;
	private String instagram;
	private String twitter;
	private String businessNo;
	private String medicalNo;
	private Integer taxType;
	private String bank;
	private String bankName;
	private String depositor;
	private String depositorNot;
	private String account;
	private String dispYn;
	private String approve;
	private String approveName;
	private String reason;
	private String latitude;
	private String longitude;
	private String doctorIntro;
	private String doctorHistory;
	private String divisionYn;
	private String pickupYn;
	private String katalkYn;
	private String katalkId;
	private String reservationYn;

	private String clinicSellCd;
	private String clinicBuyCd;
	private String businessOwner;
	private String businessName;
	private String businessItem;
	private String businessType;
	private String businessNo2;
	private String noticeYn;
	private String notice;
	private Integer RealScore;

	private Integer divisionScore;
	private Integer pickupScore;
	private Integer katalkScore;
	private Integer snsScore;
	private Integer infoScore;
	private Integer bbsScore;
	private Integer totalScore;
	private Integer recommendSeq;
	private String businessOwner2;
	private String businessName2;
	private String businessItem2;
	private String businessType2;
	private Date loginDate;
	private Date loginClinicDate;

	private String smsYn;
	private String emailYn;
	private String smsYnOld;
	private String emailYnOld;

	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public String getClinicId() {
		return clinicId;
	}
	public void setClinicId(String clinicId) {
		this.clinicId = clinicId;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMonSh() {
		return monSh;
	}
	public void setMonSh(String monSh) {
		this.monSh = monSh;
	}
	public String getMonSm() {
		return monSm;
	}
	public void setMonSm(String monSm) {
		this.monSm = monSm;
	}
	public String getMonEh() {
		return monEh;
	}
	public void setMonEh(String monEh) {
		this.monEh = monEh;
	}
	public String getMonEm() {
		return monEm;
	}
	public void setMonEm(String monEm) {
		this.monEm = monEm;
	}
	public String getMonClose() {
		return monClose;
	}
	public void setMonClose(String monClose) {
		this.monClose = monClose;
	}
	public String getTueSh() {
		return tueSh;
	}
	public void setTueSh(String tueSh) {
		this.tueSh = tueSh;
	}
	public String getTueSm() {
		return tueSm;
	}
	public void setTueSm(String tueSm) {
		this.tueSm = tueSm;
	}
	public String getTueEh() {
		return tueEh;
	}
	public void setTueEh(String tueEh) {
		this.tueEh = tueEh;
	}
	public String getTueEm() {
		return tueEm;
	}
	public void setTueEm(String tueEm) {
		this.tueEm = tueEm;
	}
	public String getTueClose() {
		return tueClose;
	}
	public void setTueClose(String tueClose) {
		this.tueClose = tueClose;
	}
	public String getWedSh() {
		return wedSh;
	}
	public void setWedSh(String wedSh) {
		this.wedSh = wedSh;
	}
	public String getWedSm() {
		return wedSm;
	}
	public void setWedSm(String wedSm) {
		this.wedSm = wedSm;
	}
	public String getWedEh() {
		return wedEh;
	}
	public void setWedEh(String wedEh) {
		this.wedEh = wedEh;
	}
	public String getWedEm() {
		return wedEm;
	}
	public void setWedEm(String wedEm) {
		this.wedEm = wedEm;
	}
	public String getWedClose() {
		return wedClose;
	}
	public void setWedClose(String wedClose) {
		this.wedClose = wedClose;
	}
	public String getThuSh() {
		return thuSh;
	}
	public void setThuSh(String thuSh) {
		this.thuSh = thuSh;
	}
	public String getThuSm() {
		return thuSm;
	}
	public void setThuSm(String thuSm) {
		this.thuSm = thuSm;
	}
	public String getThuEh() {
		return thuEh;
	}
	public void setThuEh(String thuEh) {
		this.thuEh = thuEh;
	}
	public String getThuEm() {
		return thuEm;
	}
	public void setThuEm(String thuEm) {
		this.thuEm = thuEm;
	}
	public String getThuClose() {
		return thuClose;
	}
	public void setThuClose(String thuClose) {
		this.thuClose = thuClose;
	}
	public String getFriSh() {
		return friSh;
	}
	public void setFriSh(String friSh) {
		this.friSh = friSh;
	}
	public String getFriSm() {
		return friSm;
	}
	public void setFriSm(String friSm) {
		this.friSm = friSm;
	}
	public String getFriEh() {
		return friEh;
	}
	public void setFriEh(String friEh) {
		this.friEh = friEh;
	}
	public String getFriEm() {
		return friEm;
	}
	public void setFriEm(String friEm) {
		this.friEm = friEm;
	}
	public String getFriClose() {
		return friClose;
	}
	public void setFriClose(String friClose) {
		this.friClose = friClose;
	}
	public String getSatSh() {
		return satSh;
	}
	public void setSatSh(String satSh) {
		this.satSh = satSh;
	}
	public String getSatSm() {
		return satSm;
	}
	public void setSatSm(String satSm) {
		this.satSm = satSm;
	}
	public String getSatEh() {
		return satEh;
	}
	public void setSatEh(String satEh) {
		this.satEh = satEh;
	}
	public String getSatEm() {
		return satEm;
	}
	public void setSatEm(String satEm) {
		this.satEm = satEm;
	}
	public String getSatClose() {
		return satClose;
	}
	public void setSatClose(String satClose) {
		this.satClose = satClose;
	}
	public String getSunSh() {
		return sunSh;
	}
	public void setSunSh(String sunSh) {
		this.sunSh = sunSh;
	}
	public String getSunSm() {
		return sunSm;
	}
	public void setSunSm(String sunSm) {
		this.sunSm = sunSm;
	}
	public String getSunEh() {
		return sunEh;
	}
	public void setSunEh(String sunEh) {
		this.sunEh = sunEh;
	}
	public String getSunEm() {
		return sunEm;
	}
	public void setSunEm(String sunEm) {
		this.sunEm = sunEm;
	}
	public String getSunClose() {
		return sunClose;
	}
	public void setSunClose(String sunClose) {
		this.sunClose = sunClose;
	}
	public String getHolidaySh() {
		return holidaySh;
	}
	public void setHolidaySh(String holidaySh) {
		this.holidaySh = holidaySh;
	}
	public String getHolidaySm() {
		return holidaySm;
	}
	public void setHolidaySm(String holidaySm) {
		this.holidaySm = holidaySm;
	}
	public String getHolidayEh() {
		return holidayEh;
	}
	public void setHolidayEh(String holidayEh) {
		this.holidayEh = holidayEh;
	}
	public String getHolidayEm() {
		return holidayEm;
	}
	public void setHolidayEm(String holidayEm) {
		this.holidayEm = holidayEm;
	}
	public String getHolidayClose() {
		return holidayClose;
	}
	public void setHolidayClose(String holidayClose) {
		this.holidayClose = holidayClose;
	}
	public String getLunchSh() {
		return lunchSh;
	}
	public void setLunchSh(String lunchSh) {
		this.lunchSh = lunchSh;
	}
	public String getLunchSm() {
		return lunchSm;
	}
	public void setLunchSm(String lunchSm) {
		this.lunchSm = lunchSm;
	}
	public String getLunchEh() {
		return lunchEh;
	}
	public void setLunchEh(String lunchEh) {
		this.lunchEh = lunchEh;
	}
	public String getLunchEm() {
		return lunchEm;
	}
	public void setLunchEm(String lunchEm) {
		this.lunchEm = lunchEm;
	}
	public String getLunchYn() {
		return lunchYn;
	}
	public void setLunchYn(String lunchYn) {
		this.lunchYn = lunchYn;
	}
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public String getBlog() {
		return blog;
	}
	public void setBlog(String blog) {
		this.blog = blog;
	}
	public String getYoutube() {
		return youtube;
	}
	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}
	public String getFacebook() {
		return facebook;
	}
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	public String getInstagram() {
		return instagram;
	}
	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public String getMedicalNo() {
		return medicalNo;
	}
	public void setMedicalNo(String medicalNo) {
		this.medicalNo = medicalNo;
	}
	public Integer getTaxType() {
		return taxType;
	}
	public void setTaxType(Integer taxType) {
		this.taxType = taxType;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getDepositor() {
		return depositor;
	}
	public void setDepositor(String depositor) {
		this.depositor = depositor;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getDispYn() {
		return dispYn;
	}
	public void setDispYn(String dispYn) {
		this.dispYn = dispYn;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getApprove() {
		return approve;
	}
	public void setApprove(String approve) {
		this.approve = approve;
	}
	public String getApproveName() {
		return approveName;
	}
	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDoctorIntro() {
		return doctorIntro;
	}
	public void setDoctorIntro(String doctorIntro) {
		this.doctorIntro = doctorIntro;
	}
	public String getDoctorHistory() {
		return doctorHistory;
	}
	public void setDoctorHistory(String doctorHistory) {
		this.doctorHistory = doctorHistory;
	}
	public String getDivisionYn() {
		return divisionYn;
	}
	public void setDivisionYn(String divisionYn) {
		this.divisionYn = divisionYn;
	}
	public String getPickupYn() {
		return pickupYn;
	}
	public void setPickupYn(String pickupYn) {
		this.pickupYn = pickupYn;
	}
	public String getKatalkYn() {
		return katalkYn;
	}
	public void setKatalkYn(String katalkYn) {
		this.katalkYn = katalkYn;
	}
	public String getReservationYn() {
		return reservationYn;
	}
	public void setReservationYn(String reservationYn) {
		this.reservationYn = reservationYn;
	}
	public String getClinicSellCd() {
		return clinicSellCd;
	}
	public void setClinicSellCd(String clinicSellCd) {
		this.clinicSellCd = clinicSellCd;
	}
	public String getClinicBuyCd() {
		return clinicBuyCd;
	}
	public void setClinicBuyCd(String clinicBuyCd) {
		this.clinicBuyCd = clinicBuyCd;
	}
	public String getBusinessOwner() {
		return businessOwner;
	}
	public void setBusinessOwner(String businessOwner) {
		this.businessOwner = businessOwner;
	}
	public String getBusinessItem() {
		return businessItem;
	}
	public void setBusinessItem(String businessItem) {
		this.businessItem = businessItem;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getNoticeYn() {
		return noticeYn;
	}
	public void setNoticeYn(String noticeYn) {
		this.noticeYn = noticeYn;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getBusinessNo2() {
		return businessNo2;
	}
	public void setBusinessNo2(String businessNo2) {
		this.businessNo2 = businessNo2;
	}
	public String getMtel1() {
		return mtel1;
	}
	public void setMtel1(String mtel1) {
		this.mtel1 = mtel1;
	}
	public String getMtel2() {
		return mtel2;
	}
	public void setMtel2(String mtel2) {
		this.mtel2 = mtel2;
	}
	public Integer getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	public Integer getBbsScore() {
		return bbsScore;
	}
	public void setBbsScore(Integer bbsScore) {
		this.bbsScore = bbsScore;
	}
	public Integer getRealScore() {
		return RealScore;
	}
	public void setRealScore(Integer realScore) {
		RealScore = realScore;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getAlarmTel1() {
		return alarmTel1;
	}
	public void setAlarmTel1(String alarmTel1) {
		this.alarmTel1 = alarmTel1;
	}
	public String getAlarmTel2() {
		return alarmTel2;
	}
	public void setAlarmTel2(String alarmTel2) {
		this.alarmTel2 = alarmTel2;
	}
	public Integer getDivisionScore() {
		return divisionScore;
	}
	public void setDivisionScore(Integer divisionScore) {
		this.divisionScore = divisionScore;
	}
	public Integer getPickupScore() {
		return pickupScore;
	}
	public void setPickupScore(Integer pickupScore) {
		this.pickupScore = pickupScore;
	}
	public Integer getKatalkScore() {
		return katalkScore;
	}
	public void setKatalkScore(Integer katalkScore) {
		this.katalkScore = katalkScore;
	}
	public Integer getSnsScore() {
		return snsScore;
	}
	public void setSnsScore(Integer snsScore) {
		this.snsScore = snsScore;
	}
	public Integer getInfoScore() {
		return infoScore;
	}
	public void setInfoScore(Integer infoScore) {
		this.infoScore = infoScore;
	}
	public Integer getRecommendSeq() {
		return recommendSeq;
	}
	public void setRecommendSeq(Integer recommendSeq) {
		this.recommendSeq = recommendSeq;
	}
	public String getBusinessOwner2() {
		return businessOwner2;
	}
	public void setBusinessOwner2(String businessOwner2) {
		this.businessOwner2 = businessOwner2;
	}
	public String getBusinessItem2() {
		return businessItem2;
	}
	public void setBusinessItem2(String businessItem2) {
		this.businessItem2 = businessItem2;
	}
	public String getBusinessType2() {
		return businessType2;
	}
	public void setBusinessType2(String businessType2) {
		this.businessType2 = businessType2;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getBusinessName2() {
		return businessName2;
	}
	public void setBusinessName2(String businessName2) {
		this.businessName2 = businessName2;
	}
	public String getKatalkId() {
		return katalkId;
	}
	public void setKatalkId(String katalkId) {
		this.katalkId = katalkId;
	}
	public String getDepositorNot() {
		return depositorNot;
	}
	public void setDepositorNot(String depositorNot) {
		this.depositorNot = depositorNot;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public Date getLoginClinicDate() {
		return loginClinicDate;
	}
	public void setLoginClinicDate(Date loginClinicDate) {
		this.loginClinicDate = loginClinicDate;
	}
	public String getSmsYn() {
		return smsYn;
	}
	public void setSmsYn(String smsYn) {
		this.smsYn = smsYn;
	}
	public String getEmailYn() {
		return emailYn;
	}
	public void setEmailYn(String emailYn) {
		this.emailYn = emailYn;
	}
	public String getSmsYnOld() {
		return smsYnOld;
	}
	public void setSmsYnOld(String smsYnOld) {
		this.smsYnOld = smsYnOld;
	}
	public String getEmailYnOld() {
		return emailYnOld;
	}
	public void setEmailYnOld(String emailYnOld) {
		this.emailYnOld = emailYnOld;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}
