package fr.sae502.uniplanify.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.sae502.uniplanify.SessionBean;
import fr.sae502.uniplanify.models.CleCompositeIndisponibilite;
import fr.sae502.uniplanify.models.Indisponibilite;
import fr.sae502.uniplanify.models.Rdv;
import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.IndisponibiliteRepository;
import fr.sae502.uniplanify.repository.RdvRepository;
import jakarta.mail.internet.MimeMessage;

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
            @RequestParam(value = "debutjour") LocalDate debutjour,
            @RequestParam(value = "debutheure") LocalTime debutheure,
            @RequestParam(value = "finjour") LocalDate finjour,
            @RequestParam(value = "finheure") LocalTime finheure,
            @RequestParam(value = "motif") String motif) {
        ModelAndView mav = new ModelAndView("indispoConfirm");

        Indisponibilite indisponibilite = new Indisponibilite();
        CleCompositeIndisponibilite cleCompositeIndisponibilite = new CleCompositeIndisponibilite(debutjour, debutheure,
                finjour, finheure);
        indisponibilite.setCleCompositeIndisponibilite(cleCompositeIndisponibilite);
        indisponibilite.setMotif(motif);
        try {
            indisponibiliteRepository.save(indisponibilite);
            System.out.println("Indisponibilité enregistrée : " + indisponibilite);
            mav.addObject("indispo", indisponibilite);
        } catch (Exception e) {
            mav.addObject("indispo", null);
        }

        List<Rdv> hasDelete = removeRdvsReserves(debutjour, debutheure, finjour, finheure);
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
    private List<Rdv> removeRdvsReserves(LocalDate startDay, LocalTime startTime, LocalDate endDay, LocalTime endTime) {
        List<Rdv> allRdvInIndispo = rdvRepository.findInIndispo(startDay, startTime, endDay, endTime);
        for (Rdv rdv : allRdvInIndispo) {
            for (Utilisateur user : rdv.getParticipants()) {
                System.out.println("mail envoyé à " + user.getEmail() + " : " + envoieMail(user.getEmail(), "Rdv supprimé",
                        "Votre rdv " + rdv + " a été supprimé car il est dans une indisponibilité"));
            }
            rdvRepository.delete(rdv);
            System.out.println("Rdv supprimé : " + rdv);
        }
        return allRdvInIndispo;
    }

    public boolean envoieMail(String to, String subject, String text) {
        try {
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("thomas.dujardin2.etu@univ-lille.fr");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText("supprime");
            System.out.println("on va envoyer le mail à " + to + " : avec comme contenu :\n" + text + "");
            sender.send(message);
            return true;
        } catch (Exception e) {
            System.out.println("Erreur lors de l'envoie du mail : " + e.getMessage());
            return false;
        }
    }


    @GetMapping("/confirmSuppressionIndispo")
    public ModelAndView confirmSuppressionIndispo(@RequestParam(value = "debutJour") LocalDate debutJour,
            @RequestParam(value = "debutHeure") LocalTime debutHeure,
            @RequestParam(value = "finJour") LocalDate finJour,
            @RequestParam(value = "finHeure") LocalTime finHeure) {
        ModelAndView mav = new ModelAndView("confirmSuppressionIndispo");
        mav.addObject("indispo", new Indisponibilite(new CleCompositeIndisponibilite(debutJour, debutHeure, finJour, finHeure)));
        return mav;
    }

    @PostMapping("/suppressionIndispo")
    public ModelAndView suppressionIndispo(
            @RequestParam(value = "debutJour") LocalDate debutjour,
            @RequestParam(value = "debutHeure") LocalTime debutheure,
            @RequestParam(value = "finJour") LocalDate finjour,
            @RequestParam(value = "finHeure") LocalTime finheure,
            @RequestParam(value = "reponse") String reponse) {
        ModelAndView mav = new ModelAndView("redirect:/pro");

        if(reponse.equals("non")) {
            return mav;
        }

        Indisponibilite indisponibilite = new Indisponibilite();
        CleCompositeIndisponibilite cleCompositeIndisponibilite = new CleCompositeIndisponibilite(debutjour, debutheure,
                finjour, finheure);
        indisponibilite.setCleCompositeIndisponibilite(cleCompositeIndisponibilite);
        try {
            indisponibiliteRepository.delete(indisponibilite);
            System.out.println("Indisponibilité supprimée : " + indisponibilite);
            mav.addObject("indispo", indisponibilite);
        } catch (Exception e) {
            mav.addObject("indispo", null);
        }

        return mav;
    }


}
