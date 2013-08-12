package it.sevenbits.entity.hibernate;

import it.sevenbits.entity.Subscriber;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Subscriber entity class for hibernate
 */
@Entity
@Table(name = "subscriber")
public class SubscriberEntity extends Subscriber {
    private Long id;

    public SubscriberEntity() {
    }

    public SubscriberEntity(final String email) {
        super(email);
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "email", length = 200, nullable = false)
    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(final String email) {
        super.setEmail(email);
    }
}
