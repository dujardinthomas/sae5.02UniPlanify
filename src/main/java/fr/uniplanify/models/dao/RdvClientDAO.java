package fr.uniplanify.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import fr.uniplanify.models.dto.Client;

public class RdvClientDAO {

	private DS ds = new DS();
	private Connection con;

	private ClientDAO clientDAO = new ClientDAO();

	public List<Client> getAllClientsInRDV(LocalDate d, LocalTime t) {
		System.out.println("debuttttt");
		con = ds.getConnection();
		List<Client> clients = new ArrayList<Client>();
		Statement stmt;
		try {
			stmt = con.createStatement();

			System.out.println("okkkkkkkkkkkkk");

			String query = "select * from rdvClient where jour= '" + java.sql.Date.valueOf(d) + "' and heure = '"
					+ Time.valueOf(t) + "'";
			System.out.println("putaineeeeeee " + query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				clients.add(clientDAO.getClientByIdC(rs.getInt("idC")));
				System.out.println("on ajoute un client a liste");
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		ds.closeConnection(con);
		return clients;

	}

	public boolean createRdvClient(LocalDate date, LocalTime time, int idC) {
		boolean res = false; // on cree un boolean pour pouvoir fermer la connexion
		con = ds.getConnection();
		String query = "insert into rdvClient values (?, ?, ?)";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.setDate(1, java.sql.Date.valueOf(date));
			ps.setTime(2, Time.valueOf(time));
			ps.setInt(3, idC);
			ps.executeUpdate();
				res = true;
				System.out.println("rdvCLient add dans rdvclientdao");
		} catch (SQLException e) {
			e.getMessage();
			res = false;
		}
		ds.closeConnection(con);
		return res;
	}

}
