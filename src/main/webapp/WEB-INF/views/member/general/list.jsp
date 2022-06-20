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


	function goExcel() {
		$("#searchForm").attr("action", "excel");
		$("#searchForm").submit();
		$("#searchForm").attr("action", "");
	}

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
					<th>회원ID</th>
					<td>
						<input type="text" name="memId" value="${ param.memId }" style="width:100%" >
					</td>
					<th>회원명</th>
					<td>
						<input type="text" name="name" value="${ param.name }" style="width:100%" >
					</td>
				</tr>
				<tr>
					<th>성별</th>
					<td>
						<label><input type="radio" name="gender" value="" ${ empty param.gender ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="gender" value="M" ${ param.gender eq 'M' ? 'checked' : '' }><span>남성</span></label>
						<label><input type="radio" name="gender" value="W" ${ param.gender eq 'W' ? 'checked' : '' }><span>여성</span></label>
					</td>
					<th>등급</th>
					<td>
						<select name="gradeNo" style="width:200px">
							<option value="" ${ empty param.cate ? 'selected' : '' }>전체</option>
							<c:forEach items="${ listGrade }" var="row">
								<option value="${ row.gradeNo }" ${ param.gradeNo eq row.gradeNo ? 'selected' : '' }>${ row.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>휴대폰번호</th>
					<td>
						<input type="text" name="mtel" value="${ param.mtel }" style="width:100%" >
					</td>
					<th>이메일</th>
					<td>
						<input type="text" name="email" value="${ param.email }" style="width:100%" >
					</td>
				</tr>
				<tr>
					<th>가입일</th>
					<td>
						<div class="dateBox">
							<input type="text" name="fromCdate" id="fromCdate" readonly value="${ param.fromCdate }">
						</div>
						<div class="dateBox">
							<input type="text" name="toCdate" id="toCdate" readonly value="${ param.toCdate }">
						</div>
					</td>
					<th>병의원 ID</th>
					<td>
						<input type="text" name="clinicId" value="${ param.clinicId }" style="width:100%" >
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
				<a href="javascript:goExcel()" class="btnTypeC btnSizeA"><span>엑셀 다운로드</span></a>
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
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th>No</th>
					<th>회원ID</th>
					<th>회원명</th>
					<th>등급</th>
					<th>포인트</th>
					<th>가입일</th>
					<th>가입경로</th>
					<th>최종로그인</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.memNo }" /></td>
					<td><a href="edit?memNo=${ row.memNo }">${ utils.idMasking(row.memId) }</a></td>
					<td><a href="edit?memNo=${ row.memNo }">${ utils.nameMasking(row.name) }</a></td>
					<td><c:out value="${ row.gradeName }" /></td>
					<td><c:out value="${ row.curPoint }" /></td>
					<td><fmt:formatDate value="${row.cdate}" pattern="${ DateTimeFormat }"/></td>
					<td><c:out value="${ row.joinType }" /></td>
					<td><fmt:formatDate value="${row.loginDate}" pattern="${ DateTimeFormat }"/></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="paging"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
