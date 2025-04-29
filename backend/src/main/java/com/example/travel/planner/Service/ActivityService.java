package com.example.travel.planner.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.example.travel.planner.Model.Activity;
import com.example.travel.planner.Model.Destination;
import com.example.travel.planner.Respository.ActivityRepository;
import com.example.travel.planner.Respository.DestinationRepository;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    
    @Autowired
    private DestinationRepository destinationRepository;
    
    public List<Activity> findByDestination(Destination destination) {
        return activityRepository.findByDestination(destination);
    }
    
    @Transactional
    public Activity addActivity(Activity activity, Long destinationId) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));
        activity.setDestination(destination);
        return activityRepository.save(activity);
    }
}
