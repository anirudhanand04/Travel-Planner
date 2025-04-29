package com.example.travel.planner.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.travel.planner.Model.Activity;
import com.example.travel.planner.Model.Destination;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByDestination(Destination destination);
}
