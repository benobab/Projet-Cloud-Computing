/**
 * Created by Nicolas Sagon on 20/01/2016.
 */
function isConnected(){
    return window.localStorage.google_token != null && window.localStorage.google_token != undefined && window.localStorage.google_token != "undefined";
}

function getConnectedEmail(){
    return window.localStorage.email;
}