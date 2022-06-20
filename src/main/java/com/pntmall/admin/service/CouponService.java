package com.pntmall.admin.service;

import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base32;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pntmall.admin.domain.Coupon;
import com.pntmall.admin.domain.CouponGrade;
import com.pntmall.admin.domain.CouponMem;
import com.pntmall.admin.domain.CouponProduct;
import com.pntmall.admin.domain.CouponSearch;
import com.pntmall.admin.domain.CouponSerial;
import com.pntmall.admin.domain.CouponSerialSearch;
import com.pntmall.common.type.Param;

@Service
public class CouponService {
    public static final Logger logger = LoggerFactory.getLogger(CouponService.class);

    @Autowired
    private SqlSessionTemplate sst;

    @Autowired
    private SeqService seqService;

	private static String[] ALPHABET = {
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z", "2", "3", "4", "5",
			"6", "7"
		};

    private static char[] digits = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', '-', '*' // '.', '-'
        };

    private static String keyString = "0WWWPNTMALLCOM0";
    
    public List<Coupon> getList(CouponSearch couponSearch) {
    	return sst.selectList("Coupon.list", couponSearch);
    }

    public Integer getCount(CouponSearch couponSearch) {
    	return sst.selectOne("Coupon.count", couponSearch);
    }
    
    public Coupon getInfo(String couponid) {
    	return sst.selectOne("Coupon.info", couponid);
    }
    
    public List<CouponGrade> getGradeList(String couponid) {
    	return sst.selectList("Coupon.gradeList", couponid);
    }
    
    public List<CouponMem> getMemList(String couponid) {
    	return sst.selectList("Coupon.memList", couponid);
    }
    
    public List<CouponProduct> getProductList(String couponid) {
    	return sst.selectList("Coupon.productList", couponid);
    }
    
    @Transactional
    public void create(Coupon coupon, Param param) {
    	String couponid = seqService.getId();
    	coupon.setCouponid(couponid);
    	
    	sst.insert("Coupon.insert", coupon);

    	if(coupon.getTarget() == 1) {
	    	// grade 등급
	    	String[] gradeNos = param.getValues("gradeNo");
	    	for(String gradeNo : gradeNos) {
	    		CouponGrade couponGrade = new CouponGrade();
	    		couponGrade.setCouponid(couponid);
	    		couponGrade.setGradeNo(Integer.parseInt(gradeNo));
	    		sst.insert("Coupon.insertGrade", couponGrade);
	    	}
    	} else {
	    	// mem
	    	String[] memIds = param.get("memId").split("/");
	    	for(String memId : memIds) {
	    		CouponMem couponMem = new CouponMem();
	    		couponMem.setCouponid(couponid);
	    		couponMem.setMemId(memId);
	    		sst.insert("Coupon.insertMem", couponMem);
	    	}
    	}
    	
    	if(coupon.getGubun() == 1) {
	    	// product
	    	String[] pnos = param.getValues("pno");
	    	for(String pno : pnos) {
	    		CouponProduct couponProduct = new CouponProduct();
	    		couponProduct.setCouponid(couponid);
	    		couponProduct.setPno(Integer.parseInt(pno));
	    		sst.insert("Coupon.insertProduct", couponProduct);
	    	}
    	}
    }

    @Transactional
    public void modify(Coupon coupon, Param param) {
    	sst.insert("Coupon.update", coupon);
    	String couponid = coupon.getCouponid();
    	
    	if(coupon.getTarget() == 1) {
	    	// grade 등급
	    	sst.delete("Coupon.deleteGrade", couponid);
	    	String[] gradeNos = param.getValues("gradeNo");
	    	for(String gradeNo : gradeNos) {
	    		CouponGrade couponGrade = new CouponGrade();
	    		couponGrade.setCouponid(couponid);
	    		couponGrade.setGradeNo(Integer.parseInt(gradeNo));
	    		sst.insert("Coupon.insertGrade", couponGrade);
	    	}
    	} else {
	    	// mem
	    	sst.delete("Coupon.deleteMem", couponid);
	    	String[] memIds = param.get("memId").split("/");
	    	for(String memId : memIds) {
	    		CouponMem couponMem = new CouponMem();
	    		couponMem.setCouponid(couponid);
	    		couponMem.setMemId(memId);
	    		sst.insert("Coupon.insertMem", couponMem);
	    	}
    	}
    	
    	if(coupon.getGubun() == 1) {
	    	// product
	    	sst.delete("Coupon.deleteProduct", couponid);
	    	String[] pnos = param.getValues("pno");
	    	for(String pno : pnos) {
	    		CouponProduct couponProduct = new CouponProduct();
	    		couponProduct.setCouponid(couponid);
	    		couponProduct.setPno(Integer.parseInt(pno));
	    		sst.insert("Coupon.insertProduct", couponProduct);
	    	}
    	}
    }
    
    public List<CouponSerial> getSerialList(CouponSerialSearch couponSerialSearch) {
    	return sst.selectList("Coupon.serialList", couponSerialSearch);
    }

    public Integer getSerialCount(CouponSerialSearch couponSerialSearch) {
    	return sst.selectOne("Coupon.serialCount", couponSerialSearch);
    }

    public void createSerial(CouponSerial couponSerial, Integer qty) throws Exception {
    	for(int i = 0; i < qty; i++) {
    		sst.insert("Coupon.insertSerial", couponSerial);
    		int seq = couponSerial.getSeq();
    		Param p = makeSerial(seq);
    		
    		couponSerial.setSeq64(p.get("seq64"));
    		couponSerial.setSerial(p.get("serial"));
    		
    		sst.update("Coupon.updateSerial", couponSerial);
    	}
    }
    
    
	public Param makeSerial(int i) throws Exception {
		Param param = new Param();
		try {
			Base32 base32 = new Base32();
			String s = toUnsignedString(i, 6, 7);
			byte[] b = encrypt(s);
			String s1 = base32.encodeToString(b);
			String random = addCheckBits(s1);
			
			param.set("seq64", s);
			param.set("serial", random);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
		}
		
		return param;
	}
	
	private String addCheckBits(String s) {
		String bit1 = "";
		String bit2 = "";
		String bit3 = "";
		int index = 0;
		for(int i = 0; i < 12; i++) {
			String t = s.substring(i, i + 1);
			for(int j = 0; j < ALPHABET.length; j++) {
				if(t.equals(ALPHABET[j])) {
					index += j;
					break;
				}
			}
			
			if(i == 3) bit1 = ALPHABET[index % 32];
			else if(i == 7) bit2 = ALPHABET[index % 32];
			else if(i == 11) bit3 = ALPHABET[index % 32];
		}
		
//		return s.substring(0, 4) + "-" + s.substring(4, 8) + "-" + s.substring(8, 12) + "-" + s.substring(12, 13) + bit1 + bit2 + bit3;
		return s.substring(0, 4) + s.substring(4, 8) + s.substring(8, 12) + s.substring(12, 13) + bit1 + bit2 + bit3;
	}
	
	private byte[] encrypt(String in) throws Exception {
		DESKeySpec keySpec = new DESKeySpec(keyString.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(keySpec);
		
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(in.getBytes());
	}

	/**
	 * 진수변환
	 * @param i 변환할 숫자
	 * @param shift 2 ^ shift 진수
	 * @param length 
	 * @return
	 */
	private String toUnsignedString(long l, int shift, int length) {
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << shift;
        long mask = radix - 1;
        long number = l;
        do {
            buf[--charPos] = digits[(int) (number & mask)];
            number >>>= shift;
        } while (number != 0);
        String s = new String(buf, charPos, (64 - charPos));
        
        if(length != 0) {
        	int len = s.length();
        	for(int i = 0; i < length - len; i++) {
        		s = "0" + s;
        	}
        }
        
        return s;
    }
	
	public List<Coupon> getLogList(CouponSearch couponSearch) {
		return sst.selectList("Coupon.logList", couponSearch);
	}
	
	public Integer getLogCount(CouponSearch couponSearch) {
		return sst.selectOne("Coupon.logCount", couponSearch);
	}
}