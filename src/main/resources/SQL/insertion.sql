-- SCRIPT BDD SAE-N5
DROP TABLE IF EXISTS rdvClient;
DROP TABLE IF EXISTS rdv;
DROP TABLE IF EXISTS indisponibilite;
DROP TABLE IF EXISTS client;

DROP TABLE IF EXISTS semaineTypePro;
DROP TYPE IF EXISTS semaineDay;

CREATE TABLE client (
    idC SERIAL PRIMARY KEY,
    nomC varchar(200),
    prenomC varchar(200),
    mailC varchar(200),
    password varchar(200),
    pro BOOLEAN DEFAULT false
);

INSERT INTO client (nomC, prenomC, mailC, password, pro) VALUES ('DUJARDIN', 'Veronique', 'contact@dujardin-neurofeedback-dynamique.fr', 'vero', true);
INSERT INTO client (nomC, prenomC, mailC, password) VALUES ('DUJARDIN', 'Thomas', 'thomas.dujardin2.etu@univ-lille.fr', 'toto');

CREATE TABLE rdv(
    jour date,
    heure time,
    duree INTEGER,
    nbPersonneMax INTEGER,
    etat varchar(200),
    CONSTRAINT pk_rdv PRIMARY KEY (jour, heure)
); 

CREATE TABLE rdvClient(
    jour date,
    heure time,
    idC INTEGER,
    CONSTRAINT pk_rdvClient PRIMARY KEY (jour, heure, idC),
    CONSTRAINT fk_rdvClientJour FOREIGN KEY (jour, heure) REFERENCES rdv(jour, heure),
    CONSTRAINT fk_rdvClientClient FOREIGN KEY (idC) REFERENCES client(idC)
);

--table externe mais qui sera utilisé avant d'insert un rdv
CREATE TABLE indisponibilite(
    debutJour date,
    debutHeure time,
    finJour date,
    finHeure time, 
    CONSTRAINT pk_indisponibilite PRIMARY KEY (debutJour, debutHeure, finJour, finHeure)
);



-- --POUR SPECIFIER UNE SEMAINE TYPE POUR EVITER LES REPETS D'INDISPO
-- CREATE TYPE semaineDay AS ENUM (
--     'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche'
-- );


CREATE TABLE semaineTypePro(
    jourSemaine varchar(15),
    heureDebut time,
    heureFin time,
    CONSTRAINT pk_semaineType PRIMARY KEY (jourSemaine)
);

--EXEMPLE D'INSERTION BDD SAE-N5

--semaineType du pro
INSERT INTO semaineTypePro values 
('Lundi', '08:00:00', '18:00:00'),
('Mardi', '08:00:00', '18:00:00'),
('Mercredi', '08:00:00', '18:00:00'),
('Jeudi', '08:00:00', '18:00:00'),
('Vendredi', '08:00:00', '18:00:00'),
('Samedi', '08:00:00', '18:00:00'),
('Dimanche', '00:00:00', '00:00:00');



--des client
INSERT INTO client (nomC, prenomC, mailC, password, pro) VALUES ('DUJARDIN', 'Veronique', 'contact@dujardin-neurofeedback-dynamique.fr', 'vero', true);
INSERT INTO client (nomC, prenomC, mailC, password) VALUES ('DUJARDIN', 'Thomas', 'thomas.dujardin2.etu@univ-lille.fr', 'toto');
INSERT INTO client (nomC, prenomC, mailC, password) VALUES ('NOM2', 'Prenom2', 'thomas.dujardin2.etu@univ-lille.fr', 'prenom2');
INSERT INTO client (nomC, prenomC, mailC, password) VALUES ('NOM3', 'Prenom3', 'prenom3.nom3.etu@univ-lille.fr', 'prenom3');
INSERT INTO client (nomC, prenomC, mailC, password) VALUES ('NOM4', 'Prenom4', 'prenom4.nom4.etu@univ-lille.fr', 'prenom4');






--des rdv
INSERT INTO rdv (jour, heure, duree, nbPersonneMax, etat) 
VALUES ('2023-12-15', '09:00:00', 15, 1, 'reservé');
INSERT INTO rdv VALUES ('2023-12-15', '10:00:00', 15, 1, 'reservé');
INSERT INTO rdv VALUES ('2023-12-15', '16:00:00', 15, 1, 'reservé');
INSERT INTO rdv VALUES ('2023-09-22', '16:00:00', 15, 1, 'reservé');


--Association du client 1 et 2 au rendez vous de 10h
INSERT INTO rdvClient (jour, heure, idC)
VALUES ('2023-12-15', '09:00:00', 2);
INSERT INTO rdvClient VALUES ('2023-12-15', '10:00:00', 3);
INSERT INTO rdvClient VALUES ('2023-12-15', '16:00:00', 4);
INSERT INTO rdvClient VALUES ('2023-09-22', '16:00:00', 5);


-- --Les non disponibilités (indisponibilites) du professionel non dispo de 8h à 10h
INSERT INTO indisponibilite (debutJour, debutHeure, finJour, finHeure) 
VALUES ('2023-12-11', '10:00:00', '2023-12-11', '11:00:00');

INSERT INTO indisponibilite VALUES ('2023-12-12', '10:00:00', '2023-12-12', '11:00:00');
INSERT INTO indisponibilite VALUES ('2023-12-13', '08:00:00', '2023-12-13', '10:00:00');


--REQUETES DE SELECT
--RDV DU JOUR
SELECT
  rdv.heure,
  client.nomC,
  client.prenomC,
  rdv.etat
FROM
  rdv
INNER JOIN
  rdvClient
ON
  rdv.jour = rdvClient.jour
AND
  rdv.heure = rdvClient.heure
INNER JOIN
  client
ON
  rdvClient.idC = client.idC;
-- WHERE
--   rdv.jour = DATE( NOW());