$(document).ready(function() {
    $(".js-delete-search").click(function(e) {
        e.preventDefault();
        var id = $(this).data('adv-id');
        var destUrl = $(this).data('dest-url');
        var search = $(this).parent();
        var sendingData = {
            id: id
        };
        $.ajax({
            url: destUrl,
            type: 'GET',
            data: sendingData,
            success: function(result) {
                $.gritter.add({
                    title:"Ваш поиск удален.",
                    image:"/resources/images/newdesign/logo.png"
                });
                search.hide();
            },
            error: function() {
                $.gritter.add({
                    title:"Поиск не удален. Пожалуйста, повторите попытку позже.",
                    image:"/resources/images/newdesign/logo.png"
                });
            }
        });
})});