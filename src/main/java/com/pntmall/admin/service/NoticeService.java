package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pntmall.admin.domain.Notice;
import com.pntmall.admin.domain.NoticeComment;
import com.pntmall.admin.domain.NoticeSearch;

@Service
public class NoticeService {
    public static final Logger logger = LoggerFactory.getLogger(NoticeService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Notice> getList(NoticeSearch noticeSearch) {
    	return sst.selectList("Notice.list",noticeSearch);
    }

    public Integer getCount(NoticeSearch noticeSearch) {
    	return sst.selectOne("Notice.count",noticeSearch);
    }

    public Notice getInfo(Integer noticeNo) {
    	return sst.selectOne("Notice.info", noticeNo);
    }

    public void create(Notice notice) {
    	sst.insert("Notice.insert", notice);
    }

    public void modify(Notice notice) {
    	sst.update("Notice.update", notice);
    }

    public List<NoticeComment> getNoticeCommentList(Integer noticeNo) {
    	return sst.selectList("Notice.noticeCommentList",noticeNo);
    }

    public void setStatusNoticeComment(NoticeComment noticeComment) {
    	logger.debug("noticeComment 2222222222", noticeComment);
    	sst.update("Notice.setStatusNoticeComment", noticeComment);
    }

}
