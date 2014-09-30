$(document).ready(function() {
    "use strict";

    $(".js-who-owner-view").click(function () {
        var exchButton = $(this);
        var ownerAdvId = exchButton.data('adv-id');
        var hiddenId = $('.hiddenOwnerAdvId');
        hiddenId.val(ownerAdvId);
        var exchangeAdvName = $('.js-exchange-adv');
        var advName = $(this).parent().siblings('.adv-name');
        exchangeAdvName.text((advName).text());
    });
});
