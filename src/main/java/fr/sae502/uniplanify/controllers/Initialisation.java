package fr.sae502.uniplanify.controllers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.sae502.uniplanify.models.Contraintes;
import fr.sae502.uniplanify.models.JourneeTypePro;
import fr.sae502.uniplanify.repository.ContraintesRepository;
import fr.sae502.uniplanify.repository.JourneeTypeProRepository;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Controller
public class Initialisation {

    private JourneeTypeProRepository journeeTypeProRepository;
    private ContraintesRepository contraintesRepository;

    public Initialisation(JourneeTypeProRepository journeeTypeProRepository,
            ContraintesRepository contraintesRepository) {
        this.journeeTypeProRepository = journeeTypeProRepository;
        this.contraintesRepository = contraintesRepository;
    }

    @GetMapping(value = "/initialisation")
    public String initialisation() {
        return "initialisation";
    }

    @PostMapping(value = "init")
    @Transactional
    public String initialisationPost(HttpServletRequest req,
            @RequestParam(value = "jour_travaille[]") String[] joursTravailles,
            @RequestParam(value = "dureeDefaut") int dureeDefaut,
            @RequestParam(value = "nbPersonneMax") int nbPersonneMax) {


        if (joursTravailles != null) {

            contraintesRepository.deleteAll();

            contraintesRepository.save(new Contraintes(dureeDefaut, nbPersonneMax));
            System.out.println("contraintes enregistrées : " + dureeDefaut + " min par rdv avec " + nbPersonneMax
                    + " personnes maxi/rdv");

            int nbJours = joursTravailles.length;

            String[] joursWork = new String[nbJours];
            int[][] heuresStartWork = new int[nbJours][2]; // heure + min
            int[][] heuresEndWork = new int[nbJours][2];

            for (int i = 0; i < nbJours; i++) {
                // Traitez chaque jour coché ici

                String debut = req.getParameter("debut_" + joursTravailles[i]);
                String fin = req.getParameter("fin_" + joursTravailles[i]);

                System.out.println("Jour travaillé : " + joursTravailles[i] + " de " + debut + " a " + fin);

                joursWork[i] = joursTravailles[i];
                heuresStartWork[i][0] = Integer.parseInt(debut.split(":")[0].toString());
                heuresStartWork[i][1] = Integer.parseInt(debut.split(":")[1].toString());

                heuresEndWork[i][0] = Integer.parseInt(fin.split(":")[0].toString());
                heuresEndWork[i][1] = Integer.parseInt(fin.split(":")[1].toString());

            }

            List<JourneeTypePro> weekWork = new ArrayList<>();
            for (int j = 0; j < joursWork.length; j++) {
                weekWork.add(new JourneeTypePro(joursWork[j].toLowerCase(),
                        LocalTime.of(heuresStartWork[j][0], heuresStartWork[j][1]),
                        LocalTime.of(heuresEndWork[j][0], heuresEndWork[j][1])));
            }
            System.out.println(weekWork.toString());

            journeeTypeProRepository.deleteAll();
            for (JourneeTypePro journeeTypePro : weekWork) {
                journeeTypeProRepository.save(journeeTypePro);
                System.out.println(journeeTypePro + " ajouté dans la base de donnée !");
            }

            System.out.println("toute la semaine type à été ajouté a la bdd !");

        } else {
            System.out.println("Aucun jour sélectionné");
        }

        return "calendrier";
    }
}
