<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	$(function() {
		window.resizeTo(1024, 800);
	});

	function setProduct() {
		if($("input[name=pno]:checked").length == 0) {
			alert("제품을 선택하세요.");
			return;
		}

		var pno = $("input[name=pno]:checked").val();
		var info = {
				info1 : $("#info1" + pno).val(),
				info2 : $("#info2" + pno).val(),
				info3 : $("#info3" + pno).val(),
				info4 : $("#info4" + pno).val(),
				info5 : $("#info5" + pno).val(),
				info6 : $("#info6" + pno).val(),
				info7 : $("#info7" + pno).val(),
				info8 : $("#info8" + pno).val(),
				info9 : $("#info9" + pno).val(),
				info10 : $("#info10" + pno).val(),
				info11 : $("#info11" + pno).val(),
				info12 : $("#info12" + pno).val(),
				info13 : $("#info13" + pno).val(),
		};

		opener.setProductInfo(info);
		self.close();
	}
</script>
</head>
<body>
<div id="popWrapper">
	<h1>제품 검색</h1>
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
						<input type="text" name="pname" style="width:100%" value="${ param.pname }">
					</td>
				</tr>
				<tr>
					<th>제품 코드</th>
					<td>
						<input type="text" name="pno" style="width:100%" value="${ param.pno }">
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
				<col width="100px">
				<col width="100px">
				<col width="80px">
				<col width="">
				<col width="80px">
				<col width="80px">
			</colgroup>
			<thead>
				<tr>
					<th>브랜드</th>
					<th>제품코드</th>
					<th>SAP코드</th>
					<th>제품명</th>
					<th>판매가격</th>
					<th>선택</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.brandName }" /></td>
					<td><c:out value="${ row.pno }" /></td>
					<td><c:out value="${ row.matnr }" /></td>
					<td><c:out value="${ row.pname }" /></td>
					<td><fmt:formatNumber value="${ row.salePrice }" pattern="#,###" /></td>
					<td>
						<input type="radio" name="pno" value="${ row.pno }">
						<input type="hidden" name="info1" id="info1${ row.pno }" value="${ row.info1 }">
						<input type="hidden" name="info2" id="info2${ row.pno }" value="${ row.info2 }">
						<input type="hidden" name="info3" id="info3${ row.pno }" value="${ row.info3 }">
						<input type="hidden" name="info4" id="info4${ row.pno }" value="${ row.info4 }">
						<input type="hidden" name="info5" id="info5${ row.pno }" value="${ row.info5 }">
						<input type="hidden" name="info6" id="info6${ row.pno }" value="${ row.info6 }">
						<input type="hidden" name="info7" id="info7${ row.pno }" value="${ row.info7 }">
						<input type="hidden" name="info8" id="info8${ row.pno }" value="${ row.info8 }">
						<input type="hidden" name="info9" id="info9${ row.pno }" value="${ row.info9 }">
						<input type="hidden" name="info10" id="info10${ row.pno }" value="${ row.info10 }">
						<input type="hidden" name="info11" id="info11${ row.pno }" value="${ row.info11 }">
						<input type="hidden" name="info12" id="info12${ row.pno }" value="${ row.info12 }">
						<input type="hidden" name="info13" id="info13${ row.pno }" value="${ row.info13 }">
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
			<a href="javascript:setProduct();" class="btnTypeC btnSizeA"><span>확인</span></a>
		</div>
		<div class="paging btnSide"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>
	</div>
</div><!-- //wrapper -->
</body>
</html>
