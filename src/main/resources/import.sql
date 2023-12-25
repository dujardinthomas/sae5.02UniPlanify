-- LE MOT DE PASSE EST 'a'
INSERT INTO utilisateur (nom, prenom, email, password, urlphoto, authority, enabled) VALUES ('admin', 'admin', 'a', '$2a$10$H0i7FQJI2CbrX.AH.9TkduRfB26DuS4MNhclaf680wm/ilhYEjc4G', '../img/profils/default.jpg', 'ROLE_ADMIN', true);
INSERT INTO utilisateur (nom, prenom, email, password, urlphoto, authority, enabled) VALUES ('b', 'b', 'b', '$2a$10$H0i7FQJI2CbrX.AH.9TkduRfB26DuS4MNhclaf680wm/ilhYEjc4G', '../img/profils/default.jpg', 'ROLE_USER', true);


-- INSERT INTO utilisateur (nom, prenom, email, password, urlphoto, authority, enabled) VALUES ('b', 'b', 'thomasdujardin2003@gmail.com', 'b', '../img/profils/default.jpg');
-- INSERT INTO utilisateur (nom, prenom, email, password, urlphoto, authority, enabled) VALUES ('c', 'c', 'thoduj123@gmail.com', 'c', false, '../img/profils/default.jpg');
-- INSERT INTO utilisateur (nom, prenom, email, password, urlphoto, authority, enabled) VALUES ('d', 'd', 'd', 'd', false, '../img/profils/default.jpg');
-- INSERT INTO utilisateur (nom, prenom, email, password, urlphoto, authority, enabled) VALUES ('e', 'e', 'e', 'e', false, '../img/profils/default.jpg');
-- INSERT INTO utilisateur (nom, prenom, email, password, urlphoto, authority, enabled) VALUES ('f', 'f', 'f', 'f', false, '../img/profils/default.jpg');
-- INSERT INTO utilisateur (nom, prenom, email, password, urlphoto, authority, enabled) VALUES ('g', 'g', 'g', 'g', false, '../img/profils/default.jpg');
-- INSERT INTO utilisateur (nom, prenom, email, password, urlphoto, authority, enabled) VALUES ('h', 'h', 'h', 'h', false, '../img/profils/default.jpg');
-- INSERT INTO utilisateur (nom, prenom, email, password, urlphoto, authority, enabled) VALUES ('i', 'i', 'i', 'i', false, '../img/profils/default.jpg');
-- INSERT INTO utilisateur (nom, prenom, email, password, urlphoto, authority, enabled) VALUES ('j', 'j', 'j', 'j', false, '../img/profils/default.jpg');
-- INSERT INTO utilisateur (nom, prenom, email, password, urlphoto, authority, enabled) VALUES ('k', 'k', 'k', 'k', false, '../img/profils/default.jpg');
-- INSERT INTO utilisateur (nom, prenom, email, password, urlphoto, authority, enabled) VALUES ('l', 'l', 'l', 'l', false, '../img/profils/default.jpg');
-- INSERT INTO utilisateur (nom, prenom, email, password, urlphoto, authority, enabled) VALUES ('m', 'm', 'm', 'm', false, '../img/profils/default.jpg');



INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('lundi', '08:00:00', '18:00:00');
INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('mardi', '09:00:00', '18:00:00');
INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('mercredi', '10:00:00', '18:00:00');
INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('jeudi', '11:00:00', '18:00:00');
INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('vendredi', '12:00:00', '18:00:00');
-- INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('Samedi', '08:00:00', '18:00:00');
-- INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('Dimanche', '00:00:00', '00:00:00');

INSERT INTO Contraintes (DUREE_DEFAULT_MINUTES, NB_PERSONNE_MAX_DEFAULT, ADRESSE, DESCRIPTION, EMAIL, NOM, TELEPHONE) values (20, 2, 'lille', 'Votre cabinet chaleureux', 'uniplanify@gmail.com', 'Centre Medical', '0123456789');

-- INSERT INTO Indisponibilite (debut_jour, debut_heure, fin_jour, fin_heure, motif) values ('2023-12-12', '10:00:00', '2023-12-12', '10:40:00', 'indispo');
-- INSERT INTO Indisponibilite (debut_jour, debut_heure, fin_jour, fin_heure, motif) values ('2023-12-12', '16:00:00', '2023-12-14', '17:00:00', 'indispo');


INSERT INTO Indisponibilite (jour, debut_heure, fin_heure, motif) values ('2023-12-12', '10:19:00', '10:42:00', 'indispo');
INSERT INTO Indisponibilite (jour, debut_heure, fin_heure, motif) values ('2023-12-13', '12:00:00', '14:00:00', 'indispo');

INSERT INTO Indisponibilite (jour, debut_heure, fin_heure, motif) values ('2023-12-21', '08:00:00', '20:42:00', 'indispo');
INSERT INTO Indisponibilite (jour, debut_heure, fin_heure, motif) values ('2023-12-25', '08:00:00', '20:42:00', 'indispo');
