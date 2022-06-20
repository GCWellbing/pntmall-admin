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

	function uploadExcel() {
		if($("#excelForm input[name=excel]").val() != '') {
	        ef.multipart($("#excelForm"), "/order/pg/upload", "excel", function(result) {
	        	if(result.succeed) document.location.reload();
	        	enableScreen();
	        });
		}
	}

	function setCheckAll(b) {
		$("input[name=sno]").each(function() {
			$(this).prop("checked", b);
		});
	}

	function sap() {
		if($("input[name=sno]:checked").length == 0) {
			alert("전송할 데이터를 선택하세요.");
		} else {
			disableScreen();
			ef.proc($("#listForm"), function(result) {
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
					<th>지급일자</th>
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

		<form name="listForm" id="listForm" action="sap" method="post">
		<div class="overListWrap">
			<table class="list">
				<colgroup>
					<col width="30">
					<col width="60">
					<col width="90">
					<col width="90">
					<col width="130">

					<col width="100">
					<col width="100">
					<col width="60">
					<col width="80">
					<col width="90">

					<col width="140">
					<col width="90">
					<col width="90">
					<col width="90">
					<col width="140">

					<col width="100">
					<col width="100">
					<col width="100">
					<col width="120">
					<col width="300">

					<col width="220">
					<col width="90">
					<col width="100">
				</colgroup>
				<thead>
					<tr>
						<th><input type="checkbox" id="checkAll" onclick="setCheckAll(this.checked)"></th>
						<th>No.</th>
						<th>지급일자</th>
						<th>매출일자</th>
						<th>상점ID</th>
						<th>결제수단</th>
						<th>결제상태</th>
						<th>코드</th>
						<th>결제기관</th>
						<th>결제일자</th>
						<th>주문번호</th>
						<th>금액</th>
						<th>수수료</th>
						<th>당기발생액</th>
						<th>승인번호</th>
						<th>구매자</th>
						<th>구매자ID</th>
						<th>매입/취소요청일</th>
						<th>비고</th>
						<th>상품정보</th>
						<th>거래번호</th>
						<th>SAP전송일</th>
						<th>SAP전송결과</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ list }" var="row">
					<tr>
						<td><input type="checkbox" name="sno" value="${ row.sno }"></td>
						<td><c:out value="${ row.sno }" /></td>
						<td><c:out value="${ row.issueDate }" /></td>
						<td><c:out value="${ row.saleDate }" /></td>
						<td><c:out value="${ row.shopId }" /></td>
						<td><c:out value="${ row.payType }" /></td>
						<td><c:out value="${ row.payStatus }" /></td>
						<td><c:out value="${ row.code }" /></td>
						<td><c:out value="${ row.payOrgan }" /></td>
						<td><c:out value="${ row.payDate }" /></td>
						<td><c:out value="${ row.orderid }" /></td>
						<td><fmt:formatNumber value="${ row.amt }" pattern="#,###" /></td>
						<td><fmt:formatNumber value="${ row.fee }" pattern="#,###" /></td>
						<td><fmt:formatNumber value="${ row.issueAmt }" pattern="#,###" /></td>
						<td><c:out value="${ row.authNo }" /></td>
						<td><c:out value="${ row.buyer }" /></td>
						<td><c:out value="${ row.buyerId }" /></td>
						<td><c:out value="${ row.reqDate }" /></td>
						<td><c:out value="${ row.bigo }" /></td>
						<td><c:out value="${ row.pname }" /></td>
						<td><c:out value="${ row.dealNo }" /></td>
						<td><fmt:formatDate value="${ row.sapDate }" pattern="${ DateTimeFormat }" /></td>
						<td><c:out value="${ row.sapResult }" /></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		</form>
		<div class="btnArea">
			<div class="fl">
				<input type="button" onclick="sap()" class="btnTypeC btnSizeA" value="SAP 전송">
 			</div>
			<form name="excelForm" id="excelForm" enctype="multipart/form-data" method="post" action="upload">
				<input type="file" name="excel" style="width:300px" accept=".xlsx">
				<input type="button" onclick="uploadExcel()" class="btnTypeC btnSizeA" value="파일 업로드">
			</form>
		</div>
		<div class="paging"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
