package fr.sae502.uniplanify.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fr.sae502.uniplanify.models.Unavailability;
import fr.sae502.uniplanify.models.Rdv;
import fr.sae502.uniplanify.models.UserAccount;
import fr.sae502.uniplanify.models.repository.UnavailabilityRepository;
import fr.sae502.uniplanify.models.repository.ConstraintProRepository;
import fr.sae502.uniplanify.models.repository.RdvRepository;
import fr.sae502.uniplanify.models.repository.TypicalDayProRepository;
import fr.sae502.uniplanify.models.repository.UserAccountRepository;
import fr.sae502.uniplanify.view.Weekly;

@Controller
@RequestMapping(value = "/pro")
public class ProController {

    @Autowired
    private RdvRepository rdvRepository;

    @Autowired
    private UserAccountRepository utilisateurRepository;

    @Autowired
    private UnavailabilityRepository unavailabilityRepository;

    @Autowired
    private ConstraintProRepository constraintProRepository;

    @Autowired
    private TypicalDayProRepository typicalDayProRepository;

    private UserAccount user;

    @RequestMapping(value = { "", "/" })
    public String espacePro(Principal principal, Model model) {
        user = utilisateurRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("rdvs", (List<Rdv>) rdvRepository.findAll());
        model.addAttribute("listIndispo", (List<Unavailability>) unavailabilityRepository.findAll());

        LocalDate currentDate = LocalDate.now();
        int dayDebut = currentDate.getDayOfMonth();
        int monthDebut = currentDate.getMonthValue();
        int yearDebut = currentDate.getYear();

        int dayFin = currentDate.plusDays(6).getDayOfMonth();
        int monthFin = currentDate.plusDays(6).getMonthValue();
        int yearFin = currentDate.plusDays(6).getYear();

        LocalDate startDate = LocalDate.of(yearDebut, monthDebut, dayDebut);
        LocalDate endDate = LocalDate.of(yearFin, monthFin, dayFin);
        
        Weekly semaine = new Weekly(startDate,
                endDate,
                constraintProRepository,
                typicalDayProRepository,
                unavailabilityRepository,
                rdvRepository);
        model.addAttribute("semaine", semaine);
        return "pro";
    }

    @GetMapping(value = "/profil")
    public String getProfil(Model model, Principal principal) {
        user = utilisateurRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("origine", "redirect:/pro");
        return "profil";
    }

    @PostMapping(value = "/profil")
    public String majProfil(
            @RequestParam(value = "nom", defaultValue = "") String nom,
            @RequestParam(value = "prenom", defaultValue = "") String prenom,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "oldPassword", defaultValue = "") String oldPassword,
            @RequestParam(value = "newPassword", defaultValue = "") String newPassword,
            @RequestParam(value = "origine", defaultValue = "/") String origine,
            @RequestParam(value = "avatar") MultipartFile avatar,
            Principal principal) {

        user = utilisateurRepository.findByEmail(principal.getName());

        if (!avatar.isEmpty()) {
            String fileName = avatar.getOriginalFilename() + "";
            try {
                String newFile = "profil_" + user.getId() + fileName.substring(fileName.lastIndexOf("."));

                Path cheminDuRepertoireActuel = Paths.get("").toAbsolutePath();
                String emplacement = cheminDuRepertoireActuel + "/src/main/resources/static/img/profils/";
                String filePath = emplacement + newFile;

                File dest = new File(filePath);
                avatar.transferTo(dest);
                user.setUrlphoto("../img/profils/" + newFile);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setPassword(newPassword, oldPassword);

        System.out.println("dans la base : ? " + utilisateurRepository.save(user));
        return origine;
    }
}
