$(document).ready(function() {
 //   $("#contact").keypress(function(e){
 //       if(e.keyCode===13) {
 //       e.preventDefault();
 //       }
 //   });
    $.fancybox.resize();
 //   $("#contact").submit(function() { return false; });
    $("#close-window").click( function(){
        $.fancybox.close();
    });
    $("a.login").fancybox({
        "imageScale" : false,
        "width" : 600,
        "height" : 460,
        "margin" :0,
        "padding" : 0,
        'modal' :  true,
        'overlayOpacity' : 0.13 ,
        'overlayColor' : '#000000',
        'scrolling' : 'no'
    });
    $("a.manage-panel").fancybox({
        "imageScale" : false,
        "width" : 600,
        "height" : 460,
        "margin" :0,
        "padding" : 0,
        'modal' :  true,
        'overlayOpacity' : 0.13 ,
        'overlayColor' : '#000000',
        'scrolling' : 'no',
 //       'onStart' : function() {
   //                       document.getElementById('mainEntry').innerHTML = "<div class="">  </div>" ;
  //                     } ;
    });
});