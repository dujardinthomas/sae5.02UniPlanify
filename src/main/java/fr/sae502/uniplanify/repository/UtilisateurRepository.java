package fr.sae502.uniplanify.repository;

import org.springframework.data.repository.CrudRepository;

import fr.sae502.uniplanify.models.Utilisateur;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer> {
    
}
