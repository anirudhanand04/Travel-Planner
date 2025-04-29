package com.example.travel.planner.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import com.example.travel.planner.Model.Accommodation;
import com.example.travel.planner.Model.Trip;
import com.example.travel.planner.Service.AccommodationService;
import com.example.travel.planner.Service.TripService;

@RestController
@RequestMapping("/api/accommodations")
@CrossOrigin(origins = "*")
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;
    
    @Autowired
    private TripService tripService;
    
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
