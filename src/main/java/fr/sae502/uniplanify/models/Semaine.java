package fr.sae502.uniplanify.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.core.Local;

import fr.sae502.uniplanify.repository.ContraintesRepository;
import fr.sae502.uniplanify.repository.IndisponibiliteRepository;
import fr.sae502.uniplanify.repository.JourneeTypeProRepository;
import fr.sae502.uniplanify.repository.RdvRepository;
import lombok.Data;
@Data
public class Semaine {
    private List<Jour> jours;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    private ContraintesRepository constraintRepository;
    private JourneeTypeProRepository journeeTypeProRepository;
    private IndisponibiliteRepository indisponibiliteRepository;
    private RdvRepository rdvRepository;
    
    public List<Jour> generateSemaine(LocalDate dateDebut, LocalDate dateFin) {
        List<Jour> jours = new ArrayList<>();
        for (LocalDate date = dateDebut; date.isBefore(dateFin) || date.equals(dateFin); date = date.plusDays(1)) {
            Jour jour = new Jour(date, constraintRepository, journeeTypeProRepository, indisponibiliteRepository,
                    rdvRepository);
            jours.add(jour);
        }
        return jours;
    }

    public String getTitle(){
        return "Semaine du " + dateDebut.getDayOfMonth() + "/" + dateDebut.getMonthValue() + "/" + dateDebut.getYear() + " au " + dateFin.getDayOfMonth() + "/" + dateFin.getMonthValue() + "/" + dateFin.getYear();
    }


    public Semaine(LocalDate debut, LocalDate fin, ContraintesRepository constraintRepository,
            JourneeTypeProRepository journeeTypeProRepository, IndisponibiliteRepository indisponibiliteRepository,
            RdvRepository rdvRepository) {
        this.dateDebut = debut;
        this.dateFin = fin;
        this.constraintRepository = constraintRepository;
        this.journeeTypeProRepository = journeeTypeProRepository;
        this.indisponibiliteRepository = indisponibiliteRepository;
        this.rdvRepository = rdvRepository;
        this.jours = generateSemaine(debut, fin);
    }
}
