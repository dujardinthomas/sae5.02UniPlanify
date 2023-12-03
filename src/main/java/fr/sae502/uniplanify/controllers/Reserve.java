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

        // Contraintes contraintes = contraintesRepository.findAll();
        // int nbPersonne = contraintes.getNbPersonneMaxDefault();


        // if (rdvExistant != null) {
        //     // rdv a déja été reservé mais peut etre qu'on peut ajouter des gens
        //     if (rdvExistant.getClients().size() < nbPersonne) {
        //         // on peut ajouter un client

        //         try {
        //             rdvExistant.addClient(client);
        //             rdvExistant.setCommentaire(client.getNomC() + " ajouté le " + jjMMyy);
        //             em.getTransaction().begin();
        //             em.persist(rdvExistant);
        //             em.getTransaction().commit();
        //         } catch (Exception e) {
        //             out.println("<h1>Vous avez déja résérvé ce rendez-vous du " + dateDuRdv + " à " + heureDuRdv + " !</h1>");
        //         }

        //         boolean statut = em.contains(rdvExistant);

        //         out.println("<h1>client " + client.getIdC() + (statut ? " à été " : "n'a pas été ")
        //                 + " ajouté au rendez-vous du " + dateDuRdv + " à " + heureDuRdv + "</h1>");

        //     } else {
        //         out.println("<h1>Impossible de prendre un rendez-vous pour ce " + dateDuRdv + " à " + heureDuRdv
        //                 + " !<h1>");
        //         out.println("<h2>Il n'y a malheureusement plus de places disponible pour ce rendez-vous.</h2>");
        //     }
            //mav.addObject("rdv", rdvExistant);
        // } else {

        //     Rdv nouveauRdv = new Rdv();
        //     nouveauRdv.setCleCompositeRDV(cleRDV);
        //     nouveauRdv.addClient(client);
        //     nouveauRdv.setCommentaire(commentaire);
        //     nouveauRdv.setEtat("Réservé");

        //     em.getTransaction().begin();
        //     em.persist(nouveauRdv);
        //     em.getTransaction().commit();

        //     boolean statut = em.contains(nouveauRdv);

        //     out.println(
        //             "<h1>rendez-vous du " + dateDuRdv + " à " + heureDuRdv
        //                     + (statut ? " à été crée " : "n'a pas été crée ")
        //                     + " avec le client " + client.getIdC() + "</h1>");
        //     mav.addObject("rdv", nouveauRdv);
        // }

        // String monEspace = "Perso";
        // if (client.getPro() == true) {
        //     monEspace = "Pro";
        // }
        // out.println("<h2><a href=\"../" + monEspace + "\">Accéder à mon espace</a></h2>");


        
        
        
        return mav;
    }
    
}
