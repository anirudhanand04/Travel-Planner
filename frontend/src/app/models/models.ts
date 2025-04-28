export interface User {
    id?: number;
    username: string;
    email: string;
    password?: string;
}

export interface Trip {
    id?: number;
    name: string;
    startDate: string;
    endDate: string;
    user?: User;
    destinations?: Destination[];
    accommodations?: Accommodation[];
}

export interface Destination {
    id?: number;
    city: string;
    country: string;
    arrivalDate: string;
    departureDate: string;
    trip?: Trip;
    activities?: Activity[];
}

export interface Accommodation {
    id?: number;
    name: string;
    address: string;
    bookingReference?: string;
    checkIn: string;
    checkOut: string;
    price: number;
    currency: string;
    confirmed: boolean;
    trip?: Trip;
    destination?: Destination;
}

export interface Activity {
    id?: number;
    name: string;
    description: string;
    date: string;
    location: string;
    price: number;
    currency: string;
    destination?: Destination;
}