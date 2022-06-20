<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>

	function goSubmit() {

		if($("input[name=status]:checked").val() == "" || $("input[name=status]:checked").val() == undefined){
			alert("예약상태 설정하세요.");
			return false;
		}

		if($("input[name=status]:checked").val() == "025003" && $("#reason").val() == ""){
			alert("상담취소인 경우에는 불가 사유를 입력하셔야 합니다.");
			return false;
		}

		disableScreen();
		ef.proc($("#editForm"), function(result) {
			alert(result.message);
			if(result.succeed) location.href = "list";
			enableScreen();
		});
	}

</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="editForm" id="editForm" method="POST" action="modify">
			<input type="hidden" name="resNo" value="${ reservation.resNo }" />
			<input type="hidden" name="clinicMemNo" value="${ reservation.clinicMemNo }" />
			<input type="hidden" name="cuser" value="${ reservation.cuser }" />
			<input type="hidden" name="uuser" value="${ reservation.uuser }" />
			<input type="hidden" name="rdate" value="${ reservation.rdate }" />
			<input type="hidden" name="rtime" value="${ reservation.rtime }" />
			<input type="hidden" name="cate" value="${ reservation.cate }" />
			<input type="hidden" name="cateName" value="${ reservation.cateName }" />
		<h2>예약 상태 확인</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>유형</th>
					<td>${ reservation.cate eq '016005' ? '닥터팩 화상상담 예약':'병의원 방문 예약' }</td>
				</tr>
				<tr>
					<th>병의원명</th>
					<td>${ reservation.clinicName}</td>
				</tr>
				<tr>
					<th>예약자명</th>
					<td><a href="/member/general/edit?memNo=${ reservation.cuser}" style="color:#2d8ab5;text-decoration:underline">${ reservation.cuserName}</a></td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td>${ reservation.mtel1}${ reservation.mtel2}</td>
				</tr>
				<tr>
					<th>접수 신청일</th>
					<td><fmt:formatDate value="${reservation.cdate}" pattern="${ DateTimeFormat }"/></td>
				</tr>
				<tr>
					<th>예약일</th>
					<td>${ reservation.rdate}</td>
				</tr>
				<tr>
					<th>예약시간</th>
					<td>${ reservation.rtime}</td>
				</tr>
				<tr>
					<th>예약목적</th>
					<td>${ reservation.cateName}</td>
				</tr>
				<tr>
					<th>예약목적(상세)</th>
					<td>${ reservation.content}</td>
				</tr>
				<tr>
					<th>예약상태 설정</th>
					<td>
						<c:if test="${ reservation.status eq '025001' }">
							<label><input type="radio" name="status" value="025004" ${ reservation.status eq '025004' ? 'checked' : '' }><span>상담예약확정</span></label>
							<label><input type="radio" name="status" value="025003" ${ reservation.status eq '025003' ? 'checked' : '' }><span>상담취소</span></label>
						</c:if>
						<c:if test="${ reservation.status ne '025001' }">
							${ reservation.statusName}
						</c:if>
					</td>
				</tr>
				<tr>
					<th>불가 사유</th>
					<td><textarea name="reason" id="reason" style="width:100%;height:100px">${ reservation.reason }</textarea></td>
				</tr>
			</table>
		</div>

		<div class="btnArea" style="display:${reservation.status eq '025001' ? '':'none'};">
			<span class="fl">
				<a href="${ retrivePath }" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
			<a href="javascript:goSubmit()" class="btnTypeC btnSizeA"><span>저장</span></a>
		</div>
		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
