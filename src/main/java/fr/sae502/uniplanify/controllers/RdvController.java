package fr.sae502.uniplanify.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.sae502.uniplanify.login.SessionBean;
import fr.sae502.uniplanify.models.CleCompositeRDV;
import fr.sae502.uniplanify.models.Contraintes;
import fr.sae502.uniplanify.models.EnvoieUnMail;
import fr.sae502.uniplanify.models.Rdv;
import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.ContraintesRepository;
import fr.sae502.uniplanify.repository.IndisponibiliteRepository;
import fr.sae502.uniplanify.repository.RdvRepository;

@Controller
@Component
@RequestMapping(value = "/rdv")
public class RdvController {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private RdvRepository rdvRepository;
    @Autowired
    private ContraintesRepository contraintesRepository;

    @Autowired
    private IndisponibiliteRepository indisponibiliteRepository;

    @Autowired
    private SessionBean sessionBean;

    @GetMapping("/confirmSuppressionRDV")
    public ModelAndView modifyRDV(@RequestParam(value = "year") int year,
            @RequestParam(value = "month") int month,
            @RequestParam(value = "day") int day,
            @RequestParam(value = "hours") int hours,
            @RequestParam(value = "minutes") int minutes
            ) {
        
        ModelAndView mav = new ModelAndView("confirmSuppressionRdv");
        mav.addObject("rdv", new Rdv(new CleCompositeRDV(LocalDate.of(year, month, day), LocalTime.of(hours, minutes, 0)), null, null));
        return mav;
    }

    @PostMapping("/suppressionRdv")
    public ModelAndView confirmSuppressionRDV(@RequestParam(value = "year", required = false) int year,
            @RequestParam(value = "month", required = false) int month,
            @RequestParam(value = "day", required = false) int day,
            @RequestParam(value = "hours", required = false) int hours,
            @RequestParam(value = "minutes", required = false) int minutes,
            @RequestParam(value = "reponse") String reponse,
            @RequestParam(value = "raison") String raison
            ) {

        ModelAndView mav = new ModelAndView("redirect:/perso");

        if(reponse.equals("non")) {
            return mav;
        }

        Rdv rdvExistantASuppr = rdvRepository.findById(new CleCompositeRDV(LocalDate.of(year, month, day), LocalTime.of(hours, minutes, 0))).orElse(null);
        try {
            
            EnvoieUnMail envoieUnMail = new EnvoieUnMail();
            for(Utilisateur participant : rdvExistantASuppr.getParticipants()) {
                System.out.println("envoie du mail à " + participant.getEmail() + " : " + envoieUnMail.envoieMail(sender, participant.getEmail(), "Annulation de votre rendez-vous", "Votre rendez-vous du " + rdvExistantASuppr.getCleCompositeRDV().getJour() + " à " + rdvExistantASuppr.getCleCompositeRDV().getHeure() + " a été annulé pour la raison suivante : " + raison));
            }

            rdvRepository.delete(rdvExistantASuppr);
            System.out.println("Rdv supprimé : " + rdvExistantASuppr);

        } catch (Exception e) {
            System.out.println("Erreur lors de la suppression du rdv : " + rdvExistantASuppr);
            System.out.println(e.getMessage());
        }
        return mav;
    }

    @GetMapping(value = "/reserve")
    public ModelAndView reserve(@RequestParam(value = "year", required = false) int year,
            @RequestParam(value = "month", required = false) int month,
            @RequestParam(value = "day", required = false) int day,
            @RequestParam(value = "hours", required = false) int hours,
            @RequestParam(value = "minutes", required = false) int minutes
            ) {

        ModelAndView mav = new ModelAndView("reservation");

        LocalDate dateDuRdv = LocalDate.of(year, month, day);
        LocalTime heureDuRdv = LocalTime.of(hours, minutes, 0);
        System.out.println(dateDuRdv);

        Utilisateur user = sessionBean.getUtilisateur();
        // Utilisateur user = utilisateurRepository.findById(1).orElse(null);
        mav.addObject("user", user);
        
        Map.Entry<Boolean,String> rdvPossibleStatus = getBooleanRdvPossible(dateDuRdv, heureDuRdv).entrySet().iterator().next();
        
        if(!rdvPossibleStatus.getKey()) {
            mav.addObject("status", rdvPossibleStatus.getValue());
            mav.addObject("rdv", new Rdv(new CleCompositeRDV(dateDuRdv, heureDuRdv), null, null));
            return mav;
        }
        LocalDateTime heureActuelle = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yy");
        String jjMMyy = heureActuelle.format(format);
        String commentaire = "reservé le " + jjMMyy;

        CleCompositeRDV cleRDV = new CleCompositeRDV();
        cleRDV.setHeure(heureDuRdv);
        cleRDV.setJour(dateDuRdv);
        Rdv rdvExistant = rdvRepository.findById(cleRDV).orElse(null);

        Contraintes contraintes = contraintesRepository.findAll().iterator().next();
        int nbPersonne = contraintes.getNbPersonneMaxDefault();
        
        String etat = "";

        if (rdvExistant != null) {
            // rdv a déja été reservé mais peut etre qu'on peut ajouter des gens
            if (rdvExistant.getParticipants().size() < nbPersonne) {
                // on peut ajouter un client

                try {
                    rdvExistant.addParticipant(user);
                    rdvExistant.setCommentaire(user.getNom() + " ajouté le " + jjMMyy);
                    rdvExistant.recalculerRemplissagePourcentage(contraintesRepository);
                    
                    rdvRepository.save(rdvExistant);
                    etat = "add";
                    
                } catch (Exception e) {
                    etat = "error";
                }
                
            } else {
                etat = "plein";
            }
            mav.addObject("status", etat);
            mav.addObject("rdv", rdvExistant);
        } else {

            Rdv nouveauRdv = new Rdv();
            nouveauRdv.setCleCompositeRDV(cleRDV);
            nouveauRdv.addParticipant(user);
            nouveauRdv.setCommentaire(commentaire);
            nouveauRdv.setEtat("Réservé");
            nouveauRdv.recalculerRemplissagePourcentage(contraintesRepository);
            
            rdvRepository.save(nouveauRdv);
            String statut = "created";
            mav.addObject("status", statut);
            mav.addObject("rdv", nouveauRdv);
        }        
        return mav;
    }

    private Map<Boolean, String> getBooleanRdvPossible(LocalDate dateDuRdv, LocalTime heureDuRdv) {
        Map<Boolean, String> statut = new HashMap<>();
        LocalDateTime heureActuelle = LocalDateTime.now();
        LocalDateTime heureDuRdvComplete = LocalDateTime.of(dateDuRdv, heureDuRdv);

        //1.On vérifie si le rdv est dans le passé
        if (heureDuRdvComplete.isBefore(heureActuelle)) {
            statut.put(false, "old");
            return statut;
        }

        //2.On verifie si le rdv est dans les horaires d'ouverture
        //TODO

        
        //3.On verifie si le rdv chevauche le precedent
        Rdv previousRDV = rdvRepository.findPreviousRdvSingle(dateDuRdv, heureDuRdv);
        System.out.println("previousRDV : " + previousRDV);
        int dureeRDV = contraintesRepository.findAll().iterator().next().getDureeDefaultMinutes();
        if (previousRDV != null && previousRDV.getHeure().plusMinutes(dureeRDV).isAfter(heureDuRdv) && previousRDV.getCleCompositeRDV().getJour().equals(dateDuRdv)) {
            statut.put(false, "chevauchement");
            System.out.println("chevauchement avec le rdv precedent");
            return statut;
        }

        //4.On verifie si le rdv est dans une indisponibilité
        //TODO
        
        statut.put(true, "ok");
        return statut;
    }
}
