package Data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import Login.Client;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddData")
public class AddData extends HttpServlet {

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        PrintWriter out = res.getWriter();
        out.println("<html><header><title>Login</title></header><body>");

        Connection con = null;
        try {
            con = DriverManager.getConnection(
                    getServletContext().getInitParameter("url"),
                    getServletContext().getInitParameter("login"),
                    getServletContext().getInitParameter("password"));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("erreur connection sql");
            out.println("connexion refusé");
            res.sendError(404, "connexion refusé !");
        }

        addClients(con, out, 100000);
        addRdv(con, out, 2022, true);
        addRdv(con, out, 2023, false);

    }

    private void suppressionDataTable(Connection con, String string) {
        try {
            PreparedStatement delete = con.prepareStatement(string);
            delete.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void addRdv(Connection con, PrintWriter out, int annee, boolean delete) {
        try {

            if(delete == true){
                suppressionDataTable(con, "DELETE from rdv");
            }
            
            String insertQuery = "INSERT INTO rdv (jour, heure, duree, nbPersonneMax, etat) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

            int heure = 8;
            List<LocalDate> joursDeLannee = listerJoursAnnee(annee);

            for (int j = 0; j < joursDeLannee.size(); j++) {

                for (int i = 0; i < 9; i++) {
                    preparedStatement.setObject(1, joursDeLannee.get(j));
                    preparedStatement.setObject(2, getRandomTime(LocalTime.of(heure++, 0), LocalTime.of(17, 45)));
                    preparedStatement.setInt(3, 15);
                    preparedStatement.setInt(4, 1);
                    preparedStatement.setString(5, "en attente");

                    // Exécutez la requête d'insertion
                    preparedStatement.executeUpdate();

                    if (heure == 17) {
                        heure = 8;
                    }
                }
            }

            out.println("Les rdv ont été insérés avec succès dans la base de données.");

        } catch (Exception e) {
            // TODO: handle exception
            out.println("Erreur : " + e.getMessage());
        }
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

    private static LocalTime getRandomTime(LocalTime startTime, LocalTime endTime) {
        if (startTime.isBefore(endTime)) {
            return startTime.plusHours(1); // Incrémentation d'une heure
        } else {
            return startTime;
        }
    }

    private void addClients(Connection con, PrintWriter out, int nb) {

        suppressionDataTable(con, "DELETE from client");


        try {

            String insertQuery = "INSERT INTO Client (nomC, prenomC, mailC, password, pro) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

            List<Client> clients = new ArrayList<>();
            for (int i = 0; i < nb; i++) {
                clients.add(new Client("Nom" + i, "Prenom" + i, "email" + i + "@example.com", "password" + i, false));
            }

            for (Client client : clients) {
                preparedStatement.setString(1, client.getNomC());
                preparedStatement.setString(2, client.getPrenomC());
                preparedStatement.setString(3, client.getMailC());
                preparedStatement.setString(4, client.getPassword());
                preparedStatement.setBoolean(5, client.getPro());

                // Exécutez la requête d'insertion
                preparedStatement.executeUpdate();
            }

            out.println("Les " + nb + " clients ont été insérés avec succès dans la base de données.");

        } catch (Exception e) {
            // TODO: handle exception
            out.println("Erreur : " + e.getMessage());
        }
    }

}
