package com.example.project.repository;

import com.example.project.domain.Batch;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BatchRepository extends CrudRepository<Batch, Integer> {
    List<Batch> findByFruit(int fruit);
}
