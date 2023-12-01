package fr.sae502.uniplanify.repository;

import org.springframework.data.repository.CrudRepository;

import fr.sae502.uniplanify.models.CleCompositeRDV;
import fr.sae502.uniplanify.models.Rdv;

public interface RdvRepository extends CrudRepository<Rdv, CleCompositeRDV> {
    
}
