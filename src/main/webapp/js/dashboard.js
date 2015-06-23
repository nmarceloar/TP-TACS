//** clases **********************************************************
var Viaje = function () {
    this.pricedetail = null;
    this.ida = null;
    this.vuelta = null;
};

var City = function () {
    this.description = null;
//    this.geolocation = null;
    this.latitude = null;
    this.longitude = null;
    this.code = null;
    this.setCityInfo = function (item) {
        this.description = item.label;
//        this.description = item.name;
        this.code = item.id;
//        this.code = item.code;
//        this.geolocation = item.geolocation;
        this.latitude = item.latitude;
        this.longitude = item.longitude;
    };
};

var Trip = function (org, dst, start, end) {
    this.fromCity = org;
    this.toCity = dst;
    this.startDate = start;
    this.endDate = end;
    this.price = null;
    this.outbound = null;
    this.inbound = null;
    this.isReady = function () {
        return this.outbound != null && this.inbound != null;
    };
    //convierto los datos del viaje para ser enviados a la api
    this.toJSON = function () {
        var segments = new Array();
        for (var i = 0; i < this.outbound.segments.length; i++) {
            this.outbound.segments[i].duration = dateDifference(this.outbound.segments[i].departure_datetime, this.outbound.segments[i].arrival_datetime).toString();
            segments.push(this.outbound.segments[i]);
        }
        for (var i = 0; i < this.inbound.segments.length; i++) {
            this.inbound.segments[i].duration = dateDifference(this.inbound.segments[i].departure_datetime, this.inbound.segments[i].arrival_datetime).toString();
            segments.push(this.inbound.segments[i]);
        }
//    	return JSON.stringify(segments);
        return segments;
    };
};
//** clases **********************************************************

//** globales ***************************************************************
//mapas de google maps
var mapaNuevoViaje; //el de la pantalla de creación de viaje
var mapaVuelo; //el de la pantalla de selección de vuelos
var mapaReview; //el de detalle de vuelo
var mapaReviewRecom;
var markerOrigen;
var markerDestino;
var airports; // Variable global para guardar aeropertos

//array con los puntos marcados en el mapa
var markersVuelos = new Array();

//engloba todo lo del viaje que se está gestando
var currentTrip = null;

var opcionesViaje = new Array();

//mantengo referencia del viaje actual
var currentMap = null;

//guardo los datos de la ciudad de origen
var orgCity = new City();
//guardo los datos de la ciudad de destino
var dstCity = new City();
//contador de viajes para darle un orden a la lista que se muestra
var contViajes = 0;
//** globales ***************************************************************


//###############################VARIABLES DE FACEBOOK#######################
var id;
var token;
var appToken;
var listAmigos = []; // Se guardan los nombres para llenar el autocomplete
var listIdAmigosARecomendar = []; // Se guardan pares id, nombre de los posibles a recibir recomendaciones
var amigosSelecRecomendar = []; // Se colocan los finalmente seleccionados para recomendar
var idViajeARecomendar;
var recomActiva;
//###############################VARIABLES DE FACEBOOK#######################


//** main *******************************************************************

$(function () {

    //####################### FACEBOOK #######################################

    //GET DEL TOKEN
    $(function () {
        $.ajaxSetup({cache: true});
        $.getScript('//connect.facebook.net/en_US/sdk.js', function () {
            FB.init({
                appId: '1586547271608233',
                version: 'v2.3' // or v2.0, v2.1, v2.0
            });
            $('#loginbutton,#feedbutton').removeAttr('disabled');
            FB.getLoginStatus(function (response) {
                updateStatusCallback(response);

            });
        });
    });

    $("#cerrarSesion").click(function () {

        $.ajax({
            url: 'http://localhost:8080/api/logout/',
            datatype: 'text',
            success: function (data) {
                console.log(data);
            },
        });
        FB.logout(function (response) {
            // Person is now logged out

            var url = "/";
            $(location).attr('href', url);

        });
    });



    //####################### FACEBOOK #######################################

    //config google maps
    google.maps.event.addDomListener(window, 'load', initialize);

    formResetViaje();
    formResetVuelos();

    $("#modDetalleViaje").on("shown.bs.modal", function (e) {
        //hack para que el mapa se dibuje bien
        google.maps.event.trigger(mapaReview, "resize");
    });

    $("#modDetalleViajeRecom").on("shown.bs.modal", function (e) {
        //hack para que el mapa se dibuje bien
        google.maps.event.trigger(mapaReviewRecom, "resize");
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

//    $("#verTodasRecomendaciones").click(function (event) {
//        event.preventDefault();
//        //TODO get lista de recomendaciones completa
//        $("#modListaRecomendaciones").modal('show');
//    });
    // notificaciones de recomendación *******************************


    $("#btnAceptarRecom").click(function (event) {
        console.log('Recomendacion activa es: ' + recomActiva);
        $.ajax({
            url: 'http://localhost:8080/api/recommendations/one/' + recomActiva + '?st=acp',
            type: 'PUT',
            dataType: 'text',
            success: function (data) {
                console.log(data);
                bootbox.alert('Se ha aceptado la recomendacion', function () {
                });
            },
            error: function (data) {
                console.log(data);
                bootbox.alert('Fallo la aceptacion de recomendacion', function () {
                });
            }
        });
    });

    $("#btnRechazarRecom").click(function (event) {
        $.ajax({
            url: 'http://localhost:8080/api/recommendations/one/' + recomActiva + '?st=rej',
            type: 'PUT',
            dataType: 'text',
            success: function (data) {
                console.log(data);
                bootbox.alert('Se ha rechazado la recomendacion', function () {
                });
            },
            error: function (data) {
                console.log(data);
                bootbox.alert('Fallo el rechazo de recomendacion', function () {
                });
            }
        });
    });

    $("#btnRecomendarViaje").click(function (event) {
        event.preventDefault();
        $("#modRecomendar").modal("show");
        $("#amigosList").html("");
        $("#boxAmigos").val('');
        amigosSelecRecomendar = [];
    });

    /**
     * Agrego la funcionalidad de efectivamente recomendar y notificar por un viaje
     */
    $("#btnRecomendar").click(function (event) {
        console.log("TOQUE RECOMENDAR DESDE DETALLE VIAJE");

        event.preventDefault();
        $("#modRecomendar").hide();

        $.each(amigosSelecRecomendar, function (i, val) {
            // Posteo de recomendacion    
            console.log('Recomendacion posteada ------');
            console.log('usuario a postearle: ' + val);
            console.log('usuario que postea: ' + id);
            console.log('id viaje recomendado: ' + idViajeARecomendar);
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/api/recommendations/' + val,
                data: JSON.stringify({
                    "idUsuario": id,
                    "idViaje": idViajeARecomendar
                }),
                contentType: 'application/json',
                dataType: 'text',
                success: function (data) {
                    console.log("el app token es");
                    appToken = "1586547271608233|" + data;
                    console.log(appToken);
                    FB.api('/' + val + '/notifications', 'post', {
                        template: "Recibiste una recomendacion de un viaje",
                        href: 'http://localhost:8080',
                        access_token: appToken
                    }, function (data) {
                        console.log(data);
                    });
                    bootbox.alert("Has recomendado el viaje satisfactoriamente", function () {
                    });
                }
            });

//             Posteo notificacion //CORREGIR °!!!!!!!!!!!!!!!!!!!!
//            $.ajax({
//                type: 'POST',
//                url: 'http://graph.facebook.com/v2.3/' + val + '/notifications?template=Recibiste una recomendacion de un viaje&href=http://localhost:8080',
////                data: "template=Recibiste una recomendacion de un viaje&href=http://localhost:8080",
////                contentType: 'application/json',
//                dataType: 'json',
//                success: function (data) {
//                    console.log('Parametros notification: ' + data);
//                },
//            	error:function(data){
//            		console.log(data);
//            	}
//            });

        });


    });


    // ---------------------------------------------------------------------------------------

    //TODO habría que hacer que los elementos se te vayan agregando en el input (como el de facebook)
    $("#boxAmigos").autocomplete({
        //TODO get amigoss
//        source: listaAmigosTrucha,
        source: listAmigos,
        select: function (event, ui) {
            for (var indice in listIdAmigosARecomendar) {
                if (listIdAmigosARecomendar[indice].name == ui.item.value) {
                    amigosSelecRecomendar.push(listIdAmigosARecomendar[indice].id);
                }
            }
            $("#amigosList").append("<li>" + ui.item.value + "</li>");
            $("#boxAmigos").val('');
        }
    });
    // recomendar ****************************************

    //modal nuevo viaje ******************************
    $("#modNuevoViaje").on("shown.bs.modal", function (e) {
        //hack para que el mapa se dibuje bien
        google.maps.event.trigger(mapaNuevoViaje, "resize");
        currentMap = mapaNuevoViaje;
        //posiciono el cursor en la ciudad de origen
        $("#ciudadOrigen").focus();
    });

//    Aceptar / Rechazar recomendacion


    $("#btnNuevoViaje").click(function (event) {
        $("#modNuevoViaje").modal('show');
    });

    $("#ciudadOrigen").autocomplete(
            {
                source: function (request, response) {
                    $.ajax({
                        url: 'http://localhost:8080/api/cities',
                        dataType: 'json',
                        data: {
                            'name': request.term
                        },
                        success: function (data) {
                            autocomplete_processCities(data, response);
                        }
                    });
                },
                minLength: 3,
                select: function (event, ui) {
                    $("#fechaDesdeContainer").show();
                    orgCity.setCityInfo(ui.item);
                    $("#desc-origen").html(orgCity.description);
                    markerOrigen = setMapMarker(mapaNuevoViaje,
                            orgCity.latitude, orgCity.longitude, orgCity.description);
                    // me centro en el marker
                    mapaNuevoViaje.setCenter(markerOrigen.getPosition());
                    mapaNuevoViaje.setZoom(10);
                    $("#fechaDesde").focus();
                }
            }).data("ui-autocomplete")._renderItem = autocomplete_renderItemCiudades;

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

    $("#ciudadDestino").autocomplete(
            {
                source: function (request, response) {
                    $.ajax({
                        url: 'http://localhost:8080/api/cities',
                        dataType: 'json',
                        data: {
                            'name': request.term
                        },
                        success: function (data) {
                            autocomplete_processCities(data, response);
                        }
                    });
                },
                minLength: 3,
                select: function (event, ui) {
                    // destino
                    $("#fechaHastaContainer").show();
                    dstCity.setCityInfo(ui.item);
                    $("#desc-destino").html(dstCity.description);
                    markerDestino = setMapMarker(mapaNuevoViaje,
                            dstCity.latitude, dstCity.longitude, dstCity.description);
                    // hago zoom out para que se vean los dos puntos marcados
                    setMapBounds(mapaNuevoViaje);
                    $("#fechaHasta").focus();
                }
            }).data("ui-autocomplete")._renderItem = autocomplete_renderItemCiudades;


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
        currentTrip = new Trip(orgCity, dstCity, $("#fechaDesde").val(), $("#fechaHasta").val());
        getVuelos();
        //$("a[role=vueloIda]").click(initClickIda);
    });
    $("#btnCancelarViaje").click(function (event) {
        formResetViaje();
    });
    $("#modVuelos").on("shown.bs.modal", function (e) {
        //hack para que el mapa se dibuje bien
        currentMap = mapaVuelo;
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
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/api/trips',
            data: JSON.stringify({
                "idPassenger": id,
                "fromCity": currentTrip.fromCity.description,
                "toCity": currentTrip.toCity.description,
                "price": currentTrip.price.total + " " + currentTrip.price.currency,
                "itinerary": currentTrip.toJSON()
            }),
            contentType: 'application/json',
            dataType: 'json',
            success: function (data) {
                console.log(data.result);
                contViajes++;
                $("#listViajes").append(getViajeHTML(data.id));
                bootbox.confirm("Felicitaciones por tu viaje! Queres publicarlo en tu muro de Facebook?",
                        function (result) {
                            if (result) {
                                FB.api('/' + id + '/permissions', 'get', function (resp) {
                                    console.log(resp);
                                    var dioPermiso = false;
                                    for (var i = 0; i < resp.data.length; i++) {
                                        if (resp.data[i].permission == 'publish_actions' && resp.data[i].status == 'granted') {
                                            dioPermiso = true;
                                        }
                                    }
                                    ;
                                    if (dioPermiso == true) {
                                        //El tipo ya dio permiso para publicar
                                        publicar();
                                    } else {
                                        //Todavia no dio permiso
                                        FB.login(function (response) {
                                            var respondioOk = false;
//        							verifico que respondio
                                            FB.api('/' + id + '/permissions', 'get', function (resp) {
                                                console.log(resp);
                                                var respondioOk = false;
                                                for (var i = 0; i < resp.data.length; i++) {
                                                    if (resp.data[i].permission == 'publish_actions' && resp.data[i].status == 'granted') {
                                                        respondioOk = true;
                                                    }
                                                }
                                                ;
                                                if (respondioOk == true) {
                                                    publicar();
                                                }
                                                formResetViaje();
                                                formResetVuelos();
                                            })
                                        }, {
                                            scope: 'publish_actions',
                                            return_scopes: true
                                        });
                                    }
                                });
                            } else {
                                //limpio el form para futuros viajes
                                formResetViaje();
                                formResetVuelos();
                            }
                        });
                $("div[id=" + data.id + "] a[role=linkViaje]").click(data.id, initClickDetalle);
                $("div[id=" + data.id + "] button[id=btnRecomendarViaje]").click(data.id, initClickRecomendar);
                $("div[id=" + data.id + "] a[id=eliminarViaje]").click(data.id, initClickEliminar);
                $("div[id=" + data.id + "] a[id=compartirViaje]").click(data.id, initClickCompartir);
            }
        });

    });
    $("#btnVolver").click(function (e) {
        e.preventDefault();
        $("#modNuevoViaje").modal('show');
        formResetVuelos();
    });
    //**************************************************
});

// ** templates ************************************************
function getViajeHTML(idViaje) {
    return '<div class="list-group-item" id="' + idViaje + '">'
            + '<h3 class="list-group-item-heading"><a href="#" role="linkViaje">Viaje ' + contViajes + '. Desde '
            + currentTrip.fromCity.description
            + ' a '
            + currentTrip.toCity.description
            + ' saliendo el d&iacute;a '
            + currentTrip.outbound.segments[0].departure_datetime
            + ' y volviendo el d&iacute;a '
            + currentTrip.inbound.segments[(currentTrip.inbound.segments.length - 1)].arrival_datetime
            + '</a></h3>'
            + '<p class="list-group-item-text" align="right"><button type="button" class="btn btn-xs btn-primary" id="btnRecomendarViaje">Recomendar <span class="glyphicon glyphicon-share-alt"></span></button> <a href="#" id="compartirViaje">Compartir</a> <a href="#" id="eliminarViaje">Eliminar</a></p>'
            + '</div>';
}



function getViajesPropiosHTML(data) {
    contViajes++;
    return '<div class="list-group-item" id="'
            + data.id
            + '">'
            + '<h3 class="list-group-item-heading"><a href="#" role="linkViaje">Viaje '
            + contViajes
            + '. Desde '
            + data.tripDetails.fromCity.name
            + ' a '
            + data.tripDetails.toCity.name
            + ' saliendo el d&iacute;a '
            + data.tripDetails.outboundItinerary[0].departure
            + ' y volviendo el d&iacute;a '
            + data.tripDetails.inboundItinerary[data.tripDetails.inboundItinerary.length - 1].arrival
            + '</a></h3>'
            + '<p class="list-group-item-text" align="right"><button type="button" class="btn btn-xs btn-primary" id="btnRecomendarViaje">Recomendar <span class="glyphicon glyphicon-share-alt"></span></button> <a href="#" id="compartirViaje">Compartir</a> <a href="#" id="eliminarViaje">Eliminar</a></p>'
            + '</div>';
}


function getViajesDeAmigosHTML(data) {
    return '<div class="list-group-item" id="itemAmigo">'
            + '<h3 class="list-group-item-heading"><a href="#" role="linkViaje">Viaje Desde '
            + data.fromCity
            + ' a '
            + data.toCity
            + ' saliendo el d&iacute;a '
            + data.tripDepartureDate
            + ' y volviendo el d&iacute;a '
            + data.tripArrivalDate
            + '</a></h3>'
            + '<p class="list-group-item-text" align="right"><button type="button" class="btn btn-xs btn-primary" id="btnRecomendarViaje">Recomendar <span class="glyphicon glyphicon-share-alt"></span></button> <a href="#" id="compartirViaje">Compartir</a> <a href="#" id="eliminarViaje">Eliminar</a></p>'
            + '</div>';
}


//function insertarRecomendacionesDeViajes(data, count) {
function insertarRecomendacionesDeViajes(data) {
//    return '<li><a href="#" role="linkViajeRecom" id="itemRecom'
    return '<li><a href="#" role="linkViajeRecom" id="' + data.id + '" trip="'
            + data.viajeAsoc
            + '">'
            + data.nombreyap
            + ' quiere que viajes desde '
            + data.origen
            + ' hasta '
            + data.destino
            + '</a></li>';
}

function getTitleViaje(vuelo) {
    var canttramos = vuelo.segments.length;
    return ' el ' + vuelo.segments[0].departure_datetime +
            ' y llegando el ' + vuelo.segments[(canttramos - 1)].arrival_datetime +
            ((canttramos > 1) ? ' en ' + canttramos + ' escalas' : '') + '.';
}

function getDescripcionViaje(i, viaje, sentido) {
    var canttramos = viaje.segments.length;
    var desc = '<li class="list-group-item" role="opcion-vuelo"><label><input type="radio" name="optradio-' + sentido + '" alternativa="' + i + '" class="vuelo-tramo-ajuste" />';
    for (var i = 0; i < canttramos; i++) {
        desc += '<p class="vuelo-tramos-detail">Vuelo ' + viaje.segments[i].flight_id + ' de ' + viaje.segments[i].airline + ' ' +
                'saliendo de ' + viaje.segments[i].from + ' a las ' + viaje.segments[i].departure_datetime + ' ' +
                'llegando a ' + viaje.segments[i].to + ' a las ' + viaje.segments[i].arrival_datetime + '.</p>';
    }
    return desc + '</label></li>';
}

function templateVuelo(posData, vuelo) {
    var i, j, titleida, titlevuelta;

    var descripcionida = '<li class="list-group-item active">Ida</li>';
    for (i = 0; i < vuelo.outbound_choices.length; i++) {
        descripcionida += getDescripcionViaje(i, vuelo.outbound_choices[i], 'ida');
    }

    var descripcionvuelta = '<li class="list-group-item active">Vuelta</li>';
    for (j = 0; j < vuelo.inbound_choices.length; j++) {
        descripcionvuelta += getDescripcionViaje(j, vuelo.inbound_choices[j], 'vuelta');
    }

    return '<div class="list-group-item" role="vuelo" vuelo="' + posData + '">' +
            '<a href="#" role="ver-detalle-vuelo" vuelo="' + posData + '"><h4 class="list-group-item-heading">Vol\u00e1 por ' + vuelo.price_detail.total + vuelo.price_detail.currency + '</h4></a>' +
            '<div class="list-group" role="opciones-vuelo" vuelo="' + posData + '" type="ida">' + descripcionida + '</div>' +
            '<div class="list-group" role="opciones-vuelo" vuelo="' + posData + '" type="vuelta">' + descripcionvuelta + '</div>' +
            '</div>';
}
//** templates ************************************************

// ** manejo de datos ********************************************
function dateDifference(date1, date2) {
    var d1 = new Date(date1);
    var d2 = new Date(date2);
    var timeDiff = Math.abs(d2.getTime() - d1.getTime());
    return Math.ceil(timeDiff / (1000 * 3600));
}
//** manejo de datos ********************************************

//** manejo de forms *********************************************
function formResetViaje() {
    $("#frmNuevoViaje")[0].reset();
    $("#btnBuscarVuelo").hide();
    $("#dstContainter").hide();
    $("#orgMapContainer").hide();
    $("#dstMapContainer").hide();
    $("#fechaDesdeContainer").hide();
    $("#fechaHastaContainer").hide();
    markerOrigen = null;
    markerDestino = null;
}

function formResetVuelos() {
    $("#frmVuelos")[0].reset();
    $("#btnViajar").hide();
    $("#sinVuelos").hide();
    $("#boxVuelo").hide();
    $("#lstVuelos").hide();
    $("#cargandoVuelos").show();
    opcionesViaje = Array();
    currentTrip = null;
}
//** manejo de forms *********************************************

// ** funciones ajax ************************************************************** 
function getVuelos() {
    $.ajax({
        url: 'http://localhost:8080/api/trip-options',
        dataType: 'json',
        data: {
            'fromCity': currentTrip.fromCity.code,
            'toCity': currentTrip.toCity.code,
            'startDate': currentTrip.startDate,
            'endDate': currentTrip.endDate,
            'offset': 0, //TODO falta paginación!
            'limit': 5
        },
        success: function (data) {
            $("#cargandoVuelos").hide();
            airports = data.airports;
            var datalen = data.items.length;
            if (datalen > 0) {
                $("#sinVuelos").hide();
                $("#lstVuelos").show();
                $("#lstVuelos .list-group").html('');
                opcionesViaje = data.items;
                var i = 0;
                var line;
                for (i = 0; i < datalen; i++) {
                    $("#listaVuelos").append(templateVuelo(i, data.items[i]));
                }
                $("div[role=opciones-vuelo]").hide();
                $("a[role=ver-detalle-vuelo]").click(function (event) {
                    $("div[role=opciones-vuelo][vuelo!=" + $(this).attr("vuelo") + "]").hide();
                    $("div[role=opciones-vuelo][vuelo!=" + $(this).attr("vuelo") + "]").children("input[type=radio]").prop("disabled", true);
                    $("div[role=opciones-vuelo][vuelo=" + $(this).attr("vuelo") + "]").toggle();
                    $("div[role=opciones-vuelo][vuelo=" + $(this).attr("vuelo") + "]").children("input[type=radio]").prop("disabled", false);
                });
                $("input[type=radio][alternativa]").click(function (event) {
                    var i_vuelo = $(this).parents("div[role=opciones-vuelo]").attr("vuelo");
                    var i_alternativa = $(this).attr('alternativa');
                    var i_type = $(this).parents("div[role=opciones-vuelo]").attr("type");
                    var airportdata = new Array();
                    if (i_type == "ida") {
                        currentTrip.outbound = opcionesViaje[i_vuelo].outbound_choices[i_alternativa];
//                        getInfoAirportsAndMap(currentTrip.outbound);
                        for (var x in airports) {
                            if (airports[x].code == currentTrip.outbound.segments[0].from) {
                                console.log('Encontre el aeropuerto: ' + airports[x].code + ' en L: ' + airports[x].latitude + ' Lon: ' + airports[x].longitude)
                                drawFlightRoute(airports[x].latitude, airports[x].longitude);
                            }
                        }
                    } else {
                        currentTrip.inbound = opcionesViaje[i_vuelo].inbound_choices[i_alternativa];
//                        getInfoAirportsAndMap(currentTrip.inbound);
                        for (var x in airports) {
                            if (airports[x].code == currentTrip.inbound.segments[0].from) {
                                console.log('Encontre el aeropuerto: ' + airports[x].code + ' en L: ' + airports[x].latitude + ' Lon: ' + airports[x].longitude)
                                drawFlightRoute(airports[x].latitude, airports[x].longitude);
                            }
                        }
                    }
                    currentTrip.price = opcionesViaje[i_vuelo].price_detail;
                    if (currentTrip.isReady()) {
                        $("#btnViajar").show();
                    }
                });
            }
            else {
                $("#cargandoVuelos").hide();
                $("#lstVuelos").show();
                $("#sinVuelos").show();
            }

        },
        error: function () {
            $("#cargandoVuelos").hide();
            $("#lstVuelos").show();
            $("#sinVuelos").show();
        }
    });
}

//busco el aeropuerto que quiero por código
function getAirportData(code, airportlist) {
    for (var i = 0; i < airportlist.length; i++) {
        if (code == airportlist[i].code) {
            return airportlist[i];
        }
    }
    return null;
}

//function getInfoAirportsAndMap(flight) {
//    var prep = '';
//    prep = "&code=" + flight.airportCodesAsSet.join("&code=")
//    $.ajax({
//        url: 'http://localhost:8080/api/airports?' + prep,
//        dataType: 'json',
//        success: function (data) {
//            result = data;
//            drawFlightRoute(data);
//        },
//        error: function () {
//            console.log("ERROR no se puede dibujar ruta");
//        }
//    });
//}
//** funciones ajax **************************************************************


function initClickDetalle(id) {

// reviso si la lista de recomendaciones está abierta y la cierro si hace falta
    if (typeof $("#modListaRecomendaciones").data("bs.modal") != 'undefined' && $("#modListaRecomendaciones").data("bs.modal").isShown) {
        $("#modListaRecomendaciones").modal("hide");
    }

    var precio;
    var desde;
    var hasta;

    $.ajax({
        url: "http://localhost:8080/api/trips/one/" + id.data,
        dataType: 'json',
        success: function (data) {
            console.log('El viaje a recomendar es: ' + data.idTrip);
            idViajeARecomendar = data.idTrip;
            console.log("Respuesta trip");
            //console.log(data);
            precio = data.price;
            desde = data.fromCity;
            hasta = data.toCity;
            var titulo = "\u00A1Tu viaje desde " + desde + " hasta " + hasta + "!";
            $("div[id=modDetalleViaje] h4").html(titulo);
            var itinerario = "";
            var enter = "<br>";
            $.each(data.itinerary, function (index, value) {
                console.log(value);
                var fechaSalida = value.departure_datetime;
                var fechaLlegada = value.arrival_datetime;
                if (fechaSalida.toString("dd/MM/yyyy") == fechaLlegada.toString("dd/MM/yyyy")) {
                    itinerario = itinerario + "Sal\u00eds el " + fechaSalida.toString("dd/MM/yyyy") + " desde " + value.from + " a las " + fechaSalida.toString("HH:mm") + " hs " +
                            "y lleg\u00e1s a " + value.to + " a las " + fechaLlegada.toString("HH:mm") + " del mismo d\u00eda";
                }
                else
                {
                    itinerario = itinerario + "Sal\u00eds el " + fechaSalida.toString("dd/MM/yyyy") + " desde " + value.from + " a las " + fechaSalida.toString("HH:mm") + " hs " +
                            "y lleg\u00e1s el " + fechaSalida.toString("dd/MM/yyyy") + " a " + value.to + " a las " + fechaLlegada.toString("HH:mm");
                }
                itinerario = itinerario + enter;
            });
            $("div[id=modDetalleViaje] div[class=col-md-6]").html(itinerario);
            //TODO llenar el mapa de detalle del viaje
            $("#modDetalleViaje").modal('show');
        }
    });

}

function initClickDetalleRecom(datos) {

    event.preventDefault();
    var idViaje = datos.data.viaje;
    recomActiva = datos.data.idRec;
    console.log('click detalle: idViaje: ' + idViaje + ' - recom: ' + recomActiva);


    // reviso si la lista de recomendaciones está abierta y la cierro si hace falta
    if (typeof $("#modListaRecomendaciones").data("bs.modal") != 'undefined' && $("#modListaRecomendaciones").data("bs.modal").isShown) {
        $("#modListaRecomendaciones").modal("hide");
    }
    var precio;
    var desde;
    var hasta;
    $.ajax({
        url: "http://localhost:8080/api/trips/one/" + idViaje,
        dataType: 'json',
        success: function (data) {
            console.log(data);
            precio = data.price;
            desde = data.fromCity;
            hasta = data.toCity;
            var titulo = "\u00A1Tu viaje desde " + desde + " hasta " + hasta + "!";
            $("div[id=modDetalleViajeRecom] h4").html(titulo);
            var itinerario = "";
            var enter = "<br>";
            $.each(data.itinerary, function (index, value) {
                console.log(value);
                var fechaSalida = value.departure_datetime;
                var fechaLlegada = value.arrival_datetime;
                if (fechaSalida.toString("dd/MM/yyyy") == fechaLlegada.toString("dd/MM/yyyy")) {
                    itinerario = itinerario + "Sal\u00eds el " + fechaSalida.toString("dd/MM/yyyy") + " desde " + value.from + " a las " + fechaSalida.toString("HH:mm") + " hs " +
                            "y lleg\u00e1s a " + value.to + " a las " + fechaLlegada.toString("HH:mm") + " del mismo d\u00eda";
                }
                else
                {
                    itinerario = itinerario + "Sal\u00eds el " + fechaSalida.toString("dd/MM/yyyy") + " desde " + value.from + " a las " + fechaSalida.toString("HH:mm") + " hs " +
                            "y lleg\u00e1s el " + fechaSalida.toString("dd/MM/yyyy") + " a " + value.to + " a las " + fechaLlegada.toString("HH:mm");
                }
                itinerario = itinerario + enter;
            });
            $("div[id=modDetalleViajeRecom] div[class=col-md-6]").html(itinerario);
            //TODO llenar el mapa de detalle del viaje
            $("#modDetalleViajeRecom").modal('show');
        }
    });

}

function initClickRecomendar(idViajeParaRecomendar) {
    // reviso si la lista de recomendaciones está abierta y la cierro si hace falta
    if (typeof $("#modListaRecomendaciones").data("bs.modal") != 'undefined' && $("#modListaRecomendaciones").data("bs.modal").isShown) {
        $("#modListaRecomendaciones").modal("hide");
    }
    event.preventDefault();
    idViajeARecomendar = idViajeParaRecomendar.data;
    $("#modRecomendar").modal("show");
    $("#amigosList").html("");
    $("#boxAmigos").val('');
    amigosSelecRecomendar = [];
    console.log("recomende por boton");
}


function initClickEliminar(idViajeAEliminar) {
    console.log(idViajeAEliminar.data);
    // reviso si la lista de recomendaciones está abierta y la cierro si hace falta
    if (typeof $("#modListaRecomendaciones").data("bs.modal") != 'undefined' && $("#modListaRecomendaciones").data("bs.modal").isShown) {
        $("#modListaRecomendaciones").modal("hide");
    }
    $.ajax({
        url: "http://localhost:8080/api/me/created-trips/" + idViajeAEliminar.data,
        dataType: "text",
        method: "DELETE",
        success: function (data) {
            console.log(data);
            $("#listViajes").html('<div class="list-group-item active"><h4>Tus viajes</h4></div>' +
                    '<div class="list-group-item" id="itemSinViaje">Todav\u00eda no hiciste ning\u00fan viaje <span class="glyphicon glyphicon-thumbs-down"></span></div>');
            $.ajax({
                url: 'http://localhost:8080/api/me/created-trips/',
                dataType: 'json',
                success: function (data) {
                    if (data.length != 0) {
                        $("#itemSinViaje").hide();
                        $.each(data, function (index, value) {
                            $("#listViajes").append(getViajesPropiosHTML(value));
                            $("div[id=" + value.id + "] a[role=linkViaje]").click(value.id, initClickDetalle);
                            $("div[id=" + value.id + "] a[id=eliminarViaje]").click(value.id, initClickEliminar);
                            $("div[id=" + value.id + "] a[id=compartirViaje]").click(value.id, initClickCompartir);
                        });
                    }
                }
            });
        }
    })
}

function initClickCompartir(idViajeACompartir) {
    console.log(idViajeACompartir.data);
    // reviso si la lista de recomendaciones está abierta y la cierro si hace falta
    if (typeof $("#modListaRecomendaciones").data("bs.modal") != 'undefined' && $("#modListaRecomendaciones").data("bs.modal").isShown) {
        $("#modListaRecomendaciones").modal("hide");
    }
    $.ajax({
        url: 'http://localhost:8080/api/trips/one/' + idViajeACompartir.data,
        dataType: 'json',
        success: function (data) {
            console.log(data);

            bootbox.confirm("Queres publicar este viaje en tu muro de Facebook?",
                    function (result) {
                        if (result) {
                            FB.api('/' + id + '/permissions', 'get', function (resp) {
                                console.log(resp);
                                var dioPermiso = false;
                                for (var i = 0; i < resp.data.length; i++) {
                                    if (resp.data[i].permission == 'publish_actions' && resp.data[i].status == 'granted') {
                                        dioPermiso = true;
                                    }
                                }
                                ;
                                if (dioPermiso == true) {
                                    //El tipo ya dio permiso para publicar
                                    compartir(data);
                                } else {
                                    //Todavia no dio permiso
                                    FB.login(function (response) {
                                        var respondioOk = false;
//								verifico que respondio
                                        FB.api('/' + id + '/permissions', 'get', function (resp) {
                                            console.log(resp);
                                            var respondioOk = false;
                                            for (var i = 0; i < resp.data.length; i++) {
                                                if (resp.data[i].permission == 'publish_actions' && resp.data[i].status == 'granted') {
                                                    respondioOk = true;
                                                }
                                            }
                                            ;
                                            if (respondioOk == true) {
                                                compartir(data);
                                            }
                                            formResetViaje();
                                            formResetVuelos();
                                        })
                                    }, {
                                        scope: 'publish_actions',
                                        return_scopes: true
                                    });
                                }
                            });
                        }
                    })
        }
    })



}

//helpers autocomplete
//******************************************************************
function autocomplete_processCities(data, response) {
    if (data.length == 0) {
        data.push({
            'id': '',
            'label': 'No se encotraron ciudades con este nombre',
            'value': ''
        });
        response(data);
    } else {
        response($.map(data, function (item) {
            return {
                id: item.code,
//             label: item.description,
//             value: item.description,
                label: item.name,
                value: item.name,
//             geolocation: item.geolocation
                latitude: item.latitude,
                longitude: item.longitude
            }
        }));
    }
}

var autocomplete_renderItemCiudades = function (ul, item) {
    // Add the .ui-state-disabled class and don't wrap in <a> if value is empty
    if (item.id == '') {
        return $('<li class="ui-state-disabled">' + item.label + '</li>')
                .appendTo(ul);
    } else {
        return $("<li>").append("<a>" + item.label + "</a>").appendTo(ul);
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
    mapaReviewRecom = new google.maps.Map(document.getElementById("googleMapViajeReviewRecom"), mapProp);
    var markerBounds = new google.maps.LatLngBounds();
}

function setMapMarker(map, latitude, longitude, description) {
    var marker = new google.maps.Marker({
        position: new google.maps.LatLng(latitude, longitude),
        map: map,
        title: description
    });
    return marker;
}
function setMapBounds(map) {
    var latlngbounds = new google.maps.LatLngBounds();
    latlngbounds.extend(markerOrigen.getPosition());
    latlngbounds.extend(markerDestino.getPosition());
    map.fitBounds(latlngbounds);
}

//function drawFlightRoute(airportdata) {
//    markersVuelos = [];
//    var latlngbounds = new google.maps.LatLngBounds();
//    for (var i = 0; i < airportdata.length; i++) {
//        var marker = setMapMarker(currentMap, airportdata[i].geolocation, airportdata[i].description);
//        markersVuelos.push(marker);
//        latlngbounds.extend(marker.getPosition());
//    }
//
//    currentMap.fitBounds(latlngbounds);
//
//    var path = [];
//    for (var i = 0; i < markersVuelos.length; i++) {
//        path.push(markersVuelos[i].getPosition());
//    }
//    var color = getRandomColor();
//    flightPath = new google.maps.Polyline({
//        path: path,
//        strokeColor: color,
//        strokeOpacity: 0.8,
//        strokeWeight: 2,
//        map: currentMap
//    });
//}

function drawFlightRoute(latitude, longitude) {
    markersVuelos = [];
    var latlngbounds = new google.maps.LatLngBounds();
//    for (var i = 0; i < airportdata.length; i++) {
        var marker = setMapMarker(currentMap, latitude, longitude);
        markersVuelos.push(marker);
        latlngbounds.extend(marker.getPosition());
//    }

    currentMap.fitBounds(latlngbounds);

    var path = [];
    for (var i = 0; i < markersVuelos.length; i++) {
        path.push(markersVuelos[i].getPosition());
    }
    var color = getRandomColor();
    flightPath = new google.maps.Polyline({
        path: path,
        strokeColor: color,
        strokeOpacity: 0.8,
        strokeWeight: 2,
        map: currentMap
    });
}

function getRandomColor() {
    var letters = '0123456789ABCDEF'.split('');
    var color = '#';
    for (var i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}
/*
 * gmaps functions *******************************************************************
 */

//####################### FACEBOOK #######################################
function updateStatusCallback(response) {

    console.log('updateStatusCallback');

    if (response.status === 'connected') {




        console.log("TOKEN");
        console.log(response.authResponse.accessToken);
        console.log("EXPIRES IN");
        console.log(response.authResponse.expiresIn);
        console.log("ID");
        console.log(response.authResponse.userID);
        console.log("API--");

        id = response.authResponse.userID;
        console.log(id);

        FB.api('/' + id, {
            fields: 'name'
        }, function (response) {
            $("#nombreUsuarioFb").html(response.name);
        });

        FB.api('/' + id + '?fields=picture', function (response) {

            $("#imagenPerfil").attr("src", response.picture.data.url);
        });

//        var url = 'http://localhost:8080/api/passengers/query?id=' + id;
//        console.log("logueo url cambiada posta");
//        console.log(url);
//        $(document).ready(function () {
//            $.ajax({
//                url: url,
//                dataType: 'text',
//                success: function (data) {
//                    console.log("LLEGO BIEN el token");
//                    console.log(data);
//                    token = data;
//                }
//            });
//        })
        token = response.authResponse.accessToken;
        //#############################################################

        /**
         * Implemento la carga de viajes del usuario mediante rest
         */


        /**
         * Lleno mis viajes anteriores
         */
        $.ajax({
            url: 'http://localhost:8080/api/me/created-trips',
            dataType: 'json',
            success: function (data) {
                if (data.length != 0) {
                    $("#itemSinViaje").hide();
                    $.each(data, function (index, value) {
                        $("#listViajes").append(getViajesPropiosHTML(value));
                        console.log(value.id);
                        $("div[id=" + value.id + "] a[role=linkViaje]").click(value.id, initClickDetalle);
                        $("div[id=" + value.id + "] button[id=btnRecomendarViaje]").click(value.id,initClickRecomendar);
                        $("div[id=" + value.id + "] a[id=eliminarViaje]").click(value.id, initClickEliminar);
                        $("div[id=" + value.id + "] a[id=compartirViaje]").click(value.id, initClickCompartir);
                    });
                }
            }
        }
        );
        
        /**
         * Lleno mis viajes aceptados
         */
        $.ajax({
            url: 'http://localhost:8080/api/me/accepted-trips',
            dataType: 'json',
            success: function (data) {
                if (data.length != 0) {
                    $("#itemSinViajeAceptado").hide();
                    $.each(data, function (index, value) {
                        $("#listViajesAceptados").append(getViajesPropiosHTML(value));
                        console.log(value.id);
                        $("div[id=" + value.id + "] a[role=linkViaje]").click(value.id, initClickDetalle);
                        $("div[id=" + value.id + "] button[id=btnRecomendarViaje]").click(value.id,initClickRecomendar);
                        $("div[id=" + value.id + "] a[id=eliminarViaje]").click(value.id, initClickEliminar);
                        $("div[id=" + value.id + "] a[id=compartirViaje]").click(value.id, initClickCompartir);
                    });
                }
            }
        }
        );

        /**
         * LLeno los viajes de los amigos
         */
        $.ajax({
            url: 'http://localhost:8080/api/trips/friends/' + id,
            dataType: 'json',
            success: function (data) {
                if (data.length !== 0) {
                    $.each(data, function (index, value) {
                        $("#listViajesAmigos").append(getViajesDeAmigosHTML(value));
                        $("div[id=" + value.idTrip + "] a[role=linkViaje]").click(value.idTrip, initClickDetalle);
                        $("div[id=" + value.idTrip + "] button[id=btnRecomendarViaje]").click(value.idTrip, initClickRecomendar);
                        $("div[id=" + value.idTrip + "] a[id=eliminarViaje]").click(value.idTrip, initClickEliminar);
                        $("div[id=" + value.idTrip + "] a[id=compartirViaje]").click(value.idTrip, initClickCompartir);
                    });
                }
            }
        });

        /**
         * Lleno con las recomendaciones que me hicieron
         */
        $.ajax({
            url: 'http://localhost:8080/api/recommendations/' + id,
            dataType: 'json',
            success: function (data) {
//      		var i = 1;
                if (data.length != 0) {
                    $.each(data, function (index, value) {
//      				$("#listRecomendaciones").append(insertarRecomendacionesDeViajes(value, i));
//      				i++;
                        $("#listRecomendaciones").append(insertarRecomendacionesDeViajes(value));
                        //<a href="#" role="linkViajeRecom" id="itemRecom" trip="4">
                        $("a[role=linkViajeRecom][id=" + value.id + "][trip=" + value.viajeAsoc + "]").click({"viaje": value.viajeAsoc, "idRec": value.id}, initClickDetalleRecom);
                    });
                    $("#listRecomendaciones").append("<li class=\"divider\"></li>");
                    $("#listRecomendaciones").append("<li><a href=\"#\" id=\"verTodasRecomendaciones\">Ver todas las recomendaciones</a></li>");
                }
            }

        });
        

        /**
         * LLeno los amigos que pueden ser destino de recomendaciones
         */
        $.ajax({
            url: 'http://localhost:8080/api/friends/' + id,
            dataType: 'json',
            success: function (data) {
                $.each(data, function (index, value) {
                    var nombreCom = value.nombre + ' ' + value.apellido;
//                    console.log('Agrego ' + value.id + ' a lista de amigos.')
//                    listIdAmigosARecomendar.push(value.id);
//                    listAmigos.push(nombreCom);
                    listIdAmigosARecomendar.push({"id": value.id, "name": nombreCom});
                    listAmigos.push(nombreCom);
                });
            }
        });



        //#############################################################

    } else if (response.status === 'not_authorized') {
        console.log("Esta logueado en face, pero todavia no acepto")
        // The person is logged into Facebook, but not your app.
        var url = "/";
        $(location).attr('href', url);
    } else {
        // The person is not logged into Facebook, so we're not sure if
        // they are logged into this app or not.
        console.log("No esta logueado en face")
        var url = "/";
        $(location).attr('href', url);
    }


}



function dameLongToken() {

    return token;

}


function getViajeParaFB(from, to, salida, vuelta) {
    return 'TACS POR EL MUNDO: Viajo desde '
            + from
            + ' a '
            + to
            + ' saliendo el dia '
            + salida
            + ' y volviendo el dia '
            + vuelta;
}
//PUBLICAR EN MURO CUANDO SE CREA UN VIAJE
function publicar() {
//	pruebo las cosas del mapa
    console.log("https://maps.googleapis.com/maps/api/staticmap?center=" + mapaVuelo.getCenter().toUrlValue() +
            "&zoom=" + mapaVuelo.getZoom() +
            "&maptype=" + mapaVuelo.getMapTypeId() +
            "&size=600x400" +
            "&markers=color:green%7C" + markerOrigen.getPosition().toString().trim() +
            "%7C" + markerDestino.getPosition().toString().trim() +
            "&path=color:red%7C" + markerOrigen.getPosition().toString().trim() +
            "%7C" + markerDestino.getPosition().toString().trim());
//	posteo en muro de facebook
    FB.api('/' + id + '/feed', 'post', {
        message: getViajeParaFB(currentTrip.fromCity.description,
                currentTrip.toCity.description,
                currentTrip.outbound.segments[0].departure_datetime,
                currentTrip.inbound.segments[(currentTrip.inbound.segments.length - 1)].arrival_datetime),
        picture: "https://maps.googleapis.com/maps/api/staticmap?center=" + mapaVuelo.getCenter().toUrlValue() +
                "&zoom=" + mapaVuelo.getZoom() +
                "&maptype=" + mapaVuelo.getMapTypeId() +
                "&size=600x400" +
                "&markers=color:green%7C" + markerOrigen.getPosition().toString().trim() +
                "%7C" + markerDestino.getPosition().toString().trim() +
                "&path=color:red%7C" + markerOrigen.getPosition().toString().trim() +
                "%7C" + markerDestino.getPosition().toString().trim(),
        name: 'TACS POR EL MUNDO',
        description: 'viaje',
        access_token: token
    }, function (data) {
        console.log(data);
    });
    bootbox.alert("Excelente! Tu nuevo viaje ya esta publicado", function () {
    });
    formResetViaje();
    formResetVuelos();
}
//COMPARTIR UN VIAJE EN EL MURO YA CREADO PREVIAMENTE
function compartir(viaje) {
//	pruebo las cosas del mapa
//	console.log("https://maps.googleapis.com/maps/api/staticmap?center=" + mapaVuelo.getCenter().toUrlValue() +
//			"&zoom=" + mapaVuelo.getZoom() +
//			"&maptype=" + mapaVuelo.getMapTypeId() +
//			"&size=600x400" +
//			"&markers=color:green%7C" + markerOrigen.getPosition().toString().trim() +
//			"%7C" + markerDestino.getPosition().toString().trim() +
//			"&path=color:red%7C" + markerOrigen.getPosition().toString().trim() +
//			"%7C" + markerDestino.getPosition().toString().trim());
//	posteo en muro de facebook
    FB.api('/' + id + '/feed', 'post', {
        message: getViajeParaFB(viaje.fromCity, viaje.toCity, viaje.tripDepartureDate.toString("dd/MM/yyyy HH:mm"), viaje.tripArrivalDate.toString("dd/MM/yyyy HH:mm")),
//        picture: "https://maps.googleapis.com/maps/api/staticmap?center=" + mapaVuelo.getCenter().toUrlValue() +
//                "&zoom=" + mapaVuelo.getZoom() +
//                "&maptype=" + mapaVuelo.getMapTypeId() +
//                "&size=600x400" +
//                "&markers=color:green%7C" + markerOrigen.getPosition().toString().trim() +
//                "%7C" + markerDestino.getPosition().toString().trim() +
//                "&path=color:red%7C" + markerOrigen.getPosition().toString().trim() +
//                "%7C" + markerDestino.getPosition().toString().trim(),
        name: 'TACS POR EL MUNDO',
        description: 'viaje',
        access_token: token
    }, function (data) {
        console.log(data);
    });
    bootbox.alert("Excelente! Tu nuevo viaje ya esta publicado", function () {
    });
}
//####################### FACEBOOK #######################################

//Salís el 23/04/2015 desde Ezeiza, Buenos Aires a las 14:35 hs y llegás al Aeropuerto Internacional de la ciudad de Panamá a las 23:35 del mismo día

function llegadaDatosViaje(datos) {
    $("#detViajeRecom").append(
            'Salio el '
            + datos.tripDepartureDate
            + ' desde '
            + datos.fromCity
            + ' hasta '
            + datos.toCity
            + ' llegando el dia '
            + datos.tripArrivalDate
            );
}

//function abrirPabelDetalleViajeRecomendado(viajeId) {
//    // reviso si la lista de recomendaciones está abierta y la cierro si hace falta
//    if (typeof $("#modListaRecomendaciones").data("bs.modal") != 'undefined' && $("#modListaRecomendaciones").data("bs.modal").isShown) {
//        $("#modListaRecomendaciones").modal("hide");
//    }
//    // Elimino si ya hay un detalle escrito
//    $("#detViajeRecom").empty();
//    // Completo con los datos del pedido ajax
//    $.getJSON("http://localhost:8080/api/trips/one/" + viajeId, llegadaDatosViaje);
//    $("#modDetalleViajeRecom").modal('show');
//}
//
//$("#itemRecom").on("click", function () {
//    var viajeId = $(this).attr("trip");
//    abrirPabelDetalleViajeRecomendado(viajeId);
//});
