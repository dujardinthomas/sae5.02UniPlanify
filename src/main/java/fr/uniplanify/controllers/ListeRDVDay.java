package fr.uniplanify.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.uniplanify.models.dto.Creneau;
import fr.uniplanify.models.dto.Indisponibilite;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ListeRDVDay {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Map today = new HashMap<String, String>();
        today.put("heure", "etat : dispo, encore ou complet");
    }

    // public List<Creneau> getCreneauxDisponibles() throws SQLException {
    //     List<Creneau> creneaux = getCreneaux();

    //     List<Indisponibilite> indisponibilités = getIndisponibilitésEnCours();

    //     for (Creneau creneau : creneaux) {
    //         for (Indisponibilité indisponibilite : indisponibilités) {
    //             if (creneau.getDate().compareTo(indisponibilite.getDateDebut()) >= 0 &&
    //                     creneau.getDate().compareTo(indisponibilite.getDateFin()) <= 0) {
    //                 creneaux.remove(creneau);
    //                 break;
    //             }
    //         }
    //     }

    //     return creneaux;
    // }

}
