package fr.uniplanify.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.uniplanify.models.dto.Client;

public class ClientDAO {

	private DS ds = new DS();
	private Connection con;


	public Client getClientByIdC(int idC){
		return getClients("select * from client where idc = '" + idC + "'");
	}

	public Client getClientByNomC(String nomC){
		return getClients("select * from client where nomc = '" + nomC + "'");
	}

	public Client getClientByPrenomC(String prenomC){
		return getClients("select * from client where prenomC = '" + prenomC + "'");
	}

	public Client getClientByMailAndPwd(String mailC, String pwd){
		return getClients("select * from client where mailC = '" + mailC + "' and password = '" + pwd + "'");
	}
	

	private Client getClients(String query) {
		System.out.println(query);
		//List<Client> clients = new ArrayList<>();
		Client clients = null;
		con = ds.getConnection();
		Statement stmt;
		try {
			stmt = con.createStatement();
			//String query = "select * from clients where idc = " + idC;
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				//System.out.println(rs.getInt("idc") + rs.getString("nomc") + rs.getString("prenomc") + rs.getString("passsword") + rs.getObject("pro"));
				clients = new Client(
					rs.getInt("idc"), 
					rs.getString("nomc"), 
					rs.getString("prenomc"), 
					rs.getString("mailc"),
					rs.getString("password"),
					rs.getBoolean("pro"));
			}
		} catch (SQLException e) {
			e.getMessage();
			System.out.println("erreur de select client!");
		}
		ds.closeConnection(con);
		return clients;
	}
	

	public boolean createClient(Client c) {
		con = ds.getConnection();
		String query = "insert into client (nomc, prenomc, mailc, password, pro) values (?,?,?,?,?)";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			//ps.setInt(1, c.getIdC());
			ps.setString(1, c.getNomC());
			ps.setString(2, c.getPrenomC());
			ps.setString(3, c.getMailC());
			ps.setString(4, c.getPassword());
			ps.setBoolean(5, c.getPro());
			System.out.println(ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.getMessage();
			System.out.println("erreur de insert client!");
			return false;
		}
		ds.closeConnection(con);
		return true;
	}

}
