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
		v.add("sdate", {
			empty : "전시기간을 입력하세요."
		});
		v.add("edate", {
			empty : "전시기간을 입력하세요."
		});
		v.add("pname", {
			empty : "제품명을 입력하세요.",
			max : "50"
		});
		v.add("capa", {
			empty : "용량을 입력하세요.",
			max : "50"
		});
		v.add("summary", {
			empty : "제품대표설명을 입력하세요.",
			max : "2000"
		});
		/*
		v.add("orgImg", {
			empty : "원본이미지를 입력하세요."
		});
		v.add("img", {
			empty : "대표이미지를 입력하세요."
		});
		*/
		v.add("salePrice", {
			empty : "판매가격을 입력하세요.",
			format : "numeric"
		});

		$("#sdate").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		$("#edate").datepicker({
			changeMonth: true,
		    changeYear: true
		});

		$("#intakeList").on("click", ".btnUp", function() {
			var currObj = $(this).parents("tr");
			var idx = $(currObj).index();

			if (idx > 0) {
				var upperObj = currObj.prev();
				upperObj.before($("<tr></tr>").html(currObj.html()));

				//jquery 버그? 변경된 값은 html()에서 제너레이트 되지 않는다. 변경된 값을 다시 재지정.
				$("#intakeList input[name=intakeNo]", upperObj.prev()).val($("#intakeList input[name=intakeNo]", currObj).val());

				currObj.remove();
			}
		});

		$("#intakeList").on("click", ".btnDown", function() {
			var currObj = $(this).parents("tr");
			var size = $("#intakeList tr").length;
			var idx = $(currObj).index();

			if (idx + 1 < size) {
				var belowObj = currObj.next();
				belowObj.after($("<tr></tr>").html(currObj.html()));

				//jquery 버그? 변경된 값은 html()에서 제너레이트 되지 않는다. 변경된 값을 다시 재지정.
				$("#intakeList input[name=intakeNo]", belowObj.next()).val($("#intakeList input[name=intakeNo]", currObj).val());

				currObj.remove();
			}
		});

		$("#doseList").on("click", ".btnUp", function() {
			var currObj = $(this).parents("tr");
			var idx = $(currObj).index();

			if (idx > 0) {
				var upperObj = currObj.prev();
				upperObj.before($("<tr></tr>").html(currObj.html()));

				//jquery 버그? 변경된 값은 html()에서 제너레이트 되지 않는다. 변경된 값을 다시 재지정.
				$("#doseList input[name=doseNo]", upperObj.prev()).val($("#doseList input[name=doseNo]", currObj).val());

				currObj.remove();
			}
		});

		$("#doseList").on("click", ".btnDown", function() {
			var currObj = $(this).parents("tr");
			var size = $("#doseList tr").length;
			var idx = $(currObj).index();

			if (idx + 1 < size) {
				var belowObj = currObj.next();
				belowObj.after($("<tr></tr>").html(currObj.html()));

				//jquery 버그? 변경된 값은 html()에서 제너레이트 되지 않는다. 변경된 값을 다시 재지정.
				$("#doseList input[name=doseNo]", belowObj.next()).val($("#doseList input[name=doseNo]", currObj).val());

				currObj.remove();
			}
		});

		$("#nutritionList").on("click", ".btnUp", function() {
			var no = $(this).parents("tr").find("input[name=nutritionNo]").val();
			var content = $("#nutritionList input[name=nutritionContent" + no + "]").val();
			var standard = $("#nutritionList input[name=nutritionStandard" + no + "]").val();

			var currObj = $(this).parents("tr");
			var idx = $(currObj).index();

			if (idx > 0) {
				var upperObj = currObj.prev();
				upperObj.before($("<tr></tr>").html(currObj.html()));

				//jquery 버그? 변경된 값은 html()에서 제너레이트 되지 않는다. 변경된 값을 다시 재지정.
				$("#nutritionList input[name=nutritionNo]", upperObj.prev()).val($("#nutritionList input[name=nutritionNo]", currObj).val());

				currObj.remove();

				$("#nutritionList input[name=nutritionContent" + no + "]").val(content);
				$("#nutritionList input[name=nutritionStandard" + no + "]").val(standard);
			}
		});

		$("#nutritionList").on("click", ".btnDown", function() {
			var no = $(this).parents("tr").find("input[name=nutritionNo]").val();
			var content = $("#nutritionList input[name=nutritionContent" + no + "]").val();
			var standard = $("#nutritionList input[name=nutritionStandard" + no + "]").val();

			var currObj = $(this).parents("tr");
			var size = $("#nutritionList tr").length;
			var idx = $(currObj).index();

			if (idx + 1 < size) {
				var belowObj = currObj.next();
				belowObj.after($("<tr></tr>").html(currObj.html()));

				//jquery 버그? 변경된 값은 html()에서 제너레이트 되지 않는다. 변경된 값을 다시 재지정.
				$("#nutritionList input[name=nutritionNo]", belowObj.next()).val($("#nutritionList input[name=nutritionNo]", currObj).val());

				currObj.remove();

				$("#nutritionList input[name=nutritionContent" + no + "]").val(content);
				$("#nutritionList input[name=nutritionStandard" + no + "]").val(standard);
			}
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
			if($("input[name=matnr]").val() == '') {
				alert("SAP 제품정보를 입력하세요.");
				return;
			}
			if($("input[name=cateNo]").length == 0) {
				alert("카테고리를 입력하세요.");
				return;
			}

			disableScreen();
			oEditors.getById["pcDesc"].exec("UPDATE_CONTENTS_FIELD", []);
			oEditors.getById["moDesc"].exec("UPDATE_CONTENTS_FIELD", []);

			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list";
				enableScreen();
			});
		}
	}

	function setSap(sap) {
		$("#sap").html(sap.maktx + " (" + sap.matnr + ")");
		$("input[name=matnr]").val(sap.matnr);
		$("input[name=netpr]").val(sap.netpr);
	}

	function setCate(categories) {
		var html;
		for(var i = 0; i < categories.length; i++) {
			if($("input[name=cateNo][value=" + categories[i].cateNo + "]").length == 0) {
				html = "<p style='margin-bottom:5px'><a href='#' onclick='removeCate(this); return false;' class='btnSizeC'>삭제</a>";
				html += "&nbsp;&nbsp;" + categories[i].name;
				html += "<input type='hidden' name='cateNo' value='" + categories[i].cateNo + "'></p>";
				$("#cateDiv").append(html);
			}
		}
	}

	function removeCate(obj) {
		$(obj).parent().remove();
	}

	function removeOption(obj) {
		$(obj).parent().parent().remove();
	}

	function setIntake(intakes) {
		var html;
		for(var i = 0; i < intakes.length; i++) {
			if($("input[name=intakeNo][value=" + intakes[i].intakeNo + "]").length == 0) {
				html = "<tr><td class='al'>" + intakes[i].content;
				html += "<input type='hidden' name='intakeNo' value='" + intakes[i].intakeNo + "'></td>";
				html += "<td><input type='button' class='btnUp' value='위로'>";
				html += "<input type='button' class='btnDown' value='아래로'></td>";
				html += "<td><input type='button' class='btnSizeC' onclick='removeIntake(this); return false;' value='삭제'></td></tr>";
				$("#intakeList").append(html);
			}
		}
	}

	function removeIntake(obj) {
		$(obj).parent().parent().remove();
	}

	function setDose(doses) {
		var html;
		for(var i = 0; i < doses.length; i++) {
			if($("input[name=doseNo][value=" + doses[i].doseNo + "]").length == 0) {
				html = "<tr><td class='al'>" + doses[i].content;
				html += "<input type='hidden' name='doseNo' value='" + doses[i].doseNo + "'></td>";
				html += "<td><input type='button' class='btnUp' value='위로'>";
				html += "<input type='button' class='btnDown' value='아래로'></td>";
				html += "<td><input type='button' class='btnSizeC' onclick='removeDose(this); return false;' value='삭제'></td></tr>";
				$("#doseList").append(html);
			}
		}
	}

	function removeDose(obj) {
		$(obj).parent().parent().remove();
	}

	function setNutrition(nutritions) {
		var html;
		for(var i = 0; i < nutritions.length; i++) {
			if($("input[name=nutritionNo][value=" + nutritions[i].nutritionNo + "]").length == 0) {
				html = $("#nutritionRow").html();
				html = html.replace(/##NUTRITION_NO##/gi, nutritions[i].nutritionNo);
				html = html.replace(/##NUTRITION_NAME##/gi, nutritions[i].name);
				html = html.replace(/##NUTRITION_FUNC##/gi, nutritions[i].func);
				html = html.replace(/##NUTRITION_STANDARD##/gi, nutritions[i].standard);
				html = html.replace(/##NUTRITION_UNIT_NAME##/gi, nutritions[i].unitName);
				$("#nutritionList").append(html);
			}
		}
	}

	function removeNutrition(obj) {
		$(obj).parent().parent().remove();
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

	function removeNutrition(obj) {
		$(obj).parent().parent().remove();
	}

	function setGift(gifts) {
		var html;
		for(var i = 0; i < gifts.length; i++) {
			if($("input[name=giftPno][value=" + gifts[i].giftPno + "]").length == 0) {
				html = "<p style='margin-bottom:5px'><a href='#' onclick='removeGift(this); return false;' class='btnSizeC'>삭제</a>";
				html += "&nbsp;&nbsp;" + gifts[i].pname;
				html += "<input type='hidden' name='giftPno' value='" + gifts[i].giftPno + "'></p>";
				$("#giftDiv").append(html);
			}
		}
	}

	function removeGift(obj) {
		$(obj).parent().remove();
	}

	function uploadImage(field) {
		if($("input[name=" + field + "]").val() != '') {
	        disableScreen();

	        var action = "/upload/upload?field=" + field + "&path=product&fileType=image";
	        ef.multipart($("#editForm"), action, field, function(result) {

	        	if(result.succeed) {
	        		var s = field.replace(/File/gi, '');
	        		$("input[name=" + s + "]").val(result.param.uploadUrl);
	        		$("#" + s + "Tag").attr("src", result.param.uploadUrl);
	        		$("#" + s + "Div").show();
	        	} else {
	        		alert(result.message);
	        	}

	        	$("input[name=" + field + "]").val("");
	        	enableScreen();
	        });
		}
	}

	function removeImg(s) {
		$("input[name=" + s + "]").val('');
		$("#" + s + "Tag").attr("src", '');
		$("#" + s + "Div").hide();
	}

	function setDiscountRate(no) {
		if(no == 1) {
			$(".discount").html("원");
		} else {
			$(".discount").html("%");
		}
	}

	function setProductInfo(info) {
		$("input[name=info1]").val(info.info1);
		$("input[name=info2]").val(info.info2);
		$("input[name=info3]").val(info.info3);
		$("input[name=info4]").val(info.info4);
		$("input[name=info5]").val(info.info5);
		$("input[name=info6]").val(info.info6);
		$("input[name=info7]").val(info.info7);
		$("input[name=info8]").val(info.info8);
		$("input[name=info9]").val(info.info9);
		$("input[name=info10]").val(info.info10);
		$("input[name=info11]").val(info.info11);
		$("input[name=info12]").val(info.info12);
		$("input[name=info13]").val(info.info13);
	}
</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="pno" value="${ product.pno }" />
			<input type="hidden" name="ptype" value="1">
		<h2>기본 전시 정보</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>SAP 제품정보<sup>*</sup></th>
					<td>
						<span id="sap">
						<c:if test="${ mode eq 'modify' }">
						${ product.maktx } (${ product.matnr })
						</c:if>
						</span>
						<input type="hidden" name="matnr" value="${ product.matnr }">
						<a href="#" onclick="showPopup('/popup/sap', '1024', '800'); return false" class="btnSizeA btnTypeD">선택</a>
					</td>
				</tr>
				<tr>
					<th>노출등급</th>
					<td>
						<c:forEach items="${ gradeList }" var="row">
							<label><input type="checkbox" name="gradeNo" value="${ row.gradeNo }" ${ mode eq 'create' || !empty row.pno ? "checked" : "" }><span>${ row.gradeName }</span></label>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th>카테고리<sup>*</sup></th>
					<td>
						<div id="cateDiv">
						<c:forEach items="${ categoryList }" var="row">
							<p style="margin-bottom:5px">
								<a href="#" onclick="removeCate(this); return false;" class="btnSizeC">삭제</a>
								&nbsp;&nbsp;${ row.pcateName } &gt; ${ row.cateName }
								<input type="hidden" name="cateNo" value="${ row.cateNo }">
							</p>
						</c:forEach>
						</div>
						<a href="#" onclick="showPopup('/popup/category', '450', '600'); return false" class="btnSizeA btnTypeD">추가</a>
					</td>
				</tr>
				<tr>
					<th>리뷰 노출 여부</th>
					<td>
						<label><input type="radio" name="reviewYn" value="Y" ${ mode eq 'create' || product.reviewYn eq 'Y' ? 'checked' : '' }><span>노출</span></label>
						<label><input type="radio" name="reviewYn" value="N" ${ product.reviewYn eq 'N' ? 'checked' : '' }><span>비노출</span></label>
					</td>
				</tr>
				<tr>
					<th>병의원 매출</th>
					<td>
						<label><input type="radio" name="clinicSettlement" value="Y" ${ mode eq 'create' || product.clinicSettlement eq 'Y' ? 'checked' : '' }><span>포함</span></label>
						<label><input type="radio" name="clinicSettlement" value="N" ${ product.clinicSettlement eq 'N' ? 'checked' : '' }><span>미포함</span></label>
					</td>
				</tr>
				<tr>
					<th>전시기간<sup>*</sup></th>
					<td>
						<div class="dateBox">
							<input type="text" name="sdate" id="sdate" value="${ mode eq 'create' ? today : product.sdate }" readonly>
						</div> ~
						<div class="dateBox">
							<input type="text" name="edate" id="edate" value="${ mode eq 'create' ? '2099.12.31' : product.edate }" readonly>
						</div>
					</td>
				</tr>
				<tr>
					<th>전시여부</th>
					<td>
						<label><input type="radio" name="status" value="S" ${ mode eq 'create' || product.status eq 'S' ? 'checked' : '' }><span>공개</span></label>
						(<label><input type="checkbox" name="soldout" value="Y" ${ product.soldout eq 'Y' ? 'checked' : '' }><span>품절</span></label>)
						<label><input type="radio" name="status" value="H" ${ product.status eq 'H' ? 'checked' : '' }><span>비공개</span></label>
						<label><input type="radio" name="status" value="E" ${ product.status eq 'E' ? 'checked' : '' }><span>단종</span></label>
					</td>
				</tr>
				<tr>
					<th>전시순서(추천순)</th>
					<td>
						<input type="text" name="rank" value="${ product.rank }" style="width:100px" maxlength="10">
					</td>
				</tr>
			</table>
		</div>

		<h2>상세 전시 정보</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>브랜드<sup>*</sup></th>
					<td>
						<select name="brand" style="width:200px">
							<c:forEach items="${ brandList }" var="row">
								<option value="${ row.code1 }${ row.code2 }" ${ row.code1.concat(row.code2) eq product.brand ? 'selected' : '' }>${ row.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>제품명<sup>*</sup></th>
					<td>
						<input type="text" name="pname" value="${ product.pname }" style="width:100%" maxlength="50">
					</td>
				</tr>
				<tr>
					<th>용량<sup>*</sup></th>
					<td>
						<input type="text" name="capa" value="${ product.capa }" style="width:50%" maxlength="50">
						ex) 100mg*60정(30g)
					</td>
				</tr>
				<tr>
					<th>복용 개월 수<sup>*</sup></th>
					<td>
						<label><input type="radio" name="doseMonth" value="0" ${ mode eq 'create' || product.doseMonth eq 0 ? 'checked' : '' }><span>비노출</span></label>
						<label><input type="radio" name="doseMonth" value="1" ${ product.doseMonth eq 1 ? 'checked' : '' }><span>1개월분</span></label>
						<label><input type="radio" name="doseMonth" value="2" ${ product.doseMonth eq 2 ? 'checked' : '' }><span>2개월분</span></label>
						<label><input type="radio" name="doseMonth" value="3" ${ product.doseMonth eq 3 ? 'checked' : '' }><span>3개월분</span></label>
						<label><input type="radio" name="doseMonth" value="4" ${ product.doseMonth eq 4 ? 'checked' : '' }><span>4개월분</span></label>
						<label><input type="radio" name="doseMonth" value="5" ${ product.doseMonth eq 5 ? 'checked' : '' }><span>5개월분</span></label>
					</td>
				</tr>
				<tr>
					<th>제품대표설명<sup>*</sup></th>
					<td>
						<textarea name="summary" style="width:100%;height:100px">${ product.summary }</textarea>
					</td>
				</tr>
				<tr>
					<th>태그</th>
					<td>
						<c:set var="tag" value="" />
						<c:forEach items="${ tagList }" var="row">
							<c:set var="tag" value="${ tag.concat(',').concat(row.tag) }" />
						</c:forEach>
						<c:if test='${ fn:length(tagList) > 0 }'>
							<c:set var='tag' value='${ fn:substring(tag, 1, fn:length(tag)) }' />
						</c:if>
						<input type="text" name="tag" value="${ tag }" style="width:100%">
						<p>* 태그는 콤마(,)로 구별하여 입력해주세요.</p>
					</td>
				</tr>
				<tr>
					<th>제품유형</th>
					<td>
						<c:forEach items="${ iconList }" var="row">
							<label><input type="checkbox" name="iconNo" value="${ row.iconNo }" ${ !empty row.pno ? "checked" : "" }><span>${ row.content }</span></label>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th>배송유형</th>
					<td>
						<label><input type="checkbox" name="coldYn" value="Y" ${ product.coldYn eq 'Y' ? "checked" : "" }><span>냉장배송</span></label>
						<label><input type="checkbox" name="routineYn" value="Y" ${ product.routineYn eq 'Y' ? "checked" : "" }><span>정기배송</span></label>
						<label><input type="checkbox" name="clinicPickup" value="Y" ${ product.clinicPickup eq 'Y' ? "checked" : "" }><span>병의원픽업</span></label>
					</td>
				</tr>
				<tr>
					<th>소분가능제품</th>
					<td>
						<label><input type="checkbox" name="subdivision" value="Y" ${ product.subdivision eq 'Y' ? "checked" : "" }><span>소분가능제품</span></label><br>
						1회 복용량 : <input type="number" name="dosage" value='${ product.dosage }'>
						1일 복용횟수 : <input type="number" name="doseCnt" value='${ product.doseCnt }'>
						복용방법(용법코드) : <select name="doseMethod" style="width:150px">
							<option value="ND" ${ product.doseMethod eq 'ND' ? 'selected' : '' }></option>
							<option value="DM" ${ product.doseMethod eq 'DM' ? 'selected' : '' }>오전</option>
							<option value="LMSM" ${ product.doseMethod eq 'LMSM' ? 'selected' : '' }>오후</option>
							<option value="HS" ${ product.doseMethod eq 'HS' ? 'selected' : '' }>자기전</option>
							<option value="QE1" ${ product.doseMethod eq 'QE1' ? 'selected' : '' }>공복/식전</option>
							<option value="DR03" ${ product.doseMethod eq 'DR03' ? 'selected' : '' }>식후</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>제품 이미지<sup>*</sup></th>
					<td>
						<div class="file">
							<strong>원본이미지</strong>
							<input type="file" name="orgImgFile" onchange="uploadImage('orgImgFile')">
							<p class="txt">* Image Size : 590*590(jpg) / Max 10Mbyte</p>
							<div id="orgImgDiv" style="display:${ !empty product.orgImg ? 'block' : 'none' }">
								<img src="${ product.orgImg }" id="orgImgTag">
								<a href="#" onclick="removeImg('orgImg'); return false;" class="btnSizeC">삭제</a>
							</div>
							<input type="hidden" name="orgImg" value="${ product.orgImg }">
						</div>
						<div class="file">
							<strong>대표이미지</strong>
							<input type="file" name="imgFile" onchange="uploadImage('imgFile')">
							<p class="txt">* Image Size : 590*590(jpg) / Max 10Mbyte</p>
							<div id="imgDiv" style="display:${ !empty product.img ? 'block' : 'none' }">
								<img src="${ product.img }" id="imgTag">
								<a href="#" onclick="removeImg('img'); return false;" class="btnSizeC">삭제</a>
							</div>
							<input type="hidden" name="img" value="${ product.img }">
						</div>
						<div class="file">
							<strong>이미지(1)</strong>
							<input type="file" name="img1File" onchange="uploadImage('img1File')">
							<p class="txt">* Image Size : 590*590(jpg) / Max 10Mbyte</p>
							<div id="img1Div" style="display:${ !empty product.img1 ? 'block' : 'none' }">
								<img src="${ product.img1 }" id="img1Tag">
								<a href="#" onclick="removeImg('img1'); return false;" class="btnSizeC">삭제</a>
							</div>
							<input type="hidden" name="img1" value="${ product.img1 }">
						</div>
						<div class="file">
							<strong>이미지(2)</strong>
							<input type="file" name="img2File" onchange="uploadImage('img2File')">
							<p class="txt">* Image Size : 590*590(jpg) / Max 10Mbyte</p>
							<div id="img2Div" style="display:${ !empty product.img2 ? 'block' : 'none' }">
								<img src="${ product.img2 }" id="img2Tag">
								<a href="#" onclick="removeImg('img2'); return false;" class="btnSizeC">삭제</a>
							</div>
							<input type="hidden" name="img2" value="${ product.img2 }">
						</div>
						<div class="file">
							<strong>이미지(3)</strong>
							<input type="file" name="img3File" onchange="uploadImage('img3File')">
							<p class="txt">* Image Size : 590*590(jpg) / Max 10Mbyte</p>
							<div id="img3Div" style="display:${ !empty product.img3 ? 'block' : 'none' }">
								<img src="${ product.img3 }" id="img3Tag">
								<a href="#" onclick="removeImg('img3'); return false;" class="btnSizeC">삭제</a>
							</div>
							<input type="hidden" name="img3" value="${ product.img3 }">
						</div>
						<div class="file">
							<strong>이미지(4)</strong>
							<input type="file" name="img4File" onchange="uploadImage('img4File')">
							<p class="txt">* Image Size : 590*590(jpg) / Max 10Mbyte</p>
							<div id="img4Div" style="display:${ !empty product.img4 ? 'block' : 'none' }">
								<img src="${ product.img4 }" id="img4Tag">
								<a href="#" onclick="removeImg('img4'); return false;" class="btnSizeC">삭제</a>
							</div>
							<input type="hidden" name="img4" value="${ product.img4 }">
						</div>
						<div class="file">
							<strong>제형이미지</strong>
							<input type="file" name="img5File" onchange="uploadImage('img5File')">
							<p class="txt">* Image Size : 590*590(jpg) / Max 10Mbyte</p>
							<div id="img5Div" style="display:${ !empty product.img5 ? 'block' : 'none' }">
								<img src="${ product.img5 }" id="img5Tag">
								<a href="#" onclick="removeImg('img5'); return false;" class="btnSizeC">삭제</a>
							</div>
							<input type="hidden" name="img5" value="${ product.img5 }">
						</div>
					</td>
				</tr>
				<tr>
					<th>제품상세내용<sup>*</sup><br>(PC)</th>
					<td>
						<textarea name="pcDesc" id="pcDesc" style="width:100%;height:100px">${ product.pcDesc }</textarea>
					</td>
				</tr>
				<tr>
					<th>제품상세내용<sup>*</sup><br>(Mobile)</th>
					<td>
						<textarea name="moDesc" id="moDesc" style="width:100%;height:100px">${ product.moDesc }</textarea>
					</td>
				</tr>
				<tr>
					<th>증정품</th>
					<td>
						<div id="giftDiv">
						<c:forEach items="${ giftList }" var="row">
							<p style="margin-bottom:5px">
								<a href="#" onclick="removeCate(this); return false;" class="btnSizeC">삭제</a>
								&nbsp;&nbsp;${ row.pname }
								<input type="hidden" name="giftPno" value="${ row.giftPno }">
							</p>
						</c:forEach>
						</div>
						<a href="#" onclick="showPopup('/popup/gift', '1024', '800'); return false" class="btnSizeA btnTypeD">추가</a>
					</td>
				</tr>
			</table>
		</div>

		<h2>추천 섭취 대상
			<span class="fr">
				<a href="#" onclick="showPopup('/popup/intake', '500', '600'); return false" class="btnSizeA btnTypeD">추가</a>
			</span></h2>
		<div class="">
			<table class="list">
				<colgroup>
					<col width="">
					<col width="100px">
					<col width="100px">
				</colgroup>
				<thead>
					<tr>
						<th>내용</th>
						<th>순서</th>
						<th>관리</th>
					</tr>
				</thead>
				<tbody id="intakeList">
					<c:forEach items="${ intakeList }" var="row">
						<tr>
							<td class='al'>
								<c:out value="${ row.content }" />
								<input type='hidden' name='intakeNo' value='${ row.intakeNo }'>
							</td>
							<td>
								<input type='button' class='btnUp' value='위로'>
								<input type='button' class='btnDown' value='아래로'>
							</td>
							<td><input type='button' class='btnSizeC' onclick='removeIntake(this); return false;' value='삭제'></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<h2>복용 안내
			<span class="fr">
				<a href="#" onclick="showPopup('/popup/dose', '500', '600'); return false" class="btnSizeA btnTypeD">추가</a>
			</span></h2>
		<div class="">
			<table class="list">
				<colgroup>
					<col width="">
					<col width="100px">
					<col width="100px">
				</colgroup>
				<thead>
					<tr>
						<th>내용</th>
						<th>순서</th>
						<th>관리</th>
					</tr>
				</thead>
				<tbody id="doseList">
					<c:forEach items="${ doseList }" var="row">
						<tr>
							<td class='al'>
								<c:out value="${ row.content }" />
								<input type='hidden' name='doseNo' value='${ row.doseNo }'>
							</td>
							<td>
								<input type='button' class='btnUp' value='위로'>
								<input type='button' class='btnDown' value='아래로'>
							</td>
							<td><input type='button' class='btnSizeC' onclick='removeIntake(this); return false;' value='삭제'></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<h2>영양/기능 정보
			<span class="fr">
				<a href="#" onclick="showPopup('/popup/nutrition', '500', '600'); return false" class="btnSizeA btnTypeD">추가</a>
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
						<th>영양성분</th>
						<th>기능</th>
						<th>함량</th>
						<th>영양성분 기준치(1일)</th>
						<th>순서</th>
						<th>관리</th>
					</tr>
				</thead>
				<tbody id="nutritionList">
					<c:forEach items="${ nutritionList }" var="row">
						<tr>
							<td class='al'>
								<c:out value="${ row.nutritionName }" />
								<input type='hidden' name='nutritionNo' value='${ row.nutritionNo }'>
							</td>
							<td class="al">
								<c:out value="${ row.func }" />
							</td>
							<td>
								<input type="text" name="nutritionContent${ row.nutritionNo }" value="${ row.content }" style="width:100px">
							</td>
							<td>
								<input type="text" name="nutritionStandard${ row.nutritionNo }" value="${ row.standard }" style="width:100px"> <c:out value="${ row.unitName }" />
							</td>
							<td>
								<input type='button' class='btnUp' value='위로'>
								<input type='button' class='btnDown' value='아래로'>
							</td>
							<td><input type='button' class='btnSizeC' onclick='removeNutrition(this); return false;' value='삭제'></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<h2>옵션 제품
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
						<th>제품명</th>
						<th>옵션명</th>
						<th>판매가</th>
						<th>순서</th>
						<th>관리</th>
					</tr>
				</thead>
				<tbody id="optionList">
					<c:forEach items="${ optionList }" var="row">
						<tr>
							<td>
								<c:out value="${ row.optPno }" />
								<input type='hidden' name='optPno' value='${ row.optPno }'>
							</td>
							<td class="al">
								<c:out value="${ row.pname }" />
							</td>
							<td>
								<input type="text" name="optNm${ row.optPno }" value="${ row.optNm }" style="width:100%">
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

		<h2>제품 필수정보 등록
			<span class="fr">
				<a href="#" onclick="showPopup('/popup/productInfo', '1024', '800'); return false" class="btnSizeA btnTypeD">가져오기</a>
			</span></h2>
		<div class="">
			<table class="list">
				<colgroup>
					<col width="30%">
					<col width="">
				</colgroup>
				<thead>
					<tr>
						<th>유형</th>
						<th>내용</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>식품의 유형</th>
						<td>
							<input type="text" name="info1" value="${ product.info1 }" style="width:100%" maxlength="500">
						</td>
					</tr>
					<tr>
						<th>생산자 및 소재지, 수입품의 경우 수입자를 함께 포기</th>
						<td>
							<input type="text" name="info2" value="${ product.info2 }" style="width:100%" maxlength="500">
						</td>
					</tr>
					<tr>
						<th>제조연월일, 유통기한 또는 품질유지기한</th>
						<td>
							<input type="text" name="info3" value="${ product.info3 }" style="width:100%" maxlength="500">
						</td>
					</tr>
					<tr>
						<th>포장단위별 용량(중량), 수량</th>
						<td>
							<input type="text" name="info4" value="${ product.info4 }" style="width:100%" maxlength="500">
						</td>
					</tr>
					<tr>
						<th>원재료명 및 함량</th>
						<td>
							<input type="text" name="info5" value="${ product.info5 }" style="width:100%" maxlength="500">
						</td>
					</tr>
					<tr>
						<th>영양 및 기능정보</th>
						<td>
							<input type="text" name="info6" value="${ product.info6 }" style="width:100%" maxlength="500">
						</td>
					</tr>
					<tr>
						<th>섭취량, 섭취방법 및 섭취 시 주의사항</th>
						<td>
							<input type="text" name="info7" value="${ product.info7 }" style="width:100%" maxlength="500">
						</td>
					</tr>
					<tr>
						<th>질병의 예방 및 치료를 위한 의약품이 아니라는 내용의 표현</th>
						<td>
							<input type="text" name="info8" value="${ product.info8 }" style="width:100%" maxlength="500">
						</td>
					</tr>
					<tr>
						<th>유전자재조합식품에 해당하는 경우의 표시</th>
						<td>
							<input type="text" name="info9" value="${ product.info9 }" style="width:100%" maxlength="500">
						</td>
					</tr>
					<tr>
						<th>표시광고 사전 심의필</th>
						<td>
							<input type="text" name="info10" value="${ product.info10 }" style="width:100%" maxlength="500">
						</td>
					</tr>
					<tr>
						<th>수입식품에 해당하는 경우 “건강기능식품에 관한 법률에 따른 수입신고를 필함”의 문구</th>
						<td>
							<input type="text" name="info11" value="${ product.info11 }" style="width:100%" maxlength="500">
						</td>
					</tr>
					<tr>
						<th>소비자상담 관련 전화번호</th>
						<td>
							<input type="text" name="info12" value="${ product.info12 }" style="width:100%" maxlength="500">
						</td>
					</tr>
					<tr>
						<th>기타</th>
						<td>
							<input type="text" name="info13" value="${ product.info13 }" style="width:100%" maxlength="500">
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<h2>기본 가격정책</h2>
		<div class="">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>SAP 단가</th>
					<td>
						<input type="text" name="netpr" value="${ product.netpr }" style="width:100px;text-align:right" readonly> 원
					</td>
				</tr>
				<tr>
					<th>판매가격<sup>*</sup></th>
					<td>
						<input type="text" name="salePrice" value="${ product.salePrice }" style="width:100px;text-align:right" maxlength="10"> 원
					</td>
				</tr>
				<tr>
					<th>병의원출하가(공급가)<sup>*</sup></th>
					<td>
						<input type="text" name="supplyPrice" value="${ product.supplyPrice }" style="width:100px;text-align:right" maxlength="10"> 원
					</td>
				</tr>
				<tr>
					<th>할인 구분</th>
					<td>
						<label><input type="radio" name="discountType" value="1" ${ mode eq 'create' || product.discountType eq 1 ? 'checked' : '' } onclick="setDiscountRate(1)"><span>정량</span></label>
						<label><input type="radio" name="discountType" value="2" ${ product.discountType eq 2 ? 'checked' : '' } onclick="setDiscountRate(2)"><span>정률</span></label>
					</td>
				</tr>
				<tr>
					<th>등급별 가격정책</th>
					<td>
						<table class="list" style="width:300px">
							<colgroup>
								<col width="150px">
								<col width="200px">
							</colgroup>
							<thead>
								<tr>
									<th>등급</th>
									<th>할인</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${ discountList }" var="row">
									<tr>
										<th><c:out value="${ row.gradeName }" /></th>
										<td>
											<input type="text" name="discount${ row.gradeNo }" value="${ row.discount }" style="width:100px;text-align:right" maxlength="10">
											<input type="hidden" name="discountGrade" value="${ row.gradeNo }">
											<span class="discount">원</span>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
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
					<td><fmt:formatDate value="${ product.cdate }" pattern="${ DateTimeFormat }" /> / ${ product.cuserId }</td>
					<th>수정일/수정자</th>
					<td><fmt:formatDate value="${ product.udate }" pattern="${ DateTimeFormat }" /> / ${ product.uuserId }</td>
				</tr>
			</table>
		</div>

		<div class="btnArea">
			<span class="fl">
				<a href="${ retrivePath }" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
			<c:if test="${ mode eq 'create' }">
				<a href="javascript:goSubmit()" class="btnTypeD btnSizeA"><span>등록</span></a>
			</c:if>
			<c:if test="${ mode eq 'modify' && updateAuth eq 'Y' }">
				<a href="javascript:goSubmit()" class="btnTypeD btnSizeA"><span>수정</span></a>
			</c:if>
		</div>
		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

<table style="display:none">
	<tbody id="nutritionRow">
		<tr>
			<td class="al">
				##NUTRITION_NAME##
				<input type='hidden' name='nutritionNo' value='##NUTRITION_NO##'>
			</td>
			<td class="al">
				##NUTRITION_FUNC##
			</td>
			<td>
				<input type="text" name="nutritionContent##NUTRITION_NO##" value="" style="width:100px">
			</td>
			<td>
				<input type="text" name="nutritionStandard##NUTRITION_NO##" value="##NUTRITION_STANDARD##" style="width:100px"> ##NUTRITION_UNIT_NAME##
			</td>
			<td>
				<input type='button' class='btnUp' value='위로'>
				<input type='button' class='btnDown' value='아래로'>
			</td>
			<td><input type='button' class='btnSizeC' onclick='removeNutrition(this); return false;' value='삭제'></td>
		</tr>
	</tbody>
</table>
<table style="display:none">
	<tbody id="optionRow">
		<tr>
			<td>
				##OPTION_PNO##
				<input type='hidden' name='optPno' value='##OPTION_PNO##'>
			</td>
			<td class="al">
				##OPTION_NM##
			</td>
			<td>
				<input type="text" name="optNm##OPTION_PNO##" value="##OPTION_NM##" style="width:100%">
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
		fCreator: "createSEditorInIFrame"
	});
</script>

</body>
</html>
