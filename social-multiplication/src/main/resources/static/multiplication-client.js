function updateMultiplication() {
    $.ajax({
        url: "http://localhost:8080/multiplications/random"
    }).then(function(data){
        $("#attempt-form").find("input[name='result-attempt']").val("");
        $("#attempt-form").find("input[name='user-alias']").val("");

        $('.multiplication-a').empty().append(data.factorA);
        $('.multiplication-b').empty().append(data.factorB);
    });
}

$(document).ready(function(){
    updateMultiplication();

    $("attempt-form").submit(function(event){
        event.preventDefault();

        var a = $('.multiplication-a').text();
        var b = $('.multiplication-b').text();

        var form = $(this);
        var attempt = $form.find("input[name='result-attempt']").val();
        var userAlias = $form.find("input[name='user-alias']").val();
    });
});