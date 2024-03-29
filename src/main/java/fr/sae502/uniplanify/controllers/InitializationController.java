package fr.sae502.uniplanify.controllers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import fr.sae502.uniplanify.models.ConstraintPro;
import fr.sae502.uniplanify.models.TypicalDayPro;
import fr.sae502.uniplanify.models.repository.ConstraintProRepository;
import fr.sae502.uniplanify.models.repository.TypicalDayProRepository;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Controller
@RequestMapping(value = "/pro")
public class InitializationController {

    @Autowired
    private TypicalDayProRepository typicalDayProRepository;

    @Autowired
    private ConstraintProRepository constraintRepository;

    @GetMapping(value = "/initialisation")
    public String initialisation(Model model) {
        try {
            model.addAttribute("contraintes", constraintRepository.findAll().iterator().next());
            model.addAttribute("journeetypepro", (List<TypicalDayPro>) typicalDayProRepository.findAll());
        } catch (Exception e){
            System.out.println("pas de contraintes enregistrées");
            model.addAttribute("contraintes", null);
        }
        return "initialisation";
    }

    @PostMapping(value = "init")
    @Transactional
    public String initialisationPost(HttpServletRequest req,
            @RequestParam(value = "nom") String nom,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "telephone") String telephone,
            @RequestParam(value = "adresse") String adresse,
            @RequestParam(value = "jour_travaille[]", required = false) String[] joursTravailles,
            @RequestParam(value = "dureeDefaut") int dureeDefaut,
            @RequestParam(value = "nbPersonneMax") int nbPersonneMax) {

        constraintRepository.deleteAll();

        constraintRepository
                .save(new ConstraintPro(dureeDefaut, nbPersonneMax, nom, description, email, telephone, adresse));
        System.out.println("contraintes enregistrées : " + dureeDefaut + " min par rdv avec " + nbPersonneMax
                + " personnes maxi/rdv");

        if (joursTravailles != null) {

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
                try {
                    heuresStartWork[i][0] = Integer.parseInt(debut.split(":")[0].toString());
                    heuresStartWork[i][1] = Integer.parseInt(debut.split(":")[1].toString());

                    heuresEndWork[i][0] = Integer.parseInt(fin.split(":")[0].toString());
                    heuresEndWork[i][1] = Integer.parseInt(fin.split(":")[1].toString());
                } catch (Exception e) {
                    System.out.println("il manque des horaires pour le jour " + joursTravailles[i]);
                    return "redirect:/pro/initialisation?error="+joursTravailles[i];
                }

            }

            List<TypicalDayPro> weekWork = new ArrayList<>();
            for (int j = 0; j < joursWork.length; j++) {
                weekWork.add(new TypicalDayPro(joursWork[j].toLowerCase(),
                        LocalTime.of(heuresStartWork[j][0], heuresStartWork[j][1]),
                        LocalTime.of(heuresEndWork[j][0], heuresEndWork[j][1])));
            }
            System.out.println(weekWork.toString());

            typicalDayProRepository.deleteAll();
            for (TypicalDayPro journeeTypePro : weekWork) {
                typicalDayProRepository.save(journeeTypePro);
                System.out.println(journeeTypePro + " ajouté dans la base de donnée !");
            }

            System.out.println("toute la semaine type à été ajouté a la bdd !");

        } else {
            System.out.println("Aucun jour sélectionné");
        }

        return "redirect:/";
    }
}
