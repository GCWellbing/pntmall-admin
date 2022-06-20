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
			<li><a href="edit?memNo=${ param.cuser }">기본정보</a></li>
			<li><a href="point?memNo=${ param.cuser }">포인트이력</a></li>
			<li><a href="coupon?memNo=${ param.cuser }">쿠폰이력</a></li>
			<li><a href="order?memNo=${ param.cuser }">주문이력</a></li>
			<li><a href="qnaList?quser=${ param.cuser }">문의내역</a></li>
			<li class="on"><a href="health?cuser=${ param.cuser }">마이헬스체크이력</a></li>
			<li><a href="reser?cuser=${ param.cuser }">병의원예약이력</a></li>
		</ul>

		<form name="searchForm" id="searchForm">
		<input type="hidden" name="cuser" id="cuser" value="${ param.cuser }">
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
				<col width="50">
				<col width="60">
				<col width="60">
				<col width="80">
				<col width="80">
				<col width="80">
				<col width="150">
				<col width="">
				<col width="80">
				<col width="100">
				<col width="80">
			</colgroup>
			<thead>
				<tr>
					<th>No</th>
					<th>성별</th>
					<th>년도</th>
					<th>키</th>
					<th>몸무게</th>
					<th>BMI지수(수치)</th>
					<th>비만도</th>
					<th>건강고민</th>
					<th>진행단계</th>
					<th>진행일</th>
					<th>상세</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="no" value="${ count }" />
				<c:forEach items="${ list }" var="row">
					<tr>
						<td><c:out value="${ no }" /></td>
						<td><c:out value="${row.gender eq 'M' ? '남성' : '여성'}" /></td>
						<td><c:out value='${row.year}' /></td>
						<td><fmt:formatNumber value="${row.height}" pattern="###.#"/>cm</td>
						<td><fmt:formatNumber value="${row.weight}" pattern="###.#"/>kg</td>
						<td><c:out value='${row.bmi}' /></td>
						<td><c:out value='${ row.cdtionName}' /></td>
						<td><c:out value='${row.healthTopicName1}' /> / <c:out value='${row.healthTopicName2}' /> / <c:out value='${row.healthTopicName3}' /></td>
						<td>${ row.topicCount == 0 ? "2단계" : "1단계"}</td>
						<td><fmt:formatDate value="${row.cdate}" pattern="${ DateFormat }" /></td>
						<td>
							<c:if test="${  row.resNo > 0 }">
								<input type="button" class="btnTypeB btnSizeB" onclick="goReservation('${  row.resNo }');" value="상세">
							</c:if>
						</td>
					</tr>
					<c:set var="no" value="${ no - 1 }" />
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
<script>
	function goReservation(resNo) {
		location.href = "/clinic/doctorPack/edit?resNo="+resNo;
	}
</script>

</body>
</html>
