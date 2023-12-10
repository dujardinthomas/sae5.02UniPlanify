package fr.sae502.uniplanify.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.sae502.uniplanify.models.CleCompositeRDV;
import fr.sae502.uniplanify.models.Rdv;

public interface RdvRepository extends CrudRepository<Rdv, CleCompositeRDV> {

    @Query("SELECT r from Rdv r " +
           "JOIN r.participants p " +
           "WHERE p.id = :clientId " +
           "ORDER BY r.cleCompositeRDV.jour ASC, r.cleCompositeRDV.heure ASC")
    List<Rdv> findRdvsByClientId(@Param("clientId") int clientId);


    @Query("SELECT r from Rdv r " +
           "JOIN r.participants p " +
           "WHERE r.cleCompositeRDV.jour >= :startDay " +
           "AND r.cleCompositeRDV.heure >= :startTime " +
           "AND r.cleCompositeRDV.jour <= :endDay " +
           "AND r.cleCompositeRDV.heure <= :endTime " +
           "ORDER BY r.cleCompositeRDV.jour ASC, r.cleCompositeRDV.heure ASC")
    List<Rdv> findInIndispo(LocalDate startDay, LocalTime startTime, LocalDate endDay, LocalTime endTime);


}
