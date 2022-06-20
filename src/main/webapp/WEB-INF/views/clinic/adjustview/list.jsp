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
		$("#resetBtn").click(function() {
			document.location.href = "list";
		});

		$("#deadline").datepicker();
	});

	function goCreate() {
		if(confirm("정산데이타를 생성하시겠습니까?")) {
			$("#searchForm").attr("action", "create");
			$("#searchForm").attr("method", "post");

			disableScreen();
			ef.proc($("#searchForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.reload();
				enableScreen();
			});
		}
	}

	function goExcel() {
		$("#searchForm").attr("action", "excel");
		$("#searchForm").submit();
		$("#searchForm").attr("action", "");
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
					<th>정산대상기간</th>
					<td colspan="3">
						<select name="year1" style="width:100px">
							<c:forEach var='i' begin='2021' end='${ thisYear }'>
								<option value='${ thisYear - i + 2021 }' ${ thisYear - i + 2021 eq clinicAdjustSearch.year1 ? 'selected' : '' }>${ thisYear - i + 2021 }</option>
							</c:forEach>
						</select>
						<select name="quarter1" style="width:100px">
							<c:forEach var='i' begin='1' end='4'>
								<option value='${ i }' ${ i eq clinicAdjustSearch.quarter1 ? 'selected' : '' }>${ i }분기</option>
							</c:forEach>
						</select>
						~
						<select name="year2" style="width:100px">
							<c:forEach var='i' begin='2021' end='${ thisYear }'>
								<option value='${ thisYear - i + 2021 }' ${ thisYear - i + 2021 eq clinicAdjustSearch.year2 ? 'selected' : '' }>${ thisYear - i + 2021 }</option>
							</c:forEach>
						</select>
						<select name="quarter2" style="width:100px">
							<c:forEach var='i' begin='1' end='4'>
								<option value='${ i }' ${ i eq clinicAdjustSearch.quarter2 ? 'selected' : '' }>${ i }분기</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>병의원ID</th>
					<td>
						<input type="text" name="memId" value="${ param.memId }" style="width:100%" >
					</td>
					<th>병의원명</th>
					<td>
						<input type="text" name="clinicName" value="${ param.clinicName }" style="width:100%" >
					</td>
				</tr>
				<tr>
					<th>정산상태</th>
					<td colspan=3>
						<select name="status" style="width:200px">
							<option value=""></option>
							<option value="10" ${ 10 eq param.status ? 'selected' : '' }>정산예정</option>
							<option value="20" ${ 20 eq param.status ? 'selected' : '' }>정산확인요청</option>
							<option value="30" ${ 30 eq param.status ? 'selected' : '' }>세금계산서발행요청</option>
							<option value="40" ${ 40 eq param.status ? 'selected' : '' }>세금계산서수정요청</option>
							<option value="50" ${ 50 eq param.status ? 'selected' : '' }>세금계산서확인</option>
							<option value="60" ${ 60 eq param.status ? 'selected' : '' }>지급준비중</option>
							<option value="70" ${ 70 eq param.status ? 'selected' : '' }>지급완료</option>
							<option value="80" ${ 80 eq param.status ? 'selected' : '' }>미정산</option>
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
					<strong class="totalTit">Total</strong> <span class="totalCount"><fmt:formatNumber value="${ count }" pattern="#,###" /></span> /
					<strong class="totalTit">정산금액</strong> <span class="totalCount"><fmt:formatNumber value="${ totAdjustAmt }" pattern="#,###" /></span>
				</div>
			</div>
			<p class="fr">
				<a href="javascript:goExcel()" class="btnTypeC btnSizeA"><span>엑셀 다운로드</span></a>
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
				<col width="100px">
				<col width="">
				<col width="">
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
					<th>정산기간</th>
					<th>병의원명</th>
					<th>병의원ID</th>
					<th>총매출금액</th>
					<th>일반매출금액</th>
					<th>총출하가</th>
					<th>이용수수료율</th>
					<th>홍보수수료율</th>
					<th>픽업매출금액</th>
					<th>픽업수수료율</th>
					<th>닥터팩매출금액</th>
					<th>닥터팩수수료율</th>
					<th>정산금액</th>
					<th>정산상태</th>
					<th>정산상태마감일</th>
					<th>지급완료일</th>
					<th>SAP전송결과</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
					<tr>
						<td><c:out value="${ row.year }" />/<c:out value="${ row.quarter }" /></td>
						<td><a href="detail?memNo=${ row.memNo }&year=${ row.year }&quarter=${ row.quarter }">${ row.clinicName }</a></td>
						<td><c:out value="${ row.memId }" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.totSaleAmt }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.saleAmt }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.totSupplyAmt }" pattern="#,###" /></td>
						<td class="ar"><c:out value="${ row.fee }" /></td>
						<td class="ar"><c:out value="${ row.promoFee }" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.pickupSaleAmt }" pattern="#,###" /></td>
						<td class="ar"><c:out value="${ row.pickupFee }" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.dpackSaleAmt }" pattern="#,###" /></td>
						<td class="ar"><c:out value="${ row.dpackFee }" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.adjustAmt }" pattern="#,###" /></td>
						<td>
							<c:choose>
								<c:when test='${ row.status eq 10 }'>
									정산예정
								</c:when>
								<c:when test='${ row.status eq 20 }'>
									정산확인요청
								</c:when>
								<c:when test='${ row.status eq 30 }'>
									세금계산서발행요청
								</c:when>
								<c:when test='${ row.status eq 40 }'>
									세금계산서수정요청
								</c:when>
								<c:when test='${ row.status eq 50 }'>
									세금계산서확인
								</c:when>
								<c:when test='${ row.status eq 60 }'>
									지급준비중
								</c:when>
								<c:when test='${ row.status eq 70 }'>
									지급완료
								</c:when>
								<c:when test='${ row.status eq 80 }'>
									미정산
								</c:when>
							</c:choose>
						</td>
						<td><c:out value="${ row.deadline }" /></td>
						<td><c:out value="${ row.paymentDate }" /></td>
						<td><c:out value="${ row.sapResult }" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div class="btnArea">
		</div>
		<div class="paging"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->
</body>
<script>
</script>
</html>
