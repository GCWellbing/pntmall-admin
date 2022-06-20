package com.pntmall.admin.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Product;
import com.pntmall.admin.domain.ProductCategory;
import com.pntmall.admin.domain.ProductDiscount;
import com.pntmall.admin.domain.ProductDose;
import com.pntmall.admin.domain.ProductGift;
import com.pntmall.admin.domain.ProductGrade;
import com.pntmall.admin.domain.ProductIcon;
import com.pntmall.admin.domain.ProductIntake;
import com.pntmall.admin.domain.ProductNutrition;
import com.pntmall.admin.domain.ProductOption;
import com.pntmall.admin.domain.ProductSearch;
import com.pntmall.admin.domain.ProductTag;
import com.pntmall.admin.domain.SapProduct;
import com.pntmall.admin.domain.SapProductSearch;
import com.pntmall.common.type.Param;

@Service
public class ProductService {
    public static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private SqlSessionTemplate sst;

    public List<Product> getList(ProductSearch productSearch) {
    	return sst.selectList("Product.list", productSearch);
    }

    public Integer getCount(ProductSearch productSearch) {
    	return sst.selectOne("Product.count", productSearch);
    }
    
    public Product getInfo(Integer pno) {
    	return sst.selectOne("Product.info", pno);
    }
    
    public List<SapProduct> getSapList(SapProductSearch sapProductSearch) {
    	return sst.selectList("Product.sapList", sapProductSearch);
    }
    
    public Integer getSapCount(SapProductSearch sapProductSearch) {
    	return sst.selectOne("Product.sapCount", sapProductSearch);
    }
    
    public List<ProductGrade> getGradeList(Integer pno) {
    	return sst.selectList("Product.gradeList", pno);
    }
    
    public List<ProductCategory> getCategoryList(Integer pno) {
    	return sst.selectList("Product.categoryList", pno);
    }
    
    public List<ProductTag> getTagList(Integer pno) {
    	return sst.selectList("Product.tagList", pno);
    }
    
    public List<ProductIcon> getIconList(Integer pno) {
    	return sst.selectList("Product.iconList", pno);
    }
    
    public List<ProductGift> getGiftList(Integer pno) {
    	return sst.selectList("Product.giftList", pno);
    }
    
    public List<ProductIntake> getIntakeList(Integer pno) {
    	return sst.selectList("Product.intakeList", pno);
    }
    
    public List<ProductDose> getDoseList(Integer pno) {
    	return sst.selectList("Product.doseList", pno);
    }
    
    public List<ProductNutrition> getNutritionList(Integer pno) {
    	return sst.selectList("Product.nutritionList", pno);
    }
    
    public List<ProductOption> getOptionList(Integer pno) {
    	return sst.selectList("Product.optionList", pno);
    }
    
    public List<ProductDiscount> getDiscountList(Integer pno) {
    	return sst.selectList("Product.discountList", pno);
    }
    
    @Transactional
    public void create(Product product, Param param) {
    	sst.insert("Product.insert", product);
    	Integer pno = product.getPno();
    	
    	// category
    	String[] cateNos = param.getValues("cateNo");
    	for(String cateNo : cateNos) {
    		ProductCategory productCategory = new ProductCategory();
    		productCategory.setPno(pno);
    		productCategory.setCateNo(Integer.parseInt(cateNo));
    		sst.insert("Product.insertCategory", productCategory);
    	}

    	// grade 노출 등급
    	String[] gradeNos = param.getValues("gradeNo");
    	for(String gradeNo : gradeNos) {
    		ProductGrade productGrade = new ProductGrade();
    		productGrade.setPno(pno);
    		productGrade.setGradeNo(Integer.parseInt(gradeNo));
    		sst.insert("Product.insertGrade", productGrade);
    	}
    	
    	// tag
    	if(StringUtils.isNotEmpty(param.get("tag"))) {
	    	String[] tags = param.get("tag").split(",");
	    	for(String tag : tags) {
	    		ProductTag productTag = new ProductTag();
	    		productTag.setPno(pno);
	    		productTag.setTag(tag);
	    		sst.insert("Product.insertTag", productTag);
	    	}
    	}
    	
    	// icon 제품유형
    	String[] iconNos = param.getValues("iconNo");
    	for(String iconNo : iconNos) {
    		ProductIcon productIcon = new ProductIcon();
    		productIcon.setPno(pno);
    		productIcon.setIconNo(Integer.parseInt(iconNo));
    		sst.insert("Product.insertIcon", productIcon);
    	}
    	
    	// gift 증정품
    	String[] giftPnos = param.getValues("giftPno");
    	for(String giftPno : giftPnos) {
    		ProductGift productGift = new ProductGift();
    		productGift.setPno(pno);
    		productGift.setGiftPno(Integer.parseInt(giftPno));
    		sst.insert("Product.insertGift", productGift);
    	}
    	
    	// intake 추천 섭취대상
    	String[] intakeNos = param.getValues("intakeNo");
    	for(int i = 0; i < intakeNos.length; i++) {
    		ProductIntake productIntake = new ProductIntake();
    		productIntake.setPno(pno);
    		productIntake.setIntakeNo(Integer.parseInt(intakeNos[i]));
    		productIntake.setRank(i);
    		sst.insert("Product.insertIntake", productIntake);
    	}
    	
    	// dose 복용안내
    	String[] doseNos = param.getValues("doseNo");
    	for(int i = 0; i < doseNos.length; i++) {
    		ProductDose productDose = new ProductDose();
    		productDose.setPno(pno);
    		productDose.setDoseNo(Integer.parseInt(doseNos[i]));
    		productDose.setRank(i);
    		sst.insert("Product.insertDose", productDose);
    	}
    	
    	// nutrition 영양/기능 정보
    	String[] nutritionNos = param.getValues("nutritionNo");
    	for(int i = 0; i < nutritionNos.length; i++) {
    		ProductNutrition productNutrition = new ProductNutrition();
    		productNutrition.setPno(pno);
    		productNutrition.setNutritionNo(Integer.parseInt(nutritionNos[i]));
    		productNutrition.setStandard(param.get("nutritionStandard" + nutritionNos[i]));
    		productNutrition.setContent(param.get("nutritionContent" + nutritionNos[i]));
    		productNutrition.setRank(i);
    		sst.insert("Product.insertNutrition", productNutrition);
    	}
    	
    	// option 옵션제품
    	String[] optPnos = param.getValues("optPno");
    	for(int i = 0; i < optPnos.length; i++) {
    		ProductOption productOption = new ProductOption();
    		productOption.setPno(pno);
    		productOption.setOptPno(Integer.parseInt(optPnos[i]));
    		productOption.setOptNm(param.get("optNm" + optPnos[i]));
    		productOption.setRank(i);
    		sst.insert("Product.insertOption", productOption);
    	}
    	
    	// discount 등급별 할인
    	String[] discountGrades = param.getValues("discountGrade");
    	for(String discountGrade : discountGrades) {
    		ProductDiscount productDiscount = new ProductDiscount();
    		productDiscount.setPno(pno);
    		productDiscount.setGradeNo(Integer.parseInt(discountGrade));
    		productDiscount.setDiscount(param.getInt("discount" + discountGrade));
    		sst.insert("Product.insertDiscount", productDiscount);
    	}
    }

    @Transactional
    public void modify(Product product, Param param) {
    	sst.insert("Product.update", product);
    	Integer pno = product.getPno();
    	
    	// category
    	sst.delete("Product.deleteCategory", pno);
    	String[] cateNos = param.getValues("cateNo");
    	for(String cateNo : cateNos) {
    		ProductCategory productCategory = new ProductCategory();
    		productCategory.setPno(pno);
    		productCategory.setCateNo(Integer.parseInt(cateNo));
    		sst.insert("Product.insertCategory", productCategory);
    	}

    	// grade 노출 등급
    	sst.delete("Product.deleteGrade", pno);
    	String[] gradeNos = param.getValues("gradeNo");
    	for(String gradeNo : gradeNos) {
    		ProductGrade productGrade = new ProductGrade();
    		productGrade.setPno(pno);
    		productGrade.setGradeNo(Integer.parseInt(gradeNo));
    		sst.insert("Product.insertGrade", productGrade);
    	}
    	
    	// tag
    	sst.delete("Product.deleteTag", pno);
    	if(StringUtils.isNotEmpty(param.get("tag"))) {
	    	String[] tags = param.get("tag").split(",");
	    	for(String tag : tags) {
	    		ProductTag productTag = new ProductTag();
	    		productTag.setPno(pno);
	    		productTag.setTag(tag);
	    		sst.insert("Product.insertTag", productTag);
	    	}
    	}
    	
    	// icon 제품유형
    	sst.delete("Product.deleteIcon", pno);
    	String[] iconNos = param.getValues("iconNo");
    	for(String iconNo : iconNos) {
    		ProductIcon productIcon = new ProductIcon();
    		productIcon.setPno(pno);
    		productIcon.setIconNo(Integer.parseInt(iconNo));
    		sst.insert("Product.insertIcon", productIcon);
    	}
    	
    	// gift 증정품
    	sst.delete("Product.deleteGift", pno);
    	String[] giftPnos = param.getValues("giftPno");
    	for(String giftPno : giftPnos) {
    		ProductGift productGift = new ProductGift();
    		productGift.setPno(pno);
    		productGift.setGiftPno(Integer.parseInt(giftPno));
    		sst.insert("Product.insertGift", productGift);
    	}
    	
    	// intake 추천 섭취대상
    	sst.delete("Product.deleteIntake", pno);
    	String[] intakeNos = param.getValues("intakeNo");
    	for(int i = 0; i < intakeNos.length; i++) {
    		ProductIntake productIntake = new ProductIntake();
    		productIntake.setPno(pno);
    		productIntake.setIntakeNo(Integer.parseInt(intakeNos[i]));
    		productIntake.setRank(i);
    		sst.insert("Product.insertIntake", productIntake);
    	}
    	
    	// dose 복용안내
    	sst.delete("Product.deleteDose", pno);
    	String[] doseNos = param.getValues("doseNo");
    	for(int i = 0; i < doseNos.length; i++) {
    		ProductDose productDose = new ProductDose();
    		productDose.setPno(pno);
    		productDose.setDoseNo(Integer.parseInt(doseNos[i]));
    		productDose.setRank(i);
    		sst.insert("Product.insertDose", productDose);
    	}
    	
    	// nutrition 영양/기능 정보
    	sst.delete("Product.deleteNutrition", pno);
    	String[] nutritionNos = param.getValues("nutritionNo");
    	for(int i = 0; i < nutritionNos.length; i++) {
    		ProductNutrition productNutrition = new ProductNutrition();
    		productNutrition.setPno(pno);
    		productNutrition.setNutritionNo(Integer.parseInt(nutritionNos[i]));
    		productNutrition.setStandard(param.get("nutritionStandard" + nutritionNos[i]));
    		productNutrition.setContent(param.get("nutritionContent" + nutritionNos[i]));
    		productNutrition.setRank(i);
    		sst.insert("Product.insertNutrition", productNutrition);
    	}
    	
    	// option 옵션제품
    	sst.delete("Product.deleteOption", pno);
    	String[] optPnos = param.getValues("optPno");
    	for(int i = 0; i < optPnos.length; i++) {
    		ProductOption productOption = new ProductOption();
    		productOption.setPno(pno);
    		productOption.setOptPno(Integer.parseInt(optPnos[i]));
    		productOption.setOptNm(param.get("optNm" + optPnos[i]));
    		productOption.setRank(i);
    		sst.insert("Product.insertOption", productOption);
    	}
    	
    	// discount 등급별 할인
    	sst.delete("Product.deleteDiscount", pno);
    	String[] discountGrades = param.getValues("discountGrade");
    	for(String discountGrade : discountGrades) {
    		ProductDiscount productDiscount = new ProductDiscount();
    		productDiscount.setPno(pno);
    		productDiscount.setGradeNo(Integer.parseInt(discountGrade));
    		productDiscount.setDiscount(param.getInt("discount" + discountGrade));
    		sst.insert("Product.insertDiscount", productDiscount);
    	}
    }
}
