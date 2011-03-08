

function positionSelectUpdateState(jSelect) {
	var jDiv = jSelect.parents('.positionSelect');
	var jOther = jDiv.find('input.other');
	var jSelect = jDiv.find('select');
	if(jSelect.attr('value') == 'other') {
		jOther.show();
		jSelect.hide();
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

	//document.getElementById('localisation').value=+position.coords.latitude+';'+position.coords.longitude;
	$('input.lat').each(function(i, elm) {
		$(this).attr('value', position.coords.latitude);
	});
	$('input.lng').each(function(i, elm) {
		$(this).attr('value', position.coords.longitude);
	});
	/*
	$('.positionSelect select option').each(function(index, obj) {
		var jObj = $(obj);
		console.log(jObj.val());
		if(jObj.val() == 'here') {
			jObj.attr("disabled", "");
		}
	});
	*/
	$('.positionSelect select option[value="here"]').each(function(index, obj) {
		var jObj = $(obj);
		jObj.html("Ici");
		jObj.removeAttr("disabled");

	});
	
}

function geolocalisation_error(msg) {
	$('.positionSelect select option[value="here"]').each(function(index, obj) {
		var jObj = $(obj);
		jObj.html("Ici - Detection de la position echouee");
	});
}

function geolocalisation_detectPosition() {
	if (geolocalisation_isSupported()) {
		
		$('.positionSelect select option[value="here"]').each(function(index, obj) {
			var jObj = $(obj);
			jObj.html("Ici - Detection en cours...");
		});
		
		//maximum age of 10 minutes = 600000 milliseconds
		navigator.geolocation.getCurrentPosition(geolocalisation_success, geolocalisation_error, {maximumAge:600000, timeout:10000});
	} else {
		$('.positionSelect select option[value="here"]').each(function(index, obj) {
			var jObj = $(obj);
			jObj.html("Ici - Detection non supportee");
		});
		//error('geolocalisation not supported');
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
	var posDetected=false;
	$('.detectPosition').each(function() {
		if(posDetected == false) {
			posDetected=true;
			geolocalisation_detectPosition();
		}
	});
	
	
	$('.positionSelect select option[value="here"]').each(function(index, obj) {
		var jObj = $(obj);
		jObj.attr("disabled", "disabled");
	});
	
	
});
