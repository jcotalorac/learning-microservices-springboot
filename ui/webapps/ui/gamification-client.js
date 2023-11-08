function updateLeaderBoard() {
    $.ajax({
        url: "http://localhost:8081/leaders"
    }).then(function(data){
        $('#leaderboard-body').empty();
        data.forEach(function(row){
            $('#leaderboard-body').append('<tr>' +
            '<td>' + row.userId + '</td>' +
            '<td>' + row.totalScore + '</td>' +
            '</tr>');
        });
    });
}

function updateStats(userId) {
    $.ajax(
    );
}