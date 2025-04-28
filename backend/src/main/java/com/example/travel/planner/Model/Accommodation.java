package com.example.travel.planner.Model;

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
    
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
    
    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;
}
