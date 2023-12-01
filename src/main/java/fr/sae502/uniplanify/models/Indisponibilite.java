package fr.sae502.uniplanify.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Indisponibilite {

    @EmbeddedId // Annotation pour indiquer l'utilisation d'une clé primaire composite
    private CleCompositeIndisponibilite CleCompositeIndisponibilite;

    private String motif;
}
