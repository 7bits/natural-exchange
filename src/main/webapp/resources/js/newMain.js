$(document).ready(function () {
    $(".js-category").hover(
        function () {
            var categoryName = $(this).children('.js-category-name');
            categoryName.css("color", "rgb(26, 178, 175)");
        },
        function () {
            var categoryName = $(this).children('.js-category-name');
            categoryName.css("color", "rgb(255, 255, 255)");
        }
    );
});
