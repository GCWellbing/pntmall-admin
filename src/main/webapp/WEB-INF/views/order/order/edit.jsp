<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="cjurl" value="https://www.cjlogistics.com/ko/tool/parcel/tracking?gnbInvcNo=" />
<c:set var="lturl" value="https://www.lotteglogis.com/home/reservation/tracking/linkView?InvNo=" />
<c:set var="lourl" value="https://www.ilogen.com/web/personal/trace/" />
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
					<td><c:out value="${ order.orderid }" /></td>
					<th>주문일</th>
					<td><fmt:formatDate value="${ order.odate }" pattern="${ DateTimeFormat }" /></td>
				</tr>
				<tr>
					<th>주문유형</th>
					<td><c:out value="${ order.gubunName }" /></td>
					<th>배송유형</th>
					<td><c:out value="${ order.orderGubunName }" /></td>
				</tr>
				<tr>
					<th>아이디</th>
					<td><c:out value="${ order.memId }" /></td>
					<th>주문자명</th>
					<td><a href="/member/general/edit?memNo=${ order.memNo }"><c:out value="${ order.oname }" /></a></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td><c:out value="${ order.oemail }" /></td>
					<th>휴대전화</th>
					<td><c:out value="${ order.omtel1 }${ order.omtel2 }" /></td>
				</tr>
				<tr>
					<th>등급</th>
					<td><c:out value="${ order.gradeName }" /></td>
					<th>병의원코드</th>
					<td><c:out value="${ order.clinicId }" /></td>
				</tr>
				<tr>
					<th>디바이스</th>
					<td><c:out value="${ order.device }" /></td>
					<th>첫주문여부</th>
					<td><c:out value="${ order.firstOrderYn }" /></td>
				</tr>
				<tr>
					<th>주문상태</th>
					<td>
						<c:out value="${ order.statusName }" />
						<a href="#" onclick="showPopup('/popup/statusLog?orderid=${ param.orderid }', '500', '600'); return false" class="btnSizeA btnTypeB">주문상태 로그</a>
					</td>
					<th>상담자 ID</th>
					<td><c:out value='${ orderMemo.adminId }' /></td>
				</tr>
				<tr>
					<th>메모</th>
					<td colspan="3">
						<form name="memoForm" id="memoForm" method="POST" action="addMemo">
							<input type="hidden" name="orderid" value="${ order.orderid }">
							<textarea name="memo" style="width:90%;height:100px"><c:out value="${ orderMemo.memo }" /></textarea>
							<a href="javascript:goMemo()" class="btnSizeA btnTypeC"><span>등록</span></a>
						</form>
					</td>
				</tr>
			</table>
		</div>

		<form name="cancelForm" id="cancelForm" method="POST" action="cancel">
			<input type="hidden" name="orderid" value="${ param.orderid }">
		</form>
		<form name="returnForm" id="returnForm" method="POST" action="createReturn">
			<input type="hidden" name="orderid" value="${ param.orderid }">
		<span class="fr">
			<c:choose>
				<c:when test='${ fn:indexOf("110,120", order.status) >= 0 }'>
					<a href="#" onclick="goCancel(); return false;" class="btnSizeA btnTypeD">전체주문 취소</a>
				</c:when>
				<c:when test='${ fn:indexOf("150,160", order.status) >= 0 }'>
					<a href="#" onclick="goExchange(); return false;" class="btnSizeA btnTypeD">교환신청</a>
					<a href="#" onclick="goReturn(); return false;" class="btnSizeA btnTypeD">반품신청</a>
				</c:when>
				<c:when test='${ fn:indexOf("410", order.status) >= 0 }'>
					<a href="#" onclick="goStatus('420'); return false;" class="btnSizeA btnTypeD">픽업 가능</a>
				</c:when>
				<c:when test='${ fn:indexOf("420", order.status) >= 0 }'>
					<a href="#" onclick="goStatus('430'); return false;" class="btnSizeA btnTypeD">픽업 완료</a>
					<a href="#" onclick="goStatus('440'); return false;" class="btnSizeA btnTypeD">픽업 미완료</a>
				</c:when>
			</c:choose>
			<c:if test='${ order.status ne "191" }'>
				<a href="#" onclick="goStatus('191'); return false;" class="btnSizeA btnTypeD">수기주문취소</a>
			</c:if>
		</span>
		<c:set var='idx' value='0' />
		<c:forEach items='${ shipList }' var='row'>
			<h2>제품정보(${ row.shipGubun eq 1 ? '일반배송' : '냉장배송' })</h2>
			<div class="">
				<table class="list">
					<colgroup>
						<col width="150px">
						<col width="100px">
						<col width="150px">
						<col width="">
						<col width="100px">
						<col width="100px">
						<col width="100px">
						<col width="100px">
						<col width="100px">
						<col width="100px">
					</colgroup>
					<thead>
						<tr>
							<th>주문번호</th>
							<th>제품코드</th>
							<th>SAP코드</th>
							<th>제품명</th>
							<th>수량</th>
							<th>판매가</th>
							<th>쿠폰</th>
							<th>매출가</th>
							<th>기반품/교환수량</th>
							<th>반품/교환수량</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items='${ row.items }' var='item'>
							<tr>
								<td><c:out value="${ item.orderid }" />_<c:out value="${ item.shipNo }" /></td>
								<td><c:out value="${ item.pno }" /></td>
								<td><c:out value="${ item.matnr }" /></td>
								<td class="al">
									<c:out value="${ item.pname }" />
									<c:if test='${ !empty item.gift }'>
										<c:forEach items="${fn:split(item.gift, '|') }" var="gift">
											<p>[증정품] ${ gift }</p>
										</c:forEach>
									</c:if>
								</td>
								<td><c:out value="${ item.qty }" /></td>
								<td><fmt:formatNumber value="${ item.salePrice }" pattern="#,###" /></td>
								<td><fmt:formatNumber value="${ item.discount }" pattern="#,###" /></td>
								<td><fmt:formatNumber value="${ item.applyPrice }" pattern="#,###" /></td>
								<td><c:out value="${ item.returnQty + item.exchangeQty }" /></td>
								<td>
									<c:choose>
										<c:when test='${ fn:indexOf("150,160,190", order.status) >= 0 }'>
											<input type="hidden" name="idx" value="${ idx }">
											<input type="hidden" name="shipNo${ idx }" value="${ row.shipNo }">
											<input type="hidden" name="itemNo${ idx }" value="${ item.itemNo }">
											<input type="hidden" name="pno${ idx }" value="${ item.pno }">
											<select name="returnQty${ idx }" class="returnQty">
												<c:forEach var='i' begin='0' end='${ item.qty - item.returnQty - item.exchangeQty }'>
													<option value="${ i }">${ i }</option>
												</c:forEach>
											</select>
										</c:when>
										<c:otherwise>
											-
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<c:set var='idx' value='${ idx + 1 }' />
						</c:forEach>
						<tr>
							<th colspan="3">배송정보</th>
							<td colspan="7" class="al">
								<c:if test='${ !empty row.invoice }'>
									<c:set var="trackurl" value="${ row.shipper eq '018001' ? lturl : (row.shipper eq '018002' ? cjurl : lourl) }${ row.invoice }" />
									<c:out value="${ row.shipperName }" />
									<a href="${ trackurl }" target="_blank"><c:out value="${ row.invoice }" /></a>
								</c:if>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</c:forEach>
		</form>

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
					<th>제품총가격</th>
					<td><fmt:formatNumber value="${ order.amt }" pattern="#,###" />원</td>
					<th>등급할인</th>
					<td>- <fmt:formatNumber value="${ order.gradeDiscount }" pattern="#,###" />원</td>
				</tr>
				<tr>
					<th>배송비</th>
					<td><fmt:formatNumber value="${ order.shipAmt }" pattern="#,###" />원</td>
					<th>배송비 할인</th>
					<td>- <fmt:formatNumber value="${ order.shipDiscount }" pattern="#,###" />원</td>
				</tr>
				<tr>
					<th>쿠폰할인</th>
					<td>- <fmt:formatNumber value="${ order.couponDiscount }" pattern="#,###" />원</td>
					<th>포인트 사용</th>
					<td>- <fmt:formatNumber value="${ order.point }" pattern="#,###" />원</td>
				</tr>
				<tr>
					<th>총결제금액</th>
					<td><fmt:formatNumber value="${ order.payAmt }" pattern="#,###" />원</td>
					<th>결제수단</th>
					<td>
						<c:out value="${ order.payTypeName }" />
						<c:if test='${ order.payType eq "013003" }'>
							<br><c:out value="${ paymentLog.bank }" /> <c:out value="${ paymentLog.accountNumber }" />
						</c:if>
					</td>
				</tr>
			</table>
		</div>

		<span class="fr">
			<c:choose>
				<c:when test='${ order.orderGubun ne 3 and fn:indexOf("110,120", order.status) >= 0 }'>
					<a href="#" onclick="goAddr(); return false" class="btnSizeA btnTypeD">배송지 정보 수정</a>
				</c:when>
			</c:choose>
		</span>
		<h2>배송지정보</h2>
		<div class="white_box">
			<c:choose>
				<c:when test='${ order.orderGubun eq 3 }'>
					<table class="board">
						<colgroup>
							<col width="150px">
							<col width="">
							<col width="150px">
							<col width="">
						</colgroup>
						<tr>
							<th>배송유형</th>
							<td>병의원 픽업</td>
							<th>병의원정보</th>
							<td>${ order.pickupClinicName }</td>
						</tr>
						<tr>
							<th>방문일자</th>
							<td><c:out value="${ order.pickupDate }" /></td>
							<th>방문시간</th>
							<td><c:out value="${ order.pickupTime }" /></td>
						</tr>
					</table>
				</c:when>
				<c:otherwise>
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
				</c:otherwise>
			</c:choose>
		</div>

		<c:if test='${ fn:length(returnItemList) > 0 }'>
			<h2>교환/반품 목록</h2>
			<div class="">
				<table class="list">
					<colgroup>
						<col width="150px">
						<col width="100px">
						<col width="100px">
						<col width="100px">
						<col width="">
						<col width="100px">
						<col width="100px">
						<col width="100px">
					</colgroup>
					<thead>
						<tr>
							<th>주문번호</th>
							<th>일시</th>
							<th>제품코드</th>
							<th>SAP코드</th>
							<th>제품명</th>
							<th>수량</th>
							<th>매출가</th>
							<th>상태</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items='${ returnItemList }' var='row'>
							<tr>
								<td><a href="redit?orderid=<c:out value="${ row.orderid }" />"><c:out value="${ row.orderid }" />_<c:out value="${ row.shipNo }" /></a></td>
								<td><fmt:formatDate value="${ row.cdate }" pattern="${ DateTimeFormat }" /></td>
								<td><c:out value="${ row.pno }" /></td>
								<td><c:out value="${ row.matnr }" /></td>
								<td><c:out value="${ row.pname }" /></td>
								<td><c:out value="${ row.qty }" /></td>
								<td><fmt:formatNumber value="${ row.applyPrice }" pattern="#,###" /></td>
								<td><c:out value="${ row.statusName }" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>

		<div class="btnArea">
			<span class="fl">
				<a href="${ retrivePath }" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
		</div>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

<form name="statusForm" id="statusForm" action="status" method="post">
	<input type="hidden" name="orderid" value="${ order.orderid }">
	<input type="hidden" name="status">
</form>

<script type="text/javascript">
	$(function() {
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

	function goReturn() {
		var b = false;
		$(".returnQty").each(function() {
			if($(this).val() > 0) b = true;
		});

		if(!b) {
			alert("반품 수량을 선택하세요.");
		} else {
			if(confirm("반품신청을 하시겠습니까?")) {
				disableScreen();
				$("#returnForm").attr("action", "createReturn");
		        ef.proc($("#returnForm"), function(result) {
					alert(result.message);
					document.location.reload();
				});
			}
		}
	}

	function goExchange() {
		var b = false;
		$(".returnQty").each(function() {
			if($(this).val() > 0) b = true;
		});

		if(!b) {
			alert("교환 수량을 선택하세요.");
		} else {
			if(confirm("교환신청을 하시겠습니까?")) {
				disableScreen();
				$("#returnForm").attr("action", "createExchange");
		        ef.proc($("#returnForm"), function(result) {
					alert(result.message);
					document.location.reload();
				});
			}
		}
	}

	function goCancel() {
		<c:choose>
			<c:when test='${ order.status eq "120" && order.payType eq "013003"}'>
				showPopup('/popup/refundAccount?orderid=${ param.orderid }', '500', '600');
			</c:when>
			<c:otherwise>
				if(confirm("취소처리를 하시겠습니까?")) {
					disableScreen();
			        ef.proc($("#cancelForm"), function(result) {
						alert(result.message);
						document.location.reload();
					});
				}
			</c:otherwise>
		</c:choose>
	}

	function goStatus(status) {
		var msg = "주문상태를 변경하시겠습니까?";
		if(status == "191") msg = "수기주문취소는 PG취소, OMS취소를 수동으로 해야합니다.\n수기주문취소하시겠습니까?";

		if(confirm(msg)) {
			$("#statusForm input[name=status]").val(status);

			disableScreen();
			ef.proc($("#statusForm"), function(result) {
				alert(result.message);
				document.location.reload();;
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
