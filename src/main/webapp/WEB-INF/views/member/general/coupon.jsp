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
			<li><a href="edit?memNo=${ param.memNo }">기본정보</a></li>
			<li><a href="point?memNo=${ param.memNo }">포인트이력</a></li>
			<li class="on"><a href="coupon?memNo=${ param.memNo }">쿠폰이력</a></li>
			<li><a href="order?memNo=${ param.memNo }">주문이력</a></li>
			<li><a href="qnaList?quser=${ param.memNo }">문의내역</a></li>
			<li><a href="health?cuser=${ param.memNo }">마이헬스체크이력</a></li>
			<li><a href="reser?cuser=${ param.memNo }">병의원예약이력</a></li>
		</ul>

		<form name="searchForm" id="searchForm">
		<input type="hidden" name="memNo" id="memNo" value="${ param.memNo }">
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
		</form>

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
					<th>쿠폰코드</th>
					<th>쿠폰명</th>
					<th>쿠폰 금액</th>
					<th>사용구분</th>
					<th>사용일</th>
					<th>발급일</th>
					<th>만료일</th>
					<th>주문번호</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
					<tr>
						<td><c:out value='${ row.mcouponid }' /></td>
						<td><c:out value='${ row.title }' /></td>
						<td><fmt:formatNumber value="${ row.discount }" pattern="#,###" />${ row.discountType eq 1 ? '원' : '%' }</td>
						<td>${ empty row.useDate ? '미사용' : '사용' }</td>
						<td><fmt:formatDate value="${row.useDate}" pattern="${ DateFormat }"/></td>
						<td><fmt:formatDate value="${row.cdate}" pattern="${ DateFormat }"/></td>
						<td><c:out value='${ row.expire }' /></td>
						<td><c:out value='${ row.orderid }' /></td>
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
