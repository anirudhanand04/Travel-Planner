package com.example.travel.planner.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.travel.planner.Model.Accommodation;
import com.example.travel.planner.Model.Trip;
import com.example.travel.planner.Model.Destination;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    List<Accommodation> findByTrip(Trip trip);
    List<Accommodation> findByDestination(Destination destination);
}
