// package fr.uniplanify;

// import java.time.LocalDate;
// import java.time.LocalTime;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import fr.uniplanify.models.dao.ClientDAO;
// import fr.uniplanify.models.dao.RdvClientDAO;
// import fr.uniplanify.models.dao.RdvDAO;
// import fr.uniplanify.models.dto.Client;
// import fr.uniplanify.models.dto.Rdv;

// public class TestsAleaData {

//     ClientDAO clientDAO = new ClientDAO();
//     RdvClientDAO rdvClientDAO = new RdvClientDAO();
//     RdvDAO rdvDAO = new RdvDAO();

//     int duree = 15;
//     int nbPersonneMax = 1;

//     public boolean createClient(int nb) {
//         for (int c = 1; c <= nb; c++) {
//             // System.out.println(
//             clientDAO.createClient(new Client("nom" + c, "prenom" + c, "mail" + c,
//                     "password" + c, false));
//         }
//         return true;

//     }

//     public boolean createRDVForYear(int year) {
//         List<LocalDate> joursDeLannee = listerJoursAnnee(year);
//         for (int j = 0; j < joursDeLannee.size(); j++) {
//             for (int h = 8; h <= 17; h++) {
//                 for (int m = 0; m < 60; m = m + 15) {
//                     LocalDate d = joursDeLannee.get(j);
//                     LocalTime t = AllowRDV(LocalTime.of(h, m), LocalTime.of(17, 45));
//                     if (t == null) {
//                         // rdv non autorisé depasse la plage horaire revenir au lendemain
//                         break;
//                     }
//                     System.out.println(rdvDAO.createRDV(new Rdv(
//                             d,
//                             t,
//                             "en attente",
//                             // rdvClientDAO.getAllClientsInRDV(d, t)
//                             // rdvClientDAO.createRdvClient(d, t, j)
//                             new ArrayList<>(Arrays.asList(clientDAO.getClientByIdC(j))))));
//                 }
//             }
//         }
//         return false;
//     }

//     private List<LocalDate> listerJoursAnnee(int annee) {
//         List<LocalDate> jours = new ArrayList<>();
//         LocalDate dateDebut = LocalDate.of(annee, 1, 1); // Premier jour de l'année
//         LocalDate dateFin = LocalDate.of(annee, 12, 31); // Dernier jour de l'année
//         while (!dateDebut.isAfter(dateFin)) {
//             jours.add(dateDebut);
//             dateDebut = dateDebut.plusDays(1); // Incrémente d'un jour
//         }
//         return jours;
//     }

//     private LocalTime AllowRDV(LocalTime startTime, LocalTime endTime) {
//         if (startTime.isBefore(endTime) || startTime.plusMinutes(duree) == endTime) {
//             return startTime; // RDV AUTORISE
//         } else {
//             return null;
//         }
//     }
// }
