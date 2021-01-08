package com.example.project.repository;

import com.example.project.domain.Batch;
import org.springframework.data.repository.CrudRepository;

public interface BatchRepository extends CrudRepository<Batch, Integer> {
}
