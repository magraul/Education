var tabel = document.getElementById("tabelNumere");

var last_click;
let variante;
let size = 16;

var numberOfClicks = 0;

last_click = -1
gasite = []

var apasate = [];

var dict = {}

function initializare() {


    variante = [];

    for (let i = 0; i < 4; i++) {
        let table_tr = document.createElement("tr");
        tabel.appendChild(table_tr);
        for (let j = 0; j < 4; j++) {
            let para = document.createElement("p");
            let table_td = document.createElement("td");
            table_td.appendChild(para);
            table_tr.appendChild(table_td);
        }
    }

    for (let j = 0; j < 2; j++) {
        for (let i = 0; i < size / 2; i++) {
            variante.push(i);
        }
    }

    transform(function(elem) {
        var poz = Math.random() * size;
        poz = Math.floor(poz);

        size -= 1;
        elem.children[0].innerHTML = variante[poz];
        variante.splice(poz, 1);
    });
}




function transform(func) {
    //variabila auxiliara pentru a nu strica tabelul initial
    let principal = tabel;
    let elem = principal.children;
    for (let i = 0; i < elem.length; i++) {
        let copil = elem[i].children;
        for (let j = 0; j < copil.length; j++) {
            func(copil[j]);
        }
    }
}




function verifica(last_click, elem) {
    let ok = true;

    if (last_click.children[0].innerHTML.localeCompare(elem.children[0].innerHTML) !== 0) {
        ok = false;
    }
    return ok;
}



function run() {
    transform(function(elem) {
        elem.addEventListener('click', handler, true);

        function handler() {

            console.log('click')
            if (last_click !== elem) {
                numberOfClicks++;
                if (numberOfClicks > 2)
                    return;
                elem.children[0].style.display = "block";
                if (last_click === -1) {
                    last_click = elem;
                } else {
                    if (verifica(last_click, elem)) {
                        // a fost nimerita o combinatie
                        console.log('a nimerit')

                        var aux = last_click;

                        last_click.parentNode.replaceChild(aux, last_click);

                        let prima = elem;

                        elem.parentNode.replaceChild(prima, elem);



                        gasite.push(last_click.textContent);
                        console.log('gasite:');
                        console.log(gasite);
                        last_click = -1;
                        numberOfClicks = 0;

                    } else {
                        // nu a fost nimerita
                        console.log(' nu a nimerit')

                        setTimeout(function() {

                            console.log(elem.textContent)

                            var gasit = false;
                            for (i = 0; i < gasite.length; i++) {
                                if (gasite[i] === elem.textContent.toString())
                                    gasit = true;
                            }

                            console.log(gasit)
                            if (!gasit) {
                                elem.children[0].style.display = "none";
                            }



                            console.log(gasite)

                            gasit = false;
                            for (i = 0; i < gasite.length; i++) {
                                if (gasite[i] === last_click.textContent.toString())
                                    gasit = true;
                            }

                            console.log(gasit)
                            if (!gasit) {
                                last_click.children[0].style.display = "none";
                            }

                            last_click = -1;
                            numberOfClicks = 0;

                        }, 1500);

                    }
                }
            }
        }
    });
}





initializare();

run();