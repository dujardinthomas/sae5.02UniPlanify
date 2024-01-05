package fr.sae502.uniplanify;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import fr.sae502.uniplanify.models.repository.ConstraintProRepository;
import fr.sae502.uniplanify.models.repository.RdvRepository;
import fr.sae502.uniplanify.models.repository.TypicalDayProRepository;
import fr.sae502.uniplanify.models.repository.UnavailabilityRepository;
import fr.sae502.uniplanify.models.repository.UserAccountRepository;

// import fr.sae502.uniplanify.repository.ConstraintRepository;
// import fr.sae502.uniplanify.repository.UnavailabilityRepository;
// import fr.sae502.uniplanify.repository.TypicalDayProRepository;
// import fr.sae502.uniplanify.repository.RdvRepository;
// import fr.sae502.uniplanify.repository.UserAccountRepository;

@Component
public class Demarrage implements ApplicationRunner{

    @Autowired
    private TypicalDayProRepository journeeTypeProRepository;
    @Autowired
    private ConstraintProRepository constraintRepository;
    @Autowired
    private UnavailabilityRepository indisponibiliteRepository;
    @Autowired
    private RdvRepository rdvRepository;
    @Autowired
    private UserAccountRepository utilisateurRepository;

    public Demarrage(TypicalDayProRepository journeeTypeProRepository, ConstraintProRepository constraintRepository,
            UnavailabilityRepository indisponibiliteRepository, RdvRepository rdvRepository,
            UserAccountRepository utilisateurRepository) {
        this.journeeTypeProRepository = journeeTypeProRepository;
        this.constraintRepository = constraintRepository;
        this.indisponibiliteRepository = indisponibiliteRepository;
        this.rdvRepository = rdvRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public Demarrage() {
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Hello UniPlanify, contenu de la base de données :");
        System.out.println();
        System.out.println("Journée type pro : " + journeeTypeProRepository.findAll());
        System.out.println();
        System.out.println("Contraintes : " + constraintRepository.findAll());
        System.out.println();
        System.out.println("Indisponibilités : " + indisponibiliteRepository.findAll());
        System.out.println();
        System.out.println("Rendez-vous : " + rdvRepository.findAll());
        System.out.println();
        System.out.println("Utilisateurs : " + utilisateurRepository.findAll());
    }
    
}
