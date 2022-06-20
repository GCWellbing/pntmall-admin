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

	    $("#chkComment").change(function(){
	        if($("#chkComment").is(":checked")){
	            $("#trComment").show();
	        }else{
	            $("#trComment").hide();
	        }
	    });

		getLength();

	});


	function goSubmit(){
		if (!$("#editForm").valid()){
			return false;
		}

		if($("textarea[name=content]").val().length > 1000) {
			alert("상세사유를 1000자 이하로 입력하세요.");
			return;
		}

		if($(".attach").length > 0){
			$("#type1").val("1"); //포토 리뷰
		}else{
			$("#type1").val("2"); //일반 리뷰
		}

		disableScreen();

		ef.proc($("#editForm"), function(result) {
			alert(result.message);
			enableScreen();
			if(result.succeed) location.href = "${ retrivePath }";
		});

	}


	function fileCheck(field) {
		if(document.getElementById(field).value!=""){
			var fileSize = document.getElementById(field).files[0].size;
			var maxSize = 5 * 1024 * 1024;//5MB

			if(fileSize > maxSize){
				alert("첨부파일 사이즈는 5MB 이내로 등록 가능합니다. ");
				return false;
			}
			return true;
		}
	}

	function uploadImage(field) {
		var fileName = $("input[name=" + field + "]").val()
		var orgFilename = fileName.split('/').pop().split('\\').pop();

		if(!fileCheck(field)){
			return false;
		}

		if($("input[name=" + field + "]").val() != '') {
			disableScreen();

			var action = "/upload/upload?field=" + field + "&path=review&fileType=image";
			ef.multipart($("#editForm"), action, field, function(result) {

				if(result.succeed) {
					var fieldName = field.replace(/File/gi, '');
					var gubun = fieldName.replace(/Img/gi, '');

					var html = "";
					html += "<div class='attach'>";
					html += "	<img src='"+result.param.uploadUrl+"'>";
					html += "	<input type='hidden' name='attach' value='"+result.param.uploadUrl+"'>";
					html += "	<input type='hidden' name='attachOrgName' value='"+orgFilename+"'>";
					html += "	<input type='hidden' name='gubun' value='"+gubun+"'>";
					html += "	<input type='button' class='btnTypeE sizeD' onclick='removeImg(this); return false;' value='삭제'>";
					html += "</div>";
					$("#"+fieldName).append(html);
				} else {
					alert(result.message);
				}

				$("input[name=" + field + "]").val("");

				if($(".attach").length >= 5){
					$("#reviewImgFile").hide();
				}else{
					$("#reviewImgFile").show();
				}
				enableScreen();
			});
		}
	}

	function removeImg(obj) {
		$(obj).parent().remove();

		if($(".attach").length >= 5){
			$("#reviewImgFile").hide();
		}else{
			$("#reviewImgFile").show();
		}
	}

	function setFileField(obj){
		var fileFullPath = $(obj).val()
		var filename = fileFullPath.replace(/^.*[\\\/]/, '');
		$(obj).parents(".fileField").find("input[type=text]").val(filename)
	}

	function setProduct(options) {
		$('#pname').html(options[0].pname);
		$('#pno').val(options[0].pno);
	}

	function getLength() {
		var s = $("textarea[name=content]").val().replace("내용을 입력해주세요.","");
		var len = s.length;
		$("#content2Length").html(len);
	}

</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="reviewNo" value="${ review.reviewNo }" />
<%--			<input type="hidden" name="cuser" value="${ review.cuser }" />--%>
			<input type="hidden" name="bestPointYn" value="${ review.bestPointYn }" />
		<h2>정보 등록</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>등록자ID</th>
					<td>
<%--						<a href="/member/general/edit?memNo=${ review.cuser }">${ review.cuserName}</a>--%>
						<input type="text" name="cuser" id="cuser" value="${ review.cuser }">
					</td>
					<th>등록일</th>
					<td><fmt:formatDate value="${review.cdate}" pattern="${ DateTimeFormat }"/></td>
				</tr>
				<tr >
					<th>제품명</th>
					<td><p style="float: left; padding-top: 5px; padding-left: 5px;" id="pname">${ review.pname }</p>
						<a href="#" onclick="showPopup('/popup/product', '500', '600'); return false" class="btnSizeA btnTypeD" style="float: right;">추가</a>
					</td>
					<th>제품코드</th>
					<td><input type="text" name="pno" id="pno" value="${ review.pno }"></td>
				</tr>
				<tr >
					<th>구분(1)</th>
					<td>
						<select name="type1" id="type1" class="defaultSelect" style="width:40%">
							<option value="1" ${review.type1 eq 1 ? 'selected':'' }>포토리뷰</option>
							<option value="2" ${review.type1 eq 2 ? 'selected':'' }>일반리뷰</option>
						</select>
					</td>
					<th>구분(2)</th>
					<td>
						<select name="type2" id="type2" class="defaultSelect" style="width:40%">
							<option value="1" ${review.type2 eq 1 ? 'selected':'' }>일반리뷰</option>
							<option value="2" ${review.type2 eq 2 ? 'selected':'' }>한달리뷰</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>베스트 선정</th>
					<td colspan=3>
						<label><input type="radio" name="bestYn" value="Y" ${ review.bestYn eq 'Y' ? 'checked' : '' }><span>Y</span></label>
						<label><input type="radio" name="bestYn" value="N" ${ review.bestYn eq 'N' ? 'checked' : '' }><span>N</span></label>
					</td>
				</tr>
				<tr>
					<th>메인노출</th>
					<td colspan=3>
						<label><input type="radio" name="mainYn" value="Y" ${ review.mainYn eq 'Y' ? 'checked' : '' }><span>Y</span></label>
						<label><input type="radio" name="mainYn" value="N" ${ review.mainYn eq 'N' ? 'checked' : '' }><span>N</span></label>
					</td>
				</tr>
				<tr>
					<th>Dr.Pnt 이용후기 게시</th>
					<td colspan=3>
						<label><input type="radio" name="reviewYn" value="Y" ${ review.reviewYn eq 'Y' ? 'checked' : '' }><span>Y</span></label>
						<label><input type="radio" name="reviewYn" value="N" ${ review.reviewYn eq 'N' ? 'checked' : '' }><span>N</span></label>
					</td>
				</tr>
				<tr>
					<th>게시 블록</th>
					<td colspan=3>
						<label><input type="radio" name="dispYn" value="Y" ${ review.dispYn eq 'Y' ? 'checked' : '' }><span>Y</span></label>
						<label><input type="radio" name="dispYn" value="N" ${ review.dispYn eq 'N' ? 'checked' : '' }><span>N</span></label>
					</td>
				</tr>
			</table>
		</div>

		<h2>리뷰 정보</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>별점</th>
					<td><select name="star" id="star" class="defaultSelect" style="width:15%">
						<option value="5" ${review.star eq 5 ? 'selected':'' }>★★★★★</option>
						<option value="4" ${review.star eq 4 ? 'selected':'' }>★★★★☆</option>
						<option value="3" ${review.star eq 3 ? 'selected':'' }>★★★☆☆</option>
						<option value="2" ${review.star eq 2 ? 'selected':'' }>★★☆☆☆</option>
						<option value="1" ${review.star eq 1 ? 'selected':'' }>★☆☆☆☆</option>
					</select>
<%--						<c:forEach var="i" begin="1" end="${ review.star }" step="1">★</c:forEach>--%>
					</td>
				</tr>
				<tr>
					<th>제목</th>
					<td><input style="width: 50%;" name="title" id="title" type="text" placeholder="제목을 입력하세요." title="제목" value="${mode eq 'create' ? '' : review.title  }"></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><textarea style="width: 50%;" name="content" id="content" placeholder="내용을 입력해주세요." onkeyup="getLength()">${mode eq 'create' ? '' : review.content  }</textarea>
						<p class="byte"><span id="content2Length">0</span>/1000</p></td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td>
						<input type="file" name="reviewImgFile" id="reviewImgFile" onchange="uploadImage('reviewImgFile');setFileField(this)">
						<div id="reviewImg">
							<c:if test="${ not empty reviewImgList }">
								<c:forEach items="${ reviewImgList }" var="item">
									<div class='attach'>
										<img src='${ item.attach }'>
										<input type='hidden' name='attach' value='${ item.attach }'>
										<input type='hidden' name='attachOrgName' value='${ item.attachOrgName }'>
										<input type='hidden' name='gubun' value='1'>
										<input type='button' class='btnTypeE sizeD' onclick='removeImg(this); return false;' value='삭제'>
									</div>
								</c:forEach>
							</c:if>
						</div>
					</td>
				</tr>
<%--				<tr style="display:${fn:length(reviewImgList) == 0?'none':'' };">--%>
<%--					<th>첨부파일</th>--%>
<%--					<td colspan=3>--%>
<%--						<c:forEach items="${ reviewImgList }" var="row">--%>
<%--							<div>--%>
<%--								<img src='${row.attach}'>--%>
<%--							</div>--%>
<%--						</c:forEach>--%>
<%--					</td>--%>
<%--				</tr>--%>
			</table>
		</div>

		<div class="btnArea">
			<span class="fl">
				<a href="${ retrivePath }" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
			<c:if test="${ mode eq 'create' && updateAuth eq 'Y' }">
				<a href="javascript:goSubmit()" class="btnTypeC btnSizeA"><span>등록</span></a>
			</c:if>
			<c:if test="${ mode eq 'addModify' && updateAuth eq 'Y' }">
				<a href="javascript:goSubmit()" class="btnTypeC btnSizeA"><span>수정</span></a>
			</c:if>
		</div>

		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
