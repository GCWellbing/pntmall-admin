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
		v.add("img", {
			empty : "이미지를 입력하세요."
		});
		v.add("content", {
			empty : "내용을 입력하세요.",
			max : "100"
		});

		$("#submitBtn").click(function() {
			if(v.validate()) {
				disableScreen();
				ef.proc($("#editForm"), function(result) {
					alert(result.message);
					if(result.succeed) location.href = "list";
					enableScreen();
				});
			}
		});

		$("#resetBtn").click(function() {
			$("#editForm")[0].reset();
			$("#editForm").attr("action", "create");
			$("input[name=img]").val("");
    		$("#imgTag").attr("src", "");
    		$("#imgTag").hide();
		});

		$("#subList").on("click", ".btnUp", function() {
			var currObj = $(this).parents("tr");
			var idx = $(currObj).index();

			if (idx > 0) {
				var upperObj = currObj.prev();
				upperObj.before($("<tr></tr>").html(currObj.html()));

				//jquery 버그? 변경된 값은 html()에서 제너레이트 되지 않는다. 변경된 값을 다시 재지정.
				$("#subList input[name=code2]", upperObj.prev()).val($("#subList input[name=code2]", currObj).val());

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
				$("#subList input[name=code2]", belowObj.next()).val($("#subList input[name=code2]", currObj).val());

				currObj.remove();
			}
		});
	});

	function modify(doseNo) {
		disableScreen();
		$.getJSON("info?doseNo=" + doseNo, function(json) {
			$("#editForm input[name=doseNo]").val(json.param.info.doseNo);
			$("input[name=img]").val(json.param.info.img);
			$("input[name=content]").val(json.param.info.content);
			$("input[name=rank]").val(json.param.info.rank);
			$("input[name=status]:input[value=" + json.param.info.status + "]").prop("checked", true);
			$("#editForm").attr("action", "modify");

			$("input[name=img]").val(json.param.info.img);
    		$("#imgTag").attr("src", json.param.info.img);
    		$("#imgTag").show();

    		enableScreen();
		});
	}

	function remove(doseNo) {
		if(confirm("삭제하시겠습니까?")) {
			disableScreen();
			$("#editForm input[name=doseNo]").val(doseNo);
			$("#editForm").attr("action", "remove");

			ef.proc($("#editForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list";
				enableScreen();
			});
		}
	}

	function modifyRank() {
		if(confirm("정렬 일괄 수정하시겠습니까?")) {
			disableScreen();
			ef.proc($("#listForm"), function(result) {
				alert(result.message);
				if(result.succeed) location.href = "list";
				enableScreen();
			});
		}
	}

	function uploadImage(field) {
		if($("input[name=" + field + "]").val() != '') {
	        disableScreen();

	        var action = "/upload/upload?field=" + field + "&path=dose&fileType=image";
	        ef.multipart($("#editForm"), action, field, function(result) {

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
			<input type="hidden" name="doseNo" value="">
		<div class="white_box">
			<table class="board">
				<colgroup>
					<col width="10%">
					<col width="">
				</colgroup>
				<tr>
					<th>이미지</th>
					<td>
						<input type="file" name="imgFile" onchange="uploadImage('imgFile')" style="width:80%">
						<p class="txt">* Image Size : 72*72(jpg) / Max 10Mbyte</p>
						<input type="hidden" name="img" value="">
						<img src="" id="imgTag" style="display:none">
					</td>
				</tr>
				<tr>
					<th>내용</th>
					<td>
						<input type="text" name="content" value="" style="width:100%">
					</td>
				</tr>
				<tr>
					<th>정렬 순서</th>
					<td>
						<input type="text" name="rank" value="" style="width:100px">
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
				<col width="50px">
				<col width="">
				<col width="">
				<col width="">
				<col width="100px">
				<col width="200px">
			</colgroup>
			<thead>
				<tr>
					<th>No.</th>
					<th>썸네일</th>
					<th>내용</th>
					<th>상태</th>
					<th>순서</th>
					<th>관리</th>
				</tr>
			</thead>
			<tbody id="subList">
				<c:forEach items="${ list }" var="row">
				<tr>
					<td><c:out value="${ row.doseNo }" /></td>
					<td><img src="<c:out value="${ row.img }" />" style="width:100px"></td>
					<td><c:out value="${ row.content }" /></td>
					<td><c:out value="${ row.statusName }" /></td>
					<td>
						<input type="button" class="btnUp" value="위로">
						<input type="button" class="btnDown" value="아래로">
						<input type="hidden" name="doseNo" value="${ row.doseNo }">
					</td>
					<td>
						<c:if test="${ updateAuth eq 'Y' }">
							<input type="button" class="btnTypeB btnSizeB" onclick="modify('${ row.doseNo }')" value="수정">
							<input type="button" class="btnTypeC btnSizeB" onclick="remove('${ row.doseNo }')" value="삭제">
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="btnArea">
			<a href="javascript:modifyRank()" class="btnTypeC btnSizeA"><span>정렬 일괄 수정</span></a>
		</div>
		</form>
	</div><!-- //container -->
	<c:import url="/include/footer" />
</div><!-- //wrapper -->

</body>
</html>
