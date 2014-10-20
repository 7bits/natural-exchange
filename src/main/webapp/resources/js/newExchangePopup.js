$(document).ready(function() {
    "use strict";

    $(".js-who-owner").click(function() {
        var exchButton = $(this);
        var ownerAdvId = exchButton.data('adv-id');
        var hiddenId = $('.hiddenOwnerAdvId');
        hiddenId.val(ownerAdvId);
        var exchangeAdvName = $('.js-exchange-adv');
        var advName = $(this).parent().find('.adv-name');
        exchangeAdvName.text((advName).text());
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

    $(".js-choose-advert").on('click', function() {
        var currentClickedBox = $(this);
        var currentAdvertisement = currentClickedBox.siblings('.js-chosen-advert');
        currentAdvertisement.prop( "checked", true );
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
        var exchangeAdvError = $('.js-advert-error');
        exchangeAdvError.text("");
    });
    $('.js-exchange-complete').click(function(e) {
        e.preventDefault();
        var ownerAdvId = $(".hiddenOwnerAdvId").val();
        var offerAdvId = $(".hiddenOfferAdvId").val();
        var exchangePropose = $(".js-exchange-propose").val();
        var mainUrl = $('.js-exchange-form').data("mainurl");
        var exchangeAdvError = $('.js-advert-error');
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
                    $.gritter.add({
                        title:"Вы совершили обмен!",
                        text:"Пожалуйста, дождитесь ответа от владельца вещи о возможном обмене. Ответ придет на ваш e-mail.",
                        image:"/resources/images/newdesign/logo.png",
                        sticky: true
                    });
                    $.fancybox.close();
                } else {
                    var errorVariant = data.errors;
                    if (errorVariant.idExchangeOfferAdvertisement) {
                        exchangeAdvError.text(errorVariant.idExchangeOfferAdvertisement);
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
