<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setAttribute("MENU_TITLE", new String("우편번호 찾기"));
	String layerId = request.getParameter("layerId");
%>
<!DOCTYPE HTML>
<html lang="ko">
<head>
<c:import url="/include/head" />
<link rel="stylesheet" href="/static/css/popup.css?time=<%=System.currentTimeMillis()%>" type="text/css">
</head>
<body>
<div id="popWrap" class="typeB">
	<h1>${MENU_TITLE}</h1>
	<div id="popCont">
		
		<div class="btnArea">
			<span><button class="btnTypeE sizeB" onclick="hidePopupLayer();">취소</button></span>
			<span><button class="btnTypeA sizeB">등록</button></span>
		</div>
	</div><!-- //popCont -->
</div><!-- //popWrap -->
<script>
//팝업높이조절
setPopup(<%=layerId%>);
</script>
</body>
</html>