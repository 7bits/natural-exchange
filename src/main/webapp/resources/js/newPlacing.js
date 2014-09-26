$("#add-tag").on('click', function() {
    var tags = $('.js-tags-chosen');
    var previousTags = tags.val();
    var currentTag = $('.js-added-tag');
    var tagText = currentTag.val() + " ";
    $('.js-tags-placing').append("<div class='tags-and-cross'><div class='chosen-tag'>" + tagText + "</div><a class='cross-circle js-deleting-tag'></a></div>");
    var currentTags = previousTags.concat(tagText);
    tags.val(currentTags);
    currentTag.val("");
});

$("body").on('click', '.js-deleting-tag', function(){
    var currentCrossPressed = $(this);
    var deletedTag = $(".js-deleting-tag").siblings().text();
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
                errorField.text("Размер файла не должен превышать 3 мегабайт");
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
