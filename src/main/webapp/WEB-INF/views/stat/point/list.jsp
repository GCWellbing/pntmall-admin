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
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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

		<c:if test='${ param.gubun eq 2 }'>
		$("#sdate").monthpicker(options);
		$("#edate").monthpicker(options);
		</c:if>
		<c:if test='${ param.gubun ne 2 }'>
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
		<c:if test='${ param.gubun eq 2 }'>
		$("#sdate").val(dates[n].substring(0, 7));
		$("#edate").val(dates[0].substring(0, 7));
		</c:if>
		<c:if test='${ param.gubun ne 2 }'>
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
						<c:if test='${ param.gubun ne 2 }'>
						<a href="#" onclick="setDate(1); return false;" class="btnSizeC">어제</a>
						<a href="#" onclick="setDate(7); return false;" class="btnSizeC">전월</a>
						<a href="#" onclick="setDate(2); return false;" class="btnSizeC">7일</a>
						<a href="#" onclick="setDate(3); return false;" class="btnSizeC">15일</a>
						</c:if>
						<a href="#" onclick="setDate(4); return false;" class="btnSizeC">1개월</a>
						<a href="#" onclick="setDate(5); return false;" class="btnSizeC">3개월</a>
						<a href="#" onclick="setDate(6); return false;" class="btnSizeC">6개월</a>
						<a href="#" onclick="setDate(7); return false;" class="btnSizeC">12개월</a>
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
			<li ${ param.gubun eq 2 ? 'class="on"' : '' }><a href="list?gubun=2">월별 현황</a></li>
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
			</colgroup>
			<tbody>
				<tr>
					<th>총 잔여 포인트</th>
					<th>총 지급건수</th>
					<th>총 지급금액</th>
					<th>총 사용건수</th>
					<th>총 사용금액</th>
					<th>총 소멸건수</th>
					<th>총 소멸금액</th>
				</tr>
				<tr>
					<td id="totPoint"></td>
					<td id="totCnt1"></td>
					<td id="totSum1"></td>
					<td id="totCnt2"></td>
					<td id="totSum2"></td>
					<td id="totCnt3"></td>
					<td id="totSum3"></td>
				</tr>
			</tbody>
		</table>

		<div>
			<canvas id="myChart" style="width:100%;height:500px"></canvas>
		</div>
		
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

		<div style="width:100%; height:400px; overflow:auto">
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
						<th>날짜</th>
						<th>잔여 포인트</th>
						<th>지급건수</th>
						<th>지급금액</th>
						<th>사용건수</th>
						<th>사용금액</th>
						<th>소멸건수</th>
						<th>소멸금액</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="totPoint" value="0"/>
					<c:set var="totCnt1" value="0"/>
					<c:set var="totSum1" value="0"/>
					<c:set var="totCnt2" value="0"/>
					<c:set var="totSum2" value="0"/>
					<c:set var="totCnt3" value="0"/>
					<c:set var="totSum3" value="0"/>
					<c:forEach items="${ list }" var="row">
						<c:set var="totPoint" value="${ row.get('point') }"/>
						<c:set var="totCnt1" value="${ totCnt1 + row.getInt('cnt1') }"/>
						<c:set var="totSum1" value="${ totSum1 + row.getInt('sum1') }"/>
						<c:set var="totCnt2" value="${ totCnt2 + row.getInt('cnt2') }"/>
						<c:set var="totSum2" value="${ totSum2 + row.getInt('sum2') }"/>
						<c:set var="totCnt3" value="${ totCnt3 + row.getInt('cnt3') }"/>
						<c:set var="totSum3" value="${ totSum3 + row.getInt('sum3') }"/>
						<tr>
							<td>${ row.get('cdate') }</td>
							<td><fmt:formatNumber value="${ row.get('point') }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ row.get('cnt1') }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ row.get('sum1') }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ row.get('cnt2') }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ row.get('sum2') }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ row.get('cnt3') }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ row.get('sum3') }" pattern="#,###" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="btnArea">
		</div>
		<div class="paging"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->
</body>

<script>
	$(function() {
		<c:if test='${ fn:length(list) > 0 }'>
		$("#totPoint").html('${ totPoint }'.formatMoney())
		$("#totCnt1").html('${ totCnt1 }'.formatMoney())
		$("#totSum1").html('${ totSum1 }'.formatMoney())
		$("#totCnt2").html('${ totCnt2 }'.formatMoney())
		$("#totSum2").html('-' + '${ totSum2 * -1 }'.formatMoney())
		$("#totCnt3").html('${ totCnt3 }'.formatMoney())
		$("#totSum3").html('-' + '${ totSum3 * -1 }'.formatMoney())
		</c:if>
	});

	const ctx = document.getElementById('myChart');
	var xValues = [
		<c:forEach items="${ list }" var="row">
		'${ row.get("cdate") }',
		</c:forEach>
		];

	new Chart("myChart", {
		type: "line",
		data: {
			labels: xValues,
			datasets: [{
				label: '잔여포인트',
				data: [
					<c:forEach items="${ list }" var="row">
						${ row.get("point") },
					</c:forEach>
					],
				borderColor: "#ed7d31",
				backgroundColor: "#ed7d31",
				fill: false,
				yAxisID: 'y'
			},{
				label: '지급 금액',
				data: [
					<c:forEach items="${ list }" var="row">
						${ row.get("sum1") },
					</c:forEach>
					],
				borderColor: "#a5a5a5",
				backgroundColor: "#a5a5a5",
				fill: false,
				yAxisID: 'y'
			},{
				label: '사용 금액',
				data: [
					<c:forEach items="${ list }" var="row">
						${ row.get("sum2") },
					</c:forEach>
					],
				borderColor: "#ffc000",
				backgroundColor: "#ffc000",
				fill: false,
				yAxisID: 'y'
			},{
				label: '소멸 금액',
				data: [
					<c:forEach items="${ list }" var="row">
						${ row.get("sum3") },
					</c:forEach>
					],
				borderColor: "#5b9bd5",
				backgroundColor: "#5b9bd5",
				fill: false,
				yAxisID: 'y'
			}]
		},
		options: {
			scales: {
				y: {
					type: 'linear',
					display: true,
					position: 'left',
// 					min:0,
// 					max:${ max + (max / 10) }
				}
			},
			plugins: {
				legend: {
					position: 'bottom'
				}
			}
		}
	});

</script>
</html>
