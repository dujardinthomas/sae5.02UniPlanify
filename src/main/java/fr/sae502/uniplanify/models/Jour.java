package fr.sae502.uniplanify.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.sae502.uniplanify.repository.ContraintesRepository;
import fr.sae502.uniplanify.repository.IndisponibiliteRepository;
import fr.sae502.uniplanify.repository.JourneeTypeProRepository;
import fr.sae502.uniplanify.repository.RdvRepository;

public class Jour {
    private LocalDate date;
    private boolean ouvert;
    private String dayName;
    private String linkDay;
    private List<Rdv> rdvs;

    private ContraintesRepository constraintRepository;
    private JourneeTypeProRepository journeeTypeProRepository;
    private IndisponibiliteRepository indisponibiliteRepository;
    private RdvRepository rdvRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH);

    public boolean getOuvert() {
        return ouvert;
    }

    public String getTitle(){
        return date.format(formatter);
    }

    public int getPositionSemaine() {
        return date.getDayOfWeek().getValue();
    }

    public LocalDate getDate() {
        return date;
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getYear() {
        return date.getYear();
    }

    public String getDayName() {
        return dayName;
    }

    public String getLinkDay() {
        return linkDay;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    public List<Rdv> getRdvs() {
        return rdvs;
    }

    @Override
    public String toString() {
        return "Jour [date=" + date + ", dayName=" + dayName + ", linkDay=" + linkDay + ", rdvs=" + rdvs
                + ", constraintRepository=" + constraintRepository + ", journeeTypeProRepository="
                + journeeTypeProRepository + ", indisponibiliteRepository=" + indisponibiliteRepository
                + ", rdvRepository=" + rdvRepository + ", formatter=" + formatter + "]";
    }

    public Jour(LocalDate date, boolean ouvert) {
        this.date = date;
        this.ouvert = ouvert;
        this.rdvs = new ArrayList<>();
    }

    public Jour(LocalDate date, boolean ouvert, ContraintesRepository constraintRepository,
            JourneeTypeProRepository journeeTypeProRepository,
            IndisponibiliteRepository indisponibiliteRepository,
            RdvRepository rdvRepository) {
        this.ouvert = ouvert;
        this.date = date;
        this.constraintRepository = constraintRepository;
        this.journeeTypeProRepository = journeeTypeProRepository;
        this.indisponibiliteRepository = indisponibiliteRepository;
        this.rdvRepository = rdvRepository;
        this.rdvs = createRdvListOfSelectedDate(date);
    }

    public Jour(LocalDate date, ContraintesRepository constraintRepository,
            JourneeTypeProRepository journeeTypeProRepository,
            IndisponibiliteRepository indisponibiliteRepository,
            RdvRepository rdvRepository) {
        this.date = date;
        this.constraintRepository = constraintRepository;
        this.journeeTypeProRepository = journeeTypeProRepository;
        this.indisponibiliteRepository = indisponibiliteRepository;
        this.rdvRepository = rdvRepository;
        this.rdvs = createRdvListOfSelectedDate(date);
    }

    public List<Rdv> createRdvListOfSelectedDate(LocalDate selectedDate) {
        List<Rdv> listRdvDay = new ArrayList<>();

        // Récupération des contraintes tout est sur la meme ligne
        Contraintes contrainte = constraintRepository.findAll().iterator().next();
        int nbPersonneMax = contrainte.getNbPersonneMaxDefault();
        int dureeRDV = contrainte.getDureeDefaultMinutes();

        String dayStringNumberMonthYear = selectedDate.format(formatter);
        JourneeTypePro dayTime = null;
        try {
            dayTime = journeeTypeProRepository.findById((dayStringNumberMonthYear.split(" ")[0])).get(); 
        } catch (Exception e) {
            System.out.println("jour fermé!");
            this.ouvert = false;
            return listRdvDay;
        }
        
        System.out.println("Le jour actuel est disponible.");
        this.ouvert = true;

        Iterable<Indisponibilite> indisponibiliteIterator = indisponibiliteRepository.findAll();

        LocalTime startTimeDay = dayTime.getHeureDebut();
        LocalTime endTimeDay = dayTime.getHeureFin();
        LocalTime timeNow = startTimeDay;

        while (!timeNow.plusMinutes(dureeRDV).isAfter(endTimeDay)) {

            for (Indisponibilite indispo : indisponibiliteIterator) {
                CleCompositeIndisponibilite i = indispo.getCleCompositeIndisponibilite();

                while (((timeNow.equals(i.getDebutHeure()) || timeNow.isAfter(i.getDebutHeure()))
                        && timeNow.isBefore(i.getFinHeure()))) {
                    System.out.println("Le jour et l'heure actuels sont indisponibles !");
                    timeNow = timeNow.plusMinutes(dureeRDV);
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
            } else if (rdvActuelle.getParticipants().size() < nbPersonneMax) {
                // Si des places sont disponibles dans le rendez-vous
                rdvActuelle.setEtat(
                        "ENCORE " + (nbPersonneMax - rdvActuelle.getParticipants().size()) + " PLACES DISPONIBLES SUR "
                                + nbPersonneMax + " POUR LE MOMENT ");
            } else {
                // Si le rendez-vous est complet
                rdvActuelle.setEtat("COMPLET");
            }
            timeNow = timeNow.plusMinutes(dureeRDV); // Incrément de la duree de rdv fixé par le pro
            listRdvDay.add(rdvActuelle);

        }
        System.out.println("il y a : " + listRdvDay.size() + " rdvs");
        return listRdvDay;
    }
}
