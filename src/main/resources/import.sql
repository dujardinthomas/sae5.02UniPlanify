-- -- LE MOT DE PASSE EST 'a'
INSERT INTO user_account (nom, prenom, email, password, urlphoto, authority, enabled) VALUES ('admin', 'admin', 'uniplanify@gmail.com', '$2a$10$H0i7FQJI2CbrX.AH.9TkduRfB26DuS4MNhclaf680wm/ilhYEjc4G', '../img/profils/default.jpg', 'ROLE_ADMIN', true);
-- INSERT INTO user_account (nom, prenom, email, password, urlphoto, authority, enabled) VALUES ('b', 'b', 'b', '$2a$10$H0i7FQJI2CbrX.AH.9TkduRfB26DuS4MNhclaf680wm/ilhYEjc4G', '../img/profils/default.jpg', 'ROLE_USER', true);

-- INSERT INTO user_account (nom, prenom, email, password, urlphoto, authority, enabled) VALUES ('c', 'c', 'thomasdujardin2003@gmail.com', '$2a$10$H0i7FQJI2CbrX.AH.9TkduRfB26DuS4MNhclaf680wm/ilhYEjc4G', '../img/profils/default.jpg', 'ROLE_USER', true);


INSERT INTO typical_day_pro (day_pro, start_time, end_time) values ('lundi', '08:00:00', '18:00:00');
INSERT INTO typical_day_pro (day_pro, start_time, end_time) values ('mardi', '09:00:00', '18:00:00');
INSERT INTO typical_day_pro (day_pro, start_time, end_time) values ('mercredi', '10:00:00', '18:00:00');
INSERT INTO typical_day_pro (day_pro, start_time, end_time) values ('jeudi', '11:00:00', '18:00:00');
INSERT INTO typical_day_pro (day_pro, start_time, end_time) values ('vendredi', '12:00:00', '18:00:00');


INSERT INTO constraint_pro (DUREE_DEFAULT_MINUTES, NB_PERSONNE_MAX_DEFAULT, ADRESSE, DESCRIPTION, EMAIL, NOM, TELEPHONE, start_lunch, end_lunch) values (20, 2, 'Nieppe', 'Votre cabinet chaleureux pour effectuer tranquillement vos tests', 'thomasdujardin2003@gmail.com', 'Cabinet DUJARDIN', '0637707677', '12:30:00', '14:00:00');


-- INSERT INTO unavailability (day_unavailability, start_time, end_time, motif) values ('2023-12-12', '10:19:00', '10:42:00', 'indispo');
-- INSERT INTO unavailability (day_unavailability, start_time, end_time, motif) values ('2023-12-13', '12:00:00', '14:00:00', 'indispo');

-- INSERT INTO unavailability (day_unavailability, start_time, end_time, motif) values ('2023-12-21', '08:00:00', '20:42:00', 'indispo');
-- INSERT INTO unavailability (day_unavailability, start_time, end_time, motif) values ('2023-12-25', '08:00:00', '20:42:00', 'indispo');

