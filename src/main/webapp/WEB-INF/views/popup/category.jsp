<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	$(function() {
		$("#regist").click(function() {
			setCate();
		});

		setSub();
	});

	function setSub() {
		var html = "";
		<c:forEach items="${ list }" var="row">
			<c:if test="${ row.pcateNo ne 0 }">
				html = "<tr><td>&gt; ${ row.name }</td><td><label><input type='checkbox' name='cateNo' value='${ row.cateNo }'><span></span></label>";
				html += "<input type='hidden' name='cateName' id='cateName${ row.cateNo }' value='${ row.pname } &gt; ${ row.name }'></td></tr>";
				$("#subCateRow${ row.pcateNo }").append(html);
			</c:if>
		</c:forEach>
	}

	function setCate() {
		if($("input[name=cateNo]:checked").length == 0) {
			alert("카테고리를 선택하세요.");
			return;
		}

		var categories = new Array();
		$("input[name=cateNo]:checked").each(function() {
			var cate = {
					cateNo : $(this).val(),
					name : $("#cateName" + $(this).val()).val()
			}

			categories.push(cate);
		});

		opener.setCate(categories);
		self.close();
	}
</script>
</head>
<body>
<div id="popWrapper">
	<h1>카테고리 추가</h1>
	<div id="popContainer">
		<div class="content scr"><!-- 창 높이보다 내용이 많아지면 자동으로 스크롤 생김 -->
			<div class="white_box">
				<table class="board">
					<colgroup>
						<col width="30">
						<col width="">
						<col width="30">
					</colgroup>
					<c:forEach items="${ list }" var="row">
						<c:if test="${ row.pcateNo eq 0 }">
							<tr>
								<td class="ac"><a href="#" onclick="showSubCate('subCate${ row.cateNo }', this); return false" class="btnSubCate">+</a></td>
								<td><a href="#" onclick="showSubCate('subCate${ row.cateNo }', this); return false">${ row.name }</a></td>
								<td><!-- <label><input type="checkbox"><span></span></label> --></td>
							</tr>
							<tr class="subCate" id="subCate${ row.cateNo }" style="display:none">
								<td></td>
								<td colspan="2" style="padding:0">
									<table class="innerBoard">
										<colgroup>
											<col width="">
											<col width="30">
										</colgroup>
										<tbody id="subCateRow${ row.cateNo }"></tbody>
									</table>
								</td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="btnArea">
			<input type="button" id="regist" class="btnSizeA btnTypeD" value="확인">
		</div>
	</div>
</div><!-- //wrapper -->
<script>
function showSubCate(target, self){
	if($(self).parents("tr").hasClass("open")){
		$("#"+target).hide();
		$(self).parents("tr").removeClass("open")
		$(self).parents("tr").find(".btnSubCate").text("+")
	} else {
		$("#"+target).show();
		$(self).parents("tr").addClass("open")
		$(self).parents("tr").find(".btnSubCate").text("-")
	}

}
</script>
</body>
</html>
