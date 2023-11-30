package fr.sae502.uniplanify;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer>{

    Client findByMailAndPassword(String mail, String password);

    
}