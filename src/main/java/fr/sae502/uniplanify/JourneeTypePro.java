package fr.sae502.uniplanify;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class JourneeTypePro {
    
    @Id
    private String jour;
    private LocalTime heureDebut;
    private LocalTime heureFin;

}
