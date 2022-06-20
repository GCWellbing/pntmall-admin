package com.pntmall.admin.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pntmall.admin.domain.BankCard;
import com.pntmall.admin.domain.ClinicAdjust;
import com.pntmall.admin.domain.PaymentLog;
import com.pntmall.admin.domain.PgBill;
import com.pntmall.admin.domain.SapVendor;
import com.pntmall.admin.domain.SapVendorOut;
import com.pntmall.common.type.Param;
import com.pntmall.common.utils.Utils;
import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;

@Service
public class SapService {
    public static final Logger logger = LoggerFactory.getLogger(SapService.class);

	public static String DESTINATION_NAME1 = "ABAP_AS_WITHOUT_POOL";
	public static String DESTINATION_NAME2 = "ABAP_AS_WITH_POOL";

	@Value("${jco.ashost}")
    String jcoAshost;

    @Value("${jco.sysnr}")
    String jcoSysnr;

    @Value("${jco.client}")
    String jcoClient;

    @Value("${jco.lang}")
    String jcoLang;

    @Value("${jco.user}")
    String jcoUser;

    @Value("${jco.passwd}")
    String jcoPasswd;
    
    @Autowired
    ClinicAdjustService clinicAdjustService;

    @Autowired
    PgBillService pgBillService;

    @Autowired
    OrderService orderService;

    @Autowired
    CodeService codeService;

    private void init() {
    	logger.debug("------------------------------ " + jcoAshost);

    	Properties connectProperties = new Properties();
		connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, jcoAshost);
		connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, jcoSysnr);
		connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, jcoClient);
		connectProperties.setProperty(DestinationDataProvider.JCO_LANG, jcoLang);
		connectProperties.setProperty(DestinationDataProvider.JCO_USER, jcoUser);
		connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, jcoPasswd);
		createDestinationDataFile(DESTINATION_NAME1, connectProperties);

		connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, "10");
        createDestinationDataFile(DESTINATION_NAME2, connectProperties);
    }

	private void createDestinationDataFile(String destinationName, Properties connectProperties) {
		String fileName = destinationName + ".jcoDestination";
    	if(!"local".equals(Utils.getActiveProfile())) {
    		fileName = "/opt/tomcat_main/" + fileName;
    	}

    	File destCfg = new File(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(destCfg, false);
            connectProperties.store(fos, "for tests only !");
            fos.close();
        }
        catch (Exception e) {
        	logger.error("error", e);
            throw new RuntimeException("Unable to create the destination files", e);
        }
    }

	public SapVendorOut setVendor(SapVendor sapVendor) throws RuntimeException {
		logger.info(this.getClass().getName() + " Schedule Start");
		SapVendorOut sapVendorOut = new SapVendorOut();

		this.init();
		JCoDestination destination = null;
        JCoFunction function = null;

        try {
	    	destination = JCoDestinationManager.getDestination((!"local".equals(Utils.getActiveProfile()) ? "/opt/tomcat_main/" : "") + DESTINATION_NAME2);
	    	JCoContext.begin(destination);
	    	function = destination.getRepository().getFunction("ZFI_B2C_V_C");
	    	if(function == null) {
	    		throw new RuntimeException("no function");
	    	}

	    	JCoStructure isList = function.getImportParameterList().getStructure("IS_LIST");
	    	isList.setValue("VD_NM", sapVendor.getVdNm());
	    	isList.setValue("POST_NO", sapVendor.getPostNo());
	    	isList.setValue("DTL_ADDR_1", sapVendor.getDtlAddr1());
	    	isList.setValue("DTL_ADDR_2", sapVendor.getDtlAddr2());
	    	isList.setValue("NAT_CD", sapVendor.getNatCd());
	    	isList.setValue("PHONE_NO", sapVendor.getPhoneNo());
	    	isList.setValue("FAX_NO", sapVendor.getFaxNo());
	    	isList.setValue("EMAIL", sapVendor.getEmail());
	    	isList.setValue("BIZ_REG_NO", sapVendor.getBizRegNo());
	    	isList.setValue("BIZ_REG_NO2", sapVendor.getBizRegNo2());
	    	isList.setValue("CORP_REG_NO", sapVendor.getCorpRegNo());
	    	isList.setValue("REP_NM", sapVendor.getRepNm());
	    	isList.setValue("BOS", sapVendor.getBos());
	    	isList.setValue("TOB", sapVendor.getTob());
	    	isList.setValue("BANKL", sapVendor.getBankl());
	    	isList.setValue("BANKN", sapVendor.getBankn());
	    	isList.setValue("KOINH", sapVendor.getKoinh());

	    	logger.info("------------ isList " + isList);
	    	function.execute(destination);

	        // RFC 호출 결과
	    	logger.debug("----------------- " + function.getExportParameterList());
	    	JCoParameterList list =function.getExportParameterList();
	    	for(JCoField field : list) {
	    		logger.info("------ field name : " + field.getName() + ", " + field.getString());
	    	}

	    	JCoParameterList list2 = function.getExportParameterList();
		    	logger.debug("----------------- " + list2);
	    	JCoStructure master = list2.getStructure("ES_OUTPUT");
	    	logger.debug("----------------- ES_OUTPUT:::" + master);

        	sapVendorOut.setPntResult(master.getString("PNT_RESULT"));
        	sapVendorOut.setPntMsg(master.getString("PNT_MSG"));
        	sapVendorOut.setPntVdrCd(master.getString("PNT_VDR_CD"));
        	sapVendorOut.setPntVdrNm(master.getString("PNT_VDR_NM"));
        	sapVendorOut.setPntCtrCd(master.getString("PNT_CTR_CD"));
        	sapVendorOut.setPntCtrNm(master.getString("PNT_CTR_NM"));
        	sapVendorOut.setPntBizRegNo(master.getString("PNT_BIZ_REG_NO"));

        } catch(Exception e) {
        	logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
            try { JCoContext.end(destination); } catch(Exception e) {}
            if(function != null) function = null;
            if(destination != null) destination = null;
        }
        logger.info("sapVendorOut :" + sapVendorOut);
		return sapVendorOut;
	}

	
	public void fee(Param param) {
		List<ClinicAdjust> list = clinicAdjustService.getListForSap(param);
		
		this.init();
		JCoDestination destination = null;
        JCoFunction function = null;

        try {
	    	destination = JCoDestinationManager.getDestination((!"local".equals(Utils.getActiveProfile()) ? "/opt/tomcat_main/" : "") + DESTINATION_NAME2);
	    	JCoContext.begin(destination);
	    	function = destination.getRepository().getFunction("ZFI_B2C_FEE");
	    	if(function == null) {
	    		throw new RuntimeException("no function");
	    	}
	    	
	    	String idate = Utils.getTimeStampString("yyyyMMdd");
	    	function.getImportParameterList().setValue("IS_INPUT", idate);

	    	JCoTable table = function.getTableParameterList().getTable("IT_LIST");

	        for(ClinicAdjust clinicAdjust : list) {
//	        	logger.debug(clinicAdjust.toString());
	        	table.appendRow();
	        	table.setValue("BKTXT", "PNT몰 병의원지급수수료");
	        	table.setValue("BIZ_REG_NO", StringUtils.isNotEmpty(clinicAdjust.getBusinessNo2()) ? clinicAdjust.getBusinessNo2() : clinicAdjust.getBusinessNo());
	        	table.setValue("IF_KEY", clinicAdjust.getSno());
	        	table.setValue("PNT_PRICE", clinicAdjust.getTotSaleAmt());
	        	table.setValue("PNT_SUPPLY", clinicAdjust.getTotSupplyAmt());
	        	table.setValue("APPRAMT", clinicAdjust.getTotSaleAmt());
	        	table.setValue("FEE_PICKUP_AMT", clinicAdjust.getAdjustAmt());
			}
	        
	        logger.info("-------- fee table row : " + table.getNumRows());
	        logger.info("-------- fee table : " + table);

	    	function.execute(destination);

	    	JCoTable output = function.getTableParameterList().getTable("ET_OUTPUT");
	    	logger.info("---------- fee output : " + output);

	    	output.firstRow();
	        do {
	        	try {
		        	ClinicAdjust clinicAdjust = new ClinicAdjust();
		        	clinicAdjust.setSno(Integer.parseInt(output.getString("IF_KEY")));
		        	clinicAdjust.setSapResult(output.getString("RESULT"));
		        	clinicAdjust.setSapMsg(output.getString("MSG"));
		        	clinicAdjust.setSapGjahr(output.getString("GJAHR"));
		        	clinicAdjust.setSapBelnr(output.getString("BELNR"));
		        	clinicAdjust.setSapBudat(output.getString("BUDAT"));
		        	clinicAdjust.setSapPntVdrCd(output.getString("PNT_VDR_CD"));
		        	clinicAdjust.setSapPntVdrNm(output.getString("PNT_VDR_NM"));
		        	clinicAdjust.setSapBizRegNo(output.getString("BIZ_REG_NO"));
		        	
		        	clinicAdjustService.modifySap(clinicAdjust);
	        	} catch(Exception e) {
	        		logger.error(e.getMessage(), e);
	        	}
	        } while(output.nextRow());

        } catch(Exception e) {
        	logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
            try { JCoContext.end(destination); } catch(Exception e) {}
            if(function != null) function = null;
            if(destination != null) destination = null;
        }
	}
	
	public void settlement(Param param) {
		List<PgBill> list = pgBillService.getListForSap(param.getValues("sno"));
		this.init();
		JCoDestination destination = null;
        JCoFunction function = null;

        try {
	    	destination = JCoDestinationManager.getDestination((!"local".equals(Utils.getActiveProfile()) ? "/opt/tomcat_main/" : "") + DESTINATION_NAME2);
	    	JCoContext.begin(destination);
	    	function = destination.getRepository().getFunction("ZFI_B2C_SETTLEMENT");
	    	if(function == null) {
	    		throw new RuntimeException("no function");
	    	}
	    	
	    	JCoTable table = function.getTableParameterList().getTable("IT_LIST");

	        for(PgBill pgBill : list) {
	        	table.appendRow();
	        	table.setValue("SAUPNO", StringUtils.isNotEmpty(pgBill.getBusinessNo2()) ? pgBill.getBusinessNo2() : pgBill.getBusinessNo());
	        	table.setValue("TRDATE", pgBill.getSaleDate());
	        	table.setValue("CARDNO", pgBill.getOrderid());
	        	table.setValue("APPNO", StringUtils.isNotEmpty(pgBill.getAuthNo()) ? pgBill.getAuthNo() : pgBill.getOrderid().substring(6));
	        	table.setValue("AMT", pgBill.getAmt());
	        	table.setValue("ACQDATE", pgBill.getPayDate());
	        	table.setValue("REALAMT", pgBill.getIssueAmt());
	        	table.setValue("FEEAMT1", 0);
	        	table.setValue("FEEAMT2", pgBill.getFee());
	        	table.setValue("IPGUMDATE", pgBill.getIssueDate());
	        	table.setValue("ACCNO", "34791003703805");
	        	table.setValue("IF_KEY", pgBill.getSno());
			}
	        
	        logger.info("-------- settlement table row : " + table.getNumRows());
	        logger.info("-------- settlement table : " + table);

	    	function.execute(destination);

	    	JCoTable output = function.getTableParameterList().getTable("ET_OUTPUT");
	    	logger.info("---------- settlement output : " + output);
	        
	        output.firstRow();
	        do {
	        	try {
		        	PgBill pgBill = new PgBill();
			        pgBill.setSno(Integer.parseInt(output.getString("IF_KEY")));
			        pgBill.setSapResult(output.getString("PNT_RESULT"));
			        pgBill.setSapMsg(output.getString("PNT_MSG"));
			        
			        pgBillService.modifySapResult(pgBill);
	        	} catch(Exception e) {
	        		logger.error(e.getMessage(), e);
	        	}
	        } while(output.nextRow());
	        
        } catch(Exception e) {
        	logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
            try { JCoContext.end(destination); } catch(Exception e) {}
            if(function != null) function = null;
            if(destination != null) destination = null;
        }
	}

	public void billCollection(int plogNo) throws Exception {
		logger.info(this.getClass().getName() + " Schedule Start");
		
		PaymentLog paymentLog = orderService.getPaymentLogInfoByNo(plogNo);
		
		this.init();
		JCoDestination destination = null;
        JCoFunction function = null;
		
        try {
	    	JSONParser parser = new JSONParser();
	    	JSONObject log = (JSONObject) parser.parse(paymentLog.getLog());

	    	destination = JCoDestinationManager.getDestination((!"local".equals(Utils.getActiveProfile()) ? "/opt/tomcat_main/" : "") + DESTINATION_NAME2);
	    	JCoContext.begin(destination);
	    	function = destination.getRepository().getFunction("ZFI_B2C_COLLECTION");
	    	if(function == null) {
	    		throw new RuntimeException("no function");
	    	}
	    	
	    	
	    	int pickup = paymentLog.getOrderGubun() == 4 ? 3 : (paymentLog.getOrderGubun() == 3 ? 2 : 1);
	    	String uzawe = "";
	    	String bankl = "";
	    	String zstmemb = "";
	    	String cardno = "";
	    	String zcompcd = "";
	    	String apprno = "";
	    	int appramt = paymentLog.getPayAmt();
	    	String bizRegNo = paymentLog.getBusinessNo2();

	    	if("013001".equals(paymentLog.getPayType())) {
				JSONObject card = (JSONObject) log.get("card");
				cardno = card.get("number").toString();
				BankCard bankCard = new BankCard();
				bankCard.setGubun(2);
				bankCard.setTossCd(card.get("company").toString());
				zcompcd = codeService.getSapCd(bankCard);
				apprno = card.get("approveNo").toString();

				if(paymentLog.getGubun() == 1) {
					uzawe = "04";
				} else {
					uzawe = "C4";
				}
    		} else if("013002".equals(paymentLog.getPayType())) {
				if(paymentLog.getGubun() == 1) {
					uzawe = "02";
				} else {
					uzawe = "C2";
				}
    		} else if("013003".equals(paymentLog.getPayType())) {
    			JSONObject va = (JSONObject) log.get("virtualAccount");
				BankCard bankCard = new BankCard();
				bankCard.setGubun(1);
				bankCard.setTossCd(va.get("bank").toString());
    			bankl = codeService.getSapCd(bankCard);
    			
				if(paymentLog.getGubun() == 1) {
					uzawe = "02";
				} else {
					uzawe = "C2";
				}
    		} else if("013005".equals(paymentLog.getPayType())) {
    			String payMethod = (String) log.get("payment_method_type");
    			if("CARD".equals(payMethod)) {
    				JSONObject card = (JSONObject) log.get("card_info");
    				apprno = card.get("approved_id").toString();

    				if(paymentLog.getGubun() == 1) {
    					uzawe = "04";
    				} else {
    					uzawe = "C4";
    				}
    			} else {
    				if(paymentLog.getGubun() == 1) {
    					uzawe = "02";
    				} else {
    					uzawe = "C2";
    				}
    			}
    		}
	    	
	    	JCoStructure isInput = function.getImportParameterList().getStructure("IS_INPUT");
	    	isInput.setValue("BUDAT", Utils.getTimeStampString(paymentLog.getCdate(), "yyyyMMdd"));
	    	isInput.setValue("BKTXT", "PNT몰 수금");
	    	isInput.setValue("PICKUP", pickup);
	    	isInput.setValue("ORDNO", paymentLog.getOrderid());
	    	isInput.setValue("BIZ_REG_NO", bizRegNo);
	    	isInput.setValue("UZAWE", uzawe);
	    	isInput.setValue("BANKL", bankl);
	    	isInput.setValue("ZSTMEMB", zstmemb);
	    	isInput.setValue("CARDNO", cardno);
	    	isInput.setValue("ZCOMPCD", zcompcd);
	    	isInput.setValue("APPRNO", apprno);
	    	isInput.setValue("APPRAMT", appramt);
	    	logger.info("------------ billCollection isInput " + isInput);
	    	
	    	function.execute(destination);

	        // RFC 호출 결과
	    	JCoParameterList list = function.getExportParameterList();
//	    	logger.debug("----------------- ExportParameterList : " + list);

	    	JCoStructure es = list.getStructure("ES_OUTPUT");
	    	logger.debug("----------------- billCollection es : " + es);

	    	paymentLog.setSapResult(es.getString("PNT_RESULT"));
	    	paymentLog.setSapMsg(es.getString("PNT_MSG"));
	    	orderService.modifyPaymentLogSap(paymentLog);
	    	
			logger.info(this.getClass().getName() + " Schedule End");
        } catch(Exception e) {
        	logger.error(e.getMessage(), e);
			throw e;
		} finally {
            try { JCoContext.end(destination); } catch(Exception e) {}
            if(function != null) function = null;
            if(destination != null) destination = null;
        }	
	}
	
}
