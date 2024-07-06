package fr.sae502.uniplanify.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public int getDay() {
        return date.getDayOfMonth();
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getYear() {
        return date.getYear();
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


        List<Unavailability> UnavailabilitysOfDay = unavailabilityRepository.findByDay(selectedDate);
        // System.out.println("il y a : " + UnavailabilitysOfDay.size() + " indispo sur la journée");

        LocalTime startTimeDay = dayTime.getStartTime();
        LocalTime endTimeDay = dayTime.getEndTime();

        LocalTime iterableTime = startTimeDay;
        while (!iterableTime.plusMinutes(dureeRDV).isAfter(endTimeDay)) {

            // Vérification des indisponibilités
            if (!UnavailabilitysOfDay.isEmpty()) {
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
            cleRDV.setTimeRdv(iterableTime);
            cleRDV.setDayRdv(selectedDate);
            //ON AJOUTE SOIT LE RDV SOIT UN RDV VIDE
            Rdv rdvActuelle = rdvRepository.findById(cleRDV).orElse(new Rdv(cleRDV));

            //on le definit a ouvert pour qu'il soit affiché
            rdvActuelle.setOuvert(true);

             //si le rdv est dans le passé on le propose pas
            if(iterableTime.isBefore(LocalTime.now()) && selectedDate.isEqual(LocalDate.now())) {
                System.out.println("le rdv est dans le passé on le ferme");
                rdvActuelle.setOuvert(false);
                //iterableTime = iterableTime.plusMinutes(dureeRDV);
            }

            iterableTime = iterableTime.plusMinutes(dureeRDV); // Incrément de la duree de rdv fixé par le pro
            listRdvDay.add(rdvActuelle);
            //System.out.println("ajout d'un rdv à la liste : " + rdvActuelle);
        }
        // System.out.println("il y a : " + listRdvDay.size() + " rdvs sur la journée du " + this.date);
        if(listRdvDay.size() == 0) {
            System.out.println("la liste est vide : pas de rdv sur la journée !!!!!!! donc on ferme le jour");
            this.ouvert = false;
        } else {
            this.ouvert = true;
        }
        return listRdvDay;
    }

    
}
