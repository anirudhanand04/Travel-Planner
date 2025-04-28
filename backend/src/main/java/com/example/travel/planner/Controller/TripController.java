package com.example.travel.planner.Controller;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "*")
public class TripController {
    @Autowired
    private TripService tripService;
    
    @Autowired
    private RecommendationService recommendationService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Trip>> getUserTrips(@PathVariable Long userId) {
        User user = userService.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tripService.findByUser(user));
    }
    
    @PostMapping("/user/{userId}")
    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip, @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tripService.createTrip(trip, userId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        Optional<Trip> trip = tripService.findById(id);
        return trip.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping("/{id}/recommendations")
    public ResponseEntity<List<Destination>> getRecommendations(
            @PathVariable Long id,
            @RequestParam String preferences) {
        Optional<Trip> tripOpt = tripService.findById(id);
        if (!tripOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Trip trip = tripOpt.get();
        List<Destination> recommendations = recommendationService.recommendDestinations(
                trip.getUser(), trip.getStartDate(), trip.getEndDate(), preferences);
        
        return ResponseEntity.ok(recommendations);
    }
}
