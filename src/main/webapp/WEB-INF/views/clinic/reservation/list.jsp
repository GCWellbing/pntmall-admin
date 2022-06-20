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
					<th>예약목적</th>
					<td colspan=3>
						<select name="cate" style="width:200px">
							<option value="" ${ empty param.cate ? 'selected' : '' }>전체</option>
							<c:forEach items="${ cateList }" var="row">
								<option value="${ row.code1 }${ row.code2 }" ${ param.cate eq row.code1+=row.code2 ? 'selected' : '' }>${ row.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
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
					<th>예약상태</th>
					<td>
						<select name="status" style="width:200px">
							<option value="" ${ empty param.status ? 'selected' : '' }>전체</option>
							<c:forEach items="${ statusList }" var="row">
								<c:if test="${ row.code2 ne '006' }">
									<option value="${ row.code1 }${ row.code2 }" ${ param.status eq row.code1+=row.code2 ? 'selected' : '' }>${ row.name }</option>
								</c:if>
							</c:forEach>
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
					<th>유형</th>
					<th>병의원명</th>
					<th>예약자명</th>
					<th>전화번호</th>
					<th>예약일</th>
					<th>예약시간</th>
					<th>접수 신청일</th>
					<th>예약목적</th>
					<th>예약상태</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.resNo }" /></td>
					<td><c:out value="${ row.cate eq '016005' ? '닥터팩 화상상담 예약':'병의원 방문 예약' }" /></td>
					<td>${ row.clinicName }</a></td>
					<td><a href="edit?resNo=${ row.resNo }">${ utils.idMasking(row.cuserId) }(${ utils.nameMasking(row.cuserName) })</a></td>
					<td><c:out value="${ row.mtel1 }" />${ utils.tel2Masking(row.mtel2) }</td>
					<td><c:out value="${ row.rdate}" /></td>
					<td><c:out value="${ row.rtime}" /></td>
					<td><fmt:formatDate value="${row.cdate}" pattern="${ DateTimeFormat }"/></td>
					<td><c:out value="${ row.cateName}" /></td>
					<td><c:out value="${ row.statusName}" /></td>
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
