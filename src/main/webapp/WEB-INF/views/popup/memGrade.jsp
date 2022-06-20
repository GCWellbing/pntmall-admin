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
			document.location.href = 'memGrade';
		});
	});

	function setGrade() {
		if($("input[name=gradeNo]:checked").length == 0) {
			alert("회원등급을 선택하세요.");
			return;
		}

		if($("#singleChoice").val() == "Y" &&  $("input[name=gradeNo]:checked").length > 1) {
			alert("한 개의 등급만 선택해 주세요.");
			return;
		}
		singleChoice

		var grades = new Array();

		$("input[name=gradeNo]:checked").each(function() {
			var gradeNo = $(this).val();
			var grade = {
				gradeNo : gradeNo,
				name : $("#name" + gradeNo).val()
			};

			grades.push(grade)
		});

		opener.setGrade(grades);
		self.close();
	}
</script>
</head>
<body>
<div id="popWrapper">
<input type="hidden" name="singleChoice" id="singleChoice" value="${ param.singleChoice }">

	<h1>회원등급</h1>
	<div id="popContainer"><%--
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
 --%>
		<table class="list">
			<colgroup>
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="60px">
			</colgroup>
			<thead>
				<tr>
					<th>등급명</th>
					<th>비고</th>
					<th>포인트 적립률</th>
					<th>상태</th>
					<th>선택</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td class="al"><c:out value="${ row.name }" /></td>
					<td class="al"><c:out value="${ row.remark }" /></td>
					<td class="al"><c:out value="${ row.pointRate }" /></td>
					<td class="al"><c:out value="${ row.statusName }" /></td>
					<td>
						<input type="checkbox" name="gradeNo" value="${ row.gradeNo }">
						<input type="hidden" name="name" id="name${ row.gradeNo }" value="${ row.name }">
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
			<a href="javascript:setGrade();" class="btnTypeC btnSizeA"><span>확인</span></a>
		</div>
	</div>
</div><!-- //wrapper -->
</body>
</html>
