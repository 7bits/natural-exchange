$(document).ready(function () {
    "use strict";
    var deletedAdvertisement = $('.js-deleted-advertisement');
    if (deletedAdvertisement.val().length > 0) {
        $.gritter.add({
            title: "Ваше предложение удалено!",
            image: "/resources/images/newdesign/logo.png"
        });
    }
});
