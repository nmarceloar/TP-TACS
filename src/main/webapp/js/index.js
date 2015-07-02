$(function() {

	// cargo los distintos pedazos de html
	var cities = [ 'Londres',
		'Salta',
		'New York',
		'Río de Janeiro',
		'Beirut' ];
	$("#destino").autocomplete({
		source : cities
	});

	// funciones de login ********************************
	$('#fbLoginModal').modal('show');

	// handler del evento aceptar
	$('#fbLoginOK').click(function(event) {

		event.preventDefault();
		// comunicación con facebook
		// si devuelve bien
		// document.location.href = 'html/dashboard.html';
		// si falla, lo saco
	});

	// handler del boton cancelar
	$('#fbLoginCancelar').click(function(event) {

		event.preventDefault();
		console.log('cancel log in fb');
	});
	// ******************************************************
});


$body = $("body");

$(document).on({
    ajaxStart: function() { $body.addClass("loading");    },
     ajaxStop: function() { $body.removeClass("loading"); }    
});

// **************************************************************FACEBOOK
// This is called with the results from from FB.getLoginStatus().
function statusChangeCallback(response) {

	console.log('statusChangeCallback');
	console.log(response);
	// The response object is returned with a status field that lets the
	// app know the current login status of the person.
	// Full docs on the response object can be found in the documentation
	// for FB.getLoginStatus().
	if (response.status === 'connected') {

		console.log("Esta logueado y acepto");
		console.log("TOKEN");
		console.log(response.authResponse.accessToken);
		console.log("EXPIRES IN");
		console.log(response.authResponse.expiresIn);
		console.log("ID");
		console.log(response.authResponse.userID);
		console.log("API--");

		// FB.api('/me', function(response) {
		// console.log(JSON.stringify(response));
		// });

		// Logged into your app and Facebook.
		$.ajax({
			type : 'POST',
			url : '/api/login',
			data : "token=" + response.authResponse.accessToken,
//			dataType : 'text',
			success : function(data, textStatus, jQxhr) {
				console.log(textStatus);
				console.log(data);
				console.log(jQxhr);
				var url = "/html/dashboard.html";
				$(location).attr('href', url);
			},
			error : function(jqXhr, textStatus, errorThrown) {

				console.log(textStatus);
				console.log(errorThrown);
				console.log(jQxhr);

			}
		});

		console.log("esto seguro primero");

		// testAPI();

	} else if (response.status === 'not_authorized') {
		console
			.log("Esta logueado en face, pero todavia no acepto")
		// The person is logged into Facebook, but not your app.
		document.getElementById('status').innerHTML = 'Para usar TACS por el mundo ten\u00e9s que iniciar sesi\u00f3n ' + 'con tu cuenta de FaceBook.';
	} else {
		// The person is not logged into Facebook, so we're not sure if
		// they are logged into this app or not.
		console.log("No esta logueado en face")
		document.getElementById('status').innerHTML = 'Para usar TACS por el mundo ten\u00e9s que iniciar sesi\u00f3n ' + 'con tu cuenta de FaceBook.';
	}
}

// This function is called when someone finishes with the Login
// Button. See the onlogin handler attached to it in the sample
// code below.
function checkLoginState() {

	FB.getLoginStatus(function(response) {

		statusChangeCallback(response);
	});
}

// Load the SDK asynchronously
(function(d, s, id) {

	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id))
		return;
	js = d.createElement(s);
	js.id = id;
	js.src = "//connect.facebook.net/es_LA/sdk.js";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

// ACA SE PONEN LOS DATOS DE LA APP
window.fbAsyncInit = function() {

	FB.init({
//		appId : '1431380883824864',
		appId : '1586547271608233',
		cookie : true, // enable cookies to allow the server to access
		// the session
		xfbml : true, // parse social plugins on this page
		version : 'v2.3' // use version 2.2
	});

	// Now that we've initialized the JavaScript SDK, we call
	// FB.getLoginStatus(). This function gets the state of the
	// person visiting this page and can return one of three states to
	// the callback you provide. They can be:
	//
	// 1. Logged into your app ('connected')
	// 2. Logged into Facebook, but not your app ('not_authorized')
	// 3. Not logged into Facebook and can't tell if they are logged into
	// your app or not.
	//
	// These three cases are handled in the callback function.

	FB.getLoginStatus(function(response) {

		statusChangeCallback(response);
	});

};

// Here we run a very simple test of the Graph API after login is
// successful. See statusChangeCallback() for when this call is made.
function testAPI() {

	console.log('Welcome!  Fetching your information.... ');
	FB
		.api('/me',
			function(response) {

				console
					.log('Successful login for: ' + response.name);
				document.getElementById('status').innerHTML = 'Thanks for logging in, ' + response.name +
					'!';
			});
}

// *************************************************************************TERMINA
// FACEBOOK
