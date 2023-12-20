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
    private String linkDay;
    private List<Rdv> rdvs;

    private int remplissagePourcentageDay;

    private ContraintesRepository constraintRepository;
    private JourneeTypeProRepository journeeTypeProRepository;
    private IndisponibiliteRepository indisponibiliteRepository;
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
            this.remplissagePourcentageDay = 0;
        } else {
            int remplissagePourcentageDayInterne = 0;
            for (Rdv rdv : this.rdvs) {
                remplissagePourcentageDayInterne += rdv.getRemplissagePourcentage();
            }
            try {
                this.remplissagePourcentageDay = remplissagePourcentageDayInterne / this.rdvs.size();
                //setOuvert(true);
            } catch (Exception e) {
                System.out.println("IL N'Y A PAS DE RDV POUR CE JOUR" + this);
                //if(this.remplissagePourcentageDay == 0) {
                    this.remplissagePourcentageDay = 100;
                //}
                setOuvert(false);
            }
            
            
        }
    }

    public int getRemplissagePourcentageDay() {
        recalculerRemplissagePourcentageDay();
        return this.remplissagePourcentageDay;
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

        Iterable<Indisponibilite> indisponibiliteIterator = indisponibiliteRepository.findAll();

        boolean indispoSurJournee = false;
        List<Indisponibilite> indispoDuJour = new ArrayList<>();
        for (Indisponibilite indispo : indisponibiliteIterator) {
            CleCompositeIndisponibilite i = indispo.getCleCompositeIndisponibilite();
            
            if (selectedDate.isEqual(i.getJour())) {
                System.out.println("ya une indispo sur la journée faut savoir mtn quand ");
                indispoDuJour.add(indispo);
                System.out.println("indispo du jour : " + indispoDuJour);
                indispoSurJournee = true;
            }
        }
        System.out.println("il y a : " + indispoDuJour.size() + " indispo sur la journée");
        System.out.println("Le jour actuel est disponible");
        //this.ouvert = true;

        LocalTime startTimeDay = dayTime.getHeureDebut();
        LocalTime endTimeDay = dayTime.getHeureFin();
        LocalTime timeNow = startTimeDay;

        while (!timeNow.plusMinutes(dureeRDV).isAfter(endTimeDay)) {

            // Vérification des indisponibilités
            if (indispoSurJournee) {
                System.out.println("recherche d'indispo creneau par creneau..." + timeNow);
                for (Indisponibilite indispo : indispoDuJour) {
                    CleCompositeIndisponibilite i = indispo.getCleCompositeIndisponibilite();

                    if (timeNow.plusMinutes(dureeRDV).isAfter(i.getDebutHeure())
                            && timeNow.plusMinutes(dureeRDV).isBefore(i.getFinHeure())) {
                        System.out.println("la fin du rdv est dans une indispo" + timeNow.plusMinutes(dureeRDV));
                        timeNow = timeNow.plusMinutes(dureeRDV);
                    }

                    while (((timeNow.equals(i.getDebutHeure()) || timeNow.isAfter(i.getDebutHeure()))
                            && timeNow.isBefore(i.getFinHeure()))) {
                        System.out.println("Le jour et l'heure actuel sont indisponibles !" + timeNow);
                        timeNow = timeNow.plusMinutes(dureeRDV);
                    }
                }
            }

            if ((timeNow.isAfter(endTimeDay)) || timeNow.equals(endTimeDay)) {
                System.out.println("fin de la journée");
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
                rdvActuelle.setRemplissagePourcentage(0);
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
        System.out.println("il y a : " + listRdvDay.size() + " rdvs sur la journée du " + this.date + "pour en parametres : " + selectedDate);
        if(listRdvDay.size() == 0) {
            System.out.println("la liste est vide !!!!!!! donc on ferme le jour");
            this.ouvert = false;
        } else {
            this.ouvert = true;
        }
        return listRdvDay;
    }
}
