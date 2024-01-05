package fr.sae502.uniplanify.models.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.sae502.uniplanify.models.CompositeKeyRDV;
import fr.sae502.uniplanify.models.Rdv;

public interface RdvRepository extends CrudRepository<Rdv, CompositeKeyRDV> {

       // @Query("SELECT r from Rdv r " +
       //               "JOIN r.participants p " +
       //               "WHERE p.id = :clientId " +
       //               "ORDER BY r.CompositeKeyRDV.day ASC, r.CompositeKeyRDV.time ASC")
       // List<Rdv> findRdvsByClientId(@Param("clientId") int clientId);.

       // obtient les rdv d'un client
       List<Rdv> findByParticipantId(int clientId);

       // obtient les rdv sur une période donnée a savoir un jour donné et une heure de début et de fin
       List<Rdv> findByCompositeKeyRDVDayAndCompositeKeyRDVTimeBetween(LocalDate day, LocalTime startTime, LocalTime endTime);
       
       // //supprime un participant d'un rdv donne la requete
       // @Query("DELETE FROM Rdv r WHERE r.participant.id = :clientId")
       // void deleteByParticipantId(int clientId);

       


       // obtenir le dernier rendez depuis un jour et une heure dateDuRdv, heureDuRdv
       Rdv findFirstByCompositeKeyRDVDayAndCompositeKeyRDVTimeBeforeOrderByCompositeKeyRDVDayDescCompositeKeyRDVTimeDesc(LocalDate day, LocalTime time);

       // @Query("SELECT r from Rdv r " +
       //               "JOIN r.participants p " +
       //               "WHERE r.CompositeKeyRDV.day = :day " +
       //               "AND r.CompositeKeyRDV.time >= :startTime " +
       //               "AND r.CompositeKeyRDV.time <= :endTime " +
       //               "ORDER BY r.CompositeKeyRDV.day ASC, r.CompositeKeyRDV.time ASC")
       // List<Rdv> findInPeriode(LocalDate day, LocalTime startTime, LocalTime endTime);


       // @Query("SELECT r from Rdv r ORDER BY r.cleCompositeRDV.jour DESC, r.cleCompositeRDV.heure DESC")
       // List<Rdv> findLastRdv();

       // default Rdv findLastRdvSingle() {
       //        List<Rdv> rdvs = findLastRdv();
       //        return rdvs.isEmpty() ? null : rdvs.get(0);
       // }

       // @Query("SELECT r from Rdv r " +
       //        "WHERE r.CompositeKeyRDV.day < :day " +
       //        "OR (r.CompositeKeyRDV.day = :day AND r.CompositeKeyRDV.time < :time) " +
       //        "ORDER BY r.CompositeKeyRDV.day DESC, r.CompositeKeyRDV.time DESC")
       // List<Rdv> findPreviousRdv(LocalDate day, LocalTime time);

       // default Rdv findPreviousRdvSingle(LocalDate day, LocalTime time) {
       //        List<Rdv> rdvs = findPreviousRdv(day, time);
       //        return rdvs.isEmpty() ? null : rdvs.get(0);
       // }

}
