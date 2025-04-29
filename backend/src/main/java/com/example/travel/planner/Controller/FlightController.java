package com.example.travel.planner.Controller;

import com.example.travel.planner.Model.FlightSearchResponse;
import com.example.travel.planner.Service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "*")
public class FlightController {
    
    @Autowired
    private FlightService flightService;
    
    @GetMapping("/search")
    public ResponseEntity<FlightSearchResponse> searchFlights(
            @RequestParam String fromId,
            @RequestParam String toId,
            @RequestParam(defaultValue = "0") Integer stops,
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "1") Integer adults,
            @RequestParam(defaultValue = "0") Integer children,
            @RequestParam(defaultValue = "BEST") String sort,
            @RequestParam(defaultValue = "ECONOMY") String cabinClass,
            @RequestParam(defaultValue = "USD") String currencyCode) {
        
        FlightSearchResponse results = flightService.searchFlights(
                fromId, toId, stops, pageNo, adults, children, sort, cabinClass, currencyCode);
        
        return ResponseEntity.ok(results);
    }
} 