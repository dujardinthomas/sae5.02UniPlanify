# PlanifyPro_SAE-N5

Thomas Dujardin
Début : 14/09/2023

## Objectif : 
A la manière de prendreunrendezvous, de Doctolib ou de nombreux sites de prise de rendez-vous mis en place
durant la crise COVID-19, l’objectif de ce projet consiste à réaliser un site internet de gestion de rendez-vous multi-
utilisateurs. Le site doit permettre d’une part de montrer aux utilisateurs les créneaux libres, d’autre part de permettre
aux utilisateurs de saisir et gérer leurs rendez-vous, et évidemment de n’autoriser des rendez-vous que s’ils respectent
les contraintes souhaitées pour ce site.

Le site doit donc impérativement s’appuyer sur de nombreux paramètres permettant l’expression de contraintes afin
de pouvoir être adapté à toutes les situations. Par exemple le planning de réservation des créneaux de piscine (avec la
contrainte “pas plus de 30 personnes par heure”) ou le planning de réservation de créneaux chez le médecin (avec la
contrainte “pas plus d’1 personne toutes les 15mn”). C’est un framework général : on ne cherche pas un site qui permet
de créer plusieurs plannings. Si on souhaite faire deux plannings avec des contraintes différentes (par exemple l’un pour
la piscine, l’autre pour le médecin), on créera 2 sites WEB en changeant à l’initialisation leurs paramètres.
Ce projet se fait en binôme.

### Répartitions
Rdv hebdomadaire : le jeudi 13h30
1sem) création bdd
2sem) affichage calendrier avec chaque case cliquable avec un cpt dans chaque case pour commencer
3sem)Authentification + connexion bdd dao JPA
4sem) PROFIL 
5sem) MAIL SECURITÉ
6sem) RENDU 

## Mise en forme : 

- 2 roles : user et pro (admin)


## Cas d'utilisation :

- En tant que User je dois pouvoir :
    - voir un calendrier sur un mois avec les crenaux libres et non disponible
    - prendre un rdv : pouvoir reserver un creneau sur la plage disponible
    - modifier un rdv
    - supprimer un rdv


- En tant que Pro je dois pouvoir :
    - créer mon calendrier et y definir des paramètres généraux comme la durée d'un rdv et le nombre de personne acceptés
    - voir ma journée actuelle avec mes rdv, disponibilité et indisponibilité
    - créer disponibilité : ouvrir la prise de rdv
    - supprimer disponibilité : fermer la prise de rdv et annuler ceux déja reservé



## Ma démarche : 
1) Est ce que le pro doit indiquer ses disponibilités (crée a chaque fois des dispo) ou ses non disponibilités (crée uniquement ses empechements)

-> j'opte pour qu'il indique ses non dispo car il y aura moins de ligne dans la table il sera rarement absent


1.1) Est ce que il indique l'empechement global avec dateheure debut et fin ou alors pour chaque créneau chaque dateheure (si toute la journée, indique 8h, 8h15, ...., 18h)

-> j'opte pour qu'il indique la dateheure début et dateheure de fin, la durée d'un rdv sera géré en java avec la variable initial (cf ligne 38)


### Les tables sql

- Une table client qui contients les infos des clients

- Une table professionnels qui contient les infos du professionnel (malgré qu'il n'y ait que 1 pro, il faut quand meme stocker ses données et ses préferences de rendez-vous (à initialiser quand il va créer son agenda))

- Une table indisponibilités qui contient des périodes d'indisponibilités du pro avec la date et heure de début et de fin

- Une table reservations qui contient l'id client, l'id Pro, et l'heure et la date


### Mise en pratique

Quand client selectionne une journée, affiche en vert les créneaux dispo et en rouge les créneaux non dispo.

Si client reserve sur periode libre, = clique sur du vert, insert into reservations.
Si il reserve sur période non libre (pro qui a mis une indispo) = impossible de cliquer sur du rouge verifie 

Voir pour la vérif si il peut reserver, tester à l'affichage de la page (et ensuite en js...) ou a chaque tentative de rdv...


### MCD
Table : 

    Clients
        idC (Clé primaire)
        nomC
        prenomC
        mailC

    Professionnels
        idP (Clé primaire)
        nomP
        prenomP
        mailP
        dureeRDV (en minute)
        nbPersonne

    Indisponibilités
        idP (Clé étrangère vers Professionnel)
        debutEmpechement
        finEmpechement

    Réservations
        idC (Clé étrangère vers Client)
        idP (Clé étrangère vers Professionnel)
        jourheure (heure du rendez-vous)

Relations :

    Relation entre Client et Réservation :
        Un client peut effectuer plusieurs réservations.
        Un client est lié à une réservation par l'intermédiaire de l'attribut idC.

    Relation entre Professionnel et Réservation :
        Un professionnel peut avoir plusieurs réservations.
        Un professionnel est lié à une réservation par l'intermédiaire de l'attribut idP.

    Relation entre Professionnel et Indisponibilité :
        Un professionnel peut avoir plusieurs indisponibilités.
        Une indisponibilité est liée à un professionnel par l'intermédiaire de l'attribut idP.