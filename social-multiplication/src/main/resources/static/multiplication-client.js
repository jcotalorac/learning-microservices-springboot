function updateMultiplication() {
    $.ajax({
        url: "http://localhost:8080/multiplications/random"
    }).then(function(data){
        $("#attempt-form").find("input[name='result-attempt']").val("");
        $("#attempt-form").find("input[name='user-alias']").val("");
    });
}

$(document).ready(function(){
    updateMultiplication();
});