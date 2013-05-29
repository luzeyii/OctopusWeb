<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>OctopusWeb 1.0</title>
</head>
<!--  body onload="getConsoleContent();" -->
<body onload="load()">

<div class="row-fluid">
		<div class="span4"><button value="click" id="ShowMsg">ShowMsg</button></div>
		<div class="span4"><button value="click" id="ShowMap">ShowMap</button></div>
		<div class="span4"><button value="click" id="ShowChart">ShowChart</button></div>
</div>
	
<div id="map" style="width: 750px; height: 500px"></div>   
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/chart/highcharts/highcharts.src.js"></script>
<script type="text/javascript" src="js/chart/highcharts/exporting.js"></script>
<script type="text/javascript" src="js/chart/highstock/highstock.src.js"></script>
<script type="text/javascript" src="js/table/jquery.dataTables.js"></script>
<script type="text/javascript" src="js/ui/jquery-ui-1.8.24.custom.min.js"></script>

 <script    
          src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=true;    
          key=AIzaSyDK9rppAJzOX4U0BpQOBMKR8S0wynF7OC0"    
          type="text/javascript"></script>  

<script type="text/javascript">
function load() {     //加载地图    
    if (GBrowserIsCompatible()) {     
        var map = new GMap2(document.getElementById("map"));     
        map.addControl(new GSmallMapControl());    //放大缩小    
        map.addControl(new GMapTypeControl());     //地图种类    
        map.enableScrollWheelZoom();    //启用鼠标滚轮    
        map.setCenter(new GLatLng(39.9000, 116.3000), 6);   //地图坐标 三个参数分别为 "纬度" "经度" "比例尺"    
       
        function createMarker(point, address,name,tel) {  //创建标记内容及标记的鼠标事件    
           var marker = new GMarker(point);    
           var html = '<div>'+    
               '<a >Name:'+ name +'</a><br/>'+    
               '<a >Address:'+address +'</a><br/>'+    
               '<a >telephone:'+tel +'</a>'+    
               '</div>';    
           GEvent.addListener(marker, "mouseover", function() {    
               marker.openInfoWindowHtml(html);    
           });    
           GEvent.addListener(marker, "mouseout", function() {    
               marker.closeInfoWindow();    
           });    
           GEvent.addListener(marker, "click", function() {    
               map.setCenter(point, 12);     
           });    
           return marker;    
       }    
       var point = new GLatLng(39.9000,116.3000);     // 设置标记    
       map.addOverlay(createMarker(point,'beijing','sh','123456'));//加入标记    
   }      
}        
</script>
</body>

</html>