$(document).ready(function() {
    var need = 100;
    var texts = document.getElementsByName( "text" );
    for (var i = 0, length = texts.length; i < length; i++) {
        if ( texts[i].innerHTML.length > need ) {
            texts[i].innerHTML = node.innerHTML.slice( 0, need ) + "…";
        }
    }
});
