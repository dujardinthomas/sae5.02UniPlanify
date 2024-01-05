package fr.sae502.uniplanify.models;

import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class TypicalDayPro{
    @Id
    private String day;
    private LocalTime startTime;
    private LocalTime endTime;

    public TypicalDayPro(String day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public TypicalDayPro() {
    }
    
}