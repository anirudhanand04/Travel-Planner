import { Component } from '@angular/core';

@Component({
    selector: 'app-root',
    template: `
    <div class="app-container">
      <router-outlet></router-outlet>
    </div>
  `,
    styles: [`
    .app-container {
      min-height: 100vh;
      padding: 0;
      margin: 0;
    }
  `]
})
export class AppComponent {
    title = 'Travel Planner';
}