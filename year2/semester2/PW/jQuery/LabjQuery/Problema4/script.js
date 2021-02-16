var first_click1 = true;
var first_click2 = true;


$(document).ready(function() {
    $("#tabel1").each(function(_, val) {
        var body = $(val).children("tbody");
        body.children("tr").each(function(___, val2) {

            $(val2).children("th").each(function(_, __) {
                $(this).click(function() {
                    var lungime = $(this).parent()[0].cells.length;
                    for (let i = 1; i < lungime - 1; i++)
                        for (let j = i + 1; j < lungime; j++) {
                            let content1 = $(this).parent().children("td:eq(" + (i - 1) + ")").html();
                            let content2 = $(this).parent().children("td:eq(" + (j - 1) + ")").html();

                            if (first_click2 == true) {

                                if (!(content1.localeCompare(content2) < 0)) {
                                    var linii = $(val).children().children();
                                    linii.each(function(_, __) {
                                        var child_1 = $(this).children().eq(i);
                                        var child_2 = $(this).children().eq(j);
                                        var aux = child_1.html();
                                        child_1.html(child_2.html());
                                        child_2.html(aux);
                                    });
                                }

                            } else if (first_click2 == false) {
                                if (!(content1.localeCompare(content2) > 0)) {
                                    var linii = $(val).children().children();
                                    linii.each(function(_, val) {
                                        var child_1 = $(this).children().eq(i);
                                        var child_2 = $(this).children().eq(j);
                                        var aux = child_1.html();
                                        child_1.html(child_2.html());
                                        child_2.html(aux);
                                    });
                                }


                            }
                        }

                    if (first_click2 == true)
                        first_click2 = false;
                    else first_click2 = true;

                });
            });
        });
    });

    $("#tabel2").each(function(_, val) {
        var body = $(val).children("tbody");
        var childs = body.children(":first");
        childs.children("th").each(function(_, __) {
            $(this).click(function() {
                var tbody = $(this).parent().parent();
                var lungime = tbody.children("tr").length;
                var poz = $(this).index();
                for (let i = 1; i < lungime - 1; i++)
                    for (let j = i + 1; j < lungime; j++) {
                        let content1 = tbody.children("tr:eq(" + i + ")").children("td:eq(" + poz + ")").html();
                        let content2 = tbody.children("tr:eq(" + j + ")").children("td:eq(" + poz + ")").html();
                        if (first_click1 == true) {

                            if (!(content1.localeCompare(content2) < 0))
                                tbody.children("tr:eq(" + i + ")").before(tbody.children("tr:eq(" + j + ")"));
                        } else if (first_click1 == false) {
                            if (!(content1.localeCompare(content2) > 0))
                                tbody.children("tr:eq(" + i + ")").before(tbody.children("tr:eq(" + j + ")"));
                        }

                    }
                if (first_click1 == true)
                    first_click1 = false;
                else first_click1 = true;
            });
        });
    });
});