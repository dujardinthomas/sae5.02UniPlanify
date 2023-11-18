package fr.uniplanify.controllers;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.uniplanify.models.dto.Constraints;
import fr.uniplanify.models.dto.JourneeTypePro;

// import fr.uniplanify.models.constraints.Medecin;
// import fr.uniplanify.models.dao.ClientDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Pro/Initialisation")
public class Initialisation extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencexml");
        EntityManager em = emf.createEntityManager();

        String[] joursTravailles = req.getParameterValues("jour_travaille[]");

        if (joursTravailles != null) {

            Constraints cons = new Constraints();

            int dureeDefaut = Integer.parseInt(req.getParameter("dureeDefaut"));
            int nbPersonneMax = Integer.parseInt(req.getParameter("nbPersonneMax"));
            cons.setDureeDefaultMinutes(dureeDefaut);
            cons.setNbPersonneMaxDefault(nbPersonneMax);

            em.getTransaction().begin();
            em.persist(cons);
            em.getTransaction().commit();
            System.out.println("contraintes enregistrées : " + dureeDefaut + " min par rdv avec " + nbPersonneMax
                    + " personnes maxi/rdv");

            int nbJours = joursTravailles.length;

            String[] joursWork = new String[nbJours];
            int[][] heuresStartWork = new int[nbJours][2]; //heure + min
            int[][] heuresEndWork = new int[nbJours][2];

            for (int i = 0; i < nbJours; i++) {
                // Traitez chaque jour coché ici

                String debut = req.getParameter("debut_" + joursTravailles[i]);
                String fin = req.getParameter("fin_" + joursTravailles[i]);

                System.out.println("Jour travaillé : " + joursTravailles[i] + " de " + debut + " a " + fin);

                joursWork[i] = joursTravailles[i];
                heuresStartWork[i][0] = Integer.parseInt(debut.split(":")[0].toString());
                heuresStartWork[i][1] = Integer.parseInt(debut.split(":")[1].toString());

                heuresEndWork[i][0] = Integer.parseInt(fin.split(":")[0].toString());
                heuresEndWork[i][1] = Integer.parseInt(fin.split(":")[1].toString());

            }

            List<JourneeTypePro> weekWork = new ArrayList<>();
            for (int j = 0; j < joursWork.length; j++) {
                weekWork.add(new JourneeTypePro(joursWork[j].toLowerCase(),
                        LocalTime.of(heuresStartWork[j][0], heuresStartWork[j][1]),
                        LocalTime.of(heuresEndWork[j][0], heuresEndWork[j][1])));
            }
            System.out.println(weekWork.toString());

            for (JourneeTypePro journeeTypePro : weekWork) {
                em.getTransaction().begin();
                em.persist(journeeTypePro);
                em.getTransaction().commit();
                System.out.println(journeeTypePro + " ajouté dans la base de donnée !");
            }

            System.out.println("toute la semaine type à été ajouté a la bdd !");

        } else {
            System.out.println("Aucun jour sélectionné");
        }

        res.sendRedirect("../Deconnect");

    }

}
