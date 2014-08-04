$(document).ready(function() {
    "use strict";
    document.getElementById("anonymExchangePopup").style.display="block";
    $(".js-exchange-anonym").fancybox({
        "width" : 600,
        "height" : 460,
        "margin" :0,
        "padding" : 0,
        'modal' :  true,
        'overlayOpacity' : 0.13 ,
        'overlayColor' : '#000000',
        'scrolling' : 'no'
    });
    $("#anonymPopup").keypress(function(e){
        if(e.keyCode===13) {
            e.preventDefault();
        }
    });
    $.fancybox.resize();
    $("#anonymPopup").submit(function() { return false; });
    $("#closePopup").click( function(){
        $.fancybox.close();
    });
});
/**
 * Created by evgeniy on 8/4/14.
 */
