$(".js-want-delete-avatar").on('change', function() {
    var checkbox = $(this);
    var chooseImageBlock = $('.js-choose-avatar');
    if (checkbox.is(":checked")) {
        chooseImageBlock.hide();
    } else {
        chooseImageBlock.show();
    }
});

$(document).ready(function() {
    "use strict";
    var avatar = $(".js-avatar-chosen");

    avatar.change(function(e) {
        var errorField = $(".error-user-photo");
        if (avatar[0].files[0]) {
            if (avatar[0].files[0].size > 3 * 1024 * 1024) {
                errorField.text("Размер файла не должен превышать 3 мегобайт");
                return false;
            }
        }
        if (typeValidation()) {
            errorField.text("");
            $('.js-image-text').text("Изображение выбрано");
        } else {
            errorField.text("Расширение должно быть только .jpg или .png");
            return false;
        }
    });
});