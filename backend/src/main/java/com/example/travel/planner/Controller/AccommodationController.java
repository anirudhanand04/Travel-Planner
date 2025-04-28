package com.example.travel.planner.Controller;

@RestController
@RequestMapping("/api/accommodations")
@CrossOrigin(origins = "*")
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;
    
    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<Accommodation>> getTripAccommodations(@PathVariable Long tripId) {
        Optional<Trip> tripOpt = tripService.findById(tripId);
        if (!tripOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(accommodationService.findByTrip(tripOpt.get()));
    }
    
    @PostMapping("/trip/{tripId}/destination/{destinationId}")
    public ResponseEntity<CompletableFuture<Accommodation>> bookAccommodation(
            @RequestBody Accommodation accommodation,
            @PathVariable Long tripId,
            @PathVariable Long destinationId) {
        CompletableFuture<Accommodation> future = accommodationService.bookAccommodation(
                accommodation, tripId, destinationId);
        return ResponseEntity.accepted().body(future);
    }
}
