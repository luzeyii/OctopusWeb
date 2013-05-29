
//存放全局变量
var timeout = 10000;
var colorArraySize = 10;
var colorQualityArray = [];
var canvasWidth = $("#canvas").attr("width");
var canvasHeight = $("#canvas").attr("height");
var xMax = 1000;
var yMax = 1000;
var moteRadius = 10;
//Chart的全局变量
var chartX;
//Request的全局变量
var moteLocationArray = [];
var selectedMoteid = -1;
var broadcast = false;

//页面加载时，获得MsgMaxID
$(function() {
	para = {action:"getInitID"};
	$.post("console",para,null,"html");
	$.post("chart",para,null,"html");
	
	//初始化colorQualityArray
	var colorVal = 100;
	for(var i = 0; i < colorArraySize; i ++) {
		var colorTmp = colorVal-10*i;
		colorQualityArray[i] = "rgba(250," + colorTmp + "," + colorTmp;
	}
	chartX = createChart();
	clearRequest();
	//set up the application
	showChart();
	getConsoleContent();
	showMap();
	
	
});

$("#broadcast").click(function() {
	if(this.checked == true) {
		broadcast = true;
		$("#battery").html("Battery: 0%");
		$("#moteid").html("All of motes selected");
		$("#sleepdutycycle").removeAttr("disabled");
		$("#awakedutycycle").removeAttr("disabled");
		$("#threshold").removeAttr("disabled");
		$("#periodsampling").removeAttr("disabled");
		$("#sleep").removeAttr("disabled");
		$("#awake").removeAttr("disabled");
		$("#modeauto").removeAttr("disabled");
		$("#modequery").removeAttr("disabled");
		$("#btnSleepDutyCycle").removeAttr("disabled");
		$("#btnAwakeDutyCycle").removeAttr("disabled");
		$("#btnThreshold").removeAttr("disabled");
		$("#btnPeriodSampling").removeAttr("disabled");
		$("#btnReading").removeAttr("disabled");
		$("#btnBootNetwork").removeAttr("disabled");
		//alert(selectedMoteid + " " + broadcast);
	}
	else {
		broadcast = false;
		selectedMoteid = -1;
		clearRequest();
	}
});

$("#sleepdutycycle").change(function() {
	$("#sleeplabel").html($(this).val());
});
$("#awakedutycycle").change(function() {
	$("#awakelabel").html($(this).val());
});
$("#threshold").change(function() {
	$("#thresholdlabel").html($(this).val());
});


$("#sleep").click(function() {
	var para={
			"action" :"setsleep",
			"moteid" : selectedMoteid,
			"broadcast" : broadcast
			};
	$.post("request",para,function(data){
		if(broadcast == false) {
			if(data == "") return;
 			var moteStatus = eval('('+ data +')');
 			refrashRequest(moteStatus);
		}
	},"html");
});

$("#awake").click(function() {
	var para={
			"action" :"setawake",
			"moteid" : selectedMoteid,
			"broadcast" : broadcast
			};
	$.post("request",para,function(data){
		if(broadcast == false) {
			if(data == "") return;
 			var moteStatus = eval('('+ data +')');
 			refrashRequest(moteStatus);
		}
	},"html");
});

$("#modeauto").click(function() {
	var para={
			"action" :"setmodeauto",
			"moteid" : selectedMoteid,
			"broadcast" : broadcast
			};
	$.post("request",para,function(data){
		if(broadcast == false) {
			if(data == "") return;
 			var moteStatus = eval('('+ data +')');
 			refrashRequest(moteStatus);
		}
	},"html");
});

$("#modequery").click(function() {
	var para={
			"action" :"setmodequery",
			"moteid" : selectedMoteid,
			"broadcast" : broadcast
			};
	$.post("request",para,function(data){
		if(broadcast == false) {
			if(data == "") return;
 			var moteStatus = eval('('+ data +')');
 			refrashRequest(moteStatus);
		}
	},"html");
});		

$("#btnSleepDutyCycle").click(function() {
	var para={
			"action" :"setsleepdutycycle",
			"moteid" : selectedMoteid,
			"broadcast" : broadcast,
			"value" : $("#sleepdutycycle").val()
			};
	$.post("request",para,function(data){
		if(broadcast == false) {
			if(data == "") return;
 			var moteStatus = eval('('+ data +')');
 			refrashRequest(moteStatus);
		}
	},"html");
});

$("#btnAwakeDutyCycle").click(function() {
	var para={
			"action" :"setawakedutycycle",
			"moteid" : selectedMoteid,
			"broadcast" : broadcast,
			"value" : $("#awakedutycycle").val()
			};
	$.post("request",para,function(data){
		if(broadcast == false) {
			if(data == "") return;
 			var moteStatus = eval('('+ data +')');
 			refrashRequest(moteStatus);
		}
	},"html");
});

$("#btnThreshold").click(function() {
	var para={
			"action" :"setthreshold",
			"moteid" : selectedMoteid,
			"broadcast" : broadcast,
			"value" : $("#threshold").val()
			};
	$.post("request",para,function(data){
		if(broadcast == false) {
			if(data == "") return;
 			var moteStatus = eval('('+ data +')');
 			refrashRequest(moteStatus);
		}
	},"html");
});

$("#btnPeriodSampling").click(function() {
	if($("#periodsampling").val() < 1024) {
		alert("the value can't be smaller than 1024ms");
		return;
	}
	var para={
			"action" :"setperiodsampling",
			"moteid" : selectedMoteid,
			"broadcast" : broadcast,
			"value" : $("#periodsampling").val()
			};
	$.post("request",para,function(data){
		if(broadcast == false) {
			if(data == "") return;
 			var moteStatus = eval('('+ data +')');
 			refrashRequest(moteStatus);
		}
	},"html");
});

$("#btnReading").click(function() {
	
	var para={
			"action" :"getreading",
			"moteid" : selectedMoteid,
			"broadcast" : broadcast
			};
	$.post("request",para,null,"html");
});

$("#btnBootNetwork").click(function() {
	
	var para={
			"action" :"setbootnetwork",
			"moteid" : selectedMoteid,
			"broadcast" : broadcast
			};
	$.post("request",para,null,"html");
});

var createChart = function() {
	 return new Highcharts.Chart({
         chart: {
             renderTo: 'Chart',
             defaultSeriesType: 'spline'
         },
         title: {
             text: 'Real Time Readings From Motes'
         },
         xAxis: {
             type: 'datetime',
             tickPixelInterval: 150,
             maxZoom: 20 * 1000,
             title : {
            	 text: 'Datetime'
             }
         },
         yAxis: {
             minPadding: 0.2,
             maxPadding: 0.2,
             title: {
                 text: 'Readings',
                 margin: 80
             }
         },
         series: []
     });        
};

function getLocalTime(nS) {     
    return new Date(parseInt(nS)).toLocaleString().substr(0,17);
}

//Chart部分.........
	$("#ShowChart").click(function() {
		showChart();
	});
	
	var showChart = function() {
		//new Date().getTime();
		var para = {action:"getReadings",when:"now"};
		var strtmp = "";
		$.post("chart",para,function(data){
			var jsons = eval('(' + data +')');
			if(jsons.moteid == null) return;
			var moteid = jsons.moteid;
			var datetime = jsons.datetime;
			var reading = jsons.reading;
			
			
			
			var options = {
					id : "default",
					name : "default",
					data : []
			};
			var sName = "Mote Default";
			var point = [];
			
			for(var i = 0; i< moteid.length; i ++) {
				//strtmp += transferTime(datetime[i]) + "/n";
				if(moteid[i] == 0) continue;
				
				strtmp += getLocalTime(datetime[i]) + " ";
				
				sName = "Mote " + moteid[i];
				options = chartX.get(sName);
				if(options == null) {
					options = {
							id : sName,
							name : sName,
							data : []
					};
					chartX.addSeries(options);
				}
				//options = chartX.get(sName);
				//point = [getLocalTime(datetime[i]),reading[i]];
				point = [(datetime[i] + 8*3600*1000),reading[i]];
				chartX.get(sName).addPoint(point,true,false);
			}
			//alert(strtmp);
		},"html");
		setTimeout("showChart()",3000);
	};
//map部分。。。。。。。。。。。。。。
$("#ShowMap").click(function() {
	showMap();
});

$("#canvas").click(function(ev) {
	//alert(ev.layerX + " " + ev.layerY);
 	var mx = ev.offsetX;
 	var my = ev.offsetY;
 	selectedMoteid = -1;
 	for(var i = 0; i < moteLocationArray.length; i ++ ) {
 		var mote = moteLocationArray[i];
 		if((mote.x - moteRadius <= mx && mx <= mote.x+moteRadius) && (mote.y-moteRadius <= my && my <= mote.y+moteRadius)) {
 			selectedMoteid = mote.moteid;
 			//alert(selectedMoteid);
 			break;
 		}	
 	}
 	
 	if(selectedMoteid != -1) {
 		var para = {action : "getMoteStatus", moteid : selectedMoteid};
 		$.post("request",para, function(data) {
 			if(data == "") return;
 			var moteStatus = eval('('+ data +')');
 			refrashRequest(moteStatus);
 		},"html");
 	}
 	else {
 		clearRequest();
 	}
});

var refrashRequest = function(moteStatus) {
	
	$("#moteid").html("Mote " + moteStatus.moteid + " selected");
	$("#broadcast").removeAttr("checked");
	$("#battery").html("Battery:" + moteStatus.battery+"%");
	if(moteStatus.requestmode == true) {
		$("#modeauto").attr("disabled","disabled");
		$("#modequery").removeAttr("disabled");
	}
	else {
		$("#modequery").attr("disabled","disabled");
		$("#modeauto").removeAttr("disabled");
	}
	if(moteStatus.sleepmode == true) {
		$("#sleep").attr("disabled","disabled");
		$("#awake").removeAttr("disabled");
	}
	else {
		$("#awake").attr("disabled","disabled");
		$("#sleep").removeAttr("disabled");
	}
	$("#sleepdutycycle").val(moteStatus.sleepdutycycle / 100);
	$("#awakedutycycle").val(moteStatus.awakedutycycle / 100);
	$("#threshold").val(parseInt(parseFloat(moteStatus.threshold) / 16384 * 100));
	$("#periodsampling").val(moteStatus.periodsampling);
	$("#sleeplabel").html(moteStatus.sleepdutycycle / 100);
	$("#awakelabel").html(moteStatus.awakedutycycle / 100);
	$("#thresholdlabel").html(parseInt(parseFloat(moteStatus.threshold) / 16384 * 100));
	$("#sleepdutycycle").removeAttr("disabled");
	$("#awakedutycycle").removeAttr("disabled");
	$("#threshold").removeAttr("disabled");
	$("#periodsampling").removeAttr("disabled");
	$("#btnSleepDutyCycle").removeAttr("disabled");
	$("#btnAwakeDutyCycle").removeAttr("disabled");
	$("#btnThreshold").removeAttr("disabled");
	$("#btnPeriodSampling").removeAttr("disabled");
	$("#btnReading").removeAttr("disabled");
	$("#btnBootNetwork").removeAttr("disabled");
	//$("#broadcast").removeAttr("checked");
	broadcast = false;
	//alert(selectedMoteid + " " + broadcast);
};

var clearRequest = function() {
	$("#moteid").html("No specific mote selected");
	$("#broadcast").removeAttr("checked");
	$("#battery").html("Battery: 0%");
	$("#modeauto").attr("disabled","disabled");
	$("#modequery").attr("disabled","disabled");
	$("#sleep").attr("disabled","disabled");
	$("#awake").attr("disabled","disabled");
	$("#sleepdutycycle").attr("disabled","disabled");
	$("#awakedutycycle").attr("disabled","disabled");
	$("#threshold").attr("disabled","disabled");
	$("#periodsampling").attr("disabled","disabled");
	$("#sleeplabel").html(0);
	$("#awakelabel").html(0);
	$("#thresholdlabel").html(0);
	$("#sleepdutycycle").val(0);
	$("#awakedutycycle").val(0);
	$("#threshold").val(0);
	$("#periodsampling").val(0);
	$("#btnSleepDutyCycle").attr("disabled","disabled");
	$("#btnAwakeDutyCycle").attr("disabled","disabled");
	$("#btnThreshold").attr("disabled","disabled");
	$("#btnPeriodSampling").attr("disabled","disabled");
	$("#btnReading").attr("disabled","disabled");
	$("#btnBootNetwork").attr("disabled","disabled");
	//$("#broadcast").removeAttr("checked");
	broadcast = false;
	//alert(selectedMoteid + " " + broadcast);
};

var getMoteLocationArray = function(jsonMotes) {
	var locationArray = [];
	for(var i = 0; i < jsonMotes.length; i ++ ){
		var mote = jsonMotes[i];
		
		locationArray[i] = {
				moteid : mote.moteid,
				x	: toVirtualX(mote.x),
				y : toVirtualY(mote.y)
		};
	}
	return locationArray;
}

var showMap = function() {
	
	$.post("map",
			null,
			function(data) {
				var jsonMotes = eval('(' + data + ')');
				moteLocationArray = getMoteLocationArray(jsonMotes);
				var canvas = document.getElementById("canvas");
				var context = canvas.getContext("2d");
				context.strokeStyle = "black";
				context.fillStyle ="white";
				context.fillRect(0,0,canvasWidth,canvasHeight);
				//context.strokeRect(0, 0, canvasWidth,canvasHeight);
				paintParentRoutes(jsonMotes,context);
				paintMotes(jsonMotes,context);
				setTimeout("showMap()",2000);
			},
			"html"
	);
};

var paintMotes = function(jsonMotes,context) {
	
	for(var i = 0; i < jsonMotes.length; i ++ ){
		var mote = jsonMotes[i];
		
		//绘制节点
		if(mote.moteid == 0) 
			context.fillStyle = "red";
		else
			context.fillStyle = "blue";
		var moteX = toVirtualX(mote.x);
		var moteY = toVirtualY(mote.y);
		context.beginPath();
		context.arc(moteX,moteY,moteRadius,0,Math.PI * 2,true);
		context.closePath();
		context.fill();
		
		//绘制节点参数
		var moteTextX = moteX + 2 * moteRadius;
		var textMoteID = "[ID=" + mote.moteid + "]";
		var textReading = "[Reading="+ mote.reading +"]";
		var textSampling = "[SamplingPeriod=" + mote.samplingPeriod +"]";
		//context.fillStyle = "blue";
		context.fillText(textMoteID,moteTextX,moteY);
		context.fillText(textReading,moteTextX,moteY+12);
		context.fillText(textSampling,moteTextX,moteY+24);
		
	}
};
var paintParentRoutes = function(jsonMotes,context) {
	
	for(var i = 0; i < jsonMotes.length; i ++ ){
		
		var mote = jsonMotes[i];
		/*var strTmp = mote.moteid + " " + mote.parentid + " " +
					mote.x + " " + mote.y + " " + mote.quality + " " +
					mote.timeSinceLastTimeSeen;
		alert(strTmp);*/
		var parentMote = null;
		if(mote.moteid == mote.parentid)
			continue;
		
		for(var j = 0; j < jsonMotes.length; j ++) {
			if(mote.parentid == jsonMotes[j].moteid) {
				parentMote = jsonMotes[j];
				break;
			}
		}
		if(parentMote == null) {
			$("#console").append("Parent mote (id=" + mote.parentid +") from mote (id="+mote.moteid+") not found\n");
			continue;
		}
		
		//绘制路由
		context.lineWidth = 4;
		var tmp = parseFloat(mote.quality) / 65535 * (colorArraySize - 1);
		var colorIndex = parseInt(tmp);
		var alphe =1 - parseFloat(mote.timeSinceLastTimeSeen) / timeout;
		var colorStr = colorQualityArray[colorIndex] + "," + alphe + ")";
		context.strokeStyle = colorStr;
		context.moveTo(toVirtualX(mote.x),toVirtualY(mote.y));
		context.lineTo(toVirtualX(parentMote.x),toVirtualY(parentMote.y));
		context.stroke();
		//绘制路由文字
		var textX = toVirtualX( (mote.x + parentMote.x) / 2);
		var textY = toVirtualY((mote.y + parentMote.y) / 2 );
		var textRoute = "[ID=" + mote.moteid + "->ID=" + parentMote.moteid + "]";
		var textQuality = "[Quality=" + mote.quality + "]";
		var textTimeSinceLastTimeSeen = "[LastTimeSeen=" + mote.timeSinceLastTimeSeen + "]";
		context.fillStyle = colorStr;
		context.font = "10px";
		context.fillText(textRoute,textX,textY);
		context.fillText(textQuality,textX,textY+12);
		context.fillText(textTimeSinceLastTimeSeen,textX,textY+24);
		
	}
};

var toVirtualX = function(x) {
	
	var tmp = canvasWidth*x/xMax;
	if (tmp < moteRadius)					// we prevent x to go past the panel
		return moteRadius;
	else if (tmp>canvasWidth-moteRadius)
		return canvasWidth-moteRadius;
	else
		return tmp;
};

var toVirtualY = function(y) {
	
	var tmp = canvasHeight*y/yMax;
	if (tmp < moteRadius)					// we prevent x to go past the panel
		return moteRadius;
	else if (tmp>canvasHeight-moteRadius)
		return canvasHeight-moteRadius;
	else
		return tmp;
};

//message部分
$("#ShowMsg").click(function() {
	//alert("click");
	getConsoleContent();
	
});
//获得Message，并追加到TextArea
var getConsoleContent = function() {
	var strType = "";
	
	var checkboxes = $("input[type='checkbox'][name='msgType']").get();
	for(var i = 0; i < checkboxes.length; i ++) {
		if(checkboxes[i].checked) 
			strType += checkboxes[i].value + " ";
	}
	
	var para = {action:"getMsg", msgType: strType};
	$.post("console",para,function(data, statusText) {
		$("#console").append(data);
		setTimeout("getConsoleContent()", 5000);
	},"html");
};