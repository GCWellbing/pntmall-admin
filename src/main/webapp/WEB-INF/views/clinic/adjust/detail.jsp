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
	});
</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<div class="listHead">
			<div class="fl">
				<div class="totalArea">
					<strong class="totalTit">Total</strong> <span class="totalCount"><fmt:formatNumber value="${ fn:length(list) }" pattern="#,###" /></span>
					<strong class="totalTit">정산금액</strong> <span class="totalAmt"><fmt:formatNumber value="${ adjustInfo.adjustAmt }" pattern="#,###" /></span>
				</div>
			</div>
		</div>

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
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th>주문번호</th>
					<th>구매확정일</th>
					<th>주문유형</th>
					<th>주문상태</th>
					<th>상품명</th>
					<th>총출하가</th>
					<th>총결제금액</th>
					<th>수량</th>
					<th>총품목금액</th>
					<th>포인트</th>
					<th>배송비</th>
					<th>할인금액</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
					<tr>
						<td><c:out value="${ row.orderid }" /></td>
						<td>
							<c:choose>
								<c:when test='${ row.gubun eq 3 }'>
									<fmt:formatDate value="${ row.returnDate }" pattern="${ DateFormat }" />
								</c:when>
								<c:otherwise>
									<fmt:formatDate value="${ row.confirmDate }" pattern="${ DateFormat }" />
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:if test='${ row.orderGubun eq 1 }'>일반</c:if>
							<c:if test='${ row.orderGubun eq 2 }'>정기배송</c:if>
							<c:if test='${ row.orderGubun eq 3 }'>병의원픽업</c:if>
							<c:if test='${ row.orderGubun eq 4 }'>닥터팩</c:if>
						</td>
						<td><c:out value="${ row.boName }" /></td>
						<td><c:out value="${ row.pname }" /><c:if test='${ row.itemCnt > 1 }'>외 ${ row.itemCnt - 1 }종</c:if></td>
						<td class="ar"><fmt:formatNumber value="${ row.status eq '380' ? row.supplyAmt * -1 : row.supplyAmt }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.payAmt }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.itemQty }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.status eq '380' ? row.saleAmt * -1 : row.saleAmt }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.point }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.shipAmt }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.totDiscount }" pattern="#,###" /></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="12" class="al">
						사업자등록번호: <c:out value='${ clinic.businessNo2 }' /> /
						대표자이름: <c:out value='${ clinic.businessOwner }' /> /
						은행국가: KR /
						은행키: <c:out value='${ bank }' /> /
						은행계정: <c:out value='${ clinic.account }' /> /
						예금주: <c:out value='${ clinic.depositor }' />
					</td>
				</tr>
			</tbody>
		</table>
		

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->
</body>
</html>
