package com.pntmall.admin.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Bbs;
import com.pntmall.admin.domain.BbsComment;
import com.pntmall.admin.domain.BbsImg;
import com.pntmall.admin.domain.BbsSearch;
import com.pntmall.common.type.Param;

@Service
public class BbsService {
    public static final Logger logger = LoggerFactory.getLogger(BbsService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Bbs> getList(BbsSearch bbsSearch) {
    	return sst.selectList("Bbs.list",bbsSearch);
    }

    public Integer getCount(BbsSearch bbsSearch) {
    	return sst.selectOne("Bbs.count",bbsSearch);
    }

    public Bbs getInfo(Integer bbsNo) {
    	return sst.selectOne("Bbs.info", bbsNo);
    }


    @Transactional
    public void create(Bbs bbs, Param param) {
    	sst.insert("Bbs.insert", bbs);
    	Integer bbsNo = bbs.getBbsNo();
    	// 이미지
    	String[] attach = param.getValues("attach");
    	String[] attachOrgName = param.getValues("attachOrgName");

    	for(int i = 0; i < attach.length; i++) {
    		BbsImg bbsImg = new BbsImg();
    		bbsImg.setBbsNo(bbsNo);
    		bbsImg.setAttach(attach[i]);
    		bbsImg.setAttachOrgName(attachOrgName[i]);
    		sst.insert("Bbs.insertBbsImg", bbsImg);
    	}
    }

    @Transactional
    public void modify(Bbs bbs, Param param) {
    	sst.update("Bbs.update", bbs);
    	Integer bbsNo = bbs.getBbsNo();
    	// 이미지
    	sst.delete("Bbs.deleteBbsImgInfo", bbsNo);
    	String[] attach = param.getValues("attach");
    	String[] attachOrgName = param.getValues("attachOrgName");

    	for(int i = 0; i < attach.length; i++) {
    		BbsImg bbsImg = new BbsImg();
    		bbsImg.setBbsNo(bbsNo);
    		bbsImg.setAttach(attach[i]);
    		bbsImg.setAttachOrgName(attachOrgName[i]);
    		sst.insert("Bbs.insertBbsImg", bbsImg);
    	}
     }

    @Transactional
    public List<BbsComment> getBbsCommentList(Integer bbsNo) {
    	return sst.selectList("Bbs.bbsCommentList",bbsNo);
    }

    public void insertComment(BbsComment bbsComment) {
    	sst.update("Bbs.insertComment", bbsComment);
    }

    public void updateComment(BbsComment bbsComment) {
    	sst.update("Bbs.updateComment", bbsComment);
    }

    public void deleteComment(BbsComment bbsComment) {
    	sst.update("Bbs.deleteComment", bbsComment);
    }

    public List<BbsImg> getBbsImgList(Integer bbsNo) {
    	return sst.selectList("Bbs.getBbsImgList",bbsNo);
    }

}
