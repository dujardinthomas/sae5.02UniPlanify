-- INSERT INTO utilisateur (nom, prenom, email, password, pro) VALUES ('DUJARDIN', 'Veronique', 'contact@dujardin-neurofeedback-dynamique.fr', 'vero', true);
INSERT INTO utilisateur (nom, prenom, email, password, pro) VALUES ('a', 'a', 'a', 'a', true);
INSERT INTO utilisateur (nom, prenom, email, password, pro) VALUES ('b', 'b', 'b', 'b', false);

INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('lundi', '08:00:00', '18:00:00');
INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('mardi', '09:00:00', '18:00:00');
INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('mercredi', '10:00:00', '18:00:00');
INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('jeudi', '11:00:00', '18:00:00');
INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('vendredi', '12:00:00', '18:00:00');
-- INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('Samedi', '08:00:00', '18:00:00');
-- INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('Dimanche', '00:00:00', '00:00:00');

INSERT INTO Contraintes (duree_default_minutes, nb_personne_max_default) values (20, 4);

INSERT INTO Indisponibilite (debut_jour, debut_heure, fin_jour, fin_heure, motif) values ('2023-12-12', '10:00:00', '2023-12-12', '10:40:00', 'indispo');
INSERT INTO Indisponibilite (debut_jour, debut_heure, fin_jour, fin_heure, motif) values ('2023-12-12', '16:00:00', '2023-12-14', '17:00:00', 'indispo');