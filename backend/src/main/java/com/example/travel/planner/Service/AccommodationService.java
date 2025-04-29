package com.example.travel.planner.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Async;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import com.example.travel.planner.Model.Accommodation;
import com.example.travel.planner.Model.Trip;
import com.example.travel.planner.Model.Destination;
import com.example.travel.planner.Respository.AccommodationRepository;
import com.example.travel.planner.Respository.TripRepository;
import com.example.travel.planner.Respository.DestinationRepository;

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
