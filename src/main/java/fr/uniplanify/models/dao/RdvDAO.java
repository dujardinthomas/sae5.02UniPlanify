package fr.uniplanify.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import fr.uniplanify.models.dto.Rdv;

public class RdvDAO {

	private DS ds = new DS();
	private Connection con;

	RdvClientDAO rdvClient = new RdvClientDAO();

	public Rdv getRDVByDateAndHeure(LocalDate date, LocalTime heure) {
		Rdv rdv = null;
		con = ds.getConnection();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String query = "select * from rdv where date=" + java.sql.Date.valueOf(date) + " and heure =" + Time.valueOf( heure );
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				String etat = rs.getString("etat");
				rdv = new Rdv(date, heure, etat, rdvClient.getAllClientsInRDV(date, heure));
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		ds.closeConnection(con);
		return rdv;
	}


	public boolean createRDV(Rdv rdv) {
		con = ds.getConnection();
		String query = "insert into rdv (jour, heure, etat) values (?,?,?)";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.setDate(1, rdv.getJourSQL());
			ps.setTime(2, rdv.getHeureSQL());
			ps.setString(3, rdv.getEtat());
			System.out.println(ps.executeUpdate());

			for(int i=0; i<rdv.getClients().size(); i++) {
				rdvClient.createRdvClient(rdv.getJour(), rdv.getHeure(), rdv.getClients().get(i).getIdC());
				System.out.println("rdv client add! ");
			}

			System.out.println("rdv add !");
	

		} catch (SQLException e) {
			e.getMessage();
			System.out.println("erreur de insert rdv!");
			return false;
		}
		ds.closeConnection(con);
		return true;
		
			
	}

}
