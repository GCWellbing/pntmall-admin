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
		v.add("memId", {
			empty : "적용회원을 입력하세요."
		});
		v.add("point", {
			empty : "포인트를 입력하세요."
		});

		$("#edate").datepicker({
			dateFormat: "yy.mm.dd",
			minDate : "0d"
		});

	});

	function goSubmit() {
		if(v.validate()) {
			disableScreen();
			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.reload();
				enableScreen();
			});
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
		<form name="editForm" id="editForm" method="POST" action="create">
		<h2>기본 정보</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>유형<sup>*</sup></th>
					<td>
						<label><input type="radio" name="gubun" value="1" onclick="$('#expireDate').show()" checked><span>포인트 지급</span></label>
						<label><input type="radio" name="gubun" value="2" onclick="$('#expireDate').hide()"><span>포인트 차감</span></label>
					</td>
				</tr>
				<tr>
					<th>사유<sup>*</sup></th>
					<td>
						<select name="reason" style="width:200px">
							<c:forEach items='${ reasonList }' var='row'>
								<option value='${ row.code1 }${ row.code2 }'>${ row.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr id="applyMem">
					<th>적용회원<sup>*</sup></th>
					<td>
						<textarea name="memId" style="width:90%; height:100px"></textarea>
						<br><a href="#" onclick="showPopup('/popup/applyMem', '500', '600'); return false" class="btnTypeC btnSizeA"><span>일괄등록</span></a>
						* 구분자는 "/"로 입력해 주세요.
					</td>
				</tr>
				<tr id="expireDate">
					<th>만료일<sup>*</sup></th>
					<td>
						<div class="dateBox">
							<input type="text" name="edate" id="edate" value="" readonly>
						</div>
					</td>
				</tr>
				<tr>
					<th>포인트<sup>*</sup></th>
					<td>
						<input type="number" name="point" value="" style="width:150px">
					</td>
				</tr>
			</table>
		</div>

		<div class="btnArea">
			<a href="javascript:goSubmit()" class="btnTypeA btnSizeA"><span>등록</span></a>
			<a href="javascript:goSubmit()" class="btnTypeB btnSizeA"><span>초기화</span></a>
		</div>
		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
