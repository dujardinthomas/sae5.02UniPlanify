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
                     "WHERE r.cleCompositeRDV.jour = :day " +
                     "AND r.cleCompositeRDV.heure >= :startTime " +
                     "AND r.cleCompositeRDV.heure <= :endTime " +
                     "ORDER BY r.cleCompositeRDV.jour ASC, r.cleCompositeRDV.heure ASC")
       List<Rdv> findInPeriode(LocalDate day, LocalTime startTime, LocalTime endTime);


       // @Query("SELECT r from Rdv r ORDER BY r.cleCompositeRDV.jour DESC, r.cleCompositeRDV.heure DESC")
       // List<Rdv> findLastRdv();

       // default Rdv findLastRdvSingle() {
       //        List<Rdv> rdvs = findLastRdv();
       //        return rdvs.isEmpty() ? null : rdvs.get(0);
       // }

       @Query("SELECT r from Rdv r " +
              "WHERE r.cleCompositeRDV.jour < :day " +
              "OR (r.cleCompositeRDV.jour = :day AND r.cleCompositeRDV.heure < :time) " +
              "ORDER BY r.cleCompositeRDV.jour DESC, r.cleCompositeRDV.heure DESC")
       List<Rdv> findPreviousRdv(LocalDate day, LocalTime time);

       default Rdv findPreviousRdvSingle(LocalDate day, LocalTime time) {
              List<Rdv> rdvs = findPreviousRdv(day, time);
              return rdvs.isEmpty() ? null : rdvs.get(0);
       }

}
