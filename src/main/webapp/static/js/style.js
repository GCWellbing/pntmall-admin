var cWidth, cHeight, pLeft, pTop;
$(function(){
	//컨텐츠 사이즈
	//setWidth();
	//setHeight();
	//pTop = $(document).height() / 2;
	//$(".control").css("top", pTop + "px");
	//네비게이션 영역 컨트롤
	/*$(".control").draggable({
		axis: "x",
		containment: [180, , 500, ],
		drag : dragSize
	});*/

	var isNav = true;
	$(".control").click(function(e){
		if(isNav){
			$("#wrapper").addClass("hideGnb");
			//$("#header").css("width", "0px").css("min-width","0px");
			//$("#header .nav, #header .logo, #header .title").css("display","none");
			//$(this).css("left", "0px");
			//setWidth();
			isNav = false;
		} else {
			$("#wrapper").removeClass("hideGnb");
			//$("#header").css("width", "180px").css("min-width","180px");
			//$("#header .nav, #header .logo, #header .title").css("display","block");
			//$(this).css("left", "180px");
			//setWidth();
			isNav = true;
		}

		e.preventDefault();
	});

	/* placeholder */
	$('[placeholder]').focus(function() {
	    var input = $(this);
	    if (input.val() == input.attr('placeholder')) {
			input.val('');
			input.removeClass('placeholder');
		}
	})
	.blur(function() {
		var input = $(this);
		if (input.val() == '' || input.val() == input.attr('placeholder')) {
			input.addClass('placeholder');
			input.val(input.attr('placeholder'));
		}
	})
	.blur().parents('form').submit(function() {
		$(this).find('[placeholder]').each(function() {
			var input = $(this);
			if (input.val() == input.attr('placeholder')) {
				input.val('');
			}
		});
	});

	$("#popWrapper").each(function(){
		var contHeight = $(window).height() - parseInt($(this).css("padding-top")) - parseInt($(this).css("padding-bottom")) - $(this).find("h1").height() - parseInt($(this).find("h1").css("margin-bottom")) - $(this).find(".content").next(".btnArea").height() - parseInt($(this).find(".content").next(".btnArea").css("margin-top"))
		$(this).find(".content").css("max-height", contHeight)
	});
});

$(window).resize(function(){
	//setWidth();
});

function setWidth(){
	cWidth = $("#wrapper").width() - $("#header").width() - 30;
	cWidth2 = $("#wrapper").width() - $("#header").width() - 40;
	$("#gnb").css("width", cWidth + "px");
	$("#container").css("width", cWidth2 + "px");
}

function setHeight(){
	cHeight = $("#gnb").height() + $("#container").height() + 50;
	//console.log($("#header .nav").height());
	if(cHeight < $("#header .nav").height() + 165){
		cHeight = $("#header .nav").height() + 165;
	}
	$("#header").css("min-height", cHeight + "px");
}
function dragSize(){
	pLeft = parseInt($(".control").css("left"));
	cWidth = $("#wrapper").width() - pLeft - 50;
	$("#header").css("width", pLeft + "px");
	$("#container").css("width", cWidth + "px");
}


/*textarea*/
function textareaResize(obj){
	var D_target = $(obj);
	var lineHeigh = parseInt(D_target.css("line-height"));
	var maxHeight = parseInt(D_target.css("max-height"))  ? parseInt(D_target.css("max-height")) : false;
	if(D_target.val() != ""){
		D_target.css("height", D_target.prop('scrollHeight') + "px")
	}
	console.log(D_target.prop('scrollHeight'))
	D_target.on("keyup keydown", function(e){
		var keyCode = e.originalEvent.keyCode;
		$(this).height(lineHeigh);
		var scrollHeight = $(this).prop('scrollHeight');
		if($(this).css("box-sizing") == "border-box"){
			console.log($(this).css("border-top-width"))
		}
		$(this).css("height", scrollHeight + "px");
		if(maxHeight && maxHeight < scrollHeight){
			$(this).css("overflow", "auto");
			if(!(keyCode >= 37 && keyCode <= 40)){
				scrollNum = scrollHeight - maxHeight;
				$(this).scrollTop(scrollNum);
			}
		}
	});
}

browser = (function(){
	var a = navigator.userAgent.toLowerCase();
	var b,v;
	if(a.indexOf("safari/") > -1) {
		b = "safari";
		var s = a.indexOf("version/");
		var l = a.indexOf(" ", s);
		v = a.substring(s+8, l);
	}
	if(a.indexOf("chrome/") > -1) {
		b = "chrome";
		var ver = /[ \/]([\w.]+)/.exec(a)||[];
		v = ver[1];
	}
	if(a.indexOf("firefox/") > -1) {
		b = "firefox";
		var ver = /(?:.*? rv:([\w.]+)|)/.exec(a)||[];
		v = ver[1];
	}
	if(a.indexOf("opera/") > -1) {
		b = "opera";
		var ver = /(?:.*version|)[ \/]([\w.]+)/.exec(a)||[];
		v = ver[1];
	}
	if((a.indexOf("msie") > -1) || (a.indexOf(".net") > -1)) {
		b = "msie";
		var ver = /(?:.*? rv:([\w.]+))?/.exec(a)||[];
		if(ver[1])
		 v = ver[1];
		else{
		 var s = a.indexOf("msie");
		 var l = a.indexOf(".", s);
		 v = a.substring(s+4, l);
	 }
}
return { name: b || "", version: v || 0};
}());

var onImgLoad = function(selector, callback){
    $(selector).each(function(){
        if (this.complete || /*for IE 10-*/ $(this).height() > 0) {
            callback.apply(this);
        }
        else {
            $(this).on('load', function(){
                callback.apply(this);
            });
        }
    });
};

function showPopup(url, width, height){
	var leftPosition = (screen.width) / 2 - (width / 2 );
	var topPosition = (screen.height) / 2 - (height / 2);

	var win = window.open(url, '', "width=" + width + "px,height=" + height + "px,left=" + leftPosition + "px,top=" + topPosition + "px");
	win.focus();
}


function showPopupTarget(url, width, height, target){
	var leftPosition = (screen.width) / 2 - (width / 2 );
	var topPosition = (screen.height) / 2 - (height / 2);

	var win = window.open(url, target, "width=" + width + "px,height=" + height + "px,left=" + leftPosition + "px,top=" + topPosition + "px");
	win.focus();
}