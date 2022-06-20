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
		v.add("content", {
			empty : "내용을 입력하세요.",
			max : "100"
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

	function modify(intakeNo) {
		disableScreen();
		$.getJSON("info?intakeNo=" + intakeNo, function(json) {
			$("#editForm input[name=intakeNo]").val(json.param.info.intakeNo);
			$("input[name=content]").val(json.param.info.content);
			$("input[name=status]:input[value=" + json.param.info.status + "]").prop("checked", true);
			$("#editForm").attr("action", "modify");
			enableScreen();
		});
	}

	function remove(intakeNo) {
		if(confirm("삭제하시겠습니까?")) {
			disableScreen();
			$("#editForm input[name=intakeNo]").val(intakeNo);
			$("#editForm").attr("action", "remove");

			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list";
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
		<form name="editForm" id="editForm" method="post" action="create">
			<input type="text" name="dummy" value="" style="display:none">
			<input type="hidden" name="intakeNo" value="">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>내용</th>
					<td>
						<input type="text" name="content" value="" style="width:100%">
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
				<col width="60px">
				<col width="">
				<col width="">
				<col width="200px">
			</colgroup>
			<thead>
				<tr>
					<th>No.</th>
					<th>내용</th>
					<th>상태</th>
					<th>관리</th>
				</tr>
			</thead>
			<tbody id="subList">
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.intakeNo }" /></td>
					<td><c:out value="${ row.content }" /></td>
					<td><c:out value="${ row.statusName }" /></td>
					<td>
						<c:if test="${ updateAuth eq 'Y' }">
							<input type="button" class="btnTypeB btnSizeB" onclick="modify('${ row.intakeNo }')" value="수정">
							<input type="button" class="btnTypeC btnSizeB" onclick="remove('${ row.intakeNo }')" value="삭제">
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
