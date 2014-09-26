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
                } else {
                    var errorVariant = data.errors;
                    if(errorVariant.notExist) {
                        $.gritter.add({
                            title:data.errors.notExist,
                            image:"/resources/images/newdesign/logo.png"
                        });
                    } else if (errorVariant.wrong) {
                        $.gritter.add({
                            title:data.errors.wrong,
                            image:"/resources/images/newdesign/logo.png"
                        });
                    } else if (errorVariant.wrongPassword) {
                        $.gritter.add({
                            title:data.errors.wrongPassword,
                            image:"/resources/images/newdesign/logo.png"
                        });
                    } else if (errorVariant.notRegistrationComplete) {
                        $.gritter.add({
                            title: data.errors.notRegistrationComplete,
                            image:"/resources/images/newdesign/logo.png"
                        });
                    } else if (errorVariant.userIsBanned) {
                        $.gritter.add({
                            title:data.errors.userIsBanned,
                            image:"/resources/images/newdesign/logo.png"
                        });
                    }
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                $.gritter.add({
                    title:"Активируйте свой аккаунт прежде чем войти.",
                    image:"/resources/images/newdesign/logo.png"
                });
                if(jqXHR.status==404) {
                    $.gritter.add({
                        title:errorThrown,
                        image:"/resources/images/newdesign/logo.png"
                    });
                }
            }
        })
    })
});
