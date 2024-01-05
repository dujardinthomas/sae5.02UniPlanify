package fr.sae502.uniplanify.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.sae502.uniplanify.models.repository.ConstraintProRepository;
import fr.sae502.uniplanify.models.repository.RdvRepository;
import fr.sae502.uniplanify.models.repository.TypicalDayProRepository;
import fr.sae502.uniplanify.models.repository.UnavailabilityRepository;
import lombok.Data;
@Data
public class Weekly {
    private List<Daily> jours;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    private ConstraintProRepository constraintRepository;
    private TypicalDayProRepository workdayTypeProRepository;
    private UnavailabilityRepository unavailabilityRepository;
    private RdvRepository rdvRepository;
    
    public List<Daily> generateSemaine(LocalDate dateDebut, LocalDate dateFin) {
        List<Daily> jours = new ArrayList<>();
        for (LocalDate date = dateDebut; date.isBefore(dateFin) || date.equals(dateFin); date = date.plusDays(1)) {
            Daily jour = new Daily(date, constraintRepository, workdayTypeProRepository, unavailabilityRepository,
                    rdvRepository);
            jours.add(jour);
        }
        return jours;
    }

    public String getTitle(){
        return "Semaine du " + dateDebut.format(DateTimeFormatter.ofPattern("EEEE d MMMM", Locale.FRENCH)) + " au " + 
        dateFin.format(DateTimeFormatter.ofPattern("EEEE d MMMM yyyy", Locale.FRENCH));
    }


    public Weekly(LocalDate debut, LocalDate fin, ConstraintProRepository constraintRepository,
            TypicalDayProRepository workdayTypeProRepository, UnavailabilityRepository unavailabilityRepository,
            RdvRepository rdvRepository) {
        this.dateDebut = debut;
        this.dateFin = fin;
        this.constraintRepository = constraintRepository;
        this.workdayTypeProRepository = workdayTypeProRepository;
        this.unavailabilityRepository = unavailabilityRepository;
        this.rdvRepository = rdvRepository;
        this.jours = generateSemaine(debut, fin);
    }
}
