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
		$("#fromCdate").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		$("#toCdate").datepicker({
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
					<th>제목</th>
					<td >
						<input type="text" name="title" value="${ param.title }" style="width:100%" >
					</td>
					<th>등록일</th>
					<td>
						<div class="dateBox">
							<input type="text" name="fromCdate" id="fromCdate" readonly value="${ param.fromCdate }">
						</div>
						<div class="dateBox">
							<input type="text" name="toCdate" id="toCdate" readonly value="${ param.toCdate }">
						</div>
					</td>
				</tr>
				<tr>
					<th>구분</th>
					<td >
						<label><input type="radio" name="gubun" value="" ${ empty param.gubun ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="gubun" value="1" ${ param.gubun eq '1' ? 'checked' : '' }><span>이미지</span></label>
						<label><input type="radio" name="gubun" value="2" ${ param.gubun eq '2' ? 'checked' : '' }><span>영상</span></label>
					</td>
					<th>공개여부</th>
					<td>
						<label><input type="radio" name="status" value="" ${ empty param.status ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="status" value="S" ${ param.status eq 'S' ? 'checked' : '' }><span>공개</span></label>
						<label><input type="radio" name="status" value="H" ${ param.status eq 'H' ? 'checked' : '' }><span>비공개</span></label>
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
					<th>구분</th>
					<th>제목</th>
					<th>메인노출순위</th>
					<th>공개여부</th>
					<th>등록일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.mno }" /></td>
					<td><c:out value="${ row.gubun eq '1' ? '이미지':'영상' }" /></td>
					<td><a href="edit?mno=${ row.mno }"><c:out value="${ row.title }" /></a></td>
					<td><c:out value="${ row.rank }" /></td>
					<td><c:out value="${ row.statusName }" /></td>
					<td><fmt:formatDate value="${row.cdate}" pattern="${ DateTimeFormat }"/></td>
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
