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
		$("#fromUdate").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		$("#toUdate").datepicker({
			changeMonth: true,
		    changeYear: true
		});

		$("#resetBtn").click(function() {
			document.location.href = "list";
		});

		$("#chkAll").change(function(){
	        if($("#chkAll").is(":checked")){
	            $("input[name=chkMemNo]").prop("checked", true);

	        }else{
	            $("input[name=chkMemNo]").prop("checked", false);
	        }
	    });
	});


	function deleteMember(){

		if($("input[name=chkMemNo]:checked").length == 0){
			alert("선택된 항목이 없습니다.");
			return false;
		}

		if(confirm("선택한 탈퇴 내역을 삭제하시겠습니까?\r삭제된 탈퇴 내역은 복구가 불가능합니다")){
			disableScreen();
			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.reload();
				enableScreen();
				self.close();
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
		<form name="searchForm" id="searchForm">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>회원구분</th>
					<td>
						<label><input type="radio" name="clinicYn" value="" ${ param.clinicYn eq '' ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="clinicYn" value="N" ${ param.clinicYn eq 'N' ? 'checked' : '' }><span>일반회원</span></label>
						<label><input type="radio" name="clinicYn" value="Y" ${ param.clinicYn eq 'Y' ? 'checked' : '' }><span>병의원회원</span></label>
					</td>
				</tr>
				<tr>
					<th>회원ID</th>
					<td>
						<input type="text" name="memId" value="${ param.memId }" style="width:100%" >
					</td>
				</tr>
				<tr>
					<th>탈퇴일</th>
					<td>
						<div class="dateBox">
							<input type="text" name="fromUdate" id="fromUdate" readonly value="${ param.fromUdate }">
						</div>
						<div class="dateBox">
							<input type="text" name="toUdate" id="toUdate" readonly value="${ param.toUdate }">
						</div>
					</td>
				</tr>
				<tr>
					<th>탈퇴유형</th>
					<td>
						<label><input type="radio" name="seceType" value="" ${ param.seceType eq '' ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="seceType" value="owener" ${ param.seceType eq 'owener' ? 'checked' : '' }><span>본인탈퇴</span></label>
						<label><input type="radio" name="seceType" value="manager" ${ param.seceType eq 'manager' ? 'checked' : '' }><span>관리자삭제</span></label>
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
				<c:import url="/include/pageSize?pageSize=${ adminSearch.pageSize }" />
			</p>
		</div>
		</form>

		<form name="editForm" id="editForm" method="POST" action="deleteMember">
		<table class="list">
			<colgroup>
				<col width="60px">
				<col width="60px">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
				<col width="">
			</colgroup>
			<thead>
				<tr>
					<th><input type="checkbox" name="chkAll" id="chkAll"></th>
					<th>No</th>
					<th>회원구분</th>
					<th>회원ID</th>
					<th>탈퇴유형</th>
					<th>탈퇴일</th>
					<th>탈퇴처리자</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td>
						<input type="checkbox" name="chkMemNo" id="chkMemNo${ row.memNo }" value="${ row.memNo }">
					</td>
					<td><c:out value="${ row.memNo }" /></td>
					<td><a href="edit${ row.clinicYn}?memNo=${ row.memNo }">${ row.clinicYn eq 'Y' ? '병의원':'일반' }</a></td>
					<td><a href="edit${ row.clinicYn}?memNo=${ row.memNo }">${ utils.idMasking(row.memId) }</a></td>
					<td><c:out value="${ row.secedeRsn eq '020008' ? '관리자삭제':'본인탈퇴' }" /></td>
					<td><fmt:formatDate value="${row.udate}" pattern="${ DateTimeFormat }"/></td>
					<td>
						<c:if test="${ row.secedeRsn eq '020008' }">
							${ utils.idMasking(row.uuserId) } (${ utils.nameMasking(row.uuserName) })
						</c:if>
						<c:if test="${ row.secedeRsn ne '020008' }">
							본인 탈퇴
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		</form>

		<div class="btnArea">
			<a href="javascript:deleteMember();" class="btnTypeC btnSizeA"><span>선택 삭제</span></a>
		</div>
		<div class="paging btnSide"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
