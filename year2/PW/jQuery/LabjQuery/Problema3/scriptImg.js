function verifica(last_click, elem) {
    let ok = true;

    if (last_click.children().attr('class') != elem.children().attr('class')) {
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
                console.log('hi')
                gasite.push(last_click.children().attr('class'));
                last_click = -1;
                numberOfClicks = 0;

            } else {
                setTimeout(() => {

                    var gasit = false;
                    for (i = 0; i < gasite.length; i++) {
                        if (gasite[i] === $(this).children().attr('class'))
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
                        if (gasite[i] === last_click.children().attr('class'))
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