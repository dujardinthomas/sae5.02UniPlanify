package fr.sae502.uniplanify.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String urlphoto;

    // @Column(name = "pro", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    // private boolean pro;

    //colone qui indique le statut : ROLE_ADMIN ou ROLE_USER
    private String authority;

    @Column(name = "enabled", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean enabled;

    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, String email, String password, String urlphoto, String authority,
            boolean enabled) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.urlphoto = urlphoto;
        this.authority = authority;
        this.enabled = enabled;
    }


    public boolean isPro() {
        return authority.equals("ROLE_ADMIN");
    }
}
