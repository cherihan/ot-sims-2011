
function gmapInitialize() {
	if(__gmap_polyline_points.length <= 0){
		alert('no points specified');
		return false;
	}
		
	var tabSize = __gmap_polyline_points.length;
	mapStartLat = (__gmap_polyline_points[tabSize-1]['lat'] + __gmap_polyline_points[0]['lat'] ) / 2;
	mapStartLng = (__gmap_polyline_points[tabSize-1]['lng'] + __gmap_polyline_points[0]['lng'] ) / 2;
	
	
	
	var distance = Math.sqrt(Math.pow(__gmap_polyline_points[tabSize-1]['lat'] - __gmap_polyline_points[0]['lat'], 2) + Math.pow(__gmap_polyline_points[tabSize-1]['lng'] - __gmap_polyline_points[0]['lng'], 2));
	
	
	if(distance < 0.01) {
		__gmap_zoom=16;
	}else if(distance < 0.1) {
		__gmap_zoom=14;
	}else if(distance < 0.5) {
		__gmap_zoom12;
	}else if(distance < 1) {
		__gmap_zoom10;
	}else if(distance < 2) {
		__gmap_zoom=9;
	}else if(distance < 3) {
		__gmap_zoom=8;
	}else if(distance < 4) {
		__gmap_zoom=7;
	}else if(distance < 5) {
		__gmap_zoom=6;
	}else if(distance < 6) {
		__gmap_zoom=5;
	}
	
	var myLatlng = new google.maps.LatLng(mapStartLat, mapStartLng);
	var myOptions = {
		zoom : __gmap_zoom,
		center : myLatlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	
	
	
	var mapDiv = document.getElementById("map_canvas");
	var jMapDiv = $(mapDiv);
	
	var winWidth = screen.width;
	var winHeight = screen.height;
	
	winWidth = $('body').width();
	
	var divWidth=300;
	var divHeight=300;
	
	if(winWidth > 768) {
		divWidth = 0.8 * winWidth;
		divHeight = 500;
	}else{
		divWidth = 0.8 * winWidth;
		divHeight = Math.min(0.8 * winWidth, winHeight);
	}
	
	jMapDiv.css('width', divWidth+'px');
	jMapDiv.css('height', divHeight+'px');
	jMapDiv.css('margin', 'auto');
	
	var map = new google.maps.Map(mapDiv,myOptions);

	var flightPlanCoordinates = new Array();
	for(i=0 ; i < __gmap_polyline_points.length ; i++ ) {
		flightPlanCoordinates.push(new google.maps.LatLng(__gmap_polyline_points[i]['lat'], __gmap_polyline_points[i]['lng']));
	}
	
	var flightPath = new google.maps.Polyline({
		path : flightPlanCoordinates,
		strokeColor : "#FF0000",
		strokeOpacity : 1.0,
		strokeWeight : 4
	});

	flightPath.setMap(map);

}

function gmapLoadScript() {
	var script = document.createElement("script");
	script.type = "text/javascript";
	script.src = "http://maps.google.com/maps/api/js?sensor=false&callback=gmapInitialize";
	document.body.appendChild(script);
}

var __gmap_polyline_points = new Array();
var __gmap_zoom = 7;

function initializeGoogleMap(arrayListOfPoints) {
	__gmap_polyline_points = arrayListOfPoints;
	//TODO auto calcultate __gmap_zoom
	gmapLoadScript();
}


