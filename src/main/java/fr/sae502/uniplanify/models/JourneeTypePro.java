package fr.sae502.uniplanify.models;

import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "journee_type_pro")
public class JourneeTypePro{
    @Id
    private String jour;
    @Column(name = "heure_debut")
    private LocalTime heureDebut;
    @Column(name = "heure_fin")
    private LocalTime heureFin;
    
    public JourneeTypePro(String jour, LocalTime heureDebut, LocalTime heureFin) {
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public JourneeTypePro() {
    }


    
    
}