$(document).ready(function() {
    $(".js-admin-action").click(function(e) {
        e.preventDefault();
        var id = $(this).data('adv-id');
        var destUrl = $(this).data('dest-url');
        var sendingData = {
            userId: id
        };
        $.ajax({
            url: destUrl,
            type: 'POST',
            data: sendingData,
            success: function(result) {
                document.location.reload();
            },
            error: function() {
                alert("Не сработало, я не знаю в чем проблема");
            }
        })
    });
});
