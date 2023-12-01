package fr.sae502.uniplanify.repository;

import org.springframework.data.repository.CrudRepository;

import fr.sae502.uniplanify.models.CleCompositeIndisponibilite;
import fr.sae502.uniplanify.models.Indisponibilite;

public interface IndisponibiliteRepository extends CrudRepository<Indisponibilite, CleCompositeIndisponibilite> {
    
}
