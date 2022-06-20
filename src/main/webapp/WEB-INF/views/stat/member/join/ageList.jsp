<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="utils" scope="request" class="com.pntmall.common.utils.Utils"/>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
	var dates = new Array();

	$(function() {
		initdates();

		$("#sdate").datepicker();
		$("#edate").datepicker();

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

// 		console.log("dates", dates);

	}

	function formatDate(date) {
		return date.getFullYear() + '.' + (date.getMonth() < 9 ? '0' : '') + (date.getMonth() + 1) + '.' + (date.getDate() < 10 ? '0' : '') + date.getDate();
	}

	function setDate(n) {
		if(n == 7){
			var today = new Date();
			var firstDate, lastDate;
			today.setMonth(today.getMonth() - 1);
			firstDate = new Date(today.getFullYear(), today.getMonth(), 1);
			lastDate = new Date(today.getFullYear(), today.getMonth()+1, 0);
			$("#sdate").val(formatDate(firstDate));
			$("#edate").val(formatDate(lastDate));
		}else{
			$("#sdate").val(dates[n]);
			$("#edate").val(dates[0]);
		}
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
		$("#searchForm").attr("action", "downloadExcelAge");
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
					<th>기간</th>
					<td colspan=3>
						<div class="dateBox">
							<input type="text" name="sdate" id="sdate" readonly value="${ param.sdate }" style="width:110px">
						</div> ~
						<div class="dateBox">
							<input type="text" name="edate" id="edate" readonly value="${ param.edate }" style="width:110px">
						</div>
						<a href="#" onclick="setDate(1); return false;" class="btnSizeC">어제</a>
						<a href="#" onclick="setDate(7); return false;" class="btnSizeC">전월</a>
						<a href="#" onclick="setDate(2); return false;" class="btnSizeC">7일</a>
						<a href="#" onclick="setDate(3); return false;" class="btnSizeC">15일</a>
						<a href="#" onclick="setDate(4); return false;" class="btnSizeC">1개월</a>
						<a href="#" onclick="setDate(5); return false;" class="btnSizeC">3개월</a>
						<a href="#" onclick="setDate(6); return false;" class="btnSizeC">6개월</a>
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
			<li><a href="monthList">월별 현황</a></li>
			<li><a href="genderList">성별 현황</a></li>
			<li class="on"><a href="ageList">연령별 현황</a></li>
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
			</colgroup>
			<tbody>
				<tr>
					<th>총 회원수</th>
					<th>10대</th>
					<th>20대</th>
					<th>30대</th>
					<th>40대</th>
					<th>50대</th>
					<th>60대</th>
					<th>70대</th>
					<th>미확인</th>
				</tr>
				<tr>
					<td id="total"></td>
					<td id="10"></td>
					<td id="20"></td>
					<td id="30"></td>
					<td id="40"></td>
					<td id="50"></td>
					<td id="60"></td>
					<td id="70"></td>
					<td id="non"></td>
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
					<col width="">
					<col width="">
				</colgroup>
				<thead>
					<tr>
						<th>날짜</th>
						<th>총 회원수</th>
						<th>10대</th>
						<th>20대</th>
						<th>30대</th>
						<th>40대</th>
						<th>50대</th>
						<th>60대</th>
						<th>70대</th>
						<th>미확인</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="totalCnt" value="${total.get('BEFORE_CNT')}"/>
					<c:set var="Cnt10" value="${total.get('BEFORE_CNT10')}"/>
					<c:set var="Cnt20" value="${total.get('BEFORE_CNT20')}"/>
					<c:set var="Cnt30" value="${total.get('BEFORE_CNT30')}"/>
					<c:set var="Cnt40" value="${total.get('BEFORE_CNT40')}"/>
					<c:set var="Cnt50" value="${total.get('BEFORE_CNT50')}"/>
					<c:set var="Cnt60" value="${total.get('BEFORE_CNT60')}"/>
					<c:set var="Cnt70" value="${total.get('BEFORE_CNT70')}"/>
					<c:set var="nonCnt" value="${total.get('BEFORE_NON_CNT')}"/>
					<c:forEach items="${ list }" var="row">
						<tr>
							<c:set var="totalCnt" value="${totalCnt+row.get('DAY_CNT')}"/>
							<c:set var="Cnt10" value="${Cnt10+row.get('CNT10')}"/>
							<c:set var="Cnt20" value="${Cnt20+row.get('CNT20')}"/>
							<c:set var="Cnt30" value="${Cnt30+row.get('CNT30')}"/>
							<c:set var="Cnt40" value="${Cnt40+row.get('CNT40')}"/>
							<c:set var="Cnt50" value="${Cnt50+row.get('CNT50')}"/>
							<c:set var="Cnt60" value="${Cnt60+row.get('CNT60')}"/>
							<c:set var="Cnt70" value="${Cnt70+row.get('CNT70')}"/>
							<c:set var="nonCnt" value="${nonCnt+row.get('NON_CNT')}"/>
							<td>${row.get('YMD')}</td>
							<td><fmt:formatNumber value="${ totalCnt }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ Cnt10 }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ Cnt20 }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ Cnt30 }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ Cnt40 }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ Cnt50 }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ Cnt60 }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ Cnt70 }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ nonCnt }" pattern="#,###" /></td>
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
	<c:if test='${ fn:length(list) > 0 }'>
		$("#total").html('${ totalCnt }'.formatMoney());
		$("#10").html('${ Cnt10 }'.formatMoney());
		$("#20").html('${ Cnt20 }'.formatMoney());
		$("#30").html('${ Cnt30 }'.formatMoney());
		$("#40").html('${ Cnt40 }'.formatMoney());
		$("#50").html('${ Cnt50 }'.formatMoney());
		$("#60").html('${ Cnt60 }'.formatMoney());
		$("#70").html('${ Cnt70 }'.formatMoney());
		$("#non").html('${ nonCnt }'.formatMoney());

	</c:if>
</script>
<script>
const ctx = document.getElementById('myChart');

var xValues = [];
var yValuesTotal = [];
var yValues10 = [];
var yValues20 = [];
var yValues30 = [];
var yValues40 = [];
var yValues50 = [];
var yValues60 = [];
var yValues70 = [];
var yValuesN = [];

/*
var yMin = 0;
var yMax = 50;
var y1Min = 19000;
var y1Max = 20000;
*/

<c:set var="totalCnt" value="${total.get('BEFORE_CNT')}"/>
<c:set var="Cnt10" value="${total.get('BEFORE_CNT10')}"/>
<c:set var="Cnt20" value="${total.get('BEFORE_CNT20')}"/>
<c:set var="Cnt30" value="${total.get('BEFORE_CNT30')}"/>
<c:set var="Cnt40" value="${total.get('BEFORE_CNT40')}"/>
<c:set var="Cnt50" value="${total.get('BEFORE_CNT50')}"/>
<c:set var="Cnt60" value="${total.get('BEFORE_CNT60')}"/>
<c:set var="Cnt70" value="${total.get('BEFORE_CNT70')}"/>
<c:set var="nonCnt" value="${total.get('BEFORE_NON_CNT')}"/>
<c:forEach items="${ list }" var="row">
	<c:set var="totalCnt" value="${totalCnt+row.get('DAY_CNT')}"/>
	<c:set var="Cnt10" value="${Cnt10+row.get('CNT10')}"/>
	<c:set var="Cnt20" value="${Cnt20+row.get('CNT20')}"/>
	<c:set var="Cnt30" value="${Cnt30+row.get('CNT30')}"/>
	<c:set var="Cnt40" value="${Cnt40+row.get('CNT40')}"/>
	<c:set var="Cnt50" value="${Cnt50+row.get('CNT50')}"/>
	<c:set var="Cnt60" value="${Cnt60+row.get('CNT60')}"/>
	<c:set var="Cnt70" value="${Cnt70+row.get('CNT70')}"/>
	<c:set var="nonCnt" value="${nonCnt+row.get('NON_CNT')}"/>
		xValues.push("${row.get('YMD')}");
		yValues10.push("${Cnt10}");
		yValues20.push("${Cnt20}");
		yValues30.push("${Cnt30}");
		yValues40.push("${Cnt40}");
		yValues50.push("${Cnt50}");
		yValues60.push("${Cnt60}");
		yValues70.push("${Cnt70}");
		yValuesN.push("${nonCnt}");
		yValuesTotal.push("${totalCnt}");
</c:forEach>

new Chart("myChart", {
	type: "line",
	data: {
		labels: xValues,
		datasets: [{
			label: '10대',
			data: yValues10,
			borderColor: "#4279BE",
			backgroundColor: "#4279BE",
			fill: false,
			yAxisID: 'y'
		},{
			label: '20대',
			data: yValues20,
			borderColor: "#27993A",
			backgroundColor: "#27993A",
			fill: false,
			yAxisID: 'y'
		},{
			label: '30대',
			data: yValues30,
			borderColor: "#E1846",
			backgroundColor: "#E1846",
			fill: false,
			yAxisID: 'y'
		},{
			label: '40대',
			data: yValues40,
			borderColor: "#eeb432",
			backgroundColor: "#eeb432",
			fill: false,
			yAxisID: 'y'
		},{
			label: '50대',
			data: yValues50,
			borderColor: "#97db64",
			backgroundColor: "#97db64",
			fill: false,
			yAxisID: 'y'
		},{
			label: '60대',
			data: yValues60,
			borderColor: "#d075a7",
			backgroundColor: "#d075a7",
			fill: false,
			yAxisID: 'y'
		},{
			label: '70대',
			data: yValues70,
			borderColor: "#99470a",
			backgroundColor: "#99470a",
			fill: false,
			yAxisID: 'y'
		},{
			label: '미확인',
			data: yValuesN,
			borderColor: "#888888",
			backgroundColor: "#888888",
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
</html>
