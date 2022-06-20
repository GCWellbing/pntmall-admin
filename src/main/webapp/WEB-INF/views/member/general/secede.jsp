<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>

	function secedeProc() {
		if($("#secedeMemo").val() == "") {
			alert("탈퇴시 남기실 말씀을 입력하세요.");
			return;
		}

		disableScreen();
		ef.proc($("#editForm"), function(result) {
			alert(result.message);
			if(result.succeed) opener.location.href = "list";
			enableScreen();
			self.close();
		});


	}
</script>
</head>
<body>
<div id="popWrapper">
	<h1>탈퇴</h1>
	<div id="popContainer">
		<form name="editForm" id="editForm" action="secedeProc">
		<input type="hidden" name="memNo" id="memNo" value="${param.memNo}">
		<input type="hidden" name="secedeRsn" id="secedeRsn" value="020008">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="160">
					<col width="">
				</colgroup>
				<tr>
					<th>남기실 말씀</th>
					<td>
						<textarea name="secedeMemo" id="secedeMemo"></textarea>
					</td>
				</tr>
				<tr>
					<th>처리자명</th>
					<td>${adminSession.adminId }(${ adminSession.name })</td>
				</tr>
			</table>
		</div>
		</form>
		<div class="btnArea">
			<a href="javascript:secedeProc();" class="btnTypeC btnSizeA"><span>확인</span></a>
		</div>
	</div>
</div><!-- //wrapper -->
</body>
</html>
