package com.example.project.service.impl;

import com.example.project.domain.Batch;
import com.example.project.repository.BatchRepository;
import com.example.project.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchServiceImpl implements BatchService {

    @Autowired
    private BatchRepository batchRepository;

    @Override
    public List<Batch> findAll() {
        return (List<Batch>) batchRepository.findAll();
    }
}
