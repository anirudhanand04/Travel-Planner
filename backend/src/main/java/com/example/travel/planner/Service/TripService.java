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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TripService {
    private static final Logger logger = LoggerFactory.getLogger(TripService.class);
    
    @Autowired
    private TripRepository tripRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<Trip> findByUser(User user) {
        return tripRepository.findByUser(user);
    }
    
    @Transactional
    public Trip createTrip(Trip trip, Long userId) {
        logger.debug("Attempting to create trip for user ID: {}", userId);
        
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            logger.error("Failed to create trip: User not found with ID: {}", userId);
            throw new RuntimeException("User not found");
        }
        
        User user = userOptional.get();
        logger.debug("Found user: {} (ID: {})", user.getUsername(), user.getId());
        
        trip.setUser(user);
        Trip savedTrip = tripRepository.save(trip);
        logger.info("Successfully created trip with ID: {} for user: {}", savedTrip.getId(), user.getUsername());
        
        return savedTrip;
    }
    
    public Optional<Trip> findById(Long id) {
        return tripRepository.findById(id);
    }
}
