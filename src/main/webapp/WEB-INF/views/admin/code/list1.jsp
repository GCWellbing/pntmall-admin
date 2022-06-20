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
			empty : "코드명을 입력하세요.",
			max : "50"
		});

		$("#submitBtn").click(function() {
			if(v.validate()) {
				disableScreen();
				ef.proc($("#editForm"), function(result) {
					alert(result.message);
					if(result.succeed) location.href = "list1";
					enableScreen();
				});
			}
		});

		$("#resetBtn").click(function() {
			$("#editForm")[0].reset();
			$("#editForm").attr("action", "create");
		});
	});

	function modify(code1) {
		disableScreen();
		$.getJSON("info?code1=" + code1 + "&code2=000", function(json) {
			$("input[name=code1]").val(json.param.info.code1);
			$("input[name=name]").val(json.param.info.name);
			$("input[name=remark]").val(json.param.info.remark);
			$("input[name=status]:input[value=" + json.param.info.status + "]").prop("checked", true);
			$("#editForm").attr("action", "modify");
			enableScreen();
		});
	}

	function remove(code1) {
		if(confirm("삭제하시겠습니까?")) {
			disableScreen();
			$("input[name=code1]").val(code1);
			$("#editForm").attr("action", "remove");

			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list1";
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
		<h2>대분류</h2>
		<form name="editForm" id="editForm" method="post" action="create1">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>대분류 코드</th>
					<td>
						<input type="text" name="code1" value="" style="width:100px" readonly> *자동생성
						<input type="hidden" name="code2" value="000">
					</td>
				</tr>
				<tr>
					<th>코드명</th>
					<td>
						<input type="text" name="name" value="" style="width:100%">
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
				<col width="300px">
			</colgroup>
			<thead>
				<tr>
					<th>대분류코드</th>
					<th>코드명</th>
					<th>상태</th>
					<th>관리</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.code1 }" /></td>
					<td><c:out value="${ row.name }" /></td>
					<td><c:out value="${ row.statusName }" /></td>
					<td>
						<c:if test="${ updateAuth eq 'Y' }">
						<input type="button" class="btnTypeB btnSizeB" onclick="modify('${ row.code1 }')" value="수정">
						<input type="button" class="btnTypeC btnSizeB" onclick="remove('${ row.code1 }')" value="삭제">
						</c:if>
						<input type="button" class="btnTypeD btnSizeB" onclick="document.location.href='list2?code1=${ row.code1 }';" value="소분류">
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
