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
		$("#fromCdateJoin").datepicker();
		$("#toCdateJoin").datepicker();

		$("#resetBtn").click(function() {
			document.location.href = "list";
		});

		$("#approveAll").change(function() {
	       if($("#approveAll").is(":checked")){
	    		$("input:checkbox[name=approveArr]").prop("checked", true);
	        }else{
	    		$("input:checkbox[name=approveArr]").prop("checked", false);
	        }
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
			</colgroup>
			<thead>
				<tr>
					<th>No</th>
					<th>병의원명</th>
					<th>소분가능여부</th>
					<th>픽업 상담여부</th>
					<th>카톡 상담여부</th>
					<th>SNS 활성도</th>
					<th>병의원정보 입력 완성도</th>
					<th>PNT몰 활동지수</th>
					<th>총 반영 점수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.memNo }" /></td>
					<td><a href="/clinic/clinic/edit?memNo=${ row.memNo }">${ row.clinicName }</a></td>
					<td><c:out value="${ row.divisionScore }" /></td>
					<td><c:out value="${ row.pickupScore }" /></td>
					<td><c:out value="${ row.katalkScore }" /></td>
					<td><c:out value="${ row.snsScore }" /></td>
					<td><c:out value="${ row.infoScore }" /></td>
					<td><c:out value="${ row.bbsScore }" /></td>
					<td><c:out value="${ row.totalScore }" /></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="paging"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
