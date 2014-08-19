$(document).ready(function() {
    "use strict";
    $(".js-entry").fancybox({
        "width" : 600,
        "height" : 460,
        "margin" :0,
        "padding" : 0,
        'modal' :  true,
        'overlayOpacity' : 0.13 ,
        'overlayColor' : '#000000',
        'scrolling' : 'no'
    });
    $("#entry-form").keypress(function(e){
        if(e.keyCode===13) {
            e.preventDefault();
        }
    });
    $.fancybox.resize();
    $("#entry-form").submit(function() { return false; });
    $("#reject").click( function(){
        $.fancybox.close();
    });
    $('.js-entry-complete').click(function(e) {
        e.preventDefault();
        var errorString = $('.reg-error');
        var acceptString = $('.reg-accepting');
        var email = $("#reg-email").val();
        var password = $("#reg-pass").val();
        var dataJson = {
            email: email,
            password: password,
            errors: null
        };
        $.ajax({
            type: 'POST',
            url: '/n-exchange/new/user/entry.html',
            data: dataJson,
            success: function(data, textStatus, jqXHR) {
                if (data.success == true) {
                    errorString.text("");
                    acceptString.text("");
                } else {
                    var errorVariant = data.errors;
                    acceptString.text("");
                    if(errorVariant.exist) {
                        errorString.text(data.errors.exist);
                    } else if (errorVariant.wrong) {
                        errorString.text(data.errors.wrong);
                    }
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                if(jqXHR.status==404) {
                    alert(errorThrown);
                }
            }
        })
    })
});
