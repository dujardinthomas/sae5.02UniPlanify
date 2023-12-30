package fr.sae502.uniplanify.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.sae502.uniplanify.models.CleCompositeIndisponibilite;
import fr.sae502.uniplanify.models.Indisponibilite;

public interface IndisponibiliteRepository extends CrudRepository<Indisponibilite, CleCompositeIndisponibilite> {

    // @Query("SELECT i from Indisponibilite i " +
    //                  "WHERE i.cleCompositeIndisponibilite.jour = :dateDuRdv " +
    //                  "AND i.cleCompositeIndisponibilite.debutHeure <= :heureDuRdv " +
    //                  "AND i.cleCompositeIndisponibilite.finHeure >= :heureDuRdv ")
    // List<Indisponibilite> finByCreneau(@Param("dateDuRdv") LocalDate dateDuRdv, @Param("heureDuRdv") LocalTime heureDuRdv);
    
}
