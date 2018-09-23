package com.sinners.demo.repository;

import com.sinners.demo.sin.Sin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SinRepository extends CrudRepository<Sin, Long> {
    List<Sin> findByType(String type);
}
