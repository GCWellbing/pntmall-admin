<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	function setSap() {
		if($("input[name=matnr]:checked").length == 0) {
			alert("제품을 선택하세요.");
			return;
		}

		var matnr = $("input[name=matnr]:checked").val();
		var sap = {
			matnr : matnr,
			maktx : $("#maktx" + matnr).val(),
			netpr : $("#netpr" + matnr).val()
		};

		opener.setSap(sap);
		self.close();
	}
</script>
</head>
<body>
<div id="popWrapper">
	<h1>마스터 제품 검색</h1>
	<div id="popContainer">
		<form name="searchForm" id="searchForm">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="160">
					<col width="">
				</colgroup>
				<tr>
					<th>제품명</th>
					<td>
						<input type="text" name="maktx" style="width:100%" value="${ param.maktx }">
					</td>
				</tr>
				<tr>
					<th>SAP 코드</th>
					<td>
						<input type="text" name="matnr" style="width:100%" value="${ param.matnr }">
					</td>
				</tr>
			</table>
		</div>
		<div class="btnArea ac">
			<input type="submit" class="btnTypeA btnSizeA" value="검색">
			<input type="button" class="btnTypeB btnSizeA" id="resetBtn" value="초기화">
		</div>
		</form>

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
					<th>SAP코드</th>
					<th>제품명</th>
					<th>재고</th>
					<th>단가</th>
					<th>선택</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.matnr }" /></td>
					<td><c:out value="${ row.maktx }" /></td>
					<td><fmt:formatNumber value="${ row.labst }" pattern="#,###" /></td>
					<td><fmt:formatNumber value="${ row.netpr }" pattern="#,###" /></td>
					<td>
						<input type="radio" name="matnr" value="${ row.matnr }">
						<input type="hidden" name="maktx" id="maktx${ row.matnr }" value="${ row.maktx }">
						<input type="hidden" name="netpr" id="netpr${ row.matnr }" value="${ row.netpr }">
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
			<a href="javascript:setSap();" class="btnTypeC btnSizeA"><span>확인</span></a>
		</div>
		<div class="paging btnSide"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>
	</div>
</div><!-- //wrapper -->
</body>
</html>
