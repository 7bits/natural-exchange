package it.sevenbits.util.form;

import org.springframework.web.multipart.MultipartFile;

public class EditingUserInfoForm {
    private String previousAvatar;
    private String firstName;
    private String lastName;
    private MultipartFile image;


    public String getPreviousAvatar() {
        return previousAvatar;
    }

    public void setPreviousAvatar(String previousAvatar) {
        this.previousAvatar = previousAvatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
