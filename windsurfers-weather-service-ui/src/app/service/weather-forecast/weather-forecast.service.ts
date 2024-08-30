import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WeatherForecastService {
  private readonly wfUrl =
    'http://localhost:8080/api/windsurfing/best-location';

  constructor(private httpClient: HttpClient) {}

  getBestLocationForWindsurfing(date: string): Observable<any> {
    return this.httpClient.get<any>(`${this.wfUrl}?date=${date}`);
  }
}
