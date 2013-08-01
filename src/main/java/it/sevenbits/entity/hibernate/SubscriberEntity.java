package it.sevenbits.entity.hibernate;

import it.sevenbits.entity.Subscriber;

import javax.persistence.*;

@Entity
@Table(name = "subscriber")
public class SubscriberEntity extends Subscriber {
    private Long id;

    public SubscriberEntity(String email) {
        super(email);
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "email",length=200, nullable = false)
    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }
}
