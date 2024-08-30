import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { WeatherForecastService } from './service/weather-forecast/weather-forecast.service';
import { FormsModule } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { LoaderComponent } from './components/loader/loader.component';

@NgModule({
  declarations: [AppComponent, HomeComponent, LoaderComponent],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule],
  providers: [WeatherForecastService, DatePipe],
  bootstrap: [AppComponent],
})
export class AppModule {}
