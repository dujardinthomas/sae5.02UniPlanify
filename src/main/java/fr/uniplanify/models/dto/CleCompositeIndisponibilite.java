package fr.uniplanify.models.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Embeddable;

@Embeddable
public class CleCompositeIndisponibilite {
    private LocalDate debutJour;
    private LocalTime debutHeure;
    private LocalDate finJour;
    private LocalTime finHeure;

    public CleCompositeIndisponibilite() {
    }

    public CleCompositeIndisponibilite(LocalDate debutJour, LocalTime debutHeure, LocalDate finJour,
            LocalTime finHeure) {
        this.debutJour = debutJour;
        this.debutHeure = debutHeure;
        this.finJour = finJour;
        this.finHeure = finHeure;
    }

    public LocalDate getDebutJour() {
        return debutJour;
    }
    public void setDebutJour(LocalDate debutJour) {
        this.debutJour = debutJour;
    }
    public LocalTime getDebutHeure() {
        return debutHeure;
    }
    public void setDebutHeure(LocalTime debutHeure) {
        this.debutHeure = debutHeure;
    }
    public LocalDate getFinJour() {
        return finJour;
    }
    public void setFinJour(LocalDate finJour) {
        this.finJour = finJour;
    }
    public LocalTime getFinHeure() {
        return finHeure;
    }
    public void setFinHeure(LocalTime finHeure) {
        this.finHeure = finHeure;
    }

   

    

}