$(document).ready(function() {
    "use strict";
    $('.submit-email').click(function(e) {
        e.preventDefault();
        var subscribeError = $('.js-subscribe-error');
        var emailString = $('.for-email');
        var sendingUrl = $('.subscribe-form').data("url");
        var dataJson = {
            email: emailString.val(),
            errors: null
        };
        subscribeError.text("");
        $.ajax({
            type: 'POST',
            url: sendingUrl,
            data: dataJson,
            success: function(data, textStatus, jqXHR) {
                if (data.success == true) {
                    $.gritter.add({
                        title:"Вы подписались на наш проект!",
                        text:"На ваш email будут приходить новости, обновления и другие нововведения проекта.",
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
                        subscribeError.text(data.errors.wrong);
                    }
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                if(jqXHR.status==404) {
                    alert(errorThrown);
                }
            }
        })

    });
    $('.e-mail-submit').click(function(e) {
        e.preventDefault();
        var subscribeError = $('.js-subscribe-error');
        var emailString = $('.e-mail-field');
        var sendingUrl = $('.subscribe-form').data("url");
        var dataJson = {
            email: emailString.val(),
            errors: null
        };
        subscribeError.text("");
        $.ajax({
            type: 'POST',
            url: sendingUrl,
            data: dataJson,
            success: function(data, textStatus, jqXHR) {
                if (data.success == true) {
                    $.gritter.add({
                        title:"Вы подписались на наш проект!",
                        text:"На ваш email будут приходить новости, обновления и другие нововведения проекта.",
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
                        subscribeError.text(data.errors.wrong);
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
