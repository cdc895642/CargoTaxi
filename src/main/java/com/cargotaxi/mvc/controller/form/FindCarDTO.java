package com.cargotaxi.mvc.controller.form;

import java.math.BigDecimal;

public class FindCarDTO {
    private Integer carTypeId;
    private String dislocation;
    private BigDecimal minCapacity;
    private BigDecimal maxCapacity;
    private Integer minLoad;
    private Integer maxLoad;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    public Integer getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(Integer carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getDislocation() {
        return dislocation;
    }

    public void setDislocation(String dislocation) {
        this.dislocation = dislocation;
    }

    public BigDecimal getMinCapacity() {
        return minCapacity;
    }

    public void setMinCapacity(BigDecimal minCapacity) {
        this.minCapacity = minCapacity;
    }

    public BigDecimal getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(BigDecimal maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Integer getMinLoad() {
        return minLoad;
    }

    public void setMinLoad(Integer minLoad) {
        this.minLoad = minLoad;
    }

    public Integer getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(Integer maxLoad) {
        this.maxLoad = maxLoad;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }
}
