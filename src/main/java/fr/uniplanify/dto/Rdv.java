package fr.uniplanify.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class Rdv {
    Date jour;
    Time heure;
    int duree;
    int nbPersonneMax;
    char etat;
    ArrayList<Client> clients;

    public Rdv(Date jour, Time heure, int duree, int nbPersonneMax, char etat, ArrayList<Client> clients) {
        this.jour = jour;
        this.heure = heure;
        this.duree = duree;
        this.nbPersonneMax = nbPersonneMax;
        this.etat = etat;
        this.clients = clients;
    }

    public Date getJour() {
        return jour;
    }

    public void setJour(Date jour) {
        this.jour = jour;
    }

    public Time getHeure() {
        return heure;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
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

    public char getEtat() {
        return etat;
    }

    public void setEtat(char etat) {
        this.etat = etat;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    
    

    
}
