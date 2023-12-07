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

    @Column(name = "pro", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean pro;

    public Utilisateur(String nom, String prenom, String email, String password, boolean pro) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.pro = pro;
    }

    public Utilisateur() {
    }

    public boolean isPro() {
        return pro;
    }
}
