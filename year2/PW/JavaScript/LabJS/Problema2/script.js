function contineNumar(str) {
    return /\d/.test(str);
}

function valideaza() {
    let nume = document.getElementById("nume");
    let data = document.getElementById("data");
    let varsta = document.getElementById("varsta");
    let email = document.getElementById("email");


    errors = '';

    if (nume.value === "" || contineNumar(nume.value)) {
        nume.style.border = "1px solid red";
        errors += 'Numele este invalid! ';
    } else {
        nume.style.border = "1px solid black";
    }

    let date = new Date(data.value);
    var year = date.getFullYear();
    if (year > 2010 || year < 1970 || date == "Invalid Date") {
        data.style.border = "1px solid red";
        errors += 'Data este invalida! Trebuie sa fie cuprinsa intre 1970 si 2010! ';
    } else {
        data.style.border = "1px solid black";
    }

    var d = new Date();
    console.log(parseInt(date.getFullYear()))

    if (varsta.value < 18 || parseInt(d.getFullYear()) - parseInt(date.getFullYear()) !== parseInt(varsta.value)) {
        varsta.style.border = "1px solid red";
        errors += 'Varsta este invalida! Trebuie minim 18 ani si sa corespunda anului nasterii! ';
    } else {
        varsta.style.border = "1px solid black";
    }



    //mailul este valid daca contine un @
    var poz = email.value.indexOf("@");

    if (poz === -1 || poz === email.value.length - 1 || poz === 0) {
        email.style.border = "1px solid red";
        errors += 'emailul este invalid! ';
    } else {
        email.style.border = "1px solid black";
    }

    if (errors.length > 0) {
        alert(errors);
        return false;
    }

    alert("Datele sunt corecte!")
    return true;
}