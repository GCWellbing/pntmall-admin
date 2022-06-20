package com.pntmall.admin.service;

import java.util.List;

import com.pntmall.common.type.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Point;
import com.pntmall.admin.domain.Review;
import com.pntmall.admin.domain.ReviewImg;
import com.pntmall.admin.domain.ReviewSearch;

@Service
public class ReviewService {
    public static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private SqlSessionTemplate sst;

    @Autowired
	private PointService pointService;

    public List<Review> getList(ReviewSearch reviewSearch) {
    	return sst.selectList("Review.list",reviewSearch);
    }

    public Integer getCount(ReviewSearch reviewSearch) {
    	return sst.selectOne("Review.count",reviewSearch);
    }

    public Review getInfo(ReviewSearch reviewSearch) {
    	return sst.selectOne("Review.info", reviewSearch);
    }

    public List<ReviewImg> getReviewImgList(ReviewSearch reviewSearch) {
    	return sst.selectList("Review.getReviewImgList",reviewSearch);
    }

    @Transactional
    public void create(Review review, Param param) {

        sst.insert("Review.insert", review);
        Integer reviewNo = review.getReviewNo();

        // 이미지
        String[] attach = param.getValues("attach");
        String[] attachOrgName = param.getValues("attachOrgName");
        for(int i = 0; i < attach.length; i++) {
            ReviewImg reviewImg = new ReviewImg();
            reviewImg.setReviewNo(reviewNo);
            reviewImg.setAttach(attach[i]);
            reviewImg.setAttachOrgName(attachOrgName[i]);
            sst.insert("Review.insertReviewImg", reviewImg);
        }
    }

    @Transactional
    public void addModify(Review review, Param param) {
        sst.update("Review.addUpdate", review);
        Integer reviewNo = review.getReviewNo();

        // 이미지
        String[] attach = param.getValues("attach");
        String[] attachOrgName = param.getValues("attachOrgName");
        String[] gubun = param.getValues("gubun");
        for(int i = 0; i < attach.length; i++) {
            if (gubun[i].equals("review")) {
                ReviewImg reviewImg = new ReviewImg();
                reviewImg.setReviewNo(reviewNo);
                reviewImg.setAttach(attach[i]);
                reviewImg.setAttachOrgName(attachOrgName[i]);
                sst.insert("Review.insertReviewImg", reviewImg);
            }
        }
    }

    @Transactional
    public void modify(Review review) {
    	Boolean savePoint = false;
    	if(!"Y".equals(review.getBestPointYn()) && "Y".equals(review.getBestYn()) ) {
    		review.setBestPointYn("Y");
    		savePoint = true;
    	}

    	sst.update("Review.update", review);

    	//포인트 적립
    	if(savePoint) {
    		Point point = new Point();
            int pointRst = sst.selectOne("Point.getPoint", "5");
            String pointRsn = "019010";
            point.setMemNo(review.getCuser());
            point.setPoint(pointRst);
            point.setReason(pointRsn);
            point.setCuser(review.getUuser());
        	pointService.addPoint(point);
    	}
    }

    @Transactional
    public void setBestSeq(List<Review> listReview) {
    	for (Review review : listReview) {
    		sst.update("Review.updateBestSeqBefore", review);
    		sst.update("Review.updateBestSeq", review);
    	}
    }

    @Transactional
    public void setReviewSeq(List<Review> listReview) {
    	for (Review review : listReview) {
    		sst.update("Review.updateReviewSeqBefore", review);
    		sst.update("Review.updateReviewSeq", review);
    	}
    }

}
