$(document).ready(function() {

    $("#cate").click(function ()  {
        var cates = document.getElementsByName('cate');
        var checks = document.getElementsByName('categories');     //массив боксов
        for (var i = 0, length = checks.length; i < length; i++) {
            if (checks[i].checked)  {
                $(cates[i]).css('backgroundImage' ,  'url(../resources/images/checkbox_selected.png)') ; }
            else {
                $(cates[i]).css('backgroundImage' ,  'url(../resources/images/checkbox.png)') ;
            }
        }
    }) ;

} );