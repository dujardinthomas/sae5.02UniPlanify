package fr.uniplanify.models.dto;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Rdv {
    @Id
    LocalDate jour;
    @Id
    LocalTime heure;
    String etat;
    List<Client> clients;

    public Rdv(LocalDate jour, LocalTime heure, String etat, List<Client> clients) {
        this.jour = jour;
        this.heure = heure;
        this.etat = etat;
        this.clients = clients;
    }

    public Rdv() {
    }

    public LocalDate getJour(){
        return jour;
    }
    public Date getJourSQL() {
        return java.sql.Date.valueOf(jour);
    }
    public LocalDate setJour(LocalDate jour){
        return jour;
    }
    public void setJourSQL(Date jour) {
        this.jour = jour.toLocalDate();
    }
    

    public LocalTime getHeure() {
        return heure;
    }
    public Time getHeureSQL() {
        return Time.valueOf(heure);
    }
    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }
    public void setHeureSQL(Time heure) {
        this.heure = heure.toLocalTime();
    }

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


    @Override
    public String toString() {
        return "Rdv [jour=" + jour + ", heure=" + heure + ", etat=" + etat + ", clients=" + clients + "]";
    }
    

    


}
