<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<c:import url="/include/head" />
<script>
	var v;
	
	$(function() {
		v = new ef.utils.Validator($("#editForm"));
		v.add("title", {
			empty : "건강주제를 입력하세요.",
			max : "100"
		}).add("img", {
			empty : "이미지를 선택해주세요.",
		}).add("rank", {
			empty : "전시순서를 입력하세요.",
			min : "1",
			max : "2",
			format : "numeric"
		});
		
		$("#submitBtn").click(function() {
			if(v.validate()) {
				disableScreen();
				ef.proc($("#editForm"), function(result) {
					alert(result.message);
					if(result.succeed) location.href = "list?healthNo=${ param.healthNo }";
					enableScreen();
				});
			}
		});
	
		$("#resetBtn").click(function() {
			$("#editForm")[0].reset();
			$("input[name=healthNo]").val("");
			$("input[name=rank]").val("1");
			$("input[name=img]").val("");
			$("#imgTag").attr("src", "");
			$("#editForm").attr("action", "create");
		});

		$("#subList").on("click", ".btnUp", function() {
			console.log("------- up click ------------ ");
			var currObj = $(this).parents("tr");
			var idx = $(currObj).index();
			
			if (idx > 0) {
				var upperObj = currObj.prev();
				upperObj.before($("<tr></tr>").html(currObj.html()));
				
				//jquery 버그? 변경된 값은 html()에서 제너레이트 되지 않는다. 변경된 값을 다시 재지정.
				$("#subList input[name=healthNo]", upperObj.prev()).val($("#subList input[name=healthNo]", currObj).val());

				currObj.remove();
			}
		});
		
		$("#subList").on("click", ".btnDown", function() {
			var currObj = $(this).parents("tr");
			var size = $("#subList tr").length;
			var idx = $(currObj).index();
			
			if (idx + 1 < size) {
				var belowObj = currObj.next();
				belowObj.after($("<tr></tr>").html(currObj.html()));
				
				//jquery 버그? 변경된 값은 html()에서 제너레이트 되지 않는다. 변경된 값을 다시 재지정.
				$("#subList input[name=healthNo]", belowObj.next()).val($("#subList input[name=healthNo]", currObj).val());

				currObj.remove();
			}
		});	
	});

	function modify(healthNo) {
		disableScreen();
		$.getJSON("info?healthNo=" + healthNo, function(json) {
			console.log(json);
			$("#editForm input[name=healthNo]").val(json.param.info.healthNo);
			$("input[name=title]").val(json.param.info.title);
			$("input[name=rank]").val(json.param.info.rank);
			$("input[name=img]").val(json.param.info.img);
			$("#imgTag").attr("src", json.param.info.img);
			$("select[name=type]").val(json.param.info.type).attr("selected", "selected");
			$("input[name=status]:input[value=" + json.param.info.status + "]").prop("checked", true);
			$("#editForm").attr("action", "modify");
			enableScreen();
		});
	}
	
	function remove(healthNo) {
		if(confirm("삭제하시겠습니까?")) {
			disableScreen();
			$("#editForm input[name=healthNo]").val(healthNo);
			$("#editForm").attr("action", "remove");
			
			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list?healthNo=${ param.healthNo }";
				enableScreen();
			});
		}
	}

	function modifyRank() {
		if(confirm("정렬 일괄 수정하시겠습니까?")) {
			disableScreen();
			ef.proc($("#listForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list?healthNo=${ param.healthNo }";
				enableScreen();
			});
		}
	}

	function uploadImage(field) {
		if($("input[name=" + field + "]").val() != '') {
			disableScreen();

			var action = "/upload/upload?field=" + field + "&path=healthTopic&fileType=image";
			ef.multipart($("#editForm"), action, field, function(result) {
				console.log("result", result);

				if(result.succeed) {
					var s = field.replace(/File/gi, '');
					$("input[name=" + s + "]").val(result.param.uploadUrl);
					$("#" + s + "Tag").attr("src", result.param.uploadUrl);
					$("#" + s + "Tag").show();
				} else {
					alert(result.message);
				}

				$("input[name=" + field + "]").val("");
				enableScreen();
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
		<form name="editForm" id="editForm" method="post" action="create">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>* 건강주제</th>
					<td>
						<input type="hidden" name="healthNo" value="">
						<input type="text" name="title" value="" style="width:100%">
					</td>
				</tr>
				<tr>
					<th>* 이미지</th>
					<td>
						<input type="file" name="imgFile" onchange="uploadImage('imgFile')" style="width:100%">
						<p class="txt">* Image Size : 500 X 256(JPG) /  Max 1Mbyte</p>
						<img src="" id="imgTag" >
						<input type="hidden" name="img" id="img" value="">
					</td>
				</tr>
				<tr>
					<th>* 주제 유형</th>
					<td>
						<select name="type" style="width:200px">
						<c:forEach items="${ typeList }" var="row">
						<option value="${ row.code2}">${row.name}</option>
						</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th>* 정렬 순서</th>
					<td>
						<input type="text" name="rank" value="1" style="width:100px">
					</td>
				</tr>
				<tr>
					<th>상태</th>
					<td>
						<label><input type="radio" name="status" value="S" checked><span>활성</span></label>
						<label><input type="radio" name="status" value="H"><span>비활성</span></label>
					</td>
				</tr>
			</table>			
			<div class="btnArea ac">
				<input type="button" class="btnTypeA btnSizeA" id="submitBtn" value="등록">
				<input type="button" class="btnTypeB btnSizeA" id="resetBtn" value="초기화">
			</div>
		</div>
		</form>
		
		<form name="listForm" id="listForm" method="post" action="modifyRank">
		<table class="list">
			<colgroup>
				<col width="80px">
				<col width="">
				<col width="">
				<col width="150">
				<col width="100px">
				<col width="100px">
				<col width="200px">
				<col width="200px">
				<col width="100px">
			</colgroup>
			<thead>
				<tr>
					<th>No</th>
					<th>이미지</th>
					<th>건강주제</th>
					<th>주제유형</th>
					<th>전시순서</th>
					<th>상태</th>
					<th>등록일</th>
					<th>수정일</th>
					<th>관리</th>
				</tr>
			</thead>
			<tbody id="subList">
				<c:set var="no" value="${ fn:length(list) }" />
				<c:forEach items="${ list }" var="row">						
				<tr>
					<input type="hidden" name="healthNo" value="${ row.healthNo }">
					<td><c:out value="${ no }" /></td>
					<td><img src="${ row.img }" ></td>
					<td><a href="edit?healthNo=${ row.healthNo }">${ row.title }</a></td>
					<td><c:out value="${ row.typeName }" /></td>
					<td><input type="text" name="rank${ row.healthNo }" class="txt" style="width:60px;text-align:right" value="<c:out value='${ row.rank }' />"  /></td>
					<td><c:out value="${ row.statusName }" /></td>
					<td><fmt:formatDate value="${row.cdate}" pattern="${ DateTimeFormat }"/></td>
					<td><fmt:formatDate value="${row.udate}" pattern="${ DateTimeFormat }"/></td>
					<td>
						<c:if test="${ updateAuth eq 'Y' }">
							<input type="button" class="btnTypeB btnSizeB" onclick="modify('${ row.healthNo }')" value="수정">
						</c:if>
					</td>
				</tr>
					<c:set var="no" value="${ no - 1 }" />
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${ fn:length(list) > 0 }">
		<div class="btnArea">
			<a href="javascript:modifyRank()" class="btnTypeC btnSizeA"><span>정렬 일괄 수정</span></a>
		</div>
		</c:if>
		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
