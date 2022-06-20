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
		$("#resetBtn").click(function() {
			document.location.href = "list";
		});
	});
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
					<th>브랜드</th>
					<td>
						<select name="brand" style="width:100%">
							<option value=""></option>
							<c:forEach items="${ brandList }" var="row">
								<option value="${ row.code1 }${ row.code2 }" ${ row.code1.concat(row.code2) eq param.brand ? 'selected' : '' }>${ row.name }</option>
							</c:forEach>
						</select>
					</td>
					<th>카테고리</th>
					<td>
						<select name="cateNo" style="width:100%">
							<option value=""></option>
							<c:forEach items="${ categoryList }" var="row">
								<c:if test="${ row.pcateNo ne 0 }">
									<option value="${ row.cateNo }" ${ row.cateNo eq param.cateNo ? 'selected' : '' }>${ row.pname } &gt; ${ row.name }</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>배송종류</th>
					<td>
						<select name="deliveryType" style="width:100%">
							<option value=""></option>
							<option value="1" ${ param.deliveryType eq '1' ? 'selected' : '' }>냉장배송</option>
							<option value="2" ${ param.deliveryType eq '2' ? 'selected' : '' }>정기배송</option>
							<option value="3" ${ param.deliveryType eq '3' ? 'selected' : '' }>병의원픽업</option>
						</select>
					</td>
					<th>제품코드</th>
					<td>
						<input type="text" name="pno" value="${ param.pno }" style="width:100%" >
					</td>
				</tr>
				<tr>
					<th>SAP 코드</th>
					<td>
						<input type="text" name="matnr" value="${ param.matnr }" style="width:100%" >
					</td>
					<th>제품명</th>
					<td>
						<input type="text" name="pname" value="${ param.pname }" style="width:100%">
					</td>
				</tr>
				<tr>
					<th>상태</th>
					<td>
						<label><input type="radio" name="status" value="" ${ empty param.status || param.status eq '' ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="status" value="S" ${ param.status eq 'S' ? 'checked' : '' }><span>공개</span></label>
						<label><input type="radio" name="status" value="H" ${ param.status eq 'H' ? 'checked' : '' }><span>비공개</span></label>
						<label><input type="radio" name="status" value="E" ${ param.status eq 'E' ? 'checked' : '' }><span>단종</span></label>
					</td>
					<th></th>
					<td></td>
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
				<col width="100px">
				<col width="80px">
				<col width="">
				<col width="80px">
				<col width="80px">
				<col width="80px">
				<col width="80px">
				<col width="100px">
			</colgroup>
			<thead>
				<tr>
					<th>제품코드</th>
					<th>SAP코드</th>
					<th>제품명</th>
					<th>판매가</th>
					<th>재고</th>
					<th>전시순서</th>
					<th>전시여부</th>
					<th>등록일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">						
				<tr>
					<td><a href="edit?pno=${ row.pno }"><c:out value="${ row.pno }" /></a></td>
					<td><c:out value="${ row.matnr }" /></td>
					<td><a href="edit?pno=${ row.pno }"><c:out value="${ row.pname }" /></a></td>
					<td><fmt:formatNumber value="${ row.salePrice }" pattern="#,###" /></td>
					<td><fmt:formatNumber value="${ row.labst }" pattern="#,###" /></td>
					<td><fmt:formatNumber value="${ row.rank }" pattern="#,###" /></td>
					<td><c:out value="${ row.statusName }" /></td>
					<td><fmt:formatDate value="${ row.cdate }" pattern="${ DateTimeFormat }" /></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
			<a href="edit" class="btnTypeC btnSizeA"><span>등록</span></a>
		</div>
		<div class="paging btnSide"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
