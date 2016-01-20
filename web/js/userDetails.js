/**
 * Created by Benobab on 20/01/16.
 */
getUserTrainingPlans = function(){
    $.post("/userdetails",{email:window.localStorage.email}).done(function(data, textStatus){
        console.log("Ok récupération des détails utilisateurs");
        var dataParsed = JSON.parse(data);
        var trainingPlanPatern = $('#patterns').find('[data-role="patternTraining"]');
        for(var i = 0; i < dataParsed.trainingPlans.length; i++){
            dataParsed.trainingPlans[i].duration = resetTotalDuration(dataParsed.trainingPlans[i].exercises);
            var newHtmlPlan = trainingPlanPatern.clone();
            newHtmlPlan.find("[data-role='labelTraining']").html(dataParsed.trainingPlans[i].title);
            newHtmlPlan.find("[data-role='labelDuration']").html(dataParsed.trainingPlans[i].duration);
            $("#trainingPlansList").append(newHtmlPlan);

        }
        var exercisePatern = $("#patterns").find('[data-role="patternExercise"]');
        for(var j = 0; j < dataParsed.exercises.length; j++){
            var newHtmlExercise = exercisePatern.clone();
            newHtmlExercise.find("[data-role='labelExercise']").html(dataParsed.exercises[j].title);
            newHtmlExercise.find("[data-role='labelDuration']").html(dataParsed.exercises[j].duration);
            $("#listExercises").append(newHtmlExercise);
        }
        console.log("LOL");
    }).fail(function(){
        console.log("Erreur récupération de la recherche");
    });
}