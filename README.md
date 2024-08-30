# Worldwide Windsurfers Weather Service

## Overview

Weather Service is an application designed to help windsurfing enthusiasts find the best cities around the world for their sport. By analyzing current weather conditions, including wind speed and temperature, the app provides precise and up-to-date information to help users identify ideal locations for their water sports adventures.
Built with a robust tech stack including **Spring Boot**, **Spring Security**, **Hibernate**, **Angular**, **TypeScript**, **HTML**, **SCSS**, **Docker**, Weather Service combines efficiency and user satisfaction.


## Features

- **Current Weather Analysis**: Get real-time data on wind speed and temperature for various cities globally.
- **Optimal Location Identification**: Quickly find cities that meet your windsurfing conditions.
- **User-Friendly Interface**: Easily navigate through weather data and city information.

The platform emphasizes user experience, leveraging **JUnit** and **Mockito** for testing, **Docker** for containerization.

## API Integration
Weather Service utilizes the Weatherbit API to fetch accurate and up-to-date weather information. This powerful API provides detailed weather data, ensuring users have the most relevant and precise information for their windsurfing needs.

# Project Setup

## Prerequisites

Ensure the following tools are installed on your system:

- **Docker**: [Install Docker](https://docs.docker.com/get-docker/)
- **Docker Compose**: [Install Docker Compose](https://docs.docker.com/compose/install/)
- **Maven**: [Install Maven](https://maven.apache.org/install.html)
- **Node.js and npm**: [Install Node.js and npm](https://nodejs.org/)
- **Angular CLI** (globally installed): Install using npm:
  
  ```bash
  npm install -g @angular/cli

1. **Clone the Repository**

   Clone the repository and navigate into the project directory:

   ```bash
   git clone https://github.com/dolienn/YarnMiracles.git
   cd yarn-miracles

2. **Configure Weatherbit API Key**

   To use the Weatherbit API, you need to:

   - Create an account on the Weatherbit website.
   - Once logged in, navigate to the API section and generate your API key.
   - Edit a `.env` file in the root directory of the project and edit your API key:

  
   ```bash
   WEATHERBIT_API_KEY=your_api_key_here

3. **Start Docker Services**

   Ensure Docker and Docker Compose are installed and running. Start the services with:

   ```bash
   docker-compose up --build

4. **Access the Application**

   Once the Docker services are up and running, you can visit the application at:

   http://localhost:80

## Available API Endpoints

The backend provides the following API endpoints for fetching windsurfing locations:

- **Get the best windsurfing location**:
 
  ```bash
  http://localhost:8080/api/windsurfing/best-location

- **Get all windsurfing locations**:
 
  ```bash
  http://localhost:8080/api/windsurfing/locations

## Access the Live Application
You can explore the Worldwide Windsurfers Weather Service online:

[🌐 Live App Link](https://windsurfers-weather-service-b435c5ef89a3.herokuapp.com/)

Visit the web application to:

- **Check real-time weather data for windsurfing.**
- **Discover the best locations for your next adventure.**
- **Access all features conveniently from your browser!**

## Verifying Application Functionality
To ensure the Worldwide Windsurfers Weather Service is working correctly after setup, follow these steps:
- **Access the API Endpoints:**
  - Open a browser or use tools like Postman/cURL to test the following endpoints:
    - `GET /api/windsurfing/best-location`: Navigate to `http://localhost:8080/api/windsurfing/best-location?date=your_chosen_date` to fetch the city with the best windsurfing conditions. Expected response: JSON data with the city name, wind speed, and temperature.
    -  `GET /api/windsurfing/locations`: Go to `http://localhost:8080/api/windsurfing/locations` to get a list of cities and their current weather data. Expected response: A JSON array containing multiple cities, each with weather information (e.g., wind speed, temperature).
   
- **Frontend Verification:**
  - Once the backend and frontend are running, open a browser and visit `http://localhost:80` (for local setup) or the live version at: [🌐 Live App](https://windsurfers-weather-service-b435c5ef89a3.herokuapp.com/)
  - Verify that the app displays the current weather conditions and allows you to select different dates. After choosing a date, the application should show the best location for windsurfing on that specific date based on the forecast data.
 
- **Test Basic Functionality:**
  - Best Location: Check if the "Best Location" feature identifies a suitable city for windsurfing based on real-time or future date weather data.
  - Location Search: Search or browse through cities to ensure accurate weather data is being fetched and displayed for the selected dates.
  - Responsive UI: Test the app across different devices or screen sizes to ensure a smooth and adaptive user experience.

By following these steps, you can confirm that the application is working as expected both locally and online.

## Troubleshooting

If you encounter issues, try the following:

1. Verify that Docker and Docker Compose are properly installed and running

2. Check the logs for any errors:

   ```bash
   docker-compose logs

3. Refer to the project's documentation or reach out to the support team for further assistance.
