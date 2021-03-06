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

		$("#sdate").val('${ param.sdate }' == '' ? dates[0] : '${ param.sdate }' );
		$("#edate").val('${ param.edate }' == '' ? dates[0] : '${ param.edate }' );

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

	function goExcel2() {
		$("#searchForm").attr("action", "excel2");
		$("#searchForm").submit();
		$("#searchForm").attr("action", "");
	}

	function goXml() {
		if(confirm("????????? ????????????(WMS?????????) ???????????? ???????????? ??????????????????? ???????????? ??? ???????????? [???????????????(WMS??????)]?????? ????????? ???????????????.")) {
			$("#searchForm").attr("action", "xml");
			$("#searchForm").submit();
			$("#searchForm").attr("action", "");
		}
	}

	function uploadExcel() {
		if($("#excelForm input[name=excel]").val() != '') {
	        ef.multipart($("#excelForm"), "/order/order/upload", "excel", function(result) {
	        	alert(result.message);
	        	if(result.succeed) document.location.reload();
	        	enableScreen();
	        });
		}
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
					<td colspan=3>
						<c:forEach items='${ statusList }' var='row'>
							<c:set var='checked' value='' />
							<c:forEach items='${ paramValues.status }' var='status'>
								<c:if test='${ status eq row.status }'>
									<c:set var='checked' value='checked' />
								</c:if>
							</c:forEach>
							<label><input type="checkbox" name="status" value="${ row.status }" ${ checked }><span>${ row.boName }</span></label>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th>????????????</th>
					<td>
						<select name="payType" style="width:100%">
							<option value=""></option>
							<c:forEach items='${ payTypeList }' var='row'>
								<option value="${ row.code1 }${ row.code2 }" ${ param.payType eq row.code1.concat(row.code2) ? 'selected' : '' }>${ row.name }</option>
							</c:forEach>
						</select>
					</td>
					<th>????????????</th>
					<td>
						<select name="orderGubun" style="width:100%">
							<option value=""></option>
							<option value="1" ${ param.orderGubun eq 1 ? 'selected' : '' }>????????????</option>
							<option value="2" ${ param.orderGubun eq 2 ? 'selected' : '' }>????????????</option>
							<option value="3" ${ param.orderGubun eq 3 ? 'selected' : '' }>???????????????</option>
							<option value="4" ${ param.orderGubun eq 4 ? 'selected' : '' }>??????</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>????????????</th>
					<td>
						<select name="gradeNo" style="width:100%">
							<option value=""></option>
							<c:forEach items='${ gradeList }' var='row'>
								<option value="${ row.gradeNo }" ${ param.gradeNo eq row.gradeNo ? 'selected' : '' }>${ row.name }</option>
							</c:forEach>
						</select>
					</td>
					<th>????????????</th>
					<td>
						<select name="device" style="width:100%">
							<option value=""></option>
							<option value="P" ${ param.device eq 'P' ? 'selected' : '' }>PC</option>
							<option value="M" ${ param.device eq 'M' ? 'selected' : '' }>Mobile</option>
							<option value="A" ${ param.device eq 'A' ? 'selected' : '' }>App</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>????????????</th>
					<td>
						<input type="text" name="spayAmt" value="${ param.spayAmt }" style="width:100px">??? ~
						<input type="text" name="epayAmt" value="${ param.epayAmt }" style="width:100px">???
					</td>
					<th>?????????</th>
					<td>
						<label><input type="checkbox" name="firstOrderYn" value="Y" ${ param.firstOrderYn eq 'Y' ? 'checked' : '' }><span>?????????</span></label>
					</td>
				</tr>
				<tr>
					<th>????????????</th>
					<td>
						<select name="keytype" style="width:100px">
							<option value="1" ${ param.keytype eq '1' ? 'selected' : '' }>????????????</option>
							<option value="2" ${ param.keytype eq '2' ? 'selected' : '' }>ID</option>
							<option value="3" ${ param.keytype eq '3' ? 'selected' : '' }>?????????</option>
							<option value="4" ${ param.keytype eq '4' ? 'selected' : '' }>?????????</option>
							<option value="5" ${ param.keytype eq '5' ? 'selected' : '' }>SAP ??????</option>
							<option value="6" ${ param.keytype eq '6' ? 'selected' : '' }>????????????</option>
							<option value="7" ${ param.keytype eq '7' ? 'selected' : '' }>?????????</option>
						</select>
						<input type="text" name="keyword" value="${ param.keyword }" style="width:500px">
					</td>
					<th>?????????ID</th>
					<td>
						<input type="text" name="clinicId" value="${ param.clinicId }" style="width:500px">
					</td>
				</tr>
			</table>
			<div class="btnArea ac">
				<input type="submit" class="btnTypeA btnSizeA" value="??????">
				<input type="button" class="btnTypeB btnSizeA" id="resetBtn" value="?????????">
			</div>
		</div>
		<div class="listHead">
			<div class="fl">
				<div class="totalArea">
					<strong class="totalTit">Total</strong> <span class="totalCount"><fmt:formatNumber value="${ count }" pattern="#,###" /></span>
				</div>
			</div>
			<p class="fr">
				<a href="javascript:goExcel2()" class="btnTypeC btnSizeA"><span>?????? ????????????</span></a>
<!-- 				<a href="javascript:goExcel2()">&nbsp;</a> -->
				<c:import url="/include/pageSize?pageSize=${ param.pageSize }" />
			</p>
		</div>
		</form>

		<table class="list">
			<colgroup>
				<col width="140px">
				<col width="80px">
				<col width="80px">
				<col width="80px">
				<col width="">
				<col width="140px">
				<col width="80px">
				<col width="100px">
				<col width="100px">
				<col width="100px">
				<col width="80px">
				<col width="80px">
				<col width="80px">
			</colgroup>
			<thead>
				<tr>
					<th>????????????</th>
					<th>??????</th>
					<th>????????????</th>
					<th>?????????</th>
					<th>?????????</th>
					<th>ID(?????????)</th>
					<th>??????</th>
					<th>???????????????</th>
					<th>???????????????</th>
					<th>????????????</th>
					<th>????????????</th>
					<th>????????????</th>
					<th>????????????</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><a href="${ fn:startsWith(row.status, '1') or fn:startsWith(row.status, '4') ? '' : 'r' }edit?orderid=${ row.orderid }"><c:out value="${ row.orderid }" /></a></td>
					<td><fmt:formatDate value="${ row.odate }" pattern="${ DateTimeFormat }"/></td>
					<td><c:out value="${ row.gubunName }" /></td>
					<td><c:out value="${ row.firstOrderYn }" /></td>
					<td><c:out value="${ row.pname }" /> ${ row.itemCount > 1 ? '??? '.concat(row.itemCount - 1).concat('???') : '' }</td>
					<td>${ utils.idMasking(row.memId) }(${ utils.nameMasking(row.oname) })</td>
					<td><c:out value="${ row.gradeName }" /></td>
					<td><fmt:formatNumber value="${ row.amt }" pattern="#,###" /></td>
					<td><fmt:formatNumber value="${ row.payAmt }" pattern="#,###" /></td>
					<td><c:out value="${ row.orderGubunName }" /></td>
					<td><c:out value="${ row.statusName }" /></td>
					<td><c:out value="${ row.device }" /></td>
					<td><c:out value="${ row.payTypeName }" /></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
			<div class="fl">
	 			<a href="javascript:goXml();" class="btnTypeC btnSizeA"><span>????????? ???????????? ????????????</span></a>
 			</div>
 			<form name="excelForm" id="excelForm" enctype="multipart/form-data" method="post" action="upload">
				<input type="file" name="excel" style="width:300px" accept=".xlsx">
				<input type="button" onclick="uploadExcel()" class="btnTypeC btnSizeA" value="????????? ?????? ?????????">
			</form>
		</div>
		<div class="paging"><!-- btnArea??? ????????? ??????????????? btnSide ?????? -->
			${ paging }
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>

