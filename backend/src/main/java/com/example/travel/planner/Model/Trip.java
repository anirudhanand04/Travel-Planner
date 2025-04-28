package com.example.travel.planner.Model;

@Entity
@Data
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<Destination> destinations = new ArrayList<>();
    
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<Accommodation> accommodations = new ArrayList<>();
}
