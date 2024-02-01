package fr.sae502.uniplanify.controllers;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.sae502.uniplanify.models.CompositeKeyUnavailability;
import fr.sae502.uniplanify.models.utils.SenderEmail;
import fr.sae502.uniplanify.models.Unavailability;
import fr.sae502.uniplanify.models.Rdv;
import fr.sae502.uniplanify.models.UserAccount;
import fr.sae502.uniplanify.models.repository.UnavailabilityRepository;
import fr.sae502.uniplanify.models.repository.RdvRepository;
import fr.sae502.uniplanify.models.repository.UserAccountRepository;

@Controller
@RequestMapping(value = "/pro")
public class UnavailabilityController {

    @Autowired
    private UnavailabilityRepository indisponibiliteRepository;

    @Autowired
    private RdvRepository rdvRepository;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private UserAccountRepository utilisateurRepository;

    private UserAccount user;

    @GetMapping("/indisponibilite")
    public String indisponibilite() {
        return "indisponibilite/indisponibilite";
    }

    @PostMapping("/indispo")
    public String enregistreIndispo(
            @RequestParam(value = "jour") LocalDate jour,
            @RequestParam(value = "debutheure") LocalTime debutheure,
            @RequestParam(value = "finheure") LocalTime finheure,
            @RequestParam(value = "motif") String motif,
            Principal principal, Model model) {

        Unavailability unavailability = new Unavailability();
        unavailability.setCompositeKeyUnavailability(new CompositeKeyUnavailability(jour, debutheure, finheure));
        unavailability.setMotif(motif);
        try {
            indisponibiliteRepository.save(unavailability);
            System.out.println("Indisponibilité enregistrée : " + unavailability);
            model.addAttribute("indispo", unavailability);
        } catch (Exception e) {
            model.addAttribute("indispo", null);
        }

        List<Rdv> hasDelete = removeRdvsReserves(jour, debutheure, finheure);
        model.addAttribute("hasDeleteList", hasDelete);

        user = utilisateurRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);

        return "indisponibilite/indispoConfirm";
    }

    /*
     * supprimer les rdv deja existant dans la base de donnée qui sont dans la plage
     * de temps de l'indisponibilité
     */
    private List<Rdv> removeRdvsReserves(LocalDate day, LocalTime startTime, LocalTime endTime) {
        List<Rdv> allRdvInIndispo = rdvRepository.findByCompositeKeyRDVDayRdvAndCompositeKeyRDVTimeRdvBetween(day, startTime, endTime);
        SenderEmail senderEmail = new SenderEmail();
        for (Rdv rdv : allRdvInIndispo) {
            for (UserAccount user : rdv.getParticipants()) {
                System.out.println("mail envoyé à " + user.getEmail() + " : "
                        + senderEmail.sendEmail(sender, user.getEmail(), "Rdv supprimé",
                        "Bonjour " + user.getPrenom() + ", \n\nvotre  rendez-vous du " + rdv.dateToString() + " à "
                                    + rdv.getHours() + " heures " + rdv.getMinutes() + " a été supprimé car il est dans une indisponibilité \\n" + //
                                            "\\n" + //
                                            "Cordialement,\\n" + //
                                            "\\n" + //
                                            "L'équipe Uniplanify"));
            }
            rdvRepository.delete(rdv);
            System.out.println("Rdv supprimé : " + rdv);
        }
        return allRdvInIndispo;
    }

    @GetMapping("/confirmSuppressionIndispo")
    public String confirmSuppressionIndispo(@RequestParam(value = "jour") LocalDate debutJour,
            @RequestParam(value = "debutHeure") LocalTime debutHeure,
            @RequestParam(value = "finHeure") LocalTime finHeure,
            Model model) {

        model.addAttribute("indispo",
                new Unavailability(new CompositeKeyUnavailability(debutJour, debutHeure, finHeure)));
        return "indisponibilite/confirmSuppressionIndispo";
    }

    @PostMapping("/suppressionIndispo")
    public String suppressionIndispo(
            @RequestParam(value = "jour") LocalDate debutjour,
            @RequestParam(value = "debutHeure") LocalTime debutheure,
            @RequestParam(value = "finHeure") LocalTime finheure,
            @RequestParam(value = "reponse") String reponse,
            Model model) {

        if (!reponse.equals("non")) {

            Unavailability unavailability = new Unavailability();
            unavailability.setCompositeKeyUnavailability(new CompositeKeyUnavailability(debutjour,debutheure,finheure));
            try {
                indisponibiliteRepository.delete(unavailability);
                System.out.println("Indisponibilité supprimée : " + unavailability);
                model.addAttribute("indispo", unavailability);
            } catch (Exception e) {
                System.out.println("Erreur lors de la suppression de l'indisponibilité : " + unavailability);
                System.out.println(e.getMessage());
                model.addAttribute("indispo", null);
            }
        }
        return "redirect:/pro";
    }

}
