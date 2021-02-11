var lista = document.getElementById("lista");
var pozaCurenta = lista.children[0];

pozaCurenta.style.display = "block";

var butonNext = document.getElementById("butonNext");
var butonPrev = document.getElementById("butonPrev");

butonPrev.addEventListener('click', function() {

    pozaCurenta.style.display = "none";
    pozaCurenta = pozaCurenta.previousElementSibling;
    if (!pozaCurenta) {
        pozaCurenta = lista.children[lista.children.length - 1];
    }
    pozaCurenta.style.display = "block";

});



butonNext.addEventListener('click', function() {
    nextFunc();
});


function nextFunc() {
    pozaCurenta.style.display = "none";
    pozaCurenta = pozaCurenta.nextElementSibling;
    if (!pozaCurenta) {
        pozaCurenta = lista.children[0];
    }
    pozaCurenta.style.display = "block";
}

function doNext() {
    nextFunc();
    setTimeout(function() {
        doNext();
    }, 3000);
}

doNext();