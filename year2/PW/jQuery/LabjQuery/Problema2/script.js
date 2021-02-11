function contineNumar(str) {
    return /\d/.test(str);
}


$(document).ready(function() {
    $("#formular").submit(function() {
        errors = '';
        console.log('hi')

        //validare nume
        if ($('#nume').val().length === 0 || contineNumar($('#nume').val())) {
            errors += 'Numele este invalid! ';
            $("#nume").css('border', 'solid 2px red');
        } else {
            $("#nume").css("border", "solid 2px black");
        }

        //validare data
        let date = new Date($("#data").val());
        var year = date.getFullYear();

        if (year > 2010 || year < 1970 || date == "Invalid Date") {
            errors += 'Data este invalida! Trebuie sa fie cuprinsa intre 1970 si 2010! ';
            $("#data").css("border", "solid 2px red")
        } else {
            $("#data").css("border", "solid 2px black")
        }

        //validare varsta
        var d = new Date();
        if (parseInt($("#varsta").val()) < 18 || parseInt(d.getFullYear()) - parseInt(date.getFullYear()) !== parseInt(varsta.value)) {
            $("#varsta").css("border", "solid 2px red");
            errors += 'Varsta este invalida! Trebuie minim 18 ani si sa corespunda anului nasterii! ';
        } else {
            $("#varsta").css("border", "solid 2px black");
        }


        //mailul este valid daca contine un @
        var poz = $("#email").val().indexOf("@");

        if (poz === -1 || poz === $("#email").val().length - 1 || poz === 0) {
            $("#email").css('border', 'solid 2px red');
            errors += 'emailul este invalid! ';
        } else {
            $("#email").css('border', 'solid 2px black');
        }

        if (errors.length > 0) {
            alert(errors);
            return false;
        }

        alert("Datele sunt corecte!")
        return true;
    })

})