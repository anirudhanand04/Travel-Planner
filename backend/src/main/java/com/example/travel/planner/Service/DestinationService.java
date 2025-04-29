package com.example.travel.planner.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import com.example.travel.planner.Model.Destination;
import com.example.travel.planner.Model.Trip;
import com.example.travel.planner.Respository.DestinationRepository;
import com.example.travel.planner.Respository.TripRepository;

@Service
public class DestinationService {
    @Autowired
    private DestinationRepository destinationRepository;
    
    @Autowired
    private TripRepository tripRepository;
    
    public List<Destination> findByTrip(Trip trip) {
        return destinationRepository.findByTrip(trip);
    }
    
    public Optional<Destination> findById(Long id) {
        return destinationRepository.findById(id);
    }
    
    @Transactional
    public Destination addDestination(Destination destination, Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));
        destination.setTrip(trip);
        return destinationRepository.save(destination);
    }
}
