<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${ list }" var="row">
	<option value="${ row.cateNo }">${ row.name }</option>
</c:forEach>
