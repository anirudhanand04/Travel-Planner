import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { AuthService } from '../../services/auth.service';
import { Trip } from '../../models/models';

@Component({
    selector: 'app-trip-form',
    templateUrl: './trip-form.component.html',
    styleUrls: ['./trip-form.component.scss']
})
export class TripFormComponent implements OnInit {
    tripForm: FormGroup;
    isEditMode = false;
    tripId?: number;
    loading = false;
    errorMessage = '';

    constructor(
        private formBuilder: FormBuilder,
        private apiService: ApiService,
        private authService: AuthService,
        private router: Router,
        private route: ActivatedRoute
    ) {
        this.tripForm = this.formBuilder.group({
            name: ['', Validators.required],
            startDate: ['', Validators.required],
            endDate: ['', Validators.required]
        }, {
            validator: this.dateRangeValidator
        });
    }

    ngOnInit() {
        const id = this.route.snapshot.paramMap.get('id');
        if (id && id !== 'new') {
            this.isEditMode = true;
            this.tripId = +id;
            this.loadTripDetails(this.tripId);
        }
    }

    dateRangeValidator(formGroup: FormGroup) {
        const start = formGroup.controls['startDate'].value;
        const end = formGroup.controls['endDate'].value;

        if (start && end && new Date(start) > new Date(end)) {
            formGroup.controls['endDate'].setErrors({ dateRange: true });
            return { dateRange: true };
        } else {
            return null;
        }
    }

    loadTripDetails(id: number) {
        this.loading = true;
        this.apiService.getTripById(id).subscribe({
            next: (trip) => {
                this.tripForm.patchValue({
                    name: trip.name,
                    startDate: trip.startDate,
                    endDate: trip.endDate
                });
                this.loading = false;
            },
            error: (error) => {
                this.errorMessage = 'Error loading trip details';
                this.loading = false;
            }
        });
    }

    onSubmit() {
        if (this.tripForm.invalid) {
            return;
        }

        this.loading = true;
        const trip: Trip = {
            name: this.tripForm.controls['name'].value,
            startDate: this.tripForm.controls['startDate'].value,
            endDate: this.tripForm.controls['endDate'].value
        };

        const currentUser = this.authService.currentUserValue;
        if (!currentUser?.id) {
            this.errorMessage = 'You must be logged in';
            this.loading = false;
            return;
        }

        if (this.isEditMode && this.tripId) {
            // Update trip logic would go here
            // For now, just redirect to trip detail
            this.router.navigate(['/trips', this.tripId]);
        } else {
            this.apiService.createTrip(trip, currentUser.id).subscribe({
                next: (newTrip) => {
                    this.router.navigate(['/trips', newTrip.id]);
                },
                error: (error) => {
                    this.errorMessage = 'Error creating trip';
                    this.loading = false;
                }
            });
        }
    }
}