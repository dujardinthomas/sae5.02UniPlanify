package fr.uniplanify.models.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Embeddable;

@Embeddable
public class CleCompositeRDVClient implements Serializable{
    private LocalDate jour;
    private LocalTime heure;
    private int idC;
    
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
    public int getIdC() {
        return idC;
    }
    public void setIdC(int idC) {
        this.idC = idC;
    }

    
}
