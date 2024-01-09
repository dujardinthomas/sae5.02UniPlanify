# SAE-N5_DUJARDIN_Thomas_UniPlanify : Gestionnaire de planning Générique 

Thomas Dujardin
- Début : 14/09/2023
- Fin : 08/01/2024

## Bien démarrer

1) télécharger le projet Maven.
2) ***mvn -v*** pour vérifier la présence de maven sinon l'installer : https://maven.apache.org/download.cgi
3) ***mvn clean package*** pour compiler le projet : cela va créer le dossier *target/*.
5) ***mvn spring-boot:run*** pour démarrer le serveur.

## Technos utilisées

JEE + framework Spring MVC Web relié à une base de données h2 en mémoire et création des tables via JPA.
C'est un ensemble de contrôler java respectant des endpoints qui fabriquent des modèles pour les envoyer sur des vues en JSP.
Le style des JSP est fait en CSS.


## Objectif

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

## Répartitions

Rdv hebdomadaire : le jeudi 13h30
1 semaine sur 2

    1sem : 37) création bdd
    2sem : 39) tomcat + affichage calendrier avec chaque case cliquable avec un cpt dans chaque case pour commencer
    3sem : 41) maven + connexion bdd dao manuels
    4sem : 46)Authentification, PROFIL, JPA (dao auto)
    5sem : 48) Spring MVC
    6sem : 50) MAIL + SECURITÉ + RENDU 

[Voir le suivi semaine par semaine ici ](suiviHebdoSAE-N5.md)


## Analyse du projet

### Cas d'utilisation :

- En tant que User je dois pouvoir :
    - voir un calendrier sur un mois avec les crénaux libres et non disponible
    - prendre un rdv : pouvoir reserver un créneau sur la plage disponible
    - modifier un rdv
    - supprimer un rdv

- En tant que Pro je dois pouvoir :
    - créer mon calendrier et y definir des paramètres généraux comme la durée d'un rdv et le nombre de personne acceptés
    - voir ma journée actuelle avec mes rdv, disponibilité et indisponibilité
    - créer disponibilité : ouvrir la prise de rdv
    - supprimer disponibilité : fermer la prise de rdv et annuler ceux déja reservé


### Ma démarche :

1) Est ce que le pro doit indiquer ses disponibilités (crée a chaque fois des dispo) ou ses non disponibilités (crée uniquement ses empechements)

-> j'opte pour qu'il indique ses non dispo car il y aura moins de ligne dans la table il sera rarement absent

Mettre par defaut disponible, le pro va indiquer ses indisponibilité, si prise de rdv regarde dans table indispo si n'existe pas


1.1) Est ce que il indique l'empechement global avec dateheure debut et fin ou alors pour chaque créneau chaque dateheure (si toute la journée, indique 8h, 8h15, ...., 18h)

-> j'opte pour qu'il indique la dateheure début et dateheure de fin, la durée d'un rdv sera géré en java avec la variable initial (cf ligne 38)

j'ai opté pour finalement qu'il indique la journée concerné et l'heure de début + l'heure de fin c'est trop compliqué sinon !

### MCD :

```
_________________                    _______________
| Utilisateurs  |                    | Rendez-vous |
|---------------|                    |-------------|
|    #idC       |--0,n Reserve 1,n---|   #jour     |
|_______________|                    |   #heure    |
                                     |_____________|

    Indisponibilités
        #debutJour
        #debutHeure
        #finHeure

    SemaineType
        #jourSemaine

    Contraintes
        #duree_default_minutes

```

### MLD :

    typical_day_pro(#day, start_time, end_time);

    unavailability(#day, #start_time, #end_time, motif);

    constraint_pro(#duree_default_minutes, nb_personne_max_default, adresse, description, email, nom, telephone);

    user_account(#id, nom, prenom, email, password, urlphoto, authority, enabled);

    rdv(#day, #time, state, comment, fill_percentage);

    rdv_participant(#rdv_day, #rdv_time, #participant_id);

    
Many-to-many (rdvClient) : Possibilité de prendre un rdv avec autant de personne que l'on veut (limite de nbPersonneMax en java)

on identifie un rdv avec son jour et son heure (difficile de mettre un num car faut pas 2 fois le même crénau)
on definie une semaine type du pro par défaut (ouvert tout les lundi de 8h à 18h)


## Première initialisation du calendrier (base de données vide)

Lors du déploiement du projet, un calendrier est installé.
La base de données actuellement utilisé est une base h2 en mémoire (pour les tests). Elle peut être changé sans aucun problème, les tables se créent au démarrage de l'application grâce à JPA.

L'admin pour sa 1ère fois accède à son espace professionnel, crée son compte (il sera exceptionnellement de type pro car aucun compte existe), se reconnecte et est automatiquement redirigé pour renseigner ses contraintes : 
- sa semaine type (les jours ouverts à la prise de rdv), 
- la durée de chaque rdv,
- le nombre de personne. 

Une fois cela fait, le calendrier est opérationnel.
Il peut modifier ses contraintes à tout moment, cependant seuls les nouveaux rdv tiendront compte des mises à jour.


## Fonctionnement

La prise d'un rdv est très simple :

Pour un utilisateur non connecté : 
1) L'utilisateur choisit un créneau selon sa vue préféré (mensuelle puis journalière ou directement hebdomadaire).
2) Le site lui demande de s'identifier. S'il n'a pas de compte, il peut en créer un en cliquant sur le lien inscription.
3) Il renseigne alors ses informations puis valide.
4) Il est réinvité à se connecter.
5) Si le rdv respecte les règles, il est enregistré et apparaît dans son espace personnel. Il reçoit la confirmation sur son adresse mail.

Pour un utilisateur connecté : 
1) L'utilisateur choisit un créneau selon sa vue préféré (mensuelle puis journalière ou directement hebdomadaire).
2) Si le rdv respecte les règles, alors il est enregistré et apparaît dans son espace personnel. Il reçoit la confirmation sur son adresse mail.


## Les fonctionnalités

### La réservation :

Les créneaux de rdv sont consultables au mois, à la semaine ou au jour.

Chaque rdv est représenté par un objet associé à une couleur spécifique (valeur arbitraires) en fonction de son taux de remplissage. Ce taux est également attribué à chaque journée (représentant la moyenne des pourcentages de remplissage des rendez-vous sur cette journée) :

- vert si le taux est à 0% : c'est à dire que le rdv est disponible,
- vert clair si le taux est inférieur à 50%,
- jaune si le taux est inférieur à 70%,
- orange si le taux est inférieur à 100%,
- rouge si le taux est égal à 100%  : c'est à dire que le rdv est complet.

Pour plus de visibilité, je me suis assuré d'afficher uniquement à la reservation les rdv dont l'heure de début n'est pas encore passé. En d'autres termes, tous les rdv affichés au client sont réservables. (attribut ouvert dans l'objet rdv)


### L'espace personnel :

L'utilisateur a la possibilité d'accéder à son espace personnel où il peut trouver :

- Tous ses rdv auxquels il participe (passés et à venir). Il peut les supprimer si besoin. Cependant, si plusieurs personnes sont impliquées dans le rendez-vous, sa suppression ne concerne que lui ; les autres participants restent enregistrés.

- Un lien permettant de modifier son profil (nom, prénom, mail, mot de passe et sa photo de profil).


### L'espace professionnel :

Le professionnel possède également son propre espace, où il peut :
- Consulter son planning ; ses rdv sur le mois, la semaine (j à j+7) ainsi que l'historique complet des rdv passés. Il peut supprimer des rdv si besoins. Dans ce cas un email de confirmation est envoyé à chaque participant de son annulation.

- Accéder à un lien permettant de modifier son profil similaire à celui de l'utilisateur.

- Ajouter des périodes d'indisponibilités en choisissant une date de début, une heure de début et une heure de fin. Il peut également y ajouter un motif.

- Redéfinir les contraintes des rendez-vous (durée, nombre maximum de personnes par rendez-vous), l'adresse du lieu (récupéré depuis la page /contact) et les heures de travail, en définissant les horaires de début et de fin. Les créneaux proposés s'alignent sur ces contraintes.


### Les indisponibilités :

Le professionnelle a la possibilité de déclarer des périodes d'indisponibilités. Elles sont représentés par une date de début, une heure de début, une heure de fin et un motif.

Lorsqu'une indisponibilité est ajoutée :
- Il n'est plus possible de réserver sur cette plage horaire.
- Les crénaux disponibles sont supprimés.
- Les rdvs déjà réservés pendant cette période sont alors supprimés et les participants en sont informés par e-mail. En d'autres termes, l'indisponibilité prime sur les rendez-vous existants.


## Points difficiles

Le projet a connu 3 grandes transitions : d'abord le passage de Tomcat à un projet Maven avec le plugin Cargo, suivi de la suppression des DAO et leur remplacement par JPA (nécessitant des annotations précises), puis enfin une transition significative vers Spring (passage des Servlets à des Contrôleurs), avec un ajout continu de dépendances.

Chacune de ces transitions a demandé des ajustements minutieux dans une configuration existante, ce qui n'était pas toujours simple. Malgré ces changements majeurs, je n'ai pas rencontré d'autres difficultés notables.

Malgré les réécritures du projet, cela m'a plu car j'ai pu observer le fonctionnement et la puissance de ces technologies.


## Améliorations potentielles

L'amélioration du front de l'espace personnel aurait pu être une piste, mais la priorité a été accordée au développement du back-end.