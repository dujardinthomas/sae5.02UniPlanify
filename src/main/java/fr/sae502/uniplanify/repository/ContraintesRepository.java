package fr.sae502.uniplanify.repository;

import org.springframework.data.repository.CrudRepository;

import fr.sae502.uniplanify.models.Contraintes;

public interface ContraintesRepository extends CrudRepository<Contraintes, String> {
    
}
