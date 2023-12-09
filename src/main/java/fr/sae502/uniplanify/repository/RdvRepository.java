package fr.sae502.uniplanify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.sae502.uniplanify.models.CleCompositeRDV;
import fr.sae502.uniplanify.models.Rdv;

public interface RdvRepository extends CrudRepository<Rdv, CleCompositeRDV> {

    @Query("SELECT r from Rdv r " +
           "JOIN r.participants p " +
           "WHERE p.id = :clientId " +
           "ORDER BY r.cleCompositeRDV.jour ASC, r.cleCompositeRDV.heure ASC")
    List<Rdv> findRdvsByClientId(@Param("clientId") int clientId);


}
