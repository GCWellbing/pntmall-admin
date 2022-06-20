<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>

	var idx = ${ fn:length(list) + 1 };
	var ex_no;

	var v;

	$(function() {
		v = new ef.utils.Validator($("#editForm"));
		v.add("question", {
			empty : "질문을 입력하세요.",
			max : "100"
		});

		$("input[name=type]").on('click', function(){
			if($(this).val() == 'I'){
				$("#exampleList").html("");
				$("input[name=naYn]").prop("checked", false);
				$("input[name=etcYn]").prop("checked", false);
				$("input[name=popupYn]").prop("checked", false);

				$("input[name=naYn]").prop("disabled", true);
				$("input[name=etcYn]").prop("disabled", true);
				$("input[name=popupYn]").prop("disabled", true);
				$("#addExampleBtn").hide();
				idx = 1;
			}else{
				$("input[name=naYn]").prop("disabled", false);
				$("input[name=etcYn]").prop("disabled", false);
				$("input[name=popupYn]").prop("disabled", false);
				$("#addExampleBtn").show();
			}
		});

		$("input[name=popupYn]").on('click', function(){
			if($(this).is(":checked")){
				$('[id^="pointRow"]').hide();
				$('[id^="productRow"]').hide();
				$('[id^="productFunRow"]').hide();
				$('[id^="reportRow"]').hide();

				$('[id^="warningRow"]').show();

			}else{
				$('[id^="pointRow"]').show();
				$('[id^="productRow"]').show();
				$('[id^="productFunRow"]').show();
				$('[id^="reportRow"]').show();

				$('[id^="warningRow"]').hide();
			}
		});

		<c:if test="${info.popupYn eq 'Y'}">
			$('[id^="pointRow"]').hide();
			$('[id^="productRow"]').hide();
			$('[id^="productFunRow"]').hide();
			$('[id^="reportRow"]').hide();

			$('[id^="warningRow"]').show();
		</c:if>
	});



	function goSubmit() {
		if(v.validate()) {
			let exLength = $("input[name='exNo']").length - 1;
			let pupupYn = $("input[name=popupYn]:checked").val();
			if($("input[name=type]:checked").val() != "I"){
				if(exLength > 0){
					for (let idx = 1; idx <= exLength; idx++) {
						if($("input[name=example"+idx+"]").val() == '') {
							alert("보기명을 입력하지 않았습니다.");
							return;
						}
						if(pupupYn != 'Y'){
							if($("input[name=point"+idx+"]").val() == '') {
								alert("배점을 입력하지 않았습니다.");
								return;
							}

							let productLength = $("input[name=pno"+idx+"]").length;
							if(productLength == 0) {
								alert("추천제품을 선택하지 않았습니다.");
								return;
							}
						}

						let nutritionLength = $("input[name=nutritionNo"+idx+"]").length;
						if(nutritionLength == 0) {
							alert("대표성분을 선택하지 않았습니다.");
							return;
						}
						if(pupupYn != 'Y') {
							/*if($("select[name=productFun"+idx+"]").val() == '') {
								alert("제품기능을 선택하지 않았습니다.");
								return;
							}*/
							if ($("textarea[name=report" + idx + "]").val() == '') {
								alert("제품기능 보고서를 입력하지 않았습니다.");
								return;
							}
						}

						if(pupupYn == 'Y'){
							if($("textarea[name=warning"+idx+"]").val() == '') {
								alert("주위문구를 입력하지 않았습니다.");
								return;
							}
						}

						if($("input[name=rank"+idx+"]").val() == '') {
							alert("전시순서를 입력하 않았습니다.");
							return;
						}
					}
				}
			}

			if(pupupYn == 'Y'){
				$('[id^="productDiv"]').html("");
				$("input[name^=point]").val("");
				$("textarea[name^=report]").val("");
				$("select[name^=productFun]").val("");
			}else{
				$("textarea[name^=warning]").val("");
			}

			disableScreen();

			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "edit?healthNo=${ topic.healthNo }";
				enableScreen();
			});
		}
	}

	function addExample() {
		var html;
		html = $("#addExample").html();
		html = html.replace(/##EX_NO##/gi, idx++);
		$("#exampleList").append(html);
	}

	function removeExample(no) {
		$("#example" +no).remove();

		$("input[name='exNo']").each(function (idx) {
			var exNo = $(this).val();
			if($.isNumeric(exNo)){
				var num = idx + 1;

				$(this).val(num);
				$("#example"+exNo).attr("id", "example"+ num);
				$("input[name='example"+ exNo +"\']").attr("name", "example"+ num);
				$("#pointRow"+exNo).attr("id", "pointRow"+ num);
				$("input[name='point"+ exNo +"\']").attr("name", "point"+ num);
				$("#productRow"+exNo).attr("id", "productRow"+ num);
				$("#productDiv"+exNo).attr("id", "productDiv"+ num);
				$("input[name='pno"+ exNo +"\']").attr("name", "pno"+ num);
				$("#nutritionDiv"+exNo).attr("id", "nutritionDiv"+ num);
				$("input[name='nutritionNo"+ exNo +"\']").attr("name", "nutritionNo"+ num);

				$("#productFunRow"+exNo).attr("id", "productFunRow"+ num);
				$("select[name='productFun"+ exNo +"\']").attr("name", "productFun"+ num);

				$("#reportRow"+exNo).attr("id", "reportRow"+ num);
				$("textarea[name='report"+ exNo +"\']").attr("name", "report"+ num);

				$("#warningRow"+exNo).attr("id", "warningRow"+ num);
				$("input[name='warning"+ exNo +"\']").attr("name", "warning"+ num);

				$("input[name='rank"+ exNo +"\']").attr("name", "rank"+ num);

				$("#removeExample"+exNo).removeAttr("onclick");
				$("#removeExample"+exNo).attr("onclick", "removeExample(\'"+ num+ "\'); return false;");
				$("#removeExample"+exNo).attr("id", "removeExample"+ num);

				$("#addProduct"+exNo).removeAttr("onclick");
				$("#addProduct"+exNo).attr("onclick", "addProduct(\'"+ num+ "\'); return false;");
				$("#addProduct"+exNo).attr("id", "addProduct"+ num);

				$("#addNutrition"+exNo).removeAttr("onclick");
				$("#addNutrition"+exNo).attr("onclick", "addNutrition(\'"+ num+ "\'); return false;");
				$("#addNutrition"+exNo).attr("id", "addNutrition"+ num);

			}
		});
	}

	function addProduct(no){
		ex_no = no;
		showPopup('/popup/product', '450', '600');
	}

	function setProduct(options) {
		var html;
		for(var i = 0; i < options.length; i++) {
			html = "<p style='margin-bottom:5px'><a href='#' onclick='removeProduct(this); return false;' class='btnSizeC'>삭제</a>";
			html += "&nbsp;&nbsp;" + options[i].pname;
			html += "<input type='hidden' name='pno"+ex_no+"' value='" + options[i].pno + "'></p>";
			$("#productDiv"+ex_no).append(html);
		}
	}

	function removeProduct(obj) {
		$(obj).parent().remove();
	}

	function addNutrition(no){
		ex_no = no;
		showPopup('/popup/nutrition', '500', '600');
	}

	function setNutrition(nutritions) {
		var html;
		for(var i = 0; i < nutritions.length; i++) {
			html = "<p style='margin-bottom:5px'><a href='#' onclick='removeNutrition(this); return false;' class='btnSizeC'>삭제</a>";
			html += "&nbsp;&nbsp;" + nutritions[i].name;
			html += "<input type='hidden' name='nutritionNo"+ex_no+"' value='" + nutritions[i].nutritionNo + "'></p>";
			$("#nutritionDiv"+ex_no).append(html);
		}
	}

	function removeNutrition(obj) {
		$(obj).parent().remove();
	}

</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="healthNo" value="${ topic.healthNo }" />
		<h2>기본 문진정보</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>건강주제<sup>*</sup></th>
					<td>
						${topic.title}
					</td>
				</tr>
				<tr>
					<th>질문<sup>*</sup></th>
					<td>
						<input type="text" name="question" value="${ info.question }" style="width:100%" maxlength="100">
					</td>
				</tr>
				<tr>
					<th>유형</th>
					<td>
						<label><input type="radio" name="type" value="P" ${ mode eq 'createQuestion' || info.type eq 'P' ? 'checked' : '' }><span>복수</span></label>
						<label><input type="radio" name="type" value="S" ${ info.type eq 'S' ? 'checked' : '' }><span>단독</span></label>
						<label><input type="radio" name="type" value="I" ${ info.type eq 'I' ? 'checked' : '' }><span>입력</span></label>
					</td>
				</tr>
				<tr>
					<th>해당없음</th>
					<td>
						<label><input type="checkbox" name="naYn" value="Y" ${ info.naYn eq 'Y' ? "checked" : "" } ${ info.type eq 'I' ? 'disabled' : '' }><span>해당없음 노출</span></label>
					</td>
				</tr>
				<tr>
					<th>기타</th>
					<td>
						<label><input type="checkbox" name="etcYn" value="Y" ${ info.etcYn eq 'Y' ? "checked" : "" } ${ info.type eq 'I' ? 'disabled' : '' }><span>기타 노출</span></label>
					</td>
				</tr>
				<tr>
					<th>안내팝업</th>
					<td>
						<label><input type="checkbox" name="popupYn" value="Y" ${ info.popupYn eq 'Y' ? "checked" : "" } ${ info.type eq 'I' ? 'disabled' : '' }><span>안내팝업 노출</span></label>
					</td>
				</tr>
			</table>
		</div>

		<div class="btnArea">
		<span class="fr">
			<a href="#" onclick="addExample(); return false" class="btnSizeA btnTypeD" id="addExampleBtn" style="${ info.type eq 'I' ? 'display:none' : '' }" >보기 추가</a>
		</span>
		</div>

		<div id="exampleList">
			<c:forEach items="${list}" var="row" varStatus="exNo">
			<div id="example${exNo.count}">
				<h2>보기${exNo.count}
					<span class="fr">
						<a href="#" id="removeExample${exNo.count}" onclick="removeExample('${exNo.count}'); return false" class="btnSizeA btnTypeD">삭제</a>
					</span>
				</h2>
				<div class="white_box" >
					<table class="board">
						<colgroup>
							<col width="15%">
							<col width="">
						</colgroup>
						<tr>
							<th>보기명<sup>*</sup></th>
							<td>
								<input type='hidden' name='exNo' value='${ exNo.count }'>
								<input type="text" name="example${exNo.count}" value="${ row.example }" style="width:100%" maxlength="100">
							</td>
						</tr>
						<tr id="pointRow${exNo.count}" style="${info.popupYn eq 'Y' ? 'display:none' : ''}">
							<th>배점<sup>*</sup></th>
							<td>
								<input type="text" name="point${exNo.count}" value="${ row.point }" style="width:50%" maxlength="3">
							</td>
						</tr>
						<tr id="productRow${exNo.count}"  style="${info.popupYn eq 'Y' ? 'display:none' : ''}">
							<th>추천제품<sup>*</sup></th>
							<td>
								<div id="productDiv${exNo.count}">
									<c:forEach items="${ productList }" var="pRow">
										<c:if test="${pRow.exNo eq row.exNo}">
										<p style="margin-bottom:5px">
											<a href="#" onclick="removeProduct(this); return false;" class="btnSizeC">삭제</a>
											&nbsp;&nbsp;${ pRow.pname }
											<input type="hidden" name="pno${exNo.count}" value="${ pRow.pno }">
										</p>
										</c:if>
									</c:forEach>
								</div>
								<a href="#" id="addProduct${exNo.count}" onclick="addProduct('${exNo.count}'); return false" class="btnSizeA btnTypeD">추가</a>
							</td>
						</tr>
						<tr>
							<th>대표성분<sup>*</sup></th>
							<td>
								<div id="nutritionDiv${exNo.count}">
									<c:forEach items="${ nutritionList }" var="nRow">
										<c:if test="${nRow.exNo eq row.exNo}">
										<p style="margin-bottom:5px">
											<a href="#" onclick="removeNutrition(this); return false;" class="btnSizeC">삭제</a>
											&nbsp;&nbsp;${ nRow.nutritionName }
											<input type="hidden" name="nutritionNo${exNo.count}" value="${ nRow.nutritionNo }">
										</p>
										</c:if>
									</c:forEach>
								</div>
								<a href="#" id="addNutrition${exNo.count}" onclick="addNutrition('${exNo.count}'); return false" class="btnSizeA btnTypeD">추가</a>
							</td>
						</tr>
						<tr id="productFunRow${exNo.count}"  style="${info.popupYn eq 'Y' ? 'display:none' : ''}">
							<th>제품기능<sup>*</sup></th>
							<td>
								<select name="productFun${exNo.count}" style="width:200px">
									<option value="">선택</option>
									<c:forEach items="${ typeList }" var="tRow">
										<option value="${ tRow.code2}" ${tRow.code2 eq row.productFun ? 'selected' : ''}>${tRow.name}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr id="reportRow${exNo.count}"  style="${info.popupYn eq 'Y' ? 'display:none' : ''}">
							<th>제품기능 보고서</th>
							<td>
								<textarea name="report${exNo.count}" style="width:100%;height:50px">${row.report}</textarea>
							</td>
						</tr>
						<tr id="warningRow${exNo.count}"  style="${info.popupYn ne 'Y' ? 'display:none' : ''}">
							<th>주의문구</th>
							<td>
								<textarea name="warning${exNo.count}" style="width:100%;height:50px">${row.warning}</textarea>
							</td>
						</tr>
						<tr>
							<th>전시순서</th>
							<td>
								<input type="text" name="rank${exNo.count}" value="${row.rank}" style="width:100px">
							</td>
						</tr>
					</table>
				</div>
			</div>
			</c:forEach>
		</div>
		<br><br>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>등록일/등록자</th>
					<td><fmt:formatDate value="${ info.cdate }" pattern="${ DateTimeFormat }" /> / ${ info.cuserId }</td>
					<th>수정일/수정자</th>
					<td><fmt:formatDate value="${ info.udate }" pattern="${ DateTimeFormat }" /> / ${ info.uuserId }</td>
				</tr>
			</table>
		</div>

		<div class="btnArea">
			<span class="fl">
				<a href="list" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
			<c:if test="${ mode eq 'createQuestion' }">
				<a href="javascript:goSubmit()" class="btnTypeD btnSizeA"><span>등록</span></a>
			</c:if>
			<c:if test="${ mode eq 'modifyQuestion' && updateAuth eq 'Y' }">
				<a href="javascript:goSubmit()" class="btnTypeD btnSizeA"><span>수정</span></a>
			</c:if>
		</div>
		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->
<div id="addExample" style="display:none">
	<div id="example##EX_NO##">
		<h2>
			보기##EX_NO##
			<span class="fr">
					<a href="#" onclick="removeExample('##EX_NO##'); return false" class="btnSizeA btnTypeD">삭제</a>
			</span>
		</h2>
		<div class="white_box" >
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>보기명<sup>*</sup></th>
					<td>
						<input type='hidden' name='exNo' value='##EX_NO##'>
						<input type="text" name="example##EX_NO##" value="" style="width:100%" maxlength="50">
					</td>
				</tr>
				<tr id="pointRow##EX_NO##">
					<th>배점<sup>*</sup></th>
					<td>
						<input type="text" name="point##EX_NO##" value="" style="width:50%" maxlength="3">
					</td>
				</tr>
				<tr id="productRow##EX_NO##">
					<th>추천제품<sup>*</sup></th>
					<td>
						<div id="productDiv##EX_NO##">
						</div>
						<a href="#" onclick="addProduct('##EX_NO##'); return false" class="btnSizeA btnTypeD">추가</a>
					</td>
				</tr>
				<tr>
					<th>대표성분<sup>*</sup></th>
					<td>
						<div id="nutritionDiv##EX_NO##">
						</div>
						<a href="#" onclick="addNutrition('##EX_NO##'); return false" class="btnSizeA btnTypeD">추가</a>
					</td>
				</tr>
				<tr id="productFunRow##EX_NO##">
					<th>제품기능<sup>*</sup></th>
					<td>
						<select name="productFun##EX_NO##" style="width:200px">
							<option value="">선택</option>
							<c:forEach items="${ typeList }" var="row">
								<option value="${ row.code2}">${row.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr id="reportRow##EX_NO##">
					<th>제품기능 보고서</th>
					<td>
						<textarea name="report##EX_NO##" style="width:100%;height:50px"></textarea>
					</td>
				</tr>
				<tr id="warningRow##EX_NO##" style="display:none">
					<th>주의문구</th>
					<td>
						<textarea name="warning##EX_NO##" style="width:100%;height:50px"></textarea>
					</td>
				</tr>
				<tr>
					<th>전시순서</th>
					<td>
						<input type="text" name="rank##EX_NO##" value="1" style="width:100px">
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>


</body>
</html>
