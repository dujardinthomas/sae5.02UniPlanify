package fr.sae502.uniplanify.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.sae502.uniplanify.SessionBean;
import fr.sae502.uniplanify.models.CleCompositeIndisponibilite;
import fr.sae502.uniplanify.models.CleCompositeRDV;
import fr.sae502.uniplanify.models.Indisponibilite;
import fr.sae502.uniplanify.models.Rdv;
import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.IndisponibiliteRepository;
import fr.sae502.uniplanify.repository.RdvRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Controller
@RequestMapping(value = "/pro")
public class IndisponibiliteController {

    @Autowired
    private IndisponibiliteRepository indisponibiliteRepository;

    @Autowired
    private RdvRepository rdvRepository;

    @Autowired
    private SessionBean sessionBean;

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
            @RequestParam(value = "motif") String motif
            ) {
        ModelAndView mav = new ModelAndView("indispoConfirm");

        Indisponibilite indisponibilite = new Indisponibilite();
        CleCompositeIndisponibilite cleCompositeIndisponibilite = new CleCompositeIndisponibilite(debutjour, debutheure, finjour, finheure);
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
     * supprimer les rdv deja existant dans la base de donnée qui sont dans la plage de temps de l'indisponibilité
     */
    private List<Rdv> removeRdvsReserves(LocalDate startDay, LocalTime startTime, LocalDate endDay, LocalTime endTime) {
        boolean res = false;
        List<Rdv> allRdvInIndispo = rdvRepository.findInIndispo(startDay, startTime, endDay, endTime);
        for (Rdv rdv : allRdvInIndispo) {
            //CleCompositeRDV cleCompositeRDV = new CleCompositeRDV(rdv.getJour(), rdv.getHeure());
            System.out.println("on essaye de supprimer le rdv : " + rdv);
            rdvRepository.delete(rdv);
            System.out.println("Rdv supprimé : " + rdv);
        }
        res = true;
        return allRdvInIndispo;
    }

}
