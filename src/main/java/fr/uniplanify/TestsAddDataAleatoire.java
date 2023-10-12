package fr.uniplanify;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.uniplanify.dao.ClientDAO;
import fr.uniplanify.dao.RdvClientDAO;
import fr.uniplanify.dao.RdvDAO;
import fr.uniplanify.dto.Rdv;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/TestsAddDataAleatoire")
public class TestsAddDataAleatoire extends HttpServlet {
    ClientDAO clientDAO = new ClientDAO();
    RdvClientDAO rdvClientDAO = new RdvClientDAO();
    RdvDAO rdvDAO = new RdvDAO();

    int duree = 15;
    int nbPersonneMax = 1;
    

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // for (int c = 0; c < 2000; c++) {
        //    // System.out.println(
        //             clientDAO.createClient(new Client(c, "nom" + c, "prenom" + c, "mail" + c, "password" + c, false)); 
        //             //+ " ajouté!");
        //     // crée par défaut un user standart : il doit y avoir qu'un seul pro qu'on crée
        //     // au début
        // }

        List<LocalDate> joursDeLannee = listerJoursAnnee(2022);
        for (int j = 0; j < joursDeLannee.size(); j++) {
            for (int h = 8; h < 17; h++) {

                LocalDate d = joursDeLannee.get(j);
                LocalTime t = getRandomTime(LocalTime.of(h, 0), LocalTime.of(17, 45));

                System.out.println(rdvDAO.createRDV(new Rdv(
                        d,
                        t,
                        duree,
                        nbPersonneMax,
                        "en attente",
                       // rdvClientDAO.getAllClientsInRDV(d, t)
                      // rdvClientDAO.createRdvClient(d, t, j)
                      new ArrayList<>(Arrays.asList(clientDAO.getClientByIdC(j)))
                        )));

                // crée par défaut un user standart : il doit y avoir qu'un seul pro qu'on crée
                // au début
            }
        }

        // String name = req.getParameter("name") ;
        // String prenom = req.getParameter("prenom");
        // String mail = req.getParameter("mail");
        // String password = req.getParameter("password");

        // if(name == null || prenom == null || mail == null || password ==null){
        // System.out.println("certains paramètres sont manquants veuillez ressayer !");
        // res.sendError(400, "certains paramètres sont manquants veuillez ressayer !");
        // }else{
        // System.out.println(client.createClient("DUJARDIN", "Véronique",
        // "contact@dujardin-neurofeedback-dynamique.fr", "vero",true)); //crée par
        // défaut un user standart : il doit y avoir qu'un seul pro qu'on crée au début
        // for(int i=0; i<10000 ; i++){
        // System.out.println(client.createClient(name, prenom, mail, password,false));
        // //crée par défaut un user standart : il doit y avoir qu'un seul pro qu'on
        // crée au début
        // }
        // }
    }

    private List<LocalDate> listerJoursAnnee(int annee) {
        List<LocalDate> jours = new ArrayList<>();
        LocalDate dateDebut = LocalDate.of(annee, 1, 1); // Premier jour de l'année
        LocalDate dateFin = LocalDate.of(annee, 12, 31); // Dernier jour de l'année
        while (!dateDebut.isAfter(dateFin)) {
            jours.add(dateDebut);
            dateDebut = dateDebut.plusDays(1); // Incrémente d'un jour
        }
        return jours;
    }

    private LocalTime getRandomTime(LocalTime startTime, LocalTime endTime) {
        if (startTime.isBefore(endTime)) {
            return startTime.plusHours(1); // Incrémentation d'une heure
        } else {
            return startTime;
        }
    }

}
