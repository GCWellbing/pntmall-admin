<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>

function goSubmit() {
	if($("#attach").val() == ""){
		alert("파일을 업로드 하세요");
		return false;
	}

	disableScreen();
	ef.proc($("#editForm"), function(result) {
		alert(result.message);
		if(result.succeed) location.reload();
		enableScreen();
	});
}


function uploadImageAll(field) {
	var fileName = $("input[name=" + field + "]").val()
	var orgFilename = fileName.split('/').pop().split('\\').pop();

	if($("input[name=" + field + "]").val() != '') {
        disableScreen();

        var action = "/upload/upload?field=" + field + "&path=agreeDocu&fileType=all";
        ef.multipart($("#editForm"), action, field, function(result) {

        	if(result.succeed) {
        		var s = field.replace(/File/gi, '');
        		$("input[name=attach]").val(result.param.uploadUrl);
        		$("input[name=attachOrgName]").val(orgFilename);
        		$("#attachBtn").val(orgFilename);
        		$("#attachBtn").removeAttr("onclick");
        		$("#attachBtn").attr("onclick","download('"+orgFilename+"','"+result.param.uploadUrl+"'); return false;");
        		$("#attachBtn").show();
        	} else {
        		alert(result.message);
        	}

        	$("input[name=" + field + "]").val("");
        	enableScreen();
        });
	}
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
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="">
				</colgroup>
				<tr>
					<td>
						<input type="file" name="attachFile" onchange="uploadImageAll('attachFile')">
						<p class="txt">* 첨부서류는 최대 5Mbyte까지 업로드 가능합니다.<br>* 업로드 가능한 확장자: jpg, jpeg, gif, png, bmp, pdf, zip</p>
						<input type='button' id="attachBtn" class='btnSizeC' onclick='download("${agree.attachOrgName}","${agree.attach}"); return false;' value='${agree.attachOrgName}' style="display:${ !empty agree.attach ? 'block' : 'none' }">
						<input id="attach" type='hidden' name='attach' value='${agree.attach}'>
						<input id="attachOrgName" type='hidden' name='attachOrgName' value='${agree.attachOrgName}'>
					</td>
				</tr>
			</table>
		</div>
		<div class="btnArea">
			<c:if test="${ mode eq 'create' }">
				<a href="javascript:goSubmit()" class="btnTypeC btnSizeA"><span>등록</span></a>
			</c:if>
		</div>
		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->


</body>
</html>
