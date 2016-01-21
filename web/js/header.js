function onSignIn(googleUser) {
    // Useful data for your client-side scripts:
    var profile = googleUser.getBasicProfile();
    console.log("ID: " + profile.getId()); // Don't send this directly to your server!
    console.log("Name: " + profile.getName());
    console.log("Image URL: " + profile.getImageUrl());
    console.log("Email: " + profile.getEmail());

    // The ID token you need to pass to your backend:
    var id_token = googleUser.getAuthResponse().id_token;
    window.localStorage.google_token = id_token;
    console.log("ID Token: " + id_token);

    window.localStorage.name = profile.getName();
    window.localStorage.email = profile.getEmail();
    displayUser();

    $.post("/connection",{email:profile.getEmail(),name:profile.getName()}).done(function(){
        console.log("ok lors de la connection");
    }).fail(function(){
        console.log("ko lors de la connection");
    });


};

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        console.log('User signed out.');
        $.post("/deconnection",{name:window.localStorage.name}).done(function(){
            console.log("ok deco");
        }).fail(function(){
            console.log("ko deco");
        });
        window.localStorage.clear();
        $("#buttonGoogleLogout").css("display", "none");
        $("#buttonGoogleLogin").css("display", "block");
        $("#divDisplayUser").css("display", "none");
    });
};

function testConnection(){
    if(window.localStorage.google_token != null && window.localStorage.google_token != undefined && window.localStorage.google_token != "undefined"){
        displayUser();
    }
}

//TODO lol

function displayUser(){
    $("#buttonGoogleLogout").css("display", "block");
    $("#buttonGoogleLogin").css("display", "none");
    $("#divDisplayUser").css("display", "block");
    $("#divDisplayUser").html("<a href='/userdetails.html'> " + window.localStorage.name +" | "+ window.localStorage.email + "</a> " + "<input type='button' value='Logout' class='btn btn-danger'' id='buttonGoogleLogout' onclick='signOut();'/>");
}

$(function() {
    testConnection();
});