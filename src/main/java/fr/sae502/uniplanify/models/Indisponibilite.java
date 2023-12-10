package fr.sae502.uniplanify.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Indisponibilite {

    @EmbeddedId // Annotation pour indiquer l'utilisation d'une clé primaire composite
    private CleCompositeIndisponibilite CleCompositeIndisponibilite;

    private String motif;

    public LocalDate getDebutJour() {
        return CleCompositeIndisponibilite.getDebutJour();
    }

    public LocalDate getFinJour() {
        return CleCompositeIndisponibilite.getFinJour();
    }

    public LocalTime getDebutHeure() {
        return CleCompositeIndisponibilite.getDebutHeure();
    }

    public LocalTime getFinHeure() {
        return CleCompositeIndisponibilite.getFinHeure();
    }
}
