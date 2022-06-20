<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="utils" scope="request" class="com.pntmall.common.utils.Utils"/>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script type="text/javascript">
	function download(filename, textInput) {
		location.href = "/upload/downloadFile?attach="+textInput+"&attachOrgName="+filename;
	}
</script>

</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<h2>기본정보</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>회원ID</th>
					<td>
						${ clinic.memId }
					</td>
				</tr>
			</table>
		</div>

		<h2>정산 정보</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>병의원 사업자정보</th>
					<td>
						대표자명 : ${ clinic.businessOwner }<br>
						병의원명 : ${ clinic.businessName }<br>
						사업자등록증 :
						<div id="docuImg">
							<c:set var="docuImgChk" value="N"/>
							<c:forEach items="${ clinicImgList }" var="row">
								<c:if test="${ row.gubun eq 'docu' }">
									<c:set var="docuImgChk" value="Y"/>
								</c:if>
							</c:forEach>

							<c:forEach items="${ clinicImgList }" var="row">
								<c:if test="${ row.gubun eq 'docu' }">
									<div>
										<input type='hidden' name='attach' value='${row.attach}'>
										<input type='hidden' name='attachOrgName' value='${row.attachOrgName}'>
										<input type='hidden' name='gubun' value='${row.gubun}'>
										<input type='button' class='btnSizeC' onclick='download("${row.attachOrgName}","${row.attach}"); return false;' value='${row.attachOrgName}'>
									</div>
								</c:if>
							</c:forEach>
						</div>
						<br>
						사업자번호 : ${ clinic.businessNo }<br>
						업종 : ${ clinic.businessItem }<br>
						업태 : ${ clinic.businessType }<br>
					</td>
				</tr>
				<tr>
					<th>건기식 사업자정보</th>
					<td>
						대표자명 : ${ clinic.businessOwner2 }<br>
						업체명 : ${ clinic.businessName2 }<br>
						사업자등록증 :
						<div id="healthImg">
							<c:set var="healthImgChk" value="N"/>
							<c:forEach items="${ clinicImgList }" var="row">
								<c:if test="${ row.gubun eq 'health' }">
									<c:set var="healthImgChk" value="Y"/>
								</c:if>
							</c:forEach>

							<c:forEach items="${ clinicImgList }" var="row">
								<c:if test="${ row.gubun eq 'health' }">
									<div>
										<input type='hidden' name='attach' value='${row.attach}'>
										<input type='hidden' name='attachOrgName' value='${row.attachOrgName}'>
										<input type='hidden' name='gubun' value='${row.gubun}'>
										<input type='button' class='btnSizeC' onclick='download("${row.attachOrgName}","${row.attach}"); return false;' value='${row.attachOrgName}'>
									</div>
								</c:if>
							</c:forEach>
						</div>
						<br>
						사업자번호 : ${ clinic.businessNo2 }<br>
						업종 : ${ clinic.businessItem }<br>
						업태 : ${ clinic.businessType }<br>
					</td>
				</tr>
				<tr>
					<th>입금계좌</th>
					<td>
						계좌번호 :
								<c:set var="bankName" value=""/>
								<c:forEach items="${ bankList }" var="row">
									<c:if test="${ row.code1+=row.code2 eq clinic.bank  }">
										<c:set var="bankName" value="${row.name}"/>
									</c:if>
								</c:forEach>
							    ${ bankName } ${ clinic.account }<br>
						예금주명 : ${ clinic.depositor }
								<input type="checkbox" name="depositorNot" id="depositorNot" value="Y" ${ clinic.depositorNot eq 'Y'?'checked':'' }>대표자 예금주 불일치 확인<br>
						통장 사본 :
						<div id="bankImg">
							<c:set var="bankImgChk" value="N"/>
							<c:forEach items="${ clinicImgList }" var="row">
								<c:if test="${ row.gubun eq 'bank' }">
									<c:set var="bankImgChk" value="Y"/>
								</c:if>
							</c:forEach>

							<c:forEach items="${ clinicImgList }" var="row">
								<c:if test="${ row.gubun eq 'bank' }">
									<div>
										<input type='hidden' name='attach' value='${row.attach}'>
										<input type='hidden' name='attachOrgName' value='${row.attachOrgName}'>
										<input type='hidden' name='gubun' value='${row.gubun}'>
										<input type='button' class='btnSizeC' onclick='download("${row.attachOrgName}","${row.attach}"); return false;' value='${row.attachOrgName}'>
									</div>
								</c:if>
							</c:forEach>
						</div>
						<br>
					</td>
				</tr>
				<tr>
					<th>계약서</th>
					<td>
						<div id="agreeImg">
							<c:forEach items="${ clinicImgList }" var="row">
								<c:if test="${ row.gubun eq 'agree' }">
									<div>
										<input type='hidden' name='attach' value='${row.attach}'>
										<input type='hidden' name='attachOrgName' value='${row.attachOrgName}'>
										<input type='hidden' name='gubun' value='${row.gubun}'>
										<input type='button' class='btnSizeC' onclick='download("${row.attachOrgName}","${row.attach}"); return false;' value='${row.attachOrgName}'>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</td>
				</tr>
			</table>
		</div>

		<div class="btnArea">
			<span class="fl">
				<a href="${ retrivePath }" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
		</div>

	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->


</body>
</html>
