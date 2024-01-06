package fr.sae502.uniplanify.models.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


import fr.sae502.uniplanify.models.CompositeKeyRDV;
import fr.sae502.uniplanify.models.Rdv;

public interface RdvRepository extends CrudRepository<Rdv, CompositeKeyRDV> {

       // obtient les rdv d'un client
       List<Rdv> findByParticipantId(int clientId);

       // obtient les rdv sur une période donnée a savoir un jour donné et une heure de début et de fin
       List<Rdv> findByCompositeKeyRDVDayAndCompositeKeyRDVTimeBetween(LocalDate day, LocalTime startTime, LocalTime endTime);

       // obtenir le dernier rendez depuis un jour et une heure dateDuRdv, heureDuRdv
       Rdv findFirstByCompositeKeyRDVDayAndCompositeKeyRDVTimeBeforeOrderByCompositeKeyRDVDayDescCompositeKeyRDVTimeDesc(LocalDate day, LocalTime time);
}
