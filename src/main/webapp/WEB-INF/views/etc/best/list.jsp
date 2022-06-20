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
			document.location.href = "list?bestYn=Y";
		});
	});

	function goSubmit() {

		disableScreen();
		ef.proc($("#editForm"), function(result) {
			alert(result.message);
			if(result.succeed) location.reload();
			enableScreen();
		});
	}

</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="searchForm" id="searchForm">
		 	<input type="hidden" name="bestYn" value="Y">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>제품명</th>
					<td>
						<input type="text" name="pname" value="${ param.pname }" style="width:100%" >
					</td>
					<th>제품코드</th>
					<td>
						<input type="text" name="pno" value="${ param.pno }" style="width:100%" >
					</td>
				</tr>
				<tr>
					<th>등록자</th>
					<td>
						<input type="text" name="cuserName" value="${ param.cuserName }" style="width:100%" >
					</td>
					<th>등록일</th>
					<td>
						<div class="dateBox">
							<input type="text" name="fromDate" id="fromDate" readonly value="${ param.fromDate }">
						</div>
						<div class="dateBox">
							<input type="text" name="toDate" id="toDate" readonly value="${ param.toDate }">
						</div>
					</td>
				</tr>
				<tr>
					<th>제목</th>
					<td colspan=3>
						<input type="text" name="title" value="${ param.title }" style="width:100%" >
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
				<col width="60px">
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
					<th>별점</th>
					<th>구분(1)</th>
					<th>구분(2)</th>
					<th>제품명</th>
					<th>제목</th>
					<th>댓글여부</th>
					<th>우선순위</th>
					<th>등록자</th>
					<th>등록일</th>
				</tr>
			</thead>
			<tbody>
				<form name="editForm" id="editForm" method="POST" action="setBestSeq">
					<input type="hidden" name="itemCnt" value="${fn:length(list) }"/>
					<c:forEach items="${ list }" var="row" varStatus="status">
						<input type="hidden" name="pno${status.index}" value="${ row.pno }"  >
						<input type="hidden" name="reviewNo${status.index}" value="${ row.reviewNo }"  >
						<tr>
							<td><c:out value="${ row.reviewNo }" /></td>
							<td>
								<c:forEach var="i" begin="1" end="${ row.star }" step="1">★</c:forEach>
							</td>
							<td><c:out value="${ row.type1Name }" /></td>
							<td><c:out value="${ row.type2Name }" /></td>
							<td><c:out value="${ row.pname }" /></td>
							<td><a href="/etc/review/edit?reviewNo=${ row.reviewNo }"><c:out value="${ row.title }" /></a></td>
							<td><c:out value="${ empty row.comment ? 'N':'Y' }" /></td>
							<td>
								<input type="hidden" name="bestSeqOld${status.index}" value="${ row.bestSeq }" style="width:100%" >
								<input type="text" name="bestSeq${status.index}" value="${ row.bestSeq }" style="width:100%" >
							</td>
							<td>${ utils.idMasking(row.cuserId) }(${ utils.nameMasking(row.cuserName) })</td>
							<td><fmt:formatDate value="${row.cdate}" pattern="${ DateTimeFormat }"/></td>
						</tr>
					</c:forEach>
				</form>
			</tbody>
		</table>
		<div class="btnArea">
			<a href="javascript:goSubmit()" class="btnTypeC btnSizeA"><span>저장</span></a>
		</div>
		<div class="paging btnSide"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
