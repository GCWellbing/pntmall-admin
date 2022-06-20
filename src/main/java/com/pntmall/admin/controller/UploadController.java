package com.pntmall.admin.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pntmall.common.ResultMessage;
import com.pntmall.common.azure.StorageUploader;
import com.pntmall.common.type.Param;

@Controller
@RequestMapping("/upload/")
public class UploadController {
    public static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private StorageUploader storageUploader;

    @PostMapping("/se2Upload")
    public String upload(MultipartHttpServletRequest request) {
        String return1 = request.getParameter("callback");
        String return2 = "?callback_func=" + request.getParameter("callback_func");
        String return3 = "";
        String name = "";
    	String uploadUrl = null;

        MultipartFile mFile = request.getFile("Filedata");
        try {
            if(mFile != null && mFile.getOriginalFilename() != null && !mFile.getOriginalFilename().equals("")) {
                name = mFile.getOriginalFilename().substring(mFile.getOriginalFilename().lastIndexOf(File.separator) + 1);
                String filename_ext = name.substring(name.lastIndexOf(".") + 1);
                filename_ext = filename_ext.toLowerCase();
                String[] allow_file = {"jpg", "png", "bmp", "gif"};
                int cnt = 0;
                for(int i = 0; i < allow_file.length; i++) {
                    if (filename_ext.equals(allow_file[i])) {
                        cnt++;
                    }
                }
                if(cnt == 0) {
                    return3 = "&errstr=" + name;
                } else {
                	storageUploader.initStorageUploder(request);
                	storageUploader.setPassibleExts(StorageUploader.IMAGE_EXTS);
                	uploadUrl = storageUploader.upload(mFile, "se2");

                    return3 += "&bNewLine=true";
                    return3 += "&sFileName=" + URLEncoder.encode(name, "utf-8");
                    return3 += "&sFileURL=" + uploadUrl;
                }
            } else {
                return3 += "&errstr=error";
            }
        } catch(Exception e) {
            logger.error("upload", e);
        }

        logger.debug("----- smarteditor upload : " + return1 + return2 + return3);
        return "redirect:" + return1 + return2 + return3;
    }

    @PostMapping("/upload")
	@ResponseBody
	public ResultMessage upload(
			MultipartHttpServletRequest request,
			@RequestParam("path") String path,
			@RequestParam("field") String field,
			@RequestParam("fileType") String fileType) {
		Param param = new Param();
		String uploadUrl = null;

		try {
        	storageUploader.initStorageUploder(request);
        	if("image".equals(fileType)) {
        		storageUploader.setPassibleExts(StorageUploader.IMAGE_EXTS);
        	} else if("doc".equals(fileType)) {
        		storageUploader.setPassibleExts(StorageUploader.DOC_EXTS);
        	} else{
        		storageUploader.setPassibleExts(StorageUploader.FILE_EXTS);

        	}
        	uploadUrl = storageUploader.upload(field, path);

        	logger.debug("----------------- " + uploadUrl);
			if(uploadUrl == null) {
				return new ResultMessage(false, "업로드중 오류가 발생했습니다.");
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, e.getMessage());
		}

		param.set("uploadUrl", uploadUrl);
		return new ResultMessage(true, "", param);
	}

    @PostMapping("/uploadImage")
	@ResponseBody
	public ResultMessage uploadImage(
			MultipartHttpServletRequest request,
			@RequestParam("path") String path,
			@RequestParam("field") String field) {
		return upload(request, path, field, "image");
	}

	@RequestMapping("/multiUpload")
	@ResponseBody
	public ResultMessage multiUpload(
			MultipartHttpServletRequest request,
			@RequestParam("path") String path,
			@RequestParam("field") String field,
			@RequestParam("fileType") String fileType) {
		Param param = new Param();
		List<String> list = new ArrayList<String>();

		try {
        	storageUploader.initStorageUploder(request);
        	if("image".equals(fileType)) {
        		storageUploader.setPassibleExts(StorageUploader.IMAGE_EXTS);
        	} else {
        		storageUploader.setPassibleExts(StorageUploader.FILE_EXTS);
        	}

			List<MultipartFile> files = request.getFiles(field);

			for(MultipartFile file : files) {
				String uploadUrl = storageUploader.upload(file, path);
				if(uploadUrl == null) {
					return new ResultMessage(false, "업로드중 오류가 발생했습니다.");
				}

	        	logger.debug("----------------- " + uploadUrl);
				list.add(uploadUrl);
			}

		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return new ResultMessage(false, e.getMessage());
		}

		param.set("uploadUrls", list);
		return new ResultMessage(true, "", param);
	}

	@RequestMapping("/multiUploadImage")
	@ResponseBody
	public ResultMessage multiUploadImage(
			MultipartHttpServletRequest request,
			@RequestParam("path") String path,
			@RequestParam("field") String field) {
		return multiUpload(request, path,field, "image");
	}

    @RequestMapping("/downloadFile")
    public void  fileDownload(HttpServletRequest request, HttpServletResponse response) {
    	try {
    		Param param = new Param(request.getParameterMap());

        	storageUploader.fileDown(request, response, param.get("attach"), param.get("attachOrgName")); //파일다운로드

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
		}
    }

}
