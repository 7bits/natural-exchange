$(document).ready(function() {
    $('.submit-email').click(function(e) {
        e.preventDefault();
        var emailString = $('.for-email');
        var errorString = $('.errorLk');
        var dataJson = {
            email: emailString.val(),
            errors: null
        };

        $.ajax({
            type: 'POST',
            url: '/n-exchange/new/main.html',
            data: dataJson,
            success: function(data, textStatus, jqXHR) {
                if (data.success == true) {
                    errorString.text("");
                    emailString.val("Ваш e-mail добавлен.");
                } else {
                    var errorVariant = data.errors;
                    if(errorVariant.exist) {
                        emailString.val(data.errors.exist);
                        errorString.text("");
                    } else if (errorVariant.wrong) {
                        errorString.text(data.errors.wrong);
                    }
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                if(jqXHR.status==404) {
                    alert(errorThrown);
                }
            }
        })
    })
});
