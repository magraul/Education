var xmlHttp = createXmlHttpRequest();
var marca;
var model;
var alertat = false;
document.addEventListener("DOMContentLoaded", function(event) {
    marca = document.getElementById("marca").value;
    model = document.getElementById("model").value;
    xmlHttp.open("GET", "http://localhost/php/ajax_server_pr3.php?size=1", true);
    xmlHttp.onreadystatechange = handleResponse;
    xmlHttp.send(null);
});

function createXmlHttpRequest() {
    var xmlHttp;

    try {
        xmlHttp = new XMLHttpRequest();
    } catch (e) {
        xmlHttp = false;
    }
    if (!xmlHttp)
        alert("eroare");
    else
        return xmlHttp;
}

function display() {
    if (xmlHttp.readyState == 0 || xmlHttp.readyState == 4) {
        if ((marca != document.getElementById("marca").value || model != document.getElementById("model").value) && alertat == false) {
            alert("campurile sunt pe cale sa se schimbe!");
            alertat = true;
        } else {
            alertat = false;
            var sel = document.getElementById("iduri");
            var text = sel.options[sel.selectedIndex].text;
            id = encodeURIComponent(text);
            xmlHttp.open("GET", "http://localhost/php/ajax_server_pr3.php?id=" + id, true);
            xmlHttp.onreadystatechange = handleResponse;
            xmlHttp.send(null);
        }
    } else {
        setTimeout('display()', 1000);
    }
}

function handleResponse() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            var xmlResponse = xmlHttp.responseXML;
            var xmlDocumentElement = xmlResponse.documentElement;
            var message = xmlDocumentElement.getElementsByTagName("metoda")[0];
            if (message.childNodes[0].nodeValue == "id") {
                var raspuns = xmlDocumentElement.getElementsByTagName("rezultat")[0].childNodes[0].nodeValue;
                var elems = raspuns.split("#");
                document.getElementById("marca").value = elems[0];
                document.getElementById("model").value = elems[1];
                marca = elems[0];
                model = elems[1];
            } else if (message.childNodes[0].nodeValue == "size") {
                var raspuns = xmlDocumentElement.getElementsByTagName("rezultat")[0].childNodes[0].nodeValue;
                var select = document.getElementById("iduri");
                while (select.lastElementChild) {
                    select.removeChild(select.lastElementChild);
                }
                for (i = 1; i <= raspuns; i++) {
                    var option = document.createElement("option");
                    option.innerHTML = i;
                    select.appendChild(option);
                }
            }
        } else {
            alert('eroare!');
        }
    }
}

function trimite() {
    if (xmlHttp.readyState == 0 || xmlHttp.readyState == 4) {
        var mar = encodeURIComponent(document.getElementById("marca").value);
        var mod = encodeURIComponent(document.getElementById("model").value);
        if (mar == "" || mod == "") {
            alert('campurile sunt obigatorii!')
            return;
        }
        xmlHttp.open("GET", "http://localhost/php/ajax_server_pr3.php?marca=" + mar + "&model=" + mod, true);
        xmlHttp.onreadystatechange = handleResponse;
        xmlHttp.send(null);
        document.getElementById("marca").value = "";
        document.getElementById("model").value = "";
    } else {
        setTimeout('trimite()', 1000);
    }
}