$(document).ready(function() {
    "use strict";
    document.getElementById("exchangeOk").style.display="block";
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
    $.fancybox.resize();
    $("#ok").click( function(){
        $.fancybox.close();
    });
});
