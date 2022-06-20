<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	var v;

	$(function() {
		$("#sdate").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		$("#edate").datepicker({
			changeMonth: true,
		    changeYear: true
		});

		v = new ef.utils.Validator($("#editForm"));
		v.add("sdate", {
			empty : "게시기간을 입력하세요."
		});
		v.add("edate", {
			empty : "게시기간을 입력하세요."
		});
		v.add("title", {
			empty : "제목을 입력하세요.",
			max : "200"
		});
		v.add("pcImg", {
			empty : "배너 이미지(PC)를 입력하세요.",
		});
		v.add("moImg", {
			empty : "배너 이미지(Mobile)를 입력하세요.",
		});
		v.add("url", {
			empty : "URL/유튜브코드를 입력하세요.",
		});

	});

	function goSubmit() {

		if(v.validate()) {
			disableScreen();
			if($("#targetChk").is(":checked")){
				$("#target").val("_blank");
			}else{
				$("#target").val("_self");
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

	        var action = "/upload/upload?field=" + field + "&path=mainVisual&fileType=image";
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


	function setProduct(options) {
		var html;
		for(var i = 0; i < options.length; i++) {
			if($("input[name=pno][value=" + options[i].pno + "]").length == 0) {
				html = $("#optionRow").html();
				html = html.replace(/##OPTION_PNO##/gi, options[i].pno);
				html = html.replace(/##OPTION_NM##/gi, options[i].pname);
				html = html.replace(/##OPTION_SALE_PRICE##/gi, options[i].salePrice);
				$("#optionList").append(html);
			}
		}
	}

	function removeOption(obj) {
		$(obj).parent().parent().remove();
	}

</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="mvNo" value="${ mainVisual.mvNo }" />
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
						<label><input type="radio" name="gubun" value="1" ${ mainVisual.gubun eq '1' || empty mainVisual.status ? 'checked' : '' }><span>이미지</span></label>
						<label><input type="radio" name="gubun" value="2" ${ mainVisual.gubun eq '2' ? 'checked' : '' }><span>영상</span></label>
					</td>
				</tr>
				<tr>
					<th>게시기간<sup>*</sup></th>
					<td>
						<input type="text" name="sdate" id="sdate" readonly value="${ mainVisual.sdate }"> ~
						<input type="text" name="edate" id="edate" readonly value="${ mainVisual.edate }">
					</td>
				</tr>
				<tr>
					<th>제목<sup>*</sup></th>
					<td>
						<input type="text" name="title" id="title" value="${ mainVisual.title }" style="width:200px" maxlength="4000">
					</td>
				</tr>
				<tr>
					<th>태그입력</th>
					<td>
						<input type="text" name="tag" id="tag" value="${ mainVisual.tag }" style="width:200px" maxlength="4000">
					</td>
				</tr>
				<tr>
					<th>배너 이미지/영상 썸네일 (PC)<sup>*</sup></th>
					<td>
						<input type="file" name="pcImgFile" onchange="uploadImage('pcImgFile')">
						<p class="txt">* Image Size : 1920*500(jpg) / Max 10Mbyte</p>
						<img src="${ mainVisual.pcImg }" id="pcImgTag" style="display:${ !empty mainVisual.pcImg ? 'block' : 'none' }">
						<input type="hidden" name="pcImg" id="pcImg" value="${ mainVisual.pcImg }">
						<input type="text" name="pcImgAlt" id="pcImgAlt" value="${ mainVisual.pcImgAlt }" placeholder="대체텍스트 입력 영역" >
					</td>
				</tr>
				<tr>
					<th>배너 이미지/영상 썸네일 (Mobile)<sup>*</sup></th>
					<td>
						<input type="file" name="moImgFile" onchange="uploadImage('moImgFile')">
						<p class="txt">* Image Size : 780*1012(jpg) / Max 10Mbyte<br></p>
						<img src="${ mainVisual.moImg }" id="moImgTag" style="display:${ !empty mainVisual.moImg ? 'block' : 'none' }">
						<input type="hidden" name="moImg" id="moImg" value="${ mainVisual.moImg }">
						<input type="text" name="moImgAlt" id="moImgAlt" value="${ mainVisual.moImgAlt }"  placeholder="대체텍스트 입력 영역">
					</td>
				</tr>
				<tr>
					<th>URL / 유튜브코드<sup>*</sup></th>
					<td>
						<input type="text" name="url" id="url" value="${ mainVisual.url }" style="width:200px" maxlength="4000">
						<input type="checkbox" name="targetChk" id="targetChk"  ${ mainVisual.target == "_blank"? "checked":""} >새 창으로 이동
						<input type="hidden" name="target" id="target" value="${mainVisual.target}" >
					</td>
				</tr>
				<tr>
					<th>전시순위</th>
					<td>
						<input type="text" name="rank" id="rank" value="${ mainVisual.rank }" style="width:200px" maxlength="4000">
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
						<label><input type="radio" name="status" value="S" ${ mainVisual.status eq 'S' || empty mainVisual.status ? 'checked' : '' }><span>공개</span></label>
						<label><input type="radio" name="status" value="H" ${ mainVisual.status eq 'H' ? 'checked' : '' }><span>비공개</span></label>
					</td>
					<th>등록일/등록자</th>
					<td><fmt:formatDate value="${ mainVisual.cdate }" pattern="${ DateTimeFormat }" /> / ${mainVisual.cuserId}
					</td>
					<th>수정일/수정자</th>
					<td><fmt:formatDate value="${mainVisual.udate}" pattern="${ DateTimeFormat }"/> / ${mainVisual.uuserId}
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


</body>
</html>
