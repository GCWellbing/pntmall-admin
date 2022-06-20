<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	var v;

	$(function() {

	});


</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<ul class="tabMenu">
			<li><a href="editN?memNo=${ param.memNo }">기본정보</a></li>
			<li><a href="order?memNo=${ param.memNo }">주문이력</a></li>
			<li class="on"><a href="qnaList?quser=${ param.memNo }">문의내역</a></li>
			<li><a href="reser?cuser=${ param.memNo }">병의원예약이력</a></li>
		</ul>
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="qnaNo" value="${ qna.qnaNo }" />
		<h2>정보 등록</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>문의유형</th>
					<td>${ qna.cateName}</td>
					<th>답변상태</th>
					<td>${ qna.statusName}</td>
				</tr>
				<tr style="display:${empty qna.pname?'none':'' };">
					<th>문의 제품</th>
					<td colspan=3>${ qna.pname }
					</td>
				</tr>
				<tr style="display:${empty qna.clinicName?'none':'' };">
					<th>병의원</th>
					<td colspan=3>${ qna.clinicName }
					</td>
				</tr>
				<tr>
					<th>제목</th>
					<td colspan=3>${ qna.title }
					</td>
				</tr>
				<tr>
					<th>작성자</th>
					<td>${ qna.quser }(${ qna.quserName })</td>
					<th>작성일</th>
					<td><fmt:formatDate value="${qna.qdate}" pattern="${ DateTimeFormat }"/></td>
				</tr>
				<tr>
					<th>내용</th>
					<td colspan=3>
						${ qna.question }
					</td>
				</tr>
				<tr style="display:${fn:length(clinicImgList) == 0?'none':'' };">
					<th>첨부파일</th>
					<td colspan=3>
						<c:forEach items="${ clinicImgList }" var="row">
							<div>
								<img src='${row.attach}'>
							</div>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th>답변<sup>*</sup></th>
					<td colspan=3>
						${ qna.answer }
					</td>
				</tr>
			</table>
		</div>

		<div class="white_box">
			<table class="board">
				<tr>
					<th>등록일/등록자</th>
					<td>
						<input type="hidden" name="cuser" value="${qna.cuser}">
						<input type="hidden" name="cdate" value="${qna.cdate}">
						<fmt:formatDate value="${ qna.cdate }" pattern="${ DateTimeFormat }" /> / ${qna.cuserName}
					</td>
					</td>
					<th>수정일/수정자</th>
					<td><fmt:formatDate value="${qna.udate}" pattern="${ DateTimeFormat }"/> / ${qna.uuserName}
					</td>
				</tr>
			</table>
		</div>

		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
