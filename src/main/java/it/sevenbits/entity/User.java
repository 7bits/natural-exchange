package it.sevenbits.entity;

import it.sevenbits.security.Role;
import it.sevenbits.util.TimeManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;

/**
 *Class for entity, that represents User in DB
 */
public class User implements UserDetails  {
    private String firstName;
    private String lastName;
    private String email;
    private String vkLink;
    private Long createdDate;
    private Long updatedDate;
    private String password;
    private Boolean isBanned;
    private String role;
    private String activationCode;
    private Long activationDate;
    private String avatar;



    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(final String activationCode) {
        this.activationCode = activationCode;
    }

    public Long getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(final Long activationDate) {
        this.activationDate = activationDate;
    }

    public User() {
    }

    public User(
            final String firstName, final String email, final String lastName, final String vkLink,
            final Long createdDate, final Long updatedDate, final Boolean banned, final String password,
            final String role, final String activationCode, final Long activationDate, final String avatar
    ) {
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
        this.vkLink = vkLink;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.isBanned = banned;
        this.password = password;
        this.role = role;
        this.activationCode = activationCode;
        this.activationDate = activationDate;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public boolean getIsBanned() {
        return isBanned;
    }
    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getVk_link() {
        return vkLink;
    }

    public String getPassword() {
        return password;
    }

    public Long getUpdateDate() {
        return updatedDate;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public void setIsBanned(final boolean banned) {
        isBanned = banned;
    }

    public void setFirstName(final String value) {
        this.firstName = value;
    }

    public void setLastName(final String value) {
        this.lastName = value;
    }

    public void setCreatedDate(final Long createdDate) {
        this.createdDate = createdDate;
    }

    public void setVk_link(final String vklink) {
        this.vkLink = vklink;
    }

    public void setUpdateDate(final Long updateDate) {
        this.updatedDate = updateDate;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
    public void setRole(final String role) {
        this.role = role;
    }
    public Role  getRoleGrantedAuth() {

        //если только одна роль
        if (role.equals("ROLE_USER")) {
            return Role.createUserRole();
        }
        if (role.equals("ROLE_ADMIN")) {
            return Role.createAdminRole();
        }
        return Role.createModeratorRole();
    }

    public String getCreatedDateFormat() {
        return TimeManager.getDateString(createdDate);
    }

   @Override
    public Collection<? extends GrantedAuthority>  getAuthorities() {
        Collection<Role> collection = new ArrayList<>();
        collection.add(this.getRoleGrantedAuth());
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
        return !isBanned;
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User that = (User) o;

        if (firstName != null ? ! firstName.equals(that.firstName) : that.firstName != null) return false;
        if (email != null ? ! email.equals(that.email) : that.email != null) return false;
        if (lastName != null ? ! lastName.equals(that.lastName) : that.lastName != null) return false;
        if (vkLink != null ? ! vkLink.equals(that.vkLink) : that.vkLink != null) return false;
        if (createdDate != null ? ! createdDate.equals(that.createdDate) : that.createdDate!= null) return false;
        if (password != null ? ! password.equals(that.password) : that.password!= null) return false;
        if (updatedDate != null ? !updatedDate.equals(that.updatedDate) : that.updatedDate != null) return false;
        if (isBanned != null ? ! isBanned.equals(that.isBanned) : that.isBanned!= null) return false;
        if (role != null ? ! role.equals(that.role) : that.role!= null) return false;
        if (activationCode!= null ? !activationCode.equals(that.activationCode) : that.activationCode!= null) return false;
        if (activationDate != null ? ! activationDate.equals(that.activationDate) : that.activationDate!= null) return false;
        if (avatar != null ? ! avatar.equals(that.avatar) : that.avatar!= null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = email!= null ? email.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName!= null ? lastName.hashCode() : 0);
        result = 31 * result + (password!= null ? password.hashCode() : 0);
        result = 31 * result + (vkLink!= null ? vkLink.hashCode() : 0);
        result = 31 * result + (activationDate!= null ? activationDate.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (updatedDate != null ? updatedDate.hashCode() : 0);
        result = 31 * result + (isBanned != null ? isBanned.hashCode() : 0);
        result = 31 * result + (role!= null ? role.hashCode() : 0);
        result = 31 * result + (isBanned != null ? isBanned.hashCode() : 0);
        result = 31 * result + (avatar!= null ? avatar.hashCode() : 0);
        return result;
    }
}
