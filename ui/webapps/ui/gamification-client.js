function updateLeaderBoard() {
    $.ajax({
        url: "http://localhost:8081/leaders"
    }).then(function(data){
        $('#leaderboard-body').empty();
        data.forEach();
    });
}