DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS indisponibilites;
DROP TABLE IF EXISTS professionnels;
DROP TABLE IF EXISTS clients;

CREATE TABLE clients (
    idC INTEGER,
    nomC text,
    prenomC text,
    mailC text,
    CONSTRAINT pk_clients PRIMARY KEY (idC)
);

--table pour y inclure tout les pros: voir si utile comme il y en a que 1
CREATE TABLE professionnels(
    idP INTEGER,
    nomP text,
    prenomP text,
    mailP text,
    --Paramètres Généraux pour les RDV--
    dureeRDV INTEGER, --en minute (conversion a faire en java pour simplicité du pro)
    nbPersonne INTEGER,
    CONSTRAINT pk_professionnels PRIMARY KEY (idP)
);

CREATE TABLE indisponibilites(
    idP INTEGER,
    debutEmpechement timestamp,
    finEmpechement timestamp,
    CONSTRAINT pk_indisponibilites PRIMARY KEY (idP, debutEmpechement, finEmpechement),
    CONSTRAINT fk_indisponibilites FOREIGN KEY (idP) REFERENCES professionnels(idP)
);

CREATE TABLE reservations(
    idC INTEGER,
    idP INTEGER,
    jourheure TIMESTAMP,
    CONSTRAINT pk_reservations PRIMARY KEY (idC, idP, jourheure),
    CONSTRAINT fk_reservationsC FOREIGN KEY (idC) REFERENCES clients(idC),
    CONSTRAINT fk_reservationsP FOREIGN KEY (idP) REFERENCES professionnels(idP)
);

--Un client
INSERT INTO clients values (1, 'DUJARDIN', 'Thomas', 'thomas.dujardin2.etu@univ-lille.fr');

-- --Un professionnel
INSERT INTO professionnels VALUES (1, 'CARISSIMO', 'Patrice', 'patrice.carissimo@gmail.com');

-- --Les non disponibilités (indisponibilites) du professionel 1 non dispo de 8h à 10h
INSERT INTO indisponibilites VALUES (1, '2023-12-15 08:00:00', '2023-12-15 10:00:00');

-- --Le client 1 reserve avec le professionnel 1 à 11h
INSERT INTO reservations VALUES (1, 1, '2023-12-15 11:00:00');

-- --Le client 1 veut reserver alors que le professionnel 1 n'est pas disponible
-- BEGIN
--     IF NOT EXISTS (
--         SELECT 1
--         FROM indisponibilites e
--         WHERE e.idP = 1
--         AND e.debutEmpechement <= '2023-12-15 09:00:00'
--         AND e.finEmpechement > '2023-12-15 09:00:00'
--     ) THEN
--         -- Le créneau est disponible, insérer la réservation
--         INSERT INTO reservations (
--         VALUES (1, 1, '2023-12-15 09:00:00');
--     ELSE
--         -- Le créneau est indisponible, renvoyer un message d'erreur
--         RAISE EXCEPTION 'Créneau indisponible pour la réservation.';
--     END IF;
-- END;