package fr.sae502.uniplanify.login;

import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import fr.sae502.uniplanify.models.Utilisateur;
//REFAIT LA SESSION POUR SPRING
@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionBean {
    private final String id = UUID.randomUUID().toString();

    private Utilisateur utilisateur;

    private String origine;

    public String getId() {
        return id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    

    
}
