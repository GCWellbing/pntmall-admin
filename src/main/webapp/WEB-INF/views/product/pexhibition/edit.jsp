<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script type="text/javascript" src="/static/se2/js/HuskyEZCreator.js" charset="utf-8"></script>
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
			empty : "제목을 입력하세요."
		});
		v.add("sdate", {
			empty : "전시기간을 시작일을 입력하세요."
		});
		v.add("edate", {
			empty : "전시기간을 종료일을 입력하세요."
		});
		v.add("summary", {
			empty : "기획전내용을 입력하세요."
		});
		v.add("img", {
			empty : "썸네일을 입력하세요."
		});
		v.add("banner", {
			empty : "제품상세 배너를 입력하세요."
		});
		v.add("pcDesc", {
			empty : "상세내용(PC)을 입력하세요."
		});
		v.add("moDesc", {
			empty : "상세내용(Mobile)을 입력하세요."
		});

		$("#productList").on("click", ".btnUp", function() {
			var pno = $(this).parents("tr").find("input[name=pno]").val();

			var currObj = $(this).parents("tr");
			var idx = $(currObj).index();

			if (idx > 0) {
				var upperObj = currObj.prev();
				upperObj.before($("<tr></tr>").html(currObj.html()));

				//jquery 버그? 변경된 값은 html()에서 제너레이트 되지 않는다. 변경된 값을 다시 재지정.
				$("#productList input[name=pno]", upperObj.prev()).val($("#productList input[name=pno]", currObj).val());

				currObj.remove();
			}
		});

		$("#productList").on("click", ".btnDown", function() {
			var pno = $(this).parents("tr").find("input[name=pno]").val();

			var currObj = $(this).parents("tr");
			var size = $("#productList tr").length;
			var idx = $(currObj).index();

			if (idx + 1 < size) {
				var belowObj = currObj.next();
				belowObj.after($("<tr></tr>").html(currObj.html()));

				//jquery 버그? 변경된 값은 html()에서 제너레이트 되지 않는다. 변경된 값을 다시 재지정.
				$("#productList input[name=pno]", belowObj.next()).val($("#productList input[name=pno]", currObj).val());

				currObj.remove();
			}
		});
	});

	function goSubmit() {
		oEditors.getById["pcDesc"].exec("UPDATE_CONTENTS_FIELD", []);
		oEditors.getById["moDesc"].exec("UPDATE_CONTENTS_FIELD", []);

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

	        var action = "/upload/upload?field=" + field + "&path=pexhibition&fileType=image";
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
			<input type="hidden" name="seno" value="${ pexhibition.seno }" />
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
						<input type="text" name="title" id="title" value="${ pexhibition.title }" style="width:200px" maxlength="4000">
					</td>
					<th>조회수</th>
					<td><c:out value="${ pexhibition.viewCnt }" /></td>
				</tr>
				<tr>
					<th>전시기간<sup>*</sup></th>
					<td colspan=3>
						<input type="text" name="sdate" id="sdate" readonly value="${ pexhibition.sdate }"> ~
						<input type="text" name="edate" id="edate" readonly value="${ pexhibition.edate }">
					</td>
				</tr>
				<tr>
					<th>기획전내용<sup>*</sup></th>
					<td colspan=3>
						<textarea name="summary" id="summary" style="width:100%;height:100px">${ pexhibition.summary }</textarea>
					</td>
				</tr>
				<tr>
					<th>썸네일<sup>*</sup></th>
					<td colspan=3>
						<input type="file" name="imgFile" onchange="uploadImage('imgFile')">
						<p class="txt">* Image Size : 624*366(jpg) / Max 10Mbyte</p>
						<img src="${ pexhibition.img }" id="imgTag" style="display:${ !empty pexhibition.img ? 'block' : 'none' }">
						<input type="hidden" name="img" id="img" value="${ pexhibition.img }">
					</td>
				</tr>
				<tr>
					<th>제품상세 배너<sup>*</sup></th>
					<td colspan=3>
						<input type="file" name="bannerFile" onchange="uploadImage('bannerFile')">
						<p class="txt">* Image Size : 501*161(jpg) / Max 10Mbyte</p>
						<img src="${ pexhibition.banner }" id="bannerTag" style="display:${ !empty pexhibition.banner ? 'block' : 'none' }">
						<input type="hidden" name="banner" id="banner" value="${ pexhibition.banner }">
					</td>
				</tr>
				<tr>
					<th>상세내용(PC)<sup>*</sup></th>
					<td colspan=3>
						<textarea name="pcDesc" id="pcDesc" style="width:100%;height:100px">${ pexhibition.pcDesc }</textarea>
					</td>
				</tr>
				<tr>
					<th>상세내용(Mobile)<sup>*</sup></th>
					<td colspan=3>
						<textarea name="moDesc" id="moDesc" style="width:100%;height:100px">${ pexhibition.moDesc }</textarea>
					</td>
				</tr>
				<tr>
					<th>제품 영역 노출여부<sup>*</sup></th>
					<td colspan=3>
						<label><input type="radio" name="productYn" value="Y" ${ pexhibition.productYn eq 'Y' || empty pexhibition.productYn ? 'checked' : '' }><span>노출</span></label>
						<label><input type="radio" name="productYn" value="N" ${ pexhibition.productYn eq 'N' ? 'checked' : '' }><span>비노출</span></label>
					</td>
				</tr>
				<tr>
					<th>GNB 노출여부<sup>*</sup></th>
					<td colspan=3>
						<label><input type="radio" name="gnbYn" value="Y" ${ pexhibition.gnbYn eq 'Y' || empty pexhibition.gnbYn ? 'checked' : '' }><span>노출</span></label>
						<label><input type="radio" name="gnbYn" value="N" ${ pexhibition.gnbYn eq 'N' ? 'checked' : '' }><span>비노출</span></label>
					</td>
				</tr>
			</table>
		</div>


		<h2>전시 제품
			<span class="fr">
				<a href="#" onclick="showPopup('/popup/product', '500', '600'); return false" class="btnSizeA btnTypeD">추가</a>
			</span></h2>
		<div class="">
			<table class="list" id="productList">
				<colgroup>
					<col width="">
					<col width="">
					<col width="">
					<col width="100px">
					<col width="100px">
				</colgroup>
				<thead>
					<tr>
						<th>제품코드</th>
						<th>제품명</th>
						<th>판매가</th>
						<th>순서</th>
						<th>관리</th>
					</tr>
				</thead>
				<tbody id="optionList">
					<c:forEach items="${ seProductList }" var="row">
						<tr>
							<td>
								<c:out value="${ row.pno }" />
								<input type='hidden' name='pno' value='${ row.pno }'>
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
					<th>공개여부<sup>*</sup></th>
					<td>
						<label><input type="radio" name="status" value="S" ${ pexhibition.status eq 'S' || empty pexhibition.status ? 'checked' : '' }><span>공개</span></label>
						<label><input type="radio" name="status" value="H" ${ pexhibition.status eq 'H' ? 'checked' : '' }><span>비공개</span></label>
					</td>
					<th>등록일/등록자</th>
					<td><fmt:formatDate value="${ pexhibition.cdate }" pattern="${ DateTimeFormat }" /> / ${pexhibition.cuser}
					</td>
					<th>수정일/수정자</th>
					<td><fmt:formatDate value="${pexhibition.udate}" pattern="${ DateTimeFormat }"/> / ${pexhibition.uuser}
					</td>
				</tr>
			</table>
		</div>


		<div class="btnArea">
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

<script type="text/javascript">
	var oEditors = [];

	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "pcDesc",
		sSkinURI: "/static/se2/SmartEditor2Skin.html",
		htParams : {
	          // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
	          bUseToolbar : true,
	          // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
	          bUseVerticalResizer : true,
	          // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
	          bUseModeChanger : true,
	          bSkipXssFilter : true,
	          fOnBeforeUnload : function(){}
	      },
		fCreator: "createSEditorInIFrame"
	});

	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "moDesc",
		sSkinURI: "/static/se2/SmartEditor2Skin.html",
		htParams : {
	          // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
	          bUseToolbar : true,
	          // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
	          bUseVerticalResizer : true,
	          // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
	          bUseModeChanger : true,
	          bSkipXssFilter : true,
	          fOnBeforeUnload : function(){}
	      },
		fCreator: "createSEditorInIFrame2"
	});
</script>

</body>
</html>
