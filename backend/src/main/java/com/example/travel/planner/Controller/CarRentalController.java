package com.example.travel.planner.Controller;

import com.example.travel.planner.Service.CarRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/car-rentals")
@CrossOrigin(origins = "*")
public class CarRentalController {
    
    @Autowired
    private CarRentalService carRentalService;
    
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchCarRentals(
            @RequestParam double pickUpLatitude,
            @RequestParam double pickUpLongitude,
            @RequestParam double dropOffLatitude,
            @RequestParam double dropOffLongitude,
            @RequestParam String pickUpTime,
            @RequestParam String dropOffTime,
            @RequestParam(defaultValue = "30") int driverAge,
            @RequestParam(defaultValue = "USD") String currencyCode,
            @RequestParam(defaultValue = "US") String location) {
        
        Map<String, Object> results = carRentalService.searchCarRentals(
                pickUpLatitude, pickUpLongitude, dropOffLatitude, dropOffLongitude,
                pickUpTime, dropOffTime, driverAge, currencyCode, location);
        
        return ResponseEntity.ok(results);
    }
} 