package com.jjgr.store_demo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //@Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<UserEntity> findByEmail(String email);
}