package fr.sae502.uniplanify.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import fr.sae502.uniplanify.repository.ContraintesRepository;
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
    private CleCompositeRDV cleCompositeRDV;

    private String etat;
    private String commentaire;
    private int remplissagePourcentage;

    @ManyToMany
    @JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = {"participants_id", "rdv_heure", "rdv_jour"}))
    private List<Utilisateur> participants;

    public Rdv() {
    }

    public void setRemplissagePourcentage(int remplissagePourcentage) {
        this.remplissagePourcentage = remplissagePourcentage;
    }

    public void recalculerRemplissagePourcentage(ContraintesRepository constraintRepository) {
        int nbParticipants = constraintRepository.findAll().iterator().next().getNbPersonneMaxDefault();
        if (this.participants == null) {
            this.remplissagePourcentage = 0;
        } else {
            this.remplissagePourcentage = this.participants.size() / nbParticipants * 100;
        }
    }

    public Rdv(CleCompositeRDV cleCompositeRDV, String etat, String commentaire) {
        this.cleCompositeRDV = cleCompositeRDV;
        this.etat = etat;
        this.commentaire = commentaire;
    }

    public int getYear() {
        return this.cleCompositeRDV.getJour().getYear();
    }

    public int getMonth() {
        return this.cleCompositeRDV.getJour().getMonthValue();
    }

    public int getDay() {
        return this.cleCompositeRDV.getJour().getDayOfMonth();
    }

    public int getHours() {
        return this.cleCompositeRDV.getHeure().getHour();
    }

    public int getMinutes() {
        return this.cleCompositeRDV.getHeure().getMinute();
    }

    public LocalTime getHeure() {
        return this.cleCompositeRDV.getHeure();
    }

    public String getHeureString() {
        return getHeure().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    
    public List<Utilisateur> getParticipants() {
        return this.participants;
    }

    public LocalDate getJour() {
        return this.cleCompositeRDV.getJour();
    }

    public void addParticipant(Utilisateur user) {
        if (this.participants == null) {
            this.participants = new ArrayList<>();
        }
        this.participants.add(user);
    }

    public String urlToStringTakeRdv(String texteAafficher) {
        if(texteAafficher.equals("code:heureDuRdv")){
            texteAafficher = this.getHeureString();
        }
        
        if(this.etat.contains("COMPLET")) {
            return this.getHeureString();
        }

        LocalDateTime dateTime = this.cleCompositeRDV.getJour().atTime(this.cleCompositeRDV.getHeure());
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
        if (this.etat.contains("ENCORE")) {
            return "'background-color:#FFA500'";
        } else if (this.etat.contains("COMPLET")) {
            return "'background-color:#ff0000'";
        } else {
            return "'background-color:#88ff00'";
        }
    }
    
}
