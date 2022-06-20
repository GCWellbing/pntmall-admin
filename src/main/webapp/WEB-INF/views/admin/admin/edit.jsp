<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	var v;

	$(function() {
		v = new ef.utils.Validator($("#editForm"));
		<c:if test="${ mode eq 'create' }">
		v.add("adminId", {
			empty : "관리자ID를 입력하세요.",
			max : "20"
		});
		</c:if>
		v.add("name", {
			empty : "관리자명을 입력하세요.",
			max : "50"
		});

		$("#checkAll").click(function() {
			$("input[name=menuNo]").each(function() {
				$(this).prop("checked", $("#checkAll").prop("checked"));
			});
		});

	});

	function goSubmit() {
		if(v.validate()) {
			<c:if test="${ mode eq 'create' }">
				if($("input[name=checkDuplicate]").val() == 'N') {
					alert("ID중복체크를 해주세요.");
					return;
				}
				if(!chkValidPasswd($("input[name=passwd]").val())) {
					$("input[name=passwd]").focus();
					return;
				}
			</c:if>
			if($("input[name=passwd]").val() != '' && $("input[name=passwd]").val() != $("input[name=passwd2]").val()) {
				alert("비밀번호 확인이 일치하지 않습니다.");
				$("input[name=passwd2]").focus();
				return;
			}

			disableScreen();
			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list";
				enableScreen();
			});
		}
	}

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

    function checkDuplication() {
		if($("input[name=adminId]").val() == '') {
			alert("아이디를 입력해주세요.");
			$("input[name=adminId]").focus();
			return;
		}

		$.getJSON("exists?adminId=" + $("input[name=adminId]").val(), function(json) {
			if(json.param.isExists) {
				alert("동일한 ID가 있습니다.");
                $("input[name=checkDuplicate]").val("N");
			}
			else {
				alert("사용가능한 아이디입니다.");
				$("input[name=checkDuplicate]").val("Y");
			}
		});
    }

    function setAuth() {
    	if($("select[name=teamNo] option:selected").val() != '') {
			$.ajax({
				method : "GET",
				url : "teamAuth",
				data : { teamNo : $("select[name=teamNo] option:selected").val() },
				dataType : "json"
			})
			.done(function(json) {
				$("input[name=menuNo]").each(function() {
					$(this).prop("checked", false);
				});

				$.each(json.param.list, function(i, row) {
					if(row.authYn == 'Y') {
						$("#menuNo" + row.menuNo).prop("checked", true);
					}
				});

				if(json.param.updateAuth == 'Y') {
					$("input[name=updateAuth]").prop("checked", true);
				} else {
					$("input[name=updateAuth]").prop("checked", false);
				}
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
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="adminNo" value="${ admin.adminNo }" />
		<h2>정보 등록</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>관리자ID<sup>*</sup></th>
					<td>
						<c:if test="${ mode eq 'create' }">
							<input type="text" name="adminId" value="" style="width:200px" maxlength="20" onchange="$('input[name=checkDuplicate]').val('N')">
							<input type="hidden" name="checkDuplicate" value="N" />
							<a href="javascript:checkDuplication();" class="btnTypeA btnSizeA"><span>ID 중복체크</span></a>
						</c:if>
						<c:if test="${ mode eq 'modify' }">
							${ admin.adminId }
						</c:if>
					</td>
				</tr>
				<tr>
					<th>관리자명<sup>*</sup></th>
					<td>
						<input type="text" name="name" value="${ admin.name }" style="width:200px" maxlength="50">
					</td>
				</tr>
				<tr>
					<th>비밀번호<sup>*</sup></th>
					<td>
						<input type="password" name="passwd" value="" style="width:200px" maxlength="20">
					</td>
				</tr>
				<tr>
					<th>비밀번호 확인<sup>*</sup></th>
					<td>
						<input type="password" name="passwd2" value="" style="width:200px" maxlength="20">
					</td>
				</tr>
				<tr>
					<th>E-mail</th>
					<td>
						<input type="text" name="email" value="${ admin.email }" style="width:100%" maxlength="300">
					</td>
				</tr>
				<tr>
					<th>휴대폰</th>
					<td>
						<input type="text" name="mtel" value="${ admin.mtel }" style="width:200px" maxlength="20" maxlength="11" onKeyup="this.value=this.value.replace(/[^0-9]/g,'');">
					</td>
				</tr>
				<tr>
					<th>소속<sup>*</sup></th>
					<td>
						<select name="teamNo" style="width:calc(50% - 5px)" onchange="setAuth()">
							<option value=""></option>
							<c:forEach items="${ teamList }" var="row">
							<option value="${ row.teamNo }" ${ row.teamNo eq admin.teamNo ? 'selected' : '' }>${ row.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>상태<sup>*</sup></th>
					<td>
						<label><input type="radio" name="status" value="S" ${ mode eq 'create' || admin.status eq 'S' ? 'checked' : '' }><span>활성</span></label>
						<label><input type="radio" name="status" value="H" ${ admin.status eq 'H' ? 'checked' : '' }><span>비활성</span></label>
					</td>
				</tr>
			</table>
		</div>

		<h2>메뉴권한</h2>
		<div class="white_box">
			<table class="board">
				<tr><td>
					<label><input type="checkbox" id="checkAll"><span>전체선택</span></label>
				</td></tr>
				<tr><td>
					${ menuUl }
				</td></tr>
				<tr><td>
					<label><input type="checkbox" name="updateAuth" value="Y" ${ admin.updateAuth eq 'Y' ? 'checked' :'' }> <span>수정/삭제 권한 부여</span></label>
					&nbsp;&nbsp;&nbsp;&nbsp;* 체크 시 메뉴에 대한 수정 권한이 부여됩니다.
				</td></tr>
			</table>
		</div>

		<div class="btnArea">
			<c:if test="${ mode eq 'create' }">
				<a href="javascript:goSubmit()" class="btnTypeC btnSizeA"><span>등록</span></a>
			</c:if>
			<c:if test="${ mode eq 'modify' && updateAuth eq 'Y' }">
				<a href="javascript:goSubmit()" class="btnTypeC btnSizeA"><span>수정</span></a>
			</c:if>
		</div>
		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
