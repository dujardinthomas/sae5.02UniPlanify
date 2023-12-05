package fr.sae502.uniplanify.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.SpringLayout.Constraints;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.sae502.uniplanify.models.CleCompositeRDV;
import fr.sae502.uniplanify.models.Contraintes;
import fr.sae502.uniplanify.models.Rdv;
import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.ContraintesRepository;
import fr.sae502.uniplanify.repository.RdvRepository;

@Controller
@Component
@RequestMapping(value = "/perso")
public class Reserve {

    private RdvRepository rdvRepository;
    private ContraintesRepository contraintesRepository;

    public Reserve(RdvRepository rdvRepository, ContraintesRepository contraintesRepository) {
        this.rdvRepository = rdvRepository;
        this.contraintesRepository = contraintesRepository;
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

        LocalDateTime heureActuelle = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yy");
        String jjMMyy = heureActuelle.format(format);
        String commentaire = "reservé le " + jjMMyy;

        CleCompositeRDV cleRDV = new CleCompositeRDV();
        cleRDV.setHeure(heureDuRdv);
        cleRDV.setJour(dateDuRdv);
        Rdv rdvExistant = rdvRepository.findById(cleRDV).orElse(null);

        Iterable<Contraintes> contraintes = contraintesRepository.findAll();
        int nbPersonne = 0;
        for (Contraintes contrainte : contraintes) {
            System.out.println(contrainte);
            nbPersonne = contrainte.getNbPersonneMaxDefault();
        }

        Utilisateur user = new Utilisateur();
        String etat = "";

        if (rdvExistant != null) {
            // rdv a déja été reservé mais peut etre qu'on peut ajouter des gens
            if (rdvExistant.getClients().size() < nbPersonne) {
                // on peut ajouter un client

                try {
                    rdvExistant.addParticipant(user);
                    rdvExistant.setCommentaire(user.getNom() + " ajouté le " + jjMMyy);
                    
                    rdvRepository.save(rdvExistant);
                    etat = "add";
                    
                } catch (Exception e) {
                    mav.addObject("erreur", "Vous avez déja résérvé");
                }

                boolean statut = rdvRepository.existsById(cleRDV);
                mav.addObject("status", statut);
                
            } else {
                mav.addObject("erreur", "plein");
            }
            mav.addObject("rdv", rdvExistant);
        } else {

            Rdv nouveauRdv = new Rdv();
            nouveauRdv.setCleCompositeRDV(cleRDV);
            nouveauRdv.addParticipant(user);
            nouveauRdv.setCommentaire(commentaire);
            nouveauRdv.setEtat("Réservé");

            
            rdvRepository.save(nouveauRdv);

            boolean statut = rdvRepository.existsById(cleRDV);
            mav.addObject("status", statut);
            mav.addObject("rdv", nouveauRdv);
        }

        // String monEspace = "Perso";
        // if (client.getPro() == true) {
        //     monEspace = "Pro";
        // }
        // out.println("<h2><a href=\"../" + monEspace + "\">Accéder à mon espace</a></h2>");


        
        
        
        return mav;
    }
    
}
