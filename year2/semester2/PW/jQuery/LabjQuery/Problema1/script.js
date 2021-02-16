$(document).ready(function() {
    $("#select1").dblclick(function() {
        $("#select2").append($('option:selected', this));
        $('option:selected', this).remove();
    });

    $("#select2").dblclick(function() {
        $("#select1").append($('option:selected', this));
        $('option:selected', this).remove();
    });
});