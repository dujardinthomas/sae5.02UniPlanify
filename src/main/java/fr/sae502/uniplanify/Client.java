package fr.sae502.uniplanify;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "is required")
    private String nom;

    @NotNull(message = "is required")
    private String prenom;

    @NotNull(message = "is required")
    private String mail;
    
    private String password;
    private Boolean pro;

    
    public Client(@NotNull(message = "is required") String nom, @NotNull(message = "is required") String prenom,
            @NotNull(message = "is required") String mail, String password, Boolean pro) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.password = password;
        this.pro = pro;
    }

    

    
    
}
