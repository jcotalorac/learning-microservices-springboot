function updateLeaderBoard() {
    $.ajax({
        url: "http://localhost:8081/leaders"
    }).then();
}