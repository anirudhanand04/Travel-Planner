package com.example.travel.planner.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.Map;

@Service
public class CarRentalService {
    private static final Logger logger = LoggerFactory.getLogger(CarRentalService.class);
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Value("${rapidapi.key}")
    private String rapidApiKey;
    
    @Value("${rapidapi.host}")
    private String rapidApiHost;
    
    public Map<String, Object> searchCarRentals(
            double pickUpLatitude, 
            double pickUpLongitude,
            double dropOffLatitude, 
            double dropOffLongitude,
            String pickUpTime,
            String dropOffTime,
            int driverAge,
            String currencyCode,
            String location) {
        
        try {
            String url = String.format(
                "https://booking-com15.p.rapidapi.com/api/v1/cars/searchCarRentals?" +
                "pick_up_latitude=%s&pick_up_longitude=%s&drop_off_latitude=%s&drop_off_longitude=%s" +
                "&pick_up_time=%s&drop_off_time=%s&driver_age=%d&currency_code=%s&location=%s",
                pickUpLatitude, pickUpLongitude, dropOffLatitude, dropOffLongitude,
                pickUpTime, dropOffTime, driverAge, currencyCode, location
            );
            
            logger.info("Searching car rentals with URL: {}", url);
            
            Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("x-rapidapi-key", rapidApiKey)
                .addHeader("x-rapidapi-host", rapidApiHost)
                .build();
            
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    logger.error("API call failed with code: {}", response.code());
                    throw new RuntimeException("Failed to search car rentals: " + response.code());
                }
                
                String responseBody = response.body().string();
                logger.debug("Received response: {}", responseBody);
                
                return objectMapper.readValue(responseBody, Map.class);
            }
        } catch (IOException e) {
            logger.error("Error searching car rentals", e);
            throw new RuntimeException("Error searching car rentals", e);
        }
    }
} 