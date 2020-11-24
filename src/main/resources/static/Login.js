function checkUser() {
    var a = document.getElementById("u");
    var username = a.value;
    var b = document.getElementById("p");
    var passwd = b.value;
    if (username != "admin" && passwd != "admin") {
        alert("Please input right username and passwd!");
    }
    else{
        window.location.href = 'main.html';
    }
}

