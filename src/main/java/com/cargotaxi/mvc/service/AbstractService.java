package com.cargotaxi.mvc.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * base interface for service layer
 */
public interface AbstractService<E> {
    public E findById(int id);
    public List<E> findAll();
    public E save(E item);
    public E update(E item);
    public void deleteById(int id);
    public void delete(E item);
    public void setRepository(JpaRepository<E, Integer> repository);
}
