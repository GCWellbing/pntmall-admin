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
				<col width="">
				<col width="">
			</colgroup>
			<tbody>
				<tr>
					<th colspan="2">총 판매금액</th>
					<th colspan="2">총 구매건수</th>
					<th colspan="2">총 구매자수</th>
					<th colspan="2">총 구매개수</th>
					<th colspan="2">최대/최소 판매금액</th>
					<th colspan="2">최대/최소 구매건수</th>
				</tr>
				<tr>
					<th colspan="2" id="amt">0</th>
					<td colspan="2" id="cnt">0</td>
					<td colspan="2" id="memCnt">0</td>
					<td colspan="2" id="qty">0</td>
					<td colspan="2">&nbsp;</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<th>PC</th>
					<td id="pcAmt">0</td>
					<td>PC</td>
					<td id="pcCnt">0</td>
					<td>PC</td>
					<td id="pcMemCnt">0</td>
					<td>PC</td>
					<td id="pcQty">0</td>
					<td>최대</td>
					<td id="maxAmt">0</td>
					<td>최대</td>
					<td id="maxCnt">0</td>
				</tr>
				<tr>
					<th>모바일</th>
					<td id="moAmt">0</td>
					<td>모바일</td>
					<td id="moCnt">0</td>
					<td>모바일</td>
					<td id="moMemCnt">0</td>
					<td>모바일</td>
					<td id="moQty">0</td>
					<td>최소</td>
					<td id="minAmt">0</td>
					<td>최소</td>
					<td id="minCnt">0</td>
				</tr>
				<tr>
					<th>앱</th>
					<td id="apAmt">0</td>
					<td>앱</td>
					<td id="apCnt">0</td>
					<td>앱</td>
					<td id="apMemCnt">0</td>
					<td>앱</td>
					<td id="apQty">0</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
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
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th rowspan="2">${ param.gubun eq 1 ? "날짜" : (param.gubun eq 2 ? "시간" : "월") }</th>
					<th colspan="4">전체</th>
					<th colspan="4">PC</th>
					<th colspan="4">모바일</th>
					<th colspan="4">앱</th>
				</tr>
				<tr>
					<th>판매금액</th>
					<th>구매건수</th>
					<th>구매자수</th>
					<th>구매개수</th>
					<th>판매금액</th>
					<th>구매건수</th>
					<th>구매자수</th>
					<th>구매개수</th>
					<th>판매금액</th>
					<th>구매건수</th>
					<th>구매자수</th>
					<th>구매개수</th>
					<th>판매금액</th>
					<th>구매건수</th>
					<th>구매자수</th>
					<th>구매개수</th>
				</tr>
			</thead>
			<tbody>
				<c:set var='amt' value='0' />
				<c:set var='pcAmt' value='0' />
				<c:set var='moAmt' value='0' />
				<c:set var='apAmt' value='0' />
				
				<c:set var='cnt' value='0' />
				<c:set var='pcCnt' value='0' />
				<c:set var='moCnt' value='0' />
				<c:set var='apCnt' value='0' />

				<c:set var='memCnt' value='0' />
				<c:set var='pcMemCnt' value='0' />
				<c:set var='moMemCnt' value='0' />
				<c:set var='apMemCnt' value='0' />
				
				<c:set var='qty' value='0' />
				<c:set var='pcQty' value='0' />
				<c:set var='moQty' value='0' />
				<c:set var='apQty' value='0' />
				
				<c:set var='maxAmt' value='0' />
				<c:set var='minAmt' value='${ Integer.MAX_VALUE }' />
				<c:set var='maxCnt' value='0' />
				<c:set var='minCnt' value='${ Integer.MAX_VALUE }' />
				
				<c:forEach items="${ list }" var="row">
					<c:set var='amt' value="${ amt + row.getInt('amt') }" />
					<c:set var='pcAmt' value="${ pcAmt + row.getInt('pc_amt') }" />
					<c:set var='moAmt' value="${ moAmt + row.getInt('mo_amt') }" />
					<c:set var='apAmt' value="${ apAmt + row.getInt('ap_amt') }" />

					<c:set var='cnt' value="${ cnt + row.getInt('cnt') }" />
					<c:set var='pcCnt' value="${ pcCnt + row.getInt('pc_cnt') }" />
					<c:set var='moCnt' value="${ moCnt + row.getInt('mo_cnt') }" />
					<c:set var='apCnt' value="${ apCnt + row.getInt('ap_cnt') }" />

					<c:set var='memCnt' value="${ memCnt + row.getInt('mem_cnt') }" />
					<c:set var='pcMemCnt' value="${ pcMemCnt + row.getInt('pc_mem_cnt') }" />
					<c:set var='moMemCnt' value="${ moMemCnt + row.getInt('mo_mem_cnt') }" />
					<c:set var='apMemCnt' value="${ apMemCnt + row.getInt('ap_mem_cnt') }" />

					<c:set var='qty' value="${ qty + row.getInt('qty') }" />
					<c:set var='pcQty' value="${ pcQty + row.getInt('pc_qty') }" />
					<c:set var='moQty' value="${ moQty + row.getInt('mo_qty') }" />
					<c:set var='apQty' value="${ apQty + row.getInt('ap_qty') }" />

					<c:if test="${ maxAmt < row.getInt('amt') }">
						<c:set var='maxAmt' value="${ row.getInt('amt') }" />
					</c:if>
					<c:if test="${ minAmt > row.getInt('amt') }">
						<c:set var='minAmt' value="${ row.getInt('amt') }" />
					</c:if>
					
					<c:if test="${ maxCnt < row.getInt('cnt') }">
						<c:set var='maxCnt' value="${ row.getInt('cnt') }" />
					</c:if>
					<c:if test="${ minCnt > row.getInt('cnt') }">
						<c:set var='minCnt' value="${ row.getInt('cnt') }" />
					</c:if>
					<tr>
						<td>${ row.get('odate') }</td>
						<td class="ar"><fmt:formatNumber value="${ row.get('amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('cnt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('mem_cnt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('qty') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('pc_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('pc_cnt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('pc_mem_cnt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('pc_qty') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('mo_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('mo_cnt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('mo_mem_cnt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('mo_qty') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('ap_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('ap_cnt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('ap_mem_cnt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('ap_qty') }" pattern="#,###" /></td>
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
	$("#amt").html('${ amt }'.formatMoney());
	$("#pcAmt").html('${ pcAmt }'.formatMoney());
	$("#moAmt").html('${ moAmt }'.formatMoney());
	$("#apAmt").html('${ apAmt }'.formatMoney());

	$("#cnt").html('${ cnt }'.formatMoney());
	$("#pcCnt").html('${ pcCnt }'.formatMoney());
	$("#moCnt").html('${ moCnt }'.formatMoney());
	$("#apCnt").html('${ apCnt }'.formatMoney());

	$("#memCnt").html('${ memCnt }'.formatMoney());
	$("#pcMemCnt").html('${ pcMemCnt }'.formatMoney());
	$("#moMemCnt").html('${ moMemCnt }'.formatMoney());
	$("#apMemCnt").html('${ apMemCnt }'.formatMoney());

	$("#qty").html('${ qty }'.formatMoney());
	$("#pcQty").html('${ pcQty }'.formatMoney());
	$("#moQty").html('${ moQty }'.formatMoney());
	$("#apQty").html('${ apQty }'.formatMoney());

	$("#maxAmt").html('${ maxAmt }'.formatMoney());
	$("#minAmt").html('${ minAmt }'.formatMoney());
	$("#maxCnt").html('${ maxCnt }'.formatMoney());
	$("#minCnt").html('${ minCnt }'.formatMoney());
	</c:if>
	
</script>
</html>
