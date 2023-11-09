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

function updateResults(alias) {
    $.ajax({
        url: "http://localhost:8080/results?alias=" + alias
    }).then(function(data){
        $('#results-div').show();
        $('#results-body').empty();

        data.forEach(function(row){
            $('#results-body')
                .append('<tr>' +
                '<td>' + row.id + '</td>' +
                '<td>' + row.multiplication.factorA + ' x ' + row.multiplication.factorB + '</td>' +
                '<td>' + row.resultAttempt + '</td>' +
                '<td>' + (row.correct == true ? 'YES': 'NO') + '</td>' +
                '</tr>');
        });
    });
}

$(document).ready(function(){
    updateMultiplication();

    $("#attempt-form").submit(function(event){
        event.preventDefault();

        var a = $('.multiplication-a').text();
        var b = $('.multiplication-b').text();

        var form = $(this);
        var attempt = form.find("input[name='result-attempt']").val();
        var userAlias = form.find("input[name='user-alias']").val();

        var data = {
            user: {
                alias: userAlias
            },
            multiplication: {
                factorA: a,
                factorB: b
            },
            resultAttempt: attempt
        };

        $.ajax({
            url: "http://localhost:8080/results",
            type: 'POST',
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            success: function(result) {
                if (result.correct) {
                    $('.result-message').empty().append("The result is correct! Congratulations!");
                } else {
                    $('.result-message').empty().append("Oops that's not correct! But keep trying!");
                }
            }
        });
        updateMultiplication();
        updateResults(userAlias);
    });
});
