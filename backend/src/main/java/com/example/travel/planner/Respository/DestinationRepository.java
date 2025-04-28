package com.example.travel.planner.Respository;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByTrip(Trip trip);
    List<Destination> findByCityContainingIgnoreCase(String city);
}
