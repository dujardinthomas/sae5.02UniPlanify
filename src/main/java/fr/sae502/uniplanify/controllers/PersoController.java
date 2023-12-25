package fr.sae502.uniplanify.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import fr.sae502.uniplanify.models.Rdv;
import fr.sae502.uniplanify.models.Utilisateur;
import fr.sae502.uniplanify.repository.RdvRepository;
import fr.sae502.uniplanify.repository.UtilisateurRepository;

@Controller
@RequestMapping(value = "/perso")
public class PersoController {

    @Autowired 
    private RdvRepository rdvRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private Utilisateur user;

    @RequestMapping(value = {"", "/"})
    public String espacePerso(Model model, Principal principal) {
        user = utilisateurRepository.findByEmail(principal.getName());
        System.out.println("l'user est ::: " + user);
        if(user.isPro()){
            return "redirect:/pro";
        }
        model.addAttribute("user", user);
        List<Rdv> rdvs = rdvRepository.findRdvsByClientId(user.getId());
        model.addAttribute("rdvs", rdvs);
        
        return "perso";
    }

    @GetMapping(value = "/profil")
    public String getProfil(Model model){
        model.addAttribute("user", user);
        model.addAttribute("origine", "redirect:/perso");
        return "profil";
    }

    @PostMapping(value = "/profil")
    public String majProfil(@RequestParam(value="nom", defaultValue = "") String nom,
        @RequestParam(value="prenom", defaultValue = "") String prenom,
        @RequestParam(value="email", defaultValue = "") String email,
        @RequestParam(value="password", defaultValue = "") String password,
        @RequestParam(value="origine", defaultValue = "/") String origine,
        @RequestParam(value="avatar") MultipartFile avatar, 
        Principal principal){

        user = utilisateurRepository.findByEmail(principal.getName());


        if(!avatar.isEmpty()){
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
        user.setPassword(password);
        // sessionBean.setUtilisateur(user);
        System.out.println("dans la base : ? " + utilisateurRepository.save(user));
        return origine;
    }

}
