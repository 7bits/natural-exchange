$(document).ready(function() {
    $(".js-delete-advertisement").click(function(e) {
        e.preventDefault();
        var id = $(this).data('adv-id');
        var destUrl = $(this).data('dest-url');
        var advertisement = $(this).closest('.advertisement');
        var sendingData = {
            id: id
        };
        $.ajax({
            url: destUrl,
            type: 'GET',
            data: sendingData,
            success: function(result) {
                $.gritter.add({
                    title:"Ваше предложение удалено.",
                    image:"/resources/images/newdesign/logo.png"
                });
                advertisement.hide();
            },
            error: function() {
                $.gritter.add({
                    title:"Предложение не удалено. Пожалуйста, повторите попытку позже.",
                    image:"/resources/images/newdesign/logo.png"
                });
            }
        })
    });
});
