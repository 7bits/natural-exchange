$(document).ready(function() {
    "use strict";
    document.getElementById("contact").style.display="block";
    $("a.save").fancybox({
        "width" : 600,
        "height" : 460,
        "margin" :0,
        "padding" : 0,
        'modal' :  true,
        'overlayOpacity' : 0.13 ,
        'overlayColor' : '#000000',
        'scrolling' : 'no'
    });
    $('.js-save').click(function() {
        var wordSearch =$(".wordSearch").val();
        var checkboxes = document.getElementsByName('categories');
        var categorySearch = "";
        for (var i = 0, length = checkboxes.length; i < length; i++) {
            if (checkboxes[i].checked)  {
                categorySearch += checkboxes[i].value ;
            }
            if (i != length - 1) {
                categorySearch += ' ';
            }
        }
        var mail = $('.js-save').data("email");
        var dataSearch = 'wordSearch=' + wordSearch + '&categorySearch=' + categorySearch + '&email=' + mail + "&isNeedCapcha=" + false;
        $.ajax({
            type: 'POST',
            url: '/n-exchange/advertisement/savingSearch.html',
            data: dataSearch,
            success: function(data, textStatus, jqXHR) {
                alert("Новый поиск доступен в вашем личном кабинете");
            },
            error: function(jqXHR, textStatus, errorThrown) {
                alert("Простите, что-то пошло не так и поиск не был сохранен");
            }
        })
    })
    $("#contact").keypress(function(e){
        if(e.keyCode===13) {
        e.preventDefault();
        }
    });
    $.fancybox.resize();
    $("#refresh").click( function() {
        document.getElementById('captcha-img').src = "captcha.jpg";
    });
    $("#contact").submit(function() { return false; });
    $("#close").click( function(){
        $("#captchaInput").val("");
        $("#emailSave").val("");
        $('.message').empty();
        document.getElementById("saving").style.display="none";
        document.getElementById("auth").style.display="none";
        $.fancybox.close();
    });
    $("#send").click( function(e){
        e.preventDefault();
        var wordSearch =$(".wordSearch").val();
        var email = $("#emailSave").val();
        var captchaInput = $("#captchaInput").val();
        var mailvalid = validateEmail(email);
        if(mailvalid === false) {
            $('.message').text("Введите корректный e-mail адрес.");
        }
//        else if (captchaInput==="") {
//             document.getElementById('message').innerHTML = "Символы с картинки не верны   ";
//        }
        else if (mailvalid === true) {
            $(".message").text("отправка... ");
            var categorySearch = "";
            var checkboxes = document.getElementsByName('categories');
            for (var i = 0, length = checkboxes.length; i < length; i++) {
                if (checkboxes[i].checked)  {
                    categorySearch += checkboxes[i].value;
                }
                if (i != length - 1) {
                    categorySearch += ' ';
                }
            }
            var dataSearch = 'wordSearch='+wordSearch+'&categorySearch='+categorySearch+'&email='+email+"&captcha="+captchaInput +
                "&isNeedCapcha="+true;
            $.ajax({
                type: 'POST',
                url: '/n-exchange/advertisement/savingSearch.html',
                data: dataSearch,
                success: function(result) {
                    if(result.success == "true") {
                        document.getElementById("saving").style.display="block";
                        setTimeout("$.fancybox.close();", 500);
                        setTimeout(function() {
                            $("#captchaInput").val("");
                            $("#emailSave").val("");
                            $('.message').empty();
                            document.getElementById("saving").style.display="none";
                            document.getElementById("auth").style.display="none";
                        },500);
                    } else if (result.success == "captcha") {
                        $('.message').empty();
                        $(".message").text("Символы с картинки не верны   ");
                    } else if (result.success == "auth") {
                        //document.getElementById("auth").style.display="block";
                    }
                    $("#refresh").click();
                }
            });

        }
    });
});
function validateEmail(email) {
    "use strict";
    var reg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return reg.test(email);
}

function validateCaptcha (captchaInput) {
    "use strict";
    var cap='<% = Session ["captcha"] %>';
    if (cap === captchaInput) {
        return true;
    }
    else {
        return false;
    }
}