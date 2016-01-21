getUserValidatedExercises = function() {
    $.post("/UserTrainings",{email:window.localStorage.email}).done(function(data, textStatus){
        var dataParsed = JSON.parse(data);
        var trainingPlanPatern = $('#patterns').find('[data-role="patternTable"]');
        for (var i = 0; i < dataParsed.length; i++) {
            var newHtmlPlan = trainingPlanPatern.clone();
            newHtmlPlan.find("[data-role='tdDate']").html(dataParsed[i].date);
            newHtmlPlan.find("[data-role='tdTraining']").html(dataParsed[i].trainingPlan);
            newHtmlPlan.find("[data-role='tdExercise']").html(dataParsed[i].exercise);
            $("#tableUserDetail").append(newHtmlPlan);
        }
    }).fail(function(){
        alert("Erreur lors de la récupération des exercices.");
    });
}