package fr.sae502.uniplanify.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Unavailability {

    @EmbeddedId // Annotation pour indiquer l'utilisation d'une clé primaire composite
    private CompositeKeyUnavailability compositeKeyUnavailability;

    private String motif;

    public LocalDate getLocalDate() {
        return compositeKeyUnavailability.getDayUnavailability();
    }

    public LocalTime getStartLocalTime() {
        return compositeKeyUnavailability.getStartTime();
    }

    public LocalTime getEndLocalTime() {
        return compositeKeyUnavailability.getEndTime();
    }

    public Unavailability(CompositeKeyUnavailability cleCompositeIndisponibilite) {
        compositeKeyUnavailability = cleCompositeIndisponibilite;
    }

    public Unavailability() {
    }

    
}
