$(document).ready(function() {
    var node = document.getElementById( "perechen" ),
    var need = 16;
    if ( node.innerHTML.length > need ) {
        node.innerHTML = node.innerHTML.slice( 0, need ) + "…";
    }
})();
