package com.example.travel.planner.Service;

@Service
public class DestinationService {
    @Autowired
    private DestinationRepository destinationRepository;
    
    @Autowired
    private TripRepository tripRepository;
    
    public List<Destination> findByTrip(Trip trip) {
        return destinationRepository.findByTrip(trip);
    }
    
    @Transactional
    public Destination addDestination(Destination destination, Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        destination.setTrip(trip);
        return destinationRepository.save(destination);
    }
}
