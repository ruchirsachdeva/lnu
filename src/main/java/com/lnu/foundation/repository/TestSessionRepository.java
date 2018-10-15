package com.lnu.foundation.repository;

import com.lnu.foundation.model.TestSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by rucsac on 15/10/2018.
 */
@RepositoryRestResource
public interface TestSessionRepository extends JpaRepository<TestSession, Long> {
}
