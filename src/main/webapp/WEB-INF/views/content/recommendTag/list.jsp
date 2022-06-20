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
		v.add("tag", {
			empty : "태그를 입력하세요.",
			max : "50"
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


		$("#optionList").on("click", ".btnUp", function() {
			var pno = $(this).parents("tr").find("input[name=pno]").val();
			var optNm = $("#optionList input[name=optNm" + pno + "]").val();

			var currObj = $(this).parents("tr");
			var idx = $(currObj).index();

			if (idx > 0) {
				var upperObj = currObj.prev();
				upperObj.before($("<tr></tr>").html(currObj.html()));

				//jquery 버그? 변경된 값은 html()에서 제너레이트 되지 않는다. 변경된 값을 다시 재지정.
				$("#optionList input[name=pno]", upperObj.prev()).val($("#optionList input[name=pno]", currObj).val());

				currObj.remove();

				$("#optionList input[name=optNm" + pno + "]").val(optNm);
			}
		});

		$("#optionList").on("click", ".btnDown", function() {
			var pno = $(this).parents("tr").find("input[name=pno]").val();
			var optNm = $("#optionList input[name=optNm" + pno + "]").val();

			var currObj = $(this).parents("tr");
			var size = $("#optionList tr").length;
			var idx = $(currObj).index();

			if (idx + 1 < size) {
				var belowObj = currObj.next();
				belowObj.after($("<tr></tr>").html(currObj.html()));

				//jquery 버그? 변경된 값은 html()에서 제너레이트 되지 않는다. 변경된 값을 다시 재지정.
				$("#optionList input[name=pno]", belowObj.next()).val($("#optionList input[name=pno]", currObj).val());

				currObj.remove();

				$("#optionList input[name=optNm" + pno + "]").val(optNm);
			}
		});

	});

	function modify(tno) {
		disableScreen();
		$.getJSON("info?tno=" + tno, function(json) {
			$("input[name=tno]").val(json.param.info.tno);
			$("input[name=rank]").val(json.param.info.rank);
			$("input[name=tag]").val(json.param.info.tag);
			$("input[name=status]:input[value=" + json.param.info.status + "]").prop("checked", true);
			$("#editForm").attr("action", "modify");
			enableScreen();
		});
	}

	function remove(tno) {
		if(confirm("삭제하시겠습니까?")) {
			disableScreen();
			$("input[name=tno]").val(tno);
			$("#editForm").attr("action", "remove");

			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list";
				enableScreen();
			});
		}
	}

	function modifyRank() {
		if(confirm("정렬 일괄 수정하시겠습니까?")) {
			disableScreen();
			ef.proc($("#listForm"), function(result) {
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
		<h2>대분류</h2>
		<form name="editForm" id="editForm" method="post" action="create">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>코드</th>
					<td>
						<input type="text" name="tno" value="" style="width:100px" readonly> *자동생성
						<input type="hidden" name="rank" value="" style="width:100px" readonly>
					</td>
				</tr>
				<tr>
					<th>태그명</th>
					<td>
						<input type="text" name="tag" value="" style="width:100%">
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

		<form name="listForm" id="listForm" method="post" action="modifyRank">
		<table class="list">
			<colgroup>
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="300px">
			</colgroup>
			<thead>
				<tr>
					<th>No</th>
					<th>태그명</th>
					<th>상태</th>
					<th>순서</th>
					<th>관리</th>
				</tr>
			</thead>
			<tbody id="optionList">
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.tno }" /></td>
					<td><c:out value="${ row.tag }" /></td>
					<td><c:out value="${ row.statusName }" /></td>
					<td>
						<input type='button' class='btnUp' value='위로'>
						<input type='button' class='btnDown' value='아래로'>
						<input type="hidden" name="tno" value="${ row.tno }">
					</td>
					<td>
						<c:if test="${ updateAuth eq 'Y' }">
						<input type="button" class="btnTypeB btnSizeB" onclick="modify('${ row.tno }')" value="수정">
						<input type="button" class="btnTypeC btnSizeB" onclick="remove('${ row.tno }')" value="삭제">
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		</form>
		<div class="btnArea">
			<a href="javascript:modifyRank()" class="btnTypeC btnSizeA"><span>정렬 일괄 수정</span></a>
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
