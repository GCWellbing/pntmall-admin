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

	function getCate2(v) {
		$.ajax({
			method : "GET",
			url : "/popup/cate2",
			data : { pcateNo : v },
			dataType : "html"
		})
		.done(function(html) {
			$("#cate2").empty();
			$("<option />", { "value" : "" }).text("??????").appendTo("#cate2");
			$("#cate2").append(html);
		});
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
					<th>????????????</th>
					<td>
						<select name="cate1" style="width:40%" onchange="getCate2(this.value)">
							<option value="">??????</option>
							<c:forEach items='${ cate1List }' var='row'>
								<option value="${ row.cateNo }" ${ row.cateNo eq param.cate1 ? 'selected' : '' }>${ row.name }</option>
							</c:forEach>
						</select>
						<select name="cate2" id="cate2" style="width:40%;">
							<option value="">??????</option>
							<c:forEach items='${ cate2List }' var='row'>
								<option value="${ row.cateNo }" ${ row.cateNo eq param.cate2 ? 'selected' : '' }>${ row.name }</option>
							</c:forEach>
						</select>
					</td>
					<th>??????</th>
					<td>
						<label><input type="checkbox" name="orderGubun" value="4" ${ param.orderGubun eq '4' ? 'checked' : '' }> <span>?????????</span></label>
					</td>
				</tr>
				<tr>
					<th>?????????</th>
					<td colspan="3">
						<input type="text" name="pname" value="${ param.pname }" style="width:500px">
					</td>
				</tr>
			</table>
			<div class="btnArea ac">
				<input type="submit" class="btnTypeA btnSizeA" value="??????">
				<input type="button" class="btnTypeB btnSizeA" id="resetBtn" value="?????????">
			</div>
		</div>

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
					<th colspan="2">??? ????????????</th>
					<th colspan="2">??? ????????????</th>
					<th colspan="2">??? ????????????</th>
				</tr>
				<tr>
					<th colspan="2" id="totAmt"></th>
					<th colspan="2" id="totQty"></th>
					<th colspan="2" id="totCnt"></th>
				</tr>
				<tr>
					<th>PC</th>
					<td id="totPcAmt"></td>
					<th>PC</th>
					<td id="totPcQty"></td>
					<th>PC</th>
					<td id="totPcCnt"></td>
				</tr>
				<tr>
					<th>?????????</th>
					<td id="totMoAmt"></td>
					<th>?????????</th>
					<td id="totMoQty"></td>
					<th>?????????</th>
					<td id="totMoCnt"></td>
				</tr>
				<tr>
					<th>???</th>
					<td id="totApAmt"></td>
					<th>???</th>
					<td id="totApQty"></td>
					<th>???</th>
					<td id="totApCnt"></td>
				</tr>
			</tbody>
		</table>

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
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th rowspan="2">??????</th>
					<th rowspan="2">???????????????</th>
					<th rowspan="2">????????????</th>
					<th rowspan="2">?????????</th>
					<th colspan="4">????????????</th>
					<th colspan="4">????????????</th>
					<th colspan="4">????????????</th>
				</tr>
				<tr>
					<th>PC</th>
					<th>?????????</th>
					<th>???</th>
					<th>??????</th>
					<th>PC</th>
					<th>?????????</th>
					<th>???</th>
					<th>??????</th>
					<th>PC</th>
					<th>?????????</th>
					<th>???</th>
					<th>??????</th>
				</tr>
			</thead>
			<tbody>
				<c:set var='no' value='1' />
				<c:set var='totAmt' value='0' />
				<c:set var='totPcAmt' value='0' />
				<c:set var='totMoAmt' value='0' />
				<c:set var='totApAmt' value='0' />
				<c:set var='totQty' value='0' />
				<c:set var='totPcQty' value='0' />
				<c:set var='totMoQty' value='0' />
				<c:set var='totApQty' value='0' />
				<c:set var='totCnt' value='0' />
				<c:set var='totPcCnt' value='0' />
				<c:set var='totMoCnt' value='0' />
				<c:set var='totApCnt' value='0' />
				<c:forEach items="${ list }" var="row">
					<tr>
						<td>${ no }</td>
						<td><img src="${ row.get('img') }"></td>
						<td>${ row.get('pno') }</td>
						<td>${ row.get('pname') }</td>
						<td class="ar"><fmt:formatNumber value="${ row.get('tot_pc_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('tot_mo_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('tot_ap_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('tot_amt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('tot_pc_qty') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('tot_mo_qty') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('tot_ap_qty') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('tot_qty') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('tot_pc_cnt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('tot_mo_cnt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('tot_ap_cnt') }" pattern="#,###" /></td>
						<td class="ar"><fmt:formatNumber value="${ row.get('tot_cnt') }" pattern="#,###" /></td>
					</tr>
					<c:set var='no' value='${ no + 1 }' />
					<c:set var='totAmt' value="${ totAmt + row.getInt('tot_amt') }" />
					<c:set var='totPcAmt' value="${ totPcAmt + row.getInt('tot_pc_amt') }" />
					<c:set var='totMoAmt' value="${ totMoAmt + row.getInt('tot_mo_amt') }" />
					<c:set var='totApAmt' value="${ totApAmt + row.getInt('tot_ap_amt') }" />
					<c:set var='totQty' value="${ totQty + row.getInt('tot_qty') }" />
					<c:set var='totPcQty' value="${ totPcQty + row.getInt('tot_pc_qty') }" />
					<c:set var='totMoQty' value="${ totMoQty + row.getInt('tot_mo_qty') }" />
					<c:set var='totApQty' value="${ totApQty + row.getInt('tot_ap_qty') }" />
					<c:set var='totCnt' value="${ totCnt + row.getInt('tot_cnt') }" />
					<c:set var='totPcCnt' value="${ totPcCnt + row.getInt('tot_pc_cnt') }" />
					<c:set var='totMoCnt' value="${ totMoCnt + row.getInt('tot_mo_cnt') }" />
					<c:set var='totApCnt' value="${ totApCnt + row.getInt('tot_ap_cnt') }" />
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
<script>
	$("#totAmt").html('${ totAmt }'.formatMoney());
	$("#totPcAmt").html('${ totPcAmt }'.formatMoney());
	$("#totMoAmt").html('${ totMoAmt }'.formatMoney());
	$("#totApAmt").html('${ totApAmt }'.formatMoney());
	$("#totQty").html('${ totQty }'.formatMoney());
	$("#totPcQty").html('${ totPcQty }'.formatMoney());
	$("#totMoQty").html('${ totMoQty }'.formatMoney());
	$("#totApQty").html('${ totApQty }'.formatMoney());
	$("#totCnt").html('${ totCnt }'.formatMoney());
	$("#totPcCnt").html('${ totPcCnt }'.formatMoney());
	$("#totMoCnt").html('${ totMoCnt }'.formatMoney());
	$("#totApCnt").html('${ totApCnt }'.formatMoney());
</script>
</html>
