package fr.uniplanify.models.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Indisponibilite {

    @EmbeddedId // Annotation pour indiquer l'utilisation d'une clé primaire composite
    private CleCompositeIndisponibilite CleCompositeIndisponibilite;

    private String motif;

    public Indisponibilite() {
    }

    public Indisponibilite(CleCompositeIndisponibilite cleCompositeIndisponibilite, String motif) {
        CleCompositeIndisponibilite = cleCompositeIndisponibilite;
        this.motif = motif;
    }

    public CleCompositeIndisponibilite getCleCompositeIndisponibilite() {
        return CleCompositeIndisponibilite;
    }

    public void setCleCompositeIndisponibilite(CleCompositeIndisponibilite cleCompositeIndisponibilite) {
        CleCompositeIndisponibilite = cleCompositeIndisponibilite;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    @Override
    public String toString() {
        return "Indisponibilite [CleCompositeIndisponibilite=" + CleCompositeIndisponibilite + ", motif=" + motif + "]";
    }

    

    
}
