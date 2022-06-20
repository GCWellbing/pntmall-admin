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
		v.add("nutritionNo", {
			empty : "원료를 입력하세요."
		});
		v.add("img", {
			empty : "이미지를 입력하세요."
		});
		v.add("content", {
			empty : "설명을 입력하세요."
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


	function uploadImage(field) {
		if($("input[name=" + field + "]").val() != '') {
	        disableScreen();

	        var action = "/upload/upload?field=" + field + "&path=ingredient&fileType=image";
	        ef.multipart($("#editForm"), action, field, function(result) {

	        	if(result.succeed) {
	        		var s = field.replace(/File/gi, '');
	        		$("input[name=" + s + "]").val(result.param.uploadUrl);
	        		$("#" + s + "Tag").attr("src", result.param.uploadUrl);
	        		$("#" + s + "Tag").show();
	        	} else {
	        		alert(result.message);
	        	}

	        	$("input[name=" + field + "]").val("");
	        	enableScreen();
	        });
		}
	}


	function setNutrition(nutritions) {
		$("#nutritionNo").val(nutritions[0].nutritionNo);
		$("#nutritionName").val(nutritions[0].name);
	}


</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="ino" value="${ ingredient.ino }" />
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
						<input type="text" name="title" id="title" value="${ ingredient.title }" style="width:200px" maxlength="4000">
					</td>
				</tr>
				<tr>
					<th>원료<sup>*</sup></th>
					<td>
						<input type="hidden" name="nutritionNo" id="nutritionNo" value="${ ingredient.nutritionNo }" style="width:200px" maxlength="4000">
						<input type="text" name="nutritionName" id="nutritionName" value="${ ingredient.nutritionName }" style="width:200px" maxlength="4000" readonly>
						<a href="#" onclick="showPopup('/popup/nutrition', '500', '600'); return false" class="btnSizeA btnTypeD">추가</a>
					</td>
				</tr>
				<tr>
					<th>아이콘 이미지<sup>*</sup></th>
					<td>
						<input type="file" name="imgFile" onchange="uploadImage('imgFile')">
						<p class="txt">* Image Size : 약 260*260(jpg) / Max 10Mbyte<br>* 유동성이 있는 사이즈로 디자인 가이드 참고</p>
						<img src="${ ingredient.img }" id="imgTag" style="display:${ !empty ingredient.img ? 'block' : 'none' }">
						<input type="hidden" name="img" value="${ ingredient.img }">
					</td>
				</tr>
				<tr>
					<th>설명<sup>*</sup></th>
					<td>
						<textarea name="content" id="content" style="width:100%;height:100px">${ ingredient.content }</textarea>
					</td>
				</tr>
				<tr>
					<th>전시순위</th>
					<td>
						<input type="text" name="rank" id="rank" value="${ ingredient.rank }" style="width:200px" maxlength="4000">
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
						<label><input type="radio" name="status" value="S" ${ ingredient.status eq 'S' || empty ingredient.status ? 'checked' : '' }><span>공개</span></label>
						<label><input type="radio" name="status" value="H" ${ ingredient.status eq 'H' ? 'checked' : '' }><span>비공개</span></label>
					</td>
					<th>등록일/등록자</th>
					<td><fmt:formatDate value="${ ingredient.cdate }" pattern="${ DateTimeFormat }" /> / ${ingredient.cuser}
					</td>
					<th>수정일/수정자</th>
					<td><fmt:formatDate value="${ingredient.udate}" pattern="${ DateTimeFormat }"/> / ${ingredient.uuser}
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
