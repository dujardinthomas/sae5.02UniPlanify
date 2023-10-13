package fr.uniplanify.controllers;

import java.io.IOException;

import fr.uniplanify.TestsAleaData;
import fr.uniplanify.models.constraints.Medecin;
import fr.uniplanify.models.dao.SemaineTypeProDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Initialisation")
public class Initialisation extends HttpServlet{
    
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        SemaineTypeProDAO s = new SemaineTypeProDAO();
        s.deleteSemaineTypePro();
        
        Medecin patrice = new Medecin(15, 
                                        1, 
                                        new String[]{"Lundi","Mardi","Mercredi","Jeudi","Vendredi"}, 
                                        new int[][]{{10,30}, {8,0}, {9,30}, {9,0}, {15,0}}, 
                                        new int[][]{{16,15}, {17,0}, {17,30}, {17,45}, {17,0}});

        //TestsAleaData tests = new TestsAleaData();
        //tests.createRDVForYear(2023);
        //TODO RECUPERER LES PLAGES DE DEBUT ET FIN DE JOURNEE
    }

}
