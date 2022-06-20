<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zip').value = data.zonecode;
                document.getElementById("addr1").value = roadAddr;

                // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
                if(roadAddr !== ''){
                    document.getElementById("addr2").value = extraRoadAddr;
                } else {
                    document.getElementById("addr2").value = '';
                }

            }
        }).open();
    }
</script>
<script>
	$(function() {

		$.validator.addMethod("notDefaultText", function (value, element) {
			   if (value == $(element).attr('placeholder')) {
			      return false;
			   } else {
			       return true;
			   }
			});

		$("#editForm").validate({
			ignore: [],
			rules: {
				name: { required: true,
	                notDefaultText: true
	            },
				memId: { required: true,
	                notDefaultText: true
	            },
	            passwd: { required: true,
	                notDefaultText: true
		        },
		        clinicName: { required: true,
		            notDefaultText: true
		        },
		        zip: { required: true,
		            notDefaultText: true
		        },
		        addr1: { required: true,
		            notDefaultText: true
		        },
		        addr2: { required: true,
		            notDefaultText: true
		        },
	            tel1: { required: true,
	                notDefaultText: true
	            },
	            tel2: { required: true,
	                notDefaultText: true,
	                number: true
	            },
	            mtel1: { required: true,
	                notDefaultText: true
	            },
	            mtel2: { required: true,
	                notDefaultText: true,
	                number: true
	            },
	            email: { required: true,
	                notDefaultText: true
	            },
	            subject: { required: true,
		            notDefaultText: true
		        },
		        businessOwner: { required: true,
		            notDefaultText: true
		        },
		        businessName: { required: true,
		            notDefaultText: true
		        },
		        businessItem: { required: true,
		            notDefaultText: true
		        },
		        businessType: { required: true,
		            notDefaultText: true
		        },
		        businessOwner2: { required: true,
		            notDefaultText: true
		        },
		        businessName2: { required: true,
		            notDefaultText: true
		        },
		        businessNo2: { required: true,
		            notDefaultText: true
		        },
		        businessItem2: { required: true,
		            notDefaultText: true
		        },
		        businessType2: { required: true,
		            notDefaultText: true
		        },
		        bank: { required: true,
		            notDefaultText: true
		        },
		        account: { required: true,
		            notDefaultText: true
		        },
		        depositor: { required: true,
		            notDefaultText: true
		        }

			},
			messages :{
				name : {
					required : "이름(원장명)을 입력하세요.",
					notDefaultText : "이름(원장명)을 입력하세요."
				},
				memId : {
					required : "ID를 입력하세요.",
					notDefaultText : "ID를 입력하세요."
				},
				passwd : {
					required : "비밀번호를 입력하세요.",
					notDefaultText : "비밀번호를 입력하세요."
				},
				clinicName : {
					required : "병의원명을 입력하세요.",
					notDefaultText : "병의원명을 입력하세요."
				},
				zip : {
					required : "우편번호를 입력하세요.",
					notDefaultText : "우편번호를 선택하세요."
				},
				addr2 : {
					required : "주소를 입력하세요.",
					notDefaultText : "주소를 선택하세요."
				},
				tel1 : {
					required : "전화번호를 선택하세요.",
					notDefaultText : "휴대폰번호를 선택하세요."
				},
				tel2 : {
					required : "전화번호를 입력하세요.",
					notDefaultText : "휴대폰번호를 입력하세요.",
					number: "휴대폰번호를 입력하세요.number"
				},
				mtel1 : {
					required : "휴대폰번호를 선택하세요.",
					notDefaultText : "휴대폰번호를 선택하세요."
				},
				mtel2 : {
					required : "휴대폰번호를 입력하세요.",
					notDefaultText : "휴대폰번호를 입력하세요.",
					number: "휴대폰번호를 입력하세요.number"
				},
				email : {
					required : "이메일를 입력하세요.",
					notDefaultText : "이메일를 입력하세요."
				},
				subject : {
					required : "진료과목을 입력하세요.",
					notDefaultText : "진료과목을 입력하세요."
				},
				businessOwner : {
					required : "병의원 대표자명을 입력하세요.",
					notDefaultText : "병의원 대표자명을 입력하세요."
				},
				businessName : {
					required : "병의원명을 입력하세요.",
					notDefaultText : "병의원명을 입력하세요."
				},
				businessItem : {
					required : "업종을 입력하세요.",
					notDefaultText : "업종을 입력하세요."
				},
				businessType : {
					required : "업태를 입력하세요.",
					notDefaultText : "업태를 입력하세요."
				},
				businessOwner2 : {
					required : "건강기능식품 대표자명을 입력하세요.",
					notDefaultText : "건강기능식품 대표자명을 입력하세요."
				},
				businessName2 : {
					required : "건강기능식품 업체명을 입력하세요.",
					notDefaultText : "건강기능식품 업체명을 입력하세요."
				},
				businessNo2 : {
					required : "건강기능식품 사업자(주민)번호를 입력하세요.",
					notDefaultText : "건강기능식품 사업자(주민)번호를 입력하세요."
				},
				businessItem2 : {
					required : "건강기능식품 업종을 입력하세요.",
					notDefaultText : "건강기능식품 업종을 입력하세요."
				},
				businessType2 : {
					required : "건강기능식품 업태를 입력하세요.",
					notDefaultText : "건강기능식품 업태를 입력하세요."
				},
				account : {
					required : "계좌번호를 입력하세요.",
					notDefaultText : "계좌번호를 입력하세요."
				},
				depositor: {
					required : "예금주명을 입력하세요.",
					notDefaultText : "예금주명을 입력하세요."
				}

			},
			errorPlacement: function(error, element) {
				// error.insertAfter(element);
				// error.css({"margin":"0 0 0 0px", "color":"red"});
			},
			invalidHandler: function(form, validator) {
				alert(validator.errorList[0].message);
				validator.errorList[0].element.focus();
			}
		});

		chkRadio();
		$("input").change(function(){
			chkRadio();
		});

	});

	function chkRadio(){
		var reservationYn = $("input[name=reservationYn]:checked").val();
		var katalkYn = $("input[name=katalkYn]:checked").val();
		var divisionYn = $("input[name=divisionYn]:checked").val();
		var pickupYn = $("input[name=pickupYn]:checked").val();

		if(katalkYn=='Y'){
			$("#katalkId").show();
		}else{
			$("#katalkId").val("");
			$("#katalkId").hide();
		}

	}

	function goSubmit() {

		if (!$("#editForm").valid()){
			return false;
		}

		if(!chkValidEmail()){
			return false;
		}

		//카카오 ID check
		if($("input[name=katalkYn]:checked").val()=='Y'){
			if( $("#katalkId").val() == "" || $("#katalkId").val() == "채널 URL 입력" ){
				alert("카카오 상담 가능이면 채널 URL을 입력하셔야 합니다." );
				$("#katalkId").focus();
				return false;
			}
		}

		//알림설정 check
		var reservationYn = $("input[name=reservationYn]:checked").val();
		var katalkYn = $("input[name=katalkYn]:checked").val();
		var divisionYn = $("input[name=divisionYn]:checked").val();
		var pickupYn = $("input[name=pickupYn]:checked").val();

		if( $("#alarmTel2").val() == ""){
			alert("알림설정 연락처를 입력하셔야 합니다." );
			$("#alarmTel2").focus();
			return false;
		}

		//건기식 대표자 : 예금주명 일치 여부 확인
		if($("#depositor").val() == $("#businessOwner2").val() ){
			if($("#depositorNot").is(":checked") ){
				alert("건강기능식품 대표자와 예금주명이 같으므로 대표자 예금주 불일치 확인을 선택해제하여 주세요!" );
				$("#depositorNot").focus();
				return false;
			}
		}else{
			if(!$("#depositorNot").is(":checked") ){
				alert("건강기능식품 대표자와 예금주명이 다르면 대표자 예금주 불일치 확인을  선택하여 주세요!" );
				$("#depositorNot").focus();
				return false;
			}
		}
		//사업자번호 검증
		if(isNaN($("#businessNo").val())){
			alert("사업자번호는 숫자만 입력 가능합니다.");
			return false;
		}

		if($("#businessNo").val().length != 10){
			alert("사업자번호를 10자 입력하세요.");
			return false;
		}

		//사업자번호 검증
		if(isNaN($("#businessNo2").val())){
			alert("건강기능식품 사업자번호는 숫자만 입력 가능합니다.");
			return false;
		}

		if($("#businessNo2").val().length != 10){
			alert("건강기능식품 사업자번호를 10자 입력하세요.");
			return false;
		}

		var chkDocu = false;
		var chkHealth = false;
		var chkBank = false;
		var chkAgree = false;
		$("input[name=gubun]").each(function(idx){
	        var gubunValue = $("input[name=gubun]:eq(" + idx + ")").val() ;
	        if(gubunValue == "docu") chkDocu = true;
	        if(gubunValue == "health") chkHealth = true;
	        if(gubunValue == "bank") chkBank = true;
	        if(gubunValue == "agree") chkAgree = true;
	    });
		if(!chkDocu){
			alert("병의원 사업자등록증을 등록해 주세요");
			return false;
		}
		if(!chkHealth){
			alert("건강기능식품 사업자등록증을 등록해 주세요");
			return false;
		}
		if(!chkBank){
			alert("통장사본을 등록해 주세요");
			return false;
		}
		if(!chkAgree){
			alert("계약서를 등록해 주세요");
			return false;
		}
		disableScreen();
		ef.proc($("#editForm"), function(result) {
			alert(result.message);
			if(result.succeed) location.reload();
			enableScreen();
		});
	}

	function goSubmitJoin() {
		if($("input[name=approve]:checked").val() == "" || $("input[name=approve]:checked").val() === undefined){
			alert("회원 상태를 선택하여 주세요");
			return false;
		}
		if($("#reason").val() == ""){
			alert("사유를 입력하여 주세요");
			return false;
		}


		disableScreen();
		ef.proc($("#editFormJoin"), function(result) {
			alert(result.message);
			if(result.succeed) location.reload();
			enableScreen();
		});
	}

	function uploadImage(field) {
		var fileName = $("input[name=" + field + "]").val()
		var orgFilename = fileName.split('/').pop().split('\\').pop();

		if($("input[name=" + field + "]").val() != '') {
	        disableScreen();

	        var action = "/upload/upload?field=" + field + "&path=clinic&fileType=image";
	        ef.multipart($("#editForm"), action, field, function(result) {

	        	if(result.succeed) {
	        		var fieldName = field.replace(/File/gi, '');
	        		var gubun = fieldName.replace(/Img/gi, '');

	        		var html = "";
					html += "<div>";
					html += "	<img src='"+result.param.uploadUrl+"'>";
					html += "	<input type='hidden' name='attach' value='"+result.param.uploadUrl+"'>";
					html += "	<input type='hidden' name='attachOrgName' value='"+orgFilename+"'>";
					html += "	<input type='hidden' name='gubun' value='"+gubun+"'>";
					html += "	<input type='button' class='btnSizeC' onclick='removeClinicImg(this); return false;' value='삭제'>";
					html += "</div>";
					$("#"+fieldName).append(html);
					if(field == "doctorImgFile"){
						$("#"+field).hide();
						$("#"+field+"Txt").hide();
					}
	        	} else {
	        		alert(result.message);
	        	}

	        	$("input[name=" + field + "]").val("");
	        	enableScreen();
	        });
		}
	}

	function uploadImageAll(field) {
		var fileName = $("input[name=" + field + "]").val()
		var orgFilename = fileName.split('/').pop().split('\\').pop();

		if($("input[name=" + field + "]").val() != '') {
	        disableScreen();

	        var action = "/upload/upload?field=" + field + "&path=clinic&fileType=all";
	        ef.multipart($("#editForm"), action, field, function(result) {

	        	if(result.succeed) {
	        		var fieldName = field.replace(/File/gi, '');
	        		var gubun = fieldName.replace(/Img/gi, '');

	        		var html = "";
					html += "<div>";
					html += "	<input type='hidden' name='attach' value='"+result.param.uploadUrl+"'>";
					html += "	<input type='hidden' name='attachOrgName' value='"+orgFilename+"'>";
					html += "	<input type='button' class='btnSizeC' onclick='download(\""+orgFilename+"\",\""+result.param.uploadUrl+"\"); return false;' value='"+orgFilename+"'>";
					html += "	<input type='hidden' name='gubun' value='"+gubun+"'>";
					html += "	<input type='button' class='btnSizeC' onclick='removeClinicImg2(this); return false;' value='삭제'>";
					html += "</div>";
					$("#"+fieldName).append(html);
					$("#"+field).hide();
					$("#"+field+"Txt").hide();
	        	} else {
	        		alert(result.message);
	        	}

	        	$("input[name=" + field + "]").val("");
	        	enableScreen();
	        });
		}
	}

	function removeClinicImg(obj) {
		var field = $(obj).parent().parent().closest('td').attr('id')
		if(field == "doctorImg"){
			$("#"+field+"File").show();
			$("#"+field+"FileTxt").show();
		}
		$(obj).parent().remove();
	}

	function removeClinicImg2(obj) {
		var field = $(obj).parent().parent().closest('div').attr('id')
		$("#"+field+"File").show();
		$("#"+field+"FileTxt").show();
		$(obj).parent().remove();
	}

	function download(filename, textInput) {
		location.href = "/upload/downloadFile?attach="+textInput+"&attachOrgName="+filename;
	}

	function validateEmail(sEmail) {
		var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
		if (filter.test(sEmail)) {
			return true;
		} else {
			return false;
		}
	}

	function chkValidEmail(){
		var sEmail = $('#email').val();
		console.log(sEmail);
		if (validateEmail(sEmail)) {
		    return true;
		} else {
			alert("이메일 형식을 확인해주세요.");
		    return false;
		}
	}

</script>
</head>
<body>
<div id="wrapper">
<c:import url="/include/header" />
	<div id="container">
		<c:import url="/include/location" />
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="memNo" value="${ clinic.memNo}" />
			<input type="hidden" name="emailYnOld" id="emailYnOld" value="${clinic.emailYn }">
			<input type="hidden" name="smsYnOld" id="smsYnOld" value="${clinic.smsYn }">
		<h2>회원상태</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>구분</th>
					<td>
						${ clinic.approveName }(${ clinic.reason })
					</td>
				</tr>
			</table>
		</div>

		<h2>병의원 회원 정보(필수)</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>이름</th>
					<td colspan=3>
						<input type="text" name="name" id="name" value="${ clinic.name }" style="width:200px">
					</td>
				</tr>
				<tr>
					<th>병의원명</th>
					<td>
						<input type="text" name="clinicName" id="clinicName" value="${ clinic.clinicName }" style="width:200px">
					</td>
					<th>병의원ID</th>
					<td>
						${ clinic.memId  }
					</td>
				</tr>
				<tr>
					<th>병의원 사업자 정보</th>
					<td colspan=3>
						병의원명 : ${ clinic.businessName }<br>
						사업자번호 :${ clinic.businessNo }<br>
						대표자명 : ${ clinic.businessOwner }
					</td>
				</tr>
				<tr>
					<th>건기식 사업자 정보</th>
					<td colspan=3>
						병의원명 : ${ clinic.businessName2 }<br>
						사업자번호 :${ clinic.businessNo2 }<br>
						대표자명 : ${ clinic.businessOwner2 }
					</td>
				</tr>
				<tr>
					<th>SAP 고객코드</th>
					<td>
						<input type="text" name="clinicSellCd" id="clinicSellCd" value="${ clinic.clinicSellCd }" style="width:200px">
					</td>
					<th>SAP 공급업체코드</th>
					<td>
						<input type="text" name="clinicBuyCd" id="clinicBuyCd" value="${ clinic.clinicBuyCd }" style="width:200px">
					</td>
				</tr>
				<tr>
					<th>위치(위도/경도)</th>
					<td colspan=3>
						위도<input type="text" name="latitude" id="latitude" value="${ clinic.latitude }" style="width:200px">
						경도<input type="text" name="longitude" id="longitude" value="${ clinic.longitude }" style="width:200px">
					</td>
				</tr>
				<tr>
					<th>병의원 주소</th>
					<td colspan=3>
						<input type="text" name="zip" id="zip" value="${ clinic.zip }" style="width:200px">
						<input type="button" onclick="sample4_execDaumPostcode()" value="우편번호 찾기" class="btnSizeA btnTypeD">
						<input type="text" name="addr1" id="addr1" value="${ clinic.addr1 }" style="width:300px">
						<input type="text" name="addr2" id="addr2" value="${ clinic.addr2 }" style="width:300px">
					</td>
				</tr>
				<tr>
					<th>병의원 전화번호<br>(대표전화번호)</th>
					<td colspan=3>
						<select  name="tel1" id="tel1" class="defaultSelect" style="width:100px">
							<c:forEach items="${ telList }" var="row">
								<option value="${ row.name }" ${ row.name eq clinic.tel1 ? 'selected':''  }>${ row.name }</option>
							</c:forEach>
						</select>
						<input type="text" name="tel2" id="tel2" value="${ clinic.tel2 }" style="width:200px" maxlength="8" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
					</td>
				</tr>
				<tr>
					<th>휴대폰 번호</th>
					<td colspan=3>
						<select  name="mtel1" id="mtel1" class="defaultSelect" style="width:100px">
							<c:forEach items="${ mtelList }" var="row">
								<option value="${ row.name }">${ row.name }</option>
							</c:forEach>
						</select>
						<input type="text" name="mtel2" id="mtel2" value="${ clinic.mtel2 }" style="width:200px" maxlength="8" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
						수신여부(
						<label><input type="radio" name="smsYn" value="Y" ${ clinic.smsYn eq 'Y' ? 'checked' : '' }><span>동의</span></label>
						<label><input type="radio" name="smsYn" value="N" ${ clinic.smsYn ne 'Y' ? 'checked' : '' }><span>미동의</span></label>)
					</td>
				</tr>
				<tr>
					<th>이메일 주소</th>
					<td colspan=3>
						<input type="text" name="email" id="email" value="${ clinic.email }" style="width:200px">
						수신여부(
						<label><input type="radio" name="emailYn" value="Y" ${ clinic.emailYn eq 'Y' ? 'checked' : '' }><span>동의</span></label>
						<label><input type="radio" name="emailYn" value="N" ${ clinic.emailYn ne 'Y' ? 'checked' : '' }><span>미동의</span></label>)
					</td>
				</tr>
				<tr>
					<th>운영시간</th>
					<td colspan=3>
						<c:set var="monShTmp" value="${empty clinic.monSh ? '9':clinic.monSh }" />
						<c:set var="monSmTmp" value="${empty clinic.monSm ? '00':clinic.monSm }" />
						<c:set var="monEhTmp" value="${empty clinic.monEh ? '18':clinic.monEh }" />
						<c:set var="monEmTmp" value="${empty clinic.monEm ? '00':clinic.monEm }" />
						월요일:<select name="monSh" id="monSh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq monShTmp ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="monSm" id="monSm" style="width:100px">
					    		<option value="00"  ${'00' eq monSmTmp ? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq monSmTmp ? 'selected':''}>30</option>
							 </select> ~
							 <select name="monEh" id="monEh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq monEhTmp ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="monEm" id="monEm" style="width:100px">
					    		<option value="00"  ${'00' eq monEmTmp ? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq monEmTmp ? 'selected':''}>30</option>
							 </select>
							 <input type="checkbox" name="monClose" value="Y" ${ clinic.monClose eq 'Y' ? 'checked' :'' }>휴진<br>
						<c:set var="tueShTmp" value="${empty clinic.tueSh ? '9':clinic.tueSh }" />
						<c:set var="tueSmTmp" value="${empty clinic.tueSm ? '00':clinic.tueSm }" />
						<c:set var="tueEhTmp" value="${empty clinic.tueEh ? '18':clinic.tueEh }" />
						<c:set var="tueEmTmp" value="${empty clinic.tueEm ? '00':clinic.tueEm }" />
						화요일:<select name="tueSh" id="tueSh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq tueShTmp ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="tueSm" id="tueSm" style="width:100px">
					    		<option value="00"  ${'00' eq tueSmTmp ? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq tueSmTmp ? 'selected':''}>30</option>
							 </select> ~
							 <select name="tueEh" id="tueEh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq tueEhTmp ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="tueEm" id="tueEm" style="width:100px">
					    		<option value="00"  ${'00' eq tueEhTmp? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq tueEhTmp ? 'selected':''}>30</option>
							 </select>
							 <input type="checkbox" name="tueClose" value="Y" ${ clinic.tueClose eq 'Y' ? 'checked' :'' }>휴진<br>
						<c:set var="wedShTmp" value="${empty clinic.wedSh ? '9':clinic.wedSh }" />
						<c:set var="wedSmTmp" value="${empty clinic.wedSm ? '00':clinic.wedSm }" />
						<c:set var="wedEhTmp" value="${empty clinic.wedEh ? '18':clinic.wedEh }" />
						<c:set var="wedEmTmp" value="${empty clinic.wedEm ? '00':clinic.wedEm }" />
						수요일:<select name="wedSh" id="wedSh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq wedShTmp ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="wedSm" id="wedSm" style="width:100px">
					    		<option value="00"  ${'00' eq wedSmTmp ? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq wedSmTmp ? 'selected':''}>30</option>
							 </select> ~
							 <select name="wedEh" id="wedEh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq wedEhTmp ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="wedEm" id="wedEm" style="width:100px">
					    		<option value="00"  ${'00' eq wedEmTmp ? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq wedEmTmp ? 'selected':''}>30</option>
							 </select>
							 <input type="checkbox" name="wedClose" value="Y" ${ clinic.wedClose eq 'Y' ? 'checked' :'' }>휴진<br>
						<c:set var="thuShTmp" value="${empty clinic.thuSh ? '9':clinic.thuSh }" />
						<c:set var="thuSmTmp" value="${empty clinic.thuSm ? '00':clinic.thuSm }" />
						<c:set var="thuEhTmp" value="${empty clinic.thuEh ? '18':clinic.thuEh }" />
						<c:set var="thuEmTmp" value="${empty clinic.thuEm ? '00':clinic.thuEm }" />
						목요일:<select name="thuSh" id="thuSh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq thuShTmp ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="thuSm" id="thuSm" style="width:100px">
					    		<option value="00"  ${'00' eq thuSmTmp ? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq thuSmTmp ? 'selected':''}>30</option>
							 </select> ~
							 <select name="thuEh" id="thuEh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq thuEhTmp ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="thuEm" id="thuEm" style="width:100px">
					    		<option value="00"  ${'00' eq thuEmTmp ? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq thuEmTmp ? 'selected':''}>30</option>
							 </select>
							 <input type="checkbox" name="thuClose" value="Y" ${ clinic.thuClose eq 'Y' ? 'checked' :'' }>휴진<br>
						<c:set var="friShTmp" value="${empty clinic.friSh ? '9':clinic.friSh }" />
						<c:set var="friSmTmp" value="${empty clinic.friSm ? '00':clinic.friSm }" />
						<c:set var="friEhTmp" value="${empty clinic.friEh ? '18':clinic.friEh }" />
						<c:set var="friEmTmp" value="${empty clinic.friEm ? '00':clinic.friEm }" />
						금요일:<select name="friSh" id="friSh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq friShTmp ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="friSm" id="friSm" style="width:100px">
					    		<option value="00"  ${'00' eq friSmTmp ? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq friSmTmp ? 'selected':''}>30</option>
							 </select> ~
							 <select name="friEh" id="friEh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq friEhTmp ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="friEm" id="friEm" style="width:100px">
					    		<option value="00"  ${'00' eq friEmTmp ? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq friEmTmp ? 'selected':''}>30</option>
							 </select>
							 <input type="checkbox" name="friClose" value="Y" ${ clinic.friClose eq 'Y' ? 'checked' :'' }>휴진<br>
						<c:set var="satShTmp" value="${empty clinic.satSh ? '9':clinic.satSh }" />
						<c:set var="satSmTmp" value="${empty clinic.satSm ? '00':clinic.satSm }" />
						<c:set var="satEhTmp" value="${empty clinic.satEh ? '18':clinic.satEh }" />
						<c:set var="satEmTmp" value="${empty clinic.satEm ? '00':clinic.satEm }" />
						토요일:<select name="satSh" id="satSh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq satShTmp ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="satSm" id="satSm" style="width:100px">
					    		<option value="00"  ${'00' eq satSmTmp ? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq satSmTmp ? 'selected':''}>30</option>
							 </select> ~
							 <select name="satEh" id="satEh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq satEhTmp ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="satEm" id="satEm" style="width:100px">
					    		<option value="00"  ${'00' eq satEmTmp ? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq satEmTmp ? 'selected':''}>30</option>
							 </select>
							 <input type="checkbox" name="satClose" value="Y" ${ clinic.satClose eq 'Y' ? 'checked' :'' }>휴진<br>
						<c:set var="sunCloseTmp" value="${empty clinic.sunSh ? 'Y':clinic.sunClose }" />
						일요일:<select name="sunSh" id="sunSh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq clinic.sunSh ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="sunSm" id="sunSm" style="width:100px">
					    		<option value="00"  ${'00' eq clinic.sunSm ? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq clinic.sunSm ? 'selected':''}>30</option>
							 </select> ~
							 <select name="sunEh" id="sunEh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq clinic.sunEh ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="sunEm" id="sunEm" style="width:100px">
					    		<option value="00"  ${'00' eq clinic.sunEm ? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq clinic.sunEm ? 'selected':''}>30</option>
							 </select>
							 <input type="checkbox" name="sunClose" value="Y" ${ sunCloseTmp eq 'Y' ? 'checked' :'' }>휴진<br>
						<c:set var="holidayCloseTmp" value="${empty clinic.holidaySh ? 'Y':clinic.holidayClose }" />
						공휴일:<select name="holidaySh" id="holidaySh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq clinic.holidaySh ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="holidaySm" id="holidaySm" style="width:100px">
					    		<option value="00"  ${'00' eq clinic.holidaySm ? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq clinic.holidaySm ? 'selected':''}>30</option>
							 </select> ~
							 <select name="holidayEh" id="holidayEh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq clinic.holidayEh ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="holidayEm" id="holidayEm" style="width:100px">
					    		<option value="00"  ${'00' eq clinic.holidayEm ? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq clinic.holidayEm ? 'selected':''}>30</option>
							 </select>
							 <input type="checkbox" name="holidayClose" value="Y" ${ holidayCloseTmp eq 'Y' ? 'checked' :'' }>휴진<br>
						<c:set var="lunchShTmp" value="${empty clinic.lunchSh ? '13':clinic.lunchSh }" />
						<c:set var="lunchSmTmp" value="${empty clinic.lunchSm ? '00':clinic.lunchSm }" />
						<c:set var="lunchEhTmp" value="${empty clinic.lunchEh ? '14':clinic.lunchEh }" />
						<c:set var="lunchEmTmp" value="${empty clinic.lunchEm ? '00':clinic.lunchEm }" />
						점심시간:<select name="lunchSh" id="lunchSh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq lunchShTmp ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="lunchSm" id="lunchSm" style="width:100px">
					    		<option value="00"  ${'00' eq lunchSmTmp ? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq cllunchSmTmp ? 'selected':''}>30</option>
							 </select> ~
							 <select name="lunchEh" id="lunchEh" style="width:100px">
								<c:forEach var="hour" begin="0" end="24">
							    	<option value="${hour}"  ${hour eq lunchEhTmp ? 'selected':''}>${hour}</option>
							    </c:forEach>
							 </select>
							 <select name="lunchEm" id="lunchEm" style="width:100px">
					    		<option value="00"  ${'00' eq lunchEmTmp ? 'selected':''}>00</option>
					    		<option value="30"  ${'30' eq lunchEmTmp ? 'selected':''}>30</option>
							 </select>
							 <input type="checkbox" name="lunchYn" value="Y" ${ clinic.lunchYn eq 'Y' ? 'checked' :'' }>없음<br>
						안내사항 : <input type="checkbox" name="noticeYn" value="Y" ${ clinic.noticeYn eq 'Y' ? 'checked' :'' }>등록<br>
						        <textarea name="notice" id="notice" style="width:100%;height:100px">${ clinic.notice }</textarea>
					</td>
				</tr>
				<tr>
					<th>진료과목</th>
					<td colspan=3>
						<input type="text" name="subject" id="subject" value="${ clinic.subject }" style="width:200px">
					</td>
				</tr>
				<tr>

					<th>병의원 이미지</th>
					<td colspan=3 id="clinicImg">
						<input type="file" name="clinicImgFile" onchange="uploadImage('clinicImgFile')">
						<p class="txt">* Image Size : 420*free(jpg) / Max 10Mbyte</p>

						<c:forEach items="${ clinicImgList }" var="row">
							<c:if test="${ row.gubun eq 'clinic' }">
								<div>
									<img src='${row.attach}'>
									<input type='hidden' name='attach' value='${row.attach}'>
									<input type='hidden' name='attachOrgName' value='${row.attachOrgName}'>
									<input type='hidden' name='gubun' value='${row.gubun}'>
									<input type='button' class='btnSizeC' onclick='removeClinicImg(this); return false;' value='삭제'>
								</div>
							</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th>원장님 소개</th>
					<td colspan=3>
						<textarea name="doctorIntro" id="doctorIntro" style="width:100%;height:100px">${ clinic.doctorIntro }</textarea>
					</td>
				</tr>
				<tr>
					<th>원장님 약력</th>
					<td colspan=3>
						<textarea name="doctorHistory" id="doctorHistory" style="width:100%;height:100px">${ clinic.doctorHistory }</textarea>
					</td>
				</tr>
				<tr>
					<th>원장님 이미지</th>
					<td colspan=3 id="doctorImg">
						<c:set var="doctorImgChk" value="N"/>
						<c:forEach items="${ clinicImgList }" var="row">
							<c:if test="${ row.gubun eq 'doctor' }">
								<c:set var="doctorImgChk" value="Y"/>
							</c:if>
						</c:forEach>
						<input type="file" name="doctorImgFile" id="doctorImgFile" onchange="uploadImage('doctorImgFile')" style="display:${doctorImgChk eq 'Y' ? 'none':''};">
						<p class="txt" style="display:${doctorImgChk eq 'Y' ? 'none':''};">* Image Size : 620*free(jpg) / Max 10Mbyte</p>

						<c:forEach items="${ clinicImgList }" var="row">
							<c:if test="${ row.gubun eq 'doctor' }">
								<div>
									<img src='${row.attach}'>
									<input type='hidden' name='attach' value='${row.attach}'>
									<input type='hidden' name='attachOrgName' value='${row.attachOrgName}'>
									<input type='hidden' name='gubun' value='${row.gubun}'>
									<input type='button' class='btnSizeC' onclick='removeClinicImg(this); return false;' value='삭제'>
								</div>
							</c:if>
						</c:forEach>
					</td>
				</tr>
			</table>
		</div>

		<h2>추가정보</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>예약 가능 여부</th>
					<td>
						<label><input type="radio" name="reservationYn" value="Y" ${ clinic.reservationYn eq 'Y' ? 'checked' : '' }><span>가능</span></label>
						<label><input type="radio" name="reservationYn" value="N" ${ clinic.reservationYn eq 'N' ? 'checked' : '' }><span>불가능</span></label>
						<ul>
							<li>- Dr.PNT제품 상담 외에도 진료, 기타 상담 등의 진행을 위해 PNTmall에서 미리 진행된 예약을 기반으로 예약한 고객 안내 및 응대, 예약 관리(예약 확인 및 확정/취소)가 가능한 병의원</li>
						    <li>- 업무 범위: 예약건 확인 및 접수, 취소 시 사유 입력(clinic.pntmall.com에서 관리), 확정된 예약건에 따라 고객 방문 시 응대 및 상담, 노쇼 및 일정 변경 등 관리 </li>
					    </ul>
					</td>
				</tr>
				<tr>
					<th>소분 가능 여부</th>
					<td>
						<label><input type="radio" name="divisionYn" value="Y" ${ clinic.divisionYn eq 'Y' ? 'checked' : '' }><span>가능</span></label>
						<label><input type="radio" name="divisionYn" value="N" ${ clinic.divisionYn eq 'N' ? 'checked' : '' }><span>불가능</span></label>
						<ul>
							<li style="color:red;">- 소분가능병의원은 개인맞춤형 건강기능식품 추천을 위해 화상상담 및 해당 서비스 일체를 진행하는 병의원을 의미합니다. 소분가능병의원 지정을 원하실 경우 반드시 당사와 우선 협의 바랍니다.</li>
							<li>- Dr.PNT의 닥터팩 소분 서비스(규제샌드박스 특례 실증 사업)를 위한 개인 맞춤형 건강기능식품 추천, 상담, 판매 등 업무 수행이 가능한 병의원</li>
						    <li>- 업무 범위: 화상 또는 대면 상담을 통한 개인맞춤형 건강기능식품 소분 추천, 상담, 상담 예약 관리 및 닥터팩 소분 구성 승인을 포함한 안내 및 관리(clinic.pntmall.com에서 관리)</li>
						    <li>- 수수료: 고객의 제품 구매 결제 금액의 00% </li>
					    </ul>
					</td>
				</tr>
				<tr>
					<th>픽업 가능 여부</th>
					<td>
						<label><input type="radio" name="pickupYn" value="Y" ${ clinic.pickupYn eq 'Y' ? 'checked' : '' }><span>가능</span></label>
						<label><input type="radio" name="pickupYn" value="N" ${ clinic.pickupYn eq 'N' ? 'checked' : '' }><span>불가능</span></label>
						<ul>
							<li>- Dr.PNT 제품을 고객이 병의원에 방문해 직접 수령해가는 픽업 서비스를 위해 고객 방문 시 안내, 제품 수령 절차 수행, 제품 재고 확인 등 업무 수행이 가능한 병의원(고객은 PNTmall에서 결제까지 완료한 상태이며, 병의원에서는 내원한 신규 고객을 병의원 신규 환자로 전환 가능)</li>
						    <li>- 업무 범위: 고객이 병의원 방문 시 응대 및 안내, 픽업 일정 확인 및 픽업 제품 전달, 재고 유무 관리(clinic.pntmall.com에서 관리), 재고 부족 등 이슈 발생 시 고객 컨택 후 PNTmall 관리자에 전달 등 전반적인 고객 안내 및 관리</li>
					    </ul>
					</td>
				</tr>
				<tr>
					<th>카톡 상담 여부</th>
					<td>
						<label><input type="radio" name="katalkYn" value="Y" ${ clinic.katalkYn eq 'Y' ? 'checked' : '' }><span>가능</span></label>
						<label><input type="radio" name="katalkYn" value="N" ${ clinic.katalkYn eq 'N' ? 'checked' : '' }><span>불가능</span></label>
						<input type="text" name="katalkId" id="katalkId" value="${ clinic.katalkId }" style="width:300px" placeholder="채널 URL 입력" style="display:${ clinic.katalkYn eq 'Y' ? '' : 'none' };">
						<ul>
							<li>- 카톡 상담이 가능한 경우, 채널 채팅 URL을 입력해주세요. (ex. http://pf.kakao.com/_jbLbu/chat)</li>
							<li>- 채팅 URL 확인 방법: 카카오톡 채널 관리자 센터 - 채널 홍보 메뉴 - 채팅 URL 복사</li>
							<li>- 병의원 카카오톡 채널을 통해 운영 시간 내 실시간으로 상담이 가능한 병의원</li>
						    <li>- 업무 범위: 고객 응대 및 상담(Dr.PNT제품 구매 문의, 예약 일정 확인/변경 문의, 위치 문의 등 각종 문의 사항)</li>
					    </ul>
					</td>
				</tr>
				<tr id="alarmTr">
					<th>알림 설정<sup>*</sup></th>
					<td>
						연락처 :
							<select  name="alarmTel1" id="alarmTel1" class="defaultSelect" style="width:100px">
								<c:forEach items="${ mtelList }" var="row">
									<option value="${ row.name }" ${ row.name eq clinic.alarmTel1 ? 'selected':''}>${ row.name }</option>
								</c:forEach>
							</select>
							<input type="text" name="alarmTel2" id="alarmTel2" value="${ clinic.alarmTel2 }" style="width:200px" maxlength="8" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');"><br>
					</td>
				</tr>
				<tr>
					<th>SNS<br>(https://포함)</th>
					<td>
						블로그 : <input type="text" name="blog" id="blog" value="${ clinic.blog }" style="width:200px"><br>
						유투브 : <input type="text" name="youtube" id="youtube" value="${ clinic.youtube }" style="width:200px"><br>
						페이스북 : <input type="text" name="facebook" id="facebook" value="${ clinic.facebook }" style="width:200px"><br>
						인스타그램 : <input type="text" name="instagram" id="instagram" value="${ clinic.instagram }" style="width:200px"><br>
						트위터 : <input type="text" name="twitter" id="twitter" value="${ clinic.twitter }" style="width:200px"><br>
					</td>
				</tr>
			</table>
		</div>

		<h2>추천 병의원 상세 정보</h2>

		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
					<col width="15%">
					<col width="">
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>소분 가능 여부(5)</th>
					<td>${ clinic.divisionScore }</td>
					<th>픽업 가능 여부(5)</th>
					<td>${ clinic.pickupScore }</td>
					<th>카톡 상담 여부(10)</th>
					<td>${ clinic.katalkScore }</td>
				</tr>
				<tr>
					<th>SNS 활성도(30)</th>
					<td>${ clinic.snsScore }</td>
					<th>병의원 입력 완성도(20)</th>
					<td>${ clinic.infoScore }</td>
					<th>PNT몰 활동지수</th>
					<td>${ clinic.bbsScore }</td>
				</tr>
				<tr>
					<th>총 점수</th>
					<td  colspan=5>${ clinic.totalScore  }</td>
				</tr>
			</table>
		</div>

		<h2>정산 정보(필수)</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>병의원 사업자정보</th>
					<td>
						대표자명 : <input type="text" name="businessOwner" id="businessOwner" value="${ clinic.businessOwner }" style="width:200px"><br>
						병의원명 : <input type="text" name="businessName" id="businessName" value="${ clinic.businessName }" style="width:200px"><br>
						사업자등록증 :
						<div id="docuImg">
							<c:set var="docuImgChk" value="N"/>
							<c:forEach items="${ clinicImgList }" var="row">
								<c:if test="${ row.gubun eq 'docu' }">
									<c:set var="docuImgChk" value="Y"/>
								</c:if>
							</c:forEach>

							<input type="file" name="docuImgFile" id="docuImgFile" onchange="uploadImageAll('docuImgFile')" style="display:${docuImgChk eq 'Y' ? 'none':''};">
							<p class="txt" id="docuImgFileTxt" style="display:${docuImgChk eq 'Y' ? 'none':''};">* Max 5Mbyte / 확장자 : jpg, jpeg, gif, png, bmp</p>

							<c:forEach items="${ clinicImgList }" var="row">
								<c:if test="${ row.gubun eq 'docu' }">
									<div>
										<input type='hidden' name='attach' value='${row.attach}'>
										<input type='hidden' name='attachOrgName' value='${row.attachOrgName}'>
										<input type='hidden' name='gubun' value='${row.gubun}'>
										<input type='button' class='btnSizeC' onclick='download("${row.attachOrgName}","${row.attach}"); return false;' value='${row.attachOrgName}'>
										<input type='button' class='btnSizeC' onclick='removeClinicImg2(this); return false;' value='삭제'>
									</div>
								</c:if>
							</c:forEach>
						</div>
						<br>
						사업자번호 : <input type="text" name="businessNo" id="businessNo" value="${ clinic.businessNo }" style="width:200px"><br>
						업태 : <input type="text" name="businessType" id=businessType value="${ clinic.businessType }" style="width:200px"><br>
						업종 : <input type="text" name="businessItem" id="businessItem" value="${ clinic.businessItem }" style="width:200px"><br>
					</td>
				</tr>
				<tr>
					<th>건기식 사업자정보</th>
					<td>
						대표자명 : <input type="text" name="businessOwner2" id="businessOwner2" value="${ clinic.businessOwner2 }" style="width:200px"><br>
						업체명 : <input type="text" name="businessName2" id="businessName2" value="${ clinic.businessName2 }" style="width:200px"><br>
						사업자등록증 :
						<div id="healthImg">
							<c:set var="healthImgChk" value="N"/>
							<c:forEach items="${ clinicImgList }" var="row">
								<c:if test="${ row.gubun eq 'health' }">
									<c:set var="healthImgChk" value="Y"/>
								</c:if>
							</c:forEach>

							<input type="file" name="healthImgFile" id="healthImgFile" onchange="uploadImageAll('healthImgFile')" style="display:${healthImgChk eq 'Y' ? 'none':''};">
							<p class="txt" id="healthImgFileTxt" style="display:${healthImgChk eq 'Y' ? 'none':''};">* Max 5Mbyte / 확장자 : jpg, jpeg, gif, png, bmp</p>

							<c:forEach items="${ clinicImgList }" var="row">
								<c:if test="${ row.gubun eq 'health' }">
									<div>
										<input type='hidden' name='attach' value='${row.attach}'>
										<input type='hidden' name='attachOrgName' value='${row.attachOrgName}'>
										<input type='hidden' name='gubun' value='${row.gubun}'>
										<input type='button' class='btnSizeC' onclick='download("${row.attachOrgName}","${row.attach}"); return false;' value='${row.attachOrgName}'>
										<input type='button' class='btnSizeC' onclick='removeClinicImg2(this); return false;' value='삭제'>
									</div>
								</c:if>
							</c:forEach>
						</div>
						<br>
						사업자번호 : <input type="text" name="businessNo2" id="businessNo2" value="${ clinic.businessNo2 }" style="width:200px"><br>
						업태 : <input type="text" name="businessType2" id="businessType2" value="${ clinic.businessType2 }" style="width:200px"><br>
						업종 : <input type="text" name="businessItem2" id="businessItem2" value="${ clinic.businessItem2 }" style="width:200px"><br>
					</td>
				</tr>
				<tr>
					<th>입금계좌</th>
					<td>
						계좌번호 : <select name="bank" id="bank" style="width:100px">
									<c:forEach items="${ bankList }" var="row">
								    	<option value="${row.code1}${row.code2}" ${row.code1+=row.code2 eq clinic.bank ? 'selected':''} >${row.name}</option>
								    </c:forEach>
								</select>
							    <input type="text" name="account" id="account" value="${ clinic.account }" style="width:200px"><br>
						예금주명 : <input type="text" name="depositor" id="depositor" value="${ clinic.depositor }" style="width:200px">
								<input type="checkbox" name="depositorNot" id="depositorNot" value="Y" ${ clinic.depositorNot eq 'Y'?'checked':'' }>대표자 예금주 불일치 확인<br>
						통장 사본 :
						<div id="bankImg">
							<c:set var="bankImgChk" value="N"/>
							<c:forEach items="${ clinicImgList }" var="row">
								<c:if test="${ row.gubun eq 'bank' }">
									<c:set var="bankImgChk" value="Y"/>
								</c:if>
							</c:forEach>

							<input type="file" name="bankImgFile" id="bankImgFile" onchange="uploadImageAll('bankImgFile')" style="display:${bankImgChk eq 'Y' ? 'none':''};">
							<p class="txt" id="bankImgFileTxt" style="display:${bankImgChk eq 'Y' ? 'none':''};">* Max 5Mbyte / 확장자 : jpg, jpeg, gif, png, bmp</p>

							<c:forEach items="${ clinicImgList }" var="row">
								<c:if test="${ row.gubun eq 'bank' }">
									<div>
										<input type='hidden' name='attach' value='${row.attach}'>
										<input type='hidden' name='attachOrgName' value='${row.attachOrgName}'>
										<input type='hidden' name='gubun' value='${row.gubun}'>
										<input type='button' class='btnSizeC' onclick='download("${row.attachOrgName}","${row.attach}"); return false;' value='${row.attachOrgName}'>
										<input type='button' class='btnSizeC' onclick='removeClinicImg2(this); return false;' value='삭제'>
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
							<c:set var="agreeImgChk" value="N"/>
							<c:forEach items="${ clinicImgList }" var="row">
								<c:if test="${ row.gubun eq 'bank' }">
									<c:set var="agreeImgChk" value="Y"/>
								</c:if>
							</c:forEach>

							<input type="file" name="agreeImgFile" id="agreeImgFile" onchange="uploadImageAll('agreeImgFile')" style="display:${agreeImgChk eq 'Y' ? 'none':''};">
							<p class="txt" id="agreeImgFileTxt" style="display:${agreeImgChk eq 'Y' ? 'none':''};">* Max 5Mbyte / 확장자 : jpg, jpeg, gif, png, bmp</p>

							<c:forEach items="${ clinicImgList }" var="row">
								<c:if test="${ row.gubun eq 'agree' }">
									<div>
										<input type='hidden' name='attach' value='${row.attach}'>
										<input type='hidden' name='attachOrgName' value='${row.attachOrgName}'>
										<input type='hidden' name='gubun' value='${row.gubun}'>
										<input type='button' class='btnSizeC' onclick='download("${row.attachOrgName}","${row.attach}"); return false;' value='${row.attachOrgName}'>
										<input type='button' class='btnSizeC' onclick='removeClinicImg2(this); return false;' value='삭제'>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</td>
				</tr>
			</table>
		</div>

		<div class="white_box">
			<table class="board">
				<tr>
					<th>전시여부<sup>*</sup></th>
					<td>
						<label><input type="radio" name="dispYn" value="Y" ${ clinic.dispYn eq 'Y' ? 'checked' : '' }><span>공개</span></label>
						<label><input type="radio" name="dispYn" value="N" ${ clinic.dispYn eq 'N' ? 'checked' : '' }><span>비공개</span></label>
					</td>
					<th>가입신청일</th>
					<td><fmt:formatDate value="${ clinic.cdate }" pattern="${ DateTimeFormat }" />
					</td>
					<th>업데이트일</th>
					<td><fmt:formatDate value="${clinic.udate}" pattern="${ DateTimeFormat }"/>
					</td>
					<th>PNTmall<br>최근로그인</th>
					<td><fmt:formatDate value="${clinic.loginDate}" pattern="${ DateTimeFormat }"/>
					</td>
					<th>병의원관리자<br>최근로그인</th>
					<td><fmt:formatDate value="${clinic.loginClinicDate}" pattern="${ DateTimeFormat }"/>
					</td>
				</tr>
			</table>
		</div>


		<div class="btnArea">
			<span class="fl">
				<a href="${ retrivePath }" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
			<c:if test="${ mode eq 'modify' && updateAuth eq 'Y' }">
				<a href="javascript:goSubmit()" class="btnTypeC btnSizeA"><span>수정</span></a>
			</c:if>
		</div>
		</form>

		<form name="editFormJoin" id="editFormJoin" method="POST" action="createJoin">
			<input type="hidden" name="memNo" value="${ clinic.memNo}" />


		<h2>회원상태 설정</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>회원 상태</th>
					<td>
						<c:forEach items="${ approveList }" var="row">
							<c:if test="${ not(row.code2 eq '001' || row.code2 eq '005') }">
								<label><input type="radio" name="approve" value="${row.code1}${row.code2}" ${ clinic.approve eq row.code1 +=row.code2 ? 'checked':'' }><span>${row.name}</span></label>
							</c:if>
					    </c:forEach>
					</td>
				</tr>
				<tr>
					<th>사유</th>
					<td>
						<textarea name="reason" id="reason" style="width:100%;height:100px"></textarea>
					</td>
				</tr>
			</table>
		</div>
		<div class="btnArea">
			<c:if test="${ mode eq 'modify' && updateAuth eq 'Y' }">
				<a href="javascript:goSubmitJoin()" class="btnTypeC btnSizeA"><span>회원상태저장</span></a>
			</c:if>
		</div>
		</form>


		<h2>회원 상태 설정 히스토리</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
					<col width="">
				</colgroup>
				<tr>
					<th>회원상태</th>
					<th>사유</th>
					<th>변경일</th>
				</tr>
				<c:forEach items="${ clinicJoinList }" var="row">
				<tr>
					<td>
						${ row.approveName }
					</td>
					<td>
						${ row.reason }
					</td>
					<td>
						<fmt:formatDate value="${row.cdate}" pattern="${ DateTimeFormat }"/>
					</td>
				  </tr>
				  </c:forEach>
			</table>
		</div>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

<div style="display:none" id="addimgTmp">
	<div>
		<img src="" name="clinicImgTag" style="display:none">
		<input type="hidden" name="clinicImg" value="">
		<input type="hidden" name="gubun" value="##GUBUN##">
		<input type='button' class='btnSizeC' onclick='removeClinicImg(this); return false;' value='삭제'>
	</div>
</div>

</body>
</html>
