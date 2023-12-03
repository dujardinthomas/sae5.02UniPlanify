package fr.sae502.uniplanify.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Contraintes {
    @Id
    @Column(name = "duree_default_minutes")
    private int dureeDefaultMinutes;
    @Column(name = "nb_personne_max_default")
    private int nbPersonneMaxDefault;

    public Contraintes() {
    }

    public Contraintes(int dureeDefaultMinutes, int nbPersonneMaxDefault) {
        this.dureeDefaultMinutes = dureeDefaultMinutes;
        this.nbPersonneMaxDefault = nbPersonneMaxDefault;
    }

    public int getDureeDefaultMinutes() {
        return dureeDefaultMinutes;
    }
    public int getNbPersonneMaxDefault() {
        return nbPersonneMaxDefault;
    }
    
    
    
}
