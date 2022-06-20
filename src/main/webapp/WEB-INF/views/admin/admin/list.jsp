<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	$(function() {
		$("#resetBtn").click(function() {
			document.location.href = "list";
		});
	});
</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="searchForm" id="searchForm">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>관리자ID</th>
					<td>
						<input type="text" name="adminId" value="${ param.adminId }" style="width:100%" >
					</td>
					<th>소속</th>
					<td>
						<select name="teamNo" style="width:calc(50% - 5px)">
							<option value=""></option>
							<c:forEach items="${ teamList }" var="row">
							<option value="${ row.teamNo }" ${ row.teamNo eq param.teamNo ? 'selected' : '' }>${ row.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>관리자명</th>
					<td>
						<input type="text" name="name" value="${ param.name }" style="width:100%">
					</td>
					<th>상태</th>
					<td>
						<label><input type="radio" name="status" value="" ${ empty param.status || param.status eq '' ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="status" value="S" ${ param.status eq 'S' ? 'checked' : '' }><span>활성</span></label>
						<label><input type="radio" name="status" value="H" ${ param.status eq 'H' ? 'checked' : '' }><span>비활성</span></label>
					</td>
				</tr>
			</table>			
			<div class="btnArea ac">
				<input type="submit" class="btnTypeA btnSizeA" value="검색">
				<input type="button" class="btnTypeB btnSizeA" id="resetBtn" value="초기화">
			</div>
		</div>
		<div class="listHead">
			<div class="fl">
				<div class="totalArea">
					<strong class="totalTit">Total</strong> <span class="totalCount"><fmt:formatNumber value="${ fn:length(list) }" pattern="#,###" /></span>
				</div>
			</div>
			<p class="fr">
				<c:import url="/include/pageSize?pageSize=${ param.pageSize }" />
			</p>
		</div>
		</form>
		
		<table class="list">
			<colgroup>
				<col width="60px">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th>No</th>
					<th>소속</th>
					<th>관리자ID</th>
					<th>관리자명</th>
					<th>상태</th>
					<th>등록일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">						
				<tr>
					<td><c:out value="${ row.adminNo }" /></td>
					<td><c:out value="${ row.teamName }" /></td>
					<td><a href="edit?adminNo=${ row.adminNo }"><c:out value="${ row.adminId }" /></a></td>
					<td><a href="edit?adminNo=${ row.adminNo }"><c:out value="${ row.name }" /></a></td>
					<td><c:out value="${ row.statusName }" /></td>
					<td><fmt:formatDate value="${ row.cdate }" pattern="${ DateTimeFormat }" /></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
			<a href="edit" class="btnTypeC btnSizeA"><span>등록</span></a>
		</div>
		<div class="paging btnSide"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
