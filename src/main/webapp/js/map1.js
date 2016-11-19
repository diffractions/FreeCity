google.maps.event.addDomListener(window, 'load', initMap);
var map;
function initMap() {
	var latitude = document.getElementById('centerlat').value;
	var longitude = document.getElementById('centerlng').value;
	var myLatlng = new google.maps.LatLng(latitude, longitude);
	var mapElement = document.getElementById('map');
	var myOptions = {
		zoom : 12,
		center : myLatlng
	}
	var geocoder = new google.maps.Geocoder;
	map = new google.maps.Map(mapElement, myOptions);
	map.addListener('click', function(event) {
		placeMarker(event.latLng);
		geocodeLatLng(geocoder, map, event.latLng); 
	});
}
var marker;

function placeMarker(location) {
	if (marker) {
		marker.setPosition(location);
	} else {
		marker = new google.maps.Marker({
			position : location,
			map : map
		});
	}

	document.getElementById('map-link-lat').value = location.lat();
	document.getElementById('map-link-lng').value = location.lng();
}


$(document).ready(function() {
	$('#city-id').change(function(e) {
		
		var latitude = document.getElementById('lat-city-'+document.getElementById('city-id').value).value;
		var longitude = document.getElementById('lng-city-'+document.getElementById('city-id').value).value;

		var myLatlng = new google.maps.LatLng(latitude, longitude);

		

		var mapElement = document.getElementById('map');
		map.setCenter(myLatlng);
 
	})
});

function geocodeLatLng(geocoder, map, location) {
	 
	var latitude  = location.lat();
	var longitude = location.lng();
	
	var myLatlng = new google.maps.LatLng(latitude, longitude);
	
	  geocoder.geocode({'location': location}, function(results, status) {
	    if (status === google.maps.GeocoderStatus.OK) {
	      if (results[0]) {  
	    	  document.getElementById('map-adress-link').value =results[0].formatted_address;
//	    	  document.getElementById('adress-link').value =results[0].formatted_address; 
	      } else {
	        window.alert('No results found');
	      }
	    } else {
	      window.alert('Geocoder failed due to: ' + status);
	    }
	  });
}





