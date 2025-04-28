package com.example.travel.planner.Respository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByDestination(Destination destination);
}
