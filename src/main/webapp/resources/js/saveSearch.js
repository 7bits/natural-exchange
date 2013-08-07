$(document).ready(function() {

    $("a.save").fancybox( {
       "width" : 1000,
       "height" : 500,
       "margin" :0,
       "autoDimensions": false,

    });
 // $("#klik").click( function () {
 //      document.getElementById("block").style.display="block";
 //      document.getElementById("main").style.display="none";
       $("#contact").submit(function() { return false; });

function validateEmail(email) {
    var reg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return reg.test(email);
  }



  $("#send").click( function(e){
    e.preventDefault();
    var wordSearch =$(".wordSearch").val();
    var email  = $("#emailSave").val();
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

        var radios = document.getElementsByName('category');
        for (var i = 0, length = radios.length; i < length; i++) {
            if (radios[i].checked) {
                // do whatever you want with the checked radio
                var categorySearch= radios[i];

                // only one radio can be logically checked, don't check the rest
                break;
            }
        }
        var dataSearch = 'wordSearch='+wordSearch+'$categorySearch='+categorySearch+'$email='+email;

        $.ajax({
          type: 'GET',
          url: '/n-exchange/advertisement/savingSearch.html',
          data: dataSearch,
          success: function(data) {
              $("#contact").fadeOut("fast", function(){
                $(this).before("<strong>" +data+"</strong>");
                setTimeout("$.fancybox.close()", 2000);
              });
            }
         });
      }
    });
} );
