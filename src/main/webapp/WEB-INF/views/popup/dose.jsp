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
			document.location.href = 'dose';
		});
	});

	function setIntake() {
		if($("input[name=doseNo]:checked").length == 0) {
			alert("복용안내를 선택하세요.");
			return;
		}

		var doses = new Array();

		$("input[name=doseNo]:checked").each(function() {
			var doseNo = $(this).val();
			var dose = {
				doseNo : doseNo,
				content : $("#content" + doseNo).val()
			};

			doses.push(dose)
		});

		opener.setDose(doses);
		self.close();
	}
</script>
</head>
<body>
<div id="popWrapper">
	<h1>복용안내 검색</h1>
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
						<input type="text" name="content" style="width:100%" value="${ param.content }">
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
				<col width="60px">
			</colgroup>
			<thead>
				<tr>
					<th>내용</th>
					<th>선택</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td class="al"><c:out value="${ row.content }" /></td>
					<td>
						<input type="checkbox" name="doseNo" value="${ row.doseNo }">
						<input type="hidden" name="content" id="content${ row.doseNo }" value="${ row.content }">
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
