package pl.dolien.weatherService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dolien.weatherService.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

}
