package com.example.travel.planner.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;
import com.example.travel.planner.Model.Trip;
import com.example.travel.planner.Model.User;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUser(User user);
    List<Trip> findByUserAndStartDateAfter(User user, LocalDate date);
}
