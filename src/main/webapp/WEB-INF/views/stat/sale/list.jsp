<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="utils" scope="request" class="com.pntmall.common.utils.Utils"/>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script type="text/javascript" src="/static/js/jquery.mtz.monthpicker.js"></script>
<script>
	var dates = new Array();

	/* MonthPicker 옵션 */
	var options = {
		pattern: 'yyyy.mm', // Default is 'mm/yyyy' and separator char is not mandatory
// 		selectedYear: year,
		startYear: 2021,
		finalYear: new Date().getFullYear(),
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
	};

	$(function() {
		initdates();

		<c:if test='${ param.gubun eq 3 }'>
		$("#sdate").monthpicker(options);
		$("#edate").monthpicker(options);
		</c:if>
		<c:if test='${ param.gubun ne 3 }'>
		$("#sdate").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		$("#edate").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		</c:if>

		$("#resetBtn").click(function() {
			document.location.href = "list";
		});

// 		$("#sdate").val('${ param.sdate }' == '' ? dates[0] : '${ param.sdate }' );
// 		$("#edate").val('${ param.edate }' == '' ? dates[0] : '${ param.edate }' );
		$("#sdate").val('${ sdate }');
		$("#edate").val('${ edate }');

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
		today.setMonth(today.getMonth() - 3);
		dates.push(formatDate(today));
		today.setMonth(today.getMonth() - 6);
		dates.push(formatDate(today));

// 		console.log("dates", dates);

	}

	function formatDate(date) {
		return date.getFullYear() + '.' + (date.getMonth() < 9 ? '0' : '') + (date.getMonth() + 1) + '.' + (date.getDate() < 10 ? '0' : '') + date.getDate();
	}

	function setDate(n) {
		<c:if test='${ param.gubun eq 3 }'>
		$("#sdate").val(dates[n].substring(0, 7));
		$("#edate").val(dates[0].substring(0, 7));
		</c:if>
		<c:if test='${ param.gubun ne 3 }'>
		$("#sdate").val(dates[n]);
		$("#edate").val(dates[0]);
		</c:if>
	}

	function goExcel() {
		$("#searchForm").attr("action", "excel");
		$("#searchForm").submit();
		$("#searchForm").attr("action", "");
	}

	function goList(v) {
		$("input[name=gubun]").val(v);
		$("#searchForm").submit();
	}
</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="searchForm" id="searchForm">
			<input type="hidden" name="gubun" value="${ param.gubun }">
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
						<c:if test='${ param.gubun ne 3 }'>
						<a href="#" onclick="setDate(0); return false;" class="btnSizeC">금일</a>
						<a href="#" onclick="setDate(1); return false;" class="btnSizeC">어제</a>
						<a href="#" onclick="setDate(2); return false;" class="btnSizeC">7일</a>
						<a href="#" onclick="setDate(3); return false;" class="btnSizeC">15일</a>
						</c:if>
						<a href="#" onclick="setDate(4); return false;" class="btnSizeC">1개월</a>
						<a href="#" onclick="setDate(5); return false;" class="btnSizeC">3개월</a>
						<a href="#" onclick="setDate(6); return false;" class="btnSizeC">6개월</a>
						<a href="#" onclick="setDate(7); return false;" class="btnSizeC">12개월</a>
					</td>
				</tr>
				<tr>
					<th>구분</th>
					<td colspan=3>
						<label><input type="checkbox" name="orderGubun" value="4" ${ param.orderGubun eq '4' ? 'checked' : '' }> <span>닥터팩</span></label>
					</td>
				</tr>
			</table>
			<div class="btnArea ac">
				<input type="submit" class="btnTypeA btnSizeA" value="검색">
				<input type="button" class="btnTypeB btnSizeA" id="resetBtn" value="초기화">
			</div>
		</div>
		<ul class="tabMenu">
			<li ${ param.gubun eq 1 ? 'class="on"' : '' }><a href="list?gubun=1">일별 현황</a></li>
			<li ${ param.gubun eq 2 ? 'class="on"' : '' }><a href="list?gubun=2">시간대별 현황</a></li>
			<li ${ param.gubun eq 3 ? 'class="on"' : '' }><a href="list?gubun=3">월별 현황</a></li>
		</ul>

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
			</colgroup>
			<tbody>
				<tr>
					<th colspan="2">총 매출금액</th>
					<th>최대 매출금액</th>
					<th>최소 매출금액</th>
					<th colspan="2">PC 매출금액</th>
					<th colspan="2">모바일 매출금액</th>
					<th colspan="2">앱 매출금액</th>
				</tr>
				<tr>
					<th colspan="2" id="totSaleAmt">0</th>
					<td rowspan="3" id="maxSaleAmt">0</td>
					<td rowspan="3" id="minSaleAmt">0</td>
					<th colspan="2" id="pcTotSaleAmt">0</th>
					<th colspan="2" id="moTotSaleAmt">0</th>
					<th colspan="2" id="apTotSaleAmt">0</th>
				</tr>
				<tr>
					<th>판매금액</th>
					<td id="saleAmt">0</td>
					<th>판매금액</th>
					<td id="pcSaleAmt">0</td>
					<th>판매금액</th>
					<td id="moSaleAmt">0</td>
					<th>판매금액</th>
					<td id="apSaleAmt">0</td>
				</tr>
				<tr>
					<th>환불금액</th>
					<td id="refundAmt">0</td>
					<th>환불금액</th>
					<td id="pcRefundAmt">0</td>
					<th>환불금액</th>
					<td id="moRefundAmt">0</td>
					<th>환불금액</th>
					<td id="apRefundAmt">0</td>
				</tr>
			</tbody>
		</table>

		<div class="listHead">
			<div class="fl">
				<div class="totalArea">
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
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th rowspan="2">${ param.gubun eq 1 ? "날짜" : (param.gubun eq 2 ? "시간" : "월") }</th>
					<th rowspan="2">구분</th>
					<th rowspan="2">매출금액</th>
					<th colspan="7">판매금액</th>
					<th colspan="1">환불금액</th>
				</tr>
				<tr>
					<th>상품판매가</th>
					<th>상품할인(-)</th>
					<th>결제금액</th>
					<th>배송비</th>
					<th>배송비할인(-)</th>
					<th>결제금액</th>
					<th>판매총액</th>
					<th>환불총액(-)</th>
				</tr>
			</thead>
			<tbody>
				<c:set var='totSaleAmt' value='0' />
				<c:set var='pcTotSaleAmt' value='0' />
				<c:set var='moTotSaleAmt' value='0' />
				<c:set var='apTotSaleAmt' value='0' />
				
				<c:set var='saleAmt' value='0' />
				<c:set var='pcSaleAmt' value='0' />
				<c:set var='moSaleAmt' value='0' />
				<c:set var='apSaleAmt' value='0' />

				<c:set var='refundAmt' value='0' />
				<c:set var='pcRefundAmt' value='0' />
				<c:set var='moRefundAmt' value='0' />
				<c:set var='apRefundAmt' value='0' />
				
				<c:set var='maxSaleAmt' value='0' />
				<c:set var='maxSaleDate' value='' />

				<c:set var='minSaleAmt' value='${ Integer.MAX_VALUE }' />
				<c:set var='minSaleDate' value='' />
				
				<c:forEach items="${ list }" var="row">
					<c:set var='totSaleAmt' value="${ totSaleAmt + row.getInt('pay_amt') + row.getInt('ship_pay_amt') - row.getInt('refund') }" />
					<c:set var='pcTotSaleAmt' value="${ pcTotSaleAmt + row.getInt('pc_pay_amt') + row.getInt('pc_ship_pay_amt') - row.getInt('pc_refund') }" />
					<c:set var='moTotSaleAmt' value="${ moTotSaleAmt + row.getInt('mo_pay_amt') + row.getInt('mo_ship_pay_amt') - row.getInt('mo_refund') }" />
					<c:set var='apTotSaleAmt' value="${ apTotSaleAmt + row.getInt('ap_pay_amt') + row.getInt('ap_ship_pay_amt') - row.getInt('ap_refund') }" />

					<c:set var='saleAmt' value="${ saleAmt + row.getInt('pay_amt') + row.getInt('ship_pay_amt') }" />
					<c:set var='pcSaleAmt' value="${ pcSaleAmt + row.getInt('pc_pay_amt') + row.getInt('pc_ship_pay_amt') }" />
					<c:set var='moSaleAmt' value="${ moSaleAmt + row.getInt('mo_pay_amt') + row.getInt('mo_ship_pay_amt') }" />
					<c:set var='apSaleAmt' value="${ apSaleAmt + row.getInt('ap_pay_amt') + row.getInt('ap_ship_pay_amt') }" />
					
					<c:set var='refundAmt' value="${ refundAmt + row.getInt('refund') }" />
					<c:set var='pcRefundAmt' value="${ pcRefundAmt + row.getInt('pc_refund') }" />
					<c:set var='moRefundAmt' value="${ moRefundAmt + row.getInt('mo_refund') }" />
					<c:set var='apRefundAmt' value="${ apRefundAmt + row.getInt('ap_refund') }" />

					<c:if test="${ maxSaleAmt < row.getInt('pay_amt') + row.getInt('ship_pay_amt') - row.getInt('refund') }">
						<c:set var='maxSaleAmt' value="${ row.getInt('pay_amt') + row.getInt('ship_pay_amt') - row.getInt('refund') }" />
						<c:set var='maxSaleDate' value="${ row.get('odate') }" />
					</c:if>
					<c:if test="${ minSaleAmt > row.getInt('pay_amt') + row.getInt('ship_pay_amt') - row.getInt('refund') }">
						<c:set var='minSaleAmt' value="${ row.getInt('pay_amt') + row.getInt('ship_pay_amt') - row.getInt('refund') }" />
						<c:set var='minSaleDate' value="${ row.get('odate') }" />
					</c:if>
					
					<tr>
						<td rowspan="4">${ row.get('odate') }</td>
						<td>PC</td>
						<td class="ar"><fmt:formatNumber value="${ row.getInt('pc_pay_amt') + row.getInt('pc_ship_pay_amt') - row.getInt('pc_refund') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('pc_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('pc_discount') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('pc_pay_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('pc_ship_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('pc_ship_discount') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('pc_ship_pay_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.getInt('pc_pay_amt') + row.getInt('pc_ship_pay_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('pc_refund') }" pattern="#,###" /></td>
					</tr>
					<tr>
						<td>모바일</td>
						<td class="ar"><fmt:formatNumber value="${ row.getInt('mo_pay_amt') + row.getInt('mo_ship_pay_amt') - row.getInt('mo_refund') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('mo_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('mo_discount') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('mo_pay_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('mo_ship_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('mo_ship_discount') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('mo_ship_pay_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.getInt('mo_pay_amt') + row.getInt('mo_ship_pay_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('mo_refund') }" pattern="#,###" /></td>
					</tr>
					<tr>
						<td>앱</td>
						<td class="ar"><fmt:formatNumber value="${ row.getInt('ap_pay_amt') + row.getInt('ap_ship_pay_amt') - row.getInt('ap_refund') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('ap_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('ap_discount') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('ap_pay_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('ap_ship_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('ap_ship_discount') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('ap_ship_pay_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.getInt('ap_pay_amt') + row.getInt('ap_ship_pay_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('ap_refund') }" pattern="#,###" /></td>
					</tr>
					<tr>
						<td>총액</td>
						<td class="ar"><fmt:formatNumber value="${ row.getInt('pay_amt') + row.getInt('ship_pay_amt') - row.getInt('refund') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('discount') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('pay_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('ship_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('ship_discount') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('ship_pay_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.getInt('pay_amt') + row.getInt('ship_pay_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('refund') }" pattern="#,###" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
		</div>
		<div class="paging"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
<script>
	<c:if test='${ fn:length(list) > 0 }'>
	$("#totSaleAmt").html('${ totSaleAmt }'.formatMoney());
	$("#pcTotSaleAmt").html('${ pcTotSaleAmt }'.formatMoney());
	$("#moTotSaleAmt").html('${ moTotSaleAmt }'.formatMoney());
	$("#apTotSaleAmt").html('${ apTotSaleAmt }'.formatMoney());

	$("#saleAmt").html('${ saleAmt }'.formatMoney());
	$("#pcSaleAmt").html('${ pcSaleAmt }'.formatMoney());
	$("#moSaleAmt").html('${ moSaleAmt }'.formatMoney());
	$("#apSaleAmt").html('${ apSaleAmt }'.formatMoney());
	
	$("#refundAmt").html('${ refundAmt }'.formatMoney());
	$("#pcRefundAmt").html('${ pcRefundAmt }'.formatMoney());
	$("#moRefundAmt").html('${ moRefundAmt }'.formatMoney());
	$("#apRefundAmt").html('${ apRefundAmt }'.formatMoney());
	
	$("#maxSaleAmt").html('${ maxSaleAmt }'.formatMoney() + '<br>' + '${ maxSaleDate }');
	$("#minSaleAmt").html('${ minSaleAmt }'.formatMoney() + '<br>' + '${ minSaleDate }');
	</c:if>
</script>
</html>
