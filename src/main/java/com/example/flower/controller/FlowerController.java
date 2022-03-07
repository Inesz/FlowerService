package com.example.flower.controller;

import com.example.flower.model.Flower;
import com.example.flower.service.FlowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FlowerController {
    private final FlowerService flowerService;

    @GetMapping(path = "/flowers")
    List<Flower> getAllFlowers() {
        return flowerService.findAll();
    }
}
