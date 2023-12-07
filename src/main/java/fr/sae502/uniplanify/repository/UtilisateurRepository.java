package fr.sae502.uniplanify.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.sae502.uniplanify.models.Utilisateur;

public interface UtilisateurRepository extends CrudRepository<Utilisateur, Integer> {

    // Méthode pour trouver un utilisateur par email et mot de passe
    @Query("SELECT u FROM Utilisateur u WHERE u.email = :email AND u.password = :password")
    Utilisateur findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
    
}
