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
        var emailError = $('.js-email-error');
        var passError = $('.js-pass-error');
        emailError.text("");
        passError.text("");
        $.fancybox.close();
    });
    $(".js-entry").click( function(){
        var emailError = $('.js-email-error');
        var passError = $('.js-pass-error');
        emailError.text("");
        passError.text("");
        $.fancybox.close();
    });
    $('.js-registration-complete').click(function(e) {
        e.preventDefault();
        var emailError = $('.js-email-error');
        var passError = $('.js-pass-error');
        var firstNameError = $('.js-firstname-error');
        var lastNameError = $('.js-lastname-error');
        var email = $("#reg-email").val();
        var firstName = $("#reg-first-name").val();
        var lastName = $("#reg-last-name").val();
        var password = $("#reg-pass").val();
        var sendingUrl = $(".js-registration-form").data("url");
        emailError.text("");
        passError.text("");
        var dataJson = {
            email: email,
            firstName: firstName,
            lastName: lastName,
            password: password,
            errors: null
        };
        $.ajax({
            type: 'POST',
            url: sendingUrl,
            data: dataJson,
            success: function(data, textStatus, jqXHR) {
                if (data.success == true) {
                    $.gritter.add({
                        title:"Вы зарегистрированы!",
                        text:"На ваш email выслано подтверждение вашего аккаунта.",
                        image:"/resources/images/newdesign/logo.png"
                    });
                } else {
                    var errorVariant = data.errors;
                    if(errorVariant.exist) {
                        $.gritter.add({
                            title:data.errors.exist,
                            image:"/resources/images/newdesign/logo.png"
                        });
                    } else if (errorVariant.wrong) {
                        if (errorVariant.wrong.email) {
                            emailError.text(errorVariant.wrong.email);
                        }
                        if (errorVariant.wrong.password) {
                            passError.text(errorVariant.wrong.password);
                        }
                        if (errorVariant.wrong.firstName) {
                            firstNameError.text(errorVariant.wrong.firstName);
                        }
                        if (errorVariant.wrong.lastName) {
                            lastNameError.text(errorVariant.wrong.lastName);
                        }
                    }
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
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

$(document).bind('keydown', function() {
    if ((event.keyCode == 13) && ($(".js-registration-form").is(":visible"))) {
        var entry = $('.js-registration-complete');
        entry.click();
    }
});
