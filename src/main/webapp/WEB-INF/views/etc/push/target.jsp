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
		window.resizeTo(1000, 800);
	});

</script>
</head>
<body>
<div id="popWrapper">
	<h1>발송 리스트</h1>
	<div id="popContainer">
		<div class="listHead">
			<div class="fl">
				<div class="totalArea">
					<strong class="totalTit">Total</strong> <span class="totalCount"><fmt:formatNumber value="${ count }" pattern="#,###" /></span>
				</div>
			</div>
		</div>

		<table class="list">
			<colgroup>
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th>ID</th>
					<th>고객명</th>
					<th>OS</th>
					<th>발송시간</th>
					<th>도착시간</th>
					<th>확인시간</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.memId }" /></td>
					<td><c:out value="${ row.memName }" /></td>
<%-- 					<td><c:out value="${ row.appToken }" /></td> --%>
					<td><c:out value="${ row.appOs }" /></td>
					<td><fmt:formatDate value="${ row.sdate }" pattern="${ DateTimeFormat }"/></td>
					<td><fmt:formatDate value="${ row.adate }" pattern="${ DateTimeFormat }"/></td>
					<td><fmt:formatDate value="${ row.odate }" pattern="${ DateTimeFormat }"/></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
		</div>
		<div class="paging"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>
	</div>
</div><!-- //wrapper -->
</body>
</html>
