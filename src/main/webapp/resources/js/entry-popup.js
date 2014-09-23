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
    $("#entry-reject").click( function(){
        var errorString = $('.reg-error');
        var acceptString = $('.reg-accepting');
        errorString.text("");
        acceptString.text("");
        $.fancybox.close();
    });
    $('.js-registration').click( function(){
        var errorString = $('.reg-error');
        var acceptString = $('.reg-accepting');
        errorString.text("");
        acceptString.text("");
    });
    $('.js-entry-complete').click(function(e) {
        e.preventDefault();
        var errorString = $('.reg-error');
        var acceptString = $('.reg-accepting');
        var email = $("#entry-email").val();
        var password = $("#entry-pass").val();
        var redirectUrl = $('.js-entry-form').data("url");
        var mainUrl = $('.js-entry-form').data("mainurl");
        var dataJson = {
            email: email,
            password: password,
            errors: null
        };
        $.ajax({
            type: 'POST',
            url: mainUrl,
            data: dataJson,
            success: function(data, textStatus, jqXHR) {
                if (data.success == true) {
                    window.location.href = redirectUrl;
                    errorString.text("");
                    acceptString.show();
                    errorString.hide();
                } else {
                    var errorVariant = data.errors;
                    errorString.show();
                    acceptString.text("");
                    acceptString.hide();
                    if(errorVariant.notExist) {
                        errorString.text(data.errors.notExist);
                    } else if (errorVariant.wrong) {
                        errorString.text(data.errors.wrong);
                    } else if (errorVariant.wrongPassword) {
                        errorString.text(data.errors.wrongPassword);
                    } else if (errorVariant.notRegistrationComplete) {
                        errorString.text(data.errors.notRegistrationComplete);
                    }
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                errorString.text("Активируйте свой аккаунт прежде чем войти.");
                if(jqXHR.status==404) {
//                    alert(errorThrown);
                }
            }
        })
    })
});
