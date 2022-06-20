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
		$("#searchForm").attr("action", "downloadExcelVisit");
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
		<input type="hidden" name="gubun" value="daily">
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
			<li class="on"><a href="list">일별 현황</a></li>
			<li><a href="monthList">월별 현황</a></li>
		</ul>

		<table class="list">
			<colgroup>
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<tbody>
				<tr>
					<th>총 방문자수</th>
					<th>PC 방문자수</th>
					<th>모바일 방문자수</th>
					<th>APP 방문자수</th>
				</tr>
				<tr>
					<td id="total"></td>
					<td id="pc"></td>
					<td id="mo"></td>
					<td id="app"></td>
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
				</colgroup>
				<thead>
					<tr>
						<th>날짜</th>
						<th>총 방문자수</th>
						<th>PC 방문자수</th>
						<th>모바일 방문자수</th>
						<th>APP 방문자수</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="totalCnt" value="0"/>
					<c:set var="totalPcCnt" value="0"/>
					<c:set var="totalMoCnt" value="0"/>
					<c:set var="totalAppCnt" value="0"/>
					<c:forEach items="${ list }" var="row">
						<c:set var="totalCnt" value="${ totalCnt + row.get('DAY_CNT') }"/>
						<c:set var="totalPcCnt" value="${ totalPcCnt + row.get('PC_CNT') }"/>
						<c:set var="totalMoCnt" value="${ totalMoCnt + row.get('MO_CNT') }"/>
						<c:set var="totalAppCnt" value="${ totalAppCnt + row.get('APP_CNT') }"/>
						<tr>
							<td>${ row.get('YMD')}</td>
							<td><fmt:formatNumber value="${ row.get('DAY_CNT') }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ row.get('PC_CNT') }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ row.get('MO_CNT') }" pattern="#,###" /></td>
							<td><fmt:formatNumber value="${ row.get('APP_CNT') }" pattern="#,###" /></td>
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
		$("#pc").html('${ totalPcCnt }'.formatMoney());
		$("#mo").html('${ totalMoCnt }'.formatMoney());
		$("#app").html('${ totalAppCnt }'.formatMoney());

	</c:if>
</script>
<script>
const ctx = document.getElementById('myChart');

var xValues = [];
var yValuesPc = [];
var yValuesMo = [];
var yValuesApp = [];
var yValuesTotal = [];

<c:forEach items="${ list }" var="row">
		xValues.push("${row.get('YMD')}");
		yValuesPc.push("${row.get('PC_CNT')}");
		yValuesMo.push("${row.get('MO_CNT')}");
		yValuesApp.push("${row.get('APP_CNT')}");
		yValuesTotal.push("${row.get('DAY_CNT')}");
</c:forEach>

new Chart("myChart", {
	type: "line",
	data: {
		labels: xValues,
		datasets: [{
			label: 'PC 방문자수',
			data: yValuesPc,
			borderColor: "#4279BE",
			backgroundColor: "#4279BE",
			fill: false,
			yAxisID: 'y'
		},{
			label: '모바일 방문자수',
			data: yValuesMo,
			borderColor: "#27993A",
			backgroundColor: "#27993A",
			fill: false,
			yAxisID: 'y'
		},{
			label: 'APP 방문자수',
			data: yValuesApp,
			borderColor: "#E1846",
			backgroundColor: "#E1846",
			fill: false,
			yAxisID: 'y'
		},{
			label: '총 방문자수',
			data: yValuesTotal,
			borderColor: "#FF5A00",
			backgroundColor: "#FF5A00",
			fill: false,
			yAxisID: 'y'
		}]
	},
	options: {
		scales: {
			y: {
				type: 'linear',
				display: true,
				position: 'left'
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
