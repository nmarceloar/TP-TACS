var mapaNuevoViaje;
var mapaVuelo;
var mapaReview;

var markerOrigen;
var markerDestino;

var City = function(){
	this.description = null;
	this.geolocation = null;
	this.code = null;
	this.setCityInfo = function(item){
		this.description = item.label;
		this.code = item.id;
		this.geolocation = item.geolocation;
	}
}

var Trip = function(org,dst,start,end){
	this.fromCity = org;
	this.toCity = dst;
	this.startDate = start;
	this.endDate = end;
}

var currentTrip = null;
var opcionesViaje = Array();

//guardo los datos de la ciudad de origen
var orgCity = new City();
//guardo los datos de la ciudad de destino
var dstCity = new City();

$(function () {
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
    	source: function(request,response){
            $.ajax({
	            url: 'http://localhost:8080/api/cities',
	            dataType: 'json',
	            data: {
	            	'name': request.term
	            },
	            success: function( data ) {
	            	autocomplete_processCities(data, response);
	            }
            });
        },
        minLength: 3,
        select: function (event, ui) {
            $("#fechaDesdeContainer").show();
            orgCity.setCityInfo(ui.item);
            $("#desc-origen").html(orgCity.description);
            markerOrigen = setMapMarker(mapaNuevoViaje, orgCity.geolocation, orgCity.description);
            //me centro en el marker
            mapaNuevoViaje.setCenter(markerOrigen.getPosition());
        	mapaNuevoViaje.setZoom(10);
        	$("#fechaDesde").focus();
        }
    })
    .data("ui-autocomplete")._renderItem = autocomplete_renderItemCiudades;
    
    $("#fechaDesde").datepicker({
    	dateFormat: 'dd/mm/yy',
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
    	source: function(request,response){
            $.ajax({
	            url: 'http://localhost:8080/api/cities',
	            dataType: 'json',
	            data: {
	            	'name': request.term
	            },
	            success: function( data ) {
	            	autocomplete_processCities(data, response);
	            }
            });
        },
        minLength: 3,
        select: function (event, ui) {
    		//destino
            $("#fechaHastaContainer").show();
            dstCity.setCityInfo(ui.item);
            $("#desc-destino").html(dstCity.description);
            markerDestino = setMapMarker(mapaNuevoViaje, dstCity.geolocation, dstCity.description);
            //hago zoom out para que se vean los dos puntos marcados
            setMapBounds(mapaNuevoViaje);
            $("#fechaHasta").focus();
        }
    })
    .data("ui-autocomplete")._renderItem = autocomplete_renderItemCiudades;
    
    $("#fechaHasta").datepicker({
    	dateFormat: 'dd/mm/yy',
        todayHighlight: true
    })
    .change(function (e) {
        $("#btnBuscarVuelo").show();
    });
    $("#btnBuscarVuelo").click(function (event) {
        event.preventDefault();
        $("#modVuelos").modal('show');
        currentTrip = new Trip(orgCity,dstCity,$("#fechaDesde").val(),$("#fechaHasta").val());
        getVuelos();
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
    $("#boxVuelo").hide();

    $("#cancelVueloIda").click(function (event) {
        event.preventDefault();
        $("#boxVueloIda").hide();
        $("#lstVuevloVuelta").hide();
        $("#lstVuevloIda").show();
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
    $("#sinVuelos").hide();
    $("#boxVuelo").hide();
    $("#lstVuelos").hide();
    $("#cargandoVuelos").show();
    opcionesViaje = Array();
}


function initClickIda() {
    $("#lstVuevloVuelta").show();
    $("#lstVuevloIda").hide();
    $("#boxVuelo").show();
    getVuelos();
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

function getVuelos() {
	$.ajax({
        url: 'http://localhost:8080/api/trip-options',
        dataType: 'json',
        data: {
        	'fromCity': currentTrip.fromCity.code,
        	'toCity': currentTrip.toCity.code,
        	'startDate': currentTrip.startDate,
        	'endDate': currentTrip.endDate
        },
        success: function( data ) {
        	//autocomplete_processCities(data, response);
        	//console.log(data);
        	$("#cargandoVuelos").hide();
    	    var datalen = data.length;
        	if(datalen > 0){
        		$("#sinVuelos").hide();
        		$("#lstVuelos").show();
        	    $("#lstVuelos .list-group").html('');
        	    opcionesViaje = data;
        	    var i = 0;
        	    var line;
        	    //TODO limito yo???
        	    if(datalen > 5){
        	    	datalen = 5;
        	    }
        	    for(i = 0; i < datalen; i++){
        	    	$("#listaVuelos").append(templateVuelo(i,data[i]));
        	    }
        	    $("div[role=opciones-vuelo]").hide();
    	    	$("a[role=ver-detalle-vuelo]").click(function(event){
    	    		//$(this).parent().children("div[role=opciones-vuelo]").toggle();
    	    		$("div[role=opciones-vuelo][vuelo="+$(this).attr("vuelo")+"]").toggle();
    	    	});
        	}
        	else{
        		$("#lstVuelos").show();
        		$("#sinVuelos").show();
        	}
        }
    });
}

var Viaje = function(){
	this.pricedetail = null;
	this.ida = null;
	this.vuelta = null;
}

function getTitleViaje(vuelo){
	var canttramos = vuelo.segments.length;
	return ' el ' + vuelo.segments[0].departure_datetime +
		' y llegando el ' + vuelo.segments[(canttramos - 1)].arrival_datetime +
		((canttramos > 1)? ' en ' + canttramos + ' escalas': '') + '.';
}

function getDescripcionViaje(viaje){
	var canttramos = viaje.segments.length;
	var desc = '<a href="#" class="list-group-item" role="opcion-vuelo">';
	for(var i = 0; i < canttramos; i++){
		desc += '<li alternativa="i">Vuelo ' + viaje.segments[i].flight_id + ' de ' + viaje.segments[i].airline + ' ' +
			'saliendo de ' + viaje.segments[i].from + ' a las ' + viaje.segments[i].departure_datetime + ' ' +
			'llegando a ' + viaje.segments[i].to + ' a las ' + viaje.segments[i].arrival_datetime + '.</li>';
	}
	return desc + '</a>';
}

function templateVuelo(posData, vuelo) {
	var i, j, titleida, titlevuelta, descripcionida, descripcionvuelta;
	for(i = 0; i < vuelo.outbound_choices.length; i++){
		//titleida = 'Saliendo ' + getTitleViaje(vuelo.outbound_choices[i]);
		descripcionida = getDescripcionViaje(vuelo.outbound_choices[i]);
	}
	for(j = 0; j < vuelo.inbound_choices.length; j++){
		//titlevuelta = 'Volviendo ' + getTitleViaje(vuelo.inbound_choices[j]);
		descripcionvuelta = getDescripcionViaje(vuelo.inbound_choices[j]);
	}
	return '<div class="list-group-item" role="vuelo" vuelo="' + posData + '">' +
		    '<a href="#" role="ver-detalle-vuelo" vuelo="' + posData + '"><h4 class="list-group-item-heading">Volá por ' + vuelo.price_detail.total + vuelo.price_detail.currency + '</h4></a>' +
		    '<div class="list-group" role="opciones-vuelo" vuelo="' + posData + '">' + descripcionida + '</div>' +
		    '<div class="list-group" role="opciones-vuelo" vuelo="' + posData + '">' + descripcionvuelta + '</div>' +
		    '</div>';
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

//helpers autocomplete ******************************************************************
function autocomplete_processCities(data, response){
	if(data.length == 0){
		data.push({
			'id': '',
			'label': 'No se encotraron ciudades con este nombre',
			'value': ''
		});
		response(data);
	}
	else{
        response($.map(data,function(item){
            return {
	            id: item.code,
	            label: item.description,
	            value: item.description,
	            geolocation: item.geolocation
            }
     }));
   }
}

var autocomplete_renderItemCiudades = function (ul, item) {
    //Add the .ui-state-disabled class and don't wrap in <a> if value is empty
    if(item.id ==''){
        return $('<li class="ui-state-disabled">'+item.label+'</li>').appendTo(ul);
    }else{
        return $("<li>")
        .append("<a>" + item.label + "</a>")
        .appendTo(ul);
    }
};
//*************************************************************************************

/*
 * gmaps functions *******************************************************************
 */
//init de google maps
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

function setMapMarker(map, geolocation, description){
	var marker = new google.maps.Marker({
		position: new google.maps.LatLng(geolocation.latitude, geolocation.longitude),
		map: map,
		title: description
	});
	return marker;
}

function setMapBounds(map){
	var latlngbounds = new google.maps.LatLngBounds();
    latlngbounds.extend(markerOrigen.getPosition());
    latlngbounds.extend(markerDestino.getPosition());
    map.fitBounds(latlngbounds);
}
/*
 * gmaps functions *******************************************************************
 */

