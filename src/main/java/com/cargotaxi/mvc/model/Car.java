package com.cargotaxi.mvc.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "car_catalog")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "car_type")
    private CarType carType;

    @NotNull
    @Column(name = "load")
    //@Size(min = 1, max = 100000)
    private Integer load;

    @NotNull
    @Column ( name="capacity")
    @Digits(integer=10, fraction=2)
    //@Size(min = 1, max = 100)
    private BigDecimal capacity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public Integer getLoad() {
        return load;
    }

    public void setLoad(Integer load) {
        this.load = load;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }
}
