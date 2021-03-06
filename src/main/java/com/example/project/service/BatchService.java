package com.example.project.service;

import com.example.project.domain.Batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface BatchService {
    List<Batch> findAll();

    Optional<Batch> findById(int batchId);

    List<Batch> findByFruit(int fruit);
}
