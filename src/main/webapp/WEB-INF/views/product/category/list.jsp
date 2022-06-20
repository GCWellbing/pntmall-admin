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
			empty : "카테고리명을 입력하세요.",
			max : "50"
		});

		$("#submitBtn").click(function() {
			if(v.validate()) {
				disableScreen();
				ef.proc($("#editForm"), function(result) {
					alert(result.message);
					if(result.succeed) location.href = "list?pcateNo=${ param.pcateNo }";
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
				$("#subList input[name=cateNo]", upperObj.prev()).val($("#subList input[name=cateNo]", currObj).val());

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
				$("#subList input[name=cateNo]", belowObj.next()).val($("#subList input[name=cateNo]", currObj).val());

				currObj.remove();
			}
		});
	});

	function modify(cateNo) {
		disableScreen();
		$.getJSON("info?cateNo=" + cateNo, function(json) {
			$("#editForm input[name=cateNo]").val(json.param.info.cateNo);
			$("input[name=pcateNo]").val(json.param.info.pcateNo);
			$("input[name=name]").val(json.param.info.name);
			$("input[name=rank]").val(json.param.info.rank);
			$("input[name=remark]").val(json.param.info.remark);
			$("input[name=status]:input[value=" + json.param.info.status + "]").prop("checked", true);
			$("#editForm").attr("action", "modify");
			enableScreen();
		});
	}

	function remove(cateNo) {
		if(confirm("삭제하시겠습니까?")) {
			disableScreen();
			$("#editForm input[name=cateNo]").val(cateNo);
			$("#editForm").attr("action", "remove");

			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list?pcateNo=${ param.pcateNo }";
				enableScreen();
			});
		}
	}

	function modifyRank() {
		if(confirm("정렬 일괄 수정하시겠습니까?")) {
			disableScreen();
			ef.proc($("#listForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list?pcateNo=${ param.pcateNo }";
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
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>카테고리 번호</th>
					<td>
						<input type="text" name="cateNo" value="" style="width:100px" readonly> *자동생성
						<input type="hidden" name="pcateNo" value="${ empty param.pcateNo ? 0 : param.pcateNo }">
					</td>
				</tr>
				<tr>
					<th>카테고리명</th>
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
			</table>
			<div class="btnArea ac">
				<input type="button" class="btnTypeA btnSizeA" id="submitBtn" value="등록">
				<input type="button" class="btnTypeB btnSizeA" id="resetBtn" value="초기화">
				<c:if test="${ !empty param.pcateNo && param.pcateNo > 0 }">
				<span class="fr">
					<input type="button" class="btnTypeD btnSizeB" onclick="document.location.href='list';" value="상위 카테고리">
				</span>
				</c:if>
			</div>
		</div>
		</form>

		<form name="listForm" id="listForm" method="post" action="modifyRank">
		<table class="list">
			<colgroup>
				<col width="100px">
				<col width="">
				<col width="">
				<col width="">
				<col width="100px">
				<col width="300px">
			</colgroup>
			<thead>
				<tr>
					<th>카테고리 번호</th>
					<th>카테고리</th>
					<th>비고</th>
					<th>상태</th>
					<th>정렬 순서</th>
					<th>관리</th>
				</tr>
			</thead>
			<tbody id="subList">
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.cateNo }" /></td>
					<td>
						<c:if test="${ !empty row.pname }">
							<c:out value="${ row.pname }" /> &gt;
						</c:if>
						<c:out value="${ row.name }" />
					</td>
					<td><c:out value="${ row.remark }" /></td>
					<td><c:out value="${ row.statusName }" /></td>
					<td>
						<input type="button" class="btnUp" value="위로">
						<input type="button" class="btnDown" value="아래로">
						<input type="hidden" name="cateNo" value="${ row.cateNo }">
					</td>
					<td>
						<c:if test="${ updateAuth eq 'Y' }">
							<input type="button" class="btnTypeB btnSizeB" onclick="modify('${ row.cateNo }')" value="수정">
							<input type="button" class="btnTypeC btnSizeB" onclick="remove('${ row.cateNo }')" value="삭제">
						</c:if>
						<c:if test="${ empty param.pcateNo || param.pcateNo eq 0 }">
							<input type="button" class="btnTypeD btnSizeB" onclick="document.location.href='list?pcateNo=${ row.cateNo }';" value="하위 카테고리">
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${ fn:length(list) > 0 }">
		<div class="btnArea">
			<a href="javascript:modifyRank()" class="btnTypeC btnSizeA"><span>정렬 일괄 수정</span></a>
		</div>
		</c:if>
		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
