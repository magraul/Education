var lista = $("#lista");
var pozaCurenta = lista.children()[0];


function nextFunc() {
    pozaCurenta.style.display = "none";
    pozaCurenta = pozaCurenta.nextElementSibling;
    if (!pozaCurenta) {
        pozaCurenta = lista.children()[0];
    }
    pozaCurenta.style.display = "block";
}

function doNext() {
    nextFunc();
    setTimeout(function() {
        doNext();
    }, 3000);
}



$(document).ready(function() {
    $("#butonPrev").click(function() {
        pozaCurenta.style.display = "none";
        pozaCurenta = pozaCurenta.previousElementSibling;
        if (!pozaCurenta) {
            pozaCurenta = lista.children()[lista.children().length - 1];
        }
        pozaCurenta.style.display = "block";
    })

    $("#butonNext").click(function() {
        nextFunc();
    })

    $("#lista").children()[0].css({
        "display": 'block'
    });
});



doNext();