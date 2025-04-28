import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { RecommendationService } from '../../services/recommendation.service';
import { Destination, Activity, Accommodation } from '../../models/models';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
    selector: 'app-destination-detail',
    templateUrl: './destination-detail.component.html',
    styleUrls: ['./destination-detail.component.scss']
})
export class DestinationDetailComponent implements OnInit {
    destinationId!: number;
    destination?: Destination;
    activities: Activity[] = [];
    recommendedActivities: Activity[] = [];
    accommodations: Accommodation[] = [];
    accommodationForm: FormGroup;
    loading = true;
    errorMessage = '';
    activeTab = 'overview';

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private apiService: ApiService,
        private recommendationService: RecommendationService,
        private formBuilder: FormBuilder
    ) {
        this.accommodationForm = this.formBuilder.group({
            name: ['', Validators.required],
            address: ['', Validators.required],
            checkIn: ['', Validators.required],
            checkOut: ['', Validators.required],
            price: [0, [Validators.required, Validators.min(0)]],
            currency: ['USD', Validators.required]
        });
    }

    ngOnInit() {
        this.destinationId = +this.route.snapshot.paramMap.get('id')!;
        this.loadDestinationDetails();
    }

    loadDestinationDetails() {
        this.loading = true;
        this.apiService.getDestinationById(this.destinationId).subscribe({
            next: (destination) => {
                this.destination = destination;
                this.loadActivities();
                this.loadAccommodations();
                this.getActivityRecommendations();
            },
            error: (error) => {
                this.errorMessage = 'Error loading destination details';
                this.loading = false;
            }
        });
    }

    loadActivities() {
        this.apiService.getDestinationActivities(this.destinationId).subscribe({
            next: (activities) => {
                this.activities = activities;
                this.loading = false;
            },
            error: (error) => {
                console.error('Error loading activities:', error);
                this.loading = false;
            }
        });
    }

    loadAccommodations() {
        if (!this.destination?.trip?.id) return;

        this.apiService.getTripAccommodations(this.destination.trip.id).subscribe({
            next: (accommodations) => {
                this.accommodations = accommodations.filter(a =>
                    a.destination?.id === this.destinationId);
            },
            error: (error) => {
                console.error('Error loading accommodations:', error);
            }
        });
    }

    getActivityRecommendations() {
        this.recommendationService.getActivityRecommendations(this.destinationId).subscribe({
            next: (activities) => {
                this.recommendedActivities = activities;
            },
            error: (error) => {
                console.error('Error getting activity recommendations:', error);
            }
        });
    }

    addActivity(activity: Activity) {
        this.apiService.addActivity(activity, this.destinationId).subscribe({
            next: (newActivity) => {
                this.activities.push(newActivity);
                this.recommendedActivities = this.recommendedActivities.filter(a =>
                    a.name !== activity.name);
            },
            error: (error) => {
                this.errorMessage = 'Error adding activity';
            }
        });
    }

    bookAccommodation() {
        if (this.accommodationForm.invalid || !this.destination?.trip?.id) {
            return;
        }

        const accommodation: Accommodation = {
            name: this.accommodationForm.controls['name'].value,
            address: this.accommodationForm.controls['address'].value,
            checkIn: this.accommodationForm.controls['checkIn'].value,
            checkOut: this.accommodationForm.controls['checkOut'].value,
            price: this.accommodationForm.controls['price'].value,
            currency: this.accommodationForm.controls['currency'].value,
            confirmed: false
        };

        this.apiService.bookAccommodation(
            accommodation,
            this.destination.trip.id,
            this.destinationId
        ).subscribe({
            next: (newAccommodation) => {
                this.accommodations.push(newAccommodation);
                this.accommodationForm.reset({
                    currency: 'USD'
                });
            },
            error: (error) => {
                this.errorMessage = 'Error booking accommodation';
            }
        });
    }

    changeTab(tab: string) {
        this.activeTab = tab;
    }

    goBack() {
        if (this.destination?.trip?.id) {
            this.router.navigate(['/trips', this.destination.trip.id]);
        } else {
            this.router.navigate(['/dashboard']);
        }
    }
}