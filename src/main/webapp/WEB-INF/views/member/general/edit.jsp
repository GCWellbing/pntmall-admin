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
		v = new ef.utils.Validator($("#editForm"));

	});

	function goSubmit() {

		if(v.validate()) {
			if(!chkValidEmail()){
				return false;
			}

			disableScreen();
			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "${ retrivePath }";
				enableScreen();
			});
		}
	}

	function goSubmitSecede() {
/*
		if(v.validate()) {
			disableScreen();
			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list";
				enableScreen();
			});
		} */
	}

	function goSubmitCs() {

		if(v.validate()) {
			disableScreen();
			ef.proc($("#editFormCs"), function(result) {
				alert(result.message);
				if(result.succeed) location.reload();
				enableScreen();
			});
		}
	}

	function goDeleteCS(sno) {
		disableScreen();
		$.ajax({
			type : "POST",
			url : "modifyCs",
			data : {"cno":sno,
					"status":"D"},
			dataType : "json"
		})
		.done(function(result) {
			alert(result.message);
			if(result.succeed) location.reload();
			enableScreen();
		});
	}

	function setGrade(grades) {
		$("#gradeNo").val(grades[0].gradeNo);
		$("#gradeName").val(grades[0].name);
	}

	function validateEmail(sEmail) {
		var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
		if (filter.test(sEmail)) {
			return true;
		} else {
			return false;
		}
	}


	function chkValidEmail(){
		var sEmail = $('#email').val();
		console.log(sEmail);
		if (validateEmail(sEmail)) {
		    return true;
		} else {
			alert("이메일 형식을 확인해주세요.");
		    return false;
		}
	}


</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<ul class="tabMenu">
			<li class="on"><a href="edit?memNo=${ param.memNo }">기본정보</a></li>
			<li><a href="point?memNo=${ param.memNo }">포인트이력</a></li>
			<li><a href="coupon?memNo=${ param.memNo }">쿠폰이력</a></li>
			<li><a href="order?memNo=${ param.memNo }">주문이력</a></li>
			<li><a href="qnaList?quser=${ param.memNo }">문의내역</a></li>
			<li><a href="health?cuser=${ param.memNo }">마이헬스체크이력</a></li>
			<li><a href="reser?cuser=${ param.memNo }">병의원예약이력</a></li>
		</ul>
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="memNo" value="${ member.memNo }" />
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
					<th>회원명</th>
					<td>
						<input type="hidden" name="name" id="name" value="${ member.name }">
						${ member.name }
					</td>
				</tr>
				<tr>
					<th>생년월일</th>
					<td>
						<c:if test="${ fn:substring(member.birthday,4,8) ne '0000' }">
							<fmt:parseDate value="${member.birthday}" var="parseDateValue" pattern="yyyyMMdd"/>
							<fmt:formatDate value="${parseDateValue}" pattern="${ DateFormat }"/>
						</c:if>
						<c:if test="${ fn:substring(member.birthday,4,8) eq '0000' }">
							${ fn:substring(member.birthday,0,4) }
						</c:if>
					</td>
					<th>성별</th>
					<td>
						${ member.gender eq 'M' ? '남': member.gender eq 'W' ? '여':''  }
					</td>
				</tr>
				<tr>
					<th>등급</th>
					<td>
						<input type="hidden" name="gradeNoOld" id="gradeNoOld" value="${ member.gradeNo }" style="width:200px" maxlength="4000">
						<input type="hidden" name="gradeNo" id="gradeNo" value="${ member.gradeNo }" style="width:200px" maxlength="4000">
						<input type="text" name="gradeName" id="gradeName" value="${ member.gradeName }" style="width:200px" maxlength="4000" readonly>
						<a href="#" onclick="showPopup('/popup/memGrade?singleChoice=Y', '500', '600'); return false" class="btnSizeA btnTypeD">변경</a>
					</td>
				</tr>
				<tr>
					<th>휴대폰번호</th>
					<td>
						<input type="text" name="mtel1" id="mtel1" value="${ member.mtel1 }" style="width:200px" maxlength="4000">
						<input type="text" name="mtel2" id="mtel2" value="${ member.mtel2 }" style="width:200px" maxlength="4000" maxlength="8" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
					</td>
					<th>문자 수신동의</th>
					<td>
						<label><input type="radio" name="smsYn" value="Y" ${ member.smsYn eq 'Y' ? 'checked' : '' }><span>동의</span></label>
						<label><input type="radio" name="smsYn" value="N" ${ member.smsYn eq 'N' or member.smsYn eq '' ? 'checked' : '' }><span>미동의</span></label>
					</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>
						<input type="text" name="email" id="email" value="${ member.email }" style="width:200px" maxlength="4000">
					</td>
					<th>이메일 수신동의</th>
					<td>
						<label><input type="radio" name="emailYn" value="Y" ${ member.emailYn eq 'Y' ? 'checked' : '' }><span>동의</span></label>
						<label><input type="radio" name="emailYn" value="N" ${ member.emailYn eq 'N' or member.emailYn eq '' ? 'checked' : '' }><span>미동의</span></label>
					</td>
				</tr>
				<tr>
					<th>마이클리닉 이름</th>
					<td>
						${ member.clinicName }
					</td>
					<th>마이클리닉 코드</th>
					<td>
						${ member.clinicId }
					</td>
				</tr>
				<tr>
					<th>가입일</th>
					<td>
						<fmt:formatDate value="${member.cdate}" pattern="${ DateTimeFormat }"/>
					</td>
					<th>가입경로</th>
					<td>
						${ member.joinType }(${ member.joinDevice })
					</td>
				</tr>
				<tr>
					<th>최근로그인</th>
					<td>
						<fmt:formatDate value="${member.loginDate}" pattern="${ DateTimeFormat }"/>
					</td>
					<th>총 로그인 수</th>
					<td>
						${ member.loginCnt }
					</td>
				</tr>
				<tr>
					<th>총 주문금액</th>
					<td>
						<fmt:formatNumber value="${ totOrderAmt }" pattern="#,###" />
					</td>
					<th>총 주문건수</th>
					<td>
						<fmt:formatNumber value="${ totOrderCount }" pattern="#,###" />
					</td>
				</tr>
				<tr>
					<th>메모</th>
					<td colspan=3>
						<textarea name="memo" style="width:100%;height:100px">${ member.memo }</textarea>
					</td>
				</tr>
			</table>
		</div>

		</form>

		<h2>CS상담정보</h2>
		<form name="editFormCs" id="editFormCs" method="POST" action="createCs">
			<input type="hidden" name="memNo" value="${ member.memNo }" />
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>메모</th>
					<td>
						<textarea name="memo" style="width:100%;height:100px"></textarea>
						<c:if test="${ mode eq 'modify' && updateAuth eq 'Y' }">
							<a href="javascript:goSubmitCs()" class="btnTypeC btnSizeA"><span>입력</span></a>
						</c:if>
					</td>
				</tr>
			</table>
		</div>
		</form>


		<h2>상담정보 리스트</h2>
		<form name="editFormCs" id="editFormCs" method="POST" action="create">
			<input type="hidden" name="memNo" value="${ member.memNo }" />
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
					<col width="">
					<col width="">
					<col width="">
				</colgroup>
				<tr>
					<th>No</th>
					<th>내용</th>
					<th>등록일</th>
					<th>등록자</th>
					<th>관리</th>
				</tr>
				<c:forEach items="${ memberCs }" var="row">
				<tr>
					<td>
						${ row.cno }
					</td>
					<td>
						${ row.memo }
					</td>
					<td>
						<fmt:formatDate value="${row.cdate}" pattern="${ DateTimeFormat }"/>
					</td>
					<td>
						${ row.cuserName }
					</td>
					<td>
						<c:if test="${ mode eq 'modify' && updateAuth eq 'Y' }">
							<a href="javascript:goDeleteCS('${ row.cno }')" class="btnTypeC btnSizeA"><span>삭제</span></a>
						</c:if>
					</td>
				  </tr>
				  </c:forEach>
			</table>
		</div>


		<div class="btnArea">
			<span class="fl">
				<a href="${ retrivePath }" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
			<c:if test="${ mode eq 'modify' && updateAuth eq 'Y' }">
				<a href="javascript:goSubmit()" class="btnTypeC btnSizeA"><span>수정</span></a>
				<a href="#" onclick="showPopup('secede?memNo=${ member.memNo }', '500', '600'); return false" class="btnSizeA btnTypeD">탈퇴</a>
			</c:if>
		</div>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->


</body>
</html>
