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
		$("#fromCdate").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		$("#toCdate").datepicker({
			changeMonth: true,
		    changeYear: true
		});

		$("#resetBtn").click(function() {
			document.location.href = "list";
		});
	});


	function secedeMemberPopUp(){

		if($("input[name=chkMemNo]:checked").length == 0){
			alert("선택된 항목이 없습니다.");
			return false;
		}

		var chkMemNos=[];
		$("input[name=chkMemNo]:checked").each(function(idx){
	    	// 해당 체크박스의 Value 가져오기
	        var value = $(this).val();
	        chkMemNos.push(value);
	        console.log("value:::",value) ;
      	});

		showPopup('secede?memNos='+chkMemNos.join(), '500', '600');
	}

	function secedeMember(secedeMemo){
		if($("input[name=chkMemNo]:checked").length == 0){
			alert("선택된 항목이 없습니다.");
			return false;
		}

		if(confirm("탈퇴 시 보유중인 회원혜택은 모두 삭제됩니다. \r\n정말로 탈퇴하시겠습니까? ")){
			disableScreen();
			$("#editForm").attr("action", "secedeMember");
			$("#secedeMemo").val(secedeMemo);

			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.reload();
				enableScreen();
				self.close();
			});
		}
	}

	function activeMember(){

		if($("input[name=chkMemNo]:checked").length == 0){
			alert("선택된 항목이 없습니다.");
			return false;
		}

		if(confirm("선택한 회원을 휴면 해제하시겠습니까?")){
			disableScreen();
			$("#editForm").attr("action", "activeMember");
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
					<th>회원ID</th>
					<td>
						<input type="text" name="memId" value="${ param.memId }" style="width:100%" >
					</td>
				</tr>
				<tr>
					<th>회원명</th>
					<td>
						<input type="text" name="name" value="${ param.name }" style="width:100%" >
					</td>
				</tr>
				<tr>
					<th>휴면회원 전환일</th>
					<td>
						<div class="dateBox">
							<input type="text" name="fromCdate" id="fromCdate" readonly value="${ param.fromCdate }">
						</div>
						<div class="dateBox">
							<input type="text" name="toCdate" id="toCdate" readonly value="${ param.toCdate }">
						</div>
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
				<input type="submit" class="btnTypeA btnSizeA" value="엑셀다운로드(추후)">
				<c:import url="/include/pageSize?pageSize=${ param.pageSize }" />
			</p>
		</div>
		</form>

		<form name="editForm" id="editForm" method="POST" action="">
		<input type="hidden" name="secedeRsn" id="secedeRsn" value="020008">
		<input type="hidden" name="secedeMemo" id="secedeMemo" value="">
		<table class="list">
			<colgroup>
				<col width="60px">
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
					<th><input type="checkbox" name="chkAll" id="chkAll"></th>
					<th>No</th>
					<th>회원ID</th>
					<th>회원명</th>
					<th>등급</th>
					<th>포인트</th>
					<th>가입일</th>
					<th>휴면회원 전환일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td>
						<input type="checkbox" name="chkMemNo" id="chkMemNo${ row.memNo }" value="${ row.memNo }">
					</td>
					<td><c:out value="${ row.memNo }" /></td>
					<td><a href="/member/general/edit?memNo=${ row.memNo }">${ utils.idMasking(row.memId) }</a></td>
					<td><a href="/member/general/edit?memNo=${ row.memNo }">${ utils.nameMasking(row.name) }</a></td>
					<td><c:out value="${ row.gradeName }" /></td>
					<td><c:out value="${ row.curPoint }" /></td>
					<td><fmt:formatDate value="${row.cdate}" pattern="${ DateTimeFormat }"/></td>
					<td><fmt:formatDate value="${row.sleepDate}" pattern="${ DateTimeFormat }"/></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		</form>
		<div class="btnArea">
			<a href="javascript:activeMember();" class="btnTypeC btnSizeA"><span>휴면회원 해제</span></a>
			<a href="javascript:secedeMemberPopUp();" class="btnTypeC btnSizeA"><span>관리자 선택탈퇴</span></a>
		</div>
		<div class="paging btnSide"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
