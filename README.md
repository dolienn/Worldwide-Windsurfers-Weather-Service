# Worldwide Windsurfers Weather Service

## Overview

Weather Service is an application designed to help windsurfing enthusiasts find the best cities around the world for their sport. By analyzing current weather conditions, including wind speed and temperature, the app provides precise and up-to-date information to help users identify ideal locations for their water sports adventures.
Built with a robust tech stack including **Spring Boot**, **Spring Security**, **Hibernate**, **PostgreSQL**, **Liquibase**, **Jenkins**, **Weatherbit API**, **Angular**, **TypeScript**, **HTML**, **SCSS**, **NGINX**, **Docker**, **Docker Compose**, **JUnit**, and **Mockito**. Weather Service combines efficiency and user satisfaction.


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
   - Add a `.env` file in the root directory with the following content:

   ```bash
   # Database Configuration
   DATABASE_URL=jdbc:postgresql://postgres:5432/weather-service
   DATABASE_USERNAME=username
   DATABASE_PASSWORD=password
   DATABASE_PORT=5432

   # Weatherbit Configuration
   WEATHERBIT_API_KEY=your_weatherbit_api_key
   WEATHERBIT_URL=https://api.weatherbit.io/v2.0/forecast/daily

   # Heroku Configuration
   HEROKU_URL=your_heroku_url
   
3. **Create a Heroku Application and Set Environment Variables**

   - Log in to [Heroku](https://dashboard.heroku.com/login) and create a new application.
   - Navigate to the Settings tab of your Heroku app.
   - Under the Config Vars section, add the following environment variables:

   ```bash
   DB_URL=jdbc:postgresql://postgres:5432/weather-service
   DB_USERNAME=username
   DB_PASSWORD=password
   DB_PORT=5432
   WEATHERBIT_API_KEY=your_weatherbit_api_key
   WEATHERBIT_URL=https://api.weatherbit.io/v2.0/forecast/daily

4. **Start Docker Services**

   Ensure Docker and Docker Compose are installed and running. Start the services with:

   ```bash
   docker-compose up --build

5. **Access the Application**

   Once the Docker services are up and running, you can visit the application at:

   http://localhost:80

## Jenkins Integration
Jenkins is used in this project for continuous integration and deployment. The Jenkins pipeline automates the build, test, and deployment process, ensuring that every code change is properly tested and deployed.

### Jenkins Pipeline
The project uses a Jenkins pipeline with the following stages:
- **Build**: Compile the project using Maven.
- **Test**: Run the unit tests to ensure the application works as expected.
- **Deploy**: Deploy the application to Heroku.

### How to Set Up Jenkins for the Project:

1. **Install Jenkins**: If you don’t have Jenkins installed, you can follow the installation instructions [here](https://www.jenkins.io/download/).
2. **Create a New Job in Jenkins**:
   - Go to your Jenkins dashboard and click on **New Item**.
   - Select **Pipeline** and give your job a name, e.g., `windsurfers-weather-service-pipeline`.
3. **Configure SCM**
   - In the **Pipeline** section, change the **Definition** to **Pipeline script from SCM**
   - Choose **SCM** as **Git**.
   - Enter the **Repository URL** pointing to your GitHub repository (e.g., `https://github.com/your_user_name/windsurfers-weather-service.git`).
   - If your repository is private, you'll need to add credentials in Jenkins to allow it to access your GitHub.
4. **Add Required Environment Variables**:
    - Go to the **Manage Jenkins** in Dashboard, then **System**.
    - In the **Global properties** section, check the **Environment variables** and add the following environment variables:

   <table border="1" cellpadding="5" cellspacing="0">
        <thead>
            <tr>
                <th>Environment Variable</th>
                <th>Value</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><code>HEROKU_API_KEY</code></td>
                <td>This is your Heroku API key used for deploying the app to Heroku</td>
            </tr>
            <tr>
                <td><code>HEROKU_URL</code></td>
                <td>The URL of your Heroku app (e.g., https://your-app-name.herokuapp.com)</td>
            </tr>
            <tr>
                <td><code>WEATHERBIT_API_KEY</code></td>
                <td>The Weatherbit API key used to fetch weather data</td>
            </tr>
            <tr>
                <td><code>WEATHERBIT_URL</code></td>
                <td>https://api.weatherbit.io/v2.0/forecast/daily</td>
            </tr>
        </tbody>
    </table>


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

[🌐 Live App Link](https://windsurfers-weather-service-02e8055dc282.herokuapp.com/)

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
