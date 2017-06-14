package com.cargotaxi.mvc.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @NotEmpty
    @Column(name = "title")
    @Size(max = 200)
    private String title;

    @NotEmpty
    @Column(name = "description")
    @Size(max = 2000)
    private String description;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="order")
    private Set<Bid> bids;

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }

    @Column(name = "date", columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @NotEmpty
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @NotEmpty
    @Column ( name="price", precision = 12, scale = 2)
    private BigDecimal price;

    @NotEmpty
    @Column (name="private", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean privateType;

    @NotEmpty
    @Column (name="finished", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean finished;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isPrivateType() {
        return privateType;
    }

    public void setPrivateType(boolean privateType) {
        this.privateType = privateType;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
