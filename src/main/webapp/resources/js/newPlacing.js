$("#add-tag").click( function() {
    var currentTag = $('.js-added-tag');
    var tagElem = $('.chosen-tags');
    var tags = $('.js-tags-chosen');
    var lastTags = tags.val() + " ";
    tagElem.text(lastTags + currentTag.val() + " ");
    tags.val(lastTags + currentTag.val());
});

$(".js-image-chosen").change( function() {
    var currentText = $('.js-image-text');
    var imageChosen = $('.js-image-chosen').val();
    var strImage = imageChosen.toString();
    strImage.substring("[a-z]:\");
    currentText.text(strImage);
});

//div.tag-block
// span.tag крутая_штука
// span.delete-button(data-tag="крутая_штука") x
//div
// span крутая_штука
// span x
//div
// span крутая_штука
// span x
//div
// span крутая_штука
// span x
//div
// span крутая_штука
// span x
//
//elem = $('.add')
//elem.parent('.tag-block')
//elem.find('.another-selector')