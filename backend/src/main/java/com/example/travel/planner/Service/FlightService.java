package com.example.travel.planner.Service;

import com.example.travel.planner.Model.FlightSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class FlightService {

    @Value("${flight.api.key}")
    private String apiKey;

    @Value("${flight.api.host}")
    private String apiHost;

    @Value("${flight.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public FlightSearchResponse searchFlights(
            String fromId,
            String toId,
            Integer stops,
            Integer pageNo,
            Integer adults,
            Integer children,
            String sort,
            String cabinClass,
            String currencyCode) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", apiKey);
        headers.set("X-RapidAPI-Host", apiHost);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("fromId", fromId)
                .queryParam("toId", toId)
                .queryParam("stops", stops)
                .queryParam("pageNo", pageNo)
                .queryParam("adults", adults)
                .queryParam("children", children)
                .queryParam("sort", sort)
                .queryParam("cabinClass", cabinClass)
                .queryParam("currencyCode", currencyCode);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<FlightSearchResponse> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                FlightSearchResponse.class);

        return response.getBody();
    }
} 