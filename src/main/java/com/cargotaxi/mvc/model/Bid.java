package com.cargotaxi.mvc.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "bids")
public class Bid  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "executor_id")
    private User executor;

    @Column(name = "notes")
    @Size(max = 2000)
    private String notes;

    @Column(name = "date", columnDefinition= "TIMESTAMP WITH TIME ZONE")
    @NotEmpty
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @NotEmpty
    @Column ( name="price", precision = 12, scale = 2)
    private BigDecimal price;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
