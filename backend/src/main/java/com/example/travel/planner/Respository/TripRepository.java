package com.example.travel.planner.Respository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUser(User user);
    List<Trip> findByUserAndStartDateAfter(User user, LocalDate date);
}
