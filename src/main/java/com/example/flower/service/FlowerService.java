package com.example.flower.service;

import com.example.flower.model.Flower;
import com.example.flower.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowerService {
    private final FlowerRepository flowerRepository;

    public List<Flower> findAll() {
        return flowerRepository.findAll();
    }
}
