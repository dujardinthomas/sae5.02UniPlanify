package fr.sae502.uniplanify.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.sae502.uniplanify.login.SessionBean;
import fr.sae502.uniplanify.models.CleCompositeIndisponibilite;
import fr.sae502.uniplanify.models.EnvoieUnMail;
import fr.sae502.uniplanify.models.Indisponibilite;
import fr.sae502.uniplanify.models.Rdv;
import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.IndisponibiliteRepository;
import fr.sae502.uniplanify.repository.RdvRepository;

@Controller
@RequestMapping(value = "/pro")
public class IndisponibiliteController {

    @Autowired
    private IndisponibiliteRepository indisponibiliteRepository;

    @Autowired
    private RdvRepository rdvRepository;

    @Autowired
    private SessionBean sessionBean;

    @Autowired
    private JavaMailSender sender;

    @GetMapping("/indisponibilite")
    public String indisponibilite() {
        return "indisponibilite";
    }

    @PostMapping("/indispo")
    public ModelAndView enregistreIndispo(
            @RequestParam(value = "jour") LocalDate jour,
            @RequestParam(value = "debutheure") LocalTime debutheure,
            @RequestParam(value = "finheure") LocalTime finheure,
            @RequestParam(value = "motif") String motif) {
        ModelAndView mav = new ModelAndView("indispoConfirm");

        Indisponibilite indisponibilite = new Indisponibilite();
        CleCompositeIndisponibilite cleCompositeIndisponibilite = new CleCompositeIndisponibilite(jour, debutheure, finheure);
        indisponibilite.setCleCompositeIndisponibilite(cleCompositeIndisponibilite);
        indisponibilite.setMotif(motif);
        try {
            indisponibiliteRepository.save(indisponibilite);
            System.out.println("Indisponibilité enregistrée : " + indisponibilite);
            mav.addObject("indispo", indisponibilite);
        } catch (Exception e) {
            mav.addObject("indispo", null);
        }

        List<Rdv> hasDelete = removeRdvsReserves(jour, debutheure, finheure);
        mav.addObject("hasDeleteList", hasDelete);

        Utilisateur user = sessionBean.getUtilisateur();
        // Utilisateur user = utilisateurRepository.findById(1).orElse(null);
        mav.addObject("user", user);

        return mav;
    }

    /*
     * supprimer les rdv deja existant dans la base de donnée qui sont dans la plage
     * de temps de l'indisponibilité
     */
    private List<Rdv> removeRdvsReserves(LocalDate day, LocalTime startTime, LocalTime endTime) {
        List<Rdv> allRdvInIndispo = rdvRepository.findInIndispo(day, startTime, endTime);
        EnvoieUnMail envoieUnMail = new EnvoieUnMail();
        for (Rdv rdv : allRdvInIndispo) {
            for (Utilisateur user : rdv.getParticipants()) {
                System.out.println("mail envoyé à " + user.getEmail() + " : " + envoieUnMail.envoieMail(sender, user.getEmail(), "Rdv supprimé",
                        "Votre rdv " + rdv + " a été supprimé car il est dans une indisponibilité"));
            }
            rdvRepository.delete(rdv);
            System.out.println("Rdv supprimé : " + rdv);
        }
        return allRdvInIndispo;
    }

    


    @GetMapping("/confirmSuppressionIndispo")
    public ModelAndView confirmSuppressionIndispo(@RequestParam(value = "jour") LocalDate debutJour,
            @RequestParam(value = "debutHeure") LocalTime debutHeure,
            @RequestParam(value = "finHeure") LocalTime finHeure) {
        ModelAndView mav = new ModelAndView("confirmSuppressionIndispo");
        mav.addObject("indispo", new Indisponibilite(new CleCompositeIndisponibilite(debutJour, debutHeure, finHeure)));
        return mav;
    }

    @PostMapping("/suppressionIndispo")
    public ModelAndView suppressionIndispo(
            @RequestParam(value = "jour") LocalDate debutjour,
            @RequestParam(value = "debutHeure") LocalTime debutheure,
            @RequestParam(value = "finHeure") LocalTime finheure,
            @RequestParam(value = "reponse") String reponse) {
        ModelAndView mav = new ModelAndView("redirect:/pro");

        if(reponse.equals("non")) {
            return mav;
        }

        Indisponibilite indisponibilite = new Indisponibilite();
        CleCompositeIndisponibilite cleCompositeIndisponibilite = new CleCompositeIndisponibilite(debutjour, debutheure, finheure);
        indisponibilite.setCleCompositeIndisponibilite(cleCompositeIndisponibilite);
        try {
            indisponibiliteRepository.delete(indisponibilite);
            System.out.println("Indisponibilité supprimée : " + indisponibilite);
            mav.addObject("indispo", indisponibilite);
        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression de l'indisponibilité : " + indisponibilite);
            System.out.println(e.getMessage());
            mav.addObject("indispo", null);
        }

        return mav;
    }


}
