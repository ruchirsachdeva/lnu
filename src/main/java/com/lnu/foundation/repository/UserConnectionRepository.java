package com.lnu.foundation.repository;

import com.lnu.foundation.model.Userconnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by rucsac on 10/10/2018.
 */
@RepositoryRestResource
//@CrossOrigin(origins = "http://localhost:4200")
public interface UserconnectionRepository extends JpaRepository<Userconnection, String> {
}
