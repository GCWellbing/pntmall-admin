<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="utils" scope="request" class="com.pntmall.common.utils.Utils"/>
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

	function goCommentSubmit() {
			disableScreen();
			ef.proc($("#editCommentForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.reload();
				enableScreen();
			});
	}

	function setStatus(bbsNo,commentNo,status) {

		if(v.validate()) {
			disableScreen();
			$.ajax({
				method : "POST",
				url : "setStatusBbsComment",
				data : { "bbsNo" : bbsNo
					    ,"commentNo" : commentNo
					    , "status" : status },
				dataType : "json"
			})
			.done(function(result) {
				alert(result.message);
				if(result.succeed) location.href = "edit?bbsNo="+bbsNo;
				enableScreen();
			});
		}
	}

	function goDownload(){
		window.location.href = "downloadExcel?bbsNo=${ bbs.bbsNo }";
	}

	function commentResultChg(commentNo){
		$("#commentResult"+commentNo).hide();
		$("#commentResultEdit"+commentNo).show();
	}

	function cancelComment(commentNo){
		$("#commentResult"+commentNo).show();
		$("#commentResultEdit"+commentNo).hide();
	}

	function updateComment(bbsNo, commentNo){
		if(confirm("수정하시겠습니까?")){
			$.ajax({
				method : "POST",
				url : "updateComment",
				data : { "bbsNo" : bbsNo
					    ,"commentNo" : commentNo
					    ,"comment" : $("#comment"+commentNo).val() },
				dataType : "json"
			})
			.done(function(result) {
				alert(result.message);
				if(result.succeed) location.reload();
				enableScreen();
			});
		}
	}

	function deleteComment(bbsNo, commentNo){
		if(confirm("삭제하시겠습니까?")){
			$.ajax({
				method : "POST",
				url : "deleteComment",
				data : { "bbsNo" : bbsNo
					    ,"commentNo" : commentNo
					    ,"status" : "D" },
				dataType : "json"
			})
			.done(function(result) {
				alert(result.message);
				if(result.succeed) location.reload();
				enableScreen();
			});
		}
	}

	function uploadImageAll(field) {
		var fileName = $("input[name=" + field + "]").val();
		var orgFilename = fileName.split('/').pop().split('\\').pop();

		if(!fileCheck()){
			return false;
		}

		if($("input[name=" + field + "]").val() != '') {
	        disableScreen();

	        var action = "/upload/upload?field=" + field + "&path=clinicBbs&fileType=all";
	        ef.multipart($("#editForm"), action, field, function(result) {

	        	if(result.succeed) {
	        		var fieldName = field.replace(/File/gi, '');
	        		var gubun = fieldName.replace(/Img/gi, '');

	        		var html = "";
					html += "<div>";
					html += "	<input type='hidden' name='attach' value='"+result.param.uploadUrl+"'>";
					html += "	<input type='hidden' name='attachOrgName' value='"+orgFilename+"'>";
					html += "	<input type='button' class='btnSizeC' onclick='download(\""+orgFilename+"\",\""+result.param.uploadUrl+"\"); return false;' value='"+orgFilename+"'>";
					html += "	<input type='button' class='btnSizeC' onclick='removeBbsImg(this); return false;' value='삭제'>";
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
	function fileCheck() {
	    if(document.getElementById("bbsImgFile").value!=""){
	        var fileSize = document.getElementById("bbsImgFile").files[0].size;
	        var maxSize = 5 * 1024 * 1024;//2MB

	        if(fileSize > maxSize){
	           alert("첨부파일 사이즈는 5MB 이내로 등록 가능합니다. ");
	           return false;
	        }
        	return true;
		}
	}

	function removeBbsImg(obj) {
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
			<input type="hidden" name="bbsNo" value="${ bbs.bbsNo }" />
		<h2>정보 등록</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>분류<sup>*</sup></th>
					<td>
						<select name="cate" style="width:200px">
							<c:forEach items="${ cateList }" var="row">
								<option value="${ row.code1 }${ row.code2 }" ${ bbs.cate eq row.code1+=row.code2 ? 'selected' : '' }>${ row.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>제목<sup>*</sup></th>
					<td>
						<input type="text" name="title" id="title" value="${ bbs.title }" style="width:200px" maxlength="4000">
					</td>
				</tr>
				<tr>
					<th>내용<sup>*</sup></th>
					<td>
						<textarea name="content" id="content" style="width:100%;height:100px">${ bbs.content }</textarea>
					</td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td>
						<div id="bbsImg">
							<input type="file" name="bbsImgFile" id="bbsImgFile" onchange="uploadImageAll('bbsImgFile')">
							<p class="txt">* 최대 5Mbyte / 확장자 : jpg, jpeg, gif, png, bmp, pdf, ppt, pptx, xlsx, xls, doc, docx</p>

							<c:forEach items="${ bbsImgList }" var="row">
								<div>
									<input type='hidden' name='attach' value='${row.attach}'>
									<input type='hidden' name='attachOrgName' value='${row.attachOrgName}'>
									<input type='button' class='btnSizeC' onclick='download("${row.attachOrgName}","${row.attach}"); return false;' value='${row.attachOrgName}'>
									<input type='button' class='btnSizeC' onclick='removeBbsImg(this); return false;' value='삭제'>
								</div>
							</c:forEach>
						</div>
					</td>
				</tr>
				<tr>
					<th>상단고정</th>
					<td>
						<input type="checkbox" name="fixYn" id="fixYn" value="Y" ${ bbs.fixYn eq 'Y' ? 'checked':'' } ><span>상단고정</span>
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
						<label><input type="radio" name="status" value="S" ${ bbs.status eq 'S' || empty bbs.status ? 'checked' : '' }><span>공개</span></label>
						<label><input type="radio" name="status" value="H" ${ bbs.status eq 'H' ? 'checked' : '' }><span>비공개</span></label>
					</td>
					<th>등록일/등록자</th>
					<td><fmt:formatDate value="${ bbs.cdate }" pattern="${ DateTimeFormat }" /> / ${bbs.cuserId}
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

		<c:if test="${ mode ne 'create' }">
			<h2><fmt:formatNumber value="${ fn:length(bbsCommentList) }" pattern="#,###" />개의 댓글</h2>
			<div class="white_box">
				<form name="editCommentForm" id="editCommentForm" method="POST" action="insertComment">
					<input type="hidden" name="bbsNo" value="${ bbs.bbsNo }" />
				<table class="board">
					<colgroup>
						<col width="15%">
						<col width="">
						<col width="15%">
					</colgroup>
					<tr>
						<th>댓글<sup>*</sup></th>
						<td>
							<textarea name="comment" id="comment" style="width:100%;height:100px"></textarea>
						</td>
						<td><a href="javascript:goCommentSubmit()" class="btnTypeC btnSizeA"><span>등록</span></a></td>
					</tr>
				</table>
				</form>
			</div>
		</c:if>

		<table class="board">
			<form name="editCommentForm" id="editCommentForm" method="POST" action="insertComment">
				<input type="hidden" name="bbsNo" value="${ bbs.bbsNo }" />
			<colgroup>
				<col width="">
				<col width="200px">
			<tbody>
				<c:forEach items="${ bbsCommentList }" var="row">
					<tr id="commentResult${ row.commentNo }">
						<td>
							${ utils.idMasking(row.cuserId) } (<fmt:formatDate value="${row.cdate}" pattern="${ DateTimeFormat }"/>)<br>
							<pre><c:out value="${ row.comment }" /></pre>
						</td>
						<td>
							<a href="#" onclick="commentResultChg('${ row.commentNo }');return false;" class="btnTypeC btnSizeA"><span>수정</span></a>
							<a href="#" onclick="deleteComment('${ row.bbsNo }','${ row.commentNo }');return false;" class="btnTypeC btnSizeA"><span>삭제</span></a>
						</td>
					</tr>
					<tr id="commentResultEdit${ row.commentNo }" style="display:none;">
						<td>
							<c:out value="${ utils.idMasking(row.cuserId) }" /> (<fmt:formatDate value="${row.cdate}" pattern="${ DateTimeFormat }"/>)<br>
							<textarea name="comment" id="comment${ row.commentNo }" style="width:100%;height:100px">${ row.comment }</textarea>
						</td>
						<td>
							<a href="#" onclick="updateComment('${ row.bbsNo }','${ row.commentNo }');return false;" class="btnTypeC btnSizeA"><span>저장</span></a>
							<a href="#" onclick="cancelComment('${ row.commentNo }');return false;" class="btnTypeC btnSizeA"><span>취소</span></a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
			</form>
		</table>
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
