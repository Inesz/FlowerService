package com.example.stats.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "flowers_history")
public class FlowersHistory {
    String oldName;
    String newName;
    Date date;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
