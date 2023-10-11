package fr.uniplanify.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DS {
    public Connection getConnection(){
	      String url = "jdbc:postgresql://psqlserv/but3";
	      String nom = "thomasdujardin2etu";
	      String mdp = "moi";
	      String driver = "org.postgresql.Driver";
	      Connection con = null;
	      try {
	          Class.forName(driver);
	          con = DriverManager.getConnection(url,nom,mdp);
	      } catch (Exception e){
	          System.out.println("impossible de créer connection a la bdd!");
	      }
	      return con;
	  }

}
