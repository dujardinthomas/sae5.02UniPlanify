DROP TABLE IF EXISTS rdvClient;
DROP TABLE IF EXISTS rdv;
DROP TABLE IF EXISTS indisponibilite;
DROP TABLE IF EXISTS client;

CREATE TABLE client (
    idC INTEGER,
    nomC text,
    prenomC text,
    mailC text,
    CONSTRAINT pk_client PRIMARY KEY (idC)
);

CREATE TABLE rdv(
    jour date,
    heure time,
    duree INTEGER,
    nbPersonneMax INTEGER,
    etat varchar(30),
    CONSTRAINT pk_rdv PRIMARY KEY (jour, heure)
); 

CREATE TABLE rdvClient(
    jour date,
    heure time,
    idC INTEGER,
    CONSTRAINT pk_rdvClient PRIMARY KEY (jour, heure, idC),
    CONSTRAINT fk_rdvClientJOur FOREIGN KEY (jour, heure) REFERENCES rdv(jour, heure),
    CONSTRAINT fk_rdvClientclient FOREIGN KEY (idC) REFERENCES client(idC)
);

--table externe mais qui sera utilisé avant d'insert un rdv
CREATE TABLE indisponibilite(
    debutJour date,
    debutHeure time,
    finJour date,
    finHeure time, 
    CONSTRAINT pk_indisponibilite PRIMARY KEY (debutJour, debutHeure, finJour, finHeure)
);




--des client
INSERT INTO client values (1, 'DUJARDIN', 'Thomas', 'thomas.dujardin2.etu@univ-lille.fr');
INSERT INTO client values (2, 'NOM2', 'PRENOM2', 'nom2.prenom2.etu@univ-lille.fr');


--des rdv
INSERT INTO rdv VALUES ('2023-12-15', '10:00:00', 15, 1, 'reservé');
INSERT INTO rdv VALUES ('2023-12-15', '16:00:00', 15, 1, 'reservé');


--Association du client 1 et 2 au rendez vous de 10h
INSERT INTO rdvClient VALUES ('2023-12-15', '10:00:00', 1);
INSERT INTO rdvClient VALUES ('2023-12-15', '10:00:00', 2);


-- --Les non disponibilités (indisponibilites) du professionel non dispo de 8h à 10h
INSERT INTO indisponibilite VALUES ('2023-12-12', '10:00:00', '2023-12-11', '11:00:00');
INSERT INTO indisponibilite VALUES ('2023-12-13', '08:00:00', '2023-12-13', '10:00:00');




-- DROP TABLE IF EXISTS semaineTypeTravaille;
-- DROP TYPE IF EXISTS semaine;

-- --POUR SPECIFIER UNE SEMAINE TYPE POUR EVITER LES REPETS D'INDISPO
-- CREATE TYPE semaine AS ENUM (
--     'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche'
-- );

-- CREATE TABLE semaineTypeTravaille(
--     jour Semaine,
--     heureDebut time,
--     heureFin time
-- );

-- --FAIT DANS ESPACE PRO 
-- INSERT INTO semaineTypeTravaille VALUES 
-- ('Lundi', '08:00:00', '18:00:00'),
-- ('Mardi', '08:00:00', '18:00:00'),
-- ('Mercredi', '08:00:00', '18:00:00'),
-- ('Jeudi', '08:00:00', '18:00:00'),
-- ('Vendredi', '08:00:00', '18:00:00'),
-- ('Samedi', '08:00:00', '18:00:00'),
-- ('Dimanche', '00:00:00', '00:00:00');