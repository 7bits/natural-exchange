function Validate()
{
    var image =document.getElementById("image").value;
    if(image!=''){
        var checkimg = image.toLowerCase();
        if (!checkimg.match(/(\.jpg|\.png|\.JPG|\.PNG|\.jpeg|\.JPEG)$/)){
            alert("Please enter Image File Extensions .jpg,.png,.jpeg");
            document.getElementById("image").focus();
            return false;
        }
    }
    return true;
}