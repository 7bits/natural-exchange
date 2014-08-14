$(document).ready(function() {
    "use strict";
    $(".js-registration").fancybox({
        "width" : 600,
        "height" : 460,
        "margin" :0,
        "padding" : 0,
        'modal' :  true,
        'overlayOpacity' : 0.13 ,
        'overlayColor' : '#000000',
        'scrolling' : 'no'
    });
    $("#registration-form").keypress(function(e){
        if(e.keyCode===13) {
            e.preventDefault();
        }
    });
    $.fancybox.resize();
    $("#registration-form").submit(function() { return false; });
    $("#reg-reject").click( function(){
        $.fancybox.close();
    });
});
