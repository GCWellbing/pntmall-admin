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
					<th>쿠폰유형</th>
					<td>
						<select name="gubun">
							<option value="" ${ param.gubun eq '' ? 'selected' : '' }>전체</option>
							<option value="1" ${ param.gubun eq '1' ? 'selected' : '' }>제품쿠폰</option>
							<option value="2" ${ param.gubun eq '2' ? 'selected' : '' }>배송비쿠폰</option>
						</select>
					</td>
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
					<th>쿠폰명</th>
					<td>
						<input type="text" name="title" value="${ param.title }" style="width:100%" >
					</td>
					<th>아이디</th>
					<td>
						<input type="text" name="memId" value="${ param.memId }" style="width:100%" >
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
				<a href="javascript:goExcel()" class="btnTypeC btnSizeA"><span>엑셀 다운로드</span></a>
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
					<th>쿠폰유형</th>
					<th>쿠폰명</th>
					<th>아이디(이름)</th>
					<th>주문번호</th>
					<th>쿠폰할인금액</th>
					<th>발급일</th>
					<th>만료일</th>
					<th>사용일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">						
				<tr>
					<td><c:out value="${ row.gubun eq 1 ? '제품쿠폰' : '배송비쿠폰' }" /></td>
					<td><c:out value="${ row.title }" /></td>
					<td><c:out value="${ row.memId }" />(<c:out value="${ row.memName }" />)</td>
					<td><a href="/order/order/edit?orderid=<c:out value="${ row.orderid }" />"><c:out value="${ row.orderid }" /></a></td>
					<td><fmt:formatNumber value="${ row.discount }" pattern="#,###" /><c:out value="${ row.discountType eq 1 ? '원' : '%' }" /></td>
					<td><fmt:formatDate value="${ row.cdate }" pattern="${ DateTimeFormat }" /></td>
					<td><c:out value="${ row.expire }" /></td>
					<td><fmt:formatDate value="${ row.useDate }" pattern="${ DateTimeFormat }" /></td>
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
