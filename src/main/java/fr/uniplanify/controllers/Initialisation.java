package fr.uniplanify.controllers;

import java.io.IOException;

import fr.uniplanify.TestsAleaData;
import fr.uniplanify.models.CreateDataBase;
import fr.uniplanify.models.constraints.Medecin;
import fr.uniplanify.models.dao.SemaineTypeProDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Pro/Initialisation")
public class Initialisation extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String[] joursTravailles = req.getParameterValues("jour_travaille[]");

        CreateDataBase cdb = new CreateDataBase();
        cdb.createnewdatabase();

        if (joursTravailles != null) {

            int dureeDefaut = Integer.parseInt(req.getParameter("dureeDefaut"));
            int nbPersonneMax = Integer.parseInt(req.getParameter("nbPersonneMax"));

            int nbJours = joursTravailles.length;

            String[] jours = new String[nbJours];
            int[][] debuts = new int[nbJours][2];
            int[][] fins = new int[nbJours][2];

            for (int i = 0; i < nbJours; i++) {
                // Traitez chaque jour coché ici

                String debut = req.getParameter("debut_" + joursTravailles[i]);
                String fin = req.getParameter("fin_" + joursTravailles[i]);

                System.out.println("Jour travaillé : " + joursTravailles[i] + " de " + debut + " a " + fin);

                jours[i] = joursTravailles[i];
                debuts[i][0] = Integer.parseInt(debut.split(":")[0].toString());
                debuts[i][1] = Integer.parseInt(debut.split(":")[1].toString());

                fins[i][0] = Integer.parseInt(fin.split(":")[0].toString());
                fins[i][1] = Integer.parseInt(fin.split(":")[1].toString());

            }

            Medecin patrice = new Medecin(dureeDefaut, nbPersonneMax, jours, debuts, fins);

        } else {
            System.out.println("Aucun jour sélectionné");
        }

        TestsAleaData tests = new TestsAleaData();
        tests.createClient(10);
    }

}
