package it.sevenbits.entity;

/**
 *Класс, представляющий сущность таблицы User а БД
 */
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String vkLink;
    private Long createdDate;
    private Long updatedDate;
    private Boolean isDeleted;

    public User() {
    }

    public User(final String firstName, final String email, final String lastName, final String vkLink, final Long createdDate, final Long updatedDate, final Boolean deleted) {
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
        this.vkLink = vkLink;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        isDeleted = deleted;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Long createdDate) {
        this.createdDate = createdDate;
    }

    public String getVklink() {
        return vkLink;
    }

    public void setVklink(final String vklink) {
        this.vkLink = vklink;
    }

    public Long getUpdateDate() {
        return updatedDate;
    }

    public void setUpdateDate(final Long updateDate) {
        this.updatedDate = updateDate;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(final boolean deleted) {
        isDeleted = deleted;
    }

    public void setFirstName(String value){
        this.firstName = value;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void setLastName(String value){
        this.lastName = value;
    }

    public String getLastName(){
        return this.lastName;
    }

    public void setEmailName(String value){
        this.email = value;
    }

    public String getEmailName(){
        return this.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (createdDate != null ? !createdDate.equals(user.createdDate) : user.createdDate != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (isDeleted != null ? !isDeleted.equals(user.isDeleted) : user.isDeleted != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (updatedDate != null ? !updatedDate.equals(user.updatedDate) : user.updatedDate != null) return false;
        if (vkLink != null ? !vkLink.equals(user.vkLink) : user.vkLink != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (vkLink != null ? vkLink.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }
}
