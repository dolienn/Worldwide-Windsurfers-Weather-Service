package pl.dolien.weatherService.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.dolien.weatherService.entities.Location;
import pl.dolien.weatherService.repository.LocationRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationServiceTest {

    @InjectMocks
    private LocationService locationService;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private Location testLocation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        initializeTestData();
    }

    @Test
    void shouldReturnAllLocations() {
        when(locationRepository.findAll()).thenReturn(List.of(testLocation));

        List<Location> locations = locationService.getAllLocations();

        assertEquals(1, locations.size());
        assertEquals(testLocation, locations.get(0));
        verify(locationRepository, times(1)).findAll();
    }

    private void initializeTestData() {
        testLocation = Location.builder()
                .cityName("Jastarnia")
                .countryName("Poland")
                .build();
    }
}