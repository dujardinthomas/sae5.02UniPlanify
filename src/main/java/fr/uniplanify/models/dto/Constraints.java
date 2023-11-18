package fr.uniplanify.models.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Constraints {
    @Id
    int dureeDefaultMinutes = 15;
    int nbPersonneMaxDefault = 1;

    public Constraints(int dureeDefaultMinutes, int nbPersonneMaxDefault) {
        this.dureeDefaultMinutes = dureeDefaultMinutes;
        this.nbPersonneMaxDefault = nbPersonneMaxDefault;
    }

    
    public Constraints() {
    }


    public int getDureeDefaultMinutes() {
        return dureeDefaultMinutes;
    }

    public void setDureeDefaultMinutes(int dureeDefaultMinutes) {
        this.dureeDefaultMinutes = dureeDefaultMinutes;
    }

    public int getNbPersonneMaxDefault() {
        return nbPersonneMaxDefault;
    }
    
    public void setNbPersonneMaxDefault(int nbPersonneMaxDefault) {
        this.nbPersonneMaxDefault = nbPersonneMaxDefault;
    }
    
    
}
