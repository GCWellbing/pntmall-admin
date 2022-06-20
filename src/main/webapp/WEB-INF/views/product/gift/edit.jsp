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
		v.add("pname", {
			empty : "제품명을 입력하세요.",
			max : "50"
		});

	});
	
	function goSubmit() {
		if(v.validate()) {
			/*
			if($("input[name=matnr]").val() == '') {
				alert("SAP 제품정보를 입력하세요.");
				return;
			}
			*/
			disableScreen();
			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list";
				enableScreen();
			});
		}
	}

	function setSap(sap) {
		$("#sap").html(sap.maktx + " (" + sap.matnr + ")");
		$("input[name=matnr]").val(sap.matnr);
// 		$("input[name=netpr]").val(sap.netpr);
	}
	
</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="pno" value="${ product.pno }" />
			<input type="hidden" name="ptype" value="2">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<!-- 
				<tr>
					<th>SAP 제품정보<sup>*</sup></th>
					<td>
						<span id="sap">
						<c:if test="${ mode eq 'modify' }">
						${ product.maktx } (${ product.matnr })
						</c:if>
						</span>
						<input type="hidden" name="matnr" value="${ product.matnr }">
						<a href="#" onclick="showPopup('/popup/sap', '550', '600'); return false" class="btnSizeA btnTypeD">선택</a>
					</td>
				</tr>
				 -->
				<tr>
					<th>제품명<sup>*</sup></th>
					<td>	
						<input type="text" name="pname" value="${ product.pname }" style="width:100%" maxlength="50">
					</td>
				</tr>
				<tr>
					<th>전시여부</th>
					<td>	
						<label><input type="radio" name="status" value="S" ${ mode eq 'create' || product.status eq 'S' ? 'checked' : '' }><span>공개</span></label>
						<label><input type="radio" name="status" value="H" ${ product.status eq 'H' ? 'checked' : '' }><span>비공개</span></label>
						<label><input type="radio" name="status" value="E" ${ product.status eq 'E' ? 'checked' : '' }><span>단종</span></label>
					</td>
				</tr>
			</table>
		</div>
		
		<br><br>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>등록일/등록자</th>
					<td><fmt:formatDate value="${ product.cdate }" pattern="${ DateTimeFormat }" /> / ${ product.cuserId }</td>
					<th>수정일/수정자</th>
					<td><fmt:formatDate value="${ product.udate }" pattern="${ DateTimeFormat }" /> / ${ product.uuserId }</td>
				</tr>
			</table>
		</div>
		
		<div class="btnArea">
			<span class="fl">
				<a href="${ retrivePath }" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>	
			<c:if test="${ mode eq 'create' }">
				<a href="javascript:goSubmit()" class="btnTypeD btnSizeA"><span>등록</span></a>
			</c:if>
			<c:if test="${ mode eq 'modify' && updateAuth eq 'Y' }">
				<a href="javascript:goSubmit()" class="btnTypeD btnSizeA"><span>수정</span></a>
			</c:if>
		</div>
		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
