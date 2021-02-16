var select1 = document.getElementById("select1");
var select2 = document.getElementById("select2");


function mutare1() {

    var optiune = select1.options[select1.selectedIndex];
    optiune.remove();
    select2.appendChild(optiune);
}

function mutare2() {

    var optiune = select2.options[select2.selectedIndex];
    optiune.remove();
    select1.appendChild(optiune);

}