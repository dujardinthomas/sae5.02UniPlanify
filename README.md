# SAE-N5_DUJARDIN_Thomas_UniPlanify : Gestionnaire de planning

Thomas Dujardin
Début : 14/09/2023
Fin : 08/01/2024

## Démarrer le projet Maven
1) télécharger le projet

2) Vérifier si maven est installé sur votre machine par ***mvn -v***, sinon l'extraire (https://maven.apache.org/download.cgi), puis créer la variable systeme ***M2_HOME*** pointant sur la racine du dossier maven et ajouter dans votre path ***%M2_HOME%\bin***

4) Compiler le projet avec ***mvn clean package*** : cela va créer le dossier target

5) Lancer le serveur avec ***mvn spring-boot:run***

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
Ce projet se fait en binôme ou seul.

### Répartitions
Rdv hebdomadaire : le jeudi 13h30
1 semaine sur 2

    1sem : 37) création bdd
    2sem : 39) tomcat + affichage calendrier avec chaque case cliquable avec un cpt dans chaque case pour commencer
    3sem : 41) maven + connexion bdd dao manuels
    4sem : 46)Authentification, PROFIL, JPA (dao auto)
    5sem : 48) Spring MVC
    6sem : 50) MAIL + SECURITÉ + RENDU 

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

Mettre par defaut disponible, le pro va indiquer ses indisponibilité, si prise de rdv regarde dans table indispo si n'existe pas


1.1) Est ce que il indique l'empechement global avec dateheure debut et fin ou alors pour chaque créneau chaque dateheure (si toute la journée, indique 8h, 8h15, ...., 18h)

-> j'opte pour qu'il indique la dateheure début et dateheure de fin, la durée d'un rdv sera géré en java avec la variable initial (cf ligne 38)

j'ai opté pour finalement qu'il indique la journée concerné et l'heure de début + l'heure de fin c'est trop compliqué sinon !



### MCD


Légende :
    #CléPrimaire
    [CléEtrangère]


Table : 

    Utilisateurs
        #idC
        nomC
        prenomC
        mailC
        password
        pro
    
    0,n
    Reserve
    1,n

    Rendez-vous
        #jour
        #heure
        duree
        nbPersonneMax
        etat

   
    Indisponibilités
        #debutJour
        #debutHeure
        #finJour
        #finHeure


    SemaineType
        #jourSemaine
        heureDebut
        heureFin



### MLD

    typical_day_pro(#day, start_time, end_time);

    unavailability(#day, #start_time, #end_time, motif);

    constraint_pro(#duree_default_minutes, nb_personne_max_default, adresse, description, email, nom, telephone);

    user_account(#id, nom, prenom, email, password, urlphoto, authority, enabled);

    rdv(#day, #time, state, comment, fill_percentage);

    rdv_participant(#rdv_day, #rdv_time, #participant_id);

    
    


Many-to-many (rdvClient) : Possibilité de prendre un rdv avec autant de personne que l'on veut (limite de nbPersonneMax en java)

on identifie un rdv avec son jour et son heure (difficile de mettre un num car faut pas 2 fois le même crénau)
on definie une semaine type du pro par défaut (ouvert tout les lundi de 8h à 18h)


### Mise en pratique

#### Init : 
new calendrier(durée, nbPersonneMax, semaineType);
A chaque insert de rdv, insert durée et nbPersonneMax

L'admin pour sa 1ère fois accède à Pro/initialisation pour renseigner sa semaine type, la durée de rdv et le nombre de personne. Lors du traitement, la base de données sera crée !

Le 1er utilisateur crée est automatiquement de type pro ! les autres sont à ajoutés via le SGBD.


#### Fonctionnement :
Quand user selectionne une journée : 
    - affiche en vert les créneaux dispo  (select * from semaineType where jourSemaine = now())
    - en rouge les créneaux indispo (si select debutHeure from Indispo where debutJour = now())
    - en gris les rdv reservés (si select * from rendez-vous where jour = now())

    
Quand pro selectionne sa journée : 
    - Lister tous les rdv du jour (select * from Rendez-vous where jour=now())

Si client reserve sur periode libre, = clique sur du vert, insert into reservations.
Si il reserve sur période non libre (pro qui a mis une indispo) = impossible de cliquer sur du rouge verifie 

Voir pour la vérif si il peut reserver, tester à l'affichage de la page (et ensuite en js...) ou a chaque tentative de rdv...

Mis en place un code couleur dans la vue (html, en valeur arbitraires) pour savoir la disponibilité restante sur un rdv + sur la journée (ensemble des rdv / nbRdv). vert (=0%) < vert clair (<50%) < jaune (<70%) < orange (100%) < rouge (=100%)


quand prise de rdv,
1) insert rdv avec date choisit
2) insert rdvclient avec idC et date choisit


#### Indisponibilités :
Pour les indisponibilités, quand le pro déclare une indispo (par une date de début, une heure de début, une date de fin et une heure de fin) il faut que tout les rdv déja réservés soit supprimés (ou annuler pour garder trace , a voir ...). 
=> Faire requete sql des rdv pendant l'indispo. on obtient une liste et on supprime cette liste de la bdd.

Pour les rdv à venir, supprimer les propositions qui sont dans la plage.

On propose pas l'horaire 
Si(
(jourProposé >= jourDebutIndispo) &&
(jourProposé <= jourFinIndispo))

et si( 
(horaireProposé >= heureDebutIndispo)
(horaireProposé <= heureFinIndispo)) 

On commence par regarder la journée et si est dedans on regarde l'heure pour eviter des calculs inutiles !

requete sql des propositions de rdv pendant l'indispo. on obtient une liste et on skip quand timeNow
soit on crée une liste du jour avec tout les rdv a ne pas mettre
soit on crée une liste d'indispo correspondant avec la date actuel


### Les vues

Un créneau de rdv est représenté par une case avec un code couleur :
- vert : disponible personne a réservé
- jaune/orange : encore de la place
- rouge : plein

#### Jour
Affiche le jour : tout les creneaux de rdv sur la journée possible.

#### Hebdomadaire
Affiche la semaine démarrant a la date de consultation jusqu'a j+7 (du mercredi au mardi par exemple)
Ce n'est rien d'autre qu'une liste de 7 jours !

#### Mensuel
Affiche le mois complet : une case par jour.
Grisé si le jour est dans le passé ou fermé 




## Suivi du projet 
[suiviHebdoSAE-N5.md](suiviHebdoSAE-N5.md)