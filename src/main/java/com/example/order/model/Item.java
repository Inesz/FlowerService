package com.example.order.model;

import com.example.flower.model.Flower;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Item in order.
 */
@Entity
@Getter
@Setter
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    private Flower flower;
    private Integer quantity;

    @ManyToOne()
    private Order order;
}
