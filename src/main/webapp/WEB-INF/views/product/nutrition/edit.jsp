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
		v = new ef.utils.Validator($("#editForm"));
		v.add("name", {
			empty : "영양성분명을 입력하세요.",
			max : "50"
		});
		v.add("func", {
			empty : "기능을 입력하세요.",
			max : "50"
		});
// 		v.add("content", {
// 			empty : "성분설명을 입력하세요.",
// 			max : "100"
// 		});
		v.add("standard", {
			empty : "영양성분 기준치를 입력하세요.",
			max : "20"
		});
		/*
		v.add("intake1", {
			empty : "5세이하 상한섭취량을 입력하세요.",
			max : "20"
		});
		v.add("intake2", {
			empty : "6~8세 상한섭취량을 입력하세요.",
			max : "20"
		});
		v.add("intake3", {
			empty : "9~11세 상한섭취량을 입력하세요.",
			max : "20"
		});
		v.add("intake4", {
			empty : "12~14세 상한섭취량을 입력하세요.",
			max : "20"
		});
		v.add("intake5", {
			empty : "15~18세 상한섭취량을 입력하세요.",
			max : "20"
		});
		v.add("intake6", {
			empty : "19~29세 상한섭취량을 입력하세요.",
			max : "20"
		});
		v.add("intake7", {
			empty : "30~44세 상한섭취량을 입력하세요.",
			max : "20"
		});
		v.add("intake8", {
			empty : "45~64세 상한섭취량을 입력하세요.",
			max : "20"
		});
		v.add("intake9", {
			empty : "64~74세 상한섭취량을 입력하세요.",
			max : "20"
		});
		v.add("intake10", {
			empty : "75세이상 상한섭취량을 입력하세요.",
			max : "20"
		});
		*/

		$("#submitBtn").click(function() {
			if(v.validate()) {
				disableScreen();
				ef.proc($("#editForm"), function(result) {
					alert(result.message);
					if(result.succeed) location.href = "list";
					enableScreen();
				});
			}
		});

		$("#resetBtn").click(function() {
			$("#editForm")[0].reset();
			$("#editForm").attr("action", "create");
		});
	});

	function uploadImage(field) {
		if($("input[name=" + field + "]").val() != '') {
	        disableScreen();

	        var action = "/upload/upload?field=" + field + "&path=nutrition&fileType=image";
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
</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="editForm" id="editForm" method="post" action="${ mode }">
			<input type="hidden" name="nutritionNo" value="${ nutrition.nutritionNo }">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>영양성분명<sup>*</sup></th>
					<td>
						<input type="text" name="name" value="${ nutrition.name }" style="width:100%">
					</td>
				</tr>
				<tr>
					<th>기능<sup>*</sup></th>
					<td>
						<input type="text" name="func" value="${ nutrition.func }" style="width:100%">
					</td>
				</tr>
				<tr>
					<th>성분설명(마이헬스체크)</th>
					<td>
						<input type="text" name="content" value="${ nutrition.content }" style="width:100%">
					</td>
				</tr>
				<tr>
					<th>아이콘</th>
					<td>
						<input type="file" name="iconFile" onchange="uploadImage('iconFile')">
						<p class="txt">* Image Size : 214*195(jpg) / Max 10Mbyte</p>
						<img src="${ nutrition.icon }" id="iconTag" style="display:${ !empty nutrition.icon ? 'block' : 'none' }">
						<input type="hidden" name="icon" id="icon" value="${ nutrition.icon }">
					</td>
				</tr>
				<tr>
					<th>영양성분 기준치<sup>*</sup></th>
					<td>
						<input type="text" name="standard" value="${ nutrition.standard }" style="width:100px">
					</td>
				</tr>
				<tr>
					<th>단위<sup>*</sup></th>
					<td>
						<select name="unit" style="width:200px">
							<c:forEach items="${ unitList }" var="row">
								<option value="${ row.code1 }${ row.code2 }" ${ nutrition.unit eq row.code1.concat(row.code2) ? 'selected' : '' }>${ row.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>상한 섭취량<br>(마이헬스체크)</th>
					<td>
						<table class="board" style="width:700px">
							<colgroup>
								<col width="80px">
								<col width="100px">
								<col width="80px">
								<col width="100px">
							</colgroup>
							<tr>
								<th>5세 이하</th>
								<td><input type="text" name="intake1" value="${ nutrition.intake1 }"></td>
								<th>19~29세</th>
								<td><input type="text" name="intake6" value="${ nutrition.intake6 }"></td>
							</tr>
							<tr>
								<th>6~8세</th>
								<td><input type="text" name="intake2" value="${ nutrition.intake2 }"></td>
								<th>30~44세</th>
								<td><input type="text" name="intake7" value="${ nutrition.intake7 }"></td>
							</tr>
							<tr>
								<th>9~11세</th>
								<td><input type="text" name="intake3" value="${ nutrition.intake3 }"></td>
								<th>45~64세</th>
								<td><input type="text" name="intake8" value="${ nutrition.intake8 }"></td>
							</tr>
							<tr>
								<th>12~14세</th>
								<td><input type="text" name="intake4" value="${ nutrition.intake4 }"></td>
								<th>65~74세</th>
								<td><input type="text" name="intake9" value="${ nutrition.intake9 }"></td>
							</tr>
							<tr>
								<th>15~18세</th>
								<td><input type="text" name="intake5" value="${ nutrition.intake5 }"></td>
								<th>75세 이상</th>
								<td><input type="text" name="intake10" value="${ nutrition.intake10 }"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th>논문1</th>
					<td>
						<input type="text" name="article1" value="${ nutrition.article1 }" style="width:100%">
					</td>
				</tr>
				<tr>
					<th>논문2</th>
					<td>
						<input type="text" name="article2" value="${ nutrition.article2 }" style="width:100%">
					</td>
				</tr>
			</table>
		</div>
		<div class="white_box">
			<table class="board">
				<tr>
					<th>공개여부<sup>*</sup></th>
					<td>
						<label><input type="radio" name="status" value="S" ${ nutrition.status eq 'S' || empty nutrition.status ? 'checked' : '' }><span>공개</span></label>
						<label><input type="radio" name="status" value="H" ${ nutrition.status eq 'H' ? 'checked' : '' }><span>비공개</span></label>
					</td>
					<th>등록일/등록자</th>
					<td><fmt:formatDate value="${ nutrition.cdate }" pattern="${ DateTimeFormat }" /> / ${ nutrition.cuserId }
					</td>
					<th>수정일/수정자</th>
					<td><fmt:formatDate value="${ nutrition.udate }" pattern="${ DateTimeFormat }"/> / ${ nutrition.uuserId }
					</td>
				</tr>
			</table>
		</div>
		<div class="btnArea">
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
