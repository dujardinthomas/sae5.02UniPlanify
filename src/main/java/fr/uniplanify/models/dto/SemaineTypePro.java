package fr.uniplanify.models.dto;

import java.util.ArrayList;
import java.util.List;

public class SemaineTypePro {

    List<JournéePro> semaine = new ArrayList<>();

    public SemaineTypePro(List<JournéePro> semaine) {
        this.semaine = semaine;
    }

    public List<JournéePro> getSemaine() {
        return semaine;
    }

    public void setSemaine(List<JournéePro> semaine) {
        this.semaine = semaine;
    }

    @Override
    public String toString() {
        return "SemaineTypePro [semaine=" + semaine + "]";
    }


    

    
    
    
}
