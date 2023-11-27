package fr.uniplanify.models.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CleCompositeIndisponibilite {
    @Id
    private LocalDate debutjour;
    @Id
    private LocalTime debutheure;
    @Id
    private LocalDate finjour;
    @Id
    private LocalTime finheure;


    public CleCompositeIndisponibilite(LocalDate debutjour, LocalTime debutheure, LocalDate finjour, LocalTime finheure) {
        this.debutjour = debutjour;
        this.debutheure = debutheure;
        this.finjour = finjour;
        this.finheure = finheure;
    }

    

    public CleCompositeIndisponibilite() {
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
        return "CleCompositeIndisponibilite [debutjour=" + debutjour + ", debutheure=" + debutheure + ", finjour=" + finjour
                + ", finheure=" + finheure + "]";
    }

}
