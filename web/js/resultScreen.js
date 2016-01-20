/**
 * Created by Nicolas Sagon on 20/01/2016.
 */

getResearch = function(){

    $.post("/search", {keywords : queryParameters.search}).done(function(data, textStatus){
        console.log("LOL");
    }).fail(function(){
        console.log("Erreur récupération de la rechercher");
    });
}

var queryParameters = function () {
    var query_string = {};
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (typeof query_string[pair[0]] === "undefined") {
            query_string[pair[0]] = decodeURIComponent(pair[1]);
        } else if (typeof query_string[pair[0]] === "string") {
            var arr = [query_string[pair[0]], decodeURIComponent(pair[1])];
            query_string[pair[0]] = arr;
        } else {
            query_string[pair[0]].push(decodeURIComponent(pair[1]));
        }
    }
    return query_string;
}();


$(function(){
   getResearch();
});