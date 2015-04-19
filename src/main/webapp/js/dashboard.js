var map;

$(function(){
	//datos de prueba
	var cities = ['Londres','Salta','New York','R&iactue;o de Janeiro','Beirut'];
	var contViajes = 0;
	
	//config google maps
	google.maps.event.addDomListener(window, 'load', initialize);
	
	//inicializo los forms
	formResetViaje();
	formResetVuelos();
	//modal nuevo viaje ******************************
	/*$("#modNuevoViaje").on('shown',function() {
	    google.maps.event.trigger(map, "resize");
	});*/
	
	//cuando de hace click en un nuevo viaje
	$("#btnNuevoViaje").click(function(event){
		event.preventDefault();
		//TODO debería enforcar en el primer input del form
		$("#ciudadOrigen").focus();
		//TODO debería arreglar el corrimiento del mapa
		google.maps.event.trigger(map, "resize");
		//dispara el modal de nuevo viaje
		$("#modNuevoViaje").modal('show');
	});
	
	//el autocomplete es de jqueryui
	$("#ciudadOrigen").autocomplete({
	  //TODO ahora carga de un array estático, debería usar una de nuestras apis que dado un string te devuelve una lista de ciudades
      source: cities,
      select: function(event, ui){
    	  //evento de selección de elemento del autocomplete, cuando elige le habilito el campo de fecha de salida
    	  $("#fechaDesdeContainer").show();
      }
    });
	
	//el datepicker es un selector de fechas que te lo provee bootstrap
	$("#fechaDesde").datepicker({
        format: 'dd/mm/yyyy',
		//TODO falta hacer que te bloquee todas las fechas anteriores al día de hoy
        //startDate: 'today',
        todayHighlight: true
    })
    .change(function(e){
    	//una vez selecciona fecha, le muestro el input de ciudad destino
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
	  //TODO acá arriba quise hacer la comunucación con despegar, pero falló. de todos modos sirve de ejemplo para usarlo con nuestra api
	  source: cities,
      minLength: 3,
      select: function( event, ui ) {
    	  //muetro box de fecha de vuelta
    	  $("#fechaHastaContainer").show();
      }
    });
	
	$("#fechaHasta").datepicker({
		//TODO acá habría que poner que no pueda elegir fechas anteriores a la fecha de salida
        format: 'dd/mm/yyyy',
        startDate: '',
        todayHighlight: true
    })
    .change(function(e){
    	//muestro el botón de buscar vuelo
    	$("#btnBuscarVuelo").show();
    });
	
	//defino acciones a realizar cuando le doy click al botón de elegir vuelos
	$("#btnBuscarVuelo").click(function(event){
		event.preventDefault();
		//TODO habría que validad que las fechas sean válidads y que haya dos ciudades elegidas
		//acá levanto los vuelos
		getVuelos('Ida');
		//le asigno una acción al evento de click sobre los links de vuelos que generé
		$("a[role=vueloIda]").click(initClickIda);
		//muestro el modal de vuelos
		$("#modVuelos").modal('show');
	});
	
	$("#btnCancelarViaje").click(function(event){
		formResetViaje();
	});
	//**************************************************
	
	//modal selección de vuelos ************************
	formResetVuelos();
	
	//acciones a realizar si cancelo la selección del vuelo de ida
	$("#cancelVueloIda").click(function(event){
		/*
		 * básicamente lo que hace es ocultar la caja que tiene el detalle del vuelo elegido, ocultar los vuelos de vuelta y 
		 * volver a mostrar los vuelos de ida
		 */
		event.preventDefault();
		$("#boxVueloIda").hide();
		$("#lstVuevloVuelta").hide();
		$("#lstVuevloIda").show();
	});
	
	//acciones a realizar si cancelo la selección del vuelo de vuelta
	$("#cancelVueloVuelta").click(function(event){
		//bastante similar al anterior
		event.preventDefault();
		$("#boxVueloVuelta").hide();
		$("#lstVuevloVuelta").show();
	});
	
	//acá es donde recién se guardaría el vuelo
	$("#btnViajar").click(function(event){
		event.preventDefault();
		//el contador es solo para difernciar los datos de prueba ahora, después vuela. lo uso como id
		contViajes++;
		/*
		 * TODO acá debería llamar a la api que graba los viajes y todo lo siguiente lo haría solo si grabó bien
		 * Si no grabó bien, debería frenar la operación e informar al usuario del error.
		 * ****************************************************************************************************
		 */
		//al elemendo de la lista de viajes el adjunto el html que contiene el nuevo viaje
		$("#listViajes").append(getViajeHTML(contViajes,$("#ciudadOrigen").val(),$("#ciudadDestino").val(),
											 $("#fechaDesde").val(),$("#fechaHasta").val()));
		//le asigno acciones al evento de click sobre el nuevo viaje. sería la apertura del modal de detalle
		$("a[role=viaje][id="+contViajes+"]").click(function(event){
			event.preventDefault();
			//TODO levantar detalles del vuelo usando la api correspondiente
			$("#modDetalleViaje").modal('show');
		});
		//limpio el form para futuros viajes
		$("#itemSinViaje").hide();
		//oculto el elemento inicial que dice que no hay viajes
		formResetViaje();
		formResetVuelos();
		//*******************************************************************************************************
	});
	//si hace click en volver, solo se limpia el modal de vuelos y retorna al modal de viaje con los datos que tenía previamente
	$("#btnVolver").click(function(e){
		e.preventDefault();
		$("#modNuevoViaje").modal('show');
		formResetVuelos();
	});
	//**************************************************
});

/*
 * dados los datos del viaje me devuelve un string con html para insertar en la lista de viajes
 */
function getViajeHTML(idViaje, origen, destino, desde, hasta) {
	return '<div class="list-group-item">'
			+ '<h3 class="list-group-item-heading"><a href="#" id="'+ idViaje + '" role="viaje">Viaje 1. Desde '
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

/*
 * limpio el form inicial del viaje
 */
function formResetViaje(){
	$("#frmNuevoViaje")[0].reset();
	$("#btnBuscarVuelo").hide();
	$("#dstContainter").hide();
	$("#orgMapContainer").hide();
	$("#dstMapContainer").hide();
	$("#fechaDesdeContainer").hide();
	$("#fechaHastaContainer").hide();
}

/*
 * limpio el form de vuelos
 */
function formResetVuelos(){
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
    center:new google.maps.LatLng(51.508742,-0.120850),
    zoom:5,
    mapTypeId:google.maps.MapTypeId.ROADMAP
  };
  map=new google.maps.Map(document.getElementById("googleMapViaje"),mapProp);
  map=new google.maps.Map(document.getElementById("googleMapVuelo"),mapProp);
  map=new google.maps.Map(document.getElementById("googleMapVueloDetalle"),mapProp);
}

/*
 * defino las acciones para lo que pasa cuando se elige el vuelo de ida
 */
function initClickIda(){
	$("#lstVuevloVuelta").show();
	$("#lstVuevloIda").hide();
	$("#boxVueloIda").show();
	getVuelos('Vuelta');
	$("a[role=vueloVuelta]").click(initClickVuelta);
}

/*
 * defino las acciones para cuando se selecciona un vuelo de vuelta
 */
function initClickVuelta(){
	$("#lstVuevloVuelta").hide();
	$("#boxVueloVuelta").show();
	$("#btnViajar").show();
}

/*
 * levanto los vuelos para la pantalla de cración de viaje
 */
function getVuelos(sentido){
	//TODO $.ajax a nuestra api
	//bucle json
	$("#sinVuelos"+sentido).hide();
	$("#lstVuevlo"+sentido+" .list-group").html('');
	//TODO pongo 4 solo para ver una cierta cantidad, habría que ver cuántos vuelos vamos a mostrar y si paginamos o agregamos un scroll
	for(i=1;i<=4;i++){
		$("#lstVuevlo"+sentido+" .list-group").append(templateVuelo(sentido,i,"aeropuerto origen","aeropuerto destino","12:45","aerolinea"));
	}
}

/*
 * crea el snippet html que contiene los detalles del vuelo para la selección de vuelos
 */
function templateVuelo(sentido,idVuelo,aeropuertoOrigen,aeropuertoDestion,horarioSalida,aerolinea,horarioLlegada){
	return '<a href="#" class="list-group-item" role="vuelo'+sentido+'" id="'+idVuelo+'">'+
				'<h4 class="list-group-item-heading">Vuelo '+idVuelo+' de '+aerolinea+' de las '+horarioSalida+' horas.</h4>'+
				'<p class="list-group-item-text">Saliendo desde el aeropuerto '+aeropuertoOrigen+' llegando al aeropuerto '+aeropuertoDestion+' a las '+horarioLlegada+'</p>'+
			'</a>';
}
