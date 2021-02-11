function getRandomInt(max) {
    return Math.floor(Math.random() * Math.floor(max));
}

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
    xmlHttp.open("GET", "http://localhost/php/ajax_server_pr6.php?procesor=initial&memorie=-1&placa=-1", true);
    xmlHttp.onreadystatechange = handleLoad;
    xmlHttp.send(null);

    $("#send").click(function() {

        var procesorr = $("#procesor option:selected").text();
        var memorie = $("#memorie option:selected").text()
        var placa = $("#placa option:selected").text()

        xmlHttp.open("GET", "http://localhost/php/ajax_server_pr6.php?procesor=" + procesorr + "&memorie=" + memorie + "&placa=" + placa, true);
        xmlHttp.onreadystatechange = handleResponse;
        xmlHttp.send(null);


    });
});



function handleLoad() {
    $('#produse')
        .find('option')
        .remove()
        .end();

    xmlResponse = xmlHttp.responseXML;
    xmlDocumentElement = xmlResponse.documentElement;
    var raspuns = xmlDocumentElement.firstChild.data;

    var categorii = raspuns.split(";");
    var elems = categorii[0].split(",");
    elems.forEach(x => { if (x !== '') $("#produse").append('<option value="' + x + '">' + x + '</option>') })

    var elems = categorii[1].split(",");
    elems.forEach(x => { if (x !== '') $("#procesor").append('<option value="' + x + '">' + x + '</option>') })

    var elems = categorii[2].split(",");
    elems.forEach(x => { if (x !== '') $("#memorie").append('<option value="' + x + '">' + x + '</option>') })

    var elems = categorii[3].split(",");
    elems.forEach(x => { if (x !== '') $("#placa").append('<option value="' + x + '">' + x + '</option>') })

}

function handleResponse() {
    console.log("send")
    xmlResponse = xmlHttp.responseXML;
    xmlDocumentElement = xmlResponse.documentElement;
    var raspuns = xmlDocumentElement.firstChild.data;
    console.log(raspuns);
}