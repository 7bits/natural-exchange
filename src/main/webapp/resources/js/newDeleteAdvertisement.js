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
                alert("Предложение успешно удалено");
                document.location.reload();
            },
            error: function() {
                alert("Предложение не удалено, пожалуйста, повторите попытку позже");
            }
        })
    });
});
