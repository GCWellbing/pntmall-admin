<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
$(function() {
	<c:if test="${ system eq 'local' }">
	proc();
	</c:if>
});

function proc() {
	if($("input[name=adminId]").val() == 'ID') {
		alert("아이디를 입력하지 않았습니다.");
		$("input[name='adminId']").focus();
	} else if($("input[name=passwd]").val() == 'PASSWORD') {
		alert("암호를 입력하지 않았습니다.");
		$("input[name='passwd']").focus();
	} else {
        disableScreen();
        ef.proc($("#form"), function(result) {
			if (result.succeed) window.location.href = "/index";
			else alert(result.message);

			enableScreen();
		});
	}
}

function checkSpacePress(){
	if(window.event.keyCode == 32) {
		$("#adminId").val($("#adminId").val().replace(" ",""));
		$("#passwd").val($("#passwd").val().replace(" ",""));
	}
}

</script>
</head>
<body>
<form id="form" name="form" method="POST" action="/loginProc">
<div class="loginArea">
	<h1>
		<span><img src="/static/images/logo_login.png" alt="Dr.PNT"></span>
		<i>Administration</i>
	</h1>
	<fieldset>
		<input type="text" name="adminId" value="${ system eq 'local' ? 'super' : '' }" class="txt" placeholder="ID" onKeyUp="JavaScript:checkSpacePress();">
		<input type="password" name="passwd" value="${ system eq 'local' ? '1111' : '' }" class="txt" placeholder="PASSWORD" onKeyUp="JavaScript:checkSpacePress();">
		<a href="#" onclick="proc(); return false;" class="btn_login">LOGIN</a>
	</fieldset>
	<p class="txtArea">If you forgot ID and password, please contact <a href="mailto:gcwb@gccorp.com">gcwb@gccorp.com</a></p>
</div>
</form>

</body>
</html>
