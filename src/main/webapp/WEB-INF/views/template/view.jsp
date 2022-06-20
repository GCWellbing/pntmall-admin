<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script type="text/javascript" src="/static/se2/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">
	$(function() {
		$("#fromDate").datepicker();
		$("#toDate").datepicker();
	});

	function uploadImage(field) {
		if($("input[name=" + field + "]").val() != '') {
	        disableScreen();

	        var action = "/upload/upload?field=" + field + "&path=image&fileType=image";
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

</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<form name="editForm" id="editForm" method="post" action="">
		<div id="location">
			<h1>마스터관리</h1>
			<ul>
				<li>제품관리</li>
				<li class="on">마스터관리</li>
			</ul>
		</div>
		<h2>마스터 상품정보</h2>
		<div class="white_box">
			<table class="board formType">
				<colgroup>
					<col width="15%">
					<col width="">
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>SAP코드</th>
					<td>111</td>
					<th>제품명</th>
					<td>222</td>
				</tr>
				<tr>
					<th>사업부</th>
					<td>333</td>
					<th>상품부</th>
					<td>444</td>
				</tr>
			</table>
		</div>

		<h2>기본 전시정보</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>상품종류</th>
					<td>
						<label><input type="radio" name="productType" checked><span>일반상품</span></label>
						<label><input type="radio" name="productType"><span>증정품</span></label>
					</td>
					<th>SAP 매출반영</th>
					<td>AA(매출주문)</td>
				</tr>
				<tr>
					<th>카테고리<sup>*</sup></th>
					<td>
						<p style="margin-bottom:5px">
							<a href="" class="btnSizeC">삭제</a>
							&nbsp;&nbsp;기능별 &gt; 면역력
						</p>
						<p style="margin-bottom:5px">
							<a href="" class="btnSizeC">삭제</a>
							&nbsp;&nbsp;기능별 &gt; 면역력
						</p>
						<a href="#" onclick="showPopup('/popup/addCategory', '450', '600'); return false" class="btnSizeA btnTypeD">추가</a>
					</td>
					<th>리뷰 노출여부</th>
					<td>
						<label><input type="radio" name="review" checked><span>노출</span></label>
						<label><input type="radio" name="review"><span>비노출</span></label>
					</td>
				</tr>
				<tr>
					<th>전시기간</th>
					<td>
						<div class="dateBox">
							<input type="text" id="fromDate" readonly>
						</div>
						<div class="dateBox">
							<input type="text" id="toDate" readonly>
						</div>
					</td>
					<th>전시여부 </th>
					<td>
						<label><input type="radio" name="open" checked><span>공개</span></label>
						<span style="margin-right:10px">(<label><input type="checkbox"><span>품절</span></label>)</span>
						<label><input type="radio" name="open"><span>비공개</span></label>
						<label><input type="radio" name="open"><span>단종</span></label>
					</td>
				</tr>
			</table>
		</div>

		<h2>상세 전시정보</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>상품종류</th>
					<td>
						<label><input type="radio" name="type" checked><span>메인상품</span></label>
						<label><input type="radio" name="type"><span>특별상품</span></label>
					</td>
				</tr>
				<tr>
					<th>제품명<sup>*</sup></th>
					<td>
						<input type="text" style="width:100%">
					</td>
				</tr>
				<tr>
					<th>용량<sup>*</sup></th>
					<td>
						<input type="text" style="width:200px">
						<span class="txt">ex) 100mg*60정(30g)</span><!-- 줄바꿈하는 텍스트는 p태그로  -->
					</td>
				</tr>
				<tr>
					<th>태그입력</th>
					<td>
						<input type="text" style="width:100%">
						<p class="txt">* 태그는 콤마(,)로 구별하여 입력해주세요.</p>
					</td>
				</tr>
				<tr>
					<th>제품대표설명<sup>*</sup></th>
					<td>
						<textarea style="width:100%;height:100px"></textarea>
					</td>
				</tr>
				<tr>
					<th>배송유형<sup>*</sup></th>
					<td>
						<label><input type="checkbox"><span>택배배송</span></label>
						<span style="margin-right:10px">(
							<label><input type="radio" name="deliveryType"><span>일반배송</span></label>
							<label><input type="radio" name="deliveryType"><span>냉장배송</span></label>
						)</span>
						<label><input type="checkbox"><span>정기배송</span></label>
						<label><input type="checkbox"><span>병의원픽업</span></label>
					</td>
				</tr>
				<tr>
					<th>제품 이미지<sup>*</sup></th>
					<td>
						<div class="file">
							<strong>원본이미지</strong>
							<input type="file" name="imgFile" onchange="uploadImage('imgFile')">
							<p class="txt">* Image Size : OOO X OOO(JPG) / Max 1Mbyte</p>
							<img src="" id="imgTag" style="display:none">
							<input type="hidden" name="img" value="">
						</div>
						<div class="file">
							<strong>대표이미지</strong>
							<input type="file">
							<p class="txt">* Image Size : OOO X OOO(JPG) / Max 1Mbyte</p>
						</div>
						<div class="file">
							<strong>이미지(1)</strong>
							<input type="file">
							<a href="" class="btnSizeC">삭제</a>
							<p class="txt">* Image Size : OOO X OOO(JPG) / Max 1Mbyte</p>
						</div>
						<a href="" class="btnSizeA btnTypeD">이미지 추가</a>
					</td>
				</tr>
				<tr>
					<th>editor</th>
					<td>
						<textarea name="editor" id="editor" style="width:100%;height:100px"></textarea>
					</td>
				</tr>
			</table>
		</div>

		<h2>메뉴권한</h2>
		<div class="white_box">
			<table class="board">
				<tr><td>
					<label><input type="checkbox"><span>전체선택</span></label>
				</td></tr>
				<tr><td>
					<ul class="menuTree">
						<li>
							<label><input type="checkbox"><span>Menu1</span></label>
							<ul>
								<li>
									<label><input type="checkbox"><span>Menu1_1</span></label>
									<ul>
										<li><label><input type="checkbox"><span>Menu1_1_1</span></label></li>
										<li><label><input type="checkbox"><span>Menu1_1_2</span></label></li>
										<li><label><input type="checkbox"><span>Menu1_1_3</span></label></li>
									</ul>
								</li>
								<li>
									<label><input type="checkbox"><span>Menu1_2</span></label>
								</li>
								<li>
									<label><input type="checkbox"><span>Menu1_3</span></label>
									<ul>
										<li><label><input type="checkbox"><span>Menu1_3_1</span></label></li>
										<li><label><input type="checkbox"><span>Menu1_3_2</span></label></li>
										<li><label><input type="checkbox"><span>Menu1_3_3</span></label></li>
									</ul>
								</li>
							</ul>
						</li>
						<li>
							<label><input type="checkbox"><span>Menu2</span></label>
							<ul>
								<li>
									<label><input type="checkbox"><span>Menu2_1</span></label>
									<ul>
										<li><label><input type="checkbox"><span>Menu2_1_1</span></label></li>
										<li><label><input type="checkbox"><span>Menu2_1_2</span></label></li>
										<li><label><input type="checkbox"><span>Menu2_1_3</span></label></li>
									</ul>
								</li>
								<li>
									<label><input type="checkbox"><span>Menu2_2</span></label>
									<ul>
										<li><label><input type="checkbox"><span>Menu2_2_1</span></label></li>
										<li><label><input type="checkbox"><span>Menu2_2_2</span></label></li>
										<li><label><input type="checkbox"><span>Menu2_2_3</span></label></li>
									</ul>
								</li>
							</ul>
						</li>
					</ul><!-- //menuTree -->
				</td></tr>
			</table>
		</div>

		<div class="btnArea">
			<a href="" class="btnTypeC btnSizeA"><span>전시상품 등록</span></a>
		</div>

		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
					<col width="10%">
					<col width="">
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>공개여부<sup>*</sup></th>
					<td>
						<label><input type="radio" name=""><span>공개</span></label>
						<label><input type="radio" name=""><span>비공개</span></label>
					</td>
					<th>등록일/등록자</th>
					<td>2021.01.01 /  gcwb</td>
					<th>수정일/수정자</th>
					<td>2021.01.01 /  gcwb</td>
				</tr>
			</table>
		</div>

		<div class="btnArea">
			<span class="fl">
				<a href="" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
			<a href="" class="btnTypeB btnSizeA"><span>삭제</span></a>
			<a href="" class="btnTypeD btnSizeA"><span>수정</span></a>
		</div>
		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

<script type="text/javascript">
	var oEditors = [];

	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "editor",
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
