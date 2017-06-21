package com.cargotaxi.mvc.service;

/**
 * specific methods for operations with phones
 */
public interface PhoneService<E> extends AbstractService<E>{
    public boolean isPhoneExist(String number);
}
