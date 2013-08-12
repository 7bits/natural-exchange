package it.sevenbits.entity.hibernate;

import it.sevenbits.entity.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 *Класс, представляющий сущность таблицы User а БД
 */
@Entity
@Table(name = "user")
public class UserEntity extends User {

    private Long id;

    private Set<AdvertisementEntity> advertisements;


    public UserEntity() {
        super();
    }



    public UserEntity(
            final String firstName, final String email, final String lastName,
            final String vkLink, final Long createdDate, final Long updatedDate,
            final Boolean deleted, final String password, final String role
    ) {
        super(firstName, email, lastName, vkLink, createdDate, updatedDate, deleted, password, role);
    }

    public UserEntity(final User user) {
        super(
                user.getFirstName(), user.getEmail(), user.getLastName(), user.getVklink(), user.getCreatedDate(),
                user.getUpdateDate(), user.getIsDeleted(), user.getPassword(), user.getRole()
        );
    }

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    public Set<AdvertisementEntity> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(final Set<AdvertisementEntity> advertisements) {
        this.advertisements = advertisements;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(name = "created_date", nullable = false)
    @Override
    public Long getCreatedDate() {
        return super.getCreatedDate();
    }

    @Column(name = "vk_link", nullable = false)
    @Override
    public String getVklink() {
        return super.getVklink();
    }

    @Column(name = "updated_date", nullable = false)
    @Override
    public Long getUpdateDate() {
        return super.getUpdateDate();
    }

    @Column(name = "is_deleted", nullable = false)
    @Override
    public boolean getIsDeleted() {
        return super.getIsDeleted();
    }

    @Column(name = "first_name", nullable = false)
    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Column(name = "last_name", nullable = false)
    @Override
    public String getLastName() {
        return super.getLastName();
    }

    @Column(name = "email", nullable = false)
    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Column(name = "password", nullable = false)
    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Column(name = "role", nullable = false)
    @Override
    public String getRole() {
        return super.getRole();
    }

    public void setId(final Long id) {
        this.id = id;
    }


}
