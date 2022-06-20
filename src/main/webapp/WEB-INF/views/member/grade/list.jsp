<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	var v;

	$(function() {
		v = new ef.utils.Validator($("#editForm"));
		v.add("name", {
			empty : "등급명을 입력하세요.",
			max : "50"
		});
		v.add("pointRate", {
			empty : "포인트 적립율을 입력하세요.",
			max : "3",
			format : "numeric"
		});

		$("#submitBtn").click(function() {
			if(v.validate()) {
				disableScreen();
				ef.proc($("#editForm"), function(result) {
					alert(result.message);
					if(result.succeed) location.href = "list";
					enableScreen();
				});
			}
		});

		$("#resetBtn").click(function() {
			$("#editForm")[0].reset();
			$("#editForm").attr("action", "create");
		});
	});

	function modify(gradeNo) {
		disableScreen();
		$.getJSON("info?gradeNo=" + gradeNo, function(json) {
			$("input[name=gradeNo]").val(json.param.info.gradeNo);
			$("input[name=name]").val(json.param.info.name);
			$("input[name=pointRate]").val(json.param.info.pointRate);
			$("input[name=remark]").val(json.param.info.remark);
			$("input[name=status]:input[value=" + json.param.info.status + "]").prop("checked", true);
			$("#editForm").attr("action", "modify");
			enableScreen();
		});
	}

</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="editForm" id="editForm" method="post" action="create">
			<input type="hidden" name="gradeNo">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>등급명</th>
					<td>
						<input type="text" name="name" value="" style="width:100%">
					</td>
				</tr>
				<tr>
					<th>포인트 적립율</th>
					<td>
						<input type="text" name="pointRate" value="" style="width:100px">
					</td>
				</tr>
				<tr>
					<th>비고</th>
					<td>
						<input type="text" name="remark" value="" style="width:100%">
					</td>
				</tr>
				<tr>
					<th>상태</th>
					<td>
						<label><input type="radio" name="status" value="S" checked><span>활성</span></label>
						<label><input type="radio" name="status" value="H"><span>비활성</span></label>
					</td>
				</tr>
			</table>
			<div class="btnArea ac">
				<input type="button" class="btnTypeA btnSizeA" id="submitBtn" value="등록">
				<input type="button" class="btnTypeB btnSizeA" id="resetBtn" value="초기화">
			</div>
		</div>
		</form>

		<table class="list">
			<colgroup>
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="100px">
			</colgroup>
			<thead>
				<tr>
					<th>등급명</th>
					<th>비고</th>
					<th>포인트 적립율</th>
					<th>상태</th>
					<th>관리</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.name }" /></td>
					<td><c:out value="${ row.remark }" /></td>
					<td><c:out value="${ row.pointRate }" /></td>
					<td><c:out value="${ row.statusName }" /></td>
					<td>
						<c:if test="${ updateAuth eq 'Y' }">
						<input type="button" class="btnTypeB btnSizeB" onclick="modify('${ row.gradeNo }')" value="수정">
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
