$(document).ready(function() {
    $(".js-moder-action").click(function(e) {
        e.preventDefault();
        var id = $(this).data('adv-id');
        var destUrl = $(this).data('dest-url');
        var sendingData = {
            id: id
        };
        $.ajax({
            url: destUrl,
            type: 'GET',
            data: sendingData,
            success: function(result) {
                document.location.reload();// = destUrl;
            },
            error: function() {
                alert("Не сработало, я не знаю в чем проблема");
            }
        })
    });
});

$(document).ready(function () {
    "use strict";
    if ($(".container").find(".js-deleted-advertisement").length) {
        $.gritter.add({
            title: "Ваше предложение удалено!",
            image: "/resources/images/newdesign/logo.png"
        });
    }
});
