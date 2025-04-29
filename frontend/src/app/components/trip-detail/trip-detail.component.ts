import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { RecommendationService } from '../../services/recommendation.service';
import { Trip, Destination, Accommodation, Activity } from '../../models/models';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
    selector: 'app-trip-detail',
    templateUrl: './trip-detail.component.html',
    styleUrls: ['./trip-detail.component.scss']
})
export class TripDetailComponent implements OnInit {
    tripId!: number;
    trip?: Trip;
    destinations: Destination[] = [];
    accommodations: Accommodation[] = [];
    recommendedDestinations: Destination[] = [];
    recommendationForm: FormGroup;
    loading = true;
    recommendationsLoading = false;
    errorMessage = '';
    activeTab = 'overview';

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private apiService: ApiService,
        private recommendationService: RecommendationService,
        private formBuilder: FormBuilder
    ) {
        this.recommendationForm = this.formBuilder.group({
            preferences: ['', Validators.required]
        });
    }

    ngOnInit() {
        this.tripId = +this.route.snapshot.paramMap.get('id')!;
        this.loadTripDetails();
    }

    loadTripDetails() {
        this.loading = true;
        this.apiService.getTripById(this.tripId).subscribe({
            next: (trip) => {
                this.trip = trip;
                this.loadDestinations();
                this.loadAccommodations();
            },
            error: (error) => {
                this.errorMessage = 'Error loading trip details';
                this.loading = false;
            }
        });
    }

    loadDestinations() {
        this.apiService.getTripDestinations(this.tripId).subscribe({
            next: (destinations) => {
                this.destinations = destinations;
                this.loading = false;
            },
            error: (error) => {
                this.errorMessage = 'Error loading destinations';
                this.loading = false;
            }
        });
    }

    loadAccommodations() {
        this.apiService.getTripAccommodations(this.tripId).subscribe({
            next: (accommodations) => {
                this.accommodations = accommodations;
            },
            error: (error) => {
                console.error('Error loading accommodations:', error);
            }
        });
    }

    getRecommendations() {
        if (this.recommendationForm.invalid) {
            return;
        }

        this.recommendationsLoading = true;
        const preferences = this.recommendationForm.controls['preferences'].value;

        this.recommendationService.getDestinationRecommendations(this.tripId, preferences).subscribe({
            next: (destinations) => {
                this.recommendedDestinations = destinations;
                this.recommendationsLoading = false;
            },
            error: (error) => {
                this.errorMessage = 'Error getting recommendations';
                this.recommendationsLoading = false;
            }
        });
    }

    addDestination(destination: Destination) {
        if (!this.trip) return;

        this.apiService.addDestination(destination, this.tripId).subscribe({
            next: (newDestination) => {
                this.destinations.push(newDestination);
                this.recommendedDestinations = this.recommendedDestinations.filter(d =>
                    d.city !== destination.city || d.country !== destination.country);
            },
            error: (error) => {
                this.errorMessage = 'Error adding destination';
            }
        });
    }

    viewDestination(destinationId: number) {
        this.router.navigate(['/destinations', destinationId]);
    }

    changeTab(tab: string) {
        this.activeTab = tab;
    }

    calculateDuration(endDate: string, startDate: string): number {
        const end = new Date(endDate);
        const start = new Date(startDate);
        const diffTime = end.getTime() - start.getTime();
        return Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    }

    goBack() {
        // ... existing code ...
    }
}