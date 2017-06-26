package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.model.Phone;

/**
 * specific methods for operations with phones
 */
public interface PhoneService extends AbstractService<Phone>{
    public boolean isPhoneExist(String number);
}
