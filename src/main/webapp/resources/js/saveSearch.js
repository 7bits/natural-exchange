$(document).ready(function() {
  $("#klik").click( function () {
       document.getElementById("block").style.display="block";
       document.getElementById("main").style.display="none";
       $("#contact").submit(function() { return false; });

function validateEmail(email) {
    var reg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return reg.test(email);
  }

  $("#send").click( function(e){
    e.preventDefault();
    var wordSearch =$(".wordSearch").val();
    var email  = $("#emaiSavel").val();
    var mailvalid = validateEmail(email);
  // Проверка правильности электронного адреса
    if(mailvalid == false) {
      $("#emailSave").addClass("error");
    }
    else if(mailvalid == true){
      $("#emailSave").removeClass("error");
        // если обе проверки пройдены
        // сначала мы скрываем кнопку отправки
        $("#send").replaceWith("отправка...");
        var dataSearch = '$wordSearch'+wordSearch+'$email'+email;

        $.ajax({
          type: 'GET',
          url: '/advertisement/savingSearch.html',
          data: dataSearch,
          success: function(data) {
              $("#contact").fadeOut("fast", function(){
                $(this).before("<strong>Успешно! Ваше сообщение отправлено :)</strong>");
                setTimeout("$.fancybox.close()", 1000);
              });
            }
         });
      }
    });
} );
} );