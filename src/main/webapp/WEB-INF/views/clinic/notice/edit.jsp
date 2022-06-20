<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script type="text/javascript" src="/static/se2/js/HuskyEZCreator.js" charset="utf-8"></script>
<script>
	var v;

	$(function() {
		v = new ef.utils.Validator($("#editForm"));
		v.add("title", {
			empty : "제목을 입력하세요.",
			max : "200"
		});

	});

	function goSubmit() {
		oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);

		if(v.validate()) {
			disableScreen();
			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list";
				enableScreen();
			});
		}
	}

	function setStatus(noticeNo,commentNo,status) {

		if(v.validate()) {
			disableScreen();
			$.ajax({
				method : "POST",
				url : "setStatusNoticeComment",
				data : { "noticeNo" : noticeNo
					    ,"commentNo" : commentNo
					    , "status" : status },
				dataType : "json"
			})
			.done(function(result) {
				alert(result.message);
				if(result.succeed) location.href = "edit?noticeNo="+noticeNo;
				enableScreen();
			});
		}
	}

	function goDownload(){
		window.location.href = "downloadExcel?noticeNo=${ notice.noticeNo }";
	}


	function uploadImageAll(field) {
		var fileName = $("input[name=" + field + "]").val();
		var orgFilename = fileName.split('/').pop().split('\\').pop();

		if($("input[name=" + field + "]").val() != '') {
	        disableScreen();

	        var action = "/upload/upload?field=" + field + "&path=clinicNotice&fileType=all";
	        ef.multipart($("#editForm"), action, field, function(result) {

	        	if(result.succeed) {
	        		var fieldName = field.replace(/File/gi, '');
	        		var gubun = fieldName.replace(/Img/gi, '');

	        		var html = "";
					html += "<div>";
					html += "	<input type='hidden' name='attach' value='"+result.param.uploadUrl+"'>";
					html += "	<input type='hidden' name='attachOrgName' value='"+orgFilename+"'>";
					html += "	<input type='button' class='btnSizeC' onclick='download(\""+orgFilename+"\",\""+result.param.uploadUrl+"\"); return false;' value='"+orgFilename+"'>";
					html += "	<input type='button' class='btnSizeC' onclick='removeClinicNoticeImg(this); return false;' value='삭제'>";
					html += "</div>";
					$("#"+fieldName).append(html);
	        	} else {
	        		alert(result.message);
	        	}

	        	$("input[name=" + field + "]").val("");
	        	enableScreen();
	        });
		}
	}

	function removeClinicNoticeImg(obj) {
		$(obj).parent().remove();
	}

    function download(filename, textInput) {
    	location.href = "/upload/downloadFile?attach="+textInput+"&attachOrgName="+filename;
  }

</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="noticeNo" value="${ notice.noticeNo }" />
		<h2>정보 등록</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>제목<sup>*</sup></th>
					<td>
						<input type="text" name="title" id="title" value="${ notice.title }" style="width:200px" maxlength="4000">
					</td>
				</tr>
				<tr>
					<th>내용<sup>*</sup></th>
					<td>
						<textarea name="content" id="content" style="width:100%;height:100px">${ notice.content }</textarea>
					</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td>
						<div id="noticeImg">
							<input type="file" name="noticeImgFile" onchange="uploadImageAll('noticeImgFile')">
							<p class="txt">* 최대 5Mbyte / 확장자 : jpg, jpeg, gif, png, bmp, pdf, ppt, pptx, xlsx, xls, doc, docx</p>

							<c:forEach items="${ noticeImgList }" var="row">
								<div>
									<input type='hidden' name='attach' value='${row.attach}'>
									<input type='hidden' name='attachOrgName' value='${row.attachOrgName}'>
									<input type='button' class='btnSizeC' onclick='download("${row.attachOrgName}","${row.attach}"); return false;' value='${row.attachOrgName}'>
									<input type='button' class='btnSizeC' onclick='removeClinicNoticeImg(this); return false;' value='삭제'>
								</div>
							</c:forEach>
						</div>
					</td>
				</tr>
				<tr>
					<th>상단고정</th>
					<td>
						<input type="checkbox" name="fixYn" id="fixYn" value="Y" ${ notice.fixYn eq 'Y' ? 'checked':'' } ><span>상단고정</span>
					</td>
				</tr>
			</table>
		</div>

		<h2>메뉴권한</h2>
		<div class="white_box">
			<table class="board">
				<tr>
					<th>공개여부<sup>*</sup></th>
					<td>
						<label><input type="radio" name="status" value="S" ${ notice.status eq 'S' || empty notice.status ? 'checked' : '' }><span>공개</span></label>
						<label><input type="radio" name="status" value="H" ${ notice.status eq 'H' ? 'checked' : '' }><span>비공개</span></label>
					</td>
					<th>등록일</th>
					<td><fmt:formatDate value="${ notice.cdate }" pattern="${ DateTimeFormat }" />
					</td>
					<th>수정일</th>
					<td><fmt:formatDate value="${notice.udate}" pattern="${ DateTimeFormat }"/>
					</td>
				</tr>
			</table>
		</div>

		<div class="btnArea">
			<span class="fl">
				<a href="${ retrivePath }" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
			<c:if test="${ mode eq 'create' }">
				<a href="javascript:goSubmit()" class="btnTypeC btnSizeA"><span>등록</span></a>
			</c:if>
			<c:if test="${ mode eq 'modify' && updateAuth eq 'Y' }">
				<a href="javascript:goSubmit()" class="btnTypeC btnSizeA"><span>수정</span></a>
			</c:if>
		</div>
		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

<script type="text/javascript">
	var oEditors = [];

	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "content",
		sSkinURI: "/static/se2/SmartEditor2Skin.html",
		htParams : {
	          // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
	          bUseToolbar : true,
	          // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
	          bUseVerticalResizer : true,
	          // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
	          bUseModeChanger : true,
	          bSkipXssFilter : true,
	          fOnBeforeUnload : function(){}
	      },
		fCreator: "createSEditorInIFrame"
	});
</script>

</body>
</html>
