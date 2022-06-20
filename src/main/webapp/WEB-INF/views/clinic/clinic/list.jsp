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
		$("#fromCdateJoin").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		$("#toCdateJoin").datepicker({
			changeMonth: true,
		    changeYear: true
		});

		$("#resetBtn").click(function() {
			document.location.href = "list";
		});

		$("#approveAll").change(function() {
	       if($("#approveAll").is(":checked")){
	    		$("input:checkbox[name=approveArr]").prop("checked", true);
	        }else{
	    		$("input:checkbox[name=approveArr]").prop("checked", false);
	        }
		});

		$("#chkAll").change(function(){
	        if($("#chkAll").is(":checked")){
	            $("input[name=chkMemNo]").prop("checked", true);

	        }else{
	            $("input[name=chkMemNo]").prop("checked", false);
	        }
	    });
	});

	function setStatus(approve){
		if($("input[name=chkMemNo]:checked").length == 0){
			alert("선택된 항목이 없습니다.");
			return false;
		}

		var chkApproveRst = true;
		if(approve == "006002"){
			$("[id^='chkMemNo']:checked").each(function(){
				var k = $(this).attr('id').replace("chkMemNo","");
				if(!($("#chkApprove"+k).val() == "006001" || $("#chkApprove"+k).val() == "006005" || $("#chkApprove"+k).val() == "006007"  )){
					chkApproveRst = false;
				}
			});
		}

		if(!chkApproveRst){
			alert("가입 대기, 업데이트 요청, 비활성화 인 건만 승인(활성) 가능합니다.");
			return false;
		}

		$("#approve").val(approve);

		var pop_title = "popupOpener";

		showPopupTarget("", "500", "600", pop_title);
        var frmData = document.editForm ;
        frmData.target = pop_title ;
        frmData.action = "statusPop" ;

        frmData.submit() ;
	}

	function goExcel() {
		$("#searchForm").attr("action", "excel");
		$("#searchForm").submit();
		$("#searchForm").attr("action", "");
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
					<th>병의원코드</th>
					<td>
						<input type="text" name="clinicId" value="${ param.clinicId }" style="width:100%" >
					</td>
					<th>사업자번호</th>
					<td>
						<input type="text" name="businessNo" value="${ param.businessNo }" style="width:100%" >
					</td>
				</tr>
				<tr>
					<th>병의원명/업체명</th>
					<td>
						<input type="text" name="clinicName" value="${ param.clinicName }" style="width:100%" >
					</td>
					<th>건기식 사업자 여부</th>
					<td>
						<label><input type="radio" name="business2Yn" value="" ${ param.business2Yn eq '' ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="business2Yn" value="Y" ${ param.business2Yn eq 'Y' ? 'checked' : '' }><span>Y</span></label>
						<label><input type="radio" name="business2Yn" value="N" ${ param.business2Yn eq 'N' ? 'checked' : '' }><span>N</span></label>
					</td>
				</tr>
				<tr>
					<th>가입승인여부</th>
					<td colspan=3>
						<input type="checkbox" name="approveAll" value="ALL" id="approveAll"  ${ param.approveAll eq 'ALL' ? 'checked' : '' }>전체&nbsp;
						<c:forEach items="${ approveList }" var="row">
							<c:if test="${ row.code2  ne '004' }">
								<input type="checkbox" name="approveArr" value="${ row.code1 }${ row.code2 }" ${ fn:contains(approveArr, row.code1+=row.code2)  ? 'checked' : '' }>${ row.name }&nbsp;
							</c:if>
						</c:forEach>

					</td>
				</tr>
				<tr>
					<th>가입승인일</th>
					<td>
						<div class="dateBox">
							<input type="text" name="fromCdateJoin" id="fromCdateJoin" readonly value="${ param.fromCdateJoin }">
						</div>
						<div class="dateBox">
							<input type="text" name="toCdateJoin" id="toCdateJoin" readonly value="${ param.toCdateJoin }">
						</div>
					</td>
					<th>전시여부</th>
					<td>
						<label><input type="radio" name="dispYn" value="" ${ empty param.dispYn ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="dispYn" value="Y" ${ param.dispYn eq 'Y' ? 'checked' : '' }><span>공개</span></label>
						<label><input type="radio" name="dispYn" value="N" ${ param.dispYn eq 'N' ? 'checked' : '' }><span>비공개</span></label>
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
				<a href="javascript:goExcel()" class="btnTypeC btnSizeA"><span>엑셀 다운로드</span></a>
				<c:import url="/include/pageSize?pageSize=${ param.pageSize }" />
			</p>
		</div>
		</form>

		<table class="list">
		<form name="editForm" id="editForm" method="POST" action="statusPop">
		<input type="hidden" name="approve" id="approve" value="">


			<colgroup>
				<col width="60px">
				<col width="60px">
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
					<th><input type="checkbox" name="chkAll" id="chkAll"></th>
					<th>No</th>
					<th>병의원ID</th>
					<th>병의원명</th>
					<th>업체명</th>
					<th>건기식 사업자번호</th>
					<th>병의원 사업자번호</th>
					<th>회원상태</th>
					<th>가입신청일</th>
					<th>업데이트일</th>
					<th>전시여부</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ list }" var="row">
				<tr>
					<td>
						<input type="checkbox" name="chkMemNo" id="chkMemNo${ row.memNo }" value="${ row.memNo }">
						<input type="hidden" name="chkApprove" id="chkApprove${ row.memNo }" value="${ row.approve }">
					</td>
					<td><c:out value="${ row.memNo }" /></td>
					<td><a href="edit?memNo=${ row.memNo }"><c:out value="${ row.memId }" /></a></td>
					<td><a href="edit?memNo=${ row.memNo }">${ row.clinicName }</a></td>
					<td>${ row.businessName2 }</td>
					<td><c:out value="${ row.businessNo2 }" /></td>
					<td><c:out value="${ row.businessNo }" /></td>
					<td><c:out value="${ row.approveName }" /></td>
					<td><fmt:formatDate value="${row.cdate}" pattern="${ DateTimeFormat }"/></td>
					<td><fmt:formatDate value="${row.udate}" pattern="${ DateTimeFormat }"/></td>
					<td><c:out value="${ row.dispYn }" /></td>
				</tr>
				</c:forEach>
			</tbody>
		</form>
		</table>
		<div class="btnArea">
			<a href="javascript:setStatus('006007');" class="btnTypeC btnSizeA"><span>비활성</span></a>
		</div>
		<div class="paging btnSide"><!-- btnArea와 동시에 노출될때만 btnSide 추가 -->
			${ paging }
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
