<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	$(function() {
	});
	
	function goSubmit(no) {
		if($("#editForm" + no + " input[name=point]").val() == '') {
			alert("지급포인트를 입력하세요.");
			return;
		}
		
		disableScreen();
		ef.proc($("#editForm" + no), function(result) {
			alert(result.message);
			if(result.succeed) location.reload();
			enableScreen();
		});
		
	}
</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<table class="list">
			<colgroup>
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th>유형</th>
					<th>제목</th>
					<th>지급포인트</th>
					<th>변경일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">						
				<tr>
					<td><c:out value="${ row.typeName }" /></td>
					<td><c:out value="${ row.title }" /></td>
					<td>
						<form name="editForm${ row.typeNo }" id="editForm${ row.typeNo }" method="post" action="create">
							<input type="hidden" name="typeNo" value="${ row.typeNo }">
							<input type="number" name="point" value="${ row.point }" style="width:100px">
							<a href="#" onclick="goSubmit(${ row.typeNo }); return false;" class="btnTypeB btnSizeA"><span>적용</span></a>
						</form>
					</td>
					<td><fmt:formatDate value="${ row.cdate }" pattern="${ DateTimeFormat }" /></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
