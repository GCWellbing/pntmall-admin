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

		v = new ef.utils.Validator($("#editForm"));
		v.add("gubun", {
			empty : "구분을 입력하세요."
		});
		v.add("title", {
			empty : "제목을 입력하세요.",
			max : "200"
		});
		v.add("subTitle", {
			empty : "소제목을 입력하세요."
		});
		v.add("thumb", {
			empty : "썸네일을 입력하세요."
		});
		v.add("content", {
			empty : "내용을 입력하세요."
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

		$("input[name='gubun']:radio").change(function () {
	        //라디오 버튼 값을 가져온다.
	        var gubun = this.value;
	        if(gubun == 1){
	        	$("#thumTitle").text("썸네일(이미지)");
				$('#trYoutube').attr('style', "display:none;");
	        }else{
	        	$("#thumTitle").text("썸네일(영상)");
				$('#trYoutube').attr('style', "display:'';");
	        }
	});

	});

	function goSubmit() {
		oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);

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

	        var action = "/upload/upload?field=" + field + "&path=magazine&fileType=image";
	        ef.multipart($("#editForm"), action, field, function(result) {

	        	if(result.succeed) {
	        		var s = field.replace(/File/gi, '');
	        		if(s == "attach"){
	        			var filename = $("input[name=" + field + "]")[0].files[0].name;
	        			$("input[name=attachOrgName]").val(filename);
	        			$("#attachTagName").text(filename);
	        		}
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

	function uploadFile(field) {
		if($("input[name=" + field + "]").val() != '') {
	        disableScreen();

	        var action = "/upload/upload?field=" + field + "&path=magazine&fileType=ALL";
	        ef.multipart($("#editForm"), action, field, function(result) {

	        	if(result.succeed) {
	        		var s = field.replace(/File/gi, '');
        			var filename = $("input[name=" + field + "]")[0].files[0].name;
        			$("input[name=" + s + "OrgName]").val(filename);
        			$("#" + s + "TagName").text(filename);
	        		$("input[name=" + s + "]").val(result.param.uploadUrl);
	        		$("#" + s + "Tag").attr("href", result.param.uploadUrl);
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
			<input type="hidden" name="mno" value="${ magazine.mno }" />
		<h2>정보 등록</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>구분<sup>*</sup></th>
					<td>
						<label><input type="radio" name="gubun" value="1" ${ magazine.gubun eq '1' || empty magazine.status ? 'checked' : '' }><span>이미지</span></label>
						<label><input type="radio" name="gubun" value="2" ${ magazine.gubun eq '2' ? 'checked' : '' }><span>영상</span></label>
					</td>
				</tr>
				<tr>
					<th>제목<sup>*</sup></th>
					<td>
						<input type="text" name="title" id="title" value="${ magazine.title }" style="width:200px" maxlength="4000">
					</td>
				</tr>
				<tr>
					<th>소제목<sup>*</sup></th>
					<td>
						<input type="text" name="subTitle" id="subTitle" value="${ magazine.subTitle }" style="width:200px" maxlength="4000">
					</td>
				</tr>
				<tr>
					<th>태그입력</th>
					<td>
						<input type="text" name="tag" id="tag" value="${ magazine.tag }" style="width:200px" maxlength="4000">
						*태그는 콤마(,)로 구별하여 입력해주세요
					</td>
				</tr>
				<tr>
					<th><p id="thumTitle">썸네일(${ magazine.gubun eq '1' || empty magazine.status ? '이미지' : '영상' })</p><sup>*</sup></th>
					<td>
						<input type="file" name="thumbFile" onchange="uploadImage('thumbFile')">
						<p class="txt">* Image Size : 1280*720(jpg) / Max 10Mbyte</p>
						<img src="${ magazine.thumb }" id="thumbTag" style="display:${ !empty magazine.thumb ? 'block' : 'none' }">
						<input type="hidden" name="thumb" value="${ magazine.thumb }">
					</td>
				</tr>
				<tr id="trYoutube" style="display:${ magazine.gubun eq '1' || empty magazine.status ? 'none' : '' };">
					<th>유튜브 코드<sup>*</sup></th>
					<td>
						<input type="text" name="youtube" id="youtube" value="${ magazine.youtube }" style="width:200px" maxlength="4000">
					</td>
				</tr>
				<tr>
					<th>내용<sup>*</sup></th>
					<td>
						<textarea name="content" id="content" style="width:100%;height:100px">${ magazine.content }</textarea>
					</td>
				</tr>
				<tr>
					<th>파일첨부</th>
					<td>
						<input type="file" name="attachFile" onchange="uploadFile('attachFile')">
						<p class="txt">*  최대 5Mbyte / 확장자 : jpg, jpeg, gif, png, bmp, pdf, ppt, pptx, xlsx, xls, doc, docx</p>
						<a href="${ magazine.attach }" target="_blank" id="attachTag" style="display:${ !empty magazine.attach ? 'block' : 'none' }">
							<p class="txt" id="attachTagName">${ magazine.attachOrgName }</p>
						</a>
						<input type="hidden" name="attachOrgName" value="${ magazine.attachOrgName }" readonly>
						<input type="hidden" name="attach" value="${ magazine.attach }" readonly>
					</td>
				</tr>
				<tr>
					<th>전시순위</th>
					<td>
						<input type="text" name="rank" id="rank" value="${ magazine.rank }" style="width:200px" maxlength="4000">
					</td>
				</tr>
			</table>
		</div>

		<h2>전시 제품
			<span class="fr">
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
					<c:forEach items="${ magazineProductList }" var="row">
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
					<th>공개여부<sup>*</sup></th>
					<td>
						<label><input type="radio" name="status" value="S" ${ magazine.status eq 'S' || empty magazine.status ? 'checked' : '' }><span>공개</span></label>
						<label><input type="radio" name="status" value="H" ${ magazine.status eq 'H' ? 'checked' : '' }><span>비공개</span></label>
					</td>
					<th>등록일/등록자</th>
					<td><fmt:formatDate value="${ magazine.cdate }" pattern="${ DateTimeFormat }" /> / ${magazine.cuser}
					</td>
					<th>수정일/수정자</th>
					<td><fmt:formatDate value="${magazine.udate}" pattern="${ DateTimeFormat }"/> / ${magazine.uuser}
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

<script type="text/javascript">
	var oEditors = [];

	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "content",
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
</script>

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
