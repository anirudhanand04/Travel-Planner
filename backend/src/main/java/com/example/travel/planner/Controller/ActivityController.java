package com.example.travel.planner.Controller;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "*")
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    
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
