package fr.uniplanify.models.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Rdv.findAll", query = "SELECT r from Rdv r")
public class Rdv implements Serializable {

    @EmbeddedId // Annotation pour indiquer l'utilisation d'une clé primaire composite
    private CleCompositeRDV cleCompositeRDV;

    private String etat;
    private String commentaire;
    private List<Client> clients;

    public String getEtat() {
        return etat;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void addClient(Client client) {
        if (this.clients == null) {
            this.clients = new ArrayList<>();
        }
        this.clients.add(client);
    }

    public CleCompositeRDV getCleCompositeRDV() {
        return cleCompositeRDV;
    }

    public void setCleCompositeRDV(CleCompositeRDV cleCompositeRDV) {
        this.cleCompositeRDV = cleCompositeRDV;
    }


    @Override
    public String toString() {
        return "Rdv [cleCompositeRDV=" + cleCompositeRDV + ", etat=" + etat + ", commentaire=" + commentaire
                + ", clients=" + clients + "]";
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
        } else if (this.etat.contains("PLUS")) {
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
