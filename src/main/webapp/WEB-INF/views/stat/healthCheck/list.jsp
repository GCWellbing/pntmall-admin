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

		$("#sdate").val('${ param.sdate }' == '' ? dates[2] : '${ param.sdate }' );
		$("#edate").val('${ param.edate }' == '' ? dates[0] : '${ param.edate }' );

	});

	function initdates() {
		var today = new Date();
		dates.push(formatDate(today));
		today.setDate(today.getDate() - 1);
		dates.push(formatDate(today));
		today.setDate(today.getDate() - 5);
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

// 		console.log("dates", dates);

	}

	function formatDate(date) {
		return date.getFullYear() + '.' + (date.getMonth() < 9 ? '0' : '') + (date.getMonth() + 1) + '.' + (date.getDate() < 10 ? '0' : '') + date.getDate();
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
			<input type="hidden" name="tab" value="D">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>??????</th>
					<td colspan=3>
						<div class="dateBox">
							<input type="text" name="sdate" id="sdate" readonly value="${ param.sdate }" style="width:110px">
						</div> ~
						<div class="dateBox">
							<input type="text" name="edate" id="edate" readonly value="${ param.edate }" style="width:110px">
						</div>
						<a href="#" onclick="setDate(0); return false;" class="btnSizeC">??????</a>
						<a href="#" onclick="setDate(1); return false;" class="btnSizeC">??????</a>
						<a href="#" onclick="setDate(2); return false;" class="btnSizeC">7???</a>
						<a href="#" onclick="setDate(3); return false;" class="btnSizeC">15???</a>
						<a href="#" onclick="setDate(4); return false;" class="btnSizeC">1??????</a>
						<a href="#" onclick="setDate(5); return false;" class="btnSizeC">3??????</a>
						<a href="#" onclick="setDate(6); return false;" class="btnSizeC">12??????</a>
					</td>
				</tr>
				<tr>
					<th>??????</th>
					<td colspan=3>
						<select name="gubun" style="width:100px">
							<option value="1" ${ param.gubun eq '1' || param.gubun eq '' ? "selected" : ""}>1???</option>
							<option value="2" ${ param.gubun eq '2' ? "selected" : ""}>2???</option>
						</select>
					</td>
				</tr>
			</table>
			<div class="btnArea ac">
				<input type="submit" class="btnTypeA btnSizeA" value="??????">
				<input type="button" class="btnTypeB btnSizeA" id="resetBtn" value="?????????">
			</div>
		</div>
		<ul class="tabMenu">
			<li class="on"><a href="javascript:goList('')">?????? ??????</a></li>
			<li><a href="javascript:goList(2)">?????? ??????</a></li>
		</ul>
		<div class="listHead">
			<div class="fl">
				<div class="totalArea">
				</div>
			</div>
			<p class="fr">
				<a href="javascript:goExcel()" class="btnTypeC btnSizeA"><span>?????? ????????????</span></a>
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
				<th>??????</th>
				<th>??? ????????????</th>
				<th>10???</th>
				<th>20???</th>
				<th>30???</th>
				<th>40???</th>
				<th>50???</th>
				<th>60???</th>
				<th>70?????????</th>
				<th>??????</th>
				<th>??????</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${ list }" var="row" varStatus="status">
				<c:if test="${ not status.first}">
			<tr>
				<c:if test="${ row.get('gubun') eq '1'}">
				<td rowspan="4">${ row.get("m")}</td>
				</c:if>
				<th>${ row.get('gubun') eq '1' ? '??? ????????????' : '??? ????????????'}</th>
				<th>10???</th>
				<th>20???</th>
				<th>30???</th>
				<th>40???</th>
				<th>50???</th>
				<th>60???</th>
				<th>70???</th>
				<th>??????</th>
				<th>??????</th>
			</tr>
				</c:if>
			<tr>
				<c:if test="${ status.first }">
					<td rowspan="3">${ row.get("m")}</td>
				</c:if>
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
			</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
		</div>
		<div class="paging"><!-- btnArea??? ????????? ??????????????? btnSide ?????? -->
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->
</body>
</html>
