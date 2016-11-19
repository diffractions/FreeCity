//https://developers.google.com/maps/documentation/javascript/examples/place-search?hl=ru

google.maps.event.addDomListener(window, 'load', init);

var i = 0;
var marker = [];
var contentString = [];
var content = [];
var infowindow = [];

function init() {

	var latitude = document.getElementById('centerlat').value;
	var longitude = document.getElementById('centerlng').value; 
	var zom = parseFloat(document.getElementById('mapzoom').value); 
	var mapElement = document.getElementById('map');

	var myLatlng = new google.maps.LatLng(latitude, longitude);
	var myOptions = {  
		zoom : zom, 
		center : myLatlng  
	}

	map = new google.maps.Map(mapElement, myOptions); 
	
	i = 0;
	marker = [];
	contentString = [];
	content = [];
	infowindow = [];
}

function add(latitude1, longitude1, title1, descr) {
	marker[i] = new google.maps.Marker({
		position : new google.maps.LatLng(latitude1, longitude1),
		map : map,
		title : title1,
	});

	marker[i].index = i;
	content[i] = descr;
	infowindow[i] = new google.maps.InfoWindow({
		content : content[i],
	});

	google.maps.event.addListener(marker[i], 'click', function() {
		console.log(this.index);
		console.log(i);
		infowindow[this.index].open(map, marker[this.index]);
		map.panTo(marker[this.index].getPosition());
	});

	i++;
}

$(document).ready(function() {
	$('#map_ref').click(function(e) {
		

		var latitude = document.getElementById('centerlat').value;
		var longitude = document.getElementById('centerlng').value; 
		var mapElement = document.getElementById('map');
		var zom = parseFloat(document.getElementById('mapzoom').value); 

		var myLatlng = new google.maps.LatLng(latitude, longitude);

		google.maps.event.trigger(map, 'resize');
		map.setCenter(myLatlng);
		map.setZoom(zom);

	})
});
