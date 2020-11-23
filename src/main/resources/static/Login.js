function checkUser() {
    var a = document.getElementById("u");
    var username = a.value;
    var b = document.getElementById("p");
    var passwd = b.value;
    if (username != "" && passwd != "") {
        window.location.href = 'main.html';
    }
    else{
        alert("Please input username and passwd!");
    }
}

