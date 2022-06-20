<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<link rel="stylesheet" href="//jq-simple-dtpicker-gh-master.herokuapp.com/jquery.simple-dtpicker.css">
<script src="//mugifly.github.io/jquery-simple-datetimepicker/jquery.simple-dtpicker.js"></script>
<script>
	var v;

	$(function() {
		v = new ef.utils.Validator($("#editForm"));
		v.add("title", {
			empty : "제목을 입력하세요."
		});
		v.add("msg", {
			empty : "메시지를 입력하세요."
		});
		v.add("sendDate", {
			empty : "수행시각을 입력하세요."
		});

		$("#sidate").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		$("#eidate").datepicker({
			changeMonth: true,
		    changeYear: true
		});
		$("#sendDate").appendDtpicker({
			"locale": "ko",
			"dateFormat": "YYYY.MM.DD hh:mm",
			"minuteInterval": 10,
			"closeOnSelected": true,
			"futureOnly": true,
			"autodateOnStart": false,
			"minTime":"09:00",
			"maxTime":"18:10"
		});

	});

	function goSubmit() {
		if(v.validate()) {
			disableScreen();

			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list";
				enableScreen();
			});
		}
	}

	function setTarget(v) {
		if(v == 1) {
			$("#target11").show();
			$("#target12").show();
			$("#target13").show();
			$("#target2").hide();
		} else {
			$("#target11").hide();
			$("#target12").hide();
			$("#target13").hide();
			$("#target2").show();
		}
	}

	function upload(field, ftype) {
		if($("input[name=" + field + "]").val() != '') {
	        disableScreen();

	        var action = "/upload/upload?field=" + field + "&path=push&fileType=" + ftype;
	        ef.multipart($("#editForm"), action, field, function(result) {

	        	if(result.succeed) {
	        		var s = field.replace(/File/gi, '');
	        		$("input[name=" + s + "]").val(result.param.uploadUrl);
	        		
	        		if(ftype == 'image') {
		        		$("#" + s + "Tag").attr("src", result.param.uploadUrl);
	        		} else {
		        		$("#" + s + "Tag").attr("href", result.param.uploadUrl);
		        		$("#" + s + "Tag").html(result.param.uploadUrl);
	        		}
	        		$("#" + s + "Div").show();
	        	} else {
	        		alert(result.message);
	        	}

	        	$("input[name=" + field + "]").val("");
	        	enableScreen();
	        });
		}
	}

	function removeImg(s) {
		$("input[name=" + s + "]").val('');
// 		$("#" + s + "Tag").attr("src", '');
		$("#" + s + "Div").hide();
	}

	function goStatus(s) {
		$("#statusForm input[name=status]").val(s);
		disableScreen();

		ef.proc($("#statusForm"), function(result) {
			alert(result.message);
// 			if(result.succeed) {
				location.reload();
// 			}
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
		<form name="statusForm" id="statusForm" method="POST" action="status">
			<input type="hidden" name="pno" value="${ appPush.pno }" />
			<input type="hidden" name="status" value="" />
		</form>
		<form name="editForm" id="editForm" method="POST" action="${ mode }">
			<input type="hidden" name="pno" value="${ appPush.pno }" />
		<h2>기본 정보</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>수행번호</th>
					<td>${ appPush.pno }</td>
				</tr>
				<tr>
					<th>진행현황</th>
					<td>
						<c:if test='${ appPush.status eq 10 }'>
							작성중(발송 대상: <fmt:formatNumber value="${ targetCount }" pattern="#,###" />)
							<a href="javascript:goStatus(20)" class="btnTypeD btnSizeA"><span>대기시작</span></a>
							<p>※ 정확한 발송대상은 발송완료 후 발송 대상 확인에서 확인 가능합니다.</p>
						</c:if>
						<c:if test='${ appPush.status eq 20 }'>
							대기중(발송 대상: <fmt:formatNumber value="${ targetCount }" pattern="#,###" />)
							<a href="javascript:goStatus(10)" class="btnTypeD btnSizeA"><span>대기취소</span></a>
							<p>※ 정확한 발송대상은 발송완료 후 발송 대상 확인에서 확인 가능합니다.</p>
							<p>※ 대기시작 이후에는 모든 수정이 불가합니다. 수정이 필요한 경우 수행 시각 이전에 대기 취소를 해주세요.</p>
							
						</c:if>
						<c:if test='${ appPush.status eq 30 }'>
							발송완료
						</c:if>
					</td>
				</tr>
				<tr>
					<th>제목<sup>*</sup></th>
					<td>
						<input type="text" name="title" value="${ appPush.title }" style="width:100%" maxlength="50">
					</td>
				</tr>
				<tr>
					<th>메시지<sup>*</sup></th>
					<td>
						<textarea name="msg" style="width:90%;height:100px">${ mode eq 'create' ? "(광고)" : "" } ${ appPush.msg }</textarea>
					</td>
				</tr>
				<tr>
					<th>링크</th>
					<td>
						<input type="text" name="link" value="${ appPush.link }" style="width:100%" maxlength="256">
					</td>
				</tr>
				<tr>
					<th>이미지</th>
					<td>
						<input type="file" name="imgFile" onchange="upload('imgFile', 'image')" style="width:500px">
						<p class="txt">* Image Size : 720 x 320 (jpg) / Max 1Mbyte</p>
						<div id="imgDiv" style="display:${ !empty appPush.img ? 'block' : 'none' }">
							<img src="${ appPush.img }" id="imgTag">
							<a href="#" onclick="removeImg('img'); return false;" class="btnSizeC">삭제</a>
						</div>
						<input type="hidden" name="img" value="${ appPush.img }">
					</td>
				</tr>
				<tr>
					<th>수행시각<sup>*</sup></th>
					<td>
						<div class="dateBox">
							<input type="text" name="sendDate" id="sendDate" value="${ mode eq 'create' ? today : appPush.sendDate }" class="datetimepicker" readonly>
						</div>
					</td>
				</tr>
				<tr>
					<th>메모</th>
					<td>
						<input type="text" name="memo" value="${ appPush.memo }" style="width:100%" maxlength="2000">
					</td>
				</tr>
			</table>
		</div>

		<h2>푸시 알림 대상 등록</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>업로드설정</th>
					<td>
						<label><input type="radio" name="targetGubun" value="1" onclick="setTarget(1)" ${ mode eq 'create' || appPush.targetGubun eq 1 ? 'checked' : '' }><span>검색</span></label>
						<label><input type="radio" name="targetGubun" value="2" onclick="setTarget(2)" ${ appPush.targetGubun eq 2 ? 'checked' : '' }><span>엑셀</span></label>
					</td>
				</tr>
				<tr id="target11" style="display:${ mode eq 'create' || appPush.targetGubun eq 1 ? '' : 'none' }">
					<th>설치일</th>
					<td>
						<div class="dateBox">
							<input type="text" name="sidate" id="sidate" value="${ appPush.sidate }" readonly>
						</div> ~ 
						<div class="dateBox">
							<input type="text" name="eidate" id="eidate" value="${ appPush.eidate }" readonly>
						</div>
					</td>
				</tr>
				<tr id="target12" style="display:${ mode eq 'create' || appPush.targetGubun eq 1 ? '' : 'none' }">
					<th>OS</th>
					<td>
						<label><input type="radio" name="os" value="ALL" ${ mode eq 'create' || appPush.os eq 'ALL' ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="os" value="IOS" ${ appPush.os eq 'IOS' ? 'checked' : '' }><span>iOS</span></label>
						<label><input type="radio" name="os" value="ANDROID" ${ appPush.os eq 'ANDROID' ? 'checked' : '' }><span>ANDROID</span></label>
					</td>
				</tr>
				<tr id="target13" style="display:${ mode eq 'create' || appPush.targetGubun eq 1 ? '' : 'none' }">
					<th>동의여부</th>
					<td>
						<label><input type="radio" name="agreeYn" value="A" ${ mode eq 'create' || appPush.agreeYn eq 'A' ? 'checked' : '' }><span>전체</span></label>
						<label><input type="radio" name="agreeYn" value="Y" ${ appPush.agreeYn eq 'Y' ? 'checked' : '' }><span>Y</span></label>
						<label><input type="radio" name="agreeYn" value="N" ${ appPush.agreeYn eq 'N' ? 'checked' : '' }><span>N</span></label>
					</td>
				</tr>
				<tr id="target2" style="display:${ appPush.targetGubun eq 2 ? '' : 'none' }">
					<th>Excel 등록</th>
					<td>
						<input type="file" name="excelFile" onchange="upload('excelFile', 'excel')" style="width:500px" accept=".xlsx">
						<div id="excelDiv" style="display:${ !empty appPush.excel ? 'block' : 'none' }">
							<a href="${ appPush.excel }" id="excelTag">${ appPush.excel }</a>
							<a href="#" onclick="removeImg('excel'); return false;" class="btnSizeC">삭제</a>
						</div>
						<input type="hidden" name="excel" value="${ appPush.excel }">
					</td>
				</tr>
			</table>
		</div>

		<c:if test='${ appPush.status eq 30 }'>
		<h2>발송 대상 확인</h2>
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="15%">
					<col width="">
					<col width="15%">
					<col width="">
				</colgroup>
				<tr>
					<th>진행 현황</th>
					<td>
						발송 완료
						<a href="javascript:showPopup('target?pno=${ appPush.pno }', '1000', '800');" class="btnTypeD btnSizeA"><span>발송 리스트 확인</span></a>
					</td>
					<th>대상</th>
					<td>
						<fmt:formatNumber value="${ appPush.iosCnt + appPush.androidCnt }" pattern="#,###" />
						(IOS : <fmt:formatNumber value="${ appPush.iosCnt }" pattern="#,###" /> /
						Android : <fmt:formatNumber value="${ appPush.androidCnt }" pattern="#,###" />)
					</td>
				</tr>
				<tr>
					<th>발송 시작</th>
					<td>
						<fmt:formatDate value="${ appPush.ssdate }" pattern="${ DateTimeFormat }" />
					</td>
					<th>발송 유효</th>
					<td>
						<fmt:formatNumber value="${ appPush.iosCnt + appPush.androidCnt }" pattern="#,###" />
						(IOS : <fmt:formatNumber value="${ appPush.iosCnt }" pattern="#,###" /> /
						Android : <fmt:formatNumber value="${ appPush.androidCnt }" pattern="#,###" />)
					</td>
				</tr>
				<tr>
					<th>발송 에러</th>
					<td>
						<fmt:formatNumber value="${ appPush.iosFail + appPush.androidFail }" pattern="#,###" />
						(IOS : <fmt:formatNumber value="${ appPush.iosFail }" pattern="#,###" /> /
						Android : <fmt:formatNumber value="${ appPush.androidFail }" pattern="#,###" />)
					</td>
					<th>발송 완료</th>
					<td>
						<fmt:formatNumber value="${ appPush.iosSuccess + appPush.androidSuccess }" pattern="#,###" />
						(IOS : <fmt:formatNumber value="${ appPush.iosSuccess }" pattern="#,###" /> /
						Android : <fmt:formatNumber value="${ appPush.androidSuccess }" pattern="#,###" />)
					</td>
				</tr>
				<tr>
					<th>도착</th>
					<td>
						<fmt:formatNumber value="${ appPush.iosReceiveCnt + appPush.androidReceiveCnt }" pattern="#,###" />
						(IOS : <fmt:formatNumber value="${ appPush.iosReceiveCnt }" pattern="#,###" /> /
						Android : <fmt:formatNumber value="${ appPush.androidReceiveCnt }" pattern="#,###" />)
					</td>
					<th>확인</th>
					<td>
						<fmt:formatNumber value="${ appPush.iosOpenCnt + appPush.androidOpenCnt }" pattern="#,###" />
						(IOS : <fmt:formatNumber value="${ appPush.iosOpenCnt }" pattern="#,###" /> /
						Android : <fmt:formatNumber value="${ appPush.androidOpenCnt }" pattern="#,###" />)
					</td>
				</tr>
			</table>
		</div>
		</c:if>
		
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>등록일/등록자</th>
					<td><fmt:formatDate value="${ appPush.cdate }" pattern="${ DateTimeFormat }" /> / ${ appPush.cuserId }</td>
					<th>수정일/수정자</th>
					<td><fmt:formatDate value="${ appPush.udate }" pattern="${ DateTimeFormat }" /> / ${ appPush.uuserId }</td>
				</tr>
			</table>
		</div>

		<div class="btnArea">
			<span class="fl">
				<a href="${ retrivePath }" class="btnTypeC btnSizeA"><span>목록</span></a>
			</span>
			<c:if test="${ mode eq 'create' }">
				<a href="javascript:goSubmit()" class="btnTypeD btnSizeA"><span>등록</span></a>
			</c:if>
			<c:if test="${ mode eq 'modify' && updateAuth eq 'Y' && appPush.status eq 10 }">
				<a href="javascript:goSubmit()" class="btnTypeD btnSizeA"><span>수정</span></a>
			</c:if>
		</div>
		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->
</body>
</html>

