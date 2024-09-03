package fr.sae502.uniplanify.models;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ConstraintPro {
    @Id
    @Column(name = "duree_default_minutes")
    private int dureeDefaultMinutes;
    @Column(name = "nb_personne_max_default")
    private int nbPersonneMaxDefault;

    @Column
    private String nom;
    @Column
    private String description;
    @Column
    private String email;
    @Column
    private String telephone;
    @Column
    private String adresse;

    @Column(name = "start_lunch")
    private LocalTime startLunch;
    
    @Column(name = "end_lunch")
    private LocalTime endLunch;

    public ConstraintPro() {
    }

    public ConstraintPro(int dureeDefaultMinutes, int nbPersonneMaxDefault, String nom, String description, String email,
            String telephone, String adresse, LocalTime startLunch, LocalTime endLunch) {
        this.dureeDefaultMinutes = dureeDefaultMinutes;
        this.nbPersonneMaxDefault = nbPersonneMaxDefault;
        this.nom = nom;
        this.description = description;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.startLunch = startLunch;
        this.endLunch = endLunch;
    }

    public int getDureeDefaultMinutes() {
        return dureeDefaultMinutes;
    }
    public int getNbPersonneMaxDefault() {
        return nbPersonneMaxDefault;
    }

    public LocalTime getStartLunch(){
        return startLunch;
    }

    public LocalTime getEndLunch(){
        return endLunch;
    }
    
    
    
}
