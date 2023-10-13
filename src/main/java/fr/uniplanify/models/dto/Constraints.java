package fr.uniplanify.models.dto;

public class Constraints {
    int dureeDefaultMinutes = 15;
    int nbPersonneMaxDefault = 1;

    public Constraints(int dureeDefaultMinutes, int nbPersonneMaxDefault) {
        this.dureeDefaultMinutes = dureeDefaultMinutes;
        this.nbPersonneMaxDefault = nbPersonneMaxDefault;
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
