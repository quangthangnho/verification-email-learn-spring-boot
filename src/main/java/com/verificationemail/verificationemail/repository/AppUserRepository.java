package com.verificationemail.verificationemail.repository;

import com.verificationemail.verificationemail.model.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {

    Optional<AppUserEntity> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUserEntity a SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);
}
