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
	});

	function eventCntReset() {
		disableScreen();
		$.ajax({
			type : "PUT",
			url : "/promotion/event/reset",
			dataType : "json",
			success : function(result) {
				alert("모든 회원 응모횟수가 1로 초기화 되었습니다.");
				enableScreen();
			},
			fail : function() {
				alert("오류가 발생했습니다.");
				enableScreen();
			}
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
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>기간</th>
					<td>
						<div class="dateBox">
							<input type="text" name="sdate" id="sdate" value="${ param.sdate }" readonly>
						</div> ~
						<div class="dateBox">
							<input type="text" name="edate" id="edate" value="${ param.edate }" readonly>
						</div>
					</td>
				</tr>
				<tr>
					<th>이벤트명</th>
					<td>
						<input type="text" name="name" value="${ param.name }" style="width:35%" >
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
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th>이벤트ID</th>
					<th>이벤트명</th>
					<th>기간</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">						
				<tr>
					<td><a href="edit?eventId=${ row.eventId }"><c:out value="${ row.eventId }" /></a></td>
					<td><a href="edit?eventId=${ row.eventId }"><c:out value="${ row.name }" /></a></td>
					<td><c:out value="${ row.sdate }" /> ~ <c:out value="${ row.edate }" /></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
			<a href="javascript:void(0)" onclick="eventCntReset()" class="btnTypeC btnSizeA"><span>응모횟수 초기화</span></a>
			<a href="edit" class="btnTypeC btnSizeA"><span>등록</span></a>
		</div>
		<div class="paging btnSide"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
