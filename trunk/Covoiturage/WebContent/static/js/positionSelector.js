

function positionSelectUpdateState(jSelect) {
	var jDiv = jSelect.parents('.positionSelect');
	var jOther = jDiv.find('input.other');
	if(jSelect.attr('value') == 'other') {
		jOther.show();
	}else{
		jOther.hide();
	}
}


var __geolocalisation_success_detected = false;

function geolocalisation_success(position) {
	if (__geolocalisation_success_detected) {
		// not sure why we're hitting this twice in FF, I think it's to do with a cached result coming back
		return;
	}
	
	__geolocalisation_success_detected=true;
	
	alert('Position : '+position.coords.latitude+' - '+position.coords.longitude); 
	//document.getElementById('localisation').value=+position.coords.latitude+';'+position.coords.longitude;
	$('input.lat').each(function(i, elm) {
		$(this).attr('value', position.coords.latitude);
	});
	$('input.lng').each(function(i, elm) {
		$(this).attr('value', position.coords.longitude);
	});
	
}

function geolocalisation_error(msg) {
	alert('your position cannot be determined');
	
}

function geolocalisation_detectPosition() {
	if (geolocalisation_isSupported()) {
		//maximum age of 10 minutes = 600000 milliseconds
		navigator.geolocation.getCurrentPosition(geolocalisation_success, geolocalisation_error, {maximumAge:600000});
	} else {
		error('geolocalisation not supported');
	}
}

function geolocalisation_isSupported() {
	return navigator.geolocation;
}


$(function() {
	$('.positionSelect').each(function (i, elm) {
		var jDiv = $(this);
		var jSelect = jDiv.find('select');

		var jOther = jDiv.find('input.other');
		var jLat = jDiv.find('input.lat');
		var jLng = jDiv.find('input.lng');
		
		jSelect.change(function() {
			var jSelect = $(this);
			positionSelectUpdateState(jSelect);
		});
		positionSelectUpdateState(jSelect);
		
	});
	
	
	geolocalisation_detectPosition();
	
});
