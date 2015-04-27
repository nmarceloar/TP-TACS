var map;
$(function () {
    //datos de prueba
    var cities = ['Londres', 'Salta', 'New York', 'Río de Janeiro', 'Beirut'];
    var contViajes = 0;
    //config google maps
    google.maps.event.addDomListener(window, 'load', initialize);
    formResetViaje();
    formResetVuelos();
    
    $("a[role=linkViaje]").click(initClickDetalle);
    
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
    /*$("#modNuevoViaje").on('shown',function() {
     google.maps.event.trigger(map, "resize");
     });*/

    $("#btnNuevoViaje").click(function (event) {
        event.preventDefault();
        $("#ciudadOrigen").focus();
        google.maps.event.trigger(map, "resize");
        $("#modNuevoViaje").modal('show');
    });
    $("#ciudadOrigen").change(function(e){
    	console.log($(this).val());
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
        }
    });
    $("#fechaDesde").datepicker({
        format: 'dd/mm/yyyy',
        minDate: new Date(),
        todayHighlight: true
    })//.on('changeDate', function(e){
    .change(function (e) {
        $("#dstContainter").show();
        //restrinjo la fecha de vuelta teniendo en cuenta la elejida de salida
        var date2 = $('#fechaDesde').datepicker('getDate');
        date2.setDate(date2.getDate() + 1);
        $('#fechaHasta').datepicker('option', 'minDate', date2);
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
    map = new google.maps.Map(document.getElementById("googleMapViaje"), mapProp);
    map = new google.maps.Map(document.getElementById("googleMapVuelo"), mapProp);
    map = new google.maps.Map(document.getElementById("googleMapViajeReview"), mapProp);
}

function initClickIda() {
    $("#lstVuevloVuelta").show();
    $("#lstVuevloIda").hide();
    $("#boxVueloIda").show();
    getVuelos('Vuelta');
    $("a[role=vueloVuelta]").click(initClickVuelta);
}

function initClickVuelta() {
    $("#lstVuevloVuelta").hide();
    $("#boxVueloVuelta").show();
    $("#btnViajar").show();
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