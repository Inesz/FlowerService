package com.example.servis;

import com.example.model.Flower;
import com.example.repository.FlowerRepository;
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
