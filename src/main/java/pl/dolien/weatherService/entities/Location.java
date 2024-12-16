package pl.dolien.weatherService.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String cityName;
    private String countryName;
}
