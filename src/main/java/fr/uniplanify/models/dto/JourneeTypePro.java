package fr.uniplanify.models.dto;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQuery(name="JourneeTypePro.findAll", query="SELECT j from JourneeTypePro j")
public class JourneeTypePro {
    @Id
    private String jour;
    private LocalTime heureDebut;
    private LocalTime heureFin;

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
    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }
    public LocalTime getHeureFin() {
        return heureFin;
    }
    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }
    @Override
    public String toString() {
        return "JourneeTypePro [jour=" + jour + ", heureDebut=" + heureDebut + ", heureFin=" + heureFin + "]";
    }
 
    

    
}
