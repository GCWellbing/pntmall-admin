<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	$(function() {
		$("#cancelForm").validate({
			rules : {
				refundAccount : {
					required : true,
					maxlength : 50
				},
				refundDepositor : {
					required : true,
					maxlength : 25
				}
			},
			messages : {
				refundAccount : {
					required : "계좌번호를 입력하세요.",
					maxlength : $.validator.format("이름을 {0}자 이하로 입력하세요")
				},
				refundDepositor : {
	            	required: "예금주를 입력하세요",
					maxlength : $.validator.format("이름을 {0}자 이하로 입력하세요")
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

	function goCancel() {
		disableScreen();
        ef.proc($("#cancelForm"), function(result) {
			alert(result.message);
			if(result.succeed) {
				opener.document.location.reload();
				self.close();
			} else {
				enableScreen();
			}
		});
	}


</script>
</head>
<body>
<div id="popWrapper">
	<h1>환불 계좌 정보</h1>
	<form name="cancelForm" id="cancelForm" method="post" action="/order/order/cancel">
		<input type="hidden" name="orderid" value="${ param.orderid }">
	<div id="popContainer">
		<table class="list">
			<colgroup>
				<col width="120px">
				<col width="">
			</colgroup>
			<tbody>
				<tr>
					<th>은행</th>
					<td>
						<select name="refundBank" style="width:100%">
							${ bankOptions }
						</select>
					</td>
				</tr>
				<tr>
					<th>계좌번호</th>
					<td><input type="number" name="refundAccount" style="width:100%"></td>
				</tr>
				<tr>
					<th>예금주</th>
					<td><input type="text" name="refundDepositor" style="width:100%"></td>
				</tr>
			</tbody>
		</table>
		<div class="btnArea">
			<a href="javascript:goCancel();" class="btnTypeA btnSizeA"><span>확인</span></a>
			<a href="javascript:self.close();" class="btnTypeC btnSizeA"><span>닫기</span></a>
		</div>
	</div>
	</form>
</div><!-- //wrapper -->
</body>
</html>
