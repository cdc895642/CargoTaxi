package com.cargotaxi.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * base implementation for AbstractService interface
 */

public abstract class AbstractServiceImpl<E> implements AbstractService<E> {
    JpaRepository<E, Integer> repository;

    @Override
    public E findById(int id) {
        return repository.findOne(id);
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }

    @Override
    public E save(E item) {
        return repository.save(item);
    }

    @Override
    public E update(E item) {
        return repository.save(item);
    }

    @Override
    public void deleteById(int id) {
        repository.delete(id);
    }

    @Override
    public void delete(E item) {
        repository.delete(item);
    }

    @Override
    public void setRepository(JpaRepository<E, Integer> repository) {
        this.repository=repository;
    }
}
