package fr.uniplanify.models.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Embeddable;

@Embeddable
public class CleCompositeRDV implements Serializable{
    private LocalDate jour;
    private LocalTime heure;
    
    public LocalDate getJour() {
        return jour;
    }
    public void setJour(LocalDate jour) {
        this.jour = jour;
    }
    public LocalTime getHeure() {
        return heure;
    }
    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }
    @Override
    public String toString() {
        return "CleCompositeRDV [jour=" + jour + ", heure=" + heure + "]";
    }

    
    
}
