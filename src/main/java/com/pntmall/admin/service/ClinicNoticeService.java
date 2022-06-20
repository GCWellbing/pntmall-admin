package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Notice;
import com.pntmall.admin.domain.NoticeImg;
import com.pntmall.admin.domain.NoticeSearch;
import com.pntmall.common.type.Param;

@Service
public class ClinicNoticeService {
    public static final Logger logger = LoggerFactory.getLogger(ClinicNoticeService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Notice> getList(NoticeSearch noticeSearch) {
    	return sst.selectList("ClinicNotice.list",noticeSearch);
    }

    public Integer getCount(NoticeSearch noticeSearch) {
    	return sst.selectOne("ClinicNotice.count",noticeSearch);
    }

    public Notice getInfo(Integer noticeNo) {
    	return sst.selectOne("ClinicNotice.info", noticeNo);
    }

    public List<NoticeImg> getNoticeImgList(Integer noticeNo) {
    	return sst.selectList("ClinicNotice.getClinicNoticeImgList",noticeNo);
    }

    @Transactional
    public void create(Notice notice, Param param) {
    	sst.insert("ClinicNotice.insert", notice);
    	Integer noticeNo = notice.getNoticeNo();
    	// 이미지
    	String[] attach = param.getValues("attach");
    	String[] attachOrgName = param.getValues("attachOrgName");

    	for(int i = 0; i < attach.length; i++) {
    		NoticeImg noticeImg = new NoticeImg();
    		noticeImg.setNoticeNo(noticeNo);
    		noticeImg.setAttach(attach[i]);
    		noticeImg.setAttachOrgName(attachOrgName[i]);
    		sst.insert("ClinicNotice.insertClinicNoticeImg", noticeImg);
    	}
    }

    @Transactional
    public void modify(Notice notice, Param param) {
    	sst.update("ClinicNotice.update", notice);
    	Integer noticeNo = notice.getNoticeNo();
    	// 이미지
    	sst.delete("ClinicNotice.deleteClinicNoticeImgInfo", noticeNo);
    	String[] attach = param.getValues("attach");
    	String[] attachOrgName = param.getValues("attachOrgName");

    	for(int i = 0; i < attach.length; i++) {
    		NoticeImg noticeImg = new NoticeImg();
    		noticeImg.setNoticeNo(noticeNo);
    		noticeImg.setAttach(attach[i]);
    		noticeImg.setAttachOrgName(attachOrgName[i]);
    		sst.insert("ClinicNotice.insertClinicNoticeImg", noticeImg);
    	}
     }

}
