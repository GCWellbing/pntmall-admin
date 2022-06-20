<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<select name="pageSize" onchange="$('#searchForm').submit()" style="width:100px;">
	<option value="10" ${ param.pageSize == 10 ? 'selected' : '' }>10 Views</option>
	<option value="20" ${ param.pageSize == 20 ? 'selected' : '' }>20 Views</option>
	<option value="50" ${ param.pageSize == 50 ? 'selected' : '' }>50 Views</option>
	<option value="100" ${ param.pageSize == 100 ? 'selected' : '' }>100 Views</option>
</select>