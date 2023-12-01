package fr.sae502.uniplanify.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.sae502.uniplanify.models.CleCompositeIndisponibilite;
import fr.sae502.uniplanify.models.CleCompositeRDV;
import fr.sae502.uniplanify.models.Contraintes;
import fr.sae502.uniplanify.models.Indisponibilite;
import fr.sae502.uniplanify.models.JourneeTypePro;
import fr.sae502.uniplanify.models.Rdv;
import fr.sae502.uniplanify.repository.ContraintesRepository;
import fr.sae502.uniplanify.repository.IndisponibiliteRepository;
import fr.sae502.uniplanify.repository.JourneeTypeProRepository;
import fr.sae502.uniplanify.repository.RdvRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Component
@Controller
public class Jour {

    private JourneeTypeProRepository journeeTypeProRepository;
    private ContraintesRepository constraintRepository;
    private IndisponibiliteRepository indisponibiliteRepository;
    private RdvRepository rdvRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH);

    public Jour(JourneeTypeProRepository journeeTypeProRepository, ContraintesRepository constraintRepository,
            IndisponibiliteRepository indisponibiliteRepository, RdvRepository rdvRepository) {
        this.journeeTypeProRepository = journeeTypeProRepository;
        this.constraintRepository = constraintRepository;
        this.indisponibiliteRepository = indisponibiliteRepository;
        this.rdvRepository = rdvRepository;
    }

    @RequestMapping(value = "/Jour")
    public ModelAndView jour(@RequestParam(defaultValue = "0") int year,
            @RequestParam(defaultValue = "0") int month,
            @RequestParam(defaultValue = "0") int day) {

        LocalDate selectedDate = LocalDate.now();
        if (month == 0 || day == 0 || year == 0) {
            year = selectedDate.getYear();
            month = selectedDate.getMonthValue();
            day = selectedDate.getDayOfMonth();
        } else {
            selectedDate = LocalDate.of(year, month, day);
        }

        List<Rdv> getRdvStatus = getRdvStatus(selectedDate);

        ModelAndView mav = new ModelAndView("jour");
        mav.addObject("listRDV", getRdvStatus);
        mav.addObject("selectedDate", selectedDate);

        return mav;
    }

    public List<Rdv> getRdvStatus(LocalDate selectedDate) {
        List<Rdv> listRdvDay = new ArrayList<>();

        // Récupération des contraintes
        Iterable<Contraintes> constraints = constraintRepository.findAll();
        int nbPersonneMax = 0;
        int dureeRDV = 0;
        for (Contraintes contraintes : constraints) {
            nbPersonneMax = contraintes.getNbPersonneMaxDefault();
            dureeRDV = contraintes.getDureeDefaultMinutes();
        }
        
        String dayStringNumberMonthYear = selectedDate.format(formatter);
        JourneeTypePro dayTime = journeeTypeProRepository.findById((dayStringNumberMonthYear.split(" ")[0])).get();

        if (dayTime == null) {
            System.out.println("jour fermé!");
            return listRdvDay;
        }

        // CleCompositeIndisponibilite cleCompositeIndisponibilite = new
        // CleCompositeIndisponibilite();
        // cleCompositeIndisponibilite.setDebutJour(selectedDate);
        // Indisponibilite indisponibilite = em.find(Indisponibilite.class,
        // selectedDate);

        String query = "SELECT * FROM indisponibilite WHERE " +
                "debutjour <= '" + selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "' " +
                "AND finjour >= '" + selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "'";

        Iterable<Indisponibilite> indisponibiliteIterator = indisponibiliteRepository.findAll();
        List<Indisponibilite> indisponibilites = new ArrayList<>();
        for (Indisponibilite indisponibilite : indisponibiliteIterator) {
            indisponibilites.add(indisponibilite);
        }

        // for (Indisponibilite indispo : indisponibilites) {
        // CleCompositeIndisponibilite i = indispo.getCleCompositeIndisponibilite();
        // if ( (selectedDate.isAfter(i.getDebutJour()) ||
        // selectedDate.isEqual(i.getDebutJour()))
        // && (selectedDate.isBefore(i.getFinJour())
        // || selectedDate.isEqual(i.getFinJour())) ) {
        // System.out.println("jour fermé car indispo!");
        // return listRdvDay;
        // }
        // }

        System.out.println(indisponibilites.size());
        for (Indisponibilite indisponibilite : indisponibilites) {
            System.out.println(indisponibilite);
        }

        LocalTime startTimeDay = dayTime.getHeureDebut();
        LocalTime endTimeDay = dayTime.getHeureFin();
        LocalTime timeNow = startTimeDay;

        while (!timeNow.plusMinutes(dureeRDV).isAfter(endTimeDay)) {

            for (Indisponibilite indispo : indisponibilites) {
                CleCompositeIndisponibilite i = indispo.getCleCompositeIndisponibilite();
                while (((timeNow.isAfter(i.getDebutHeure())) || (timeNow.equals(i.getDebutHeure())))
                        && (timeNow.isBefore(i.getFinHeure()))) {
                    // || timeNow.equals(i.getFinHeure())
                    System.out.println("heure indispo!");
                    timeNow = timeNow.plusMinutes(dureeRDV);
                    // continue;
                }
            }

            if ((timeNow.isAfter(endTimeDay)) || timeNow.equals(endTimeDay)) {
                break;
            }

            CleCompositeRDV cleRDV = new CleCompositeRDV();
            cleRDV.setHeure(timeNow);
            cleRDV.setJour(selectedDate);
            Rdv rdvActuelle = rdvRepository.findById(cleRDV).orElse(null);

            if (rdvActuelle == null) {
                // Si aucun rendez-vous, la plage est disponible
                rdvActuelle = new Rdv();
                rdvActuelle.setCleCompositeRDV(cleRDV);
                rdvActuelle.setEtat("DISPONIBLE POUR LE MOMENT ");
            } else if (rdvActuelle.getClients().size() < nbPersonneMax) {
                // Si des places sont disponibles dans le rendez-vous
                rdvActuelle.setEtat(
                        "ENCORE " + (nbPersonneMax - rdvActuelle.getClients().size()) + " PLACES DISPONIBLES SUR "
                                + nbPersonneMax + " POUR LE MOMENT ");
            } else {
                // Si le rendez-vous est complet
                rdvActuelle.setEtat("COMPLET");
            }
            listRdvDay.add(rdvActuelle);
            timeNow = timeNow.plusMinutes(dureeRDV); // Incrément de la duree de rdv fixé par le pro

        }
        return listRdvDay;
    }
}