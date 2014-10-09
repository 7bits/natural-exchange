$(document).ready(function () {
    "use strict";
    var searchError = $('.js-search-error');
    searchError.text("");
    $('.js-save-search').click(function (e) {
        e.preventDefault();
        var url = $('.js-save-search').data('url');
        var currentPage = $('.current-page');
        var categories = $('.choosen-category');
        var searchError = $('.js-search-error');
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
        searchError.text("");
        if (keyWords.val().length > 0) {
            if (keyWords.val().length > 20) {
                searchError.text("Недопустимо больше 20 символов.");
            } else {
                $.ajax({
                    type: 'POST',
                    url: url,
                    data: sendingData,
                    success: function (data, textStatus, jqXHR) {
                        if (data.searchVariantExist) {
                            $.gritter.add({
                                title: data.searchVariantExist,
                                image: "/resources/images/newdesign/logo.png"
                            });
                        }
                        $.gritter.add({
                            title: "Поиск успешно сохранен.",
                            image: "/resources/images/newdesign/logo.png"
                        });
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        $.gritter.add({
                            title: "Произошел сбой в системе. Повторите попытку позднее.",
                            image: "/resources/images/newdesign/logo.png"
                        });
                    }
                })
            }
        } else {
            searchError.text("Введите параметр поиска.");
        }
    });
    $('.tag-search').click(function(e) {
        var searchError = $('.js-search-error');
        searchError.text("");
    });
});
