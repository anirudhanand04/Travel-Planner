import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';
import { Destination, Activity } from '../models/models';

@Injectable({
    providedIn: 'root'
})
export class RecommendationService {
    constructor(private apiService: ApiService) { }

    getDestinationRecommendations(tripId: number, preferences: string): Observable<Destination[]> {
        return this.apiService.getRecommendations(tripId, preferences);
    }

    getActivityRecommendations(destinationId: number): Observable<Activity[]> {
        return this.apiService.getActivityRecommendations(destinationId);
    }
}
