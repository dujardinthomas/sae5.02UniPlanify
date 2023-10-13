package fr.uniplanify.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.uniplanify.models.dto.Constraints;

public class ConstraintsDAO {

    private DS ds = new DS();
	private Connection con;


    public boolean createConstraint(Constraints c) {
		con = ds.getConnection();
		String query = "insert into constraints (dureeDefaultMinutes, nbPersonneMaxDefault) values (?,?)";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, c.getDureeDefaultMinutes());
			ps.setInt(2, c.getNbPersonneMaxDefault());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.getMessage();
			System.out.println("erreur de insert Constraint!");
			return false;
		}
		ds.closeConnection(con);
		return true;
	}


    public Constraints getConstraints() {
		Constraints c = null;
		con = ds.getConnection();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String query = "select * from constraints";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				c = new Constraints(
					rs.getInt("dureeDefaultMinutes"),
                    rs.getInt("nbPersonneMaxDefault"));
			}
		} catch (SQLException e) {
			e.getMessage();
			System.out.println("erreur de select constraints!");
		}
		ds.closeConnection(con);
		return c;
	}
    
}
