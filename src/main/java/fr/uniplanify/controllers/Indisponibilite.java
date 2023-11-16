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

@WebServlet("/Pro/Indisponibilite")
public class Indisponibilite extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

            String debutjour = req.getParameter("debutjour");
            String debutheure = req.getParameter("debutheure");

            String finjour = req.getParameter("finjour");
            String finheure = req.getParameter("finheure");


            System.out.println("vous devez implementé les indisponibilites");
            System.out.println(debutjour + " " + debutheure);
            System.out.println(finjour + " " + finheure);
            

    }

}
