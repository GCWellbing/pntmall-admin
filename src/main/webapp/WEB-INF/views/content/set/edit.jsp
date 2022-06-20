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
		$("#sdate").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		$("#edate").datepicker({
			changeMonth: true,
		    changeYear: true
		});

		v = new ef.utils.Validator($("#editForm"));
		v.add("title", {
			empty : "질문 입력하세요.",
			max : "200"
		});
		v.add("sdate", {
			empty : "게시기간을 입력하세요."
		});
		v.add("edate", {
			empty : "게시기간을 입력하세요."
		});
		v.add("summary", {
			empty : "노출문구를 입력하세요."
		});
		v.add("img", {
			empty : "이미지를 입력하세요."
		});


		$("#optionList").on("click", ".btnUp", function() {
			var pno = $(this).parents("tr").find("input[name=pno]").val();
			var optNm = $("#optionList input[name=optNm" + pno + "]").val();

			var currObj = $(this).parents("tr");
			var idx = $(currObj).index();

			if (idx > 0) {
				var upperObj = currObj.prev();
				upperObj.before($("<tr></tr>").html(currObj.html()));

				//jquery 버그? 변경된 값은 html()에서 제너레이트 되지 않는다. 변경된 값을 다시 재지정.
				$("#optionList input[name=pno]", upperObj.prev()).val($("#optionList input[name=pno]", currObj).val());

				currObj.remove();

				$("#optionList input[name=optNm" + pno + "]").val(optNm);
			}
		});

		$("#optionList").on("click", ".btnDown", function() {
			var pno = $(this).parents("tr").find("input[name=pno]").val();
			var optNm = $("#optionList input[name=optNm" + pno + "]").val();

			var currObj = $(this).parents("tr");
			var size = $("#optionList tr").length;
			var idx = $(currObj).index();

			if (idx + 1 < size) {
				var belowObj = currObj.next();
				belowObj.after($("<tr></tr>").html(currObj.html()));

				//jquery 버그? 변경된 값은 html()에서 제너레이트 되지 않는다. 변경된 값을 다시 재지정.
				$("#optionList input[name=pno]", belowObj.next()).val($("#optionList input[name=pno]", currObj).val());

				currObj.remove();

				$("#optionList input[name=optNm" + pno + "]").val(optNm);
			}
		});
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


	function uploadImage(field) {
		if($("input[name=" + field + "]").val() != '') {
	        disableScreen();

	        var action = "/upload/upload?field=" + field + "&path=set&fileType=image";
	        ef.multipart($("#editForm"), action, field, function(result) {

	        	if(result.succeed) {
	        		var s = field.replace(/File/gi, '');
	        		$("input[name=" + s + "]").val(result.param.uploadUrl);
	        		$("#" + s + "Tag").attr("src", result.param.uploadUrl);
	        		$("#" + s + "Tag").show();
	        	} else {
	        		alert(result.message);
	        	}

	        	$("input[name=" + field + "]").val("");
	        	enableScreen();
	        });
		}
	}


	function setProduct(options) {
		var html;
		for(var i = 0; i < options.length; i++) {
			if($("input[name=pno][value=" + options[i].pno + "]").length == 0) {
				html = $("#optionRow").html();
				html = html.replace(/##OPTION_PNO##/gi, options[i].pno);
				html = html.replace(/##OPTION_MATNR##/gi, options[i].matnr);
				html = html.replace(/##OPTION_NM##/gi, options[i].pname);
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
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="sno" value="${ set.sno }" />
		<h2>정보 등록</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>제목<sup>*</sup></th>
					<td>
						<input type="text" name="title" id="title" value="${ set.title }" style="width:200px" maxlength="4000">
					</td>
				</tr>
				<tr>
					<th>전시기간<sup>*</sup></th>
					<td>
						<input type="text" name="sdate" id="sdate" readonly value="${ set.sdate }"> ~
						<input type="text" name="edate" id="edate" readonly value="${ set.edate }">
					</td>
				</tr>
				<tr>
					<th>노출문구<sup>*</sup></th>
					<td>
						<textarea name="summary" id="summary" style="width:100%;height:100px">${ set.summary }</textarea>
					</td>
				</tr>
				<tr>
					<th>이미지<sup>*</sup></th>
					<td>
						<input type="file" name="imgFile" onchange="uploadImage('imgFile')">
						<p class="txt">* Image Size : 487*461(jpg) / Max 10Mbyte</p>
						<img src="${ set.img }" id="imgTag" style="display:${ !empty set.img ? 'block' : 'none' }">
						<input type="hidden" name="img" value="${ set.img }">
					</td>
				</tr>
			</table>
		</div>


		<h2>전시 제품
			<span class="fr">
				PNT몰에는 순서 기준 최대 2개 제품만 노출됩니다.
				<a href="#" onclick="showPopup('/popup/product', '500', '600'); return false" class="btnSizeA btnTypeD">추가</a>
			</span></h2>
		<div class="">
			<table class="list">
				<colgroup>
					<col width="">
					<col width="">
					<col width="">
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
						<th>순서</th>
						<th>관리</th>
					</tr>
				</thead>
				<tbody id="optionList">
					<c:forEach items="${ setProductList }" var="row">
						<tr>
							<td>
								<c:out value="${ row.pno }" />
								<input type='hidden' name='pno' value='${ row.pno }'>
							</td>
							<td class="al">
								<c:out value="${ row.matnr }" />
							</td>
							<td class="al">
								<c:out value="${ row.pname }" />
							</td>
							<td>
								<fmt:formatNumber value="${ row.salePrice }" pattern="#,###" />
							</td>
							<td>
								<input type='button' class='btnUp' value='위로'>
								<input type='button' class='btnDown' value='아래로'>
							</td>
							<td><input type='button' class='btnSizeC' onclick='removeOption(this); return false;' value='삭제'></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<h2>메뉴권한</h2>
		<div class="white_box">
			<table class="board">
				<tr>
					<th>공개여부</th>
					<td>
						<label><input type="radio" name="status" value="S" ${ set.status eq 'S' || empty set.status ? 'checked' : '' }><span>공개</span></label>
						<label><input type="radio" name="status" value="H" ${ set.status eq 'H' ? 'checked' : '' }><span>비공개</span></label>
					</td>
					<th>등록일/등록자</th>
					<td><fmt:formatDate value="${ set.cdate }" pattern="${ DateTimeFormat }" /> / ${set.cuser}
					</td>
					<th>수정일/수정자</th>
					<td><fmt:formatDate value="${set.udate}" pattern="${ DateTimeFormat }"/> / ${set.uuser}
					</td>
				</tr>
			</table>
		</div>


		<div class="btnArea">
			<span class="fl">
				<a href="${ retrivePath }" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
			<c:if test="${ mode eq 'create' }">
				<a href="javascript:goSubmit()" class="btnTypeC btnSizeA"><span>등록</span></a>
			</c:if>
			<c:if test="${ mode eq 'modify' && updateAuth eq 'Y' }">
				<a href="javascript:goSubmit()" class="btnTypeC btnSizeA"><span>수정</span></a>
			</c:if>
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
				##OPTION_MATNR##
			</td>
			<td class="al">
				##OPTION_NM##
			</td>
			<td>
				##OPTION_SALE_PRICE##
			</td>
			<td>
				<input type='button' class='btnUp' value='위로'>
				<input type='button' class='btnDown' value='아래로'>
			</td>
			<td><input type='button' class='btnSizeC' onclick='removeOption(this); return false;' value='삭제'></td>
		</tr>
	</tbody>
</table>


</body>
</html>
