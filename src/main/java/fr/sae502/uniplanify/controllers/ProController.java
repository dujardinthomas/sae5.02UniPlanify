package fr.sae502.uniplanify.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import fr.sae502.uniplanify.login.SessionBean;
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
    private SessionBean sessionBean;

    @Autowired
    private RdvRepository rdvRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private IndisponibiliteRepository indisponibiliteRepository;

    private Utilisateur user;

    @RequestMapping(value = { "", "/" })
    public ModelAndView espacePro() {
        user = sessionBean.getUtilisateur();
        ModelAndView mav = new ModelAndView("pro");
        mav.addObject("user", user);
        mav.addObject("rdvs", (List<Rdv>) rdvRepository.findAll());
        mav.addObject("listIndispo", (List<Indisponibilite>) indisponibiliteRepository.findAll());
        return mav;
    }

    @GetMapping(value = "/profil")
    public ModelAndView getProfil() {
        ModelAndView mav = new ModelAndView("profil");
        mav.addObject("user", user);
        mav.addObject("origine", "redirect:/pro");
        return mav;
    }

    @PostMapping(value = "/profil")
    public ModelAndView majProfil(
            @RequestParam(value = "nom", defaultValue = "") String nom,
            @RequestParam(value = "prenom", defaultValue = "") String prenom,
            @RequestParam(value = "email", defaultValue = "") String email,
            @RequestParam(value = "password", defaultValue = "") String password,
            @RequestParam(value = "origine", defaultValue = "/") String origine,
            @RequestParam(value="avatar") MultipartFile avatar) {

        if(!avatar.isEmpty()){
            String fileName = avatar.getOriginalFilename();
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

        ModelAndView mav = new ModelAndView(origine);
        user = sessionBean.getUtilisateur();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setPassword(password);
        sessionBean.setUtilisateur(user);
        System.out.println("dans la base : ? " + utilisateurRepository.save(user));
        return mav;
    }
}
