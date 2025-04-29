package com.example.travel.planner.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import com.example.travel.planner.Model.Activity;
import com.example.travel.planner.Model.Destination;
import com.example.travel.planner.Service.ActivityService;
import com.example.travel.planner.Service.DestinationService;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "*")
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    
    @Autowired
    private DestinationService destinationService;
    
    @GetMapping("/destination/{destinationId}")
    public ResponseEntity<List<Activity>> getDestinationActivities(@PathVariable Long destinationId) {
        Optional<Destination> destinationOpt = destinationService.findById(destinationId);
        if (!destinationOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(activityService.findByDestination(destinationOpt.get()));
    }
    
    @PostMapping("/destination/{destinationId}")
    public ResponseEntity<Activity> addActivity(
            @RequestBody Activity activity,
            @PathVariable Long destinationId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(activityService.addActivity(activity, destinationId));
    }
}
