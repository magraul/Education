function creeazaCerere() {
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

var xmlHttp = creeazaCerere();

$(document).ready(function() {
    xmlHttp.open("GET", "http://localhost/php/ajax_server_pr1.php?oras1=" + 'getAll', true);
    xmlHttp.onreadystatechange = handleResponseLoad;
    xmlHttp.send(null);

    $("#select1").click(function() {
        if (xmlHttp.readyState == 0 || xmlHttp.readyState == 4) {

            var text = $("#select1 option:selected").text();
            oras = encodeURIComponent(text);
            xmlHttp.open("GET", "http://localhost/php/ajax_server_pr1.php?oras1=" + oras, true);
            xmlHttp.onreadystatechange = handleResponse;
            xmlHttp.send(null);
        } else {
            setTimeout('calculeazaRute()', 1000);
        }
    });
});





function handleResponseLoad() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            console.log('succ')
            xmlResponse = xmlHttp.responseXML;
            console.log(xmlResponse);
            xmlDocumentElement = xmlResponse.documentElement;

            var raspuns = xmlDocumentElement.firstChild.data;
            console.log('mesaj:')
            console.log(raspuns);
            var elems = raspuns.split(",");


            elems.forEach(x => { if (x !== '') $("#select1").append('<option value="' + x + '">' + x + '</option>') })
        } else {
            alert('eroare!');
        }
    }

}


function handleResponse() {
    if (xmlHttp.readyState == 4) {
        if (xmlHttp.status == 200) {
            console.log('succ')
            xmlResponse = xmlHttp.responseXML;
            console.log(xmlResponse);
            xmlDocumentElement = xmlResponse.documentElement;

            var raspuns = xmlDocumentElement.firstChild.data;
            console.log('mesaj:')
            console.log(raspuns);
            var elems = raspuns.split(",");


            $('#select2')
                .find('option')
                .remove()
                .end();


            elems.forEach(x => { if (x !== '') $("#select2").append('<option value="' + x + '">' + x + '</option>') })

        } else {
            alert('eroare!');
        }
    }
}