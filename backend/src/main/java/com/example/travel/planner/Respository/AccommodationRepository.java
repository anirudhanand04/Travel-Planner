package com.example.travel.planner.Respository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    List<Accommodation> findByTrip(Trip trip);
    List<Accommodation> findByDestination(Destination destination);
}
