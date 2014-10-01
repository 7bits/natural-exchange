var filesExt = ['jpg', 'png']; // массив расширений
function typeValidation() {
    var parts = $('input[type=file]').val().split('.');
    if(filesExt.join().search(parts[parts.length - 1]) != -1){
        return true;
    } else {
        return false;
    }
}
