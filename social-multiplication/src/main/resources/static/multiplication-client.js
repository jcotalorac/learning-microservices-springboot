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

function updateStats(String alias) {
    $.ajax({
        url: "http://localhost:8080/results?alias=" + alias
    }).then(function(data){
        $('#stats-body').empty();

        data.forEach(function(row){
            $('#stats-body')
                .append('<tr>')
                    .append('<td>')
                        .append(row.id)
                    .append('</td>')
                    .append('<td>')
                        .append(row.multiplication.factorA + ' x ' + row.multiplication.factorB)
                    .append('</td>')
                    .append('<td>')
                        .append(row.resultAttempt)
                    .append('</td>')
                    .append('<td>')
                        .append((row.correct == true ? 'YES': 'NO'))
                    .append('</td>')
                .append('</tr>');
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
            url: "/results",
            type: 'POST',
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(result) {
                if (result.correct) {
                    $('.result-message').empty().append("The result is correct! Congratulations!");
                } else {
                    $('.result-message').empty().append("Oops that's not correct! But keep trying!");
                }
            }
        });
        updateMultiplication();
        updateStats(userAlias);
    });
});
