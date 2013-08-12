package it.sevenbits.entity;

import it.sevenbits.security.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;

/**
 *Класс, представляющий сущность таблицы UserEntity а БД
 */
public class User implements UserDetails  {
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

    public User(
            final String firstName, final String email, final String lastName,
            final String vkLink, final Long createdDate, final Long updatedDate,
            final Boolean deleted, final String password, final String role
    ) {
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
        this.vkLink = vkLink;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.isDeleted = deleted;
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

    public void setFirstName(final String value) {
        this.firstName = value;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(final String value) {
        this.lastName = value;
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

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPassword(final String password) {
        this.password = password;
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

    public void setRole(final String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
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
        return isDeleted;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (!createdDate.equals(user.createdDate)) {
            return false;
        }
        if (!email.equals(user.email)) {
            return false;
        }
        if (!firstName.equals(user.firstName)) {
            return false;
        }
        if (!isDeleted.equals(user.isDeleted)) {
            return false;
        }
        if (!lastName.equals(user.lastName)) {
            return false;
        }
        if (!password.equals(user.password)) {
            return false;
        }
        if (!role.equals(user.role)) {
            return false;
        }
        if (!updatedDate.equals(user.updatedDate)) {
            return false;
        }
        if (!vkLink.equals(user.vkLink)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + vkLink.hashCode();
        result = 31 * result + createdDate.hashCode();
        result = 31 * result + updatedDate.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + isDeleted.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }
}
