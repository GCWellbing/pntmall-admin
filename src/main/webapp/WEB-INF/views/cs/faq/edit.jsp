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
		v.add("question", {
			empty : "질문 입력하세요.",
			max : "4000"
		});

	});

	function goSubmit() {
		oEditors.getById["answer"].exec("UPDATE_CONTENTS_FIELD", []);

		if(v.validate()) {
			disableScreen();
			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list";
				enableScreen();
			});
		}
	}

</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="faqNo" value="${ faq.faqNo }" />
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
								<option value="${ row.code1 }${ row.code2 }" ${ faq.cate eq row.code1+=row.code2 ? 'selected' : '' }>${ row.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>질문<sup>*</sup></th>
					<td>
						<input type="text" name="question" id="question" value="${ faq.question }" style="width:200px" maxlength="4000">
					</td>
				</tr>
				<tr>
					<th>답변</th>
					<td>
						<textarea name="answer" id="answer" style="width:100%;height:100px">${ faq.answer }</textarea>
					</td>
				</tr>
				<tr>
					<th>자주하는 질문</th>
					<td>
						<input type="checkbox" name="frequentYn" id="frequentYn" value="Y" ${ faq.frequentYn eq 'Y' ? 'checked':'' } ><span>자주하는 질문</span>
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
						<label><input type="radio" name="status" value="S" ${ faq.status eq 'S' || empty faq.status ? 'checked' : '' }><span>공개</span></label>
						<label><input type="radio" name="status" value="H" ${ faq.status eq 'H' ? 'checked' : '' }><span>비공개</span></label>
					</td>
					<th>등록일/등록자</th>
					<td><fmt:formatDate value="${ faq.cdate }" pattern="${ DateTimeFormat }" /> / ${faq.cuser}
					</td>
					<th>수정일/수정자</th>
					<td><fmt:formatDate value="${faq.udate}" pattern="${ DateTimeFormat }"/> / ${faq.uuser}
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
		elPlaceHolder: "answer",
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
