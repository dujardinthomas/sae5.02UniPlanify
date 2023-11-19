package fr.uniplanify.models.dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQuery(name="Rdv.findAll", query="SELECT r from Rdv r")
public class Rdv implements Serializable {

    @EmbeddedId // Annotation pour indiquer l'utilisation d'une clé primaire composite
    private CleCompositeRDV cleCompositeRDV;

    // @Id
    // LocalDate jour;
    // @Id
    // LocalTime heure;
    String etat;
    List<Client> clients;

    // public Rdv(LocalDate jour, LocalTime heure, String etat, List<Client> clients) {
    //     this.jour = jour;
    //     this.heure = heure;
    //     this.etat = etat;
    //     this.clients = clients;
    // }

    public Rdv() {
    }

    // public LocalDate getJour(){
    //     return jour;
    // }
    // public Date getJourSQL() {
    //     return java.sql.Date.valueOf(jour);
    // }
    // public LocalDate setJour(LocalDate jour){
    //     return jour;
    // }
    // public void setJourSQL(Date jour) {
    //     this.jour = jour.toLocalDate();
    // }
    

    // public LocalTime getHeure() {
    //     return heure;
    // }
    // public Time getHeureSQL() {
    //     return Time.valueOf(heure);
    // }
    // public void setHeure(LocalTime heure) {
    //     this.heure = heure;
    // }
    // public void setHeureSQL(Time heure) {
    //     this.heure = heure.toLocalTime();
    // }

    public String getEtat() {
        return etat;
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

    public void addClient(Client client){
        if(this.clients == null){
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
        return "Rdv [cleCompositeRDV=" + cleCompositeRDV + ", etat=" + etat + ", clients=" + clients + "]";
    }

    
    

    // @Override
    // public String toString() {
    //     return "Rdv [jour=" + jour + ", heure=" + heure + ", etat=" + etat + ", clients=" + clients + "]";
    // }
    

    


}
