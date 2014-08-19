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
    $('.js-registration-complete').click(function() {
        var email = $("#reg-email").val();
        var firstName = $("#reg-first-name").val();
        var lastName = $("#reg-last-name").val();
        var password = $("#reg-pass").val();
        var dataJson = {
            email: email,
            firstName: firstName,
            lastName: lastName,
            password: password
        };
        $.ajax({
            type: 'POST',
            url: '/n-exchange/new/user/registration.html',
            data: dataJson,
            success: function(data, textStatus, jqXHR) {
                if (data.success == "auth") {
                    errorString.text("");
                    emailString.val("Ваш e-mail добавлен.");
                } else {
                    var errorVariant = data.errors;
                    if(errorVariant.exist) {
                        emailString.val(data.errors.exist);
                        errorString.text("");
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
