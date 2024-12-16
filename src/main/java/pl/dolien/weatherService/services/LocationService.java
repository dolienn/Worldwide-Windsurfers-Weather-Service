package pl.dolien.weatherService.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dolien.weatherService.entities.Location;
import pl.dolien.weatherService.repository.LocationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
}
