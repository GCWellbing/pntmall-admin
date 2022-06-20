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
	});

	function goSubmit() {

        if($("#chkComment").is(":checked")){
            if($("#comment").val() == ""){
            	alert("댓글을 입력하여 주세요");
            	return false;
            }
        }else{
        	$("#comment").val("");
        }

		disableScreen();
		ef.proc($("#editForm"), function(result) {
			alert(result.message);
			if(result.succeed) location.href = "${ retrivePath }";
			enableScreen();
		});
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
			<input type="hidden" name="cuser" value="${ review.cuser }" />
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
					<th>등록자</th>
					<td><a href="/member/general/edit?memNo=${ review.cuser }">${ review.cuserName}</a></td>
					<th>등록일</th>
					<td><fmt:formatDate value="${review.cdate}" pattern="${ DateTimeFormat }"/></td>
				</tr>
				<tr >
					<th>제품명</th>
					<td>${ review.pname }</td>
					<th>제품코드</th>
					<td>${ review.pno }</td>
				</tr>
				<tr >
					<th>구분(1)</th>
					<td>${ review.type1Name }</td>
					<th>구분(2)</th>
					<td>${ review.type2Name }</td>
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
					<td><c:forEach var="i" begin="1" end="${ review.star }" step="1">★</c:forEach></td>
				</tr>
				<tr>
					<th>제목</th>
					<td>${ review.title }</td>
				</tr>
				<tr>
					<th>내용</th>
					<td>${ review.content }</td>
				</tr>
				<tr style="display:${fn:length(reviewImgList) == 0?'none':'' };">
					<th>첨부파일</th>
					<td colspan=3>
						<c:forEach items="${ reviewImgList }" var="row">
							<div>
								<img src='${row.attach}'>
							</div>
						</c:forEach>
					</td>
				</tr>
			</table>
		</div>


		<h2>댓글 등록</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>등록 여부</th>
					<td><input type="checkbox" name="chkComment" id="chkComment" value="Y" ${ empty review.comment ? '' : 'checked' }>댓글 입력하기</td>
				</tr>
				<tr id="trComment" style="display:${ empty review.comment ? 'none':'' }">
					<th>내용</th>
					<td>
						<textarea name="comment" id="comment" style="width:100%;height:100px">${ review.comment }</textarea>
					</td>
				</tr>
			</table>
		</div>

		<div class="btnArea">
			<span class="fl">
				<a href="${ retrivePath }" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
			<c:if test="${ mode eq 'modify' && updateAuth eq 'Y' }">
				<a href="javascript:goSubmit()" class="btnTypeC btnSizeA"><span>수정</span></a>
			</c:if>
		</div>

		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
