//앞뒤 공백을 자른다..
String.prototype.trim = function() {
    var format = /^\s*/g;
    var str = this;
    str = str.replace(format, "");
    format = /\s*$/g;
    str = str.replace(format, "");

    return str;
};

// 문자열이 이메일 형식인지 판별한다.
String.prototype.isEmail = function() {
    var format = /^((\w|[\-\.])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/;
    return (this.search(format) > -1);
};

String.prototype.isNumeric = function() {
    var format = /^[0-9]+$/;
    return (this.search(format) > -1);
};

// 문자열의 바이트를 리턴한다.
String.prototype.getBytes = function() {
	var temp;
	var mycount = 0;

	for(var k = 0 ; k < this.length ; k++ ){
		temp = this.charAt(k);

		if( escape(temp).length > 4 ) {
			mycount += 2;
		}
		else mycount++;
	}

	return mycount;
};

//숫자를 money포맷으로 3자리씩 끊어 표현한다.
String.prototype.formatMoney = function() {
	if (!this.isNumeric()) return null;

	var start = 0;
	var distance =  parseInt(this.length % 3);
	var nComma =  parseInt(this.length / 3);

	if (distance == 0){
		distance = 3;
		nComma -= 1;
	}

	var arr = new Array();
	for (var i=0; i<nComma + 1; i++) {
		arr[i] = this.substring(start, start + distance);

		start += distance;
		distance = 3;
	}

	var sReturn = "";
	var isFirst = true;
	for (var i=0; i<arr.length; i++) {
		sReturn += (isFirst ? "" : ",") + arr[i];
		isFirst = false;
	}

	return sReturn;
};

//숫자를 money포맷으로 3자리씩 끊어 표현한다.
Number.prototype.formatMoney = function() {
	return this.toString().formatMoney();
};

String.prototype.isSSN = function() {
    var sum = 0 ;
    sum = this.charAt(0) * 2 + this.charAt(1) * 3 + this.charAt(2) * 4 + this.charAt(3) * 5 +
          this.charAt(4) * 6 + this.charAt(5) * 7 + this.charAt(6) * 8 + this.charAt(7) * 9 +
          this.charAt(8) * 2 + this.charAt(9) * 3 + this.charAt(10) * 4 + this.charAt(11) * 5;

    if (sum == "0") return false;
    else {
        sum = 11 - sum % 11 ;
        if (sum > 9) sum = sum - 10;

        if (sum == this.charAt(12)) return true;
        else return false;
    }
};

//yyyymmdd 검사
String.prototype.isDate = function() {
	if (this.length != 8) return false;

	var y = parseInt(this.substr(0, 2), 10);
	var m = parseInt(this.substr(4, 2), 10);
	var d = parseInt(this.substr(6, 2), 10);

	var limit_day;
	switch (m) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			limit_day = 31;
			break;
		case 2:
			if ((y - 2008) % 4 == 0) limit_day = 29;
			else limit_day = 28;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			limit_day = 30;
			break;
		default:
			return false;
	}

	if (d > limit_day) return false;
	if (d < 1) return false;

	return true;
};

Date.prototype.format = function (f) {

    if (!this.valueOf()) return " ";

    var weekKorName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];

    var weekKorShortName = ["일", "월", "화", "수", "목", "금", "토"];

    var weekEngName = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];

    var weekEngShortName = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

    var d = this;

    return f.replace(/(yyyy|yy|MM|dd|KS|KL|ES|EL|HH|hh|mm|ss|a\/p)/gi, function ($1) {

        switch ($1) {

            case "yyyy": return d.getFullYear(); // 년 (4자리)

            case "yy": return (d.getFullYear() % 1000).zf(2); // 년 (2자리)

            case "MM": return (d.getMonth() + 1).zf(2); // 월 (2자리)

            case "dd": return d.getDate().zf(2); // 일 (2자리)

            case "KS": return weekKorShortName[d.getDay()]; // 요일 (짧은 한글)

            case "KL": return weekKorName[d.getDay()]; // 요일 (긴 한글)

            case "ES": return weekEngShortName[d.getDay()]; // 요일 (짧은 영어)

            case "EL": return weekEngName[d.getDay()]; // 요일 (긴 영어)

            case "HH": return d.getHours().zf(2); // 시간 (24시간 기준, 2자리)

            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2); // 시간 (12시간 기준, 2자리)

            case "mm": return d.getMinutes().zf(2); // 분 (2자리)

            case "ss": return d.getSeconds().zf(2); // 초 (2자리)

            case "a/p": return d.getHours() < 12 ? "오전" : "오후"; // 오전/오후 구분

            default: return $1;

        }

    });

};

String.prototype.string = function (len) { var s = '', i = 0; while (i++ < len) { s += this; } return s; };

String.prototype.zf = function (len) { return "0".string(len - this.length) + this; };

Number.prototype.zf = function (len) { return this.toString().zf(len); };

String.prototype.replaceAll = function (searchStr, replaceStr) {
    return this.split(searchStr).join(replaceStr);
}

//input field에 숫자만 넣어야 할경우에  onKeyPress이벤트에서 호출한다..
//사용예 : <input type=text name="aField" onKeyPress="return isNumberPressed()">
function isNumberPressed() {
 return !(event.keyCode < 48 || event.keyCode > 57);
}

// null과 빈문자열을 디폴트 처리
function nevl(str, sDefault) {
	if (str == null || str == undefined || str.trim() == "") {
		return sDefault;
	}
	else {
		return str;
	}
}

/* Korean initialisation for the jQuery calendar extension. */
/* Written by DaeKwon Kang (ncrash.dk@gmail.com). */
if ($.datepicker != null) {
	jQuery(function($){
		$.datepicker.regional['ko'] = {
			closeText: '닫기',
			prevText: '이전달',
			nextText: '다음달',
			currentText: '오늘',
			monthNames: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)',
			'7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
			monthNamesShort: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)',
			'7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
			dayNames: ['일','월','화','수','목','금','토'],
			dayNamesShort: ['일','월','화','수','목','금','토'],
			dayNamesMin: ['일','월','화','수','목','금','토'],
			weekHeader: 'Wk',
			dateFormat: 'yy.mm.dd',
			firstDay: 0,
			isRTL: false,
			showMonthAfterYear: true,
			yearSuffix: '년'};
		$.datepicker.setDefaults($.datepicker.regional['ko']);
		$.datepicker.setDefaults({
			showOn: "button",
			buttonImage: "/static/images/btn_calendar.gif",
			buttonImageOnly: true,
			buttonText: ""
		});
	});
}

function ajaxSubmit(form, callback) {
	var isJQueryObject = form instanceof jQuery;
	var jForm = (isJQueryObject ? form : $(form));

	var formData = jForm.serialize();

	/*action 페이지에서는 결과 json을 출력해야 한다. 예 {"isOk" : true, "msg" : "등록되었습니다."}*/
	$.ajax({
		type : "POST",
		url : jForm.attr("action"),
		data : formData,
		dataType : "json",
		success : function(result) {
			callback(result);
		},
		fail : function() {
			callback({isOk : false, msg : "서버오류가 발생했습니다."});
		}
	});
}

/*구글 크롬의로 디버깅시, 타부라우저의 console 객체 스킵*/
if (!window.console) console = {};
console.log = console.log || function(){};
console.warn = console.warn || function(){};
console.error = console.error || function(){};
console.info = console.info || function(){};

function RepString(source,be_word,af_word){
	var slen = source.length;
	var wlen = be_word.length;

	if ( source.length == 0) {
		return source;
	}
	if ( be_word.length == 0 ) {
		return source;
	}

	var after_source='';

	for (var i=0; i < slen;i++){
		var tmp =source.substring(i,i+wlen);
		var tmp_2 = source.substring(i,i+1);
		if ( tmp == be_word ) {
			after_source = after_source + af_word;
			i = (i+wlen)-1;
		}
		else {
			after_source = after_source +tmp_2;
		}
   }
   return after_source;
}

function disableScreen() {
	$("body").append("<div class='bgLayer'><img src='/static/images/loading.gif'></div>");
	$(".bgLayer").css("height", $(document).height() + "px").show();
	$(".bgLayer img").css("top", (parseInt($(window).height()/2) + parseInt($(window).scrollTop()) - 50) + "px");
	$(".bgLayer img").css("left", (parseInt($(window).width()/2) + parseInt($(window).scrollLeft()) - 50) + "px");
}

function enableScreen() {
	$(".bgLayer").remove();
}

function loadingAdd(obj) {
	if(obj == "full"){
		$("body").prepend("<div id=\"loadingArea\" class=\"fullLoad\" style=\"z-index:100010\"><img src=\"/static/images/loading.gif\" alt=\"loading...\"></div>");
	} else {
		if($(obj).css("position") != "relative" && $(obj).css("position") != "absolute"){
			$(obj).css("position", "relative");
		}
		$(obj).prepend("<div id=\"loadingArea\"><img src=\"/static/images/loading.gif\" alt=\"loading...\"></div>");
	}
} 

function loadingRemove(obj) {	
	if(obj != undefined) {
		$(obj).find("#loadingArea").remove();
	} else {		
		$("#loadingArea").remove();
	}
}

jQuery.fn.serializeObject = function() {
	var arrayData, objectData;
	arrayData = this.serializeArray();
	objectData = {};

	$.each(arrayData, function() {
		var value;

		if (this.value != null) {
			value = this.value;
		} else {
			value = '';
		}

		if (objectData[this.name] != null) {
			if (!objectData[this.name].push) {
				objectData[this.name] = [objectData[this.name]];
			}

			objectData[this.name].push(value);
		} else {
			objectData[this.name] = value;
		}
	});

	return objectData;
};

function isSequencalDate(smallDate, largeDate) {
	smallDate = smallDate.replaceAll("-", "");
	smallDate = smallDate.replaceAll(".", "");
	smallDate = smallDate.replaceAll(" ", "");
	smallDate = smallDate.replaceAll(":", "");

	largeDate = largeDate.replaceAll("-", "");
	largeDate = largeDate.replaceAll(".", "");
	largeDate = largeDate.replaceAll(" ", "");
	largeDate = largeDate.replaceAll(":", "");

	return parseInt(smallDate) < parseInt(largeDate);
}

function checkSequencalDate(smallDateObj, largeDateObj) {
	if (!isSequencalDate($(smallDateObj).val(), $(largeDateObj).val())) {
		alert("종료일이 시작일보다 빠른 날짜입니다.");
		return false;
	}
	return true;
}