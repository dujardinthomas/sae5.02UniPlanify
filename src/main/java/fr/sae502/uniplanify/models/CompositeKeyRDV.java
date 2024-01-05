package fr.sae502.uniplanify.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class CompositeKeyRDV implements Serializable{
    private LocalDate day;
    private LocalTime time;

    public CompositeKeyRDV() {
    }

    public CompositeKeyRDV(LocalDate day, LocalTime time) {
        this.day = day;
        this.time = time;
    }

    
    
}
