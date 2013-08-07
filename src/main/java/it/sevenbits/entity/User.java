package it.sevenbits.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 *Класс, представляющий сущность таблицы UserEntity а БД
 */
public class User
        /*implements UserDetails*/  {
    private String firstName;
    private String lastName;
    private String email;
    private String vkLink;
    private Long createdDate;
    private Long updatedDate;
    private String password;
    private Boolean isDeleted;
    private String role;

    public User() {
    }

    public User(final String firstName, final String email, final String lastName, final String vkLink, final Long createdDate,
                final Long updatedDate, final Boolean deleted, final String password, final String role) {
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
        this.vkLink = vkLink;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        isDeleted = deleted;
        this.password = password;
        this.role = role;
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

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String  getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }
  /*
    @Override
    public Collection<? extends GrantedAuthority>  getAuthorities() {
        Collection<String> collection = new ArrayList<>();
        collection.add(role);
        return collection;
    }



    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isDeleted;
    }
    */

    //TODO add role
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

        if (password != null ? !password.equals(user.password) : user.password  != null ) return false;

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
        result = 31 * result + (password != null ? password.hashCode(): 0);

        return result;
    }
}
