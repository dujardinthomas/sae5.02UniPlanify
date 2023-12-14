package fr.sae502.uniplanify.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Embeddable;

@Embeddable
public class CleCompositeIndisponibilite {
    private LocalDate jour;
    private LocalTime debutHeure;
    private LocalTime finHeure;

    public CleCompositeIndisponibilite() {
    }

    public CleCompositeIndisponibilite(LocalDate jour, LocalTime debutHeure, LocalTime finHeure) {
        this.jour = jour;
        this.debutHeure = debutHeure;
        this.finHeure = finHeure;
    }

    public LocalDate getJour() {
        return jour;
    }

    public void setJour(LocalDate jour) {
        this.jour = jour;
    }

    public LocalTime getDebutHeure() {
        return debutHeure;
    }

    public void setDebutHeure(LocalTime debutHeure) {
        this.debutHeure = debutHeure;
    }

    public LocalTime getFinHeure() {
        return finHeure;
    }

    public void setFinHeure(LocalTime finHeure) {
        this.finHeure = finHeure;
    }

    @Override
    public String toString() {
        return "CleCompositeIndisponibilite [debutHeure=" + debutHeure + ", finHeure=" + finHeure + ", jour=" + jour
                + "]";
    }

}