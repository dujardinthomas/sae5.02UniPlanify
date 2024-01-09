package fr.sae502.uniplanify.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String urlphoto;

    //colone spring security qui indique le statut : ROLE_ADMIN ou ROLE_USER
    private String authority;

    @Column(name = "enabled", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean enabled;

    public UserAccount() {
    }

    public UserAccount(String nom, String prenom, String email, String password, String urlphoto, String authority,
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

    public void setPassword(String newPassword, String oldPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if( passwordEncoder.matches(oldPassword, this.getPassword()) ){
            System.out.println("ancien mot de passe saisie correct, on l'autorise a changer !");
            this.password = passwordEncoder.encode(newPassword);
        }else{
            System.out.println("ancien mot de passe saisie incorrect, on l'autorise pas a changer !");
        }

    }
}
