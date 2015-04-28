var mapaNuevoViaje;
var mapaVuelo;
var mapaReview;

var markerOrigen;
var markerDestino;

$(function () {
    //datos de prueba
    var cities = ['Londres', 'Salta', 'New York', 'Río de Janeiro', 'Beirut'];
    var contViajes = 0;
    //config google maps
    google.maps.event.addDomListener(window, 'load', initialize);
    
    formResetViaje();
    formResetVuelos();
    
    $("a[role=linkViaje]").click(initClickDetalle);
    $("#modDetalleViaje").on("shown.bs.modal",function(e){
    	//hack para que el mapa se dibuje bien
    	google.maps.event.trigger(mapaReview, "resize");
    });
    
    // notificaciones de recomendación *******************************
    /* TODO
     * $("#drpDwnRecomendaciones").click(function(event){
     * 		event.preventDefault();
     * 		acá debería traer las últimas 5 notificaciones y resalto las no leídas
     * });
     * 
     * TODO
     * $("#btnMarcarComoLeidas").click.... marcar las notificaciones como leídas
     */
    
    $("#verTodasRecomendaciones").click(function(event){
    	event.preventDefault();
    	//TODO get lista de recomendaciones completa
    	$("#modListaRecomendaciones").modal('show');
    });
    // notificaciones de recomendación *******************************
    
    // recomendar ****************************************
    var listaAmigosTrucha = ['Juan','Pedro','Luis','Luc&iacute;a','Romina','Jimena','Roberto','Julio','Makoto','Dimitri'];
    $("#btnRecomendarViaje").click(function(event){
    	event.preventDefault();
    	$("#modRecomendar").modal("show");
    });
    
    //TODO habría que hacer que los elementos se te vayan agregando en el input (como el de facebook)
    $("#boxAmigos").autocomplete({
    	//TODO get amigoss
        source: listaAmigosTrucha,
        select: function (event, ui) {
            $("#amigosList").append("<li>"+ui.item.value+"</li>");
            $("#boxAmigos").val('');
        }
    });
    // recomendar ****************************************
    
    //modal nuevo viaje ******************************
    $("#modNuevoViaje").on("shown.bs.modal",function(e){
    	//hack para que el mapa se dibuje bien
    	google.maps.event.trigger(mapaNuevoViaje, "resize");
    	//posiciono el cursor en la ciudad de origen
    	$("#ciudadOrigen").focus();
    });
    
    $("#btnNuevoViaje").click(function (event) {
        $("#modNuevoViaje").modal('show');
    });
    
    $("#ciudadOrigen").autocomplete({
    	/*source : function(request, response) {
			jQuery.getJSON(
					"http://localhost:8080/api/aeropuertos?cityName="
							+ request.term, function(data) {
								console.log(data);
						response(data);
					});
		},*/
    	/*source: function(request,response){
            $.ajax({
	            url: 'http://localhost:8080/api/aeropuertos',
	            dataType: 'json',
	            data: {
	            	'cityName': $("#ciudadOrgien").val()
	            },
	            success: function( data ) {
		            console.log(data);
		            //response(data);
		            response($.map(data,function(item){
			            return {
				            id: item.icao,
				            label: item.city,
				            value: item.iata
			            }
		         }));
	            }
            });
        },*/
    	source: cities,
        select: function (event, ui) {
            $("#fechaDesdeContainer").show();
            //TODO levantar las coordenadas de la ciudad de origen y pasarselas a la siguiente función
            markerOrigen = setMapMarker(mapaNuevoViaje, -34.599722222222,-58.381944444444);
            //me centro en el marker
            mapaNuevoViaje.setCenter(markerOrigen.getPosition());
        	mapaNuevoViaje.setZoom(10);
        	$("#fechaDesde").focus();
        }
    });
    
    $("#fechaDesde").datepicker({
        format: 'dd/mm/yyyy',
        minDate: new Date(),
        todayHighlight: true
    })
    .change(function (e) {
        $("#dstContainter").show();
        //restrinjo la fecha de vuelta teniendo en cuenta la elejida de salida
        var date2 = $('#fechaDesde').datepicker('getDate');
        date2.setDate(date2.getDate() + 1);
        $('#fechaHasta').datepicker('option', 'minDate', date2);
        $("#ciudadDestino").focus();
    });
    
    $("#ciudadDestino").autocomplete({
        /*source: function(request,response){
         $.ajax({
         url: 'https://api.despegar.com/v3/autocomplete',
         headers: { 'X-ApiKey': '19638437094c4892a8af7cdbed49ee43' },
         //beforeSend: function(xhr){xhr.setRequestHeader('X-ApiKey', '19638437094c4892a8af7cdbed49ee43');},
         //url: 'http://gd.geobytes.com/AutoCompleteCity',
         dataType: 'jsonp',
         data: {
         'query': $("#ciudadDestino").val(),
         'locale': 'es_AR',
         'city_result': 5
         },
         success: function( data ) {
         //console.log(data);
         //response(data);
         response($.map(data,function(item){
         return {
         id: item.code,
         label: item.description,
         value: item.id
         }
         }));
         }
         });
         },*/
        source: cities,
        minLength: 3,
        select: function (event, ui) {
            //destino
            $("#fechaHastaContainer").show();
            //TODO levantar las coordenadas de la ciudad de destino y pasarselas a la siguiente función
            markerDestino = setMapMarker(mapaNuevoViaje, 51.507222, -0.1275);
            //hago zoom out para que se vean los dos puntos marcados
            setMapBounds(mapaNuevoViaje);
            $("#fechaHasta").focus();
        }
    });
    $("#fechaHasta").datepicker({
        format: 'dd/mm/yyyy',
        todayHighlight: true
    })
    .change(function (e) {
        $("#btnBuscarVuelo").show();
    });
    $("#btnBuscarVuelo").click(function (event) {
        event.preventDefault();
        //TODO validar fechas!
        $("#modVuelos").modal('show');
        getVuelos('Ida');
        $("a[role=vueloIda]").click(initClickIda);
    });
    $("#btnCancelarViaje").click(function (event) {
        formResetViaje();
    });
    $("#modVuelos").on("shown.bs.modal",function(e){
    	//hack para que el mapa se dibuje bien
    	google.maps.event.trigger(mapaVuelo, "resize");
    });
    //**************************************************

    //modal selección de vuelos ************************
    $("#lstVuevloVuelta").hide();
    $("#boxVueloIda").hide();
    $("#boxVueloVuelta").hide();

    $("#cancelVueloIda").click(function (event) {
        event.preventDefault();
        $("#boxVueloIda").hide();
        $("#lstVuevloVuelta").hide();
        $("#lstVuevloIda").show();
    });
    $("a[role=vueloVuelta]").click(function (event) {
        event.preventDefault();
        console.log($(this).attr("id"));
        $("#lstVuevloVuelta").hide();
        $("#boxVueloVuelta").show();
        $("#btnViajar").show();
    });
    $("#cancelVueloVuelta").click(function (event) {
        event.preventDefault();
        $("#boxVueloVuelta").hide();
        $("#lstVuevloVuelta").show();
    });
    $("#btnViajar").click(function (event) {
        event.preventDefault();
        $("#itemSinViaje").hide();
        contViajes++;
        $("#listViajes").append(getViajeHTML(contViajes, $("#ciudadOrigen").val(), $("#ciudadDestino").val(),
                $("#fechaDesde").val(), $("#fechaHasta").val()));
        
        $("div[id="+contViajes+"] a[role=linkViaje]").click(initClickDetalle);
        //limpio el form para futuros viajes
        formResetViaje();
        formResetVuelos();
    });
    $("#btnVolver").click(function (e) {
        e.preventDefault();
        $("#modNuevoViaje").modal('show');
        formResetVuelos();
    });
    //**************************************************
});
function getViajeHTML(idViaje, origen, destino, desde, hasta) {
    return '<div class="list-group-item" id="' + idViaje + '">'
            + '<h3 class="list-group-item-heading"><a href="#" role="linkViaje">Viaje 1. Desde '
            + origen
            + ' a '
            + destino
            + ' saliendo el d&iacute;a '
            + desde
            + ' y volviendo el d&iacute;a '
            + hasta
            + '</a></h3>'
            + '<p class="list-group-item-text" align="right"><button type="button" class="btn btn-xs btn-primary" id="btnRecomendarViaje">Recomendar <span class="glyphicon glyphicon-share-alt"></span></button> <a href="#">Compartir</a> <a href="#">Eliminar</a></p>'
            + '</div>';
    }

function formResetViaje() {
    $("#frmNuevoViaje")[0].reset();
    $("#btnBuscarVuelo").hide();
    $("#dstContainter").hide();
    $("#orgMapContainer").hide();
    $("#dstMapContainer").hide();
    $("#fechaDesdeContainer").hide();
    $("#fechaHastaContainer").hide();
}

function formResetVuelos() {
    $("#frmVuelos")[0].reset();
    $("#btnViajar").hide();
    $("#sinVuelosIda").show();
    $("#sinVuelosVuelta").show();
    $("#lstVuevloIda").show();
    $("#lstVuevloVuelta").hide();
    $("#boxVueloIda").hide();
    $("#boxVueloVuelta").hide();
}

// init de google maps
function initialize() {
    var mapProp = {
        center: new google.maps.LatLng(51.508742, -0.120850),
        zoom: 5,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    mapaNuevoViaje = new google.maps.Map(document.getElementById("googleMapViaje"), mapProp);
    mapaVuelo = new google.maps.Map(document.getElementById("googleMapVuelo"), mapProp);
    mapaReview = new google.maps.Map(document.getElementById("googleMapViajeReview"), mapProp);
    
    var markerBounds = new google.maps.LatLngBounds();
}

function initClickIda() {
    $("#lstVuevloVuelta").show();
    $("#lstVuevloIda").hide();
    $("#boxVueloIda").show();
    getVuelos('Vuelta');
    $("a[role=vueloVuelta]").click(initClickVuelta);
    //TODO levantar las coordenadas de la ciudad de origen y pasarselas a la siguiente función
    markerOrigen = setMapMarker(mapaVuelo, -34.599722222222,-58.381944444444);
    //me centro en el marker
    mapaVuelo.setCenter(markerOrigen.getPosition());
    mapaVuelo.setZoom(10);
}

function initClickVuelta() {
    $("#lstVuevloVuelta").hide();
    $("#boxVueloVuelta").show();
    $("#btnViajar").show();
    markerDestino = setMapMarker(mapaVuelo, 51.507222, -0.1275);
    setMapBounds(mapaVuelo);
    flightPath = new google.maps.Polyline({
        path: [markerOrigen.getPosition(), markerDestino.getPosition()],
        strokeColor:"#00F",
        strokeOpacity:0.8,
        strokeWeight:2,
        map: mapaVuelo
     });
}

function getVuelos(sentido) {
//TODO $.ajax a nuestra api
//bucle json
    $("#sinVuelos" + sentido).hide();
    $("#lstVuevlo" + sentido + " .list-group").html('');
    for (i = 1; i <= 4; i++) {
        $("#lstVuevlo" + sentido + " .list-group").append(templateVuelo(sentido, i, "aeropuerto origen", "aeropuerto destino", "12:45", "aerolinea"));
    }
}

function templateVuelo(sentido, idVuelo, aeropuertoOrigen, aeropuertoDestion, horarioSalida, aerolinea, horarioLlegada) {
    return '<a href="#" class="list-group-item" role="vuelo' + sentido + '" id="' + idVuelo + '">' +
            '<h4 class="list-group-item-heading">Vuelo ' + idVuelo + ' de ' + aerolinea + ' de las ' + horarioSalida + ' horas.</h4>' +
            '<p class="list-group-item-text">Saliendo desde el aeropuerto ' + aeropuertoOrigen + ' llegando al aeropuerto ' + aeropuertoDestion + ' a las ' + horarioLlegada + '</p>' +
            '</a>';
}

function initClickDetalle(){
	// reviso si la lista de recomendaciones está abierta y la cierro si hace falta
	if(typeof $("#modListaRecomendaciones").data("bs.modal") != 'undefined' && $("#modListaRecomendaciones").data("bs.modal").isShown){
		$("#modListaRecomendaciones").modal("hide");
	}
	//TODO get detalle de viaje (por api)
	$("#modDetalleViaje").modal('show');
}

function getDateFromInput(inputId){
	var date = $("#"+inputId).datepicker("getDate");
	console.log(date);
	return new Date(date.substring(6,9),date.substring(3,4)+1,date.substring(0,1));
}

function setMapMarker(map, posLat, posLong){
	var marker = new google.maps.Marker({
		position: new google.maps.LatLng(posLat, posLong),
		map: map,
		title: 'Hello World!'
	});
	return marker;
}

function setMapBounds(map){
	var latlngbounds = new google.maps.LatLngBounds();
    latlngbounds.extend(markerOrigen.getPosition());
    latlngbounds.extend(markerDestino.getPosition());
    map.fitBounds(latlngbounds);
}