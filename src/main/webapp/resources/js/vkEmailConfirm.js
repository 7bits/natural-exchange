$(document).ready(function() {
    document.getElementById("contactEmailConfirm").style.display="block";
    $("a.emailConfirm").fancybox({
        "width" : 600,
        "height" : 460,
        "margin" :0,
        "padding" : 0,
        'modal' :  true,
        'overlayOpacity' : 0.13 ,
        'overlayColor' : '#000000',
        'scrolling' : 'no'
    });
    $.fancybox.resize();
    $("#closeEmailConfirm").click( function(){
        $.fancybox.close();
    });
    $("#sendEmailConfirm").click( function(e){
        e.preventDefault();
        document.getElementById('messageEmailConfirm').innerHTML = "";
        var email = $("#emailConfirm").val();
        var mailvalid = validateEmail(email);
        if(mailvalid === false) {
            document.getElementById('messageEmailConfirm').innerHTML = "Введите корректный e-mail адрес.";
        }
        else if (mailvalid === true) {
            $("#sendEmailConfirm").replaceWith("отправка...");
            var jsonEmail = {"email" : email};
            jsonEmail = $.toJSON( jsonEmail );
            $.ajax({
                type: 'POST',
                url: '/n-exchange/advertisement/savingSearch.html',
                data: jsonEmail,
                success: function(data) {
                    //$("#contact").fadeOut("fast", function(){
                        //if (data === "auth") {
                         //   document.getElementById("auth").style.display="block";
                       // }
                       // if (data === "save") {
                        //    document.getElementById("saving").style.display="block";
                      //  }
                        setTimeout("$.fancybox.close();", 2000);
                    //});
                }
            });
            document.getElementById("registration").style.display="none";
            //document.getElementById("auth").style.display="none";
        }
    });
});

function validateEmail(email) {
    "use strict";
    var reg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return reg.test(email);
  }

