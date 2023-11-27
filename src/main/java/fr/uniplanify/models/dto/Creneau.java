package fr.uniplanify.models.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class Creneau {

    private LocalDate jour;
    private LocalTime heure;
    private int duree;
    
    public Creneau(LocalDate jour, LocalTime heure, int duree) {
        this.jour = jour;
        this.heure = heure;
        this.duree = duree;
    }

    
    
}
