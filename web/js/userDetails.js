/**
 * Created by Benobab on 20/01/16.
 */
getUserTrainingPlans = function(){
    $.post("/UserTrainings",{email:window.localStorage.email}).done(function(data, textStatus){
        console.log("Ok récupération des détails utilisateurs");
        var dataParsed = JSON.parse(data);
        var trainingPlanPatern = $('#patterns').find('[data-role="patternTable"]');
        for(var i = 0; i < dataParsed.trainingPlans.length; i++){
            dataParsed.trainingPlans[i].duration = resetTotalDuration(dataParsed.trainingPlans[i].exercises);
            var newHtmlPlan = trainingPlanPatern.clone();
            newHtmlPlan.find("[data-role='tdDate']").html(dataParsed.trainingPlans[i].createDate);
            newHtmlPlan.find("[data-role='tdTraining']").html(dataParsed.trainingPlans[i].title);
            newHtmlPlan.find("[data-role='tdTime']").html(dataParsed.trainingPlans[i].duration);
            $("#tableUserDetail").append(newHtmlPlan);

        }
    }).fail(function(){
        console.log("Erreur récupération de la recherche");
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