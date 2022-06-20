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
	var dates = new Array();

	$(function() {
		initdates();

		$("#sdate").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		$("#edate").datepicker({
			changeMonth: true,
		    changeYear: true
		});

		$("#resetBtn").click(function() {
			document.location.href = "list";
		});

		$("#sdate").val('${ param.sdate }' == '' ? dates[0] : '${ param.sdate }' );
		$("#edate").val('${ param.edate }' == '' ? dates[0] : '${ param.edate }' );

	});

	function initdates() {
		var today = new Date();
		dates.push(formatDate(today));
		today.setDate(today.getDate() - 1);
		dates.push(formatDate(today));
		today.setDate(today.getDate() - 6);
		dates.push(formatDate(today));
		today.setDate(today.getDate() - 8);
		dates.push(formatDate(today));
		today = new Date();
		today.setMonth(today.getMonth() - 1);
		dates.push(formatDate(today));
		today.setMonth(today.getMonth() - 2);
		dates.push(formatDate(today));
		today.setMonth(today.getMonth() - 9);
		dates.push(formatDate(today));


	}

	function formatDate(date) {
		return date.getFullYear() + '.' + (date.getMonth() < 9 ? '0' : '') + (date.getMonth() + 1) + '.' + (date.getDate() < 10 ? '0' : '') + date.getDate();
	}

	function setDate(n) {
		$("#sdate").val(dates[n]);
		$("#edate").val(dates[0]);
	}
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
					<th>기간</th>
					<td colspan=3>
						<div class="dateBox">
							<input type="text" name="sdate" id="sdate" readonly value="${ param.sdate }" style="width:110px">
						</div> ~
						<div class="dateBox">
							<input type="text" name="edate" id="edate" readonly value="${ param.edate }" style="width:110px">
						</div>
						<a href="#" onclick="setDate(0); return false;" class="btnSizeC">금일</a>
						<a href="#" onclick="setDate(1); return false;" class="btnSizeC">어제</a>
						<a href="#" onclick="setDate(2); return false;" class="btnSizeC">7일</a>
						<a href="#" onclick="setDate(3); return false;" class="btnSizeC">15일</a>
						<a href="#" onclick="setDate(4); return false;" class="btnSizeC">1개월</a>
						<a href="#" onclick="setDate(5); return false;" class="btnSizeC">3개월</a>
						<a href="#" onclick="setDate(6); return false;" class="btnSizeC">12개월</a>
					</td>
				</tr>
				<tr>
					<th>주문정보</th>
					<td colspan="3">
						<select name="keytype" style="width:100px">
							<option value="1" ${ param.keytype eq '1' ? 'selected' : '' }>주문번호</option>
							<option value="2" ${ param.keytype eq '2' ? 'selected' : '' }>ID</option>
							<option value="3" ${ param.keytype eq '3' ? 'selected' : '' }>주문자</option>
							<option value="4" ${ param.keytype eq '4' ? 'selected' : '' }>제품명</option>
						</select>
						<input type="text" name="keyword" value="${ param.keyword }" style="width:500px">
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
				<col width="150px">
				<col width="150px">
				<col width="">
				<col width="140px">
				<col width="80px">
				<col width="120px">
			</colgroup>
			<thead>
				<tr>
					<th>예약주문번호</th>
					<th>주문일</th>
					<th>제품명</th>
					<th>ID(주문자)</th>
					<th>총회차</th>
					<th>최초배송예약일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><a href="edit?orderid=${ row.orderid }"><c:out value="${ row.orderid }" /></a></td>
					<td><fmt:formatDate value="${ row.cdate }" pattern="${ DateTimeFormat }"/></td>
					<td><c:out value="${ row.pname }" /></td>
					<td>${ utils.idMasking(row.memId) }(${ utils.nameMasking(row.memName) })</td>
					<td><c:out value="${ row.cnt }" /></td>
					<td><c:out value="${ row.sdate }" /></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
<!-- 			<a href="edit" class="btnTypeC btnSizeA"><span>등록</span></a> -->
		</div>
		<div class="paging"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
