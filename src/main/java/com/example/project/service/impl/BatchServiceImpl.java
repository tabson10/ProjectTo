package com.example.project.service.impl;

import com.example.project.domain.Batch;
import com.example.project.repository.BatchRepository;
import com.example.project.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BatchServiceImpl implements BatchService {

    @Autowired
    private BatchRepository batchRepository;

    @Override
    public List<Batch> findAll() {
        List<Batch> batchList = (List<Batch>) batchRepository.findAll();
        List<Batch> activeBatchList = new ArrayList<>();

        for(Batch batch: batchList) {
            activeBatchList.add(batch);
        }

        return activeBatchList;
    }

    @Override
    public Optional<Batch> findById(int batchId) {
        return batchRepository.findById(batchId);
    }

    @Override
    public List<Batch> findByFruit(int fruit) {
        List<Batch> batchList = batchRepository.findByFruit(fruit);

        List<Batch> activeBatchList = new ArrayList<>();

        for(Batch batch: batchList) {
            activeBatchList.add(batch);
        }

        return activeBatchList;
    }
}
