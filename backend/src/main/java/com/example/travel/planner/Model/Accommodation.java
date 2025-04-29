package com.example.travel.planner.Model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;

@Entity
@Data
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String bookingReference;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Double price;
    private String currency;
    private Boolean confirmed;
    
    @JsonIgnoreProperties("accommodations")
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
    
    @JsonIgnoreProperties("accommodations")
    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;
}
