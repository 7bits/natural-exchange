function readyFn( jQuery ) {
    var vkAuthButton = $(".vkAuth");
    vkAuthButton.click(vkAuthButtonFunc);
}

$( document ).ready( readyFn );

function vkAuthButtonFunc(eventObject) {
    eventObject.preventDefault();
    var url = "https://oauth.vk.com/authorize";
    var client_id = "client_id=3862800&";
    var scope = "scope=notify&";
    var redirect_uri = "redirect_uri=http://naturalexchange.ru/advertisement/list.html&";
    var display = "display=popup&";
    var response_type = "response_type=token";
    var data = client_id + scope + redirect_uri + display + response_type;
    $.ajax({
        type: "GET",
        url: url,
        data: data,
        success: function() {
            var anchor = window.location.hash;
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
                data: jsonData,
                success: function() {
                }
            })
        },
        error: function(){
        }
    });
}