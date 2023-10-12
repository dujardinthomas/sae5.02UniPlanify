-- SCRIPT BDD SAE-N5
DROP TABLE IF EXISTS rdvClient;
DROP TABLE IF EXISTS rdv;
DROP TABLE IF EXISTS indisponibilite;
DROP TABLE IF EXISTS client;

DROP TABLE IF EXISTS semaineTypePro;
DROP TYPE IF EXISTS semaineDay;

CREATE TABLE client (
    idC int PRIMARY KEY,
    nomC varchar(200),
    prenomC varchar(200),
    mailC varchar(200),
    password varchar(200),
    pro BOOLEAN DEFAULT false
);




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



--POUR SPECIFIER UNE SEMAINE TYPE POUR EVITER LES REPETS D'INDISPO
CREATE TYPE semaineDay AS ENUM (
    'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi', 'Dimanche'
);


CREATE TABLE semaineTypePro(
    jourSemaine semaineDay,
    heureDebut time,
    heureFin time,
    CONSTRAINT pk_semaineType PRIMARY KEY (jourSemaine)
);

