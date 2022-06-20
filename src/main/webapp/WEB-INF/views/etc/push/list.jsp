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
		$("#sdate").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		$("#edate").datepicker({
			changeMonth: true,
		    changeYear: true
		});

		$("#resetBtn").click(function() {
			document.location.href = "list?bestYn=Y";
		});
	});

	function goSubmit() {

		disableScreen();
		ef.proc($("#editForm"), function(result) {
			alert(result.message);
			if(result.succeed) location.reload();
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
		<form name="searchForm" id="searchForm">
		 	<input type="hidden" name="bestYn" value="Y">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>제목</th>
					<td>
						<input type="text" name="title" value="${ param.title }" style="width:100%" >
					</td>
					<th>진행사항</th>
					<td>
						<select name="status" style="width:200px">
							<option value=""></option>
							<option value="10" ${ param.status eq 10 ? 'selected' : '' }>작성중</option>
							<option value="20" ${ param.status eq 20 ? 'selected' : '' }>대기중</option>
							<option value="30" ${ param.status eq 30 ? 'selected' : '' }>발송완료</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>등록일</th>
					<td>
						<div class="dateBox">
							<input type="text" name="sdate" id="sdate" readonly value="${ param.sdate }">
						</div> ~ 
						<div class="dateBox">
							<input type="text" name="edate" id="edate" readonly value="${ param.edate }">
						</div>
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
					<th>수행번호</th>
					<th>제목</th>
					<th>등록일</th>
					<th>수행시각</th>
					<th>상태</th>
					<th>대상</th>
					<th>발송완료</th>
					<th>발송에러</th>
					<th>확인</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row" varStatus="status">
					<tr>
						<td><c:out value="${ row.pno }" /></td>
						<td><a href="edit?pno=${ row.pno }"><c:out value="${ row.title }" /></a></td>
						<td><fmt:formatDate value="${ row.cdate }" pattern="${ DateTimeFormat }"/></td>
						<td><c:out value="${ row.sendDate }" /></td>
						<td>
							<c:choose>
								<c:when test='${ row.status eq 10 }'>
									작성중
								</c:when>
								<c:when test='${ row.status eq 20 }'>
									대기중
								</c:when>
								<c:when test='${ row.status eq 30 }'>
									발송완료
								</c:when>
							</c:choose>
						</td>
						<td>
							<c:if test='${ row.status eq 30 }'>
								<fmt:formatNumber value="${ row.iosCnt + row.androidCnt }" pattern="#,###" /><br>
								(IOS : <fmt:formatNumber value="${ row.iosCnt }" pattern="#,###" /> /
								Android : <fmt:formatNumber value="${ row.androidCnt }" pattern="#,###" />)
							</c:if>
						</td>
						<td>
							<c:if test='${ row.status eq 30 }'>
								<fmt:formatNumber value="${ row.iosSuccess + row.androidSuccess }" pattern="#,###" /><br>
								(IOS : <fmt:formatNumber value="${ row.iosSuccess }" pattern="#,###" /> /
								Android : <fmt:formatNumber value="${ row.androidSuccess }" pattern="#,###" />)
							</c:if>
						</td>
						<td>
							<c:if test='${ row.status eq 30 }'>
								<fmt:formatNumber value="${ row.iosFail + row.androidFail }" pattern="#,###" /><br>
								(IOS : <fmt:formatNumber value="${ row.iosFail }" pattern="#,###" /> /
								Android : <fmt:formatNumber value="${ row.androidFail }" pattern="#,###" />)
							</c:if>
						</td>
						<td>
							<c:if test='${ row.status eq 30 }'>
								<fmt:formatNumber value="${ row.iosOpenCnt + row.androidOpenCnt }" pattern="#,###" /><br>
								(IOS : <fmt:formatNumber value="${ row.iosOpenCnt }" pattern="#,###" /> /
								Android : <fmt:formatNumber value="${ row.androidOpenCnt }" pattern="#,###" />)
							</c:if>
						</td>
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

