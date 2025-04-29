package com.example.travel.planner.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.travel.planner.Model.Destination;
import com.example.travel.planner.Model.Trip;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByTrip(Trip trip);
    List<Destination> findByCityContainingIgnoreCase(String city);
}
