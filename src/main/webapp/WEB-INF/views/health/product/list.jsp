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

		$("#submitBtn").click(function() {
			if(v.validate()) {
				let pnoLength = $("input[name='pno']").length;
				if (pnoLength == 0) {
					alert("제외제품을 선택해주세요.");
					return;
				}

				disableScreen();
				ef.proc($("#editForm"), function(result) {
					alert(result.message);
					if(result.succeed) location.href = "list";
					enableScreen();
				});
			}
		});
	});

	var gubun = "";
	function addProduct(gb){
		gubun = gb;
		showPopup('/popup/product', '450', '600');
	}

	function setProduct(options) {
		var html;
		for(var i = 0; i < options.length; i++) {
			var isCheck = true;
			$("#product"+gubun+ " input[name=pno]").each(function(){
				if($(this).val() == options[i].pno){
					isCheck = false;
				}
			});

			if(isCheck){
				html = "<p style='margin-bottom:5px'><a href='#' onclick='removeProduct(this); return false;' class='btnSizeC'>삭제</a>";
				html += "&nbsp;&nbsp;" + options[i].pname;
				html += "<input type='hidden' name='pno' value='" + options[i].pno + "'></p>";
				html += "<input type='hidden' name='gubun' value='" + gubun + "'></p>";
				$("#product"+gubun).append(html);
			}
		}
	}

	function removeProduct(obj) {
		$(obj).parent().remove();
	}
</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="editForm" id="editForm" method="post" action="create">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>14세이하</th>
					<td>
						<div id="productD">
							<c:forEach items="${ list }" var="row">
								<c:if test="${row.gubun eq 'D'}">
									<p style="margin-bottom:5px">
										<a href="#" onclick="removeProduct(this); return false;" class="btnSizeC">삭제</a>
										&nbsp;&nbsp;${ row.pname }
										<input type="hidden" name="pno" value="${ row.pno }">
										<input type="hidden" name="gubun" value="${ row.gubun }">
									</p>
								</c:if>
							</c:forEach>
						</div>
						<a href="#" onclick="addProduct('D'); return false" class="btnSizeA btnTypeD">추가</a>
					</td>
				</tr>
				<tr>
					<th>15세이상</th>
					<td>
						<div id="productU">
							<c:forEach items="${ list }" var="row">
								<c:if test="${row.gubun eq 'U'}">
									<p style="margin-bottom:5px">
										<a href="#" onclick="removeProduct(this); return false;" class="btnSizeC">삭제</a>
										&nbsp;&nbsp;${ row.pname }
										<input type="hidden" name="pno" value="${ row.pno }">
										<input type="hidden" name="gubun" value="${ row.gubun }">
									</p>
								</c:if>
							</c:forEach>
						</div>
						<a href="#" onclick="addProduct('U'); return false" class="btnSizeA btnTypeD">추가</a>
					</td>
				</tr>
				<tr>
					<th>임산부/수유부</th>
					<td>
						<div id="productP">
							<c:forEach items="${ list }" var="row">
								<c:if test="${row.gubun eq 'P'}">
									<p style="margin-bottom:5px">
										<a href="#" onclick="removeProduct(this); return false;" class="btnSizeC">삭제</a>
										&nbsp;&nbsp;${ row.pname }
										<input type="hidden" name="pno" value="${ row.pno }">
										<input type="hidden" name="gubun" value="${ row.gubun }">
									</p>
								</c:if>
							</c:forEach>
						</div>
						<a href="#" onclick="addProduct('P'); return false" class="btnSizeA btnTypeD">추가</a>
					</td>
				</tr>
			</table>
		</div>
		</form>
		<div class="btnArea">
			<input type="button" class="btnTypeD btnSizeA" id="submitBtn" value="등록">
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
