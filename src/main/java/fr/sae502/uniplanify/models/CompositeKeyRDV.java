package fr.sae502.uniplanify.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class CompositeKeyRDV implements Serializable{
    private LocalDate dayRdv;
    private LocalTime timeRdv;

    public CompositeKeyRDV() {
    }

    public CompositeKeyRDV(LocalDate dayRdv, LocalTime timeRdv) {
        this.dayRdv = dayRdv;
        this.timeRdv = timeRdv;
    }

    
    
}
