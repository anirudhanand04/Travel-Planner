package com.example.travel.planner.Service;

@Service
public class TripService {
    @Autowired
    private TripRepository tripRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<Trip> findByUser(User user) {
        return tripRepository.findByUser(user);
    }
    
    @Transactional
    public Trip createTrip(Trip trip, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        trip.setUser(user);
        return tripRepository.save(trip);
    }
    
    public Optional<Trip> findById(Long id) {
        return tripRepository.findById(id);
    }
}
