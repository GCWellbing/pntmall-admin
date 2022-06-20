<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	var v;

	$(function() {
		v = new ef.utils.Validator($("#editForm"));
		v.add("name", {
			empty : "관리자명을 입력하세요.",
			max : "50"
		});
		v.add("passwd", {
			empty : "기존 패스워드를 입력하세요.",
			max : "20"
		});
		v.add("passwd2", {
			empty : "신규 패스워드를 입력하세요.",
			max : "20"
		});
		v.add("passwd3", {
			empty : "신규 패스워드 확인을 입력하세요.",
			max : "20"
		});

	});

    function chkValidPasswd(passwd) {
        if(passwd.length < 8) {
            alert("비밀번호는 최소 8자 이상입니다.");
            return false;
        }

        //생성 항목 포함 갯수
        //2개 항목이상 10자리 이상
        //3개 항목이상 8자리 이상
        var validCount	=	0;

        //대문자 포함 여부
        if(passwd.search(/[a-z]+/) > -1){
            validCount	+=	1;
        }
        //소문자 포함 여부
        if(passwd.search(/[A-Z]+/) > -1){
            validCount	+=	1;
        }
        //숫자 포함 여부
        if(passwd.search(/[0-9]+/) > -1){
            validCount	+=	1;
        }
        //특수문자 포함 여부
        if(passwd.search(/[~!@\#$%<>^&*\()\-=+_\']/) > -1 ){
            validCount	+=	1;
        }

        if(validCount <= 1){
            alert("비밀번호는 대문자, 소문자, 숫자, 특수문자들중 최소한 2가지 항목이 포함되어야합니다.");
            return false;
        } else if ( validCount == 2){
            if( passwd.length < 10){
                alert("비밀번호는 대문자, 소문자, 숫자, 특수문자들중 2가지 항목이 포함된경우 최소 10자이상 등록해주세요.");
                return false;
            }
        } else if ( validCount > 3){
            if( passwd.length < 8){
                alert("비밀번호는 대문자, 소문자, 숫자, 특수문자들중 3가지 항목이상 포함된경우 최소 8자이상 등록해주세요.");
                return false;
            }
        }

        var SamePass_0 = 0; //동일문자 카운트
        var SamePass_1 = 0; //연속성(+) 카운드
        var SamePass_2 = 0; //연속성(-) 카운드

        var chr_pass_0;
        var chr_pass_1;

        for(var i=0; i < passwd.length; i++) {
            chr_pass_0 = passwd.charAt(i);
            chr_pass_1 = passwd.charAt(i+1);

            //동일문자 카운트
            if(chr_pass_0 == chr_pass_1) {
                SamePass_0 = SamePass_0 + 1
            }

            //연속성(+) 카운드
            if(chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == 1) {
                SamePass_1 = SamePass_1 + 1
            }

            //연속성(-) 카운드
            if(chr_pass_0.charCodeAt(0) - chr_pass_1.charCodeAt(0) == -1) {
                SamePass_2 = SamePass_2 + 1
            }
        } // for

        if(SamePass_0 > 1) {
            alert("동일문자를 3번 이상 사용할 수 없습니다.");
            return false;
        }

        if(SamePass_1 > 1 || SamePass_2 > 1 )  {
            alert("연속된 문자열(123 또는 321, abc, cba 등)을\n 3자 이상 사용 할 수 없습니다.");
            return false;
        }

        return true;
    }

    function goSubmit() {
		if(v.validate()) {
			if(!chkValidPasswd($("input[name=passwd2]").val())) {
				$("input[name=passwd2]").focus();
				return;
			}

			if($("input[name=passwd2]").val() != $("input[name=passwd3]").val()) {
				alert("신규 패스워드 확인이 일치하지 않습니다.");
				$("input[name=passwd3]").focus();
				return;
			}

			disableScreen();
			ef.proc($("#editForm"), function(result) {
				console.log(result);
				alert(result.message);
				enableScreen();
				if(result.succeed) {
					self.close();
				}
			});
		}
	}


</script>
</head>
<body>
<div id="popWrapper">
	<h1>내 정보 수정</h1>
	<div id="popContainer">
		<div class="white_box">
			<form name="editForm" id="editForm" method="POST" action="myPageProc">
			<table class="board">
				<colgroup>
					<col width="160">
					<col width="">
				</colgroup>
				<tr>
					<th>관리자 ID</th>
					<td>${ info.adminId }</td>
				</tr>
				<tr>
					<th>관리자명<sup>*</sup></th>
					<td>
						<input type="text" name="name" value="${ info.name }">
					</td>
				</tr>
				<tr>
					<th>기존 패스워드<sup>*</sup></th>
					<td>
						<input type="password" name="passwd">
					</td>
				</tr>
				<tr>
					<th>신규 패스워드<sup>*</sup></th>
					<td>
						<input type="password" name="passwd2">
					</td>
				</tr>
				<tr>
					<th>신규 패스워드 확인<sup>*</sup></th>
					<td>
						<input type="password" name="passwd3">
					</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>
						<input type="text" name="email" value="${ info.email }">
					</td>
				</tr>
				<tr>
					<th>연락처</th>
					<td>
						<input type="text" name="mtel" value="${ info.mtel }" maxlength="11" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
					</td>
				</tr>
			</table>
			</form>
		</div>
		<div class="btnArea">
			<input type="button" id="regist" class="btnSizeA btnTypeD" value="수정" onclick="goSubmit()">
		</div>
	</div>
</div><!-- //wrapper -->
</body>
</html>
