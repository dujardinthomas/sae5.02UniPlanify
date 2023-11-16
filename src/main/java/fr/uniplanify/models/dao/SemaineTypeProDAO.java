package fr.uniplanify.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import fr.uniplanify.models.dto.JournéePro;
import fr.uniplanify.models.dto.SemaineTypePro;

public class SemaineTypeProDAO {

	private DS ds = new DS();
	private Connection con;

	public SemaineTypePro getSemaineTypePro() {
		SemaineTypePro weekDTO = null;
		List<JournéePro> horaires = new ArrayList<>();

		con = ds.getConnection();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String query = "select * from semainetypepro";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				// pour chaque jour
				horaires.add(new JournéePro(rs.getString("joursemaine"),
						rs.getTime("heuredebut").toLocalTime(),
						rs.getTime("heurefin").toLocalTime()));

			}
			weekDTO = new SemaineTypePro(horaires);
		} catch (SQLException e) {
			e.getMessage();
			System.out.println("erreur de select semainetypepro!");
		}
		ds.closeConnection(con);

		return weekDTO;

	}


	public List<LocalTime> getDayPro(String jour) {
		List<LocalTime> day = null;
		con = ds.getConnection();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String query = "select heureDebut, heureFin from semainetypepro where joursemaine = '" + jour.toLowerCase() + "'";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				day = new ArrayList<>();
				day.add(rs.getTime("heuredebut").toLocalTime());
				day.add(rs.getTime("heurefin").toLocalTime());
			}
		} catch (SQLException e) {
			e.getMessage();
			System.out.println("erreur de select heures for " + jour + " semainetypepro!");
		}
		ds.closeConnection(con);
		return day;
	}


	public boolean createSemaineTypePro(SemaineTypePro weekDTO) {
		con = ds.getConnection();
		String query = "insert into semainetypepro (joursemaine, heuredebut, heurefin) values (?,?,?)";
		for (JournéePro j : weekDTO.getSemaine()) {
			PreparedStatement ps;
			try {
				ps = con.prepareStatement(query);
				System.out.println(query);
				System.out.println(weekDTO);
				ps.setObject(1, j.getJour());
				ps.setTime(2, j.getHeureDebutSQL());
				ps.setTime(3, j.getHeureFinSQL());
				ps.executeUpdate();
			} catch (SQLException e) {
				e.getMessage();
				System.out.println("erreur de insert " + j.getJour() + " semainetypepro!");
				return false;
			}
		}

		ds.closeConnection(con);
		return true;
	}

	public boolean deleteSemaineTypePro() {
		con = ds.getConnection();
		String query = "delete from semainetypepro";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.getMessage();
			System.out.println("erreur de suppression des donnes de la table semainetypepro!");
			return false;
		}
		ds.closeConnection(con);
		return true;
	}
}
