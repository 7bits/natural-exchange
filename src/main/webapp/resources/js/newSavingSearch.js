$(document).ready(function() {
    "use strict";
    $('.js-save-search').click(function(e) {
        e.preventDefault();
        var url = $('.js-save-search').data('url');
        var currentPage = $('.current-page');
        var categories = $('.choosen-category');
        var category;
        for (var i = 0; i < categories.length; i++) {
            if (categories[i].selected) {
                category = categories[i].value;
                break;
            }
        }
        if (category == null) {
            category = 'games';
        }
        var keyWords = $('.tag-search');
        var sendingData = {
            keyWords: keyWords.val(),
            currentCategory: category,
            currentPage: currentPage[0].value
        };
        $.ajax({
            type: 'POST',
            url: url,
            data: sendingData,
            success: function(data, textStatus, jqXHR) {
                alert("Поиск успешно сохранен");
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert("Извините, что-то пошло не так");
            }
        })
    })
});
