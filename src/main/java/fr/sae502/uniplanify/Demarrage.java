package fr.sae502.uniplanify;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import fr.sae502.uniplanify.repository.ContraintesRepository;
import fr.sae502.uniplanify.repository.IndisponibiliteRepository;
import fr.sae502.uniplanify.repository.JourneeTypeProRepository;
import fr.sae502.uniplanify.repository.RdvRepository;
import fr.sae502.uniplanify.repository.UtilisateurRepository;

@Component
public class Demarrage implements ApplicationRunner{

    private JourneeTypeProRepository journeeTypeProRepository;
    private ContraintesRepository constraintRepository;
    private IndisponibiliteRepository indisponibiliteRepository;
    private RdvRepository rdvRepository;
    private UtilisateurRepository utilisateurRepository;

    public Demarrage(JourneeTypeProRepository journeeTypeProRepository, ContraintesRepository constraintRepository,
            IndisponibiliteRepository indisponibiliteRepository, RdvRepository rdvRepository, UtilisateurRepository utilisateurRepository) {
        this.journeeTypeProRepository = journeeTypeProRepository;
        this.constraintRepository = constraintRepository;
        this.indisponibiliteRepository = indisponibiliteRepository;
        this.rdvRepository = rdvRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Hello UniPlanify, contenu de la base de données :");
        System.out.println(journeeTypeProRepository.findAll());
        System.out.println(constraintRepository.findAll());
        System.out.println(indisponibiliteRepository.findAll());
        System.out.println(rdvRepository.findAll());
        System.out.println(utilisateurRepository.findAll());
    }
    
}
