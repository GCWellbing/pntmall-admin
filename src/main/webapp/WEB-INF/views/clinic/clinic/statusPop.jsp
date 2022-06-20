<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>

	function statusProc() {

		if($("#reason").val() == "") {
			alert("사유를 입력하여 주세요.");
			return;
		}

		disableScreen();
		ef.proc($("#editForm"), function(result) {
			alert(result.message);
			if(result.succeed) opener.location.reload();
			enableScreen();
			self.close();
		});


	}
</script>
</head>
<body>
<div id="popWrapper">
	<h1>${clinicJoin.approve eq '006002' ? '일괄 승인(활성) 처리':'일괄 비활성 처리' }</h1>
	<div id="popContainer">
		<form name="editForm" id="editForm" method="POST" action="setStatusProc">
		<input type="hidden" name="approve" id="approve" value="${clinicJoin.approve }">
		<input type="hidden" name="chkMemNo" id="chkMemNo" value="${clinicJoin.chkMemNo }">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="160">
					<col width="">
				</colgroup>
				<tr>
					<th>사유</th>
					<td>
						<textarea name="reason" id="reason"></textarea>
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
			<a href="javascript:statusProc();" class="btnTypeC btnSizeA"><span>확인</span></a>
			<a href="javascript:self.close();" class="btnTypeC btnSizeA"><span>취소</span></a>
		</div>
	</div>
</div><!-- //wrapper -->
</body>
</html>
