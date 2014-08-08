$(document).ready(function() {
    $("#cate").click(function ()  {
        var cates = document.getElementsByName('cate');
        var checks = document.getElementsByName('categories');
        for (var i = 0, length = checks.length; i < length; i++) {
            if (checks[i].checked)  {
                $(cates[i]).css('backgroundImage' ,  'url(../../resources/images/checkbox_selected.png)') ;
            }
            else {
                $(cates[i]).css('backgroundImage' ,  'url(../../resources/images/checkbox.png)') ;
            }
        }
    }) ;
    $("#cate-reg").click(function ()  {
        var cate = document.getElementById('cate-label');
        if (this.checked)  {
            $(cate).css('backgroundImage' ,  'url(../../resources/images/checkbox_selected.png)') ;
        }
        else {
            $(cate).css('backgroundImage' ,  'url(../../resources/images/checkbox.png)') ;
        }
    }) ;
    $("#cate2").click(function ()  {
        var cates = document.getElementsByName('cate');
        var checks = document.getElementsByName('categories');
        for (var i = 0, length = checks.length; i < length; i++) {
            if (checks[i].checked)  {
                $(cates[i]).css('backgroundImage' ,  'url(../resources/images/checkbox_selected.png)') ;
            }
            else {
                $(cates[i]).css('backgroundImage' ,  'url(../resources/images/checkbox.png)') ;
            }
        }
    }) ;
} );