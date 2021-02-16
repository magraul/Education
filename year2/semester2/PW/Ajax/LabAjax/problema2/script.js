var xmlHttp = createXmlHttpRequest();
var numarPagina = 1;

document.addEventListener("DOMContentLoaded", function(event) {
    document.getElementById("inapoi").disabled = true;
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

function previous() {
    if (xmlHttp.readyState == 0 || xmlHttp.readyState == 4) {
        if (numarPagina > 1) {
            pagina = encodeURIComponent(numarPagina - 1);
            numarPagina -= 1;
            xmlHttp.open("GET", "http://localhost/php/ajax_server_prob2.php?pagina=" + pagina, true);
            xmlHttp.onreadystatechange = handleResponse;
            xmlHttp.send(null);
            if (numarPagina == 1)
                document.getElementById("inapoi").disabled = true;
            if (document.getElementById("inainte").disabled == true)
                document.getElementById("inainte").disabled = false;
        }
    } else {
        setTimeout('previous()', 1000);
    }
}

function next() {
    if (xmlHttp.readyState == 0 || xmlHttp.readyState == 4) {
        pagina = encodeURIComponent(numarPagina);
        numarPagina += 1;
        document.getElementById("inapoi").disabled = false;
        xmlHttp.open("GET", "http://localhost/php/ajax_server_prob2.php?pagina=" + pagina, true);
        xmlHttp.onreadystatechange = handleResponse;
        xmlHttp.send(null);
    } else {
        setTimeout('next()', 1000);
    }
}

function handleResponse() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            xmlResponse = xmlHttp.responseXML;
            xmlDocumentElement = xmlResponse.documentElement;
            message = xmlDocumentElement.firstChild.data;
            if (typeof message === 'undefined') {
                document.getElementById("inainte").disabled = true;
                return;
            }
            var elems = message.split("#");
            const myNode = document.getElementById("utilizatori");
            while (myNode.lastElementChild) {
                myNode.removeChild(myNode.lastElementChild);
            }
            elems.forEach(x => {
                var div = document.createElement("div");
                div.innerHTML = x;
                document.getElementById("utilizatori").appendChild(div);
            });
        } else {
            alert('eroare!');
        }
    }
}