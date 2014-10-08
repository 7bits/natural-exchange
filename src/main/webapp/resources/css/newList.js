$(document).ready(function () {
    "use strict";
    var deletedAdvertisement = $('.js-deleted-advertisement');
    if(typeof deletedAdvertisement != 'undefined') {
        $.gritter.add({
            title: "Ваше предложение удалено!",
            image: "/resources/images/newdesign/logo.png"
        });
    }
});
