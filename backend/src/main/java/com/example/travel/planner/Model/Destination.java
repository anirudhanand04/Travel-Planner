package com.example.travel.planner.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String country;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
    
    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
    private List<Activity> activities = new ArrayList<>();
}
