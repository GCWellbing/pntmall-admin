<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<ul class="tabMenu">
			<li><a href="editN?memNo=${ param.memNo }">기본정보</a></li>
			<li class="on"><a href="order?memNo=${ param.memNo }">주문이력</a></li>
			<li><a href="qnaList?quser=${ param.memNo }">문의내역</a></li>
			<li><a href="reser?cuser=${ param.memNo }">병의원예약이력</a></li>
		</ul>

		<div class="listHead">
			<div class="fl">
				<div class="totalArea">
					<strong class="totalTit">Total</strong> <span class="totalCount"><fmt:formatNumber value="${ count }" pattern="#,###" /></span>
				</div>
			</div>
			<p class="fr">
				<c:import url="/include/pageSize?pageSize=${ param.pageSize }" />
			</p>
		</div>

		<table class="list">
			<colgroup>
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th>주문번호</th>
					<th>일시</th>
					<th>제품명</th>
					<th>ID(주문자)</th>
					<th>총결제금액</th>
					<th>배송유형</th>
					<th>주문상태</th>
					<th>결제타입</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><a href="/order/order/${ fn:startsWith(row.status, '1') ? '' : 'r' }edit?orderid=${ row.orderid }"><c:out value="${ row.orderid }" /></a></td>
					<td><fmt:formatDate value="${ row.odate }" pattern="${ DateTimeFormat }"/></td>
					<td><c:out value="${ row.pname }" /> ${ row.itemCount > 1 ? '외 '.concat(row.itemCount - 1).concat('종') : '' }</td>
					<td><c:out value="${ row.memId }" />(<c:out value="${ row.oname }" />)</td>
					<td><fmt:formatNumber value="${ row.payAmt }" pattern="#,###" /></td>
					<td><c:out value="${ row.orderGubunName }" /></td>
					<td><c:out value="${ row.statusName }" /></td>
					<td><c:out value="${ row.payTypeName }" /></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>

		<div class="btnArea">
			<span class="fl">
				<a href="${ retrivePath }" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
		</div>

		<div class="paging btnSide"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->


</body>
</html>
