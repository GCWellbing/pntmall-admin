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

	function inputTypeCheck() {
		if ($("input[name='type']:checked").val() == 1) {
			// 상단띠배너
			$("#p_img1").css('display','none');
			$("#p_img2").css('display','none');
			$("#p_back_color").css('display','table-row');
			$("#p_font_color").css('display','table-row');
			$("#rebon_img").css('display','table-row');
		} else {
			// 전면팝업
			$("#p_img1").css('display','table-row');
			$("#p_img2").css('display','table-row');
			$("#p_back_color").css('display','none');
			$("#p_font_color").css('display','none');
			$("#rebon_img").css('display','none');
		}
	}

	function inputGradeCheck() {
		if ($("input[name='displayGradeStatus']:checked").val() == 'A') {
			$('#tr_grade').css('display','none');
		} else {
			$('#tr_grade').css('display','table-row');
		}
	}

	$(function() {
		inputTypeCheck();
		inputGradeCheck();

		$("#backgroundColor").change(function (){
			$("#backColor").css('background-color', $(this).val());
		});

		$("#fontColor").change(function (){
			$("#fColor").css('background-color', $(this).val());
		});

		$("input[name='type']").change(function () {
			inputTypeCheck();
		});

		$("input[name='displayGradeStatus']").change(function () {
			inputGradeCheck();
		});

		$("#sdate").datepicker({
			changeMonth: true,
			changeYear: true
		});
		$("#edate").datepicker({
			changeMonth: true,
			changeYear: true
		});

		v = new ef.utils.Validator($("#editForm"));
		v.add("title", {
			empty : "제목을 입력하세요.",
			max : "200"
		});
		v.add("sdate", {
			empty : "게시기간을 입력하세요."
		});
		v.add("edate", {
			empty : "게시기간을 입력하세요."
		});
		// v.add("pcImg", {
		// 	empty : "이미지(PC)를 입력하세요.",
		// });
		// v.add("moImg", {
		// 	empty : "이미지(Mobile)를 입력하세요.",
		// });
		// v.add("url", {
		// 	empty : "URL/유튜브코드를 입력하세요.",
		// });

	});

	function goSubmit() {
		// oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);

		if(v.validate()) {
			disableScreen();
			$('input[name=gradeNo]:checked').each(function (){
				$('#displayGrade').val($('#displayGrade').val() + $(this).val() + ',');
			});

			if($("#targetChk").is(":checked")){
				$("#target").val("_blank");
			}else{
				$("#target").val("_self");
			}

			if($('#backgroundColor').val() == "#") {
				$('#backgroundColor').val("#e9662b");
			}

			if($('#fontColor').val() == "#") {
				$('#fontColor').val("#ffffff");
			}

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

	        var action = "/upload/upload?field=" + field + "&path=popup&fileType=image";
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



</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="popupid" value="${ popup.popupid }" />
		<h2>정보 등록</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>구분<sup>*</sup></th>
					<td>
						<label><input type="radio" name="type" value="1" ${ popup.type eq 1 || empty popup.type ? 'checked' : '' }><span>상단띠배너</span></label>
						<label><input type="radio" name="type" value="2" ${ popup.type eq 2 ? 'checked' : '' }><span>전면팝업</span></label>
					</td>
				</tr>
				<tr>
					<th>게시기간<sup>*</sup></th>
					<td>
						<input type="text" name="sdate" id="sdate" readonly value="${ popup.sdate }"> ~
						<input type="text" name="edate" id="edate" readonly value="${ popup.edate }">
					</td>
				</tr>
				<tr>
					<th>적용대상<sup>*</sup></th>
					<td>
						<label><input type="radio" name="displayGradeStatus" value="A" ${ popup.displayGradeStatus eq 'A' || empty popup.displayGradeStatus ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="displayGradeStatus" value="G" ${ popup.displayGradeStatus eq 'G' ? 'checked' : '' }><span>등급</span></label>
					</td>
				</tr>
				<tr id="tr_grade">
					<th>적용등급<sup>*</sup></th>
					<td>
						<c:forEach items="${ memGrades }" var="row">
							<label><input type="checkbox" name="gradeNo" value="${ row.gradeNo }" ${ fn:contains(popup.displayGrade, row.gradeNo) ? "checked" : "" }><span>${ row.name }</span></label>
						</c:forEach>
						<input type="hidden" name="displayGrade" id="displayGrade" value="">
					</td>
				</tr>
				<tr>
					<th>제목<sup>*</sup></th>
					<td>
						<input type="text" name="title" id="title" value="${ popup.title }" style="width:200px" maxlength="4000">
					</td>
				</tr>
				<tr id="rebon_img">
					<th>배경 이미지<sup>*</sup></th>
					<td>
						<input type="file" name="rebonImgFile" onchange="uploadImage('rebonImgFile')">
						<p class="txt">* Image Size : 1920*40(jpg) / Max 10Mbyte</p>
						<img src="${ popup.rebonImg }" id="rebonImgTag" style="display:${ !empty popup.rebonImg ? 'block' : 'none' }">
						<input type="hidden" name="rebonImg" id="rebonImg" value="${ popup.rebonImg }">
						<input type="text" name="rebonImgAlt" id="rebonImgAlt" value="${ popup.rebonImgAlt }" placeholder="대체텍스트 입력 영역" >
					</td>
				</tr>
				<tr id="p_img1">
					<th>이미지 (PC)<sup>*</sup></th>
					<td>
						<input type="file" name="pcImgFile" onchange="uploadImage('pcImgFile')">
						<p class="txt">* Image Size : 449*378(jpg) / Max 10Mbyte</p>
						<img src="${ popup.pcImg }" id="pcImgTag" style="display:${ !empty popup.pcImg ? 'block' : 'none' }">
						<input type="hidden" name="pcImg" id="pcImg" value="${ popup.pcImg }">
						<input type="text" name="pcImgAlt" id="pcImgAlt" value="${ popup.pcImgAlt }" placeholder="대체텍스트 입력 영역" >
					</td>
				</tr>
				<tr id="p_img2">
					<th>이미지 (Mobile)<sup>*</sup></th>
					<td>
						<input type="file" name="moImgFile" onchange="uploadImage('moImgFile')">
						<p class="txt">* Image Size : 320*283(jpg) / Max 10Mbyte<br></p>
						<img src="${ popup.moImg }" id="moImgTag" style="display:${ !empty popup.moImg ? 'block' : 'none' }">
						<input type="hidden" name="moImg" id="moImg" value="${ popup.moImg }">
						<input type="text" name="moImgAlt" id="moImgAlt" value="${ popup.moImgAlt }"  placeholder="대체텍스트 입력 영역">
					</td>
				</tr>
				<tr id="p_back_color">
					<th>배경색<sup>*</sup></th>
					<td>
						<input type="text" name="backgroundColor" id="backgroundColor" value="${ popup.backgroundColor }" placeholder="#">
						<div id="backColor" style="float: left; width: 28px; height: 28px; margin-right: 10px; border: thin solid #9a9a9a; background-color: ${ popup.backgroundColor }"></div>
					</td>
				</tr>
				<tr id="p_font_color">
					<th>글자색<sup>*</sup></th>
					<td>
						<input type="text" name="fontColor" id="fontColor" value="${ popup.fontColor }" placeholder="#">
						<div id="fColor" style="float: left; width: 28px; height: 28px; margin-right: 10px; border: thin solid #9a9a9a; background-color: ${ popup.fontColor }"></div>
					</td>
				</tr>
<%--				<tr>--%>
<%--					<th>설명<sup>*</sup></th>--%>
<%--					<td>--%>
<%--						<textarea name="content" id="content" style="width:100%;height:100px">${ popup.content }</textarea>--%>
<%--					</td>--%>
<%--				</tr>--%>
				<tr>
					<th>URL / 유튜브코드<sup>*</sup></th>
					<td>
						<input type="text" name="url" id="url" value="${ popup.url }" style="width:200px" maxlength="4000">
						<input type="checkbox" name="targetChk" id="targetChk" style="margin-right: 10px;" ${ popup.target == "_blank"? "checked":""} > 새 창으로 이동
						<input type="hidden" name="target" id="target" value="${popup.target}" >
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
						<label><input type="radio" name="status" value="S" ${ popup.status eq 'S' || empty popup.status ? 'checked' : '' }><span>공개</span></label>
						<label><input type="radio" name="status" value="H" ${ popup.status eq 'H' ? 'checked' : '' }><span>비공개</span></label>
					</td>
					<th>등록일/등록자</th>
					<td><fmt:formatDate value="${ popup.cdate }" pattern="${ DateTimeFormat }" /> / ${popup.cuser}
					</td>
					<th>수정일/수정자</th>
					<td><fmt:formatDate value="${popup.udate}" pattern="${ DateTimeFormat }"/> / ${popup.uuser}
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
	// var oEditors = [];
	//
	// nhn.husky.EZCreator.createInIFrame({
	// 	oAppRef: oEditors,
	// 	elPlaceHolder: "content",
	// 	sSkinURI: "/static/se2/SmartEditor2Skin.html",
	// 	htParams : {
	//           // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
	//           bUseToolbar : true,
	//           // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
	//           bUseVerticalResizer : true,
	//           // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
	//           bUseModeChanger : true,
	//           bSkipXssFilter : true,
	//           fOnBeforeUnload : function(){}
	//       },
	// 	fCreator: "createSEditorInIFrame"
	// });
</script>


</body>
</html>
