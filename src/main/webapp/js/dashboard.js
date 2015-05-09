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
            //TODO levantar las coordenadas de la ciudad de origen y pasarselas a la siguiente función
            orgCity.setCityInfo(ui.item);
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
            //TODO levantar las coordenadas de la ciudad de destino y pasarselas a la siguiente función
            dstCity.setCityInfo(ui.item);
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
    opcionesViaje = Array();
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
//TODO $.ajax a nuestra api
	$.ajax({
        url: 'http://localhost:8080/api/trip-options',
        dataType: 'json',
        //fromCity=LON&toCity=MIA&startDate=20/06/2015&endDate=30/06/2015
        data: {
        	'fromCity': currentTrip.fromCity.code,
        	'toCity': currentTrip.toCity.code,
        	'startDate': currentTrip.startDate,
        	'endDate': currentTrip.endDate
        },
        success: function( data ) {
        	//autocomplete_processCities(data, response);
        	//console.log(data);
    	    var datalen = data.length;
        	if(datalen > 0){
        		$("#sinVuelos").hide();
        	    $("#lstVuelos .list-group").html('');
        	    /*for (i = 1; i <= 4; i++) {
        	        $("#lstVuevlo" + sentido + " .list-group").append(templateVuelo(sentido, i, "aeropuerto origen", "aeropuerto destino", "12:45", "aerolinea"));
        	    }*/
        	    opcionesViaje = data;
        	    var i = 0;
        	    var line;
        	    for(i = 0; i < datalen; i++){
        	    	line = 'Opcion ' + 1 + ' precio: ' + data[i].price_detail.total + ' ' + data[i].price_detail.currency;
        	    	line += ' ' + data[i].inbound_choices.length + ' escalas de ida ';
        	    	line += ' ' + data[i].outbound_choices.length + ' escalas de vuelt.';
        	    	console.log(line);
        	    	$("#lstVuelos .list-group").append(templateVuelo(i,data[i]))
        	    }
        	}
        }
    });
}

function templateVuelo(idVuelo, vuelo) {
    /*return '<a href="#" class="list-group-item" role="vuelo" id="' + idVuelo + '">' +
            '<h4 class="list-group-item-heading">Vuelo ' + idVuelo + ' de ' + aerolinea + ' de las ' + horarioSalida + ' horas.</h4>' +
            '<p class="list-group-item-text">Saliendo desde el aeropuerto ' + aeropuertoOrigen + ' llegando al aeropuerto ' + aeropuertoDestion + ' a las ' + horarioLlegada + '</p>' +
            '</a>';*/
	return '<a href="#" class="list-group-item" role="vuelo" id="' + idVuelo + '">' +
		    '<h4 class="list-group-item-heading">Vuelo ' + idVuelo + ' que sale ' + vuelo.price_detail.total+ vuelo.price_detail.currency + '</h4>' +
		    '<p class="list-group-item-text">Escalas ida: ' + vuelo.inbound_choices.length + ', y vuelta: ' + vuelo.outbound_choices.length + '</p>' +
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


/*
 * gmaps functions *******************************************************************
 */
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

