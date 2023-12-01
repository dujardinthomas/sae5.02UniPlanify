INSERT INTO utilisateur (nom, prenom, email, password, pro) VALUES ('DUJARDIN', 'Veronique', 'contact@dujardin-neurofeedback-dynamique.fr', 'vero', true);
-- INSERT INTO utilisateur (nom, prenom, email, password, pro) VALUES ('DUJARDIN', 'Thomas', 'thomas.dujardin2.etu@univ-lille.fr', 'toto', true);

INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('lundi', '08:00:00', '18:00:00');
INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('mardi', '09:00:00', '18:00:00');
INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('mercredi', '10:00:00', '18:00:00');
INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('jeudi', '11:00:00', '18:00:00');
INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('vendredi', '12:00:00', '18:00:00');
-- INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('Samedi', '08:00:00', '18:00:00');
-- INSERT INTO journee_type_pro (jour, heure_debut, heure_fin) values ('Dimanche', '00:00:00', '00:00:00');

INSERT INTO Contraintes (duree_default_minutes, nb_personne_max_default) values (20, 4);