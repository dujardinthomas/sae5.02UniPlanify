package fr.uniplanify.models.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Rdv.findAll", query = "SELECT r from Rdv r")
public class Rdv implements Serializable {

    @EmbeddedId // Annotation pour indiquer l'utilisation d'une clé primaire composite
    private CleCompositeRDV cleCompositeRDV;

    private String etat;
    private List<Client> clients;

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void addClient(Client client) {
        if (this.clients == null) {
            this.clients = new ArrayList<>();
        }
        this.clients.add(client);
    }

    public CleCompositeRDV getCleCompositeRDV() {
        return cleCompositeRDV;
    }

    public void setCleCompositeRDV(CleCompositeRDV cleCompositeRDV) {
        this.cleCompositeRDV = cleCompositeRDV;
    }

    @Override
    public String toString() {
        return "Rdv [cleCompositeRDV=" + cleCompositeRDV + ", etat=" + etat + ", clients=" + clients + "]";
    }

}
