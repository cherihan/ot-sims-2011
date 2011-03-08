
function gmapInitialize() {
	if(__gmap_polyline_points.length <= 0){
		alert('no points specified');
		return false;
	}
		
	var myLatlng = new google.maps.LatLng(__gmap_polyline_points[0]['lat'], __gmap_polyline_points[0]['lng']);
	var myOptions = {
		zoom : __gmap_zoom,
		center : myLatlng,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	
	var map = new google.maps.Map(document.getElementById("map_canvas"),myOptions);

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


