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

    private String state;
    private String comment;
    private double fillPercentage;

    @ManyToMany
    @JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = {"participant_id", "rdv_day", "rdv_time"}))
    private List<UserAccount> participant;

    public Rdv() {
    }

    public void setRemplissagePourcentage(double fillPercentage) {
        this.fillPercentage = fillPercentage;
    }

    public void recalculerRemplissagePourcentage(ConstraintProRepository constraintRepository) {
        int nbParticipants = constraintRepository.findAll().iterator().next().getNbPersonneMaxDefault();
        if (this.participant == null) {
            this.fillPercentage = 0;
        } else {
            this.fillPercentage = (double) this.participant.size() / nbParticipants * 100;
        }
    }


    public Rdv(CompositeKeyRDV compositeKeyRDV, String state, String comment) {
        this.compositeKeyRDV = compositeKeyRDV;
        this.state = state;
        this.comment = comment;
    }



    public int getYear() {
        return this.compositeKeyRDV.getDay().getYear();
    }

    public int getMonth() {
        return this.compositeKeyRDV.getDay().getMonthValue();
    }

    public int getDay() {
        return this.compositeKeyRDV.getDay().getDayOfMonth();
    }

    public LocalDate getLocalDate() {
        return this.compositeKeyRDV.getDay();
    }

    public String dateToString(){
        return getLocalDate().format(DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH));
    }



    public int getHours() {
        return this.compositeKeyRDV.getTime().getHour();
    }

    public int getMinutes() {
        return this.compositeKeyRDV.getTime().getMinute();
    }

    public LocalTime getLocalTime() {
        return this.compositeKeyRDV.getTime();
    }

    public String getTimeToStringHorloge() {
        return getLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    
    public List<UserAccount> getParticipants() {
        return this.participant;
    }

    public boolean isEmpty() {
        return this.participant == null || this.participant.isEmpty();
    }

    

    public void addParticipant(UserAccount user) {
        if (this.participant == null) {
            this.participant = new ArrayList<>();
        }
        this.participant.add(user);
    }

    public String urlToStringTakeRdv(String texteAafficher) {
        if(texteAafficher.equals("code:heureDuRdv")){
            texteAafficher = this.getTimeToStringHorloge();
        }
        
        if(this.state.contains("COMPLET")) {
            return this.getTimeToStringHorloge();
        }

        LocalDateTime dateTime = getLocalDate().atTime(this.compositeKeyRDV.getTime());
        String href = "<a href=\"rdv/reserve?"
                + "year=" + dateTime.getYear()
                + "&month=" + dateTime.getMonthValue()
                + "&day=" + dateTime.getDayOfMonth()
                + "&hours=" + dateTime.getHour()
                + "&minutes=" + dateTime.getMinute()
                + "\">" + texteAafficher + "</a>";
        return href;
    }

    public String getStyle() {
        if (this.state.contains("ENCORE")) {
            return "'background-color:#FFA500'";
        } else if (this.state.contains("COMPLET")) {
            return "'background-color:#ff0000'";
        } else {
            return "'background-color:#88ff00'";
        }
    }
    
}
