var map;
$(function () {
    //datos de prueba
    var cities = ['Londres', 'Salta', 'New York', 'Río de Janeiro', 'Beirut'];
    var contViajes = 0;
    //config google maps
    google.maps.event.addDomListener(window, 'load', initialize);
    formResetViaje();
    formResetVuelos();
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
    $("#ciudadOrigen").autocomplete({
        source: cities,
        select: function (event, ui) {
            $("#fechaDesdeContainer").show();
        }
    });
    $("#fechaDesde").datepicker({
        format: 'dd/mm/yyyy',
        startDate: 'today',
        todayHighlight: true
    })//.on('changeDate', function(e){
            .change(function (e) {
                $("#dstContainter").show();
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
        startDate: '',
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
    /*$("a[role=vueloIda]").click(function(event){
     event.preventDefault();
     console.log($(this).attr("id"));
     $("#lstVuevloVuelta").show();
     $("#lstVuevloIda").hide();
     $("#boxVueloIda").show();
     getVuelos('Vuelta');
     });*/

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
            + '<h3 class="list-group-item-heading"><a href="#">Viaje 1. Desde '
            + origen
            + ' a '
            + destino
            + ' saliendo el d&iacute;a '
            + desde
            + ' y volviendo el d&iacute;a '
            + hasta
            + '</a></h3>'
            + '<p class="list-group-item-text" align="right"><a href="#">Compartir</a> <a href="#">Eliminar</a></p>'
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
