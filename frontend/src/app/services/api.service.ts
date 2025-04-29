import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User, Trip, Destination, Accommodation, Activity } from '../models/models';

@Injectable({
    providedIn: 'root'
})
export class ApiService {
    private apiUrl = 'http://localhost:8080/api';

    constructor(private http: HttpClient) { }

    // User endpoints
    createUser(user: User): Observable<User> {
        return this.http.post<User>(`${this.apiUrl}/users`, user);
    }

    getUserByEmail(email: string): Observable<User> {
        return this.http.get<User>(`${this.apiUrl}/users/${email}`);
    }

    // Trip endpoints
    getUserTrips(userId: number): Observable<Trip[]> {
        return this.http.get<Trip[]>(`${this.apiUrl}/trips/user/${userId}`);
    }

    createTrip(trip: Trip, userId: number): Observable<Trip> {
        return this.http.post<Trip>(`${this.apiUrl}/trips/user/${userId}`, trip);
    }

    getTripById(id: number): Observable<Trip> {
        return this.http.get<Trip>(`${this.apiUrl}/trips/${id}`);
    }

    getRecommendations(tripId: number, preferences: string): Observable<Destination[]> {
        return this.http.post<Destination[]>(
            `${this.apiUrl}/trips/${tripId}/recommendations?preferences=${preferences}`, {});
    }

    // Destination endpoints
    getTripDestinations(tripId: number): Observable<Destination[]> {
        return this.http.get<Destination[]>(`${this.apiUrl}/destinations/trip/${tripId}`);
    }

    addDestination(destination: Destination, tripId: number): Observable<Destination> {
        return this.http.post<Destination>(`${this.apiUrl}/destinations/trip/${tripId}`, destination);
    }

    getActivityRecommendations(destinationId: number): Observable<Activity[]> {
        return this.http.get<Activity[]>(`${this.apiUrl}/destinations/${destinationId}/activities/recommendations`);
    }

    getDestinationById(id: number): Observable<Destination> {
        return this.http.get<Destination>(`${this.apiUrl}/destinations/${id}`);
    }

    // Accommodation endpoints
    getTripAccommodations(tripId: number): Observable<Accommodation[]> {
        return this.http.get<Accommodation[]>(`${this.apiUrl}/accommodations/trip/${tripId}`);
    }

    bookAccommodation(accommodation: Accommodation, tripId: number, destinationId: number): Observable<Accommodation> {
        return this.http.post<Accommodation>(
            `${this.apiUrl}/accommodations/trip/${tripId}/destination/${destinationId}`, accommodation);
    }

    // Activity endpoints
    getDestinationActivities(destinationId: number): Observable<Activity[]> {
        return this.http.get<Activity[]>(`${this.apiUrl}/activities/destination/${destinationId}`);
    }

    addActivity(activity: Activity, destinationId: number): Observable<Activity> {
        return this.http.post<Activity>(`${this.apiUrl}/activities/destination/${destinationId}`, activity);
    }
}