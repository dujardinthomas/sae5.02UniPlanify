package fr.sae502.uniplanify;

import java.time.LocalTime;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import fr.sae502.uniplanify.models.JourneeTypePro;
import fr.sae502.uniplanify.repository.JourneeTypeProRepository;

@Component
public class Demarrage implements ApplicationRunner{

    private JourneeTypeProRepository journeeTypeProRepository;

    public Demarrage(JourneeTypeProRepository journeeTypeProRepository) {
        this.journeeTypeProRepository = journeeTypeProRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Hello World");
        //initJourneeTypePro();
        System.out.println(journeeTypeProRepository.findAll());
    }

    private void initJourneeTypePro() {
        journeeTypeProRepository.save(new JourneeTypePro("lundi", LocalTime.of(8, 0), LocalTime.of(12, 0)));
    }
    
}
