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
</script>
</head>
<body>
<div id="popWrapper">
	<h1>주문상태 로그 - ${ param.orderid }</h1>
	<div id="popContainer">
		<table class="list">
			<colgroup>
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th>상태</th>
					<th>변경자</th>
					<th>변경일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">						
				<tr>
					<td><c:out value="${ row.boName }" /> (<c:out value="${ row.feName }" />)</td>
					<td><c:out value="${ row.cuserId }" /></td>
					<td><fmt:formatDate value="${ row.cdate }" pattern="${ DateTimeFormat }" /></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>	
		<div class="btnArea">
			<a href="javascript:self.close();" class="btnTypeC btnSizeA"><span>닫기</span></a>
		</div>
	</div>
</div><!-- //wrapper -->
</body>
</html>
