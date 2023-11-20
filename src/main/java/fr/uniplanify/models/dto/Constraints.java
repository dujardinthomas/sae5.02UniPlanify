package fr.uniplanify.models.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQuery(name="Constraints.findAll", query="SELECT c from Constraints c")
public class Constraints {
    @Id
    private int dureeDefaultMinutes = 15;
    private int nbPersonneMaxDefault = 1;

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
