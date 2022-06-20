<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<div class="white_box">
		</div>
		<div class="listHead">
			<div class="fl">
				<div class="totalArea">
					<strong class="totalTit">Total</strong> <span class="totalCount"><fmt:formatNumber value="${ fn:length(list) }" pattern="#,###" /></span>
				</div>
			</div>
			<p class="fr">
			</p>
		</div>

		<table class="list">
			<colgroup>
				<col width="60px">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th>No</th>
					<th>부서명</th>
					<th>상태</th>
					<th>등록일</th>
				</tr>
			</thead>
			<tbody>						
				<c:forEach items="${ list }" var="row">
					<tr>
						<td><c:out value="${ row.teamNo }" /></td>
						<td><a href="edit?teamNo=${ row.teamNo }"><c:out value="${ row.name }" /></a></td>
						<td><c:out value="${ row.statusName }" /></td>
						<td><fmt:formatDate value="${ row.cdate }" pattern="${ DateTimeFormat }" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
			<a href="edit" class="btnTypeC btnSizeA"><span>등록</span></a>
		</div>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
