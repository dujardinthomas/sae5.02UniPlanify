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

import fr.sae502.uniplanify.models.CleCompositeIndisponibilite;
import fr.sae502.uniplanify.models.EnvoieUnMail;
import fr.sae502.uniplanify.models.Indisponibilite;
import fr.sae502.uniplanify.models.Rdv;
import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.IndisponibiliteRepository;
import fr.sae502.uniplanify.repository.RdvRepository;
import fr.sae502.uniplanify.repository.UtilisateurRepository;

@Controller
@RequestMapping(value = "/pro")
public class IndisponibiliteController {

    @Autowired
    private IndisponibiliteRepository indisponibiliteRepository;

    @Autowired
    private RdvRepository rdvRepository;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private Utilisateur user;

    @GetMapping("/indisponibilite")
    public String indisponibilite() {
        return "indisponibilite";
    }

    @PostMapping("/indispo")
    public String enregistreIndispo(
            @RequestParam(value = "jour") LocalDate jour,
            @RequestParam(value = "debutheure") LocalTime debutheure,
            @RequestParam(value = "finheure") LocalTime finheure,
            @RequestParam(value = "motif") String motif,
            Principal principal, Model model) {

        Indisponibilite indisponibilite = new Indisponibilite();
        CleCompositeIndisponibilite cleCompositeIndisponibilite = new CleCompositeIndisponibilite(jour, debutheure,
                finheure);
        indisponibilite.setCleCompositeIndisponibilite(cleCompositeIndisponibilite);
        indisponibilite.setMotif(motif);
        try {
            indisponibiliteRepository.save(indisponibilite);
            System.out.println("Indisponibilité enregistrée : " + indisponibilite);
            model.addAttribute("indispo", indisponibilite);
        } catch (Exception e) {
            model.addAttribute("indispo", null);
        }

        List<Rdv> hasDelete = removeRdvsReserves(jour, debutheure, finheure);
        model.addAttribute("hasDeleteList", hasDelete);

        user = utilisateurRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);

        return "indispoConfirm";
    }

    /*
     * supprimer les rdv deja existant dans la base de donnée qui sont dans la plage
     * de temps de l'indisponibilité
     */
    private List<Rdv> removeRdvsReserves(LocalDate day, LocalTime startTime, LocalTime endTime) {
        List<Rdv> allRdvInIndispo = rdvRepository.findInPeriode(day, startTime, endTime);
        EnvoieUnMail envoieUnMail = new EnvoieUnMail();
        for (Rdv rdv : allRdvInIndispo) {
            for (Utilisateur user : rdv.getParticipants()) {
                System.out.println("mail envoyé à " + user.getEmail() + " : "
                        + envoieUnMail.envoieMail(sender, user.getEmail(), "Rdv supprimé",
                                "Votre rdv " + rdv + " a été supprimé car il est dans une indisponibilité"));
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
                new Indisponibilite(new CleCompositeIndisponibilite(debutJour, debutHeure, finHeure)));
        return "confirmSuppressionIndispo";
    }

    @PostMapping("/suppressionIndispo")
    public String suppressionIndispo(
            @RequestParam(value = "jour") LocalDate debutjour,
            @RequestParam(value = "debutHeure") LocalTime debutheure,
            @RequestParam(value = "finHeure") LocalTime finheure,
            @RequestParam(value = "reponse") String reponse,
            Model model) {

        if (!reponse.equals("non")) {

            Indisponibilite indisponibilite = new Indisponibilite();
            CleCompositeIndisponibilite cleCompositeIndisponibilite = new CleCompositeIndisponibilite(debutjour,
                    debutheure,
                    finheure);
            indisponibilite.setCleCompositeIndisponibilite(cleCompositeIndisponibilite);
            try {
                indisponibiliteRepository.delete(indisponibilite);
                System.out.println("Indisponibilité supprimée : " + indisponibilite);
                model.addAttribute("indispo", indisponibilite);
            } catch (Exception e) {
                System.out.println("Erreur lors de la suppression de l'indisponibilité : " + indisponibilite);
                System.out.println(e.getMessage());
                model.addAttribute("indispo", null);
            }
        }
        return "redirect:/pro";
    }

}
