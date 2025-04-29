package com.example.travel.planner.Model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    
    @JsonIgnoreProperties("trips")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @JsonIgnoreProperties("trip")
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<Destination> destinations = new ArrayList<>();
    
    @JsonIgnoreProperties("trip")
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<Accommodation> accommodations = new ArrayList<>();
}
