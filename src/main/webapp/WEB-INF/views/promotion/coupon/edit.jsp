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
		v.add("title", {
			empty : "제목을 입력하세요.",
			max : "50"
		});
		v.add("sdate", {
			empty : "지급기간을 입력하세요."
		});
		v.add("edate", {
			empty : "지급기간을 입력하세요."
		});
		v.add("expire", {
			empty : "사용 가능기간을 입력하세요."
		});
		v.add("discount", {
			empty : "할인액(할인율)을 입력하세요",
			format : "numeric"
		});
		v.add("minPrice", {
			empty : "적용조건을 입력하세요",
			format : "numeric"
		});
		v.add("maxDiscount", {
			empty : "적용조건을 입력하세요",
			format : "numeric"
		});

		$("#sdate").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		$("#edate").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		$("#expire").datepicker({
			changeMonth: true,
		    changeYear: true
		});

		setApply("${ mode eq 'create' || coupon.target eq 1 ? '1' : '2' }");
		setUnit("${ mode eq 'create' || coupon.discountType eq 1 ? '1' : '2' }");
	});

	function goSubmit() {
		if(v.validate()) {
			disableScreen();
			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list";
				enableScreen();
			});
		}
	}

	function setProduct(arr) {
		var html;
		for(var i = 0; i < arr.length; i++) {
			if($("input[name=pno][value=" + arr[i].pno + "]").length == 0) {
				html = $("#productRow").html();
				html = html.replace(/##PNO##/gi, arr[i].pno);
				html = html.replace(/##MATKR##/gi, arr[i].matnr);
				html = html.replace(/##PNAME##/gi, arr[i].pname);
				html = html.replace(/##SALE_PRICE##/gi, arr[i].salePrice);
				$("#productList").append(html);
			}
		}
	}

	function removeProduct(obj) {
		$(obj).parent().parent().remove();
	}

	function setApply(v) {
		if(v == '1') {
			$("#applyGrade").show();
			$("#applyMem").hide();
		} else {
			$("#applyGrade").hide();
			$("#applyMem").show();
		}
	}

	function setUnit(v) {
		if(v == '1') {
			$(".discount1").html("액");
			$(".discount2").html("원");
		} else {
			$(".discount1").html("율");
			$(".discount2").html("%");
		}
	}

	function setMember(v) {
		$("textarea[name=memId]").val(v);
	}
</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="couponid" value="${ coupon.couponid }" />
		<h2>기본 정보</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>제목<sup>*</sup></th>
					<td>
						<input type="text" name="title" value="${ coupon.title }" style="width:100%" maxlength="50">
					</td>
				</tr>
				<tr>
					<th>유형</th>
					<td>
						<label><input type="radio" name="gubun" value="1" ${ mode eq 'create' || coupon.gubun eq 1 ? 'checked' : '' } onclick="$('#applyProduct').show()"><span>제품 쿠폰</span></label>
						<label><input type="radio" name="gubun" value="2" ${ coupon.gubun eq '2' ? 'checked' : '' } onclick="$('#applyProduct').hide()"><span>배송비 쿠폰</span></label>
					</td>
				</tr>
				<tr>
					<th>지급기간<sup>*</sup></th>
					<td>
						<div class="dateBox">
							<input type="text" name="sdate" id="sdate" value="${ coupon.sdate }" readonly>
						</div> ~
						<div class="dateBox">
							<input type="text" name="edate" id="edate" value="${ coupon.edate }" readonly>
						</div>
					</td>
				</tr>
				<tr>
					<th>사용가능 기간<sup>*</sup></th>
					<td>
						<div class="dateBox">
							<input type="text" name="expire" id="expire" value="${ coupon.expire }" readonly>
						</div>
					</td>
				</tr>
				<tr>
					<th>지급대상<sup>*</sup></th>
					<td>
						<label><input type="radio" name="target" value="1" ${ mode eq 'create' || coupon.target eq 1 ? 'checked' : '' } onclick="setApply('1')"><span>등급</span></label>
						<label><input type="radio" name="target" value="2" ${ coupon.target eq '2' ? 'checked' : '' } onclick="setApply('2')"><span>개별회원</span></label>
					</td>
				</tr>
				<tr id="applyGrade">
					<th>적용등급<sup>*</sup></th>
					<td>
						<c:forEach items="${ gradeList }" var="row">
							<label><input type="checkbox" name="gradeNo" value="${ row.gradeNo }" ${ !empty row.couponid ? "checked" : "" }><span>${ row.gradeName }</span></label>
						</c:forEach>
					</td>
				</tr>
				<tr id="applyMem">
					<th>적용회원<sup>*</sup></th>
					<td>
						<textarea name="memId" style="width:90%; height:100px"><c:forEach items='${ memList }' var='row'>${ row.memId }/</c:forEach></textarea>
						<br><a href="#" onclick="showPopup('/popup/applyMem', '500', '600'); return false" class="btnTypeC btnSizeA"><span>일괄등록</span></a>
						* 구분자는 "/"로 입력해 주세요.
					</td>
				</tr>
			</table>
		</div>

		<h2>쿠폰 적용 조건</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>할인구분<sup>*</sup></th>
					<td>
						<label><input type="radio" name="discountType" value="1" ${ mode eq 'create' || coupon.discountType eq 1 ? 'checked' : '' } onclick="setUnit('1')"><span>정량</span></label>
						<label><input type="radio" name="discountType" value="2" ${ coupon.discountType eq '2' ? 'checked' : '' } onclick="setUnit('2')"><span>정률</span></label>
					</td>
				</tr>
				<tr>
					<th>할인<span class="discount1">액</span><sup>*</sup></th>
					<td>
						<input type="text" name="discount" value="${ coupon.discount }" style="width:150px">
						<span class="discount2">원</span>
					</td>
				</tr>
				<tr>
					<th>적용조건<sup>*</sup></th>
					<td>
						<input type="text" name="minPrice" value="${ coupon.minPrice }" style="width:150px">원 이상 구매시 적용, 최대
						<input type="text" name="maxDiscount" value="${ coupon.maxDiscount }" style="width:150px">원 할인
					</td>
				</tr>
			</table>
		</div>

		<div id="applyProduct" style="display:${ mode eq 'create' || coupon.gubun eq 1 ? 'block' : 'none' }">
				<h2>적용 제품
				<span class="fr">
					<a href="#" onclick="showPopup('/popup/product', '500', '600'); return false" class="btnSizeA btnTypeD">추가</a>
				</span></h2>
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
							<th>판매가</th>
							<th>관리</th>
						</tr>
					</thead>
					<tbody id="productList">
						<c:forEach items="${ productList }" var="row">
							<tr>
								<td>
									<c:out value="${ row.pno }" />
									<input type='hidden' name='pno' value='${ row.pno }'>
								</td>
								<td>
									<c:out value="${ row.matnr }" />
								</td>
								<td class="al">
									<c:out value="${ row.pname }" />
								</td>
								<td>
									<fmt:formatNumber value="${ row.salePrice }" pattern="#,###" />
								</td>
								<td><input type='button' class='btnSizeC' onclick='removeProduct(this); return false;' value='삭제'></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<br><br>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
					<col width="10%">
					<col width="">
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>상태<sup>*</sup></th>
					<td>
						<label><input type="radio" name="status" value="S" ${ mode eq 'create' || coupon.status eq 'S' ? 'checked' : '' }><span>활성</span></label>
						<label><input type="radio" name="status" value="H" ${ coupon.status eq 'H' ? 'checked' : '' }><span>비활성</span></label>
					</td>
					<th>등록일/등록자</th>
					<td><fmt:formatDate value="${ coupon.cdate }" pattern="${ DateTimeFormat }" /> / ${ coupon.cuserId }</td>
					<th>수정일/수정자</th>
					<td><fmt:formatDate value="${ coupon.udate }" pattern="${ DateTimeFormat }" /> / ${ coupon.uuserId }</td>
				</tr>
			</table>
		</div>

		<div class="btnArea">
			<span class="fl">
				<a href="${ retrivePath }" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
			<c:if test="${ mode eq 'create' }">
				<a href="javascript:goSubmit()" class="btnTypeD btnSizeA"><span>등록</span></a>
			</c:if>
			<c:if test="${ mode eq 'modify' && updateAuth eq 'Y' }">
				<a href="javascript:showPopup('serial?couponid=${ coupon.couponid }', '1024', '800');" class="btnTypeA btnSizeA"><span>난수발행</span></a>
				<a href="javascript:goSubmit()" class="btnTypeD btnSizeA"><span>수정</span></a>
			</c:if>
		</div>
		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

<table style="display:none">
	<tbody id="productRow">
		<tr>
			<td>
				##PNO##
				<input type='hidden' name='pno' value='##PNO##'>
			</td>
			<td>
				##MATNR##
			</td>
			<td class="al">
				##PNAME##
			</td>
			<td>
				##SALE_PRICE##
			</td>
			<td><input type='button' class='btnSizeC' onclick='removeProduct(this); return false;' value='삭제'></td>
		</tr>
	</tbody>
</table>

</body>
</html>
