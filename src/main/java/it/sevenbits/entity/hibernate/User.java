package it.sevenbits.entity.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *Класс, представляющий сущность таблицы User а БД
 */
@Entity
@Table(name = "user")
public class User extends it.sevenbits.entity.User{

    private Integer id;

    public User() {
        super();
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
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

    public void setId(Integer id) {
        this.id = id;
    }

}
