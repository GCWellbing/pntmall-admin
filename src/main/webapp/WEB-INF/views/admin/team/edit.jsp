<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	var v;

	$(function() {
		v = new ef.utils.Validator($("#editForm"));
		v.add("name", {
			empty : "부서명을 입력하세요.",
			max : "50"
		});

		$("#checkAll").click(function() {
			$("input[name=menuNo]").each(function() {
				$(this).prop("checked", $("#checkAll").prop("checked"));
			});
		});
	});

	function goSubmit() {
		if(v.validate()) {
			disableScreen();
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
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="teamNo" value="${ team.teamNo }" />
		<h2>정보 등록</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>부서명<sup>*</sup></th>
					<td>
						<input type="text" name="name" value="${ team.name }" style="width:100%" maxlength="50">
					</td>
				</tr>
				<tr>
					<th>내용</th>
					<td>
						<textarea name="remark" style="width:100%;height:100px">${ team.remark }</textarea>
					</td>
				</tr>
				<tr>
					<th>상태<sup>*</sup></th>
					<td>
						<label><input type="radio" name="status" value="S" ${ mode eq 'create' || team.status eq 'S' ? 'checked' : '' }><span>활성</span></label>
						<label><input type="radio" name="status" value="H" ${ team.status eq 'H' ? 'checked' : '' }><span>비활성</span></label>
					</td>
				</tr>
			</table>
		</div>

		<h2>메뉴권한</h2>
		<div class="white_box">
			<table class="board">
				<tr><td>
					<label><input type="checkbox" id="checkAll"><span>전체선택</span></label>
				</td></tr>
				<tr><td>
					${ menuUl }
				</td></tr>
				<tr><td>
					<label><input type="checkbox" name="updateAuth" value="Y" ${ team.updateAuth eq 'Y' ? 'checked' :'' }> <span>수정/석제 권한 부여</span></label>
					&nbsp;&nbsp;&nbsp;&nbsp;* 체크 시 메뉴에 대한 수정 권한이 부여됩니다.
				</td></tr>
			</table>
		</div>

		<div class="btnArea">
			<c:if test="${ mode eq 'create' }">
				<a href="javascript:goSubmit()" class="btnTypeC btnSizeA"><span>등록</span></a>
			</c:if>
			<c:if test="${ mode eq 'modify' && updateAuth eq 'Y' }">
				<a href="javascript:goSubmit()" class="btnTypeC btnSizeA"><span>수정</span></a>
			</c:if>
		</div>
		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
