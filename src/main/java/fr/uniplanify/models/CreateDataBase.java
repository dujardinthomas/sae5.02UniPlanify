// package fr.uniplanify.models;

// import java.sql.Connection;
// import java.sql.SQLException;
// import java.sql.Statement;

// import fr.uniplanify.models.dao.DS;

// public class CreateDataBase {

//     DS ds = new DS();
//     Connection con;

//     public boolean createnewdatabase(){
//         boolean res;
//         res = createOrDropTablesDataBase(dropTableRdvClient);
//         res = createOrDropTablesDataBase(dropTableRdv);
//         res = createOrDropTablesDataBase(dropTableClient);
//         res = createOrDropTablesDataBase(dropTableIndisponibilite);
//         res = createOrDropTablesDataBase(dropTableSemaineTypePro);
//         res = createOrDropTablesDataBase(dropTableConstraints);

//         res = createOrDropTablesDataBase(createTableClient);
//         res = createOrDropTablesDataBase(createTableRdv);
//         res = createOrDropTablesDataBase(createTableRdvClient);
//         res = createOrDropTablesDataBase(createTableIndisponibilite);
//         res = createOrDropTablesDataBase(createTableSemaineTypePro);
//         res = createOrDropTablesDataBase(createTableConstraints);

//         return res;
//     }

//     public String dropTableClient = "DROP TABLE IF EXISTS client;";
//     public String createTableClient = "CREATE TABLE client (\r\n" + //
//             "    idC SERIAL PRIMARY KEY,\r\n" + //
//             "    nomC varchar(200),\r\n" + //
//             "    prenomC varchar(200),\r\n" + //
//             "    mailC varchar(200),\r\n" + //
//             "    password varchar(200),\r\n" + //
//             "    pro BOOLEAN DEFAULT false\r\n" + //
//             ");";

//     public String dropTableRdv = "DROP TABLE IF EXISTS rdv;";
//     public String createTableRdv = "CREATE TABLE rdv(\r\n" + //
//             "    jour date,\r\n" + //
//             "    heure time,\r\n" + //
//             "    etat varchar(200),\r\n" + //
//             "    CONSTRAINT pk_rdv PRIMARY KEY (jour, heure)\r\n" + //
//             "); ";

//     public String dropTableRdvClient = "DROP TABLE IF EXISTS rdvClient;";
//     public String createTableRdvClient = "CREATE TABLE rdvClient(\r\n" + //
//             "    jour date,\r\n" + //
//             "    heure time,\r\n" + //
//             "    idC INTEGER,\r\n" + //
//             "    CONSTRAINT pk_rdvClient PRIMARY KEY (jour, heure, idC),\r\n" + //
//             "    CONSTRAINT fk_rdvClientJour FOREIGN KEY (jour, heure) REFERENCES rdv(jour, heure),\r\n" + //
//             "    CONSTRAINT fk_rdvClientClient FOREIGN KEY (idC) REFERENCES client(idC)\r\n" + //
//             ");";

//     public String dropTableIndisponibilite = "DROP TABLE IF EXISTS indisponibilite;";
//     public String createTableIndisponibilite = "CREATE TABLE indisponibilite(\r\n" + //
//             "    debutJour date,\r\n" + //
//             "    debutHeure time,\r\n" + //
//             "    finJour date,\r\n" + //
//             "    finHeure time, \r\n" + //
//             "    CONSTRAINT pk_indisponibilite PRIMARY KEY (debutJour, debutHeure, finJour, finHeure)\r\n" + //
//             ");";

//     public String dropTableSemaineTypePro = "DROP TABLE IF EXISTS semaineTypePro;";
//     public String createTableSemaineTypePro = "CREATE TABLE semaineTypePro(\r\n" + //
//             "    jourSemaine varchar(15),\r\n" + //
//             "    heureDebut time,\r\n" + //
//             "    heureFin time,\r\n" + //
//             "    CONSTRAINT pk_semaineType PRIMARY KEY (jourSemaine)\r\n" + //
//             ");";

//     public String dropTableConstraints = "DROP TABLE IF EXISTS constraints;";
//     public String createTableConstraints = "CREATE TABLE constraints(\r\n" + //
//             "    dureeDefaultMinutes int,\r\n" + //
//             "    nbPersonneMaxDefault int\r\n" + //
//             ")\r\n" + //
//             "";


//     public boolean createOrDropTablesDataBase(String query){
//         Boolean res = false;
//         con = ds.getConnection();
// 		try {
//             Statement statement = con.createStatement();
// 			statement.execute(query);
//             System.out.println("Table créée avec succès.");
//             res = true;
// 		} catch (SQLException e) {
// 			e.getMessage();
// 			System.out.println("erreur de insert client!");
// 		}
// 		ds.closeConnection(con);
// 		return res;
//     }
// }
