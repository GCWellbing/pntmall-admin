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
	now.setFullYear(now.getFullYear() + 1);
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
		$("#searchForm").attr("action", "downloadExcelClinicVisit");
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
		</ul>

		<table class="list">
			<colgroup>
				<col width="">
			</colgroup>
			<tbody>
				<tr>
					<th>총 방문자수</th>
				</tr>
				<tr>
					<td id="clinic"></td>
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
				</colgroup>
				<thead>
					<tr>
						<th>날짜</th>
						<th>총 방문자수</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="clinicCnt" value="0"/>
					<c:forEach items="${ list }" var="row">
						<c:set var="clinicCnt" value="${ clinicCnt + row.get('CLINIC_CNT') }"/>
						<tr>
							<td>${ row.get('YMD')}</td>
							<td><fmt:formatNumber value="${ row.get('CLINIC_CNT') }" pattern="#,###" /></td>
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
		$("#clinic").html('${ clinicCnt }'.formatMoney());

	</c:if>
</script>
<script>
const ctx = document.getElementById('myChart');

var xValues = [];
var yValuesTotal = [];

<c:forEach items="${ list }" var="row">
	xValues.push("${row.get('YMD')}");
	yValuesTotal.push("${row.get('CLINIC_CNT')}");
</c:forEach>

new Chart("myChart", {
	type: "line",
	data: {
		labels: xValues,
		datasets: [{
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
