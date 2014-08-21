$("#add-tag").click( function() {
    var currentTag = $('.js-added-tag');
    var tagElem = $('.chosen-tags');
    var tags = $('.js-tags-chosen');
    var lastTags = tags.val() + " ";
    tagElem.text(lastTags + currentTag.val() + " ");
    tags.val(lastTags + currentTag.val());
});