$(document).ready(function() {
    "use strict";
    document.getElementById("exchangePopup").style.display="block";
    $(".js-exchange").fancybox({
        "width" : 600,
        "height" : 460,
        "margin" :0,
        "padding" : 0,
        'modal' :  true,
        'overlayOpacity' : 0.13 ,
        'overlayColor' : '#000000',
        'scrolling' : 'no'
    });
    $("#exchange").keypress(function(e){
        if(e.keyCode===13) {
            e.preventDefault();
        }
    });
    $.fancybox.resize();
    $("#exchange").submit(function() { return false; });
    $("#reject").click( function(){
        $.fancybox.close();
    });
});
