package com.cargotaxi.mvc.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "offers")
public class Offer  implements Serializable {

    private int id;
    private UserCar userCar;
    private String description;
    private BigDecimal price;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_car")
    public UserCar getUserCar() {
        return userCar;
    }

    public void setUserCar(UserCar userCar) {
        this.userCar = userCar;
    }

    @Column(name = "description")
    @Size(max = 2000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    @Column ( name="price", precision = 12, scale = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
