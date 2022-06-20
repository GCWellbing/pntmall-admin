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
					if(result.succeed) location.href = "list2?code1=${ param.code1 }";
					enableScreen();
				});
			}
		});

		$("#resetBtn").click(function() {
			$("#editForm")[0].reset();
			$("#editForm").attr("action", "create");
		});

		$("#subList").on("click", ".btnUp", function() {
			var currObj = $(this).parents("tr");
			var idx = $(currObj).index();

			if (idx > 0) {
				var upperObj = currObj.prev();
				upperObj.before($("<tr></tr>").html(currObj.html()));

				//jquery 버그? 변경된 값은 html()에서 제너레이트 되지 않는다. 변경된 값을 다시 재지정.
				$("#subList input[name=code2]", upperObj.prev()).val($("#subList input[name=code2]", currObj).val());

				currObj.remove();
			}
		});

		$("#subList").on("click", ".btnDown", function() {
			var currObj = $(this).parents("tr");
			var size = $("#subList tr").length;
			var idx = $(currObj).index();

			if (idx + 1 < size) {
				var belowObj = currObj.next();
				belowObj.after($("<tr></tr>").html(currObj.html()));

				//jquery 버그? 변경된 값은 html()에서 제너레이트 되지 않는다. 변경된 값을 다시 재지정.
				$("#subList input[name=code2]", belowObj.next()).val($("#subList input[name=code2]", currObj).val());

				currObj.remove();
			}
		});
	});

	function modify(code2) {
		disableScreen();
		$.getJSON("info?code1=${ param.code1 }&code2=" + code2, function(json) {
			$("#editForm input[name=code2]").val(json.param.info.code2);
			$("input[name=name]").val(json.param.info.name);
			$("input[name=rank]").val(json.param.info.rank);
			$("input[name=remark]").val(json.param.info.remark);
			$("input[name=status]:input[value=" + json.param.info.status + "]").prop("checked", true);
			$("#editForm").attr("action", "modify");
			enableScreen();
		});
	}

	function remove(code2) {
		if(confirm("삭제하시겠습니까?")) {
			disableScreen();
			$("#editForm input[name=code2]").val(code2);
			$("#editForm").attr("action", "remove");

			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list1";
				enableScreen();
			});
		}
	}

	function modifyRank() {
		if(confirm("정렬 일괄 수정하시겠습니까?")) {
			disableScreen();
			ef.proc($("#listForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list2?code1=${ param.code1 }";
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
		<h2>소분류</h2>
		<form name="editForm" id="editForm" method="post" action="create2">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>대분류 코드</th>
					<td>
						<input type="text" name="code1" value="${ param.code1 }" style="width:100px" readonly>
					</td>
				</tr>
				<tr>
					<th>소분류 코드</th>
					<td>
						<input type="text" name="code2" id="code2" value="" style="width:100px" readonly> *자동생성
					</td>
				</tr>
				<tr>
					<th>코드명</th>
					<td>
						<input type="text" name="name" value="" style="width:100%">
					</td>
				</tr>
				<tr>
					<th>정렬 순서</th>
					<td>
						<input type="text" name="rank" value="" style="width:100px">
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
				<tr>
					<th>sap code</th>
					<td>
						<input type="text" name="sapCode" value="" style="width:100%">
					</td>
				</tr>
			</table>
			<div class="btnArea ac">
				<input type="button" class="btnTypeA btnSizeA" id="submitBtn" value="등록">
				<input type="button" class="btnTypeB btnSizeA" id="resetBtn" value="초기화">
				<span class="fr">
					<input type="button" class="btnTypeD btnSizeB" onclick="document.location.href='list1';" value="대분류">
				</span>
			</div>
		</div>
		</form>

		<form name="listForm" id="listForm" method="post" action="modifyRank">
			<input type="hidden" name="code1" value="${ param.code1 }">
		<table class="list">
			<colgroup>
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="100px">
				<col width="200px">
			</colgroup>
			<thead>
				<tr>
					<th>대분류코드</th>
					<th>소분류코드</th>
					<th>코드명</th>
					<th>상태</th>
					<th>sap code</th>
					<th>정렬순서</th>
					<th>관리</th>
				</tr>
			</thead>
			<tbody id="subList">
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.code1 }" /></td>
					<td><c:out value="${ row.code2 }" /></td>
					<td><c:out value="${ row.name }" /></td>
					<td><c:out value="${ row.statusName }" /></td>
					<td><c:out value="${ row.sapCode }" /></td>
					<td>
						<input type="button" class="btnUp" value="위로">
						<input type="button" class="btnDown" value="아래로">
						<input type="hidden" name="code2" value="${ row.code2 }">
					</td>
					<td>
						<c:if test="${ updateAuth eq 'Y' }">
						<input type="button" class="btnTypeB btnSizeB" onclick="modify('${ row.code2 }')" value="수정">
						<input type="button" class="btnTypeC btnSizeB" onclick="remove('${ row.code2 }')" value="삭제">
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
			<a href="javascript:modifyRank()" class="btnTypeC btnSizeA"><span>정렬 일괄 수정</span></a>
		</div>
		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
