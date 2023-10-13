package fr.uniplanify.models.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DS {
	public Connection getConnection() {
		//System.out.println(System.getProperty("user.dir")); //dossier target/cargo/configurations/tomcat10x
		String filePath = "../../../configuration/loginFileDataBase.txt"; //dans target
		InputStream loginFile = null;
		Properties p = new Properties();
		Connection con = null;
		try {
			loginFile = new FileInputStream(filePath);
			try {
				p.load(loginFile);
				try {
					Class.forName((String) p.get("driver"));
					con = DriverManager.getConnection((String) p.get("url"), (String) p.get("user"),
							(String) p.get("password"));
				} catch (Exception e) {
					System.out.println("impossible de créer la connection a la bdd!");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("erreur lecture du fichier " + filePath);
				e.getMessage();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return con;
	}

	public void closeConnection(Connection con){
		try {
			con.close();
			//System.out.println("Connection fermé!");
		} catch (SQLException e) {
			e.getMessage();
		}
		
	}

}
