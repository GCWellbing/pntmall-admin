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
		v.add("qty", {
			empty : "발행수를 입력하세요.",
			format : "numeric"
		});
	});
	
	function goSubmit() {
		if(v.validate()) {
			disableScreen();
			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "serial?couponid=${ param.couponid }";
				enableScreen();
			});
		}
	}
	
</script>
</head>
<body>
<div id="popWrapper">
	<h1>난수 발행</h1>
	<div id="popContainer">
		<form name="editForm" id="editForm" action="createSerial">
			<input type="hidden" name="couponid" value="${ param.couponid }">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="160">
					<col width="">
				</colgroup>
				<tr>
					<th>발행 갯수</th>
					<td>
						<input type="text" name="qty" style="width:100px">
						<input type="button" class="btnTypeA btnSizeA" value="발행" onclick="goSubmit()">
					</td>
				</tr>
			</table>
		</div>
		</form>

		<table class="list">
			<colgroup>
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th>시리얼</th>
					<th>발행일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.serial }" /></td>
					<td><fmt:formatDate value="${ row.cdate }" pattern="${ DateTimeFormat }" /></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
			<a href="excel?couponid=${ param.couponid }" class="btnTypeC btnSizeA"><span>엑셀 다운로드</span></a>
		</div>
		<div class="paging btnSide"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>
	</div>
</div><!-- //wrapper -->
</body>
</html>
