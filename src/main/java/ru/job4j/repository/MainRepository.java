package ru.job4j.repository;

import org.springframework.data.repository.NoRepositoryBean;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

@NoRepositoryBean
public interface MainRepository<T, ID> extends CrudRepository<T, ID> {
    List<T> findAll();
}
