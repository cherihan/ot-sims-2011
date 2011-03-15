

function positionSelectUpdateState(jSelect) {
	var jDiv = jSelect.parents('.positionSelect');
	var jOther = jDiv.find('input.other');
	var jSelect = jDiv.find('select');
	var val = jSelect.attr('value');
	if(val == undefined || val == "") {
		val = jSelect.val();
	}
	if(val == 'other') {
		jOther.show();
		//jSelect.hide();
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
		$(this).val(position.coords.latitude);
	});
	$('input.lng').each(function(i, elm) {
		$(this).val(position.coords.longitude);
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
		jObj.html("Ici - Erreur de localisation");
	});
}

function geolocalisation_detectPosition() {
	if (geolocalisation_isSupported()) {
		
		$('.positionSelect select option[value="here"]').each(function(index, obj) {
			var jObj = $(obj);
			jObj.html("Ici - Localisation en cours...");
		});
		
		//maximum age of 10 minutes = 600000 milliseconds
		navigator.geolocation.getCurrentPosition(geolocalisation_success, geolocalisation_error, {maximumAge:600000, timeout:10000});
	} else {
		$('.positionSelect select option[value="here"]').each(function(index, obj) {
			var jObj = $(obj);
			jObj.html("Ici - Localisation impossible");
		});
		//error('geolocalisation not supported');
	}
}

function geolocalisation_isSupported() {
	return navigator.geolocation;
}

function positionSelectUpdateStateAll() {
	$('.positionSelect').each(function (i, elm) {
		var jDiv = $(this);
		var jSelect = jDiv.find('select');
		positionSelectUpdateState(jSelect);
	});
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
		
		jSelect.click(function() {
			var jSelect = $(this);
			positionSelectUpdateState(jSelect);
		});
		/*
		jSelect.blur(function() {
			var jSelect = $(this);
			positionSelectUpdateState(jSelect);
		});
		
		jSelect.select(function() {
			var jSelect = $(this);
			positionSelectUpdateState(jSelect);
		});
		*/
		
		positionSelectUpdateState(jSelect);
		
	});
	
	$('.positionSelect select option[value="here"]').each(function(index, obj) {
		var jObj = $(obj);
		jObj.attr("disabled", "disabled");
	});
	
	var posDetected=false;
	$('.detectPosition').each(function() {
		if(posDetected == false) {
			posDetected=true;
			geolocalisation_detectPosition();
		}
	});	
	
	setInterval("positionSelectUpdateStateAll()", 300);
});


function formLoading(formu) {
	jform = $(formu);
	jform.find('*').hide();
	jform.append('<div><img src="../img/loading.gif" alt="Loading.../><br />Chargement en cours</div>');
}

$(function() {
	$('form').each(function (i, elm) {
		$(elm).submit(function() {
			formLoading(this);
			return true;
		});
	});
});
