package com.lnu.foundation.repository;

import com.lnu.foundation.model.TestSession;
import com.lnu.foundation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by rucsac on 15/10/2018.
 */
@RepositoryRestResource
public interface TestSessionRepository extends JpaRepository<TestSession, Long> {

    List<TestSession> findByTest_Therapy_Med(User med);
}
