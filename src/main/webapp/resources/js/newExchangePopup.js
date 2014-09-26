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

    $(".offer-message").change(function() {
        var offerMsg = $(this);
        var hiddenMsg = $('.js-exchange-propose');
        hiddenMsg.val(offerMsg.val());
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
        var ownerAdvId = $(".hiddenOwnerAdvId").val();
        var offerAdvId = $(".hiddenOfferAdvId").val();
        var exchangePropose = $(".js-exchange-propose").val();
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
                    $.gritter.add({
                        title:"Вы совершили обмен!",
                        text:"Пожалуйста, дождитесь ответа от владельца вещи о возможном обмене. Ответ придет на ваш e-mail.",
                        image:"/resources/images/newdesign/logo.png"
                    });
                } else {
                    var errorVariant = data.errors;
                    if (errorVariant.wrong) {
                        $.gritter.add({
                            title:errorVariant.wrong,
                            image:"/resources/images/newdesign/logo.png"
                        });
                    }
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                if(jqXHR.status==404) {
//                    alert(errorThrown);
                }
            }
        })
    })
});
