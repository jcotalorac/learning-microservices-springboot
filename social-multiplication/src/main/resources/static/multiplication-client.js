function updateMultiplication() {
    $.ajax({
        url: "http://localhost:8080/multiplications/random"
    });
}

$(document).ready(function(){
    updateMultiplication();
});