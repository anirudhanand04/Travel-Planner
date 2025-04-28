package com.example.travel.planner.Service;

import com.travelplanner.model.Activity;
import com.travelplanner.model.Destination;
import com.travelplanner.model.Trip;
import com.travelplanner.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class RecommendationService {
    
    private final RestTemplate restTemplate;
    
    @Value("${tripadvisor.api.key}")
    private String apiKey;
    
    public RecommendationService() {
        this.restTemplate = new RestTemplate();
    }
    
    public List<Destination> recommendDestinations(User user, LocalDate startDate, LocalDate endDate, String preferences) {
        // In a real application, this would call an external API
        // For demonstration, we'll create some mock destinations
        
        // Simulate API call
        // HttpHeaders headers = new HttpHeaders();
        // headers.set("X-API-KEY", apiKey);
        // HttpEntity<String> entity = new HttpEntity<>(headers);
        // ResponseEntity<Map> response = restTemplate.exchange(
        //     "https://api.example.com/recommendations?preferences=" + preferences, 
        //     HttpMethod.GET, 
        //     entity, 
        //     Map.class
        // );
        
        List<Destination> recommendations = new ArrayList<>();
        
        // Mock destinations based on preferences
        if (preferences.toLowerCase().contains("beach")) {
            Destination destination = new Destination();
            destination.setCity("Bali");
            destination.setCountry("Indonesia");
            destination.setArrivalDate(startDate);
            destination.setDepartureDate(startDate.plusDays(7));
            recommendations.add(destination);
            
            destination = new Destination();
            destination.setCity("Cancun");
            destination.setCountry("Mexico");
            destination.setArrivalDate(startDate.plusDays(8));
            destination.setDepartureDate(endDate);
            recommendations.add(destination);
        } else if (preferences.toLowerCase().contains("mountain")) {
            Destination destination = new Destination();
            destination.setCity("Interlaken");
            destination.setCountry("Switzerland");
            destination.setArrivalDate(startDate);
            destination.setDepartureDate(startDate.plusDays(5));
            recommendations.add(destination);
            
            destination = new Destination();
            destination.setCity("Banff");
            destination.setCountry("Canada");
            destination.setArrivalDate(startDate.plusDays(6));
            destination.setDepartureDate(endDate);
            recommendations.add(destination);
        } else {
            Destination destination = new Destination();
            destination.setCity("Paris");
            destination.setCountry("France");
            destination.setArrivalDate(startDate);
            destination.setDepartureDate(startDate.plusDays(4));
            recommendations.add(destination);
            
            destination = new Destination();
            destination.setCity("Rome");
            destination.setCountry("Italy");
            destination.setArrivalDate(startDate.plusDays(5));
            destination.setDepartureDate(startDate.plusDays(8));
            recommendations.add(destination);
            
            destination = new Destination();
            destination.setCity("Barcelona");
            destination.setCountry("Spain");
            destination.setArrivalDate(startDate.plusDays(9));
            destination.setDepartureDate(endDate);
            recommendations.add(destination);
        }
        
        return recommendations;
    }
    
    public List<Activity> recommendActivities(Destination destination) {
        // Mock activities based on destination
        List<Activity> activities = new ArrayList<>();
        
        if ("Bali".equalsIgnoreCase(destination.getCity())) {
            activities.add(createActivity("Uluwatu Temple Visit", destination, "Explore the beautiful sea temple"));
            activities.add(createActivity("Surfing Lesson", destination, "Learn to surf at Kuta Beach"));
            activities.add(createActivity("Ubud Monkey Forest", destination, "Visit the sacred monkey forest sanctuary"));
        } else if ("Paris".equalsIgnoreCase(destination.getCity())) {
            activities.add(createActivity("Eiffel Tower", destination, "Visit the iconic Eiffel Tower"));
            activities.add(createActivity("Louvre Museum", destination, "Explore one of the world's largest art museums"));
            activities.add(createActivity("Seine River Cruise", destination, "Enjoy a relaxing cruise on the Seine River"));
        } else if ("Rome".equalsIgnoreCase(destination.getCity())) {
            activities.add(createActivity("Colosseum Tour", destination, "Visit the ancient Roman amphitheater"));
            activities.add(createActivity("Vatican Museums", destination, "Explore the vast collection of art and artifacts"));
            activities.add(createActivity("Roman Forum", destination, "Walk through the heart of ancient Rome"));
        }
        
        return activities;
    }
    
    private Activity createActivity(String name, Destination destination, String description) {
        Activity activity = new Activity();
        activity.setName(name);
        activity.setDescription(description);
        activity.setDestination(destination);
        activity.setDate(destination.getArrivalDate().plusDays(ThreadLocalRandom.current().nextInt(
                1, (int) (destination.getDepartureDate().toEpochDay() - destination.getArrivalDate().toEpochDay()))));
        activity.setPrice(ThreadLocalRandom.current().nextDouble(20, 100));
        activity.setCurrency("USD");
        return activity;
    }
}
