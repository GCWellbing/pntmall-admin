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

		$("#sdate").monthpicker(options);
		$("#edate").monthpicker(options);

		$("#resetBtn").click(function() {
			document.location.href = "list2";
		});

		$("#sdate").val('${ param.sdate }' == '' ? dates[0] : '${ param.sdate }' );
		$("#edate").val('${ param.edate }' == '' ? dates[0] : '${ param.edate }' );

	});

	function initdates() {
		var today = new Date();
		dates.push(formatDate(today));
		today.setMonth(today.getMonth() - 1);
		dates.push(formatDate(today));
		today.setMonth(today.getMonth() - 2);
		dates.push(formatDate(today));
		today.setMonth(today.getMonth() - 3);
		dates.push(formatDate(today));
		today.setMonth(today.getMonth() - 6);
		dates.push(formatDate(today));
	}

	function formatDate(date) {
		return date.getFullYear() + '.' + (date.getMonth() < 9 ? '0' : '') + (date.getMonth() + 1);
	}

	function setDate(n) {
		$("#sdate").val(dates[n]);
		$("#edate").val(dates[0]);
	}

	function goExcel() {
		$("#searchForm").attr("action", "excel");
		$("#searchForm").submit();
		$("#searchForm").attr("action", "");
	}

	function goList(v) {
		document.location.href = "list"+v;
	}
</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="searchForm" id="searchForm">
			<input type="hidden" name="tab" value="M">
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
							<input type="text" name="sdate" id="sdate" readonly value="${ param.sdate }" style="width:70px">
						</div> ~
						<div class="dateBox">
							<input type="text" name="edate" id="edate" readonly value="${ param.edate }" style="width:70px">
						</div>
						<a href="#" onclick="setDate(1); return false;" class="btnSizeC">1개월</a>
						<a href="#" onclick="setDate(2); return false;" class="btnSizeC">3개월</a>
						<a href="#" onclick="setDate(3); return false;" class="btnSizeC">6개월</a>
						<a href="#" onclick="setDate(4); return false;" class="btnSizeC">12개월</a>
					</td>
				</tr>
				<tr>
					<th>구분</th>
					<td colspan=3>
						<select name="gubun" style="width:100px">
							<option value="1" ${ param.gubun eq '1' || param.gubun eq '' ? "selected" : ""}>1차</option>
							<option value="2" ${ param.gubun eq '2' ? "selected" : ""}>2차</option>
						</select>
					</td>
				</tr>
			</table>
			<div class="btnArea ac">
				<input type="submit" class="btnTypeA btnSizeA" value="검색">
				<input type="button" class="btnTypeB btnSizeA" id="resetBtn" value="초기화">
			</div>
		</div>
		<ul class="tabMenu">
			<li><a href="javascript:goList('')">일별 현황</a></li>
			<li  class="on"><a href="javascript:goList(2)">월별 현황</a></li>
		</ul>
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
				<col width="200">
				<c:if test="${ param.gubun eq '2'}">
					<col width="">
				</c:if>
				<col width="90">
				<col width="90">
				<col width="90">
				<col width="90">
				<col width="90">
				<col width="90">
				<col width="90">
				<col width="90">
				<col width="90">
				<col width="90">
			</colgroup>
			<thead>
			<tr>
				<th rowspan="2">건강주제</th>
				<c:if test="${ param.gubun eq '2'}">
					<th rowspan="2">관심정보</th>
				</c:if>
				<th rowspan="2">선택 건수</th>
				<th colspan="7">연령대</th>
				<th colspan="2">성별</th>
			</tr>
			<tr>
				<th>10대</th>
				<th>20대</th>
				<th>30대</th>
				<th>40대</th>
				<th>50대</th>
				<th>60대</th>
				<th>70대</th>
				<th>남자</th>
				<th>여자</th>
			</tr>
			</thead>
			<tbody>
			<c:set var="healthNo" value=""/>
			<c:forEach items="${ list }" var="row" varStatus="status">
				<tr>
					<c:choose>
						<c:when test="${ param.gubun eq '2'}">
							<c:if test='${ healthNo eq "" || healthNo ne row.get("health_no")}'>
								<td rowspan="${ row.getInt("exam_cnt")}">${ row.get("title")}</td>
							</c:if>
							<td>${ row.get("example")}</td>
						</c:when>
						<c:otherwise>
							<td>${ row.get("title")}</td>
						</c:otherwise>
					</c:choose>
					<td>${ row.getInt("cnt")}</td>
					<td>${ row.getInt("teens")}</td>
					<td>${ row.getInt("twenty")}</td>
					<td>${ row.getInt("thirty")}</td>
					<td>${ row.getInt("forty")}</td>
					<td>${ row.getInt("fifty")}</td>
					<td>${ row.getInt("sixty")}</td>
					<td>${ row.getInt("seventy")}</td>
					<td>${ row.getInt("man")}</td>
					<td>${ row.getInt("woman")}</td>
				</tr>
				<c:set var="healthNo" value='${ row.get("health_no")}'/>
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
</html>
