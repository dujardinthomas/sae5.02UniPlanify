package fr.sae502.uniplanify.models.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.sae502.uniplanify.models.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, Integer> {
    
    // Méthode pour trouver un utilisateur par email et mot de passe
    @Query("SELECT u FROM UserAccount u WHERE u.email = :email AND u.password = :password")
    UserAccount findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Query("SELECT u FROM UserAccount u WHERE u.email =:email")
    UserAccount findByEmail(@Param("email") String email);
    
}
