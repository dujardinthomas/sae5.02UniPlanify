package fr.sae502.uniplanify.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.jasper.tagplugins.jstl.core.If;

import fr.sae502.uniplanify.models.CompositeKeyRDV;
import fr.sae502.uniplanify.models.CompositeKeyUnavailability;
import fr.sae502.uniplanify.models.ConstraintPro;
import fr.sae502.uniplanify.models.Rdv;
import fr.sae502.uniplanify.models.TypicalDayPro;
import fr.sae502.uniplanify.models.Unavailability;
import fr.sae502.uniplanify.models.repository.ConstraintProRepository;
import fr.sae502.uniplanify.models.repository.RdvRepository;
import fr.sae502.uniplanify.models.repository.TypicalDayProRepository;
import fr.sae502.uniplanify.models.repository.UnavailabilityRepository;

public class Daily {
    private LocalDate date;
    private boolean ouvert;
    private String linkDay;
    private List<Rdv> rdvs;

    private int fillPercentage;

    private ConstraintProRepository constraintRepository;
    private TypicalDayProRepository typicalDayProRepository;
    private UnavailabilityRepository unavailabilityRepository;
    private RdvRepository rdvRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH);

    public boolean getOuvert() {
        return ouvert;
    }

    public void setOuvert(boolean ouvert) {
        this.ouvert = ouvert;
    }

    public void recalculerRemplissagePourcentageDay() {
        if (this.rdvs == null || getOuvert() == false) {
            this.fillPercentage = 0;
        } else {
            int remplissagePourcentageDayInterne = 0;
            for (Rdv rdv : this.rdvs) {
                remplissagePourcentageDayInterne += rdv.getFillPercentage();
            }
            try {
                this.fillPercentage = remplissagePourcentageDayInterne / this.rdvs.size();
                //setOuvert(true);
            } catch (Exception e) {
                System.out.println("IL N'Y A PAS DE RDV POUR CE JOUR" + this);
                //if(this.remplissagePourcentageDay == 0) {
                    this.fillPercentage = 100;
                //}
                setOuvert(false);
            }
        }
    }

    public int getFillPercentage() {
        recalculerRemplissagePourcentageDay();
        return this.fillPercentage;
    }

    public String getTitle() {
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
        return "Jour [date=" + date + ", linkDay=" + linkDay + ", rdvs=" + rdvs
                + ", constraintRepository=" + constraintRepository + ", journeeTypeProRepository="
                + typicalDayProRepository + ", indisponibiliteRepository=" + unavailabilityRepository
                + ", rdvRepository=" + rdvRepository + ", formatter=" + formatter + "]";
    }

    public Daily(LocalDate date, boolean ouvert) {
        this.date = date;
        this.ouvert = ouvert;
        this.rdvs = new ArrayList<>();
    }

    public Daily(LocalDate date, boolean ouvert, ConstraintProRepository constraintRepository,
            TypicalDayProRepository typicalDayProRepository,
            UnavailabilityRepository unavailabilityRepository,
            RdvRepository rdvRepository) {
        this.ouvert = ouvert;
        this.date = date;
        this.constraintRepository = constraintRepository;
        this.typicalDayProRepository = typicalDayProRepository;
        this.unavailabilityRepository = unavailabilityRepository;
        this.rdvRepository = rdvRepository;
        this.rdvs = createRdvListOfSelectedDate(date);
    }

    public Daily(LocalDate date, ConstraintProRepository constraintRepository,
            TypicalDayProRepository typicalDayProRepository,
            UnavailabilityRepository unavailabilityRepository,
            RdvRepository rdvRepository) {
        this.date = date;
        this.constraintRepository = constraintRepository;
        this.typicalDayProRepository = typicalDayProRepository;
        this.unavailabilityRepository = unavailabilityRepository;
        this.rdvRepository = rdvRepository;
        this.rdvs = createRdvListOfSelectedDate(date);
    }

    public List<Rdv> createRdvListOfSelectedDate(LocalDate selectedDate) {
        List<Rdv> listRdvDay = new ArrayList<>();

        // Récupération des contraintes tout est sur la meme ligne
        ConstraintPro contrainte = constraintRepository.findAll().iterator().next();
        int nbPersonneMax = contrainte.getNbPersonneMaxDefault();
        int dureeRDV = contrainte.getDureeDefaultMinutes();

        String dayStringNumberMonthYear = selectedDate.format(formatter);
        TypicalDayPro dayTime = null;
        try {
            dayTime = typicalDayProRepository.findById((dayStringNumberMonthYear.split(" ")[0])).get();
        } catch (Exception e) {
            System.out.println("jour fermé!");
            this.ouvert = false;
            return listRdvDay;
        }

        //FERME LE JOUR SI IL EST DANS LE PASSE
        if( (LocalDate.now().isAfter(selectedDate))){
            System.out.println("jour fermé!");
            this.ouvert = false;
            return listRdvDay;
        }

        Iterable<Unavailability> indisponibiliteIterator = unavailabilityRepository.findAll();

        boolean indispoSurJournee = false;
        List<Unavailability> UnavailabilitysOfDay = new ArrayList<>();
        for (Unavailability indispo : indisponibiliteIterator) {
            CompositeKeyUnavailability i = indispo.getCompositeKeyUnavailability();
            
            if (selectedDate.isEqual(i.getDay())) {
                System.out.println("ya une indispo sur la journée faut savoir mtn quand ");
                UnavailabilitysOfDay.add(indispo);
                System.out.println("indispo du jour : " + UnavailabilitysOfDay);
                indispoSurJournee = true;
            }
        }
        System.out.println("il y a : " + UnavailabilitysOfDay.size() + " indispo sur la journée");
        System.out.println("Le jour actuel est disponible");
        //this.ouvert = true;

        LocalTime startTimeDay = dayTime.getStartTime();
        LocalTime endTimeDay = dayTime.getEndTime();

        LocalTime iterableTime = startTimeDay;
        while (!iterableTime.plusMinutes(dureeRDV).isAfter(endTimeDay)) {

            // Vérification des indisponibilités
            if (indispoSurJournee) {
                System.out.println("recherche d'indispo creneau par creneau..." + iterableTime);
                for (Unavailability indispo : UnavailabilitysOfDay) {
                    CompositeKeyUnavailability i = indispo.getCompositeKeyUnavailability();

                    if (iterableTime.plusMinutes(dureeRDV).isAfter(i.getStartTime())
                            && iterableTime.plusMinutes(dureeRDV).isBefore(i.getEndTime())) {
                        System.out.println("la fin du rdv est dans une indispo" + iterableTime.plusMinutes(dureeRDV));
                        iterableTime = iterableTime.plusMinutes(dureeRDV);
                        
                    }

                    while (((iterableTime.equals(i.getStartTime()) || iterableTime.isAfter(i.getStartTime()))
                            && iterableTime.isBefore(i.getEndTime()))) {
                        System.out.println("Le jour et l'heure actuel sont indisponibles !" + iterableTime);
                        iterableTime = iterableTime.plusMinutes(dureeRDV);
                    }
                }
            }

            if ((iterableTime.isAfter(endTimeDay)) || iterableTime.equals(endTimeDay)) {
                System.out.println("fin de la journée");
                break;
            }

            CompositeKeyRDV cleRDV = new CompositeKeyRDV();
            cleRDV.setTime(iterableTime);
            cleRDV.setDay(selectedDate);
            Rdv rdvActuelle = rdvRepository.findById(cleRDV).orElse(null);

            if (rdvActuelle == null) {
                // Si aucun rendez-vous, la plage est disponible
                rdvActuelle = new Rdv();
                rdvActuelle.setCompositeKeyRDV(cleRDV);
                rdvActuelle.setState("DISPONIBLE POUR LE MOMENT ");
                rdvActuelle.setRemplissagePourcentage(0);
            } else if (rdvActuelle.getParticipants().size() < nbPersonneMax) {
                // Si des places sont disponibles dans le rendez-vous
                rdvActuelle.setState(
                        "ENCORE " + (nbPersonneMax - rdvActuelle.getParticipants().size()) + " PLACES DISPONIBLES SUR "
                                + nbPersonneMax + " POUR LE MOMENT ");
            } else {
                // Si le rendez-vous est complet
                rdvActuelle.setState("COMPLET");
            }
            iterableTime = iterableTime.plusMinutes(dureeRDV); // Incrément de la duree de rdv fixé par le pro
            listRdvDay.add(rdvActuelle);

        }
        System.out.println("il y a : " + listRdvDay.size() + " rdvs sur la journée du " + this.date);
        if(listRdvDay.size() == 0) {
            System.out.println("la liste est vide !!!!!!! donc on ferme le jour");
            this.ouvert = false;
        } else {
            this.ouvert = true;
        }
        return listRdvDay;
    }

    
}
