package fr.sae502.uniplanify.models.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.sae502.uniplanify.models.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, Integer> {
    // obtient un user par son email
    @Query("SELECT u FROM UserAccount u WHERE u.email =:email")
    UserAccount findByEmail(@Param("email") String email);
    
}
