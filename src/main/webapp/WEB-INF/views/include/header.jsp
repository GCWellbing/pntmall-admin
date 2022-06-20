<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	$(function(){
		$("#header .nav li").each(function(){
			if($("ul", this).length != 0){
				$(this).addClass("tree");

				$(">p", this).click(function(){
					$(this).parent().find(">ul").toggle();
					setHeight();
				});
			}
			$(this).find("ul li:last-child").addClass("last");
		});

		$("#header .nav li ul li").each(function() {
			if ($(this).hasClass("on")) {
				$(this).parent().show();
				return;
			}
		});

		$("#snb .nav>li").each(function(){
			if($(this).find('ul').length > 0){
				$(this).children('p').addClass('arrow');
			}
			if($(this).hasClass('on')){
				if($(this).find('ul').length > 0){
					$(this).find('ul').show();
					$(this).children('p').find('a').addClass('open');
				}
			}
		});
		$("#snb .nav>li p.arrow a").click(function(){
			$(this).parent().parent().find('ul').toggle();
			$(this).toggleClass('open');
		});
	});

	function getMenuUrl(menuNo) {
		/*
		$.ajax({
			method : "POST",
			url : "/include/url",
			data : { "menuNo" : menuNo },
			dataType : "json"
		})
		.done(function(json) {
			console.log("result : ", json);
			if(json.succeed) {
				document.location.href = json.param.url;
			} else {
				document.location.href = "/logout";
			}
		});
		*/
		document.location.href = "/url?menuNo=" + menuNo;
	}

</script>
<div id="header">
	<div class="logo"><div class="tableSet"><a href="/"><img src="/static/images/logo.png" alt="Dr PNT"></a></div></div>
	<div class="user">
		<div class="tableSet">
			<strong class="id">${ adminSession.adminId } ${ adminSession.name }</strong>
			<ul class="user_util">
				<li><a href="#" onclick="showPopup('/popup/myPage', '500', '450'); return false">내 정보 수정</a></li>
				<li><a href="/logout">LOGOUT</a></li>
			</ul>
		</div>
	</div>
	<div id="gnb">
		<ul class="nav">
			<c:forEach items="${ topList }" var="row">
				<li ${ rootInfo.menuNo eq row.menuNo ? 'class="on"' : '' }><a href="javascript:;" onclick="getMenuUrl('${ row.menuNo }')">${ row.name }</a></li>
			</c:forEach>
		</ul>
	</div>
</div><!-- //header -->
<div id="snb">
	<p class="title">${ rootInfo.name }</p>
	${ leftUl }
	<!--
	<ul class="nav">
		<li>
			<p><a href="">마스터 관리</a></p>
		</li>
		<li class="on">
			<p><a href="">전시상품 관리</a></p>
		</li>
		<li>
			<p><a href="#" onclick="return false">부가정보 관리</a></p>
			<ul>
				<li><a href="">영양성분 관리</a></li>
				<li><a href="">섭취대상 관리</a></li>
				<li><a href="">복용안내 관리</a></li>
			</ul>
		</li>
		<li>
			<p><a href="">카테고리 관리</a></p>
		</li>
		<li>
			<p><a href="">제품 배너 관리</a></p>
		</li>
		<li>
			<p><a href="">기획전 관리</a></p>
		</li>
	</ul>
	-->
</div>