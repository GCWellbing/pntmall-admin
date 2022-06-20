<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="utils" scope="request" class="com.pntmall.common.utils.Utils"/>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<ul class="tabMenu">
			<li class="on"><a href="editN?memNo=${ param.memNo }">기본정보</a></li>
			<li><a href="order?memNo=${ param.memNo }">주문이력</a></li>
			<li><a href="qnaList?quser=${ param.memNo }">문의내역</a></li>
			<li><a href="reser?cuser=${ param.memNo }">병의원예약이력</a></li>
		</ul>
		<h2>기본정보</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>회원ID</th>
					<td>
						${ member.memId }
					</td>
					<th>마이클리닉 병의원코드</th>
					<td>
						${ member.clinicId }
					</td>
				</tr>
				<tr>
					<th>탈퇴사유</th>
					<td colspan=3>
						${ member.secedeRsnName }
					</td>
				</tr>
				<tr>
					<th>남기는 말씀</th>
					<td colspan=3>
						${ member.secedeMemo }
					</td>
				</tr>
				<tr>
					<th>탈퇴처리자</th>
					<td colspan=3>
						<c:if test="${ member.secedeRsn eq '020008' }">
							${ utils.idMasking(member.uuserId) } (${ utils.nameMasking(member.uuserName) })
						</c:if>
						<c:if test="${ member.secedeRsn ne '020008' }">
							본인 탈퇴
						</c:if>
					</td>
				</tr>
			</table>
		</div>

		<div class="btnArea">
			<span class="fl">
				<a href="${ retrivePath }" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->


</body>
</html>
