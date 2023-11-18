package fr.uniplanify.models.dto;

import java.sql.Time;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class JourneeTypePro {
    @Id
    String jour;
    LocalTime heureDebut;
    LocalTime heureFin;
    
    public JourneeTypePro(String jour, LocalTime heureDebut, LocalTime heureFin) {
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    

    public JourneeTypePro() {
    }



    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public Time getHeureDebutSQL() {
        return Time.valueOf(heureDebut);
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public void setHeureDebutSQL(Time heureDebut) {
        this.heureDebut = heureDebut.toLocalTime();
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public Time getHeureFinSQL() {
        return Time.valueOf(heureFin);
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public void setHeureFinSQL(Time heureFin) {
        this.heureFin = heureFin.toLocalTime();
    }

    @Override
    public String toString() {
        return "JournéePro [jour=" + jour + ", heureDebut=" + heureDebut + ", heureFin=" + heureFin + "]";
    }

    

    
}
