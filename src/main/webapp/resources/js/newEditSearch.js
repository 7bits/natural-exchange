$("#add-keyword").on('click', function() {
    var keywords = $('.js-keywords-chosen');
    var previousKeywords = keywords.val();
    var currentKeyword = $('.js-added-keyword');
    if (previousKeywords.length < 1) {
        var tagText = currentKeyword.val();
    } else {
        var tagText = " " + currentKeyword.val();
    }
    $('.js-keywords-placing').append("<div class='keyword-and-cross'><div class='chosen-keyword'>" + tagText + "</div><a class='cross-circle js-deleting-keyword'></a></div>");
    var currentTags = previousKeywords.concat(tagText);
    keywords.val(currentTags);
});

$("body").on('click', '.js-deleting-keyword', function(){
    var currentCrossPressed = $(this);
    var deletedKeyword = $(this).siblings().text();
    var keywords = $('.js-keywords-chosen');
    var currentKeywords = keywords.val();
    currentKeywords = currentKeywords.replace(deletedKeyword, "");
    keywords.val(currentKeywords);
    currentCrossPressed.parent().remove();
});
