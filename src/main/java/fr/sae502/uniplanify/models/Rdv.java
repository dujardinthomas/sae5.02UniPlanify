package fr.sae502.uniplanify.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.sae502.uniplanify.models.repository.ConstraintProRepository;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
public class Rdv {

    @EmbeddedId // Annotation pour indiquer l'utilisation d'une clé primaire composite
    private CompositeKeyRDV compositeKeyRDV;

    private String state; // "DISPONIBLE POUR LE MOMENT" ou "ENCORE X PLACES DISPONIBLES SUR Y POUR LE MOMENT" ou "COMPLET" 
    private String comment; // "X est parti" ou "X a rejoint le rdv"
    private double fillPercentage; // Pourcentage de remplissage du rdv
    private boolean ouvert; // Si le rdv est ouvert ou non a la reservation

    @ManyToMany
    @JoinTable(name = "rdv_participant",
            // joinColumns = @JoinColumn(name = "rdv_day", referencedColumnName = "day"),
            uniqueConstraints = @UniqueConstraint(columnNames = { "participant_id", "rdv_day_rdv", "rdv_time_rdv" }))
    private List<UserAccount> participant;

    public Rdv() {
    }

    //CREE UN RDV VIDE
    public Rdv(CompositeKeyRDV compositeKeyRDV) {
        this.compositeKeyRDV = compositeKeyRDV;
        setState("DISPONIBLE POUR LE MOMENT");
        setFillPercentage(0);
        this.ouvert = true;
    }

    public void addParticipant(UserAccount user, String commentaire, ConstraintProRepository constraintProRepository) {
        if (this.participant == null) {
            this.participant = new ArrayList<>();
        }
        this.participant.add(user);
        this.recalculerRemplissagePourcentage(constraintProRepository);
        this.setState(constraintProRepository);
        this.setComment(commentaire);
    }

    public void removeParticipant(UserAccount user, String raison, ConstraintProRepository constraintProRepository) {
        if (this.participant != null) {
            this.participant.remove(user);
        }
        this.recalculerRemplissagePourcentage(constraintProRepository);
        this.setState(constraintProRepository);
        this.setComment(user.getEmail() + " est parti");
    }

    public void recalculerRemplissagePourcentage(ConstraintProRepository constraintRepository) {
        int nbParticipants = constraintRepository.findAll().iterator().next().getNbPersonneMaxDefault();
        if (this.participant == null) {
            this.fillPercentage = 0;
        } else {
            this.fillPercentage = (double) this.participant.size() / nbParticipants * 100;
        }
    }

    public void setState(ConstraintProRepository constraintProRepository) {
        int nbPersonneMax = constraintProRepository.findAll().iterator().next().getNbPersonneMaxDefault();
        // MET A JOUR L'ETAT DU RDV
        if (this.getParticipants().size() < nbPersonneMax) {
            // Si des places sont disponibles dans le rendez-vous
            this.setState(
                    "Encore " + (nbPersonneMax - this.getParticipants().size()) + " places disponible sur "
                            + nbPersonneMax);
        } else {
            // Si le rendez-vous est complet
            this.setState("COMPLET");
        }
    }

    public String urlToStringTakeRdv(String texteAafficher) {
        if (texteAafficher.equals("code:heureDuRdv")) {
            texteAafficher = this.getTimeToStringHorloge();
        }

        if (this.state.contains("COMPLET")) {
            return this.getTimeToStringHorloge();
        }

        LocalDateTime dateTime = getLocalDate().atTime(this.compositeKeyRDV.getTimeRdv());
        String href = "<a href=\"rdv/reserve?"
                + "year=" + dateTime.getYear()
                + "&month=" + dateTime.getMonthValue()
                + "&day=" + dateTime.getDayOfMonth()
                + "&hours=" + dateTime.getHour()
                + "&minutes=" + dateTime.getMinute()
                + "\">" + texteAafficher + "</a>";
        return href;
    }


    public String getRdvPourLePro(){
        StringBuilder sb = new StringBuilder();
        sb.append(getTimeToStringHorloge() + " - " + getParticipants().size() + " participants : ");
        for (UserAccount user : getParticipants()) {
            sb.append(user.getPrenom() + " " + user.getNom() + ", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    public int getYear() {
        return this.compositeKeyRDV.getDayRdv().getYear();
    }

    public int getMonth() {
        return this.compositeKeyRDV.getDayRdv().getMonthValue();
    }

    public int getDay() {
        return this.compositeKeyRDV.getDayRdv().getDayOfMonth();
    }

    public LocalDate getLocalDate() {
        return this.compositeKeyRDV.getDayRdv();
    }

    public String dateToString() {
        return getLocalDate().format(DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH));
    }

    public int getHours() {
        return this.compositeKeyRDV.getTimeRdv().getHour();
    }

    public int getMinutes() {
        return this.compositeKeyRDV.getTimeRdv().getMinute();
    }

    public LocalTime getLocalTime() {
        return this.compositeKeyRDV.getTimeRdv();
    }

    public String getTimeToStringHorloge() {
        return getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public List<UserAccount> getParticipants() {
        return this.participant;
    }

    public int getNbParticipants() {
        if (this.participant == null) {
            return 0;
        }
        return this.participant.size();
    }
    

    public boolean isEmpty() {
        return this.participant == null || this.participant.isEmpty();
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isOuvert() {
        return this.ouvert;
    }
}
