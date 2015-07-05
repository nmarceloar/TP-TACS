//** clases **********************************************************
var Viaje = function () {
    this.fromAirport = null;
    this.toAirport = null;
    this.airline = null;
    this.flightid = null;
    this.departure = null;
    this.arrival = null;
    this.duration = null;
};

var City = function () {
    this.description = null;
//    this.geolocation = null;
    this.latitude = null;
    this.longitude = null;
    this.code = null;
    this.setCityInfo = function (item) {
        this.description = item.name;
//        this.description = item.name;
        this.code = item.code;
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
    this.toJSONoutbound = function () {
        var segments = new Array();
        for (var i = 0; i < this.outbound.segments.length; i++) {
            this.outbound.segments[i].duration = dateDifference(this.outbound.segments[i].departure_datetime, this.outbound.segments[i].arrival_datetime).toString();
            segments.push(this.outbound.segments[i]);
        }
//    	return JSON.stringify(segments);
        return segments;
    };
    this.toJSONinbound = function () {
        var segments = new Array();
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
var airlines; // Variable global para guardar aerolineas

//array con los puntos marcados en el mapa
var markersVuelos = new Array();

//array con los puntos marcados en el mapa
var lineasVuelos = new Array();

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
            url: '/api/logout',
            datatype: 'text',
            success: function (data) {
                console.log(data);
            },
            error: function(data){
            	console.log(data);
            }
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
        currentMap = mapaReview;
    });

    $("#modDetalleViajeRecom").on("shown.bs.modal", function (e) {
        //hack para que el mapa se dibuje bien
        google.maps.event.trigger(mapaReviewRecom, "resize");
        currentMap = mapaReviewRecom;
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
            url: '/api/me/received-recommendations/' + recomActiva,
            type: 'PATCH',
            data: JSON.stringify({
        		"op":"replace",
        		"path":"/status",
        		"value":"ACCEPTED"
        		}),
        	contentType: 'application/json',
            dataType: 'json',
            success: function (data) {
//    			aca mirar que viene, obtener el id del chabon owner del viaje.
                console.log(data);
                bootbox.alert('Se ha aceptado la recomendacion. Ahora este viaje aparecera como viaje Aceptado.', function () {
                });
                $("#listViajesAceptados").html("");
                $("#listViajesAceptados").append('<div class="list-group-item active"><h4>Viajes Aceptados por recomendaciones</h4></div>');
                /**
                 * Lleno mis viajes aceptados
                 */
                $.ajax({
                    url: '/api/me/accepted-trips',
                    dataType: 'json',
                    success: function (data) {
                        if (data.length != 0) {
                            $("#itemSinViajeAceptado").hide();
                            $.each(data, function (index, value) {
                            	console.log(value);
                                $("#listViajesAceptados").append(getViajesAceptadosHTML(value));
//                                $("div[id=" + value.id + "] a[role=linkViaje]").click(value.id, initClickDetalle);
//                                $("div[id=" + value.id + "] button[id=btnRecomendarViaje]").click(value.id, initClickRecomendar);
//                                $("div[id=" + value.id + "] a[id=compartirViaje]").click(value.id, initClickCompartir);
                            });
                        }
                    }
                }
                );
                $("#listRecomendaciones").html("");
    			/**
    			 * Lleno con las recomendaciones que me hicieron
    			 */
    			$.ajax({
    				url: '/api/me/received-recommendations/',
    				dataType: 'json',
    				success: function (data) {
//  					var i = 1;
    					if (data.length != 0) {
    						$("#CeroRecom").remove();
    						$.each(data, function (index, value) {
//  							$("#listRecomendaciones").append(insertarRecomendacionesDeViajes(value, i));
//  							i++;
    							$("#listRecomendaciones").append(insertarRecomendacionesDeViajes(value));
    							//<a href="#" role="linkViajeRecom" id="itemRecom" trip="4">
    							$("a[role=linkViajeRecom][id=" + value.id + "][trip=" + value.trip.id + "]").click({"idRec": value.id}, initClickDetalleRecom);
    						});
    						$("#listRecomendaciones").append("<li class=\"divider\"></li>");
//  						$("#listRecomendaciones").append("<li><a href=\"#\" id=\"verTodasRecomendaciones\">Ver todas las recomendaciones</a></li>");
    					}else{
    						$("#listRecomendaciones").append('<li id="CeroRecom"><a href="#" role="linkViajeRecom"> No tenes nuevas Recomendaciones</a></li>');
    					}
    				}
    			});
//    			ACA AGREGAR LO DE LA NOTIFICACION CUANDO ACEPTO UN VIAJE.
                console.log("el app token es");
                console.log(appToken);
                FB.api('/' + data.owner.id + '/notifications', 'post', {
                    template: data.target.name + " acepto una recomendacion del viaje desde "+data.trip.tripDetails.fromCity.name+ " hasta "+data.trip.tripDetails.toCity.name,
                    href: 'http://tacs-viajando-g1.appspot.com/html/redirect.html',
                    access_token: appToken
                }, function (data) {
                    console.log(data);
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
    		url: '/api/me/received-recommendations/' + recomActiva,
    		type: 'PATCH',
    		data: JSON.stringify({
    			"op":"replace",
    			"path":"/status",
    			"value":"REJECTED"
    		}),
    		contentType: 'application/json',
    		dataType: 'json',
    		success: function (data) {
    			console.log(data);
    			bootbox.alert('Se ha rechazado la recomendacion', function () {
    			});
    			$("#listRecomendaciones").html("");
    			/**
    			 * Lleno con las recomendaciones que me hicieron
    			 */
    			$.ajax({
    				url: '/api/me/received-recommendations/',
    				dataType: 'json',
    				success: function (data) {
//  					var i = 1;
    					if (data.length != 0) {
    						$("#CeroRecom").remove();
    						$.each(data, function (index, value) {
//  							$("#listRecomendaciones").append(insertarRecomendacionesDeViajes(value, i));
//  							i++;
    							$("#listRecomendaciones").append(insertarRecomendacionesDeViajes(value));
    							//<a href="#" role="linkViajeRecom" id="itemRecom" trip="4">
    							$("a[role=linkViajeRecom][id=" + value.id + "][trip=" + value.trip.id + "]").click({"idRec": value.id}, initClickDetalleRecom);
    						});
    						$("#listRecomendaciones").append("<li class=\"divider\"></li>");
//  						$("#listRecomendaciones").append("<li><a href=\"#\" id=\"verTodasRecomendaciones\">Ver todas las recomendaciones</a></li>");
    					}else{
    						$("#listRecomendaciones").append('<li id="CeroRecom"><a href="#" role="linkViajeRecom"> No tenes nuevas Recomendaciones</a></li>');
    					}
    				}
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
        console.log("TOQUE RECOMENDAR DESDE DETALLE VIAJE");
        event.preventDefault();
        $.ajax({
            url: '/api/me/created-trips/' + idViajeARecomendar,
            dataType: 'json',
            success: function (data) {
                console.log(data);
                var desde = data.tripDetails.fromCity.name;
                var hasta = data.tripDetails.toCity.name;
                var des = desde.split(",");
                var has = hasta.split(",");
                var titulo = "\u00A1Recomenda tu viaje desde " + des[0]+","+des[2] + " hasta " + has[0]+","+has[2] + " a tus amigos!";
                $("#modRecomendar h4").html(titulo);
            },
        	error: function(data){
        		console.log(data);
        	}
        })
        $("#modRecomendar").modal("show");
        $("#amigosList").html("");
        $("#boxAmigos").val('');
        amigosSelecRecomendar = [];
    });

    /**
     * Agrego la funcionalidad de efectivamente recomendar y notificar por un viaje
     */
    $("#btnRecomendar").click(function (event) {
    	console.log("Empiezo la recomendacion");
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
                url: '/api/me/created-recommendations',
//                data: JSON.stringify({
//                    "idUsuario": id,
//                    "idViaje": idViajeARecomendar
//                }),
//                contentType: 'application/json',
                data: "targetid="+val+"&tripid="+idViajeARecomendar,
                contentType: 'application/x-www-form-urlencoded',
                dataType: 'json',
                success: function (data) {
                	console.log(data);
                    console.log("el app token es");
                    console.log(appToken);
                    FB.api('/' + val + '/notifications', 'post', {
                        template: "Recibiste una recomendacion de un viaje",
                        href: 'http://tacs-viajando-g1.appspot.com/html/redirect.html',
                        access_token: appToken
                    }, function (data) {
                        console.log(data);
                    });
                    bootbox.alert("Has recomendado el viaje satisfactoriamente", function () {
                    });
                },
                error: function(data){
                	console.log(data);
                	bootbox.alert("Ya hiciste una recomendacion por este viaje para un usuario seleccionado", function () {
                    });
                }
            });
        });
    });


    // ---------------------------------------------------------------------------------------

    //TODO habría que hacer que los elementos se te vayan agregando en el input (como el de facebook)
    $("#boxAmigos").autocomplete({
    	//TODO get amigoss
//  	source: listaAmigosTrucha,
    	source: listAmigos,
    	select: function (event, ui) {
    		for (var indice in listIdAmigosARecomendar) {
    			if (listIdAmigosARecomendar[indice].name == ui.item.value && amigosSelecRecomendar.indexOf(listIdAmigosARecomendar[indice].id)==-1) {
    				amigosSelecRecomendar.push(listIdAmigosARecomendar[indice].id);
    	    		$("#amigosList").append("<li>" + ui.item.value + "</li>");
    			}
    		}
    		$("#boxAmigos").val('');
    	}
    });
    // recomendar ****************************************

    //modal nuevo viaje ******************************
    $("#modNuevoViaje").on("shown.bs.modal", function (e) {
        //hack para que el mapa se dibuje bien
        google.maps.event.trigger(mapaNuevoViaje, "resize");
        currentMap = mapaNuevoViaje;
        $("#ciudadOrigen").focus();
        //posiciono el cursor en la ciudad de origen
    });

//    Aceptar / Rechazar recomendacion


    $("#btnNuevoViaje").click(function (event) {
        $("#modNuevoViaje").modal('show');
    });

    $("#ciudadOrigen").autocomplete(
            {
                source: function (request, response) {
                    $.ajax({
                        url: '/api/search/cities',
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
                        url: '/api/search/cities',
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
        console.log("SUPER LOG DEL VIAJE MANS");
        console.log(JSON.stringify({
            "fromCity": armarJsonCiudad(currentTrip.fromCity),
            "toCity": armarJsonCiudad(currentTrip.toCity),
            "priceDetail": currentTrip.price,
            "outboundItinerary": armarItinerario(currentTrip.outbound),
            "inboundItinerary": armarItinerario(currentTrip.inbound),
        }));
        $.ajax({
            type: 'POST',
            url: '/api/me/created-trips',
            data: JSON.stringify({
              "fromCity": armarJsonCiudad(currentTrip.fromCity),
              "toCity": armarJsonCiudad(currentTrip.toCity),
              "priceDetail": currentTrip.price,
              "outboundItinerary": armarItinerario(currentTrip.outbound),
              "inboundItinerary": armarItinerario(currentTrip.inbound)
            }),
            contentType: 'application/json',
            dataType: 'json',
            success: function (data) {
                console.log(data);
                $("#listViajes").append(getViajesPropiosHTML(data));
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
                $("div[id=" + data.id + "] a[role=linkViaje]").click({"id":data.id,"origen":"creado"}, initClickDetalle);
                $("div[id=" + data.id + "] button[id=btnRecomendarViaje]").click(data.id, initClickRecomendar);
                $("div[id=" + data.id + "] a[id=eliminarViaje]").click(data.id, initClickEliminar);
                $("div[id=" + data.id + "] a[id=compartirViaje]").click({"id":data.id,"origen":"creado"}, initClickCompartir);
                
//              Limpio los marcadores que quedaron
                if (markersVuelos.length > 0) {
                	console.log("TIENE MARKERS, LIMPIO");
                    for (i in markersVuelos) {
                    	markersVuelos[i].setMap(null);
                    }
                    markersVuelos.length = 0;
                }
                if (lineasVuelos.length > 0) {
                	console.log("TIENE Lineas, LIMPIO");
                    for (i in lineasVuelos) {
                    	lineasVuelos[i].setMap(null);
                    }
                    lineasVuelos.length = 0;
                }
            },
            error: function(data){
            	console.log(data);
            	bootbox.alert("Ha surgido un problema! no hemos podido crear tu viaje", function () {
              	});
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
function getViajesPropiosHTML(data) {
    contViajes++;
    var des = data.tripDetails.fromCity.name.split(",");
    var has = data.tripDetails.toCity.name.split(",");
    return '<div class="list-group-item" id="'
            + data.id
            + '">'
            + '<h3 class="list-group-item-heading"><a href="#" role="linkViaje">Viaje '
            + contViajes
            + '. Desde '
            + des[0]+','+des[2] 
            + ' a '
            + has[0]+','+has[2] 
            + ' saliendo el d&iacute;a '
            + data.tripDetails.outboundDate
            + ' hs y volviendo el d&iacute;a '
            + data.tripDetails.inboundDate
            + ' hs</a></h3>'
            + '<p class="list-group-item-text" align="right"><button type="button" class="btn btn-xs btn-primary" id="btnRecomendarViaje">Recomendar <span class="glyphicon glyphicon-share-alt"></span></button> <a href="#" id="compartirViaje">Compartir</a> <a href="#" id="eliminarViaje">Eliminar</a></p>'
            + '</div>';
}

function getViajesAceptadosHTML(data) {
    var des = data.trip_details.fromCity.name.split(",");
    var has = data.trip_details.toCity.name.split(",");
//    aca hay q ponerle el id o algo
    return '<div class="list-group-item" id="'
            + data.trip_id
            + '">'
            + '<h3 class="list-group-item-heading"><a href="#" role="linkViaje">Viaje '
            + 'desde '
            + des[0]+','+des[2] 
            + ' a '
            + has[0]+','+has[2] 
            + ' saliendo el d&iacute;a '
            + data.trip_details.outboundDate
            + ' hs y volviendo el d&iacute;a '
            + data.trip_details.inboundDate
            + ' hs</a></h3>'
            + '<p class="list-group-item-text" align="right"><a href="#" id="compartirViaje">Compartir en Muro de FB</a></p>'
            + '</div>';
}


function getViajesDeAmigosHTML(data) {
	var des = data.tripDetails.fromCity.name.split(",");
    var has = data.tripDetails.toCity.name.split(",");
    return '<div class="list-group-item" id="'+data.id+'">'
            + '<h3 class="list-group-item-heading"><a href="#" role="linkViaje">'+data.owner.name+' creo un viaje desde '
            + des[0]+','+des[2] 
            + ' a '
            + has[0]+','+has[2] 
            + ' saliendo el d&iacute;a '
            + data.tripDetails.outboundDate
            + ' hs y volviendo el d&iacute;a '
            + data.tripDetails.inboundDate
            + ' hs</a></h3>'
//            + '<p class="list-group-item-text" align="right"><button type="button" class="btn btn-xs btn-primary" id="btnRecomendarViaje">Recomendar <span class="glyphicon glyphicon-share-alt"></span></button> <a href="#" id="compartirViaje">Compartir</a></p>'
            + '</div>';
}


//function insertarRecomendacionesDeViajes(data, count) {
function insertarRecomendacionesDeViajes(data) {
	var des = data.trip.tripDetails.fromCity.name.split(",");
    var has = data.trip.tripDetails.toCity.name.split(",");
//    return '<li><a href="#" role="linkViajeRecom" id="itemRecom'
    return '<li><a href="#" role="linkViajeRecom" id="' + data.id + '" trip="'
            + data.trip.id
            + '">'
            + data.owner.name
            + ' quiere que viajes desde '
            + des[0]+','+des[2] 
            + ' hasta '
            + has[0]+','+has[2] 
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
function armarJsonCiudad(ciudad){
	var json = '{"name":"'+ciudad.description+'","latitude":"'+ciudad.latitude+'","longitude":"'+ciudad.longitude+'","code":"'+ciudad.code+'"}',
    	obj = JSON.parse(json);
	return(obj);
}
function armarItinerario(iti){
	var itinerario = new Array();
	var viaje;
	for (var i = 0; i < iti.segments.length; i++){
		viaje = new Viaje();
		viaje.fromAirport = getAirportData(iti.segments[i].from,airports);
		viaje.toAirport = getAirportData(iti.segments[i].to,airports);
		viaje.duration = iti.segments[i].duration;
		viaje.flightid = iti.segments[i].flight_id;
		viaje.departure = iti.segments[i].departure_datetime;
		viaje.arrival = iti.segments[i].arrival_datetime;
		viaje.airline = getAirlineData(iti.segments[i].airline,airlines);
		itinerario.push(viaje);
		
	};
	return (itinerario);
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
    if(markerOrigen!=null){
    markerOrigen.setMap(null);}
    markerOrigen = null;
    if(markerDestino!=null){
    	markerDestino.setMap(null);}
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
        url: '/api/search/trip-options',
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
            airlines = data.airlines;
            var datalen = data.items.length;
            if (datalen > 0) {
                $("#sinVuelos").hide();
                $("#lstVuelos").show();
                $("#lstVuelos .list-group").html('');
                opcionesViaje = data.items;
                var i = 0;
                var line;
                
//            	Esto es par limpiar los marcadores que vengan en el mapa de un modal abierto anteriormennte
                if (markersVuelos.length > 0) {
                	console.log("TIENE MARKERS, LIMPIO");
                    for (i in markersVuelos) {
                    	markersVuelos[i].setMap(null);
                    }
                    markersVuelos.length = 0;
                }
                if (lineasVuelos.length > 0) {
                	console.log("TIENE Lineas, LIMPIO");
                    for (i in lineasVuelos) {
                    	lineasVuelos[i].setMap(null);
                    }
                    lineasVuelos.length = 0;
                }
                
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
                        var tam = currentTrip.outbound.segments.length - 1; //Maxima posicion de segmento

                        for (var x in airports) {
                            if (airports[x].code == currentTrip.outbound.segments[0].from) {
                                console.log('Encontre el aeropuerto: ' + airports[x].code + ' en L: ' + airports[x].latitude + ' Lon: ' + airports[x].longitude + ' - ' + airports[x].name);
                                airportdata.push({"latitude": airports[x].latitude, "longitude": airports[x].longitude, "descripcion": airports[x].name});
                            }
                        }
                        for (var x in airports) {
                            if (airports[x].code == currentTrip.outbound.segments[tam].to) {
                                console.log('Encontre el aeropuerto: ' + airports[x].code + ' en L: ' + airports[x].latitude + ' Lon: ' + airports[x].longitude + ' - ' + airports[x].name);
                                airportdata.push({"latitude": airports[x].latitude, "longitude": airports[x].longitude, "descripcion": airports[x].name});
                            }
                        }
                    } else {
                        currentTrip.inbound = opcionesViaje[i_vuelo].inbound_choices[i_alternativa];
                        var tam = currentTrip.inbound.segments.length - 1;

                        for (var x in airports) {
                            if (airports[x].code == currentTrip.inbound.segments[0].from) {
                                console.log('Encontre el aeropuerto: ' + airports[x].code + ' en L: ' + airports[x].latitude + ' Lon: ' + airports[x].longitude + ' - ' + airports[x].name);
                                airportdata.push({"latitude": airports[x].latitude, "longitude": airports[x].longitude, "descripcion": airports[x].name});
                            }
                        }
                        for (var x in airports) {
                            if (airports[x].code == currentTrip.inbound.segments[tam].to) {
                                console.log('Encontre el aeropuerto: ' + airports[x].code + ' en L: ' + airports[x].latitude + ' Lon: ' + airports[x].longitude + ' - ' + airports[x].name);
                                airportdata.push({"latitude": airports[x].latitude, "longitude": airports[x].longitude, "descripcion": airports[x].name});
                            }
                        }
                    }
                    drawFlightRoute(airportdata);
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
    	if (airportlist[i]!=null){
    		if (code == airportlist[i].code) {
    			return airportlist[i];
    		}
    	}
    }
    return null;
}
//busco la aerolinea que quiero por codigo
function getAirlineData(code, airlinelist) {
	for (var i = 0; i < airlinelist.length; i++) {
		if (airlinelist[i]!=null){
			if (code == airlinelist[i].code) {
				return airlinelist[i];
			}
		}
	}
    return null;
}

function getInfoAirportsAndMap(flight) {
    var prep = '';
    prep = "&code=" + flight.airportCodesAsSet.join("&code=")
    $.ajax({
        url: '/api/airports?' + prep,
        dataType: 'json',
        success: function (data) {
            result = data;
            drawFlightRoute(data);
        },
        error: function () {
            console.log("ERROR no se puede dibujar ruta");
        }
    });
}
//** funciones ajax **************************************************************

//Clicks de botones de Viajes
//******************************************************************
function initClickDetalle(id) {

// reviso si la lista de recomendaciones está abierta y la cierro si hace falta
	$("#modDetalleViaje").modal('show');
    var url;
    if(id.data.origen=="aceptado"){
    	url = '/api/me/accepted-trips/'
    	$("#modDetalleViaje #btnRecomendarViaje").hide();
    }else{
    	url = '/api/me/created-trips/'
    	$("#modDetalleViaje #btnRecomendarViaje").show();
    }
	
    if (typeof $("#modListaRecomendaciones").data("bs.modal") != 'undefined' && $("#modListaRecomendaciones").data("bs.modal").isShown) {
        $("#modListaRecomendaciones").modal("hide");
    }
    
    console.log('El viaje a recomendar es: ' + id.data.id);
    idViajeARecomendar = id.data.id;
    
    var precio;
    var desde;
    var hasta;

    $.ajax({
        url: url + id.data.id,
        dataType: 'json',
        success: function (data) {
        	var resptrip = data.tripDetails;
            console.log("Respuesta trip");
            console.log(resptrip);
            precio = resptrip.priceDetail.total+" "+resptrip.priceDetail.currency;
            desde = resptrip.fromCity.name;
            hasta = resptrip.toCity.name;
            var des = desde.split(",");
            var has = hasta.split(",");
            var titulo = "\u00A1Tu viaje desde " + des[0]+","+des[2] + " hasta " + has[0]+","+has[2] + "!";
            $("div[id=modDetalleViaje] h4").html(titulo);
            var enter = "<br>";
            var itinerario = '<u><strong><font size="3">Itinerario de Ida: </font></strong></u>'+enter;
            var fechaSalida;
            var fechaLlegada;
            $.each(resptrip.outboundItinerary, function (index, value) {
            	fechaSalida = new Date(value.departure);
                fechaLlegada = new Date(value.arrival);
                if (fechaSalida.toString("dd/MM/yyyy") == fechaLlegada.toString("dd/MM/yyyy")) {
                    itinerario = itinerario + "<strong>--</strong>Sal\u00eds el " + fechaSalida.toString("dd/MM/yyyy") + " desde " + value.fromAirport.name + " a las " + fechaSalida.toString("HH:mm") + " hs " +
                            "y lleg\u00e1s a " + value.toAirport.name + " a las " + fechaLlegada.toString("HH:mm") + " hs del mismo d\u00eda.";
                    itinerario = itinerario + enter + "<strong>Aerol\u00EDnea:</strong> " + value.airline.name + ".";
                    itinerario = itinerario + enter + "<strong>N\u00FAmero de vuelo:</strong> " + value.flightid + ".";
                    itinerario = itinerario + enter + "<strong>Duraci\u00F3n del vuelo:</strong> " + value.duration + " hs.";
                    
                }
                else
                {
                    itinerario = itinerario + "<strong>--</strong>Sal\u00eds el " + fechaSalida.toString("dd/MM/yyyy") + " desde " + value.fromAirport.name + " a las " + fechaSalida.toString("HH:mm") + " hs " +
                            "y lleg\u00e1s el " + fechaLlegada.toString("dd/MM/yyyy") + " a " + value.toAirport.name + " a las " + fechaLlegada.toString("HH:mm") +" hs.";
                    itinerario = itinerario + enter + "<strong>Aerol\u00EDnea:</strong> " + value.airline.name + ".";
                    itinerario = itinerario + enter + "<strong>N\u00FAmero de vuelo:</strong> " + value.flightid + ".";
                    itinerario = itinerario + enter + "<strong>Duraci\u00F3n del vuelo:</strong> " + value.duration + " hs.";
                    
                }
                itinerario = itinerario + enter;
            });
            itinerario = itinerario + enter + '<u><strong><font size="3">Itinerario de Vuelta: </font></strong></u>'+enter;
            $.each(resptrip.inboundItinerary, function (index, value) {
            	fechaSalida = new Date(value.departure);
                fechaLlegada = new Date(value.arrival);
                if (fechaSalida.toString("dd/MM/yyyy") == fechaLlegada.toString("dd/MM/yyyy")) {
                    itinerario = itinerario + "<strong>--</strong>Sal\u00eds el " + fechaSalida.toString("dd/MM/yyyy") + " desde " + value.fromAirport.name + " a las " + fechaSalida.toString("HH:mm") + " hs " +
                            "y lleg\u00e1s a " + value.toAirport.name + " a las " + fechaLlegada.toString("HH:mm") + " hs del mismo d\u00eda.";
                    itinerario = itinerario + enter + "<strong>Aerol\u00EDnea:</strong> " + value.airline.name + ".";
                    itinerario = itinerario + enter + "<strong>N\u00FAmero de vuelo:</strong> " + value.flightid + ".";
                    itinerario = itinerario + enter + "<strong>Duraci\u00F3n del vuelo:</strong> " + value.duration + " hs.";
                }
                else
                {
                    itinerario = itinerario + "<strong>--</strong>Sal\u00eds el " + fechaSalida.toString("dd/MM/yyyy") + " desde " + value.fromAirport.name + " a las " + fechaSalida.toString("HH:mm") + " hs " +
                            "y lleg\u00e1s el " + fechaLlegada.toString("dd/MM/yyyy") + " a " + value.toAirport.name + " a las " + fechaLlegada.toString("HH:mm") +" hs.";
                    itinerario = itinerario + enter + "<strong>Aerol\u00EDnea:</strong> " + value.airline.name + ".";
                    itinerario = itinerario + enter + "<strong>N\u00FAmero de vuelo:</strong> " + value.flightid + ".";
                    itinerario = itinerario + enter + "<strong>Duraci\u00F3n del vuelo:</strong> " + value.duration + " hs.";
                }
                itinerario = itinerario + enter;
            });
            itinerario = itinerario + enter;
            itinerario = itinerario + enter;
            itinerario = itinerario + "<strong> Precio Total: "+precio+"</strong>";
            $("div[id=modDetalleViaje] div[class=col-md-6]").html(itinerario);
            //TODO llenar el mapa de detalle del viaje
            currentMap = mapaReview;
            var aux = new Array();
            for (var i = 0; i < resptrip.outboundItinerary.length; i++) {
            	aux.push({"latitude": resptrip.outboundItinerary[i].fromAirport.latitude, "longitude": resptrip.outboundItinerary[i].fromAirport.longitude, "descripcion": resptrip.outboundItinerary[i].fromAirport.name});
            	aux.push({"latitude": resptrip.outboundItinerary[i].toAirport.latitude, "longitude": resptrip.outboundItinerary[i].toAirport.longitude, "descripcion": resptrip.outboundItinerary[i].toAirport.name});
            }            
//        	Esto es par limpiar los marcadores que vengan en el mapa de un modal abierto anteriormennte
            if (markersVuelos.length > 0) {
            	console.log("TIENE MARKERS, LIMPIO");
                for (i in markersVuelos) {
                	markersVuelos[i].setMap(null);
                }
                markersVuelos.length = 0;
            }
            if (lineasVuelos.length > 0) {
            	console.log("TIENE Lineas, LIMPIO");
                for (i in lineasVuelos) {
                	lineasVuelos[i].setMap(null);
                }
                lineasVuelos.length = 0;
            }
//              google.maps.event.addListener(mapaReview, 'bounds_changed', function() {
//            	  console.log(mapaReview.getMapTypeId());
//            	  console.log(mapaReview.getZoom());
//            	  console.log(mapaReview.getBounds());
//            	});
            drawFlightRoute(aux);
            aux = new Array();
            for (var i = 0; i < resptrip.inboundItinerary.length; i++) {
            	aux.push({"latitude": resptrip.inboundItinerary[i].fromAirport.latitude, "longitude": resptrip.inboundItinerary[i].fromAirport.longitude, "descripcion": resptrip.inboundItinerary[i].fromAirport.name});
            	aux.push({"latitude": resptrip.inboundItinerary[i].toAirport.latitude, "longitude": resptrip.inboundItinerary[i].toAirport.longitude, "descripcion": resptrip.inboundItinerary[i].toAirport.name});
            }            
            drawFlightRoute(aux);
        }
    });

}

function initClickDetalleRecom(datos) {
	
	$("#modDetalleViajeRecom").modal('show');
	
	event.preventDefault();
    recomActiva = datos.data.idRec;
    console.log('click detalle: recomendacion id: ' + recomActiva);


    // reviso si la lista de recomendaciones está abierta y la cierro si hace falta
    if (typeof $("#modListaRecomendaciones").data("bs.modal") != 'undefined' && $("#modListaRecomendaciones").data("bs.modal").isShown) {
        $("#modListaRecomendaciones").modal("hide");
    }
    
    var precio;
    var desde;
    var hasta;
    
    $.ajax({
        url: "/api/me/received-recommendations/" + recomActiva,
        dataType: 'json',
        success: function (data) {
        	var resptrip = data.trip.tripDetails;
            console.log("Respuesta trip");
            console.log(resptrip);
            precio = resptrip.priceDetail.total+" "+resptrip.priceDetail.currency;
            desde = resptrip.fromCity.name;
            hasta = resptrip.toCity.name;
            var des = desde.split(",");
            var has = hasta.split(",");
            var titulo = "\u00A1Viaje desde " + des[0]+","+des[2] + " hasta " + has[0]+","+has[2] + "!";
            $("div[id=modDetalleViajeRecom] h4").html(titulo);
            var enter = "<br>";
            var itinerario = '<u><strong><font size="3">Itinerario de Ida: </font></strong></u>'+enter;
            var fechaSalida;
            var fechaLlegada;
            $.each(resptrip.outboundItinerary, function (index, value) {
            	fechaSalida = new Date(value.departure);
                fechaLlegada = new Date(value.arrival);
                if (fechaSalida.toString("dd/MM/yyyy") == fechaLlegada.toString("dd/MM/yyyy")) {
                    itinerario = itinerario + "<strong>--</strong>Sal\u00eds el " + fechaSalida.toString("dd/MM/yyyy") + " desde " + value.fromAirport.name + " a las " + fechaSalida.toString("HH:mm") + " hs " +
                            "y lleg\u00e1s a " + value.toAirport.name + " a las " + fechaLlegada.toString("HH:mm") + " del mismo d\u00eda.";
                    itinerario = itinerario + enter + "<strong>Aerol\u00EDnea:</strong> " + value.airline.name + ".";
                    itinerario = itinerario + enter + "<strong>N\u00FAmero de vuelo:</strong> " + value.flightid + ".";
                    itinerario = itinerario + enter + "<strong>Duraci\u00F3n del vuelo:</strong> " + value.duration + ".";
                    
                }
                else
                {
                    itinerario = itinerario + "<strong>--</strong>Sal\u00eds el " + fechaSalida.toString("dd/MM/yyyy") + " desde " + value.fromAirport.name + " a las " + fechaSalida.toString("HH:mm") + " hs " +
                            "y lleg\u00e1s el " + fechaSalida.toString("dd/MM/yyyy") + " a " + value.toAirport.name + " a las " + fechaLlegada.toString("HH:mm") +".";
                    itinerario = itinerario + enter + "<strong>Aerol\u00EDnea:</strong> " + value.airline.name + ".";
                    itinerario = itinerario + enter + "<strong>N\u00FAmero de vuelo:</strong> " + value.flightid + ".";
                    itinerario = itinerario + enter + "<strong>Duraci\u00F3n del vuelo:</strong> " + value.duration + ".";
                    
                }
                itinerario = itinerario + enter;
            });
            itinerario = itinerario + enter + '<u><strong><font size="3">Itinerario de Vuelta: </font></strong></u>'+enter;
            $.each(resptrip.inboundItinerary, function (index, value) {
            	fechaSalida = new Date(value.departure);
                fechaLlegada = new Date(value.arrival);
                if (fechaSalida.toString("dd/MM/yyyy") == fechaLlegada.toString("dd/MM/yyyy")) {
                    itinerario = itinerario + "<strong>--</strong>Sal\u00eds el " + fechaSalida.toString("dd/MM/yyyy") + " desde " + value.fromAirport.name + " a las " + fechaSalida.toString("HH:mm") + " hs " +
                            "y lleg\u00e1s a " + value.toAirport.name + " a las " + fechaLlegada.toString("HH:mm") + " del mismo d\u00eda.";
                    itinerario = itinerario + enter + "<strong>Aerol\u00EDnea:</strong> " + value.airline.name + ".";
                    itinerario = itinerario + enter + "<strong>N\u00FAmero de vuelo:</strong> " + value.flightid + ".";
                    itinerario = itinerario + enter + "<strong>Duraci\u00F3n del vuelo:</strong> " + value.duration + ".";
                }
                else
                {
                    itinerario = itinerario + "<strong>--</strong>Sal\u00eds el " + fechaSalida.toString("dd/MM/yyyy") + " desde " + value.fromAirport.name + " a las " + fechaSalida.toString("HH:mm") + " hs " +
                            "y lleg\u00e1s el " + fechaSalida.toString("dd/MM/yyyy") + " a " + value.toAirport.name + " a las " + fechaLlegada.toString("HH:mm") +".";
                    itinerario = itinerario + enter + "<strong>Aerol\u00EDnea:</strong> " + value.airline.name + ".";
                    itinerario = itinerario + enter + "<strong>N\u00FAmero de vuelo:</strong> " + value.flightid + ".";
                    itinerario = itinerario + enter + "<strong>Duraci\u00F3n del vuelo:</strong> " + value.duration + ".";
                }
                itinerario = itinerario + enter;
            });
            itinerario = itinerario + enter;
            itinerario = itinerario + enter;
            itinerario = itinerario + "<strong> Precio Total: "+precio+"</strong>";
            $("div[id=modDetalleViajeRecom] div[class=col-md-6]").html(itinerario);
            //TODO llenar el mapa de detalle del viaje
            currentMap = mapaReviewRecom;
            var aux = new Array();
            for (var i = 0; i < resptrip.outboundItinerary.length; i++) {
            	aux.push({"latitude": resptrip.outboundItinerary[i].fromAirport.latitude, "longitude": resptrip.outboundItinerary[i].fromAirport.longitude, "descripcion": resptrip.outboundItinerary[i].fromAirport.name});
            	aux.push({"latitude": resptrip.outboundItinerary[i].toAirport.latitude, "longitude": resptrip.outboundItinerary[i].toAirport.longitude, "descripcion": resptrip.outboundItinerary[i].toAirport.name});
            }            
//        	Esto es par limpiar los marcadores que vengan en el mapa de un modal abierto anteriormennte
            if (markersVuelos.length > 0) {
            	console.log("TIENE MARKERS, LIMPIO");
                for (i in markersVuelos) {
                	markersVuelos[i].setMap(null);
                }
                markersVuelos.length = 0;
            }
            if (lineasVuelos.length > 0) {
            	console.log("TIENE Lineas, LIMPIO");
                for (i in lineasVuelos) {
                	lineasVuelos[i].setMap(null);
                }
                lineasVuelos.length = 0;
            }
//              google.maps.event.addListener(mapaReview, 'bounds_changed', function() {
//            	  console.log(mapaReview.getMapTypeId());
//            	  console.log(mapaReview.getZoom());
//            	  console.log(mapaReview.getBounds());
//            	});
            drawFlightRoute(aux);
            aux = new Array();
            for (var i = 0; i < resptrip.inboundItinerary.length; i++) {
            	aux.push({"latitude": resptrip.inboundItinerary[i].fromAirport.latitude, "longitude": resptrip.inboundItinerary[i].fromAirport.longitude, "descripcion": resptrip.inboundItinerary[i].fromAirport.name});
            	aux.push({"latitude": resptrip.inboundItinerary[i].toAirport.latitude, "longitude": resptrip.inboundItinerary[i].toAirport.longitude, "descripcion": resptrip.inboundItinerary[i].toAirport.name});
            }            
            drawFlightRoute(aux);
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
    $.ajax({
        url: '/api/me/created-trips/' + idViajeParaRecomendar.data,
        dataType: 'json',
        success: function (data) {
            console.log(data);
            var desde = data.tripDetails.fromCity.name;
            var hasta = data.tripDetails.toCity.name;
            var des = desde.split(",");
            var has = hasta.split(",");
            var titulo = "\u00A1Recomenda tu viaje desde " + des[0]+","+des[2] + " hasta " + has[0]+","+has[2] + " a tus amigos!";
            $("#modRecomendar h4").html(titulo);
        }
    })
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
        url: "/api/me/created-trips/" + idViajeAEliminar.data,
        dataType: "text",
        method: "DELETE",
        success: function (data) {
        	contViajes=0;
            console.log(data);
            $("#listViajes").html('<div class="list-group-item active"><h4>Tus viajes</h4></div>' +
                    '<div class="list-group-item" id="itemSinViaje">Todav\u00eda no hiciste ning\u00fan viaje <span class="glyphicon glyphicon-thumbs-down"></span></div>');
            $.ajax({
                url: '/api/me/created-trips/',
                dataType: 'json',
                success: function (data) {
                    if (data.length != 0) {
                        $("#itemSinViaje").hide();
                        $.each(data, function (index, value) {
                            $("#listViajes").append(getViajesPropiosHTML(value));
                            $("div[id=" + value.id + "] a[role=linkViaje]").click({"id":value.id,"origen":"creado"}, initClickDetalle);
                            $("div[id=" + value.id + "] button[id=btnRecomendarViaje]").click(value.id, initClickRecomendar);
                            $("div[id=" + value.id + "] a[id=eliminarViaje]").click(value.id, initClickEliminar);
                            $("div[id=" + value.id + "] a[id=compartirViaje]").click({"id":value.id,"origen":"creado"}, initClickCompartir);
                        });
                    }
                }
            });
        }
    })
}

function initClickCompartir(idViajeACompartir) {
    console.log(idViajeACompartir.data);
    var url;
    if(idViajeACompartir.data.origen=="aceptado"){
    	url = '/api/me/accepted-trips/'
    }else{
    	url = '/api/me/created-trips/'
    }
    // reviso si la lista de recomendaciones está abierta y la cierro si hace falta
    if (typeof $("#modListaRecomendaciones").data("bs.modal") != 'undefined' && $("#modListaRecomendaciones").data("bs.modal").isShown) {
        $("#modListaRecomendaciones").modal("hide");
    }
    $.ajax({
        url: url + idViajeACompartir.data.id,
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
//Clicks de botones de Viajes
//******************************************************************

//helpers autocomplete
//******************************************************************
function autocomplete_processCities(data, response) {
    if (data.length == 0) {
        data.push({
            'code': '',
            'name': 'No se encotraron ciudades con este nombre',
            'value': ''
        });
        response(data);
    } else {
        response($.map(data, function (item) {
            return {
                code: item.code,
//             label: item.description,
//             value: item.description,
                name: item.name,
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
        return $('<li class="ui-state-disabled">' + item.name + '</li>')
                .appendTo(ul);
    } else {
        return $("<li>").append("<a>" + item.name + "</a>").appendTo(ul);
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

function drawFlightRoute(airportdata) {
	
//    markersVuelos = [];
    
    var latlngbounds = new google.maps.LatLngBounds();
    
    for (var i = 0; i < airportdata.length; i++) {
        var marker = setMapMarker(currentMap, airportdata[i].latitude, airportdata[i].longitude, airportdata[i].description);
        markersVuelos.push(marker);
        latlngbounds.extend(marker.getPosition());
    }
    
    currentMap.fitBounds(latlngbounds);
    
//  Esto es para asegurarme de que se centre el mapa, ya que en el modal de detalle tenia problemas
//  Porque se me cambiaban las bounds despues de setearlas correctamente. 
//  Escuchando solo 1 vez, me aseguro de que queden las que setie.
    google.maps.event.addListenerOnce(currentMap, 'tilesloaded', function(){
	  currentMap.setCenter(latlngbounds.getCenter());
	  currentMap.fitBounds(latlngbounds);
	  });
    
    var path = [];
    for (var i = 0; i < markersVuelos.length; i++) {
        path.push(markersVuelos[i].getPosition());
    }
    var color = getRandomColor();
    
    var flightPath = new google.maps.Polyline({
        path: path,
        strokeColor: color,
        strokeOpacity: 0.8,
        strokeWeight: 2,
        map: currentMap
    });
    lineasVuelos.push(flightPath);
    
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

//####################### INICIO FACEBOOK #######################################
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

//        var url = '/api/passengers/query?id=' + id;
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
        /**
         * Lleno mis viajes anteriores
         */
        console.log("aca");
        $.ajax({
            url: '/api/me/token',
            dataType: 'text',
            success: function (data) {
               console.log(data);
               appToken = data;
            },
        	error: function(data){
        		console.log(data);
        	}
        });
        //#############################################################
        
        /**
         * Implemento la carga de viajes del usuario mediante rest
         */


        /**
         * Lleno mis viajes anteriores
         */
        $.ajax({
            url: '/api/me/created-trips',
            dataType: 'json',
            success: function (data) {
                if (data.length != 0) {
                    $("#itemSinViaje").hide();
                    $.each(data, function (index, value) {
                        $("#listViajes").append(getViajesPropiosHTML(value));
                        $("div[id=" + value.id + "] a[role=linkViaje]").click({"id":value.id,"origen":"creado"}, initClickDetalle);
                        $("div[id=" + value.id + "] button[id=btnRecomendarViaje]").click(value.id, initClickRecomendar);
                        $("div[id=" + value.id + "] a[id=eliminarViaje]").click(value.id, initClickEliminar);
                        $("div[id=" + value.id + "] a[id=compartirViaje]").click({"id":value.id,"origen":"creado"}, initClickCompartir);
                    });
                }
            }
        }
        );

        /**
         * Lleno mis viajes aceptados
         */
        $.ajax({
            url: '/api/me/accepted-trips',
            dataType: 'json',
            success: function (data) {
                if (data.length != 0) {
                    $("#itemSinViajeAceptado").hide();
                    $.each(data, function (index, value) {
                    	console.log(value);
                        $("#listViajesAceptados").append(getViajesAceptadosHTML(value));
                        $("div[id=" + value.trip_id + "] a[role=linkViaje]").click({"id":value.trip_id,"origen":"aceptado"}, initClickDetalle);
//                        $("div[id=" + value.trip_id + "] button[id=btnRecomendarViaje]").click(value.trip_id, initClickRecomendar);
                        $("div[id=" + value.trip_id + "] a[id=compartirViaje]").click({"id":value.trip_id,"origen":"aceptado"}, initClickCompartir);
                    });
                }
            }
        }
        );

        /**
         * LLeno los viajes de los amigos y LLeno los amigos que pueden ser destino de recomendaciones
         */
        $.ajax({
            url: '/api/me/friends',
            dataType: 'json',
            success: function (data) {
                $.each(data.data, function (index, value) {
                    var nombreCom = value.name;
//                    console.log('Agrego ' + value.id + ' a lista de amigos.')
//                    listIdAmigosARecomendar.push(value.id);
//                    listAmigos.push(nombreCom);
                    $("#itemSinViajeAmigo").hide();
                    listIdAmigosARecomendar.push({"id": value.id, "name": nombreCom});
                    listAmigos.push(nombreCom);
//                  Aca lleno los viajes de este amigo  
                    $.ajax({
                        url: '/api/me/friends-trips?friend-id=' + value.id,
                        dataType: 'json',
                        success: function (data) {
                            if (data.length !== 0) {
                                $.each(data, function (index, values) {
                                    $("#listViajesAmigos").append(getViajesDeAmigosHTML(values));
//                                    $("div[id=" + values.id + "] a[role=linkViaje]").click(values.id, initClickDetalle);
//                                    $("div[id=" + values.id + "] button[id=btnRecomendarViaje]").click(values.id, initClickRecomendar);
//                                    $("div[id=" + values.id + "] a[id=compartirViaje]").click(values.id, initClickCompartir);
                                });
                            }else{
                            	$("#itemSinViajeAmigo").show();
                            }
                        },
                        error: function(data){
                        	console.log(data.responseText);
                        	$("#itemSinViajeAmigo").show();
                        }
                    });
                });
            }
        });

        /**
         * Lleno con las recomendaciones que me hicieron
         */
        $.ajax({
            url: '/api/me/received-recommendations/',
            dataType: 'json',
            success: function (data) {
//      		var i = 1;
                if (data.length != 0) {
                	$("#CeroRecom").remove();
                    $.each(data, function (index, value) {
//      				$("#listRecomendaciones").append(insertarRecomendacionesDeViajes(value, i));
//      				i++;
                        $("#listRecomendaciones").append(insertarRecomendacionesDeViajes(value));
                        //<a href="#" role="linkViajeRecom" id="itemRecom" trip="4">
                        $("a[role=linkViajeRecom][id=" + value.id + "][trip=" + value.trip.id + "]").click({"idRec": value.id}, initClickDetalleRecom);
                    });
                    $("#listRecomendaciones").append("<li class=\"divider\"></li>");
                    $("#listRecomendaciones").append("<li><a href=\"#\" id=\"verTodasRecomendaciones\">Ver todas las recomendaciones</a></li>");
                }
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



function getViajeParaFB(from, to, salida, vuelta) {
	var des = from.split(",");
	var has = to.split(",");
	return 'TACS POR EL MUNDO: Viajo desde '
	+ des[0]+','+des[2] 
	+ ' a '
	+ has[0]+','+has[2] 
	+ ' saliendo el dia '
	+ salida
	+ ' y volviendo el dia '
	+ vuelta;
}

//PUBLICAR EN MURO CUANDO SE CREA UN VIAJE
function publicar() {
//	pruebo las cosas del mapa
//    console.log("https://maps.googleapis.com/maps/api/staticmap?center=" + mapaVuelo.getCenter().toUrlValue() +
//            "&zoom=" + mapaVuelo.getZoom() +
//            "&maptype=" + mapaVuelo.getMapTypeId() +
//            "&size=600x400" +
//            "&markers=color:green%7C" + markerOrigen.getPosition().toString().trim() +
//            "%7C" + markerDestino.getPosition().toString().trim() +
//            "&path=color:red%7C" + markerOrigen.getPosition().toString().trim() +
//            "%7C" + markerDestino.getPosition().toString().trim());
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
//	console.log("https://maps.googleapis.com/maps/api/staticmap?" +
//	"maptype=ROADMAP" +
//	"&size=600x400" +
//	"&markers=color:green%7C" +viaje.tripDetails.fromCity.latitude +","+viaje.tripDetails.fromCity.longitude +
//	"%7C" + viaje.tripDetails.toCity.latitude +","+viaje.tripDetails.toCity.longitude+
//	"&path=color:red%7C" +viaje.tripDetails.fromCity.latitude +","+viaje.tripDetails.fromCity.longitude +
//	"%7C" +viaje.tripDetails.toCity.latitude +","+viaje.tripDetails.toCity.longitude);
//	posteo en muro de facebook
	FB.api('/' + id + '/feed', 'post', {
		message: getViajeParaFB(viaje.tripDetails.fromCity.name, viaje.tripDetails.toCity.name, viaje.tripDetails.outboundDate, viaje.tripDetails.inboundDate),
		picture: "https://maps.googleapis.com/maps/api/staticmap?" +
			"maptype=ROADMAP" +
			"&size=600x400" +
			"&markers=color:green%7C" +viaje.tripDetails.fromCity.latitude +","+viaje.tripDetails.fromCity.longitude +
			"%7C" + viaje.tripDetails.toCity.latitude +","+viaje.tripDetails.toCity.longitude+
			"&path=color:red%7C" +viaje.tripDetails.fromCity.latitude +","+viaje.tripDetails.fromCity.longitude +
			"%7C" +viaje.tripDetails.toCity.latitude +","+viaje.tripDetails.toCity.longitude,
		name: 'TACS POR EL MUNDO',
		description: 'viaje',
		access_token: token
	}, function (data) {
		console.log(data);
		bootbox.alert("Excelente! Tu nuevo viaje ya esta publicado", function () {
		});
	});

}
//####################### FACEBOOK #######################################
