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