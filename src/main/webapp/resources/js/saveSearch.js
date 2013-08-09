function validateEmail(email) {
    var reg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return reg.test(email);
  }

  function validateCaptcha (captchaInput) {
      var cap='<% = Session ["captcha"] %>';
      if (cap == captchaInput)
      {return true;  }
      else {return false;}
  }






$(document).ready(function() {

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


  $("#refresh").click( function(){
     document.getElementById('captchaImg').src = "makeCaptcha.html?id=" + Math.random();
  });



     document.getElementById("contact").style.display="block";
 //
 //      document.getElementById("block").style.display="block";
 //      document.getElementById("main").style.display="none";
       $("#contact").submit(function() { return false; });


  $("#close").click( function(){
      $.fancybox.close();
  });

   var cap='<% = session ["captcha"] %>';



  $("#send").click( function(e){
    e.preventDefault();
    document.getElementById('message').innerHTML = "";
    var wordSearch =$(".wordSearch").val();
    var email  = $("#emailSave").val();
    var captchaInput = $ (".captchaInput").val();   // captchaInput - имя поля формы
    var captchaValid= validateCaptcha(captchaInput);
    var mailvalid = validateEmail(email);
    // Проверка правильности электронного адреса
    if(mailvalid == false) {
      document.getElementById('message').innerHTML = "Введите корректный e-mail адрес.";
 //    $("#emailSave").addClass("error");
    }
    else if(mailvalid == true) {
  //    $("#emailSave").removeClass("error");

        // если обе проверки пройдены
        // сначала мы скрываем кнопку отправки
        $("#send").replaceWith("отправка...");

       var categorySearch="";

        var checkboxes = document.getElementsByName('categories');     //массив боксов
        for (var i = 0, length = checkboxes.length; i < length; i++) {
           if (checkboxes[i].checked)  {
//                // do whatever you want with the checked radio
                var categorySearch=categorySearch+' '+ checkboxes[i].value;
//                // only one radio can be logically checked, don't check the rest
//                break;
           }
        }
        var dataSearch = 'wordSearch='+wordSearch+'&categorySearch='+categorySearch+'&email='+email;

        $.ajax({
          type: 'GET',
          url: '/n-exchange/advertisement/savingSearch.html',
          data: dataSearch,
          success: function(data) {
//            document.getElementById("contact").style.display="none";
              $("#contact").fadeOut("fast", function(){
                if (data == "auth") {
                    $(this).before("<strong> Авторизуйтесь!!! </strong>");
                 }
                if (data == "save") {
                    $(this).before("<strong> Спасибо!!! </strong>");
                 }
                setTimeout("$.fancybox.close()", 2000);
              });
            }
         });
          $("#contact").fadeIn("fast");
      }
    });

});