<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	function setMember() {
		if ($("#words").val() == "") {
			alert("아이디를 입력하세요.");
			return;
		}
		var words = $("#words").val().replace(/\n/gi, "/");

		opener.setMember(words);
		self.close();
	}
</script>
</head>
<body>
<div id="popWrapper">
	<h1>회원 아이디 일괄적용</h1>
	<div id="popContainer">
		<form name="searchForm" id="searchForm">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="">
				</colgroup>
				<tr>
					<td>
						<p style="margin-top:5px;margin-bottom:5px;">
							아이디를 Enter 구분으로 입력하세요. (최대 1,000개)
							<textarea name="words" id="words" style="width:99%;height:400px"></textarea>
						</p>
					</td>
				</tr>
			</table>
		</div>
		</form>
		<div class="btnArea">
			<a href="javascript:setMember();" class="btnTypeC btnSizeA"><span>확인</span></a>
		</div>
	</div>
</div><!-- //wrapper -->
</body>
</html>
