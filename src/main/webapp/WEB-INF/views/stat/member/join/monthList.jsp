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

	var now = new Date();
	var year = now.getFullYear();
	var lastYear = now.getFullYear();

	$(function() {

		/* MonthPicker 옵션 */
		options = {
			pattern: 'yyyy.mm', // Default is 'mm/yyyy' and separator char is not mandatory
			selectedYear: year,
			startYear: 2017,
			finalYear: lastYear,
			monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
		};

		initdates();

		$("#sdate").monthpicker(options);
		$("#edate").monthpicker(options);

		$("#resetBtn").click(function() {
			document.location.href = "list";
		});

		$("#sdate").val('${ param.sdate }' == '' ? "${ sdate }" : '${ param.sdate }' );
		$("#edate").val('${ param.edate }' == '' ? "${ edate }" : '${ param.edate }' );

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
		$("#sdate").val(dates[n].substring(0,7));
		$("#edate").val(dates[0].substring(0,7));
	}

	function getCate2(v) {
		$.ajax({
			method : "GET",
			url : "/popup/cate2",
			data : { pcateNo : v },
			dataType : "html"
		})
		.done(function(html) {
			$("#cate2").empty();
			$("<option />", { "value" : "" }).text("전체").appendTo("#cate2");
			$("#cate2").append(html);
		});
	}

	function goExcel() {
		$("#searchForm").attr("action", "downloadExcel");
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
		<input type="hidden" name="gubun" value="monthly">
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
			<li><a href="list">일별 현황</a></li>
			<li class="on"><a href="monthList">월별 현황</a></li>
			<li><a href="genderList">성별 현황</a></li>
			<li><a href="ageList">연령별 현황</a></li>
		</ul>

		<table class="list">
			<colgroup>
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<tbody>
				<tr>
					<th rowspan="2">총 회원수</th>
					<th colspan="3">총 신규 회원수</th>
					<th rowspan="2">총 휴면 회원수</th>
					<th rowspan="2">총 탈퇴 회원수</th>
				</tr>
				<tr>
					<th>PC</th>
					<th>모바일</th>
					<th>APP</th>
				</tr>
				<tr>
					<td><fmt:formatNumber value="${ total.get('TOTAL_BEFORE') + total.get('SUM_DAY_CNT') }" pattern="#,###" /></td>
					<td><fmt:formatNumber value="${ total.get('SUM_PC_CNT') }" pattern="#,###" /></td>
					<td><fmt:formatNumber value="${ total.get('SUM_MO_CNT') }" pattern="#,###" /></td>
					<td><fmt:formatNumber value="${ total.get('SUM_APP_CNT') }" pattern="#,###" /></td>
					<td><fmt:formatNumber value="${ total.get('SUM_SLEEP_CNT') }" pattern="#,###" /></td>
					<td><fmt:formatNumber value="${ total.get('SUM_SECEDE_CNT') }" pattern="#,###" /></td>
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
				</colgroup>
				<thead>
					<tr>
						<th rowspan="2">날짜</th>
						<th rowspan="2">총 회원수</th>
						<th colspan="3">신규 회원수</th>
						<th rowspan="2">휴면 회원수</th>
						<th rowspan="2">탈퇴 회원수</th>
					</tr>
					<tr>
						<th>PC</th>
						<th>모바일</th>
						<th>APP</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="totalCnt" value="${total.get('TOTAL_BEFORE')}"/>
					<c:forEach items="${ list }" var="row">
						<tr>
							<td>${row.get('YMD')}</td>
							<c:set var="totalCnt" value="${totalCnt+row.get('DAY_CNT')}"/>
							<td><fmt:formatNumber value="${ totalCnt }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ row.get('PC_CNT') }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ row.get('MO_CNT') }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ row.get('APP_CNT') }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ row.get('SLEEP_CNT') }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ row.get('SECEDE_CNT') }" pattern="#,###" /></td>
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
<script>
const ctx = document.getElementById('myChart');

var xValues = [];
var yValuesPc = [];
var yValuesMo = [];
var yValuesApp = [];
var yValuesSleep = [];
var yValuesSecede = [];
var yValuesTotal = [];

<c:set var="totalCnt" value="${total.get('TOTAL_BEFORE')}"/>
<c:forEach items="${ list }" var="row">
	<c:set var="totalCnt" value="${totalCnt+row.get('DAY_CNT')}"/>
		xValues.push("${row.get('YMD')}");
		yValuesPc.push("${row.get('PC_CNT')}");
		yValuesMo.push("${row.get('MO_CNT')}");
		yValuesApp.push("${row.get('APP_CNT')}");
		yValuesSleep.push("${row.get('SLEEP_CNT')}");
		yValuesSecede.push("${row.get('SECEDE_CNT')}");
		yValuesTotal.push("${totalCnt}");
</c:forEach>

new Chart("myChart", {
	type: "line",
	data: {
		labels: xValues,
		datasets: [{
			label: 'PC 신규회원수',
			data: yValuesPc,
			borderColor: "#4279BE",
			backgroundColor: "#4279BE",
			fill: false,
			yAxisID: 'y'
		},{
			label: 'MOBILE 신규회원수',
			data: yValuesMo,
			borderColor: "#27993A",
			backgroundColor: "#27993A",
			fill: false,
			yAxisID: 'y'
		},{
			label: 'APP 신규회원수',
			data: yValuesApp,
			borderColor: "#E1846",
			backgroundColor: "#E1846",
			fill: false,
			yAxisID: 'y'
		},{
			label: '휴먼회원수',
			data: yValuesSleep,
			borderColor: "#eeb432",
			backgroundColor: "#eeb432",
			fill: false,
			yAxisID: 'y'
		},{
			label: '탈퇴회원수',
			data: yValuesSecede,
			borderColor: "#AAAAAA",
			backgroundColor: "#AAAAAA",
			fill: false,
			yAxisID: 'y'
		},{
			label: '총 회원수',
			data: yValuesTotal,
			borderColor: "#FF5A00",
			backgroundColor: "#FF5A00",
			fill: false,
			yAxisID: 'y1'
		}]
	},
	options: {
		scales: {
			y: {
				type: 'linear',
				display: true,
				position: 'left'
			},
			y1: {
				type: 'linear',
				display: true,
				grid: {
					display: false
				},
				position: 'right'
			},
		},
		plugins: {
			legend: {
				position: 'bottom'
			}
		}
	}
});
</script>
</body>
</html>
