import { Component, OnInit } from '@angular/core';
import { WeatherForecastService } from '../../service/weather-forecast/weather-forecast.service';
import { Location } from '../../common/location/location';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent implements OnInit {
  bestLocation: Location = new Location('', 0, 0);
  date: Date = new Date();
  minDate: string = '';
  maxDate: string = '';
  isLocation: boolean = true;
  constructor(
    private service: WeatherForecastService,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.setDateRange();
    this.getBestLocationForWindsurfing();
  }

  getBestLocationForWindsurfing() {
    let formattedDate = this.transformDate(this.date);
    if (formattedDate == null) {
      formattedDate = this.transformDate(new Date())!;
    }
    this.service
      .getBestLocationForWindsurfing(formattedDate)
      .subscribe((location) => {
        if (location != null) {
          this.isLocation = true;
          this.bestLocation = location;
        } else {
          this.isLocation = false;
        }
      });
  }

  setDateRange() {
    const today = new Date();
    const maxDate = new Date(today);
    maxDate.setDate(today.getDate() + 15);

    this.minDate = this.transformDate(today)!;
    this.maxDate = this.transformDate(maxDate)!;
  }

  transformDate(date: Date) {
    return this.datePipe.transform(date, 'yyyy-MM-dd');
  }
}
