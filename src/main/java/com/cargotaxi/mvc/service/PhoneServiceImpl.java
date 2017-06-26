package com.cargotaxi.mvc.service;

import com.cargotaxi.mvc.dao.PhoneRepository;
import com.cargotaxi.mvc.model.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class PhoneServiceImpl extends AbstractServiceImpl<Phone> implements
        PhoneService{
    @Autowired
    private PhoneRepository phoneRepository;

    @PostConstruct
    public void init(){
        setRepository(phoneRepository);
    }

    @Override
    public boolean isPhoneExist(String number) {
        Phone phone=phoneRepository.findByNumber(number);
        return phone!=null;
    }
}
