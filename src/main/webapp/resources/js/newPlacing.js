$("#add-tag").on('click', function() {
    var currentTag = $('.js-added-tag');
    var tagText = currentTag.val() + " ";
    $('.js-chosen-tags').append("<div class='tags-and-cross'><a class='chosen-tag'>" + tagText + "</a><a class='cross-circle js-deleting-tag'></a></div>");
});

$(".js-image-chosen").on('change', function() {
    var currentText = $('.js-image-text');
    currentText.text("Изображение выбрано");
});

$("body").on('click', '.js-chosen-tags', function(){
    var deletedTag = $(".js-deleting-tag").siblings().text();
    var tags = $('.js-tags-chosen');
    var currentTags = tags.val();
    currentTags = currentTags.replace(deletedTag, "");
    tags.val(currentTags);
    $(".js-deleting-tag").siblings().remove();
    $(".js-deleting-tag").remove();
    $(".js-deleting-tag").parent().remove();
});
