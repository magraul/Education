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
    $("td").click(function() {
        if (last_click != $(this)) {
            numberOfClicks++;
            if (numberOfClicks > 2)
                return;
            $(this).children().css({
                "visibility": 'visible'
            });
        }

        if (last_click === -1) {
            last_click = $(this);
        } else {
            $(this).children().css({
                "visibility": 'visible'
            });
            if (verifica(last_click, $(this))) {

                gasite.push(last_click.text());
                last_click = -1;
                numberOfClicks = 0;

            } else {
                setTimeout(() => {

                    var gasit = false;
                    for (i = 0; i < gasite.length; i++) {
                        if (gasite[i] === $(this).text().toString())
                            gasit = true;

                    }

                    console.log(gasit)
                    if (!gasit) {
                        $(this).children().css({
                            "visibility": 'hidden'
                        });
                    }



                    console.log(gasite)

                    gasit = false;
                    for (i = 0; i < gasite.length; i++) {
                        if (gasite[i] === last_click.text().toString())
                            gasit = true;
                    }

                    console.log(gasit)
                    if (!gasit) {

                        last_click.children().css({
                            "visibility": 'hidden'
                        });

                    }





                    last_click = -1;
                    numberOfClicks = 0;

                }, 1000);



            }

        }


    });
});