$(document).ready(function () {
    var tags = $('.js-tags-chosen');
    tags.val(tags.val().slice(0, tags.val().length - 1));

    $("#add-tag").on('click', function () {
        var maxTagLength = 20;
        if ($('.js-server-tags-error').length) {
            $('.js-server-tags-error').text("");
        }
        var tags = $('.js-tags-chosen');
        var previousTags = tags.val();
        var currentTag = $('.js-added-tag');
        if (currentTag.val().length > 0) {
            var tagsError = $('.js-tags-error');
            if (currentTag.val().length > maxTagLength) {
                tagsError.text("Максимальная длина тега - 20 символов.");
                currentTag.val("");
            } else if ((previousTags.length + currentTag.val().length) > 100) {
                tagsError.text("Слишком много тегов у объявления. Пожалуйста, уберите несколько тегов.");
                currentTag.val("");
            } else {
                if (previousTags.length < 1) {
                    var tagText = currentTag.val();
                } else {
                    var tagText = " " + currentTag.val();
                }
                $('.js-tags-placing').append("<div class='tags-and-cross'><div class='chosen-tag'>" + tagText + "</div><a class='cross-circle js-deleting-tag'></a></div>");
                var currentTags = previousTags.concat(tagText);
                tags.val(currentTags);
                tagsError.text("");
                currentTag.val("");
            }
        }
    });

    $(".js-want-delete-photo").on('change', function () {
        var checkbox = $(this);
        var chooseImageBlock = $('.js-choose-image');
        var tagsButton = $('.js-tags-button');
        if (checkbox.is(":checked")) {
            chooseImageBlock.hide();
            tagsButton.css("margin-left", "500px");
        } else {
            chooseImageBlock.show();
            tagsButton.css("margin-left", "0px");
        }
    });

    $(".js-image-chosen").on('change', function () {
        var currentText = $('.js-image-text');
        currentText.text("Изображение выбрано");
        var currentImage = $('.js-current-image');
        currentImage.remove();
    });

    $("body").on('click', '.js-deleting-tag', function () {
        var tagsError = $('.js-tags-error');
        tagsError.text("");
        var currentCrossPressed = $(this);
        var parentCross = $(this).parent();
        if (parentCross.next('div').length) {
            var deletedTag = $(this).siblings().text() + " ";
            var tags = $('.js-tags-chosen');
            var currentTags = tags.val();
            if (currentTags.indexOf(deletedTag) == 0) {
                deletedTag = deletedTag.slice(1, deletedTag.length);
                currentTags = currentTags.slice(deletedTag.length, currentTags.length);
            } else {
                currentTags = currentTags.replace(deletedTag, " ");
            }
        } else {
            var deletedTag = $(this).siblings().text();
            var tags = $('.js-tags-chosen');
            var currentTags = tags.val();
            if (deletedTag.length - 1 == currentTags.length) {
                currentTags = "";
            } else {
                currentTags = currentTags.slice(0, currentTags.length - deletedTag.length);
            }
        }
        tags.val(currentTags);
        currentCrossPressed.parent().remove();
    });

    var image = $(".js-image-chosen");

    image.change(function (e) {
        var errorField = $(".error-image");
        if (image[0].files[0]) {
            if (image[0].files[0].size > 3 * 1024 * 1024) {
                errorField.text("Размер файла не должен превышать 3 мегобайт");
                return false;
            }
        }
        if (typeValidation()) {
            errorField.text("");
            $('.js-image-text').text("Изображение выбрано");
            $('.js-current-image').remove();
        } else {
            errorField.text("Расширение должно быть только .jpg или .png");
            return false;
        }
    })
});