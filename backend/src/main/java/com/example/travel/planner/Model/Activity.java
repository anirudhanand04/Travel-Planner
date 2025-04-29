package com.example.travel.planner.Model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;

@Entity
@Data
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate date;
    private String location;
    private Double price;
    private String currency;
    
    @JsonIgnoreProperties("activities")
    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;
}