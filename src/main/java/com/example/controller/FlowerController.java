package com.example.controller;

import com.example.model.Flower;
import com.example.servis.FlowerService;
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
