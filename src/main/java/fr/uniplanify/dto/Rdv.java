package fr.uniplanify.dto;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Rdv {
    LocalDate jour;
    LocalTime heure;
    int duree;
    int nbPersonneMax;
    String etat;
    List<Client> clients;

    public Rdv(LocalDate jour, LocalTime heure, int duree, int nbPersonneMax, String etat, List<Client> clients) {
        this.jour = jour;
        this.heure = heure;
        this.duree = duree;
        this.nbPersonneMax = nbPersonneMax;
        this.etat = etat;
        this.clients = clients;
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

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getNbPersonneMax() {
        return nbPersonneMax;
    }

    public void setNbPersonneMax(int nbPersonneMax) {
        this.nbPersonneMax = nbPersonneMax;
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


}
