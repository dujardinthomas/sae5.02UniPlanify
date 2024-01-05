package fr.sae502.uniplanify.models.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.sae502.uniplanify.models.CompositeKeyUnavailability;
import fr.sae502.uniplanify.models.Unavailability;

public interface UnavailabilityRepository extends CrudRepository<Unavailability, CompositeKeyUnavailability> {
    
        //obtenir les indisponibilités depuis une heure donnée
        @Query("SELECT u from Unavailability u WHERE u.compositeKeyUnavailability.day = :day " +
            "AND u.compositeKeyUnavailability.startTime <= :timeRDV " +
            "AND u.compositeKeyUnavailability.endTime > :timeRDV")
        List<Unavailability> findByCreneau(LocalDate day, LocalTime timeRDV);
}
