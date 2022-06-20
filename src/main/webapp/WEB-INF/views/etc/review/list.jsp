<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="utils" scope="request" class="com.pntmall.common.utils.Utils"/>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	$(function() {
		$("#fromDate").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		$("#toDate").datepicker({
			changeMonth: true,
		    changeYear: true
		});

		$("#resetBtn").click(function() {
			document.location.href = "list";
		});
	});

	function getCategory(cate){
		$.getJSON("getCate?cate=" + cate, function(result) {
			var obj = $("select[name='cate2']");
			$("option", obj).remove();
			obj.append($("<option value=''>전체</option>"));
			var cateList = result.param.cateList;
			for (var i=0; i<cateList.length; i++) {
				var optObj = $("<option></option>");
				optObj.val(cateList[i].cateNo).text(cateList[i].name);
				if(cateList[i].cateNo == "${param.cate2}"){
					optObj.attr("selected", true);
				}
				obj.append(optObj);
			}
		});
	}

</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="searchForm" id="searchForm">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>카테고리</th>
					<td>
						<select name="cate1" id="cate1" onchange="getCategory(this.value);" style="width:200px">
							<option value="" ${ empty param.cate1 ? 'selected' : '' }>전체</option>
							<c:forEach items="${ cateList1 }" var="row">
								<option value="${ row.cateNo }" ${ param.cate1 eq row.cateNo ? 'selected' : '' }>${ row.name }</option>
							</c:forEach>
						</select>
						<select name="cate2" id="cate2" style="width:200px">
						 	<option value="" ${ empty param.cate2 ? 'selected' : '' }>전체</option>
							<c:forEach items="${ cateList2 }" var="row">
								<option value="${ row.cateNo }" ${ param.cate2 eq row.cateNo ? 'selected' : '' }>${ row.name }</option>
							</c:forEach>
						</select>
					</td>
					<th>별점</th>
					<td>
						<select name="star" style="width:200px">
							<option value="" ${ empty param.star ? 'selected' : '' }>전체</option>
							<c:forEach var="i" begin="1" end="5" step="1">
								<option value="${ i }" ${ param.star eq i ? 'selected' : '' }>${ i }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>제품명</th>
					<td>
						<input type="text" name="pname" value="${ param.pname }" style="width:100%" >
					</td>
					<th>제품코드</th>
					<td>
						<input type="text" name="pno" value="${ param.pno }" style="width:100%" >
					</td>
				</tr>
				<tr>
					<th>등록자</th>
					<td>
						<input type="text" name="cuserName" value="${ param.cuserName }" style="width:100%" >
					</td>
					<th>등록일</th>
					<td>
						<div class="dateBox">
							<input type="text" name="fromDate" id="fromDate" readonly value="${ param.fromDate }">
						</div>
						<div class="dateBox">
							<input type="text" name="toDate" id="toDate" readonly value="${ param.toDate }">
						</div>
					</td>
				</tr>
				<tr>
					<th>구분(1)</th>
					<td>
						<label><input type="radio" name="type1" value="" ${ empty param.type1 ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="type1" value="1" ${ param.type1 eq '1' ? 'checked' : '' }><span>포토리뷰</span></label>
						<label><input type="radio" name="type1" value="2" ${ param.type1 eq '2' ? 'checked' : '' }><span>일반리뷰</span></label>
					</td>
					<th>구분(2)</th>
					<td>
						<label><input type="radio" name="type2" value="" ${ empty param.type2 ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="type2" value="2" ${ param.type2 eq '2' ? 'checked' : '' }><span>한달리뷰</span></label>
						<label><input type="radio" name="type2" value="1" ${ param.type2 eq '1' ? 'checked' : '' }><span>일반리뷰</span></label>
					</td>
				</tr>
				<tr>
					<th>베스트여부</th>
					<td>
						<label><input type="radio" name="bestYn" value="" ${ empty param.bestYn ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="bestYn" value="Y" ${ param.bestYn eq 'Y' ? 'checked' : '' }><span>Y</span></label>
						<label><input type="radio" name="bestYn" value="N" ${ param.bestYn eq 'N' ? 'checked' : '' }><span>N</span></label>
					</td>
					<th>메인노출여부</th>
					<td>
						<label><input type="radio" name="mainYn" value="" ${ empty param.mainYn ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="mainYn" value="Y" ${ param.mainYn eq 'Y' ? 'checked' : '' }><span>Y</span></label>
						<label><input type="radio" name="mainYn" value="N" ${ param.mainYn eq 'N' ? 'checked' : '' }><span>N</span></label>
					</td>
				</tr>
				<tr>
					<th>Dr.Pnt 이용후기 게시</th>
					<td>
						<label><input type="radio" name="reviewYn" value="" ${ empty param.reviewYn ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="reviewYn" value="Y" ${ param.reviewYn eq 'Y' ? 'checked' : '' }><span>Y</span></label>
						<label><input type="radio" name="reviewYn" value="N" ${ param.reviewYn eq 'N' ? 'checked' : '' }><span>N</span></label>
					</td>
					<th>댓글여부</th>
					<td>
						<label><input type="radio" name="commentYn" value="" ${ empty param.commentYn ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="commentYn" value="Y" ${ param.commentYn eq 'Y' ? 'checked' : '' }><span>Y</span></label>
						<label><input type="radio" name="commentYn" value="N" ${ param.commentYn eq 'N' ? 'checked' : '' }><span>N</span></label>
					</td>
				</tr>
				<tr>
					<th>제목</th>
					<td colspan=3>
						<input type="text" name="title" value="${ param.title }" style="width:100%" >
					</td>
				</tr>
			</table>
			<div class="btnArea ac">
				<input type="submit" class="btnTypeA btnSizeA" value="검색">
				<input type="button" class="btnTypeB btnSizeA" id="resetBtn" value="초기화">
			</div>
		</div>
		<div class="listHead">
			<div class="fl">
				<div class="totalArea">
					<strong class="totalTit">Total</strong> <span class="totalCount"><fmt:formatNumber value="${ count }" pattern="#,###" /></span>
				</div>
			</div>
			<p class="fr">
				<c:import url="/include/pageSize?pageSize=${ param.pageSize }" />
			</p>
		</div>
		</form>

		<table class="list">
			<colgroup>
				<col width="60px">
				<col width="">
				<col width="">
				<col width="">
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
					<th>별점</th>
					<th>구분(1)</th>
					<th>구분(2)</th>
					<th>제품명</th>
					<th>제목</th>
					<th>베스트여부</th>
					<th>댓글여부</th>
					<th>등록자</th>
					<th>등록일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.reviewNo }" /></td>
					<td>
						<c:forEach var="i" begin="1" end="${ row.star }" step="1">★</c:forEach>
					</td>
					<td><c:out value="${ row.type1Name }" /></td>
					<td><c:out value="${ row.type2Name }" /></td>
					<td><c:out value="${ row.pname }" /></td>
					<td><a href="edit?reviewNo=${ row.reviewNo }"><c:out value="${ row.title }" /></a></td>
					<td><c:out value="${ row.bestYn }" /></td>
					<td><c:out value="${ empty row.comment ? 'N':'Y' }" /></td>
					<td>${ utils.idMasking(row.cuserId) }(${ utils.nameMasking(row.cuserName) })</td>
					<td><fmt:formatDate value="${row.cdate}" pattern="${ DateTimeFormat }"/></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="paging "><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
