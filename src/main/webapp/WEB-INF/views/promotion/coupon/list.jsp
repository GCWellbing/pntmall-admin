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
					<td>
						<div class="dateBox">
							<input type="text" name="sdate" id="sdate" value="${ param.sdate }" readonly>
						</div> ~
						<div class="dateBox">
							<input type="text" name="edate" id="edate" value="${ param.edate }" readonly>
						</div>
					</td>
					<th></th>
					<td>
					</td>
				</tr>
				<tr>
					<th>쿠폰명</th>
					<td>
						<input type="text" name="title" value="${ param.title }" style="width:100%" >
					</td>
					<th>상태</th>
					<td>
						<select name="status1" style="width:40%">
							<option value=""></option>
							<option value="S" ${ param.status1 eq 'S' ? 'selected' : '' }>활성</option>
							<option value="H" ${ param.status1 eq 'H' ? 'selected' : '' }>비활성</option>
						</select>
						<select name="status2" style="width:40%">
							<option value=""></option>
							<option value="1" ${ param.status2 eq '1' ? 'selected' : '' }>대기중</option>
							<option value="2" ${ param.status2 eq '2' ? 'selected' : '' }>진행가능</option>
							<option value="3" ${ param.status2 eq '3' ? 'selected' : '' }>종료</option>
						</select>
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
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th>쿠폰NO</th>
					<th>쿠폰유형</th>
					<th>쿠폰명</th>
					<th>기간</th>
					<th>총 발급수</th>
					<th>총 사용수</th>
					<th>잔여 쿠폰수</th>
					<th>상태</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">						
				<tr>
					<td><a href="edit?couponid=${ row.couponid }"><c:out value="${ row.couponid }" /></a></td>
					<td><c:out value="${ row.gubunName }" /></td>
					<td><a href="edit?couponid=${ row.couponid }"><c:out value="${ row.title }" /></a></td>
					<td><c:out value="${ row.sdate }" />~<c:out value="${ row.edate }" /></td>
					<td><fmt:formatNumber value="${ row.totCnt }" pattern="#,###" /></td>
					<td><fmt:formatNumber value="${ row.totUseCnt }" pattern="#,###" /></td>
					<td><fmt:formatNumber value="${ row.totCnt - row.totUseCnt }" pattern="#,###" /></td>
					<td><c:out value="${ row.statusName }" />(<c:out value="${ row.status2Name }" />)</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
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
