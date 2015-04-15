$(function(){
	//cargo los distintos pedazos de html
	var cities = ['Londres','Salta','New York','Río de Janeiro','Beirut'];
	$("#destino").autocomplete({
      source: cities
    });
	
	// funciones de login ********************************
    $('#fbLoginModal').modal('show');
    
    //handler del evento aceptar
    $('#fbLoginOK').click(function(event){
    	event.preventDefault();
    	//comunicación con facebook
    	//si devuelve bien
    	document.location.href='html/dashboard.html';
    	//si falla, lo saco
    });
    
    //handler del boton cancelar 
    $('#fbLoginCancelar').click(function(event){
    	event.preventDefault();
    	console.log('cancel log in fb');
    });
    // ******************************************************
});
