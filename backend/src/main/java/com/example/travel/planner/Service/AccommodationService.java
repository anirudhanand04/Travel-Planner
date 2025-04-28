package com.example.travel.planner.Service;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepository accommodationRepository;
    
    @Autowired
    private TripRepository tripRepository;
    
    @Autowired
    private DestinationRepository destinationRepository;
    
    public List<Accommodation> findByTrip(Trip trip) {
        return accommodationRepository.findByTrip(trip);
    }
    
    @Transactional
    @Async
    public CompletableFuture<Accommodation> bookAccommodation(Accommodation accommodation, Long tripId, Long destinationId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));
        
        accommodation.setTrip(trip);
        accommodation.setDestination(destination);
        accommodation.setConfirmed(false);
        
        // Simulate external booking API call
        try {
            Thread.sleep(2000);
            accommodation.setConfirmed(true);
            accommodation.setBookingReference("BK" + System.currentTimeMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return CompletableFuture.completedFuture(accommodationRepository.save(accommodation));
    }
}
