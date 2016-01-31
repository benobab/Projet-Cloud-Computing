/**
 * Created by Nicolas Sagon on 20/01/2016.
 */

getResearch = function(){

    $.post("/search", {keywords : queryParameters.search}).done(function(data, textStatus){
        var dataParsed = JSON.parse(data);
        var trainingPlanPatern = $('#patterns').find('[data-role="patternTraining"]');
        for(var i = 0; i < dataParsed.trainingPlans.length; i++){
            dataParsed.trainingPlans[i].duration = resetTotalDuration(dataParsed.trainingPlans[i].exercises);
            var newHtmlPlan = trainingPlanPatern.clone();
            newHtmlPlan.find("[data-role='labelTraining']")
                .html(dataParsed.trainingPlans[i].title)
                .attr("trainingPlanId", dataParsed.trainingPlans[i].id)
                .click(function() {
                var trainingPlanId = $(this).attr('trainingPlanId');
                document.location = "/ha-result-detail-screen.html?id=" + trainingPlanId;
            });
            newHtmlPlan.find("[data-role='labelDuration']").html(dataParsed.trainingPlans[i].duration);
            $("#trainingPlansList").append(newHtmlPlan);

        }
        var exercisePatern = $("#patterns").find('[data-role="patternExercise"]');
        for(var j = 0; j < dataParsed.exercises.length; j++){
            var newHtmlExercise = exercisePatern.clone();
            newHtmlExercise.find("[data-role='labelExercise']")
                .html(dataParsed.exercises[j].title)
                .attr("trainingPlanId", dataParsed.exercises[j].trainingPlanId)
                .click(function() {
                var trainingPlanId = $(this).attr('trainingPlanId');
                document.location = "/ha-result-detail-screen.html?id=" + trainingPlanId;
            });
            newHtmlExercise.find("[data-role='labelDuration']").html(dataParsed.exercises[j].duration);
            $("#listExercises").append(newHtmlExercise);
        }
    }).fail(function(){
    });

}


function resetTotalDuration(listExercices) {
    var duration = "";
    var durationHours = 0;
    var durationMinutes = 0;
    var durationSeconds = 0;
    $.each(listExercices, function (i, e) {
        var splitValue = e.duration.split(':');
        durationHours += tryParseInt(splitValue[0]);
        durationMinutes += tryParseInt(splitValue[1]);
        durationSeconds += tryParseInt(splitValue[2]);
    });
    durationMinutes += Math.floor(durationSeconds / 60);
    durationSeconds = durationSeconds % 60;
    durationHours += Math.floor(durationMinutes / 60);
    durationMinutes = durationMinutes % 60;
    if (durationHours < 10) {
        duration += "0" + durationHours + ":";
    } else {
        duration += durationHours + ":"
    }
    if (durationMinutes < 10) {
        duration += "0" + durationMinutes + ":";
    } else {
        duration += durationMinutes + ":";
    }
    if (durationSeconds < 10) {
        duration += "0" + durationSeconds;
    } else {
        duration += durationSeconds;
    }
    return duration;
}

function tryParseInt(str) {
    if (isNaN(parseInt(str)) || !isFinite(str))
        return undefined;
    return parseInt(str);
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