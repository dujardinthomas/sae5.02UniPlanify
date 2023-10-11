package fr.uniplanify.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import fr.uniplanify.dto.Client;
import fr.uniplanify.dto.Rdv;

public class RdvDAO {

    private DS ds = new DS();
	private Connection con;

	RdvClientDAO rdvClient = new RdvClientDAO();

	private Rdv getRDVByDateAndHeure(Date date, Time heure) throws SQLException{
		Rdv rdv = null;
		con = ds.getConnection();
		Statement stmt = con.createStatement();
		String query = "select * from rdv where date=" + date + " and heure =" + heure;
		ResultSet rs = stmt.executeQuery(query);
		if(rs.next()){
			int duree = rs.getInt("duree");
			String pate = rs.getString("pate");
			int nbPersonneMax = rs.getInt("nbPersonneMax");
			char etat = (char) rs.getObject("etat");
			rdv = new Rdv(date, heure, duree, nbPersonneMax, etat, rdvClient.getAllClientsInRDV(date, heure));
		}
		try {con.close();} catch(Exception e2) {}
		return rdv;
	}


    
}
