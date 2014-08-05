function readyFn( jQuery ) {
    var vkAuthButton = $(".vkEntry");
    vkAuthButton.click(vkAuthButtonFunc);
    var sendEmailConfirm = $(".sendEmailConfirm");
    sendEmailConfirm.click(emailConfirm);
    $(".closeEmailConfirm").click(function() {
        $(".vkEmailConfirmContainer").hide();
    });
    myAuth();
}

var usrInfo;
$( document ).ready( readyFn );

//function vkAuthButtonFunc(eventObject) {
//    eventObject.preventDefault();
//    var url = "https://oauth.vk.com/authorize";
//    var client_id = "client_id=3862800&";
//    var scope = "scope=notify&";
//    var redirect_uri = "redirect_uri=http://naturalexchange.ru/login.html&";
//    var display = "display=popup&";
//    var response_type = "response_type=token";
//    var data = client_id + scope + redirect_uri + display + response_type;
//    //window.open(url + "?" + data,"window",'width=200,height=400');
//    window.location.replace(url + "?" + data);
//}

function myAuth() {
    var anchor = window.location.hash;
    if(anchor.indexOf("access_token=") > 0) {
        var t = anchor.replace("#access_token=","");
        t = t.replace(" ","");
        var access_token = "";
        var i = 0;
        while(t[i] != "&") {
            access_token += t[i];
            i++;
        }
        t = t.replace(access_token + "&expires_in=","");
        var expires_in = "";
        i = 0;
        while(t[i] != "&") {
            expires_in += t[i];
            i++;
        }
        var user_id = "";
        t = t.replace(expires_in + "&user_id=","");
        user_id = t;
        var thing = { "access_token" : access_token, "user_id": user_id };
        jsonData = $.toJSON(thing);
        $.ajax({
            type: "POST",
            url: "http://naturalexchange.ru/VK/auth.html",
            data: user_id,
            success: function(result) {
                if(result.success == "true") {
                    window.location.replace("http://naturalexchange.ru/advertisement/list.html");
                } else if (result.success == "false") {
                    var script = document.createElement('SCRIPT');
                    script.src = "https://api.vk.com/method/getProfiles?uid=" + user_id + "&v=5.0&access_token=" + access_token + "&callback=callbackFunc";
                    document.getElementsByTagName("head")[0].appendChild(script);
                }
            }
        })
    }
}

function callbackFunc(result1) {
    usrInfo = result1;
    $(".vkEmailConfirmContainer").show();
}

function emailConfirm() {
    document.getElementById('messageEmailConfirm').innerHTML = "";
    var email = $(".vkEmailConfirmInput").val();
    var mailvalid = validateEmail(email);
    if(mailvalid === false) {
        document.getElementById('messageEmailConfirm').innerHTML = "Введите корректный e-mail адрес.";
    }
    else if (mailvalid === true) {
        var jsonEmail =
            "email=" + email +
                "&first_name=" + usrInfo.response[0].first_name +
                "&last_name=" + usrInfo.response[0].last_name +
                "&id=" + usrInfo.response[0].id;
        $.ajax({
            type: 'POST',
            url: '/VK/registration.html',
            data: jsonEmail
        });
        $(".vkEmailConfirmContainer").hide();
        window.location.replace("http://naturalexchange.ru/user/regUserLink.html");
    }

}

function validateEmail(email) {
    "use strict";
    var reg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return reg.test(email);
}