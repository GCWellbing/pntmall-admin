<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="utils" scope="request" class="com.pntmall.common.utils.Utils"/>

<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	$(function() {
		$("#fromQdate").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		$("#toQdate").datepicker({
			changeMonth: true,
		    changeYear: true
		});

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
					<th>병의원명</th>
					<td>
						<input type="text" name="clinicName" value="${ param.clinicName}" style="width:100%" >
					</td>
					<th>제목+내용</th>
					<td>
						<input type="text" name="title" value="${ param.title }" style="width:100%" >
					</td>
				</tr>
				<tr>
					<th>등록일</th>
					<td>
						<div class="dateBox">
							<input type="text" name="fromQdate" id="fromQdate" readonly value="${ param.fromQdate }">
						</div>
						<div class="dateBox">
							<input type="text" name="toQdate" id="toQdate" readonly value="${ param.toQdate }">
						</div>
					</td>
					<th>문의유형</th>
					<td>
						<select name="cate" style="width:200px">
							<option value="" ${ empty param.cate ? 'selected' : '' }>전체</option>
							<c:forEach items="${ cateList2 }" var="row">
								<option value="${ row.code1 }${ row.code2 }" ${ param.cate eq row.code1+=row.code2 ? 'selected' : '' }>${ row.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>답변상태</th>
					<td>
						<label><input type="radio" name="status" value="" ${ empty param.status ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="status" value="Q" ${ param.status eq 'Q' ? 'checked' : '' }><span>답변준비중</span></label>
						<label><input type="radio" name="status" value="A" ${ param.status eq 'A' ? 'checked' : '' }><span>답변완료</span></label>
					</td>
					<th>작성자</th>
					<td>
						<input type="text" name="quserName" id="quserName" value="${ param.quserName }">
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
					<strong class="totalTit">Total</strong> <span class="totalCount"><fmt:formatNumber value="${ count }" pattern="#,###" /></span>
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
					<th>병의원명</th>
					<th>문의유형</th>
					<th>제목</th>
					<th>작성자</th>
					<th>답변상태</th>
					<th>등록일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.qnaNo }" /></td>
					<td>${ row.clinicName }</td>
					<td><c:out value="${ row.cateName }" /></td>
					<td><a href="edit?qnaNo=${ row.qnaNo }"><c:out value="${ row.title }" /></a></td>
					<td>
						<c:if test="${empty row.quserName}" >
					 		-
				 		</c:if>
						<c:if test="${not empty row.quserName}" >
					 		${ utils.idMasking(row.quserId) }(${ utils.nameMasking(row.quserName) })
				 		</c:if>
					</td>
					<td><c:out value="${ row.statusName }" /></td>
					<td><fmt:formatDate value="${row.qdate}" pattern="${ DateTimeFormat }"/></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="paging "><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
