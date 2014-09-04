$("#add-tag").on('click', function() {
    var tags = $('.js-tags-chosen');
    var previousTags = tags.val();
    var currentTag = $('.js-added-tag');
    var tagText = currentTag.val() + " ";
    $('.js-tags-placing').append("<div class='tags-and-cross'><div class='chosen-tag'>" + tagText + "</div><a class='cross-circle js-deleting-tag'></a></div>");
    var currentTags = previousTags.concat(tagText);
    tags.val(currentTags);
});

$(".js-image-chosen").on('change', function() {
    var currentText = $('.js-image-text');
    currentText.text("Изображение выбрано");
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
