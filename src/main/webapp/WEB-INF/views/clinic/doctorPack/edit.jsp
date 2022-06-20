<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:eval expression="@environment.getProperty('directSend.front.returnUrl')" var="directSendFrontReturnUrl" />
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	function goSubmitMemo() {
		disableScreen();
		$("#editForm").attr("action", "memo");
		ef.proc($("#editForm"), function(result) {
			alert(result.message);
			//if(result.succeed) location.href = "list";
			enableScreen();
		});
	}

	function goSubmit() {
		if($("#editForm input[name=pno]").length < 1) {
			alert("신청 제품 정보가 존재하지 않습니다.");
			return false;
		} else if($("#editForm input[name=pno]").length < 2 || $("#editForm input[name=pno]").length > 8) {
			alert("닥터팩은 최소 2개부터 최대 7개 제품까지 선택하여 주문 가능합니다.\n(개인맞춤형 건강기능식품 소분 조합 원칙상 7종 기능성까지만 조합 가능)");
			return false;
		}

		if($("#confirmYn").is(":checked") == false) {
			alert("고객과 맞춤형 상담을 완료하셨습니까? 를 선택하세요");
			return false;
		}

		disableScreen();
		$("#editForm").attr("action", "complete");
		ef.proc($("#editForm"), function(result) {
			alert(result.message);
			if(result.succeed) location.href = "list";
			enableScreen();
		});
	}

	function setProduct(options) {
		var html;
		for(var i = 0; i < options.length; i++) {
			if($("input[name=pno][value=" + options[i].pno + "]").length == 0) {
				html = $("#optionRow").html();
				html = html.replace(/##OPTION_PNO##/gi, options[i].pno);
				html = html.replace(/##OPTION_NM##/gi, options[i].pname);
				html = html.replace(/##OPTION_NUTRITIONNAME##/gi, options[i].nutritionName);
				html = html.replace(/##OPTION_SALE_PRICE##/gi, options[i].salePrice);
				$("#optionList").append(html);
			}
		}
	}

	function removeOption(obj) {
		$(obj).parent().parent().remove();
	}

</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="editForm" id="editForm" method="POST" action="complete">
			<input type="hidden" name="resNo" value="${ reservation.resNo }" />
			<input type="hidden" name="memNo" value="${ reservation.cuser }" />
			<input type="hidden" name="cuser" value="${ reservation.cuser }" />
		<h2>주문자 정보</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>아이디</th>
					<td>${ reservation.cuserId}</td>
					<th>주문자명</th>
					<td><a href="/member/general/edit?memNo=${ reservation.cuser}" style="color:#2d8ab5;text-decoration:underline">${ reservation.cuserName}</a></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>${ reservation.email}</td>
					<th>휴대전화</th>
					<td>${ reservation.mtel1}${ reservation.mtel2}</td>
				</tr>
				<tr>
					<th>병의원명</th>
					<td>${ reservation.clinicName}</td>
					<th>병의원ID</th>
					<td>${ reservation.clinicMemId}</td>
				</tr>
				<tr>
					<th>화상상담 예약일</th>
					<td>${ reservation.rdate}</td>
					<th>예약시간</th>
					<td>${ reservation.rtime}</td>
				</tr>
				<tr>
					<th>헬스체크 결과지</th>
					<td><a href="${directSendFrontReturnUrl}/healthCheck/resultSheet?resNo=${ reservation.resNo}&clinicMemNo=${ reservation.clinicMemNo}" target="_blank">결과 보기</a></td>
					<th>신청일</th>
					<td><fmt:formatDate value="${reservation.cdate}" pattern="${ DateFormat }"/></td>
				</tr>
				<tr>
					<th>상태</th>
					<td>
						<c:choose>
							<c:when test="${reservation.status eq '025005'}">승인</c:when>
							<c:when test="${reservation.status eq '025004'}">미승인</c:when>
							<c:when test="${reservation.status eq '025006'}">예약취소</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</td>
					<th>상담완료여부</th>
					<td>
						<c:choose>
							<c:when test="${reservation.status eq '025005'}">완료</c:when>
							<c:when test="${reservation.status eq '025004'}">미완료</c:when>
							<c:when test="${reservation.status eq '025006'}">예약취소</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<th>메모</th>
					<td colspan=3><textarea name="memo" style="width:100%;height:50px">${ reservation.memo}</textarea> <a href="#" onclick="javascript:goSubmitMemo(); return false" class="btnTypeC btnSizeA"><span>등록</span></a></td>
				</tr>
			</table>
		</div>
		<h2>신청제품정보
			<span class="fr" style="display:${ reservation.status eq '025004' ? '':'none'};">
				<a href="#" onclick="showPopup('/popup/productDoctorPack', '500', '600'); return false" class="btnSizeA btnTypeD">추가</a>
			</span></h2>
		<div class="">
			<table class="list">
				<colgroup>
					<col width="">
					<col width="">
					<col width="">
					<col width="">
					<col width="100px">
				</colgroup>
				<thead>
					<tr>
						<th>제품코드</th>
						<th>제품명</th>
						<th>영양/기능 정보</th>
						<th>판매가</th>
						<th>관리</th>
					</tr>
				</thead>
				<tbody id="optionList">
					<c:forEach items="${ reservationProductList }" var="row">
						<tr>
							<td>
								<c:out value="${ row.pno }" />
								<input type='hidden' name='pno' value='${ row.pno }'>
							</td>
							<td class="al">
								<a href="${ directSendFrontReturnUrl }/product/detail?pno=${ row.pno }" target="_blank"><c:out value="${ row.pname }" /></a>
							</td>
							<td class="al">
								<c:out value="${ row.nutritionName }" />
							</td>
							<td>
								<fmt:formatNumber value="${ row.salePrice }" pattern="#,###" />
							</td>
							<td><input type='button' class='btnSizeC' onclick='removeOption(this); return false;' value='삭제' style="display:${ reservation.status eq '025004' ? '':'none'};"></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="btnArea">
			<span class="fl">
				<a href="${ retrivePath }" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
			<div style="display:${ reservation.status eq '025004' ? '':'none'};">
				<input type="checkbox" name="confirmYn" id="confirmYn" value="Y"> 고객과 맞춤형 상담을 완료하셨습니까?
				<a href="javascript:goSubmit()" class="btnTypeC btnSizeA"><span>저장</span></a>
			</div>
		</div>
		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->
<table style="display:none">
	<tbody id="optionRow">
		<tr>
			<td>
				##OPTION_PNO##
				<input type='hidden' name='pno' value='##OPTION_PNO##'>
			</td>
			<td class="al">
				<a href="${ directSendFrontReturnUrl }/product/detail?pno=##OPTION_PNO##" target="_blank">##OPTION_NM##</a>
			</td>
			<td class="al">
				##OPTION_NUTRITIONNAME##
			</td>
			<td>
				##OPTION_SALE_PRICE##
			</td>
			<td><input type='button' class='btnSizeC' onclick='removeOption(this); return false;' value='삭제'></td>
		</tr>
	</tbody>
</table>

</body>
</html>
