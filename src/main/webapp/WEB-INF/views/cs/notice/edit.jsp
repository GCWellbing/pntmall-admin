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
		v.add("title", {
			empty : "제목을 입력하세요.",
			max : "200"
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

	function setStatus(noticeNo,commentNo,status) {

		if(v.validate()) {
			disableScreen();
			$.ajax({
				method : "POST",
				url : "setStatusNoticeComment",
				data : { "noticeNo" : noticeNo
					    ,"commentNo" : commentNo
					    , "status" : status },
				dataType : "json"
			})
			.done(function(result) {
				alert(result.message);
				if(result.succeed) location.href = "edit?noticeNo="+noticeNo;
				enableScreen();
			});
		}
	}

	function goDownload(){
		window.location.href = "downloadExcel?noticeNo=${ notice.noticeNo }";
	}
</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="noticeNo" value="${ notice.noticeNo }" />
		<h2>정보 등록</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>분류<sup>*</sup></th>
					<td>
						<select name="cate" style="width:200px">
							<c:forEach items="${ cateList }" var="row">
								<option value="${ row.code1 }${ row.code2 }" ${ notice.cate eq row.code1+=row.code2 ? 'selected' : '' }>${ row.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>댓글사용여부<sup>*</sup></th>
					<td>
						<label><input type="radio" name="commentYn" value="Y" ${ notice.commentYn eq 'Y' ? 'checked' : '' }><span>사용</span></label>
						<label><input type="radio" name="commentYn" value="N" ${ notice.commentYn eq 'N' || empty notice.commentYn ? 'checked' : '' }><span>미사용</span></label>
					</td>
				</tr>
				<tr>
					<th>제목<sup>*</sup></th>
					<td>
						<input type="text" name="title" id="title" value="${ notice.title }" style="width:200px" maxlength="4000">
					</td>
				</tr>
				<tr>
					<th>내용<sup>*</sup></th>
					<td>
						<textarea name="content" id="content" style="width:100%;height:100px">${ notice.content }</textarea>
					</td>
				</tr>
				<tr>
					<th>상단고정</th>
					<td>
						<input type="checkbox" name="fixYn" id="fixYn" value="Y" ${ notice.fixYn eq 'Y' ? 'checked':'' } ><span>상단고정</span>
					</td>
				</tr>
			</table>
		</div>

		<h2>메뉴권한</h2>
		<div class="white_box">
			<table class="board">
				<tr>
					<th>공개여부<sup>*</sup></th>
					<td>
						<label><input type="radio" name="status" value="S" ${ notice.status eq 'S' || empty notice.status ? 'checked' : '' }><span>공개</span></label>
						<label><input type="radio" name="status" value="H" ${ notice.status eq 'H' ? 'checked' : '' }><span>비공개</span></label>
					</td>
					<th>등록일/등록자</th>
					<td><fmt:formatDate value="${ notice.cdate }" pattern="${ DateTimeFormat }" /> / ${notice.cuserId}
					</td>
					<th>수정일/수정자</th>
					<td><fmt:formatDate value="${notice.udate}" pattern="${ DateTimeFormat }"/> / ${notice.uuserId}
					</td>
				</tr>
			</table>
		</div>

		<h2>전체댓글</h2>
		<div class="white_box">
			<table class="board">
				<tr>
					<th>전체댓글</th>
					<td>
						<a href="javascript:goDownload()" class="btnTypeC btnSizeA"><span>다운로드</span></a>
					</td>
					<th>댓글수</th>
					<td>${ fn:length(noticeCommentList) }
					</td>
				</tr>
			</table>
		</div>
		<div class="listHead">
			<div class="fl">
				<div class="totalArea">
					<strong class="totalTit">Total</strong> <span class="totalCount"><fmt:formatNumber value="${ fn:length(noticeCommentList) }" pattern="#,###" /></span>
				</div>
			</div>
		</div>

		<table class="list">
			<colgroup>
				<col width="60px">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th>No</th>
					<th>작성자</th>
					<th>ID</th>
					<th>내용</th>
					<th>등록일</th>
					<th>공개여부</th>
					<th>수정일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ noticeCommentList }" var="row">
				<tr>
					<td><c:out value="${ row.commentNo }" /></td>
					<td><c:out value="${ row.cuserName }" /></td>
					<td><c:out value="${ row.cuserId }" /></td>
					<td><c:out value="${ row.comment }" /></td>
					<td><fmt:formatDate value="${row.cdate}" pattern="${ DateTimeFormat }"/></td>
					<td><a href="javascript:setStatus('${row.noticeNo}','${row.commentNo}','${ row.status eq 'S'?'H':'S' }')"><c:out value="${ row.statusName }" /></a></td>
					<td><fmt:formatDate value="${row.udate}" pattern="${ DateTimeFormat }"/></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>

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

</body>
</html>
