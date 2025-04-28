package com.example.travel.planner.Service;

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
