<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	function setGift() {
		if($("input[name=pno]:checked").length == 0) {
			alert("제품을 선택하세요.");
			return;
		}

		var arr = new Array();
		$("input[name=pno]:checked").each(function() {
			var info = {
					giftPno : $(this).val(),
					pname : $("#pname" + $(this).val()).val()
			}

			arr.push(info);
		});

		opener.setGift(arr);
		self.close();
	}
</script>
</head>
<body>
<div id="popWrapper">
	<h1>증정품 검색</h1>
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
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th>제품코드</th>
					<th>SAP코드</th>
					<th>제품명</th>
					<th>선택</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.pno }" /></td>
					<td><c:out value="${ row.matnr }" /></td>
					<td><c:out value="${ row.pname }" /></td>
					<td>
						<input type="checkbox" name="pno" value="${ row.pno }">
						<input type="hidden" name="pname" id="pname${ row.pno }" value="${ row.pname }">
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
			<a href="javascript:setGift();" class="btnTypeC btnSizeA"><span>확인</span></a>
		</div>
		<div class="paging btnSide"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>
	</div>
</div><!-- //wrapper -->
</body>
</html>
