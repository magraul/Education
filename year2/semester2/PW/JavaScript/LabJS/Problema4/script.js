var first_click1 = true;
var first_click2 = true;


function sorteazaCol(Idtabel, indexLinie) {
    console.log(indexLinie)

    if (indexLinie === 0) {
        if (first_click2 === true) {
            var tabel = document.getElementById(Idtabel);

            let lungime = tabel.rows[0].cells.length;

            for (let i = 1; i < lungime - 1; i++) {
                for (let j = i + 1; j < lungime; j++) {
                    var contentCell1 = tabel.rows[indexLinie].cells[i].innerText;
                    var contentCell2 = tabel.rows[indexLinie].cells[j].innerText;

                    if (!isNaN(contentCell1)) {
                        contentCell1 = Number(contentCell1);
                        contentCell2 = Number(contentCell2);
                    }
                    if (contentCell1 > contentCell2) {
                        //inversam coloanele
                        for (let ii = 0; ii < tabel.rows.length; ii++) {
                            var contentCell1 = tabel.rows[ii].cells[i].innerText;
                            var contentCell2 = tabel.rows[ii].cells[j].innerText;
                            tabel.rows[ii].cells[i].innerHTML = contentCell2;
                            tabel.rows[ii].cells[j].innerHTML = contentCell1;
                        }
                    }
                }
            }
            first_click2 = false;
        } else {
            first_click2 = true;
            var tabel = document.getElementById(Idtabel);

            let lungime = tabel.rows[0].cells.length;

            for (let i = 1; i < lungime - 1; i++) {
                for (let j = i + 1; j < lungime; j++) {
                    var contentCell1 = tabel.rows[indexLinie].cells[i].innerText;
                    var contentCell2 = tabel.rows[indexLinie].cells[j].innerText;

                    if (!isNaN(contentCell1)) {
                        contentCell1 = Number(contentCell1);
                        contentCell2 = Number(contentCell2);
                    }
                    if (contentCell1 < contentCell2) {
                        //inversam coloanele
                        for (let ii = 0; ii < tabel.rows.length; ii++) {
                            var contentCell1 = tabel.rows[ii].cells[i].innerText;
                            var contentCell2 = tabel.rows[ii].cells[j].innerText;
                            tabel.rows[ii].cells[i].innerHTML = contentCell2;
                            tabel.rows[ii].cells[j].innerHTML = contentCell1;
                        }
                    }
                }
            }
        }
    } else {
        var tabel = document.getElementById(Idtabel);

        let lungime = tabel.rows[0].cells.length;

        for (let i = 1; i < lungime - 1; i++) {
            for (let j = i + 1; j < lungime; j++) {
                var contentCell1 = tabel.rows[indexLinie].cells[i].innerText;
                var contentCell2 = tabel.rows[indexLinie].cells[j].innerText;

                if (!isNaN(contentCell1)) {
                    contentCell1 = Number(contentCell1);
                    contentCell2 = Number(contentCell2);
                }
                if (contentCell1 > contentCell2) {
                    //inversam coloanele
                    for (let ii = 0; ii < tabel.rows.length; ii++) {
                        var contentCell1 = tabel.rows[ii].cells[i].innerText;
                        var contentCell2 = tabel.rows[ii].cells[j].innerText;
                        tabel.rows[ii].cells[i].innerHTML = contentCell2;
                        tabel.rows[ii].cells[j].innerHTML = contentCell1;
                    }
                }
            }
        }
    }

}


function modificaTabelVertical() {

    var tabel = document.getElementById("tabel1");
    var linii = tabel.rows;

    for (let i = 0; i < linii.length; i++) {
        let celula = tabel.rows[i].cells[0];
        let cntLinie = celula.parentNode.rowIndex;

        linii[i].cells[0].addEventListener('click', function() {
            sorteazaCol("tabel1", cntLinie);
        });

    }

}





function sorteazaLinii(Idtabel, indexColoana) {
    console.log(indexColoana)
    if (indexColoana === 0) {
        if (first_click1 === true) {
            var tabel = document.getElementById(Idtabel);
            for (let i = 1; i < tabel.rows.length - 1; i++) {
                for (let j = i + 1; j < tabel.rows.length; j++) {
                    var contentCell1 = tabel.rows[i].cells[indexColoana].innerText;
                    var contentCell2 = tabel.rows[j].cells[indexColoana].innerText;
                    if (!isNaN(contentCell1)) {
                        contentCell1 = Number(contentCell1);
                        contentCell2 = Number(contentCell2);
                    }

                    if (contentCell1 > contentCell2) {
                        //inversam liniile
                        var contentCell = tabel.rows[i].innerHTML;
                        tabel.rows[i].innerHTML = tabel.rows[j].innerHTML;
                        tabel.rows[j].innerHTML = contentCell;
                    }
                }
            }

            first_click1 = false;
        } else {
            first_click1 = true;
            var tabel = document.getElementById(Idtabel);
            for (let i = 1; i < tabel.rows.length - 1; i++) {
                for (let j = i + 1; j < tabel.rows.length; j++) {
                    var contentCell1 = tabel.rows[i].cells[indexColoana].innerText;
                    var contentCell2 = tabel.rows[j].cells[indexColoana].innerText;
                    if (!isNaN(contentCell1)) {
                        contentCell1 = Number(contentCell1);
                        contentCell2 = Number(contentCell2);
                    }

                    if (contentCell1 < contentCell2) {
                        //inversam liniile
                        var contentCell = tabel.rows[i].innerHTML;
                        tabel.rows[i].innerHTML = tabel.rows[j].innerHTML;
                        tabel.rows[j].innerHTML = contentCell;
                    }
                }
            }

        }
    } else {
        var tabel = document.getElementById(Idtabel);
        for (let i = 1; i < tabel.rows.length - 1; i++) {
            for (let j = i + 1; j < tabel.rows.length; j++) {
                var contentCell1 = tabel.rows[i].cells[indexColoana].innerText;
                var contentCell2 = tabel.rows[j].cells[indexColoana].innerText;
                if (!isNaN(contentCell1)) {
                    contentCell1 = Number(contentCell1);
                    contentCell2 = Number(contentCell2);
                }

                if (contentCell1 > contentCell2) {
                    //inversam liniile
                    var contentCell = tabel.rows[i].innerHTML;
                    tabel.rows[i].innerHTML = tabel.rows[j].innerHTML;
                    tabel.rows[j].innerHTML = contentCell;
                }
            }
        }
    }


}


function modificaTabelOrizontal() {
    var tabel = document.getElementById("tabel2");
    var linii = tabel.rows;
    var lungime = linii[0].cells.length;

    for (let i = 0; i < lungime; i++) {
        linii[0].cells[i].addEventListener('click', function() {
            sorteazaLinii("tabel2", i);
        })
    }
}





modificaTabelVertical();
modificaTabelOrizontal();