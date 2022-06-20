<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="utils" scope="request" class="com.pntmall.common.utils.Utils"/>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	$(function() {
		$("#fromDate").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		$("#toDate").datepicker({
			changeMonth: true,
		    changeYear: true
		});

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
		<form name="searchForm" id="searchForm">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>병의원명</th>
					<td>
						<input type="text" name="clinicName" id="clinicName" value="${ param.clinicName }">
					</td>
					<th>병의원ID</th>
					<td>
						<input type="text" name="clinicMemId" id="clinicMemId" value="${ param.clinicMemId }">
					</td>
				</tr>
				<tr>
					<th>접수신청일</th>
					<td>
						<div class="dateBox">
							<input type="text" name="fromDate" id="fromDate" readonly value="${ param.fromDate }">
						</div>
						<div class="dateBox">
							<input type="text" name="toDate" id="toDate" readonly value="${ param.toDate }">
						</div>
					</td>
					<th>상태</th>
					<td>
						<select name="status" style="width:200px">
							<option value="" ${ empty param.status ? 'selected' : '' }>전체</option>
							<option value="025005" ${ param.status eq '025005' ? 'selected' : '' }>승인</option>
							<option value="025004" ${ param.status eq '025004'? 'selected' : '' }>미승인</option>
							<option value="025006" ${ param.status eq '025006'? 'selected' : '' }>예약취소</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>예약자명</th>
					<td>
						<input type="text" name="name" id="name" value="${ param.name }">
					</td>
					<th>전화번호</th>
					<td>
						<input type="text" name="mtel" id="mtel" value="${ param.mtel }">
					</td>
				</tr>
			</table>
			<div class="btnArea ac">
				<input type="submit" class="btnTypeA btnSizeA" value="검색">
				<input type="button" class="btnTypeB btnSizeA" id="resetBtn" value="초기화">
			</div>
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
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th>No</th>
					<th>병의원명</th>
					<th>예약자명</th>
					<th>전화번호</th>
					<th>상담 예약일</th>
					<th>상담 예약시간</th>
					<th>접수 신청일</th>
					<th>상담 완료여부</th>
					<th>상태</th>
					<th>승인일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.resNo }" /></td>
					<td>${ row.clinicName }</a></td>
					<td><a href="edit?resNo=${ row.resNo }">${ utils.idMasking(row.cuserId) }(${ utils.nameMasking(row.cuserName) })</a></td>
					<td><c:out value="${ row.mtel1 }" />${ utils.tel2Masking(row.mtel2) }</td>
					<td><c:out value="${ row.rdate}" /></td>
					<td><c:out value="${ row.rtime}" /></td>
					<td><fmt:formatDate value="${row.cdate}" pattern="${ DateTimeFormat }"/></td>
					<td>
						<c:choose>
							<c:when test="${row.status eq '025005'}">완료</c:when>
							<c:when test="${row.status eq '025004'}">미완료</c:when>
							<c:when test="${row.status eq '025006'}">예약취소</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${row.status eq '025005'}">승인</c:when>
							<c:when test="${row.status eq '025004'}">미승인</c:when>
							<c:when test="${row.status eq '025006'}">예약취소</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:if test="${ row.status eq '025005' }">
							<fmt:formatDate value="${row.udate}" pattern="${ DateFormat }"/>
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="paging "><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
