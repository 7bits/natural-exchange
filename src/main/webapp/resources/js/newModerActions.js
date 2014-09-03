$(document).ready(function() {
    $(".js-moder-action").click(function() {
        var id = $(this).data('adv-id');
        var destUrl = $(this).data('dest-url');
        var sendingData = {
            advertisementId: id
        };
        $.ajax({
            url: destUrl,
            type: 'POST',
            data: sendingData,
            success: function(result) {
                document.location.href = destUrl;
            },
            error: function() {
                alert("Не сработало, я не знаю в чем проблема");
            }
        })
    });
});
