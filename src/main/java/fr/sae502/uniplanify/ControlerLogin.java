package fr.sae502.uniplanify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ControlerLogin {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/selectClients")
    @ResponseBody
    public String selectClients() {
        ///ModelMap modelmap
        //modelmap.put("etudiantsListe", clientRepository.findAll());
        return "selectClients" + clientRepository.findAll().toString();
    }

    @GetMapping("/login")
    public String etudiantForm() {
        return "login";
    }

    @PostMapping("/login")
    String getEtudiant(
            @RequestParam(value = "mail", required = true) String mail,
            @RequestParam(value = "password", required = true) String password,
            ModelMap modelmap) {

        Client client = clientRepository.findByMailAndPassword(mail, password);
        if (client != null) {
            if (client.getPassword().equals(password) && client.getMail().equals(mail)) {
                modelmap.put("etudiantsListe", clientRepository.findAll());
                return "calendrier";
            } else {
                return "login";
            }
        } else {
            return "calendrier";
        }
    }

    @RequestMapping("/inscription")
    public String inscription() {
        return "inscription";
    }

    @PostMapping("/inscription")
    String getEtudiant(
            @RequestParam(value = "nom", required = false) String nom,
            @RequestParam(value = "prenom", required = false) String prenom,
            @RequestParam(value = "mail", required = false) String mail,
            @RequestParam(value = "password", required = false) String password,
            ModelMap modelmap) {

        clientRepository.save(new Client(nom, prenom, mail, password, false));
        modelmap.put("etudiantsListe", clientRepository.findAll());
        return "lister";
    }

}
