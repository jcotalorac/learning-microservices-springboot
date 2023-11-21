var SERVER_URL = "http://localhost:8000/api";

function updateMultiplication() {
    $.ajax({
        url: SERVER_URL + "/multiplications/random"
    }).then(function(data){
        $("#attempt-form").find("input[name='result-attempt']").val("");
        $("#attempt-form").find("input[name='user-alias']").val("");

        $('.multiplication-a').empty().append(data.factorA);
        $('.multiplication-b').empty().append(data.factorB);
    });
}

function updateResults(alias) {
    var userId = -1;
    $.ajax({
        async: false,
        url: SERVER_URL + "/results?alias=" + alias,
        success: function(data){
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
                         userId = data[0].user.id;
                     }
    });
    return userId;
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
            url: SERVER_URL + "/results",
            type: 'POST',
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            success: function(result) {
                if (result.correct) {
                    $('.result-message').empty().append("<p class='bg-success text-center'>The result is correct! Congratulations!</p>");
                } else {
                    $('.result-message').empty().append("<p class='bg-danger text-center'>Oops that's not correct! But keep trying!</p>");
                }
            }
        });
        updateMultiplication();

        var userId = updateResults(userAlias);
        updateStats(userId);
        updateLeaderBoard();
    });
});
