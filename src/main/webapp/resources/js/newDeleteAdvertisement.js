$(document).ready(function() {
    $(".js-delete-advertisement").click(function(e) {
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
                $.gritter.add({
                    title:"Предложение успешно удалено.",
                    image:"/resources/images/newdesign/logo.png"
                });
                document.location.reload();
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
