import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class WeatherForecastService {
  private readonly wfUrl = `${environment.apiUrl}/best-location`;

  constructor(private httpClient: HttpClient) {}

  getBestLocationForWindsurfing(date: string): Observable<any> {
    return this.httpClient.get<any>(`${this.wfUrl}?date=${date}`);
  }
}
