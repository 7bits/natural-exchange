package it.sevenbits.security;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;


/**
 * Provides granted authority implementation in terms of Spring Security
 */
public final class Role implements GrantedAuthority {

    private static final long serialVersionUID = 6278822946136166602L;

    private static final String ROLE_USER = "ROLE_USER";
    private static final String ROLE_MODERATOR = "ROLE_MODERATOR";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    private String name;
    private String description;

    /**
     * Default constructor - creates a new instance with no values set
     */
    private Role() {
        this(null, null);
    }

    /**
     * Creates a new instance and set all properties
     * @param name name of the role
     * @param description description of the role
     */
    private Role(final String name, final String description) {

        this.name = name;
        this.description = description;
    }

    /**
     * Fabric method for user role
     * @return created role object
     */
    public static Role createUserRole() {
        return new Role(Role.ROLE_USER, "User");
    }

    /**
     * Fabric method for moderator role
     * @return created role object
     */
    public static Role createModeratorRole() {
        return new Role(Role.ROLE_MODERATOR, "Moderator");
    }

     /**
     * Fabric method for admin role
     * @return created role object
     */
    public static Role createAdminRole() {
        return new Role(Role.ROLE_ADMIN, "Admin");
    }

    /**
     * Returns name property
     * @return name property
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns description property
     * @return description property
     */
    public String getDescription() {
        return this.description;
    }
    
    /**
     * @see org.springframework.security.core.GrantedAuthority#getAuthority
     * @return the name property
     */
    @Override
    public String getAuthority() {
        return this.getName();
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }

        final Role role = (Role) o;

        return !((
                this.getName() != null
                ? !this.getName().equals(role.getName())
                : role.getName() != null
            ) || (
                this.getDescription() != null
                ? !this.getDescription().equals(role.getDescription())
                : role.getDescription() != null
            )
        );
    }

    @Override
    public int hashCode() {
        return (this.getName() != null ? this.getName().hashCode() : 0);
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
            .append( this.getName())
            .append("description", "(" + this.getDescription() + ")")
            .toString();
    }

    public int compareTo(final Object o) {

        // TODO Modify to real compare
        return (equals(o) ? 0 : -1);
    }
}
