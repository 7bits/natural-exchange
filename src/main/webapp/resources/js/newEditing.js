$("#add-tag").on('click', function() {
    var tags = $('.js-tags-chosen');
    var previousTags = tags.val();
    var currentTag = $('.js-added-tag');
    if (currentTag.val().length > 0) {
        if ((previousTags.length + currentTag.val().length) > 130) {
            var tagsError = $('.js-tags-error');
            tagsError.text("Слишком много тегов у объявления. Пожалуйста, уберите несколько тегов.");
            currentTag.val("");
        } else {
            var tagText = currentTag.val() + " ";
            $('.js-tags-placing').append("<div class='tags-and-cross'><div class='chosen-tag'>" + tagText + "</div><a class='cross-circle js-deleting-tag'></a></div>");
            var currentTags = previousTags.concat(tagText);
            tags.val(currentTags);
            currentTag.val("");
        }
    }
});

$(".js-image-chosen").on('change', function() {
    var currentText = $('.js-image-text');
    currentText.text("Изображение выбрано");
    var currentImage = $('.js-current-image');
    currentImage.remove();
});

$(".js-want-delete-photo").on('change', function() {
    var checkbox = $(this);
    var chooseImageBlock = $('.js-choose-image');
    if (checkbox.is(":checked")) {
        chooseImageBlock.hide();
    } else {
        chooseImageBlock.show();
    }
});

$("body").on('click', '.js-deleting-tag', function(){
    var tagsError = $('.js-tags-error');
    tagsError.text("");
    var currentCrossPressed = $(this);
    var deletedTag = $(this).siblings().text();
    var tags = $('.js-tags-chosen');
    var currentTags = tags.val();
    currentTags = currentTags.replace(deletedTag, "");
    tags.val(currentTags);
    currentCrossPressed.parent().remove();
});

$(document).ready(function() {
    var image = $(".js-image-chosen");

    image.change(function(e) {
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