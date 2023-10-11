package fr.uniplanify.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.uniplanify.dto.Client;

public class ClientDAO {

    private DS ds = new DS();
	private Connection con;


    public Client getClientByIdC(int idC) throws SQLException{
		Client c = null;
		con = ds.getConnection();
		Statement stmt = con.createStatement();
		String query = "select * from clients where idc = " + idC;
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			c = new Client(rs.getInt("idC"), rs.getString("nomC"), rs.getString("prenomC"), rs.getString("mailC"), rs.getString("passsword"));
		}
		return c;
	}

    
}
