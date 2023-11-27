package fr.uniplanify.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;

import fr.uniplanify.models.dto.Indisponibilite;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Pro/Indisponibilite")
public class Indispo extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

            String[] debutjour = req.getParameter("debutjour").split("-");
            String[] debutheure = req.getParameter("debutheure").split(":");

            String[] finjour = req.getParameter("finjour").split("-");
            String[] finheure = req.getParameter("finheure").split(":");


            System.out.println("vous devez implementé les indisponibilites");
            System.out.println(debutjour + " " + debutheure);
            System.out.println(finjour + " " + finheure);


            LocalDate startDay = LocalDate.of(Integer.parseInt(debutjour[0]), Integer.parseInt(debutjour[1]), Integer.parseInt(debutjour[2]));
            LocalDate endDay = LocalDate.of(Integer.parseInt(finjour[0]), Integer.parseInt(finjour[1]), Integer.parseInt(finjour[2]));

            LocalTime startTime = LocalTime.of(Integer.parseInt(debutheure[0]), Integer.parseInt(debutheure[1]));
            LocalTime endTime = LocalTime.of(Integer.parseInt(finheure[0]), Integer.parseInt(finheure[1]));

            System.out.println("indisponibilites : ");
            System.out.println(startDay + " " + startTime);
            System.out.println(endDay + " " + endTime);


            Indisponibilite indispo = new Indisponibilite(startDay, startTime, endDay, endTime);

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("no-action-bdd");
            EntityManager em = emf.createEntityManager();


            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();

            try {
                em.getTransaction().begin();
                em.persist(indispo);
                em.getTransaction().commit();
                out.println("<h1>Indisponibilité enregistré !</h1>");
                
            } catch (Exception e) {
                out.println("<h1>Erreur d'enregistrement de l'indisponibilité!</h1>");
            }



    }

}
