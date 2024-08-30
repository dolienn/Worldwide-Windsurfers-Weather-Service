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

   http://localhost:8080

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


## Troubleshooting

If you encounter issues, try the following:

1. Verify that Docker and Docker Compose are properly installed and running

2. Check the logs for any errors:

   ```bash
   docker-compose logs

3. Refer to the project's documentation or reach out to the support team for further assistance.
