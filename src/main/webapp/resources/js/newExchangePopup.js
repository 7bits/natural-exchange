$(document).ready(function() {
    "use strict";

    $(".js-who-owner").click(function() {
        var exchButton = $(this);
        var ownerAdvId = exchButton.data('adv-id');
        var hiddenId = $('.hiddenOwnerAdvId');
        hiddenId.val(ownerAdvId);
    });

    $(".chosen-advert").click(function() {
        var exchButton = $(this);
        var offerAdvId = exchButton.data('offeradvid');
        var hiddenId = $('.hiddenOfferAdvId');
        hiddenId.val(offerAdvId);
    });


    $(".js-newExchange").fancybox({
        "width" : 600,
        "height" : 460,
        "margin" :0,
        "padding" : 0,
        'modal' :  true,
        'overlayOpacity' : 0.13 ,
        'overlayColor' : '#000000',
        'scrolling' : 'no'
    });
    $("#new-exchange-popup").keypress(function(e){
        if(e.keyCode===13) {
            e.preventDefault();
        }
    });
    $.fancybox.resize();
    $("#new-exchange-popup").submit(function() { return false; });
    $("#exchange-reject").click( function(){
        $.fancybox.close();
    });
    $('.js-exchange-complete').click(function(e) {
        e.preventDefault();
        var errorString = $('.reg-error');
        var acceptString = $('.reg-accepting');
        var ownerAdvId = $(".hiddenOwnerAdvId").val();
        var offerAdvId = $(".hiddenOfferAdvId").val();
        var exchangePropose = $(".offer-message").val();
        var redirectUrl = $('.js-exchange-form').data("url");
        var mainUrl = $('.js-exchange-form').data("mainurl");
        var dataJson = {
            idExchangeOwnerAdvertisement: ownerAdvId,
            idExchangeOfferAdvertisement: offerAdvId,
            exchangePropose: exchangePropose,
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
                    acceptString.text("");
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
