package fr.uniplanify.models.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Indisponibilite {
    @Id
    private LocalDate debutjour;
    @Id
    private LocalTime debutheure;
    @Id
    private LocalDate finjour;
    @Id
    private LocalTime finheure;


    public Indisponibilite(LocalDate debutjour, LocalTime debutheure, LocalDate finjour, LocalTime finheure) {
        this.debutjour = debutjour;
        this.debutheure = debutheure;
        this.finjour = finjour;
        this.finheure = finheure;
    }

    

    public Indisponibilite() {
    }



    public LocalDate getDebutjour() {
        return debutjour;
    }

    public void setDebutjour(LocalDate debutjour) {
        this.debutjour = debutjour;
    }

    public LocalTime getDebutheure() {
        return debutheure;
    }

    public void setDebutheure(LocalTime debutheure) {
        this.debutheure = debutheure;
    }

    public LocalDate getFinjour() {
        return finjour;
    }

    public void setFinjour(LocalDate finjour) {
        this.finjour = finjour;
    }

    public LocalTime getFinheure() {
        return finheure;
    }

    public void setFinheure(LocalTime finheure) {
        this.finheure = finheure;
    }

    @Override
    public String toString() {
        return "Indisponibilite [debutjour=" + debutjour + ", debutheure=" + debutheure + ", finjour=" + finjour
                + ", finheure=" + finheure + "]";
    }

}
