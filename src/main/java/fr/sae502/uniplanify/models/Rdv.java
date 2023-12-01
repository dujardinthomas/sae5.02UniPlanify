package fr.sae502.uniplanify.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Rdv {

    @EmbeddedId // Annotation pour indiquer l'utilisation d'une clé primaire composite
    private CleCompositeRDV cleCompositeRDV;

    private String etat;
    private String commentaire;

    @ManyToMany
    private List<Utilisateur> participants;

    public List<Utilisateur> getClients() {
        return this.participants;
    }

    public void addParticipant(Utilisateur user) {
        if (this.participants == null) {
            this.participants = new ArrayList<>();
        }
        this.participants.add(user);
    }

    public String toStringTakeRdv() {
        LocalDateTime dateTime = this.cleCompositeRDV.getJour().atTime(this.cleCompositeRDV.getHeure());
        String href = "<a href=\"Perso/Reserve?"
                + "year=" + dateTime.getYear()
                + "&month=" + dateTime.getMonthValue()
                + "&day=" + dateTime.getDayOfMonth()
                + "&hours=" + dateTime.getHour()
                + "&minutes=" + dateTime.getMinute()
                + "\">Prendre RDV </a>";
        // return "Rdv [cleCompositeRDV=" + cleCompositeRDV + ", etat=" + etat + ",
        // href=" + href + ", clients=" + clients
        // + "]";
        return href;
    }

    private String getStyle() {
        if (this.etat.contains("ENCORE")) {
            return "background-color:#FFA500";
        } else if (this.etat.contains("COMPLET")) {
            return "background-color:#ff0000";
        } else {
            return "background-color:#88ff00";
        }
    }

    public String toStringJour() {
        StringBuilder sb = new StringBuilder();
        // sb.append("<tr>");
        // sb.append("<td>");
        sb.append("<div class=\"cellule\"style=\"" + getStyle() + "\">");
        sb.append("<div class=\"dayNumber\">");
        sb.append(this.getCleCompositeRDV().getHeure().format(DateTimeFormatter.ofPattern("HH:mm")));

        sb.append("</div>");
        sb.append("<div class=\"event\">");

        if(etat.contains("COMPLET")) {
            sb.append(etat + "</div>");
        } else {
            sb.append(etat + toStringTakeRdv() + "</div>");
        }
        
        sb.append("</div>");
        // sb.append("</td>");
        // sb.append("</tr>");

        return sb.toString();
    }

    
}
