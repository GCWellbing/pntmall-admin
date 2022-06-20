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
		$("#resetBtn").click(function() {
			document.location.href = 'nutrition';
		});
	});

	function setIntake() {
		if($("input[name=nutritionNo]:checked").length == 0) {
			alert("영양정보를 선택하세요.");
			return;
		}

		var nutritions = new Array();

		$("input[name=nutritionNo]:checked").each(function() {
			var nutritionNo = $(this).val();
			var nutrition = {
				nutritionNo : nutritionNo,
				name : $("#name" + nutritionNo).val(),
				func : $("#func" + nutritionNo).val(),
				standard : $("#standard" + nutritionNo).val(),
				unit : $("#unit" + nutritionNo).val(),
				unitName : $("#unitName" + nutritionNo).val()
			};

			nutritions.push(nutrition)
		});

		opener.setNutrition(nutritions);
		self.close();
	}
</script>
</head>
<body>
<div id="popWrapper">
	<h1>영양/기능 정보</h1>
	<div id="popContainer">
		<form name="searchForm" id="searchForm">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="160">
					<col width="">
				</colgroup>
				<tr>
					<th>내용</th>
					<td>
						<input type="text" name="name" style="width:100%" value="${ param.name }">
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
				<col width="60px">
			</colgroup>
			<thead>
				<tr>
					<th>영영성분명</th>
					<th>기능</th>
					<th>선택</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td class="al"><c:out value="${ row.name }" /></td>
					<td class="al"><c:out value="${ row.func }" /></td>
					<td>
						<input type="checkbox" name="nutritionNo" value="${ row.nutritionNo }">
						<input type="hidden" name="name" id="name${ row.nutritionNo }" value="${ row.name }">
						<input type="hidden" name="func" id="func${ row.nutritionNo }" value="${ row.func }">
						<input type="hidden" name="standard" id="standard${ row.nutritionNo }" value="${ row.standard }">
						<input type="hidden" name="unit" id="unit${ row.nutritionNo }" value="${ row.unit }">
						<input type="hidden" name="unitName" id="unitName${ row.nutritionNo }" value="${ row.unitName }">
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
			<a href="javascript:setIntake();" class="btnTypeC btnSizeA"><span>확인</span></a>
		</div>
	</div>
</div><!-- //wrapper -->
</body>
</html>
