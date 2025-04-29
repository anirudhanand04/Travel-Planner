package com.example.travel.planner.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import com.example.travel.planner.Model.Trip;
import com.example.travel.planner.Model.User;
import com.example.travel.planner.Respository.TripRepository;
import com.example.travel.planner.Respository.UserRepository;

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
