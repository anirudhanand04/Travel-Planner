package com.example.travel.planner.Controller;

@RestController
@RequestMapping("/api/destinations")
@CrossOrigin(origins = "*")
public class DestinationController {
    @Autowired
    private DestinationService destinationService;
    
    @Autowired
    private RecommendationService recommendationService;
    
    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<Destination>> getTripDestinations(@PathVariable Long tripId) {
        Optional<Trip> tripOpt = tripService.findById(tripId);
        if (!tripOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(destinationService.findByTrip(tripOpt.get()));
    }
    
    @PostMapping("/trip/{tripId}")
    public ResponseEntity<Destination> addDestination(
            @RequestBody Destination destination,
            @PathVariable Long tripId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(destinationService.addDestination(destination, tripId));
    }
    
    @GetMapping("/{id}/activities/recommendations")
    public ResponseEntity<List<Activity>> getActivityRecommendations(@PathVariable Long id) {
        Optional<Destination> destinationOpt = destinationService.findById(id);
        if (!destinationOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        List<Activity> recommendations = recommendationService.recommendActivities(destinationOpt.get());
        return ResponseEntity.ok(recommendations);
    }
}
