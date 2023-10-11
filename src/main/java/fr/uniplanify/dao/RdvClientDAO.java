package fr.uniplanify.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import fr.uniplanify.dto.Client;

public class RdvClientDAO {
    
    private DS ds = new DS();
	private Connection con;

	private ClientDAO clientDAO = new ClientDAO();

    public List<Client> getAllClientsInRDV(Date date, Time heure) throws SQLException{
		con = ds.getConnection();
		List<Client> clients = new ArrayList<Client>();
		Statement stmt = con.createStatement();
		String query = "select * from rdvClient where date=" + date + " and heure =" + heure;
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			clients.add(clientDAO.getClientByIdC(rs.getInt("idC")));
		}
		return clients;

	}


}
