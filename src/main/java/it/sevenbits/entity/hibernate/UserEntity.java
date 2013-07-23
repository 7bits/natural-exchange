package it.sevenbits.entity.hibernate;

import javax.persistence.*;
import java.util.Set;

/**
 *Класс, представляющий сущность таблицы User а БД
 */
@Entity
@Table(name = "user")
public class UserEntity extends it.sevenbits.entity.User{

    private Long id;
    private Set<AdvertisementEntity> advertisements;

    public UserEntity(){
        super();
    }

    public UserEntity(final String firstName, final String email, final String lastName, final String vkLink, final Long createdDate, final Long updatedDate, final Boolean deleted) {
        super(firstName,email,lastName,vkLink,createdDate,updatedDate,deleted);
    }

    @OneToMany(mappedBy="userEntity",cascade = CascadeType.ALL)
    public Set<AdvertisementEntity> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(Set<AdvertisementEntity> advertisements) {
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
    public String getFirstName(){
        return super.getFirstName();
    }

    @Column(name = "last_name", nullable = false)
    @Override
    public String getLastName(){
        return super.getLastName();
    }

    @Column(name = "email", nullable = false)
    @Override
    public String getEmailName(){
        return super.getEmailName();
    }

    public void setId(Long id) {
        this.id = id;
    }

}
