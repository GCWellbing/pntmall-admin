<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
</head>
<body>
<div id="popWrapper">
	<h1>수수료율 일괄적용</h1>
	<div id="popContainer">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="160">
					<col width="">
				</colgroup>
				<tr>
					<th>이용수수료율</th>
					<td>
						<input type="text" name="fee">%
					</td>
				</tr>
				<tr>
					<th>홍보수수료율</th>
					<td>
						<input type="text" name="promoFee">%
					</td>
				</tr>
				<tr>
					<th>픽업수수료율</th>
					<td>
						<input type="text" name="pickupFee">%
					</td>
				</tr>
				<tr>
					<th>닥터팩수수료율</th>
					<td>
						<input type="text" name="dpackFee">%
					</td>
			</table>
		</div>
		<div class="btnArea">
			<input type="button" class="btnSizeA btnTypeD" value="적용" onclick="apply()">
		</div>
	</div>
</div><!-- //wrapper -->
</body>
<script>
	function apply() {
		var fee = {
				fee : $("input[name=fee]").val(),
				promoFee : $("input[name=promoFee]").val(),
				pickupFee : $("input[name=pickupFee]").val(),
				dpackFee : $("input[name=dpackFee]").val(),
		}
		
		opener.applyFeeBatch(fee);
		self.close();
	}
</script>
</html>
