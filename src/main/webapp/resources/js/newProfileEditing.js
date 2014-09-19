$(".js-avatar-chosen").on('change', function() {
    var currentText = $('.js-image-text');
    currentText.text("Изображение выбрано");
});

$(".js-want-delete-avatar").on('change', function() {
    var checkbox = $(this);
    var chooseImageBlock = $('.js-choose-avatar');
    if (checkbox.is(":checked")) {
        chooseImageBlock.hide();
    } else {
        chooseImageBlock.show();
    }
});