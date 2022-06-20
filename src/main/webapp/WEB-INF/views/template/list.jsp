<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<div id="location">
			<h1>마스터관리</h1>
			<ul>
				<li>제품관리</li>
				<li class="on">마스터관리</li>
			</ul>
		</div>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>브랜드</th>
					<td>
						<select name="" style="width:calc(50% - 5px)">
							<option value=""></option>
							<option value=""></option>
						</select>
					</td>
					<th>카테고리</th>
					<td>
						<select name="" style="width:calc(50% - 5px)">
							<option value=""></option>
							<option value=""></option>
						</select>
						<select name="" style="width:calc(50%)">
							<option value=""></option>
							<option value=""></option>
						</select>
						
					</td>
				</tr>
				<tr>
					<th>상품종류</th>
					<td>
						<select name="" style="width:calc(50% - 5px)">
							<option value=""></option>
							<option value=""></option>
						</select>
						
					</td>
					<th>배송종류</th>
					<td>
						<select name="" style="width:calc(50% - 5px)">
							<option value=""></option>
							<option value=""></option>
						</select>
					</td>
				</tr>
				<tr>
					<th>제품코드</th>
					<td>
						<input type="text" style="width:100%">
					</td>
					<th>SAP코드</th>
					<td>
						<input type="text" style="width:100%">
					</td>
				</tr>
				<tr>
					<th>제품명</th>
					<td>
						<input type="text" style="width:100%">
					</td>
					<th>전시여부</th>
					<td>
						<label><input type="radio" name="active" checked><span>전체</span></label>
						<label><input type="radio" name="active"><span>공개</span></label>
						<label><input type="radio" name="active"><span>비공개</span></label>
						<label><input type="radio" name="active"><span>단종</span></label>
					</td>
				</tr>
			</table>			
			<div class="btnArea ac">
				<input type="submit" class="btnTypeA btnSizeA" value="검색">
				<input type="button" class="btnTypeB btnSizeA" value="초기화">
			</div>
		</div>
		<div class="listHead">
			<div class="fl">
				<div class="totalArea">
					<strong class="totalTit">Total</strong> <span class="totalCount">1,554</span>
				</div>
			</div>
			<p class="fr">
				<input type="button" class="btnTypeD btnSizeB" value="공개">
				<input type="button" class="btnTypeC btnSizeB" value="비공개">
				<select name="" style="width:100px;">
					<option value="30">20 view</option>
					<option value="50">50 view</option>
					<option value="100">100 view</option>
				</select>
			</p>
		</div>

		<table class="list">
			<colgroup>
				<col width="30px">
				<col width="60px">
				<col width="">
				<col width="">
				<col width="">
				<col width="30%">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th><label><input type="checkbox"><span></span></label></th>
					<th>No</th>
					<th>브랜드</th>
					<th>제품코드</th>
					<th>SAP코드</th>
					<th>제품명</th>
					<th>상품종류</th>
					<th>판매가</th>
					<th>재고</th>
					<th>전시여부</th>
					<th>등록일</th>
				</tr>
			</thead>
			<tbody>						
				<tr>
					<td><label><input type="checkbox"><span></span></label></td>
					<td>11</td>
					<td>Dr. PNT</td>
					<td>123</td>
					<td>12345</td>
					<td class="al"><a href="view">제품명</a></td>
					<td>일반상품</td>
					<td>80,000</td>
					<td>202026</td>
					<td>비공개</td>
					<td>2012-12-12</td>
				</tr>
			</tbody>
		</table>
		<div class="btnArea">
			<a href="" class="btnTypeC btnSizeA"><span>등록</span></a>
		</div>
		<div class="paging btnSide"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			<img src="/static/images/pgPrev2_disabled.gif">
			<img src="/static/images/pgPrev_disabled.gif">
			<strong>1</strong>
			<a href="">2</a>
			<a href="">3</a>
			<a href="">4</a>
			<a href="">5</a>
			<a href=""><img src="/static/images/pgNext.gif" alt="다음 페이지"></a>
			<a href=""><img src="/static/images/pgNext2.gif" alt="다음 10페이지"></a>			
		</div>
		
		<div class="listHead">
			<h3 class="fl">추천 섭취 대상</h3>
			<p class="fr">
				<input type="button" class="btnTypeD btnSizeB" value="전체 삭제">
				<input type="button" class="btnTypeC btnSizeB" value="추천 섭취 대상 등록">
			</p>
		</div>
		
		<table class="list">
			<colgroup>
				<col width="60">
				<col width="">
				<col width="100">
				<col width="100">
			</colgroup>
			<thead>
				<tr>
					<th>No</th>
					<th>내용</th>
					<th>순서</th>
					<th>관리</th>
				</tr>
			</thead>
			<tbody>						
				<tr>
					<td>1</td>
					<td class="al">채소나 과일 섭취가 부족하신 분</td>
					<td>
						<input type="button" class="btnDown" value="아래로">
						<input type="button" class="btnUp" value="위로">
					</td>
					<td><input type="button" class="btnTypeD btnSizeB" value="삭제"></td>
				</tr>
			</tbody>
		</table>
		
		<a href="" class="btnTypeA btnSizeA"><span>버튼</span></a>
		<a href="" class="btnTypeB btnSizeB"><span>버튼</span></a>
		<a href="" class="btnTypeC btnSizeC"><span>버튼</span></a>
		<a href="" class="btnTypeD btnSizeD"><span>버튼</span></a>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
