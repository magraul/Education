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

var last_val = getRandomInt(2);

function getVal() {
    if (last_val === 1) {
        return 'X';
    }
    return 'O';
}

function verifica(last_click, elem) {
    let ok = true;

    if (last_click.children().text().localeCompare(elem.children().text()) !== 0) {
        ok = false;
    }
    return ok;
}

var last_click = -1;
var numberOfClicks = 0;
var gasite = [];

$(document).ready(function() {
    xmlHttp.open("GET", "http://localhost/php/ajax_server_pr2.php?index_patrat=initial", true);
    xmlHttp.send(null);
    $("td").click(function() {
        $(this).children().html(getVal());


        //auto

        //inregistram mutarea
        var className = $(this).children().attr('class');

        xmlHttp.open("GET", "http://localhost/php/ajax_server_pr2.php?index_patrat=" + className + "&valoarea=" + last_val, true);
        xmlHttp.onreadystatechange = handleResponse;
        xmlHttp.send(null);


    });
});



function handleResponse() {
    if (xmlHttp.readyState == 4) {

        if (xmlHttp.status == 200) {

            xmlResponse = xmlHttp.responseXML;

            xmlDocumentElement = xmlResponse.documentElement;

            var raspuns = xmlDocumentElement.firstChild.data;

            console.log('raspuns');
            console.log(raspuns);
            if (raspuns == 'win') {
                alert('BINE BA BAIATULE\n !!!!!!!!!');
                return;
            }

            if (raspuns.includes('winC')) {
                console.log('pc win ' + raspuns)
                var poz = parseInt(raspuns.split(";")[1]);
                if (xmlHttp.responseXML != null) {
                    if (last_val === 0)
                        ++last_val;
                    else --last_val;
                    $("table td").eq(poz - 1).children().html(getVal());
                    if (last_val === 0)
                        ++last_val;
                    else --last_val;
                }

                alert('AI PIERDUT BA BAIATULE\n !!!!!!!!!');
                return;

            }

            if (xmlHttp.responseXML != null) {
                if (last_val === 0)
                    ++last_val;
                else --last_val;
                $("table td").eq(raspuns - 1).children().html(getVal());
                if (last_val === 0)
                    ++last_val;
                else --last_val;
            }

        } else {
            alert('eroare!');
        }
    }
    console.log('aicea')
}