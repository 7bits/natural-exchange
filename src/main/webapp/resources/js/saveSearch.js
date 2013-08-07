$(document).ready(function() {
  $(".save").click( function () {

    $("a.save").fancybox({
       "width" : 610,
       "height" : 460,
       "margin" :0,
       "padding" : 0,
       "autoDimensions": false,
       'hideOnOverlayClick' : false,
       'overlayOpacity' : 0.13 ,
       'overlayColor' : '#000000',
       'showCloseButton': false,
       'scrolling' : 'no'
    });


 //
 //      document.getElementById("block").style.display="block";
 //      document.getElementById("main").style.display="none";
       $("#contact").submit(function() { return false; });

function validateEmail(email) {
    var reg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return reg.test(email);
  }

//  function validateCaptcha (captchaInput) {
//      var cap=session.getAttribute("captcha");
 //     return cap.test(captchaInput);
 // }
  $("#close").click( function(){
      $.fancybox.close();
  });



  $("#send").click( function(e){
    e.preventDefault();
    var wordSearch =$(".wordSearch").val();
    var email  = $("#emailSave").val();
  //  var captchaInput = $ (".captchaInput").val();   // captchaInput - имя поля формы
  //  var captchaValid= validateCaptcha(captchaInput);
    var mailvalid = validateEmail(email);
  // Проверка правильности электронного адреса
    if(mailvalid == false) {
      $("#emailSave").addClass("error");
    }
    else if(mailvalid == true) {
      $("#emailSave").removeClass("error");
        // если обе проверки пройдены
        // сначала мы скрываем кнопку отправки
        $("#send").replaceWith("отправка...");

        var radios = document.getElementsByName('category');
        for (var i = 0, length = radios.length; i < length; i++) {
            if (radios[i].checked) {
                // do whatever you want with the checked radio
                var categorySearch= $(radios[i]).val();
                // only one radio can be logically checked, don't check the rest
                break;
            }
        }
        var dataSearch = 'wordSearch='+wordSearch+'&categorySearch='+categorySearch+'&email='+email;

        $.ajax({
          type: 'GET',
          url: '/n-exchange/advertisement/savingSearch.html',
          data: dataSearch,
          success: function(data) {
              $("#contact").fadeOut("fast", function(){
                if (data == "auth") {
                    $(this).before("<strong> Авторизируйтесь!!! </strong>");
                 }
                if (data == "send") {
                    $(this).before("<strong> Спасибо!!! </strong>");
                 }
                setTimeout("$.fancybox.close()", 2000);
              });
            }
         });
      }
    });
  });
});