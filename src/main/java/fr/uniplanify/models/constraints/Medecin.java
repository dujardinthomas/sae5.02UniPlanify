package fr.uniplanify.models.constraints;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.uniplanify.models.dao.ConstraintsDAO;
import fr.uniplanify.models.dao.SemaineTypeProDAO;
import fr.uniplanify.models.dto.Constraints;
import fr.uniplanify.models.dto.JournéePro;
import fr.uniplanify.models.dto.SemaineTypePro;

public class Medecin{

    Constraints c;

    public Medecin(int dureeDefaultMinutes, int nbPersonneMaxDefault, String[] joursWork, int[][] heuresStartWork, int[][] heuresEndWork) {
        c = new Constraints(dureeDefaultMinutes, nbPersonneMaxDefault);

        ConstraintsDAO cDAO = new ConstraintsDAO();
        cDAO.createConstraint(c);

        List<JournéePro> weekWork = new ArrayList<>();
        for(int j=0; j<joursWork.length; j++){
            weekWork.add(new JournéePro(joursWork[j], LocalTime.of(heuresStartWork[j][0], heuresStartWork[j][1]),  LocalTime.of(heuresEndWork[j][0], heuresEndWork[j][1])));
        }
        System.out.println(weekWork.toString());
        SemaineTypePro semaineTypeProWork = new SemaineTypePro(weekWork);
        SemaineTypeProDAO sem = new SemaineTypeProDAO();
        sem.createSemaineTypePro(semaineTypeProWork);

    }

    

    
}