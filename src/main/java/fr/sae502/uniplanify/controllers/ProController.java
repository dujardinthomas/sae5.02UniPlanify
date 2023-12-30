package fr.sae502.uniplanify.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fr.sae502.uniplanify.models.Indisponibilite;
import fr.sae502.uniplanify.models.Rdv;
import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.IndisponibiliteRepository;
import fr.sae502.uniplanify.repository.RdvRepository;
import fr.sae502.uniplanify.repository.UtilisateurRepository;

@Controller
@RequestMapping(value = "/pro")
public class ProController {

    @Autowired
    private RdvRepository rdvRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private IndisponibiliteRepository indisponibiliteRepository;

    private Utilisateur user;

    @RequestMapping(value = { "", "/" })
    public String espacePro(Principal principal, Model model) {
        user = utilisateurRepository.findByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("rdvs", (List<Rdv>) rdvRepository.findAll());
        model.addAttribute("listIndispo", (List<Indisponibilite>) indisponibiliteRepository.findAll());
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
        

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if( passwordEncoder.matches(oldPassword, user.getPassword()) ){
            System.out.println("ancien mot de passe saisie correct, on l'autorise a changer !");
            user.setPassword(passwordEncoder.encode(newPassword));
        }else{
            System.out.println("ancien mot de passe saisie incorrect, on l'autorise pas a changer !");
        }

        System.out.println("dans la base : ? " + utilisateurRepository.save(user));
        return origine;
    }
}
