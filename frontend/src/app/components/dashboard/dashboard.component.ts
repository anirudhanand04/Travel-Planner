// src/app/components/dashboard/dashboard.component.ts
import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { AuthService } from '../../services/auth.service';
import { Trip, User } from '../../models/models';
import { Router } from '@angular/router';

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
    currentUser: User | null = null;
    trips: Trip[] = [];
    loading = true;

    constructor(
        private apiService: ApiService,
        private authService: AuthService,
        private router: Router
    ) { }

    ngOnInit() {
        this.authService.currentUser.subscribe(user => {
            this.currentUser = user;
            if (user) {
                this.loadTrips();
            }
        });
    }

    loadTrips() {
        if (!this.currentUser?.id) return;

        this.loading = true;
        this.apiService.getUserTrips(this.currentUser.id).subscribe({
            next: (trips) => {
                this.trips = trips;
                this.loading = false;
            },
            error: (error) => {
                console.error('Error loading trips:', error);
                this.loading = false;
            }
        });
    }

    createNewTrip() {
        this.router.navigate(['/trips/new']);
    }

    viewTrip(tripId: number) {
        this.router.navigate(['/trips', tripId]);
    }

    logout() {
        this.authService.logout();
        this.router.navigate(['/login']);
    }
}