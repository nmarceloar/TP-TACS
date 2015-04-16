var map;

$(function() {
	// datos de prueba
	var cities = [ 'Londres', 'Salta', 'New York', 'Río de Janeiro', 'Beirut' ];
	var contViajes = 0;

	// config google maps

	google.maps.event.addDomListener(window, 'load', initialize);

	// modal nuevo viaje ******************************
	$("#modNuevoViaje").on('shown', function() {
		google.maps.event.trigger(map, "resize");
	});
	$("#btnNuevoViaje").click(function(event) {
		event.preventDefault();
		google.maps.event.trigger(map, "resize");
		console.log(map);
		$("#modNuevoViaje").modal('show');
	});

	formReset("frmNuevoViaje");

	$("#ciudadOrigen").autocomplete({
		source : cities,
		select : function(event, ui) {
			$("#dstContainter").show();
			$("#orgMapContainer").show();
		}
	});

	$("#ciudadDestino").autocomplete({
		source : cities,
		select : function(event, ui) {
			// destino
			$("#dstMapContainer").show();
			$("#btnViajar").show();
		}
	});

	$("#btnViajar").click(
			function(event) {
				event.preventDefault();
				$("#itemSinViaje").hide();
				// llamo ws con las ciudades
				// uso la respuesta para armar el html que incluyo con la
				// función getViajeHTML
				contViajes++;
				$("#listViajes").append(
						getViajeHTML(contViajes, $("#ciudadOrigen").val(), $(
								"#ciudadDestino").val()));
				// limpio el form para futuros viajes
				formReset("frmNuevoViaje");
			});
	// **************************************************
});

function getViajeHTML(idViaje, origen, destino) {
	return '<div class="list-group-item" id="'
			+ idViaje
			+ '">'
			+ '<h3 class="list-group-item-heading"><a href="#">Viaje 1. Desde '
			+ origen
			+ ' a '
			+ destino
			+ '</a></h3>'
			+ '<p class="list-group-item-text" align="right"><a href="#">Compartir</a> <a href="#">Eliminar</a></p>'
	'</div>';
}

function formReset(formId) {
	$("#" + formId)[0].reset();
	$("#btnViajar").hide();
	$("#dstContainter").hide();
	$("#orgMapContainer").hide();
	$("#dstMapContainer").hide();
}

// init de google maps
function initialize() {
	var mapProp = {
		center : new google.maps.LatLng(51.508742, -0.120850),
		zoom : 5,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
}
