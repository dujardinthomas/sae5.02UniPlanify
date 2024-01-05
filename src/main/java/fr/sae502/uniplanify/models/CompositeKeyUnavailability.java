package fr.sae502.uniplanify.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class CompositeKeyUnavailability {
    private LocalDate day;
    private LocalTime startTime;
    private LocalTime endTime;

    public CompositeKeyUnavailability() {
    }

    public CompositeKeyUnavailability(LocalDate day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}