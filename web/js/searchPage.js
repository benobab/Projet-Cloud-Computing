function getAllDomaine(){
    $.get("/getalldomains",function(data,status){
        var colonTemplate = $('#templates').find('[data-role="patternColon"]');
        var dataParsed = JSON.parse(data);
        for(d in dataParsed){
            var line = colonTemplate.clone();
            line.find("[data-role='paternLabel']").html(dataParsed[d]);
            $("#listDomains").append(line);
        }
    });
}

function search(){

    if($("#inputSearch").val() != ""){
        document.location = "/ha-result-screen.html?search=" + encodeURI($("#inputSearch").val());
    }
    else {
        alert("No search");
    }

}