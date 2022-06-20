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
		$("#fromDate").datepicker();
		$("#toDate").datepicker();

		$("#resetBtn").click(function() {
			document.location.href = "list";
		});
	});
</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<ul class="tabMenu">
			<li><a href="edit?memNo=${ param.cuser }">기본정보</a></li>
			<li><a href="point?memNo=${ param.cuser }">포인트이력</a></li>
			<li><a href="coupon?memNo=${ param.cuser }">쿠폰이력</a></li>
			<li><a href="order?memNo=${ param.cuser }">주문이력</a></li>
			<li><a href="qnaList?quser=${ param.cuser }">문의내역</a></li>
			<li><a href="health?cuser=${ param.cuser }">마이헬스체크이력</a></li>
			<li class="on"><a href="reser?cuser=${ param.cuser }">병의원예약이력</a></li>
		</ul>
		<form name="searchForm" id="searchForm">
		<input type="hidden" name="quser" value="${ param.memNo }">
		<div class="white_box">
		</div>
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
				<col width="60px">
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
					<th>No</th>
					<th>유형</th>
					<th>병의원명</th>
					<th>예약일</th>
					<th>예약시간</th>
					<th>접수 신청일</th>
					<th>예약 목적</th>
					<th>예약 상태</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.resNo }" /></td>
					<td><c:out value="${ row.cate eq '016005' ? '닥터팩 화상상담 예약':'병의원 방문 예약' }" /></td>
					<td>${ row.clinicName }</td>
					<td><c:out value="${ row.rdate }" /></td>
					<td><c:out value="${ row.rtime }" /></td>
					<td><fmt:formatDate value="${row.cdate}" pattern="${ DateTimeFormat }"/></td>
					<td><c:out value="${ row.cateName }" /></td>
					<td><c:out value="${ row.statusName }" /></td>
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
