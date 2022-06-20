<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		<h2>주문자 정보</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="150px">
					<col width="">
					<col width="150px">
					<col width="">
				</colgroup>
				<tr>
					<th>주문번호</th>
					<td><c:out value="${ routineOrder.orderid }" /></td>
					<th>주문일</th>
					<td><fmt:formatDate value="${ routineOrder.cdate }" pattern="${ DateTimeFormat }" /></td>
				</tr>
				<tr>
					<th>아이디</th>
					<td><c:out value="${ routineOrder.memId }" /></td>
					<th>주문자명</th>
					<td><c:out value="${ routineOrder.oname }" /></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td><c:out value="${ routineOrder.oemail }" /></td>
					<th>휴대전화</th>
					<td><c:out value="${ routineOrder.omtel1 }${ routineOrder.omtel2 }" /></td>
				</tr>
				<tr>
					<th>디바이스</th>
					<td><c:out value="${ routineOrder.device }" /></td>
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th>메모</th>
					<td colspan="3">
						<form name="memoForm" id="memoForm" method="POST" action="/order/order/addMemo">
							<input type="hidden" name="orderid" value="${ routineOrder.orderid }">
							<textarea name="memo" style="width:90%;height:100px"><c:out value="${ routineOrder.memo }" /></textarea>
							<a href="javascript:goMemo()" class="btnSizeA btnTypeC"><span>등록</span></a>
						</form>
					</td>
				</tr>
			</table>
		</div>

		<h2>제품정보</h2>
		<div class="">
			<table class="list">
				<colgroup>
					<col width="100px">
					<col width="100px">
					<col width="">
					<col width="100px">
					<col width="100px">
				</colgroup>
				<thead>
					<tr>
						<th>제품코드</th>
						<th>SAP코드</th>
						<th>제품명</th>
						<th>수량</th>
						<th>판매가</th>
					</tr>
				</thead>
				<tbody>
					<c:set var="amt" value="0" />
					<c:forEach items='${ itemList }' var='row'>
						<tr>
							<td><c:out value="${ row.pno }" /></td>
							<td><c:out value="${ row.matnr }" /></td>
							<td><c:out value="${ row.pname }" /></td>
							<td><c:out value="${ row.qty }" /></td>
							<td><fmt:formatNumber value="${ row.memPrice * row.qty }" pattern="#,###" /></td>
						</tr>
						<c:set var="amt" value="${ amt + (row.memPrice * row.qty) }" />
					</c:forEach>
				</tbody>
			</table>
		</div>

		<h2>예약주문 발송일정</h2>
		<div class="">
			<table class="list">
				<colgroup>
					<col width="">
					<col width="">
					<col width="250px">
					<col width="">
					<col width="">
					<col width="">
				</colgroup>
				<thead>
					<tr>
						<th>주문번호</th>
						<th>주문상태</th>
						<th>주문예약일자</th>
						<th>주문금액</th>
						<th>누적횟수</th>
						<th>잔여횟수</th>
						<th>예약상태</th>
					</tr>
				</thead>
				<tbody>
					<c:set var='i' value='1' />
					<c:forEach items='${ dateList }' var='row'>
						<tr>
							<td>
								<c:choose>
									<c:when test='${ row.rorderid eq "-" }'>
										<c:out value="${ row.rorderid }" />
									</c:when>
									<c:otherwise>
										<a href="/order/order/edit?orderid=<c:out value="${ row.rorderid }" />"><c:out value="${ row.rorderid }" /></a>
									</c:otherwise>
								</c:choose>
							</td>
							<td><c:out value="${ row.statusName }" /></td>
							<td>
								<c:choose>
									<c:when test='${ row.status eq "110" and row.payDate > today }'>
										<form name="dateForm${ row.dno }" id="dateForm${ row.dno }" method="post" action="modifyDate">
											<input type="hidden" name="dno" value="${ row.dno }">
											<input type="text" name="payDate" value="${ row.payDate }" style="width:110px" class="payDate">
											<a href="#" onclick="goModify(${ row.dno }); return false;" class="btnTypeB btnSizeA"><span>수정</span></a>
										</form>
									</c:when>
									<c:otherwise>
										<c:out value="${ row.payDate }" />
									</c:otherwise>
								</c:choose>
							</td>
							<td><fmt:formatNumber value="${ amt }" pattern="#,###" /></td>
							<td><c:out value="${ i }" /></td>
							<td><c:out value="${ fn:length(dateList) - i }" /></td>
							<td>
								<c:choose>
									<c:when test='${ row.status eq "110" and row.payDate > today }'>
										<form name="cancelForm${ row.dno }" id="cancelForm${ row.dno }" method="post" action="cancel">
											<input type="hidden" name="dno" value="${ row.dno }">
											<a href="#" onclick="goCancel(${ row.dno }); return false;" class="btnTypeB btnSizeA"><span>취소</span></a>
										</form>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test='${ row.status eq "110" }'>
												결제대기
											</c:when>
											<c:when test='${ row.status eq "120" }'>
												결제완료
											</c:when>
											<c:when test='${ row.status eq "190" }'>
												예약취소
											</c:when>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<c:set var='i' value='${ i + 1 }' />
					</c:forEach>
				</tbody>
			</table>
		</div>

		<h2>결제정보</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="150px">
					<col width="">
					<col width="150px">
					<col width="">
				</colgroup>
				<tr>
					<th>주문주기</th>
					<td><c:out value="${ routineOrder.period }" />달 간격</td>
					<th>총 이용회수</th>
					<td><c:out value="${ routineOrder.cnt }" />회</td>
				</tr>
				<tr>
					<th>1회 주문금액</th>
					<td><fmt:formatNumber value="${ amt }" pattern="#,###" />원</td>
					<th>총 주문금액</th>
					<td><fmt:formatNumber value="${ amt * routineOrder.cnt }" pattern="#,###" />원</td>
				</tr>
			</table>
		</div>

		<span class="fr">
			<c:choose>
				<c:when test='${ fn:indexOf("110,120", order.status) >= 0 }'>
					<a href="#" onclick="goAddr(); return false" class="btnSizeA btnTypeD">배송지 정보 수정</a>
				</c:when>
			</c:choose>
		</span>
		<h2>배송지정보</h2>
		<div class="white_box">
			<form name="addrForm" id="addrForm" method="POST" action="addAddr">
				<input type="hidden" name="orderid" value="${ param.orderid }">
			<table class="board">
				<colgroup>
					<col width="150px">
					<col width="">
				</colgroup>
				<tr>
					<th>수취인<sup>*</sup></th>
					<td><input type="text" name="sname" value="${ orderAddr.sname }"></td>
				</tr>
				<tr>
					<th>휴대폰번호<sup>*</sup></th>
					<td>
						<select name="smtel1" style="width:100px">
							<c:forEach items='${ mtel1List }' var='row'>
								<option value="${ row.name }" ${ row.name eq orderAddr.smtel1 ? 'selected' : '' }><c:out value='${ row.name }' /></option>
							</c:forEach>
						</select>
						<input type="number" name="smtel2" value="${ orderAddr.smtel2 }">
					</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td>
						<select name="stel1" style="width:100px">
							<c:forEach items='${ tel1List }' var='row'>
								<option value="${ row.name }" ${ row.name eq orderAddr.stel1 ? 'selected' : '' }><c:out value='${ row.name }' /></option>
							</c:forEach>
						</select>
						<input type="number" name="stel2" value="${ orderAddr.stel2 }">
					</td>
				</tr>
				<tr>
					<th>우편번호<sup>*</sup></th>
					<td>
						<input type="text" name="szip" value="${ orderAddr.szip}" readonly>
						<a href="#" onclick="setAddr(); return false" class="btnSizeA btnTypeB">우편번호 검색</a>
					</td>
				</tr>
				<tr>
					<th>주소<sup>*</sup></th>
					<td>
						<input type="text" name="saddr1" value="${ orderAddr.saddr1 }" style="width:90%" readonly>
						<input type="text" name="saddr2" value="${ orderAddr.saddr2 }" style="width:90%">
					</td>
				</tr>
				<tr>
					<th>요청사항</th>
					<td>
						<select name="msg2" style="width:90%" onchange="setMsg(this.value)">
							<option value="">직접입력</option>
							<c:forEach items='${ msgList }' var='row'>
								<option value="${ row.name }" ${ row.name eq orderAddr.msg ? 'selected' : '' }><c:out value='${ row.name }' /></option>
							</c:forEach>
						</select>
						<input type="text" name="msg" value="${ orderAddr.msg }" style="width:90%">
					</td>
				</tr>
			</table>
			</form>
		</div>

		<div class="btnArea">
			<span class="fl">
				<a href="${ retrivePath }" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
		</div>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

<script type="text/javascript">
	$(function() {
		$(".payDate").datepicker({
			dateFormat: "yy.mm.dd",
			minDate : "1d"
		});

		$("#addrForm").validate({
			rules : {
				sname : {
					required : true,
					maxlength : 50
				},
				szip : {
					required : true
				},
				saddr1 : {
					required : true,
					maxlength : 50
				},
				saddr2 : {
					required : true,
					maxlength : 50
				},
	            smtel2: {
	            	required: true,
	                maxlength: 8
	            },
	            stel2: {
	                maxlength: 8
	            }
			},
			messages : {
				sname : {
					required : "이름을 입력하세요.",
					maxlength : $.validator.format("이름을 {0}자 이하로 입력하세요")
				},
	            szip : {
	            	required: "우편번호를 입력하세요"
	            },
	            saddr1 : {
	            	required: "주소를 입력하세요"
	            },
	            saddr2 : {
	            	required: "상세주소를 입력하세요",
					maxlength : $.validator.format("상세주소를 {0}자 이하로 입력하세요")
	            },
				smtel2 : {
					required : "휴대폰번호를 입력하세요.",
					maxlength : $.validator.format("휴대폰번호를 {0}자 이하로 입력하세요")
				},
				stel2 : {
					maxlength : $.validator.format("전화번호를 {0}자 이하로 입력하세요")
				}
			},

			onkeyup : false,
            onclick : false,
            onfocusout : false,
            showErrors : function(errorMap, errorList) {
                if(errorList.length) {
                    alert(errorList[0].message);
                }
            }
		});
	});

	function goMemo() {
		disableScreen();
		ef.proc($("#memoForm"), function(result) {
			alert(result.message);
			enableScreen();
		});
	}

	function setMsg(v) {
		$("input[name=msg]").val(v);
	}

	function goAddr() {
		if($("#addrForm").valid()) {
			disableScreen();
	        ef.proc($("#addrForm"), function(result) {
				alert(result.message);
				enableScreen();
			});
		}
	}

	function goCancel(dno) {
		if(confirm("취소처리를 하시겠습니까?")) {
			disableScreen();
	        ef.proc($("#cancelForm" + dno), function(result) {
				alert(result.message);
				document.location.reload();
			});
		}
	}

	function goModify(dno) {
		if(confirm("수정하시겠습니까?")) {
			disableScreen();
	        ef.proc($("#dateForm" + dno), function(result) {
				alert(result.message);
				document.location.reload();
			});
		}
	}

</script>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function setAddr() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                $("input[name=szip]").val(data.zonecode);
                $("input[name=saddr1]").val(roadAddr);
            }
        }).open();
    }
</script>

</body>
</html>
